package com.softwaremagico.librodeesher.pj.training;

/*
 * #%L
 * Libro de Esher
 * %%
 * Copyright (C) 2007 - 2012 Softwaremagico
 * %%
 * This software is designed by Jorge Hortelano Otero. Jorge Hortelano Otero
 * <softwaremagico@gmail.com> C/Quart 89, 3. Valencia CP:46008 (Spain).
 *  
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *  
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *  
 * You should have received a copy of the GNU General Public License along with
 * this program; If not, see <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import com.softwaremagico.files.Folder;
import com.softwaremagico.files.RolemasterFolderStructure;
import com.softwaremagico.librodeesher.basics.ShowMessage;
import com.softwaremagico.librodeesher.pj.categories.CategoryFactory;
import com.softwaremagico.librodeesher.pj.characteristic.Characteristic;
import com.softwaremagico.librodeesher.pj.characteristic.Characteristics;
import com.softwaremagico.librodeesher.pj.skills.ChooseSkillGroup;
import com.softwaremagico.librodeesher.pj.skills.Skill;
import com.softwaremagico.librodeesher.pj.skills.SkillFactory;

public class Training {
	private String name;
	private Integer trainingTime;
	private List<String> limitedRaces;
	private List<TrainingSpecial> specials;
	private List<TrainingCategory> categories;
	private List<List<Characteristic>> updateCharacteristics; // Choose one
	private List<MinimalCharacteristicRequired> characteristicRequirements;
	private List<MinimalSkillRequired> skillRequirements;
	private List<ChooseSkillGroup> lifeSkills;
	private List<ChooseSkillGroup> commonSkills;
	private List<ChooseSkillGroup> professionalSkills;
	private List<ChooseSkillGroup> restrictedSkills;

	Training(String name) {
		this.name = name;
		try {
			readTrainingFile(name);
		} catch (Exception ex) {
			Logger.getLogger(Training.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private void readTrainingFile(String trainingName) throws Exception {
		int lineIndex = 0;
		String trainingFile = RolemasterFolderStructure.getDirectoryModule(TrainingFactory.TRAINING_FOLDER
				+ File.separator + trainingName + ".txt");
		if (trainingFile.length() > 0) {
			List<String> lines = Folder.readFileLines(trainingFile, false);
			lineIndex = setTrainingTime(lines, lineIndex);
			lineIndex = setLimitedRaces(lines, lineIndex);
			lineIndex = setTrainingSpecial(lines, lineIndex);
			lineIndex = setTrainingSkills(lines, lineIndex);
			lineIndex = setCharacteristicsUpgrade(lines, lineIndex);
			lineIndex = setProfessionalRequirements(lines, lineIndex);
			lineIndex = setSpecialSkills(lines, lineIndex, lifeSkills);
			lineIndex = setSpecialSkills(lines, lineIndex, commonSkills);
			lineIndex = setSpecialSkills(lines, lineIndex, professionalSkills);
			lineIndex = setSpecialSkills(lines, lineIndex, restrictedSkills);
		}
	}

	public int setTrainingTime(List<String> lines, int index) {
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}
		try {
			trainingTime = Integer.parseInt(lines.get(index));
		} catch (Exception e) {
			ShowMessage.showErrorMessage("Problema con la linea: \"" + lines.get(index)
					+ "\" del adiestramiento " + name, "Leer adiestramientos");
		}
		return ++index;
	}

	public int setLimitedRaces(List<String> lines, int index) {
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}
		limitedRaces = new ArrayList<>();
		while (!lines.get(index).equals("") && !lines.get(index).startsWith("#")) {
			String trainingLine = lines.get(index);
			try {
				String[] limitedRacesColumn = trainingLine.split(", ");
				for (int i = 0; i < limitedRacesColumn.length; i++) {
					if (!limitedRacesColumn[i].toLowerCase().contains("ningun")) {
						limitedRaces.add(limitedRacesColumn[i]);
					}
				}
			} catch (ArrayIndexOutOfBoundsException aiofb) {
				ShowMessage.showErrorMessage("Problema con la linea: \"" + trainingLine
						+ "\" del adiestramiento " + name, "Leer adiestramientos");
			}
			index++;
		}
		return index;
	}

	public int setTrainingSpecial(List<String> lines, int index) {
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}
		int bonus;
		int probability = 0;
		specials = new ArrayList<>();
		while (!lines.get(index).equals("")) {
			String trainingLine = lines.get(index);
			try {
				String[] specialColumns = trainingLine.split("\t");
				String special = specialColumns[0];
				try {
					probability = Integer.parseInt(specialColumns[1]);
					if (specialColumns.length > 2) {
						bonus = Integer.parseInt(specialColumns[2]);
					} else {
						bonus = 0;
					}
				} catch (NumberFormatException nfe) {
					ShowMessage.showErrorMessage("Formato de porcentaje de especial \"" + special
							+ "\" erróneo en adiestramiento " + name, "Leer adiestramientos");
					continue;
				}
				specials.add(new TrainingSpecial(special, bonus, probability));
			} catch (ArrayIndexOutOfBoundsException aiofb) {
				ShowMessage.showErrorMessage("Problema con la linea: \"" + trainingLine
						+ "\" del adiestramiento " + name, "Leer adiestramientos");
			}
			index++;
		}
		return index;
	}

	public int setTrainingSkills(List<String> lines, int index) {
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}
		categories = new ArrayList<>();
		TrainingCategory category = null;
		while (!lines.get(index).equals("") && !lines.get(index).startsWith("#")) {
			// It is a category
			// String pattern = Pattern.quote();
			if (!lines.get(index).contains("*")) {
				try {
					if (lines.get(index).contains("{")) {
						// List of categories to choose one.
						String[] lineColumns = lines.get(index).trim().split("}");
						String[] categoriesList = lineColumns[0].replace("{", "").replace(";", ",")
								.split(",");
						String[] trainingCategories = lineColumns[1].split("\t");
						category = new TrainingCategory(Arrays.asList(categoriesList),
								Integer.parseInt(trainingCategories[1]),
								Integer.parseInt(trainingCategories[2]),
								Integer.parseInt(trainingCategories[3]),
								Integer.parseInt(trainingCategories[4]));
					} else {
						String[] trainingCategories = lines.get(index).split("\t");
						if (CategoryFactory.existCategory(trainingCategories[0])) {
							category = new TrainingCategory(trainingCategories[0],
									Integer.parseInt(trainingCategories[1]),
									Integer.parseInt(trainingCategories[2]),
									Integer.parseInt(trainingCategories[3]),
									Integer.parseInt(trainingCategories[4]));
							categories.add(category);

						} else {
							ShowMessage.showErrorMessage("Categoría no encontrada en \"" + name + "\": "
									+ trainingCategories[0], "Añadir habilidades de adiestramiento.");
						}
					}
				} catch (NumberFormatException nfe) {
					ShowMessage.showErrorMessage("Numero de rangos mal formado en: \"" + lines.get(index)
							+ "\" del adiestramiento: " + name, "Leer adiestramientos");
					break;
				}
			} else { // It is a skill.
				if (category == null) {
					ShowMessage.showErrorMessage("Habilidad sin categoria asociada: " + lines.get(index),
							"Añadir habilidades de adiestramiento.");
					break;
				}
				try {
					if (lines.get(index).contains("{")) {
						// List of skills to choose one.
						String[] lineColumns = lines.get(index).replace("*", "").trim().split("}");
						String[] skillList = lineColumns[0].replace("{", "").replace(";", ",").split(",");
						TrainingSkill skill = new TrainingSkill(Arrays.asList(skillList),
								Integer.parseInt(lineColumns[1].trim()));
						category.addSkill(skill);
					} else {
						// Skill with ranges.
						String[] trainingSkills = lines.get(index).replace("*", "").trim().split("\t");
						List<String> skillList = new ArrayList<>();
						skillList.add(trainingSkills[0]); // List with only one
															// element.
						TrainingSkill skill = new TrainingSkill(skillList,
								Integer.parseInt(trainingSkills[1]));
						category.addSkill(skill);
					}
				} catch (NumberFormatException nfe) {
					nfe.printStackTrace();
					ShowMessage.showErrorMessage("Numero de rangos mal formado en: \"" + lines.get(index)
							+ "\" del adiestramiento: " + name, "Leer adiestramientos");
					break;
				}
			}
			index++;
		}
		return index;
	}

	private int setCharacteristicsUpgrade(List<String> lines, int index) {
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}
		updateCharacteristics = new ArrayList<>();
		while (!lines.get(index).equals("") && !lines.get(index).startsWith("#")) {
			String trainingLine = lines.get(index);
			try {
				if (trainingLine.contains("{")) {
					// List to choose a characteristic.
					List<Characteristic> listToChoose = new ArrayList<>();
					trainingLine = trainingLine.replace("}", "").replace("{", "");
					String[] chars = trainingLine.replace(";", ",").split(",");
					for (String abbrev : chars) {
						listToChoose.add(new Characteristic(abbrev));
					}
					updateCharacteristics.add(listToChoose);
				} else {
					// List of only one characteristic (Player is not allowed to
					// choose).
					String[] chars = trainingLine.replace(";", ",").split(",");
					for (String abbrev : chars) {
						List<Characteristic> listToChoose = new ArrayList<>();
						listToChoose.add(new Characteristic(abbrev));
						updateCharacteristics.add(listToChoose);
					}
				}
			} catch (Exception e) {
				ShowMessage.showErrorMessage("Problema con la linea: \"" + trainingLine
						+ "\" del adiestramiento " + name, "Leer Adiestramiento");
			}
			index++;
		}
		return index;
	}

	private int setProfessionalRequirements(List<String> lines, int index) {
		characteristicRequirements = new ArrayList<>();
		skillRequirements = new ArrayList<>();

		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}

		while (!lines.get(index).equals("") && !lines.get(index).startsWith("#")) {
			if (!lines.get(index).toLowerCase().contains("ningun")) {
				String[] requirementsGroup = lines.get(index).split(", ");
				for (int i = 0; i < requirementsGroup.length; i++) {
					// Religion (10) (-3)
					String pattern = Pattern.quote("(");
					String[] requirements = requirementsGroup[i].split(pattern);
					String requirementName = requirements[0];
					try {
						System.out.println(requirementsGroup[i]);
						Integer value = Integer.parseInt(requirements[1].replace(")", ""));
						Integer costModification = Integer.parseInt(requirements[2].replace(")", ""));
						// If it is a skill, the requirement is to have at least
						// X ranks.
						if (SkillFactory.existSkill(requirementName)) {
							MinimalSkillRequired minSkill = new MinimalSkillRequired(requirementName, value,
									costModification);
							skillRequirements.add(minSkill);
						} else if (Characteristics.isCharacteristicValid(requirementName)) {
							// It it is a characteristic, a minimal temporal
							// value is required.
							MinimalCharacteristicRequired minChar = new MinimalCharacteristicRequired(
									requirementName, value, costModification);
							characteristicRequirements.add(minChar);
						} else {
							ShowMessage.showErrorMessage("Requisito desconocido: \"" + lines.get(index)
									+ "\" del adiestramiento: " + name, "Leer adiestramientos");
						}
					} catch (NumberFormatException nfe) {
						ShowMessage.showErrorMessage("Requisito mal formado: \"" + lines.get(index)
								+ "\" del adiestramiento: " + name, "Leer adiestramientos");
						continue;
					}
				}
			}
			index++;
		}
		return index;
	}

	private int setSpecialSkills(List<String> lines, int index, List<ChooseSkillGroup> skillCategory) {
		skillCategory = new ArrayList<>();
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}

		while (!lines.get(index).equals("") && !lines.get(index).startsWith("#")) {
			String skillLine = lines.get(index);
			if (skillLine.toLowerCase().contains("ningun") || skillLine.toLowerCase().contains("nothing")) {
				break;
			}
			String[] skillColumns = skillLine.split(", ");
			for (int i = 0; i < skillColumns.length; i++) {
				if (!skillColumns[i].contains("{")) {
					Skill skill = SkillFactory.getSkill(skillColumns[i]);
					ChooseSkillGroup chooseSkills = new ChooseSkillGroup(1, skill);
					skillCategory.add(chooseSkills);
				}
			}
			index++;
		}
		return index;
	}
}

class TrainingSpecial {
	private String name;
	private int bonus;
	private int probability;

	public TrainingSpecial(String name, int bonus, int probability) {
		this.name = name;
		this.probability = probability;
		this.bonus = bonus;
	}
}

class TrainingCategory {
	private String name;
	private Integer categoryRanks;
	private Integer minSkills;
	private Integer maxSkills;
	private Integer skillRanks;
	private List<String> categoryOptions; // List to choose from.
	private List<TrainingSkill> skills;

	public TrainingCategory(String name, Integer categoryRanks, Integer minSkills, Integer maxSkills,
			Integer skillRanks) {
		this.name = name;
		this.categoryRanks = categoryRanks;
		this.minSkills = minSkills;
		this.maxSkills = maxSkills;
		skills = new ArrayList<>();
		categoryOptions = new ArrayList<>();
		this.skillRanks = skillRanks;
	}

	public TrainingCategory(List<String> categoryOptions, Integer ranks, Integer minSkills,
			Integer maxSkills, Integer skillRanks) {
		this.name = "";
		this.categoryOptions = categoryOptions;
		this.categoryRanks = ranks;
		this.minSkills = minSkills;
		this.maxSkills = maxSkills;
		this.skillRanks = skillRanks;
	}

	protected void addSkill(TrainingSkill skill) {
		skills.add(skill);
	}
}

class TrainingSkill {
	private List<String> skillOptions; // List to choose from.
	private Integer ranks;

	public TrainingSkill(List<String> skillOptions, Integer ranks) {
		this.skillOptions = skillOptions;
		this.ranks = ranks;
	}
}

class MinimalSkillRequired {
	String skillName;
	Integer minimalRanks;
	Integer costModification;

	public MinimalSkillRequired(String skillName, Integer minimalRanks, Integer costModification) {
		this.skillName = skillName;
		this.minimalRanks = minimalRanks;
		this.costModification = costModification;
	}
}

class MinimalCharacteristicRequired {
	String characteristicAbbreviature;
	Integer minimalValue;
	Integer costModification;

	public MinimalCharacteristicRequired(String characteristicAbbreviature, Integer minimalValue,
			Integer costModification) {
		this.characteristicAbbreviature = characteristicAbbreviature;
		this.minimalValue = minimalValue;
		this.costModification = costModification;
	}
}
