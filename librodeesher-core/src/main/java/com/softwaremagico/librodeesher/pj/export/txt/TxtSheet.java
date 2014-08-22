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
import com.softwaremagico.librodeesher.pj.perk.Perk;
import com.softwaremagico.librodeesher.pj.resistance.ResistanceType;
import com.softwaremagico.librodeesher.pj.skills.Skill;
import com.softwaremagico.librodeesher.pj.training.TrainingItem;
import com.softwaremagico.librodeesher.pj.weapons.WeaponFactory;

public class TxtSheet {
	private CharacterPlayer characterPlayer;

	public TxtSheet(CharacterPlayer characterPlayer) {
		this.characterPlayer = characterPlayer;
	}

	/**
	 * Genera un texto con el nombre, raza, profesion y otros detalles del characterPlayer.
	 */
	public static String basicCharacterInfo(CharacterPlayer characterPlayer) {
		return characterPlayer.getName() + "\tNº " + characterPlayer.getCurrentLevelNumber() + "\n"
				+ characterPlayer.getRace().getName() + "\n" + characterPlayer.getProfession().getName() + "\n";
	}

	private static String getCharacteristicsInfo(CharacterPlayer characterPlayer) {
		List<Characteristic> characteristics = Characteristics.getCharacteristics();
		String text = "Caract\tTemp\tPot\tTot\tRaza\tEsp\tTotal\n";
		text += "---------------------------------------------------------------------------------\n";
		for (int i = 0; i < characteristics.size(); i++) {
			text = text + characteristics.get(i).getAbbreviature().getAbbreviature() + "\t"
					+ characterPlayer.getCharacteristicTemporalValue(characteristics.get(i).getAbbreviature()) + "\t"
					+ characterPlayer.getCharacteristicPotencialValue().get(characteristics.get(i).getAbbreviature())
					+ "\t" + characterPlayer.getCharacteristicTemporalBonus(characteristics.get(i).getAbbreviature())
					+ "\t" + characterPlayer.getCharacteristicRaceBonus(characteristics.get(i).getAbbreviature())
					+ "\t" + characterPlayer.getCharacteristicSpecialBonus(characteristics.get(i).getAbbreviature())
					+ "\t" + characterPlayer.getCharacteristicTotalBonus(characteristics.get(i).getAbbreviature())
					+ "\n";
		}
		return text;
	}

