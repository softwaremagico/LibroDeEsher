package com.softwaremagico.librodeesher.pj.export.txt;

import java.util.List;

import com.softwaremagico.files.Folder;
import com.softwaremagico.librodeesher.basics.Spanish;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.categories.Category;
import com.softwaremagico.librodeesher.pj.categories.CategoryFactory;
import com.softwaremagico.librodeesher.pj.characteristic.Characteristic;
import com.softwaremagico.librodeesher.pj.characteristic.Characteristics;
import com.softwaremagico.librodeesher.pj.characteristic.CharacteristicsAbbreviature;
import com.softwaremagico.librodeesher.pj.equipment.MagicObject;
import com.softwaremagico.librodeesher.pj.perk.Perk;
import com.softwaremagico.librodeesher.pj.resistance.ResistanceType;
import com.softwaremagico.librodeesher.pj.skills.Skill;
import com.softwaremagico.librodeesher.pj.weapons.WeaponFactory;

public class TxtSheet {
	private CharacterPlayer characterPlayer;

	public TxtSheet(CharacterPlayer characterPlayer) {
		this.characterPlayer = characterPlayer;
	}

	/**
	 * Create text with basic info.
	 */
	public static String basicCharacterInfo(CharacterPlayer characterPlayer) {
		String text = characterPlayer.getName() + "\tNº " + characterPlayer.getCurrentLevelNumber() + "\n"
				+ characterPlayer.getRace().getName() + " (" + characterPlayer.getCulture().getName() + ")"
				+ "\n" + characterPlayer.getProfession().getName();
		String trainings = "";
		if (characterPlayer.getTrainings().size() > 0) {
			for (String training : characterPlayer.getTrainings()) {
				if (trainings.length() > 0) {
					trainings += ", ";
				}
				trainings += training;
			}
			text += " (" + trainings + ")";
		}
		return text + "\n";
	}

	private static String getCharacteristicsInfo(CharacterPlayer characterPlayer) {
		String stringFormat = "%1$-8s %2$-8s %3$-8s %4$-8s %5$-8s %6$-8s %7$-8s%n";
		// String text = "Caract\tTemp\tPot\tTot\tRaza\tEsp\tTotal\n";
		String text = String.format(stringFormat, "Caract", "Temp", "Pot", "Tot", "Raza", "Esp", "Total");
		text += "---------------------------------------------------------------------------------\n";
		for (Characteristic characteristic : Characteristics.getCharacteristics()) {
			text = text
					+ String.format(
							stringFormat,
							characteristic.getAbbreviature().getTag(),
							characterPlayer.getCharacteristicTemporalValue(characteristic.getAbbreviature()),
							characterPlayer.getCharacteristicPotentialValue(characteristic.getAbbreviature()),
							characterPlayer.getCharacteristicTemporalBonus(characteristic.getAbbreviature()),
							characterPlayer.getCharacteristicRaceBonus(characteristic.getAbbreviature()),
							characterPlayer.getCharacteristicSpecialBonus(characteristic.getAbbreviature()),
							characterPlayer.getCharacteristicTotalBonus(characteristic.getAbbreviature()));
		}
		return text;
	}

