package com.softwaremagico.librodeesher.pj.perk;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.softwaremagico.files.Folder;
import com.softwaremagico.files.RolemasterFolderStructure;
import com.softwaremagico.librodeesher.basics.ChooseType;
import com.softwaremagico.librodeesher.basics.Spanish;
import com.softwaremagico.librodeesher.pj.categories.CategoryFactory;
import com.softwaremagico.librodeesher.pj.categories.ChooseCategoryGroup;
import com.softwaremagico.librodeesher.pj.categories.InvalidCategoryException;
import com.softwaremagico.librodeesher.pj.characteristic.Characteristics;
import com.softwaremagico.librodeesher.pj.characteristic.CharacteristicsAbbreviature;
import com.softwaremagico.librodeesher.pj.profession.ProfessionFactory;
import com.softwaremagico.librodeesher.pj.race.RaceFactory;
import com.softwaremagico.librodeesher.pj.skills.ChooseSkillGroup;
import com.softwaremagico.librodeesher.pj.skills.InvalidSkillException;
import com.softwaremagico.librodeesher.pj.skills.SkillFactory;
import com.softwaremagico.log.Log;

public class PerkFactory {
	private static HashMap<String, Perk> availablePerks = new HashMap<>();
	private static List<Perk> perksList = new ArrayList<>();

	static {
		try {
			getPerksFromFiles();
		} catch (Exception ex) {
			Log.errorMessage(PerkFactory.class.getName(), ex);
			ex.printStackTrace();
		}
	}

	public static List<Perk> gerPerks() {
		return perksList;
	}

	private static void getPerksFromFiles() throws Exception {
		List<String> perkFiles = RolemasterFolderStructure.getAvailablePerksFiles();
		if (perkFiles.size() > 0) {
			for (String file : perkFiles) {
				if (file.length() > 0) {
					int lineIndex = 0;
					List<String> lines = Folder.readFileLines(file, false);
					lineIndex = setPerks(lines, lineIndex);
				}
			}
		}
	}

