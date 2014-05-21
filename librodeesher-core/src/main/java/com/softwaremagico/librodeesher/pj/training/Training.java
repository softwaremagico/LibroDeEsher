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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import com.softwaremagico.files.Folder;
import com.softwaremagico.files.RolemasterFolderStructure;
import com.softwaremagico.librodeesher.basics.ChooseType;
import com.softwaremagico.librodeesher.basics.ShowMessage;
import com.softwaremagico.librodeesher.pj.categories.CategoryFactory;
import com.softwaremagico.librodeesher.pj.characteristic.Characteristics;
import com.softwaremagico.librodeesher.pj.skills.ChooseSkillGroup;
import com.softwaremagico.librodeesher.pj.skills.Skill;
import com.softwaremagico.librodeesher.pj.skills.SkillFactory;

public class Training {
	private String name;
	private Integer trainingTime;
	private List<String> limitedRaces;
	private List<TrainingSpecial> specials;
	private List<TrainingCategory> categoriesWithRanks;
	private List<List<String>> updateCharacteristics; // Choose one
	private HashMap<String, Integer> characteristicRequirements;
	private HashMap<String, Integer> characteristicRequirementsCostModification;
	private HashMap<String, Integer> skillRequirements;
	private HashMap<String, Integer> skillRequirementsCostModification;
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
			lineIndex = setSpecialSkills(lines, lineIndex, lifeSkills, ChooseType.LIFE);
			lineIndex = setSpecialSkills(lines, lineIndex, commonSkills, ChooseType.COMMON);
			lineIndex = setSpecialSkills(lines, lineIndex, professionalSkills, ChooseType.PROFESSIONAL);
			lineIndex = setSpecialSkills(lines, lineIndex, restrictedSkills, ChooseType.RESTRICTED);
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
		categoriesWithRanks = new ArrayList<>();
		TrainingCategory trainingCategory = null;
		while (!lines.get(index).equals("") && !lines.get(index).startsWith("#")) {
			// It is a category
			if (!lines.get(index).contains("*")) {
				// complete previous category skills
				if (trainingCategory != null) {
					trainingCategory.addGeneralSkills();
				}

				try {
					if (lines.get(index).contains("{")) {
						// List of categories to choose one.
						String[] lineColumns = lines.get(index).trim().split("}");
						String[] categoriesList = lineColumns[0].replace("{", "").replace(";", ",")
								.split(",");
						String[] categoryRanks = lineColumns[1].split("\t");

						List<String> categoriesOptions = new ArrayList<>();
						for (String category : categoriesList) {
							categoriesOptions.add(category.trim());
						}

						trainingCategory = new TrainingCategory(categoriesOptions,
								Integer.parseInt(categoryRanks[1]), Integer.parseInt(categoryRanks[2]),
								Integer.parseInt(categoryRanks[3]), Integer.parseInt(categoryRanks[4]));
						categoriesWithRanks.add(trainingCategory);
					} else {
						String[] categoryRanks = lines.get(index).split("\t");
						if (CategoryFactory.existCategory(categoryRanks[0])) {
							List<String> categoriesList = new ArrayList<>();
							categoriesList.add(categoryRanks[0].trim());
							trainingCategory = new TrainingCategory(categoriesList,
									Integer.parseInt(categoryRanks[1]), Integer.parseInt(categoryRanks[2]),
									Integer.parseInt(categoryRanks[3]), Integer.parseInt(categoryRanks[4]));
							categoriesWithRanks.add(trainingCategory);

						} else {
							ShowMessage.showErrorMessage("Categoría no encontrada en \"" + name + "\": "
									+ categoryRanks[0], "Añadir habilidades de adiestramiento.");
						}
					}
				} catch (NumberFormatException nfe) {
					ShowMessage.showErrorMessage("Numero de rangos mal formado en: \"" + lines.get(index)
							+ "\" del adiestramiento: " + name, "Leer adiestramientos");
					break;
				}
			} else { // It is a skill. Must come from a defined category and not
						// a list to choose.
				if (trainingCategory == null) {
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
						trainingCategory.addSkill(skill);
					} else {
						// Skill with ranges.
						String[] trainingSkills = lines.get(index).replace("*", "").trim().split("\t");
						addTrainingSkill(trainingCategory, trainingSkills[0],
								Integer.parseInt(trainingSkills[1]));
					}
				} catch (NumberFormatException nfe) {
					ShowMessage.showErrorMessage("Numero de rangos mal formado en: \"" + lines.get(index)
							+ "\" del adiestramiento: " + name, "Leer adiestramientos");
					break;
				}
			}
			index++;
		}
		// Last category complete previous category skills
		if (trainingCategory != null) {
			trainingCategory.addGeneralSkills();
		}
		return index;
	}

	private void addTrainingSkill(TrainingCategory trainingCategory, String skillName, Integer ranks) {
		List<String> skillList = new ArrayList<>();
		skillList.add(skillName); // List with only one
									// element.
		TrainingSkill skill = new TrainingSkill(skillList, ranks);
		trainingCategory.addSkill(skill);
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
					List<String> listToChoose = new ArrayList<>();
					trainingLine = trainingLine.replace("}", "").replace("{", "");
					String[] chars = trainingLine.replace(";", ",").split(",");
					for (String abbrev : chars) {
						listToChoose.add(abbrev);
					}
					updateCharacteristics.add(listToChoose);
				} else {
					// List of only one characteristic (Player is not allowed to
					// choose).
					String[] chars = trainingLine.replace(";", ",").split(",");
					for (String abbrev : chars) {
						List<String> listToChoose = new ArrayList<>();
						listToChoose.add(abbrev);
						updateCharacteristics.add(listToChoose);
					}
				}
			} catch (Exception e) {
				ShowMessage.showErrorMessage("Problema con la linea: \"" + trainingLine
						+ "\" del adiestramiento " + name, "Leer Adiestramiento");
			}
			index++;
		}
		// Sort updates. First list with one elements.
		Collections.sort(updateCharacteristics, new Comparator<List<String>>() {
			public int compare(List<String> a1, List<String> a2) {
				return a1.size() - a2.size(); // assumes you want biggest to
												// smallest
			}
		});
		return index;
	}

	private int setProfessionalRequirements(List<String> lines, int index) {
		characteristicRequirements = new HashMap<>();
		characteristicRequirementsCostModification = new HashMap<>();
		skillRequirements = new HashMap<>();
		skillRequirementsCostModification = new HashMap<>();

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
						Integer value = Integer.parseInt(requirements[1].replace(")", ""));
						Integer costModification = Integer.parseInt(requirements[2].replace(")", ""));
						// If it is a skill, the requirement is to have at least
						// X ranks.
						if (SkillFactory.existSkill(requirementName)) {
							skillRequirements.put(requirementName, value);
							skillRequirementsCostModification.put(requirementName, costModification);
						} else if (Characteristics.isCharacteristicValid(requirementName)) {
							// It it is a characteristic, a minimal temporal
							// value is required.
							characteristicRequirements.put(requirementName, value);
							characteristicRequirementsCostModification.put(requirementName, costModification);
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

	private int setSpecialSkills(List<String> lines, int index, List<ChooseSkillGroup> skillCategory,
			ChooseType chooseType) {
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
					ChooseSkillGroup chooseSkills = new ChooseSkillGroup(1, skill, chooseType);
					skillCategory.add(chooseSkills);
				}
			}
			index++;
		}
		return index;
	}

	public List<String> getLimitedRaces() {
		return limitedRaces;
	}

	public List<String> getSkillRequirementsList() {
		return new ArrayList<>(skillRequirements.keySet());
	}

	public HashMap<String, Integer> getSkillRequirements() {
		return skillRequirements;
	}

	public HashMap<String, Integer> getSkillRequirementsCostModification() {
		return skillRequirementsCostModification;
	}

	public HashMap<String, Integer> getCharacteristicRequirements() {
		return characteristicRequirements;
	}

	public HashMap<String, Integer> getCharacteristicRequirementsCostModification() {
		return characteristicRequirementsCostModification;
	}

	public List<TrainingCategory> getCategoriesWithRanks() {
		return categoriesWithRanks;
	}

	public String getName() {
		return name;
	}

	public List<List<String>> getUpdateCharacteristics() {
		return updateCharacteristics;
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
