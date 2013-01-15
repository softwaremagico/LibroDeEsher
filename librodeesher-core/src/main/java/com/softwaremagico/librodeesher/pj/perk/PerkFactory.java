package com.softwaremagico.librodeesher.pj.perk;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;

import com.softwaremagico.files.Folder;
import com.softwaremagico.files.RolemasterFolderStructure;
import com.softwaremagico.librodeesher.basics.ShowMessage;
import com.softwaremagico.librodeesher.basics.Spanish;
import com.softwaremagico.librodeesher.pj.categories.CategoryFactory;
import com.softwaremagico.librodeesher.pj.characteristic.Characteristics;
import com.softwaremagico.librodeesher.pj.profession.ProfessionFactory;
import com.softwaremagico.librodeesher.pj.race.RaceFactory;
import com.softwaremagico.librodeesher.pj.skills.SkillFactory;

public class PerkFactory {
	private static Hashtable<String, Perk> availablePerks = new Hashtable<>();
	private static List<Perk> perksList = new ArrayList<>();

	static {
		try {
			getPerksFromFiles();
		} catch (Exception ex) {
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

	private static int setPerks(List<String> lines, int index) {
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}
		while (index < lines.size() && !lines.get(index).equals("") && !lines.get(index).startsWith("#")) {
			String line = lines.get(index);
			if (line.length() > 2) { // No end of lines.
				try {
					System.out.println(line);
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
					ShowMessage
							.showErrorMessage("Error en el coste del talento:\"" + line + "\"", "Talentos");
				} catch (ArrayIndexOutOfBoundsException aiob) {
					aiob.printStackTrace();
					ShowMessage.showErrorMessage("Error en el talento:\"" + line + "\"", "Talentos");
				}
			}
			index++;
		}
		Collections.sort(perksList, new PerkComparator());
		return index;
	}

	private static void addBonuses(Perk perk, String bonusesDescription) {
		String[] bonusesList = bonusesDescription.split(", ");
		try {
			for (String bonusSet : bonusesList) {
				boolean conditionalBonus = false;
				boolean ranksBonus = false;
				String[] bonus = bonusSet.split("\\(");
				// For each bonus.
				String bonusName = bonus[0].trim();
				// Only description effect.
				if (bonusName.toLowerCase().contains(Spanish.NO_BONUS_TAG)) {
					// DO NOTHING
					return;
				}

				if (bonus[1].contains("*")) {
					conditionalBonus = true;
				}
				if (bonus[1].contains("r")) {
					ranksBonus = true;
				}
				String bonusString = bonus[1].replace(")", "").replace("*", "").replace("r", "").trim();
				if (CategoryFactory.existCategory(bonusName)) {
					if (bonusString.toLowerCase().contains(Spanish.COMMON_TAG)) {
						perk.setCategoryToSelectCommonSkills(bonusName, 1);
					} else {
						Integer bonusNumber = Integer.parseInt(bonusString);
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
					} else {
						Integer bonusNumber = Integer.parseInt(bonusString);
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
					perk.setCharacteristicBonus(bonusName, bonusNumber);
				} else if (bonusName.toLowerCase().contains(Spanish.APPEARANCE_TAG)) {
					Integer bonusNumber = Integer.parseInt(bonusString);
					perk.setAppareanceBonus(bonusNumber);
				} else if (bonusName.toUpperCase().contains(Spanish.ARMOUR_TAG)) {
					Integer bonusNumber = Integer.parseInt(bonusString);
					perk.setArmour(bonusNumber);
				} else {
					ShowMessage.showErrorMessage("Bonus " + bonusName + " no reconocido en el talento:\""
							+ perk.getName() + "\"", "Talentos");
				}
			}
		} catch (NumberFormatException npe) {
			ShowMessage.showErrorMessage("Error en el bonus del talento:\"" + perk.getName() + "\"",
					"Talentos");
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

}