	private static int setPerks(List<String> lines, int index) throws InvalidPerkException, InvalidCategoryException,
			InvalidSkillException {
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}
		while (index < lines.size() && !lines.get(index).equals("") && !lines.get(index).startsWith("#")) {
			String line = lines.get(index);
			if (line.length() > 2) { // No end of lines.
				try {
					String[] descomposed_line = line.split("\t");
					String perkName = descomposed_line[0];
					Integer cost = Integer.parseInt(descomposed_line[1]);
					List<String> races = getPerkAvailableToRaces(descomposed_line[2]);
					List<String> professions = getPerkAvailableToProfessions(descomposed_line[2]);
					PerkCategory classification = PerkCategory.getPerkCategory(descomposed_line[3]);
					String description = descomposed_line[5];
					Perk perk = new Perk(perkName, cost, classification, description, races, professions);
					String bonuses = descomposed_line[4];
					addBonuses(perk, bonuses);
					availablePerks.put(perk.getName(), perk);
					perksList.add(perk);
				} catch (NumberFormatException npe) {
					throw new InvalidPerkException("Error en el coste del talento:\"" + line + "\"");
				} catch (ArrayIndexOutOfBoundsException aiob) {
					throw new InvalidPerkException("Error en el talento:\"" + line + "\"");
				}
			}
			index++;
		}
		Collections.sort(perksList, new PerkComparator());
		return index;
	}

	private static ChooseType getOption(String bonusString) {
		if (bonusString.toLowerCase().contains(Spanish.COMMON_TAG)) {
			return ChooseType.COMMON;
		}
		return ChooseType.BONUS;
	}

	private static void addListToChooseBonus(Perk perk, String optionsLine) throws InvalidPerkException,
			InvalidCategoryException, InvalidSkillException {
		String[] set = optionsLine.split("\\(");

		// Obtain the number of options
		Integer options = Integer.parseInt(set[1].substring(set[1].indexOf("[") + 1, set[1].indexOf("]")).trim());

		// Obtain the bonus
		String bonusString = set[1].substring(set[1].indexOf("\\(") + 1, set[1].indexOf(")")).replace("*", "")
				.replace("r", "").trim();

		// Obtain the list to choose.
		if (set[0].toLowerCase().contains(Spanish.ANY_CATEGORY)) {
			perk.addCategoriesToChoose(new ChooseCategoryGroup(options, CategoryFactory.getCategories(),
					getOption(bonusString)));
		} else if (set[0].toLowerCase().contains(Spanish.ANY_WEAPON)) {
			perk.addSkillsToChoose(new ChooseSkillGroup(options, SkillFactory.getWeaponSkills(), getOption(bonusString)));
		} else if (set[0].toLowerCase().contains(Spanish.ANY_SKILL)) {
			perk.addSkillsToChoose(new ChooseSkillGroup(options, SkillFactory.getSkills(), ChooseType.BONUS));
		} else { // Obtain the list
			String purgedLine = set[0].replace("{", "").replace("}", "").replace("|", ",").replace(";", ",");
			String[] optionsToChoose = purgedLine.split(",");
			List<String> categoriesToChoose = new ArrayList<>();
			List<String> skillsToChoose = new ArrayList<>();
			for (String option : optionsToChoose) {
				option = option.trim();
				if (SkillFactory.existSkill(option)) {
					skillsToChoose.add(option);
				} else if (CategoryFactory.existCategory(option)) {
					categoriesToChoose.add(option);
				} else {
					throw new InvalidPerkException("Categoría o habilidad desconocida: " + option + " en talento "
							+ perk.getName());
				}
			}
			if (categoriesToChoose.size() > 0) {
				perk.addCategoriesToChoose(new ChooseCategoryGroup(options, categoriesToChoose
						.toArray(new String[categoriesToChoose.size()]), getOption(bonusString)));
			}
			if (skillsToChoose.size() > 0) {
				perk.addSkillsToChoose(new ChooseSkillGroup(options, skillsToChoose.toArray(new String[skillsToChoose
						.size()]), getOption(bonusString)));
			}
		}

		if (getOption(bonusString).equals(ChooseType.BONUS)) {
			perk.setChosenBonus(Integer.parseInt(bonusString));
		}
	}

	private static void addDefinedBonus(Perk perk, String optionsLine) throws InvalidPerkException {
		boolean conditionalBonus = false;
		boolean ranksBonus = false;
		String[] bonus = optionsLine.split("\\(");
		// For each bonus.
		String bonusName = bonus[0].trim();
		// Only description effect.
		if (bonusName.toLowerCase().contains(Spanish.NOTHING_TAG)) {
			// DO NOTHING
			return;
		}
		if (bonus[1].contains("*")) {
			conditionalBonus = true;
		}
		if (bonus[1].contains("r")) {
			ranksBonus = true;
		}
		String bonusString = bonus[1].replace(")", "").trim();
		if (CategoryFactory.existCategory(bonusName)) {
			if (bonusString.toLowerCase().contains(Spanish.COMMON_TAG)) {
				perk.setCategoryToSelectCommonSkills(CategoryFactory.getCategory(bonusName), 1);
			} else if (bonusString.toLowerCase().contains(Spanish.RESTRICTED_TAG)) {
				perk.setCategoryAsRestricted(bonusName, true);
			} else {
				Integer bonusNumber = Integer.parseInt(bonusString.replace("*", "").replace("r", ""));
				if (conditionalBonus) {
					perk.setCategoriesConditionalBonus(bonusName, bonusNumber);
				} else if (ranksBonus) {
					perk.setCategoryRanks(bonusName, bonusNumber);
				} else {
					perk.setCategoryBonus(bonusName, bonusNumber);
				}
			}
		} else if (SkillFactory.existSkill(bonusName)) {
			if (bonusString.toLowerCase().contains(Spanish.COMMON_TAG)) {
				perk.setSkillAsCommon(bonusName, true);
			} else if (bonusString.toLowerCase().contains(Spanish.RESTRICTED_TAG)) {
				perk.setSkillAsRestricted(bonusName, true);
			} else {
				Integer bonusNumber = Integer.parseInt(bonusString.replace("*", "").replace("r", ""));
				if (conditionalBonus) {
					perk.setSkillConditionalBonus(bonusName, bonusNumber);
				} else if (ranksBonus) {
					perk.setSkillRanks(bonusName, bonusNumber);
				} else {
					perk.setSkillBonus(bonusName, bonusNumber);
				}
			}
		} else if (bonusName.contains(Spanish.RESISTANCE_TAG)) {
			Integer bonusNumber = Integer.parseInt(bonusString);
			perk.setResistanceBonus(bonusName.replace(Spanish.RESISTANCE_TAG, "").trim(), bonusNumber);
		} else if (Characteristics.isCharacteristicValid(bonusName)) {
			Integer bonusNumber = Integer.parseInt(bonusString);
			perk.setCharacteristicBonus(CharacteristicsAbbreviature.getCharacteristicsAbbreviature(bonusName),
					bonusNumber);
		} else if (bonusName.toLowerCase().contains(Spanish.APPEARANCE_TAG)) {
			Integer bonusNumber = Integer.parseInt(bonusString);
			perk.setAppareanceBonus(bonusNumber);
		} else if (bonusName.toUpperCase().contains(Spanish.ARMOUR_TAG)) {
			Integer bonusNumber = Integer.parseInt(bonusString);
			perk.setArmour(bonusNumber);
		} else if (bonusName.toLowerCase().contains(Spanish.MOVEMENT)) {
			Integer bonusNumber = Integer.parseInt(bonusString);
			perk.setMovementBonus(bonusNumber);
		} else {
			throw new InvalidPerkException("Bonus " + bonusName + " no reconocido en el talento:\"" + perk.getName()
					+ "\"");
		}
	}

	private static void addBonuses(Perk perk, String bonusesDescription) throws InvalidPerkException,
			InvalidCategoryException, InvalidSkillException {
		String[] bonusesList = bonusesDescription.split(", ");
		try {
			for (String bonusSet : bonusesList) {
				// A list of skills or categories to choose.
				if (bonusSet.contains("{")) {
					addListToChooseBonus(perk, bonusSet);
					// One defined skill or category.
				} else {
					addDefinedBonus(perk, bonusSet);
				}
			}
		} catch (NumberFormatException npe) {
			throw new InvalidPerkException("Error en el bonus del talento:\"" + perk.getName() + "\"");
		}
	}

	private static List<String> getPerkAvailableToRaces(String racesLine) {
		List<String> availableRaces = new ArrayList<>();
		String[] races = racesLine.replace(";", ",").split(",");
		for (String race : races) {
			if (RaceFactory.getAvailableRaces().contains(race)) {
				availableRaces.add(race);
			}
		}
		return availableRaces;
	}

	private static List<String> getPerkAvailableToProfessions(String professionLine) {
		List<String> availableProfession = new ArrayList<>();
		String[] professions = professionLine.replace(";", ",").split(",");
		for (String profession : professions) {
			if (ProfessionFactory.getAvailableProfessions().contains(profession)) {
				availableProfession.add(profession);
			}
		}
		return availableProfession;
	}

	public static Perk getPerk(String name) {
		return availablePerks.get(name);
	}

	/**
	 * One perk can be identified by the name.
	 * 
	 * @param selectedPerk
	 * @return
	 */
	public static Perk getPerk(SelectedPerk selectedPerk) {
		return getPerk(selectedPerk.getName());
	}

}