	public static String exportSkillsToText(CharacterPlayer characterPlayer) {
		int sizeMaxIncrease = 3;
		int maxNameSize = getMaxNameLength(characterPlayer);
		String categoryFormat = "%1$-" + (maxNameSize + sizeMaxIncrease)
				+ "s %2$-8s %3$-8s %4$-8s %5$-8s %6$-8s %7$-8s%n";
		String stringFormat = "%1$-" + (maxNameSize + sizeMaxIncrease + 9)
				+ "s %2$-8s %3$-8s %4$-8s %5$-8s %6$-8s%n";
		String text = String.format(categoryFormat, "Nombre", "Coste", "Rangos", "Bonus", "Car", "Otros",
				"Total");
		text += "------------------------------------------------------------------"
				+ "-------------------------------------------------------------\n";
		for (int i = 0; i < CategoryFactory.getCategories().size(); i++) {
			Category category = CategoryFactory.getCategories().get(i);
			category = characterPlayer.getCategory(category);
			if (characterPlayer.isCategoryUseful(category)) {
				String bonus = "";
				if (characterPlayer.getHistorial().isHistorialPointSelected(category)) {
					bonus += "H";
				}
				if (characterPlayer.getPerkBonus(category) != 0) {
					bonus += "T";
				}
				if (characterPlayer.getItemBonus(category) != 0) {
					bonus += "O";
				}
				if (!bonus.equals("")) {
					bonus = "(" + bonus + ")";
				}
				bonus = characterPlayer.getBonus(category) + bonus;

				text = text
						+ String.format(categoryFormat, category.getName(), characterPlayer
								.getCategoryCost(category, 0).getCostTag(), characterPlayer
								.getTotalRanks(category), characterPlayer.getRanksValue(category),
								characterPlayer.getCharacteristicsBonus(category), bonus, characterPlayer
										.getTotalValue(category));

				for (int j = 0; j < category.getSkills().size(); j++) {
					Skill skill = category.getSkills().get(j);
					if (characterPlayer.isSkillInteresting(skill)) {
						bonus = "";
						if (characterPlayer.getHistorial().isHistorialPointSelected(skill)) {
							bonus += "H";
						}
						int perkBonus = characterPlayer.getPerkBonus(skill);
						int conditionalPerkBonus = characterPlayer.getConditionalPerkBonus(skill);
						if (perkBonus != 0 || conditionalPerkBonus != 0) {
							bonus += "T";
							if (conditionalPerkBonus > 0) {
								bonus += "*";
							}
						}
						if (characterPlayer.getItemBonus(skill) != 0) {
							bonus += "O";
						}
						if (!bonus.equals("")) {
							bonus = "(" + bonus + ")";
						}
						bonus = characterPlayer.getBonus(skill) + bonus;

						String total;
						if (characterPlayer.getItemBonus(skill) > 0 || conditionalPerkBonus > 0) {
							total = (characterPlayer.getTotalValue(skill)
									- characterPlayer.getItemBonus(skill) - conditionalPerkBonus)
									+ "/" + characterPlayer.getTotalValue(skill);
						} else {
							total = characterPlayer.getTotalValue(skill).toString();
						}
						text = text
								+ String.format(
										stringFormat,
										"  *  "
												+ getNameSpecificLength(characterPlayer, skill, maxNameSize
														+ sizeMaxIncrease - 3),
										characterPlayer.getTotalRanks(skill),
										characterPlayer.getRanksValue(skill),
										characterPlayer.getTotalValue(category), bonus, total);

						// Add specialized skills.
						for (int m = 0; m < characterPlayer.getSkillSpecializations(skill).size(); m++) {
							if (characterPlayer.getItemBonus(skill) > 0 || conditionalPerkBonus > 0) {
								total = (characterPlayer.getSpecializedTotalValue(skill)
										- characterPlayer.getItemBonus(skill) - conditionalPerkBonus)
										+ "/" + characterPlayer.getSpecializedTotalValue(skill) + "";
							} else {
								total = characterPlayer.getSpecializedTotalValue(skill).toString();
							}
							text = text
									+ String.format(stringFormat, "  *  "
											+ characterPlayer.getSkillSpecializations(skill).get(m), "",
											characterPlayer.getSpecializedRanksValue(skill),
											characterPlayer.getTotalValue(category), bonus, total);
						}
					}
				}
			}
		}
		return text;
	}

	public static String exportItems(CharacterPlayer characterPlayer) {
		String text = "";
		text = text.replaceAll("\t", "  ");
		if (characterPlayer.getEquipment().size() > 0) {
			text = "Equipo:\n";
			text += "--------------------------------------------------\n";
			for (int i = 0; i < characterPlayer.getEquipment().size(); i++) {
				text += characterPlayer.getEquipment().get(i) + "\n\n";
			}
			text += "\n";
		}

		if (characterPlayer.getMagicItems().size() > 0) {
			text = "Objetos:\n";
			text += "--------------------------------------------------\n";
			for (int i = 0; i < characterPlayer.getMagicItems().size(); i++) {
				MagicObject magicItem = characterPlayer.getMagicItems().get(i);
				String magicItemString = magicItem.getName();
				if (magicItem.getDescription() != null && magicItem.getDescription().length() > 0) {
					magicItemString += " (" + magicItem.getDescription() + ")";
				}
				if (!magicItem.getBonus().isEmpty()) {
					magicItemString += ": ";
				}
				for (int j = 0; j < magicItem.getBonus().size(); j++) {
					magicItemString += magicItem.getBonus().get(j).getBonus() + " a "
							+ magicItem.getBonus().get(j).getBonusName();
					if (j < magicItem.getBonus().size() - 1) {
						magicItemString += ", ";
					}
				}

				text += magicItemString + "\n\n";
			}
			text += "\n";
		}
		return text;
	}