	public static String exportSkillsToText(CharacterPlayer characterPlayer) {
		int sizeMaxIncrease = 3;
		int maxNameSize = getMaxNameLength(characterPlayer);
		String text = getNameSpecificLength("Nombre: ", maxNameSize) + "\tCoste\tRang\tBon\tCar\tOtros\tTotal\n";
		text += "------------------------------------------------------------------"
				+ "------------------------------------------------------------------"
				+ "-------------------------------------------------------------\n";
		for (int i = 0; i < CategoryFactory.getCategories().size(); i++) {
			Category category = CategoryFactory.getCategories().get(i);
			if (characterPlayer.isCategoryUseful(category)) {
				text = text + getNameSpecificLength(category, maxNameSize + sizeMaxIncrease) + "\t"
						+ characterPlayer.getCategoryCost(category, 0).getCostTag() + "\t"
						+ characterPlayer.getTotalRanks(category) + "\t" + characterPlayer.getRanksValue(category)
						+ "\t" + characterPlayer.getCharacteristicsBonus(category) + "\t"
						+ characterPlayer.getBonus(category);
				String letra = "";
				if (characterPlayer.getHistorial().isHistorialPointSelected(category)) {
					letra += "H";
				}
				if (characterPlayer.getPerkBonus(category) != 0) {
					letra += "T";
				}
				if (characterPlayer.getItemBonus(category) != 0) {
					letra += "O";
				}
				if (!letra.equals("")) {
					text += "(" + letra + ")";
				}
				text += "\t" + characterPlayer.getTotalValue(category) + "\n";
				for (int j = 0; j < category.getSkills().size(); j++) {
					Skill skill = category.getSkills().get(j);
					if (characterPlayer.isSkillInteresting(skill)) {
						text = text + " * "
								+ getNameSpecificLength(characterPlayer, skill, maxNameSize + sizeMaxIncrease - 5);
						text = text + "\t" + "\t" + characterPlayer.getTotalRanks(skill) + "\t"
								+ characterPlayer.getRanksValue(skill) + "\t" + characterPlayer.getTotalValue(category)
								+ "\t" + characterPlayer.getBonus(skill);
						letra = "";
						if (characterPlayer.getHistorial().isHistorialPointSelected(skill)) {
							letra += "H";
						}
						int bonusTalentos = characterPlayer.getConditionalPerkBonus(skill);
						int conditionalPerkBonus = characterPlayer.getConditionalPerkBonus(skill);
						if (bonusTalentos != 0) {
							letra += "T";
							if (conditionalPerkBonus > 0) {
								letra += "*";
							}
						}
						if (characterPlayer.getItemBonus(skill) != 0) {
							letra += "O";
						}
						if (!letra.equals("")) {
							text += "(" + letra + ")";
						}
						if (characterPlayer.getItemBonus(skill) > 0 || conditionalPerkBonus > 0) {
							text += "\t"
									+ (characterPlayer.getTotalValue(skill) - characterPlayer.getItemBonus(skill) - conditionalPerkBonus)
									+ "/" + characterPlayer.getTotalValue(skill) + "";
						} else {
							text += "\t" + characterPlayer.getTotalValue(skill);
						}
						text += "\n";
						// Mostramos las habilidades especializadas.
						for (int m = 0; m < characterPlayer.getSkillSpecializations(skill).size(); m++) {
							text = text
									+ " * "
									+ formatSpecializedName(characterPlayer.getSkillSpecializations(skill).get(m),
											maxNameSize + sizeMaxIncrease);
							text = text + "\t" + "\t" + characterPlayer.getSpecializedRanks(skill) + "\t"
									+ characterPlayer.getSpecializedRanksValue(skill) + "\t"
									+ characterPlayer.getTotalValue(skill.getCategory()) + "\t"
									+ characterPlayer.getItemBonus(skill);
							letra = "";
							if (characterPlayer.isHistoryPointSelected(skill)) {
								letra += "H";
							}
							bonusTalentos = characterPlayer.getConditionalPerkBonus(skill);
							conditionalPerkBonus = characterPlayer.getConditionalPerkBonus(skill);
							if (bonusTalentos != 0) {
								letra += "T";
								if (conditionalPerkBonus > 0) {
									letra += "*";
								}
							}
							if (characterPlayer.getItemBonus(skill) != 0) {
								letra += "O";
							}
							if (!letra.equals("")) {
								text += "(" + letra + ")";
							}

							if (characterPlayer.getItemBonus(skill) > 0 || conditionalPerkBonus > 0) {
								text += "\t"
										+ (characterPlayer.getSpecializedTotalValue(skill)
												- characterPlayer.getItemBonus(skill) - conditionalPerkBonus) + "/"
										+ characterPlayer.getTotalValue(skill) + "";
							} else {
								text += "\t" + characterPlayer.getSpecializedTotalValue(skill);
							}
							text += "\n";
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
				TrainingItem magicItem = characterPlayer.getMagicItems().get(i);
				// TODO add characteristics of object
				text += magicItem.getName() + ": "; // +
													// magicItem.DevolverPropiedades();
				text += "\n\n";
			}
			text += "\n";
		}
		return text;
	}

	public static String exportResistances(CharacterPlayer characterPlayer) {
		String texto = "Modificación a las TR\n";
		texto += "--------------------------------------------------\n";
		texto += "Canalización \t" + (characterPlayer.getResistanceBonus(ResistanceType.CANALIZATION)) + "\n";
		texto += "Esencia \t" + (characterPlayer.getResistanceBonus(ResistanceType.ESSENCE)) + "\n";
		texto += "Mentalismo \t" + (characterPlayer.getResistanceBonus(ResistanceType.MENTALISM)) + "\n";
		texto += "Psiónico \t" + characterPlayer.getResistanceBonus(ResistanceType.PSIONIC) + "\n";
		texto += "Veneno \t" + (characterPlayer.getResistanceBonus(ResistanceType.POISON)) + "\n";
		texto += "Enfermedad \t" + (characterPlayer.getResistanceBonus(ResistanceType.DISEASE)) + "\n";
		texto += "Miedo \t" + (characterPlayer.getResistanceBonus(ResistanceType.FEAR)) + "\n";
		texto += "Frío \t" + characterPlayer.getResistanceBonus(ResistanceType.COLD) + "\n";
		texto += "Calor \t" + characterPlayer.getResistanceBonus(ResistanceType.HOT) + "\n";
		return texto;
	}

	public static String exportSpecials(CharacterPlayer characterPlayer) {
		String text = "Especiales:\n";
		text += "--------------------------------------------------\n";
		for (int i = 0; i < characterPlayer.getRace().getSpecials().size(); i++) {
			text += characterPlayer.getRace().getSpecials().get(i) + "\n\n";
		}
		text = text.replaceAll("\t", "  ");
		return text;
	}

	public static String exportPerks(CharacterPlayer characterPlayer) {
		String text = "Talentos:\n";
		text += "--------------------------------------------------\n";
		for (int i = 0; i < characterPlayer.getPerks().size(); i++) {
			Perk perk = characterPlayer.getPerks().get(i);
			text += perk.getName() + ":\t " + perk.getLongDescription() + ".\n\n";
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
		String text = basicCharacterInfo(characterPlayer) + "\n\n" + getCharacteristicsInfo(characterPlayer) + "\n\n"
				+ exportResistances(characterPlayer) + "\n\n" + exportSkillsToText(characterPlayer) + "\n\n"
				+ exportPerks(characterPlayer) + "\n\n" + exportSpecials(characterPlayer) + "\n\n"
				+ exportItems(characterPlayer);
		Folder.saveTextInFile(text, file);
		return true;
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

		if (skill.getName().equals(Spanish.WEAPONS_CLAW)) {
			return getSizeCode(characterPlayer) + "Ga";
		}
		if (skill.getName().equals(Spanish.WEAPONS_BITE)) {
			return getSizeCode(characterPlayer) + "Mo";
		}
		if (skill.getName().equals(Spanish.WEAPONS_OTHERS)) {
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
		String texto = getNameSpecificLength("Raza", Math.max(characterPlayer.getRace().getName().length(), 5))
				+ "\tNivel\tMov.\tMM\tVM/VA\tTam\tPV\tTA(BD)\tAtaques\n";
		texto += characterPlayer.getRace().getName() + "\t" + characterPlayer.getCurrentLevelNumber() + "\t"
				+ characterPlayer.getMovementCapacity() + "\t"
				+ characterPlayer.getCharacteristicTotalBonus(CharacteristicsAbbreviature.AGILITY) * 3 + "\t" + vel
				+ "\t" + getSizeCode(characterPlayer) + "\t" + PV + "\t " + characterPlayer.getArmourClass() + " ("
				+ characterPlayer.getDefensiveBonus() + ")\t" + characterPlayer.getTotalValue(habCC)
				+ getAttackCode(characterPlayer, habCC);
		if (habProy != null && characterPlayer.getTotalValue(habProy) > 0) {
			texto += "/" + characterPlayer.getTotalValue(habProy) + getAttackCode(characterPlayer, habProy);
		}
		if (habAtaq != null && characterPlayer.getTotalValue(habAtaq) > 0) {
			texto += "/" + characterPlayer.getTotalValue(habAtaq) + getAttackCode(characterPlayer, habAtaq) + " \n";
		}
		return texto;
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

	public boolean exportCharacterAbbreviature(String file) {
		if (file == null || file.equals("")) {
			file = getFileName(characterPlayer);
		} else {
			if (!file.endsWith(".txt")) {
				file += ".txt";
			}
		}
		String text = characterPlayer.getName() + "\n" + generateCharacterAsMonster(characterPlayer) + "\n"
				+ "HABILIDADES: \n" + generatedShortedSkills(characterPlayer);
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
			if (characterPlayer.isCommon(skill)) {
				sufix += " (c)";
				longSufix += 4;
			}
			if (characterPlayer.isProfessional(skill)) {
				sufix += " (p)";
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
				+ characterPlayer.getRace().getName() + "_" + characterPlayer.getProfession().getName() + ".txt";
	}
}