	public static String exportResistances(CharacterPlayer characterPlayer) {
		String text = "Modificación a las TR\n";
		text += "--------------------------------------------------\n";
		String stringFormat = "%1$-15s %2$-8s%n";
		text += String.format(stringFormat, "Canalización",
				characterPlayer.getResistanceBonus(ResistanceType.CANALIZATION));
		text += String.format(stringFormat, "Esencia",
				characterPlayer.getResistanceBonus(ResistanceType.ESSENCE));
		text += String.format(stringFormat, "Mentalismo",
				characterPlayer.getResistanceBonus(ResistanceType.MENTALISM));
		text += String.format(stringFormat, "Psiónico",
				characterPlayer.getResistanceBonus(ResistanceType.PSIONIC));
		text += String
				.format(stringFormat, "Veneno", characterPlayer.getResistanceBonus(ResistanceType.POISON));
		text += String.format(stringFormat, "Enfermedad",
				characterPlayer.getResistanceBonus(ResistanceType.DISEASE));
		text += String.format(stringFormat, "Miedo", characterPlayer.getResistanceBonus(ResistanceType.FEAR));
		text += String.format(stringFormat, "Frío", characterPlayer.getResistanceBonus(ResistanceType.COLD));
		text += String.format(stringFormat, "Calor", characterPlayer.getResistanceBonus(ResistanceType.HOT));
		return text;
	}

	public static String exportSpecials(CharacterPlayer characterPlayer) {
		if (characterPlayer.getRace().getSpecials().isEmpty()) {
			return "";
		}
		String text = "Especiales:\n";
		text += "--------------------------------------------------\n";
		for (int i = 0; i < characterPlayer.getRace().getSpecials().size(); i++) {
			text += characterPlayer.getRace().getSpecials().get(i) + "\n\n";
		}
		text = text.replaceAll("\t", "  ");
		return text;
	}

	public static String exportPerks(CharacterPlayer characterPlayer) {
		if (characterPlayer.getPerks().isEmpty()) {
			return "";
		}
		String text = "Talentos:\n";
		text += "--------------------------------------------------\n";
		for (int i = 0; i < characterPlayer.getPerks().size(); i++) {
			Perk perk = characterPlayer.getPerks().get(i);
			text += perk.getName() + ":\t " + perk.getLongDescription() + "\n\n";
		}
		return text;
	}

	/**
	 * Exporta un personaje a txt.
	 */
	public boolean exportSheet(String file) {
		if (file == null || file.equals("")) {
			file = getFileName(characterPlayer);
		} else {
			if (!file.endsWith(".txt")) {
				file += ".txt";
			}
		}
		String text = getCharacterStandardSheetAsText(characterPlayer);
		Folder.saveTextInFile(text, file);
		return true;
	}

	public static String getCharacterStandardSheetAsText(CharacterPlayer characterPlayer) {
		return basicCharacterInfo(characterPlayer) + "\n\n" + getCharacteristicsInfo(characterPlayer)
				+ "\n\n" + exportResistances(characterPlayer) + "\n\n" + exportSkillsToText(characterPlayer)
				+ "\n\n" + exportPerks(characterPlayer) + "\n\n" + exportSpecials(characterPlayer) + "\n\n"
				+ exportItems(characterPlayer);
	}

	private static String getSizeCode(CharacterPlayer characterPlayer) {
		return characterPlayer.getRace().getSize().getAbbreviature();
	}

	private static Skill getBestCloseCombatAttack(CharacterPlayer characterPlayer) {
		return getBestSkillValue(characterPlayer, CategoryFactory.getCloseCombatWeapons());
	}

	private static String getAttackCode(CharacterPlayer characterPlayer, Skill skill) {
		if (WeaponFactory.getWeapon(skill.getName()) != null) {
			return WeaponFactory.getWeapon(skill.getName()).getAbbreviature();
		}

		if (skill.getName().equals(Spanish.WEAPONS_RACE_CLAW)) {
			return getSizeCode(characterPlayer) + "Ga";
		}
		if (skill.getName().equals(Spanish.WEAPONS_RACE_BITE)) {
			return getSizeCode(characterPlayer) + "Mo";
		}
		if (skill.getName().equals(Spanish.WEAPONS_RACE_OTHERS)) {
			return "??";
		}
		return skill.getCategory().getAbbreviature();
	}

	private static Skill getBestLongRangeAttack(CharacterPlayer characterPlayer) {
		return getBestSkillValue(characterPlayer, CategoryFactory.getLongRangeWeapons());
	}

	private static Skill getBestSkillValue(CharacterPlayer characterPlayer, List<Category> categories) {
		Skill bestAttack = null;
		for (Category category : categories) {
			for (Skill skill : category.getSkills()) {
				if (bestAttack == null) {
					bestAttack = skill;
				} else {
					if (characterPlayer.getTotalValue(skill) > characterPlayer.getTotalValue(bestAttack)) {
						bestAttack = skill;
					}
				}
			}
		}
		return bestAttack;
	}

	private static Skill getBestOthersAttack(CharacterPlayer characterPlayer) {
		return getBestSkillValue(characterPlayer, CategoryFactory.getOthersAttack());
	}

	private static String getMovementCode(CharacterPlayer characterPlayer) {
		int rapidez = characterPlayer.getCharacteristicTotalBonus(CharacteristicsAbbreviature.SPEED);
		if (rapidez <= -14) {
			return "IM";
		}
		if (rapidez <= -10) {
			return "AR";
		}
		if (rapidez <= -6) {
			return "ML";
		}
		if (rapidez <= -2) {
			return "L";
		}
		if (rapidez <= 2) {
			return "N";
		}
		if (rapidez <= 6) {
			return "MdR";
		}
		if (rapidez <= 10) {
			return "R";
		}
		if (rapidez <= 14) {
			return "MR";
		}
		return "RS";
	}

	/**
	 * Similar to Monsters and Treasures book.
	 */
	private static String generateCharacterAsMonster(CharacterPlayer characterPlayer) {
		Skill habCC = getBestCloseCombatAttack(characterPlayer);
		Skill habProy = getBestLongRangeAttack(characterPlayer);
		Skill habAtaq = getBestOthersAttack(characterPlayer);
		String vel = getMovementCode(characterPlayer) + "   ";
		int PV = characterPlayer.getTotalDevelopmentPoints();
		String attackString = characterPlayer.getTotalValue(habCC) + getAttackCode(characterPlayer, habCC);
		if (habProy != null && characterPlayer.getTotalValue(habProy) > 0) {
			attackString += "/" + characterPlayer.getTotalValue(habProy)
					+ getAttackCode(characterPlayer, habProy);
		}
		if (habAtaq != null && characterPlayer.getTotalValue(habAtaq) > 0) {
			attackString += "/" + characterPlayer.getTotalValue(habAtaq)
					+ getAttackCode(characterPlayer, habAtaq);
		}
		int maxNameSize = Math.max(characterPlayer.getRace().getName().length() + 2, 5);

		String stringFormat = "%1$-" + maxNameSize
				+ "s %2$-8s %3$-8s %4$-8s %5$-8s %6$-8s %7$-8s %8$-8s %9$-12s%n";
		String text = String.format(stringFormat, "Raza", "Nivel", "Movim.", "MM", "VM/VA", "Tam", "PV",
				"TA(BD)", "Ataques");
		text += String.format(stringFormat, characterPlayer.getRace().getName(),
				characterPlayer.getCurrentLevelNumber(), characterPlayer.getMovementCapacity(),
				(characterPlayer.getCharacteristicTotalBonus(CharacteristicsAbbreviature.AGILITY) * 3) + "",
				vel, getSizeCode(characterPlayer), PV, characterPlayer.getArmourClass() + "("
						+ characterPlayer.getDefensiveBonus() + ")", attackString);
		return text;
	}

	private static String generatedShortedSkills(CharacterPlayer characterPlayer) {
		String text = "";
		for (int i = 0; i < CategoryFactory.getCategories().size(); i++) {
			Category category = CategoryFactory.getCategories().get(i);
			text += category.getAbbreviature() + " " + characterPlayer.getTotalValue(category);
			int added = 0;
			for (Skill skill : category.getSkills()) {
				if (characterPlayer.getTotalRanks(skill) > 0) {
					if (added == 0) {
						text += " (";
					}
					if (added > 0) {
						text += ", ";
					}
					text += skill.getName() + " " + characterPlayer.getTotalValue(skill);
					added++;
				}
			}
			if (added > 0) {
				text += ")";
			}
			if (i < CategoryFactory.getCategories().size() - 1) {
				text += ", ";
			}
			if (i == CategoryFactory.getCategories().size() - 1) {
				text += ".";
			}
		}
		return text;
	}

	public static boolean exportCharacterAbbreviature(CharacterPlayer characterPlayer, String file) {
		if (file == null || file.equals("")) {
			file = getFileName(characterPlayer);
		} else {
			if (!file.endsWith(".txt")) {
				file += ".txt";
			}
		}
		String text = characterPlayer.getName() + " (" + characterPlayer.getProfession().getName() + ")\n"
				+ generateCharacterAsMonster(characterPlayer) + "\n" + "HABILIDADES: \n"
				+ generatedShortedSkills(characterPlayer);
		Folder.saveTextInFile(text, file);
		return true;
	}

	public static String getNameSpecificLength(CharacterPlayer characterPlayer, Skill skill, int length) {
		String newName = formatName(characterPlayer, skill);

		String sufix = "";
		int longSufix = 0;

		if (characterPlayer.isRestricted(skill)) {
			sufix += " (r)";
			longSufix += 4;
		} else {
			if (characterPlayer.isProfessional(skill)) {
				sufix += " (p)";
				longSufix += 4;
			} else if (characterPlayer.isCommon(skill)) {
				sufix += " (c)";
				longSufix += 4;
			}
			if (characterPlayer.isGeneralized(skill)) {
				sufix += " (g)";
				longSufix += 4;
			}
		}

		// too long
		if (length < newName.length() + longSufix) {
			newName = newName.substring(0, length - longSufix);
		}

		newName += sufix;

		// too short
		if (length > newName.length()) {
			for (int i = newName.length(); i < length; i++) {
				newName += " ";
			}
		}

		return newName;
	}

	public static String formatSpecializedName(String name, int length) {
		// too long
		if (length < name.length()) {
			name = name.substring(0, length);
		}

		// too short
		if (length > name.length()) {
			for (int i = name.length(); i < length; i++) {
				name += " ";
			}
		}
		return name;
	}

	public static String formatName(CharacterPlayer characterPlayer, Skill skill) {
		if (characterPlayer.isGeneralized(skill)) {
			String[] name = skill.getName().split(":");
			return name[0];
		}
		return skill.getName();
	}

	public static String getNameSpecificLength(Category category, int length) {
		String nuevoNombre = formatName(category);
		return getNameSpecificLength(nuevoNombre, length);
	}

	public static String getNameSpecificLength(String text, int length) {
		if (length > text.length()) {
			for (int i = text.length(); i < length; i++) {
				text += " ";
			}
		} else {
			return text.substring(0, length - 1);
		}
		return text;
	}

	public static String formatName(Category category) {
		return category.getName();
	}

	public static int getMaxNameLength(CharacterPlayer characterPlayer) {
		int maxLength = 0;
		for (Category category : CategoryFactory.getCategories()) {
			int categoryNameLength = category.getName().length();
			if (maxLength < categoryNameLength) {
				maxLength = categoryNameLength;
			}
			for (Skill skill : category.getSkills()) {
				if (characterPlayer.isSkillInteresting(skill)) {
					int skillNameLength = skill.getName().length();
					if (maxLength < skillNameLength) {
						maxLength = skillNameLength;
					}
				}
			}
		}
		return maxLength;
	}

	private static String getFileName(CharacterPlayer characterPlayer) {
		return characterPlayer.getName() + "_N" + characterPlayer.getCurrentLevelNumber() + "_"
				+ characterPlayer.getRace().getName() + "_" + characterPlayer.getProfession().getName()
				+ ".txt";
	}
}
