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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.softwaremagico.files.Folder;
import com.softwaremagico.files.RolemasterFolderStructure;
import com.softwaremagico.librodeesher.basics.ChooseType;
import com.softwaremagico.librodeesher.basics.Spanish;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.categories.CategoryFactory;
import com.softwaremagico.librodeesher.pj.characteristic.Characteristics;
import com.softwaremagico.librodeesher.pj.characteristic.CharacteristicsAbbreviature;
import com.softwaremagico.librodeesher.pj.skills.ChooseSkillGroup;
import com.softwaremagico.librodeesher.pj.skills.Skill;
import com.softwaremagico.librodeesher.pj.skills.SkillFactory;

public class Training {
	private String name;
	private Integer trainingTime;
	private List<String> limitedRaces;
	private List<TrainingItem> objects;
	private List<TrainingCategory> categoriesWithRanks;
	// Choose one.
	private List<List<CharacteristicsAbbreviature>> updateCharacteristics;
	private HashMap<CharacteristicsAbbreviature, Integer> characteristicRequirements;
	private HashMap<CharacteristicsAbbreviature, Integer> characteristicRequirementsCostModification;
	private HashMap<String, Integer> skillRequirements;
	private HashMap<String, Integer> skillRequirementsCostModification;
	private List<ChooseSkillGroup> lifeSkills;
	private List<ChooseSkillGroup> commonSkills;
	private List<ChooseSkillGroup> professionalSkills;
	private List<ChooseSkillGroup> restrictedSkills;
	private Map<String, Integer> professionCosts;
	private HashMap<String, TrainingType> professionPreferences;

	public Training(String name) throws InvalidTrainingException {
		this.name = name;
		lifeSkills = new ArrayList<>();
		commonSkills = new ArrayList<>();
		professionalSkills = new ArrayList<>();
		restrictedSkills = new ArrayList<>();
		professionCosts = new HashMap<>();
		professionPreferences = new HashMap<>();
		readTrainingFile(name);
	}

	/**
	 * Returns the index of a TrainingCategory. Used to store into database only
	 * the index and not the POJO.
	 */
	public Integer getTrainingCategoryIndex(TrainingCategory trainingCategory) {
		return categoriesWithRanks.indexOf(trainingCategory);
	}

	private void readTrainingFile(String trainingName) throws InvalidTrainingException {
		int lineIndex = 0;
		String trainingFile = RolemasterFolderStructure.getDirectoryModule(TrainingFactory.TRAINING_FOLDER + File.separator + trainingName + ".txt");
		if (trainingFile.length() > 0) {
			List<String> lines;
			try {
				lines = Folder.readFileLines(trainingFile, false);
			} catch (IOException e) {
				throw new InvalidTrainingException("Invalid training file: " + TrainingFactory.TRAINING_FOLDER + File.separator + trainingName + ".txt");
			}
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
			lineIndex = getProfessionCosts(lines, lineIndex);
		} else {
			throw new InvalidTrainingException("Invalid training file: " + TrainingFactory.TRAINING_FOLDER + File.separator + trainingName + ".txt");
		}
	}

	public int setTrainingTime(List<String> lines, int index) throws InvalidTrainingException {
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}
		try {
			trainingTime = Integer.parseInt(lines.get(index));
		} catch (Exception e) {
			throw new InvalidTrainingException("Error in line '" + lines.get(index) + "' for training '" + getName() + "'.");
		}
		return ++index;
	}

	public int setLimitedRaces(List<String> lines, int index) throws InvalidTrainingException {
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}
		limitedRaces = new ArrayList<>();
		while (lines.get(index).length() != 0 && !lines.get(index).startsWith("#")) {
			String trainingLine = lines.get(index);
			try {
				String[] limitedRacesColumn = trainingLine.split(", ");
				for (int i = 0; i < limitedRacesColumn.length; i++) {
					if (!limitedRacesColumn[i].toLowerCase().contains("ningun")) {
						limitedRaces.add(limitedRacesColumn[i]);
					}
				}
			} catch (ArrayIndexOutOfBoundsException aiofb) {
				throw new InvalidTrainingException("Error in line '" + trainingLine + "' for training '" + getName() + "'.");
			}
			index++;
		}
		return index;
	}

	public int setTrainingSpecial(List<String> lines, int index) throws InvalidTrainingException {
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}
		int bonus;
		String skill = "";
		int probability = 0;
		objects = new ArrayList<>();
		while (lines.get(index).length() != 0) {
			String trainingLine = lines.get(index);
			try {
				String[] specialColumns = trainingLine.split("\t");
				String special = specialColumns[0];
				try {
					probability = Integer.parseInt(specialColumns[1]);
					if (specialColumns.length > 2) {
						bonus = Integer.parseInt(specialColumns[2]);
						if (specialColumns.length > 3) {
							skill = specialColumns[3];
						}
					} else {
						bonus = 0;
						skill = "";
					}
				} catch (NumberFormatException nfe) {
					throw new InvalidTrainingException("Incorrect number '" + special + "' in line '" + trainingLine + "'  for training '" + getName() + "'.");
				}
				objects.add(new TrainingItem(special, bonus, skill, probability));
			} catch (ArrayIndexOutOfBoundsException aiofb) {
				throw new InvalidTrainingException("Error in line '" + trainingLine + "' for training '" + getName() + "'.");
			}
			index++;
		}
		return index;
	}

	public int setTrainingSkills(List<String> lines, int index) throws InvalidTrainingException {
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}
		categoriesWithRanks = new ArrayList<>();
		TrainingCategory trainingCategory = null;
		while (lines.get(index).length() != 0 && !lines.get(index).startsWith("#")) {
			// It is a category
			if (!lines.get(index).contains("*")) {
				try {
					if (lines.get(index).contains("{")) {
						// List of categories to choose one.
						String[] lineColumns = lines.get(index).trim().split("}");
						String[] categoriesList = lineColumns[0].replace("{", "").replace(";", ",").split(",");
						String[] categoryRanks = lineColumns[1].split("\t");

						List<String> categoriesOptions = new ArrayList<>();
						for (String category : categoriesList) {
							categoriesOptions.add(category.trim());
						}

						trainingCategory = new TrainingCategory(categoriesOptions, Integer.parseInt(categoryRanks[1]), Integer.parseInt(categoryRanks[2]),
								Integer.parseInt(categoryRanks[3]), Integer.parseInt(categoryRanks[4]));
						categoriesWithRanks.add(trainingCategory);
					} else {
						String[] categoryRanks = lines.get(index).split("\t");
						if (CategoryFactory.existCategory(categoryRanks[0])) {
							List<String> categoriesList = new ArrayList<>();
							categoriesList.add(categoryRanks[0].trim());
							trainingCategory = new TrainingCategory(categoriesList, Integer.parseInt(categoryRanks[1]), Integer.parseInt(categoryRanks[2]),
									Integer.parseInt(categoryRanks[3]), Integer.parseInt(categoryRanks[4]));
							categoriesWithRanks.add(trainingCategory);

						} else {
							throw new InvalidTrainingException("Category not found '" + categoryRanks[0] + "' for training '" + getName() + "'.");
						}
					}
				} catch (NumberFormatException nfe) {
					throw new InvalidTrainingException("Incorrect ranks number '" + lines.get(index) + "' for training '" + getName() + "'.");
				}
			} else { // It is a skill. Must come from a defined category and not
						// a list to choose.
				if (trainingCategory == null) {
					throw new InvalidTrainingException("Skill without category '" + lines.get(index) + "'.");
				}
				try {
					if (lines.get(index).contains("{")) {
						// List of skills to choose one.
						String[] lineColumns = lines.get(index).replace("*", "").trim().split("}");
						String[] skillList = lineColumns[0].replace("{", "").replace(";", ",").split(",");
						TrainingSkill skill = new TrainingSkill(Arrays.asList(skillList), Integer.parseInt(lineColumns[1].replace("-", "").replace("\t", "")
								.trim()));
						trainingCategory.addSkill(skill);
					} else {
						// Skill with ranges.
						String[] trainingSkills = lines.get(index).replace("*", "").trim().split("\t");
						addTrainingSkill(trainingCategory, trainingSkills[0], Integer.parseInt(trainingSkills[1]));
					}
				} catch (NumberFormatException nfe) {
					throw new InvalidTrainingException("Incorrect ranks number '" + lines.get(index) + "' for training '" + getName() + "'.");
				}
			}
			index++;
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

	private int setCharacteristicsUpgrade(List<String> lines, int index) throws InvalidTrainingException {
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}
		updateCharacteristics = new ArrayList<>();
		while (lines.get(index).length() != 0 && !lines.get(index).startsWith("#")) {
			String trainingLine = lines.get(index);
			try {
				if (trainingLine.contains("{")) {
					// List to choose a characteristic.
					List<CharacteristicsAbbreviature> listToChoose = new ArrayList<>();
					trainingLine = trainingLine.replace("}", "").replace("{", "");
					String[] chars = trainingLine.replace(";", ",").split(",");
					for (String abbrev : chars) {
						listToChoose.add(CharacteristicsAbbreviature.getCharacteristicsAbbreviature(abbrev.trim()));
					}
					updateCharacteristics.add(listToChoose);
				} else {
					// Ignore nothing tag.
					if (!trainingLine.toLowerCase().contains(Spanish.NOTHING_TAG)) {
						// List of only one characteristic (Player is not
						// allowed to
						// choose).
						String[] chars = trainingLine.replace(";", ",").split(",");
						for (String abbrev : chars) {
							List<CharacteristicsAbbreviature> listToChoose = new ArrayList<>();
							listToChoose.add(CharacteristicsAbbreviature.getCharacteristicsAbbreviature(abbrev.trim()));
							updateCharacteristics.add(listToChoose);
						}
					}
				}
			} catch (Exception e) {
				throw new InvalidTrainingException("Incorrect ranks number '" + lines.get(index) + "' for training '" + getName() + "'.");
			}
			index++;
		}
		// Sort updates. First list with one elements.
		Collections.sort(updateCharacteristics, new Comparator<List<CharacteristicsAbbreviature>>() {
			public int compare(List<CharacteristicsAbbreviature> a1, List<CharacteristicsAbbreviature> a2) {
				return a1.size() - a2.size(); // assumes you want biggest to
												// smallest
			}
		});
		return index;
	}

	private int setProfessionalRequirements(List<String> lines, int index) throws InvalidTrainingException {
		characteristicRequirements = new HashMap<>();
		characteristicRequirementsCostModification = new HashMap<>();
		skillRequirements = new HashMap<>();
		skillRequirementsCostModification = new HashMap<>();

		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}

		while (lines.get(index).length() != 0 && !lines.get(index).startsWith("#")) {
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
							characteristicRequirements.put(CharacteristicsAbbreviature.getCharacteristicsAbbreviature(requirementName), value);
							characteristicRequirementsCostModification.put(CharacteristicsAbbreviature.getCharacteristicsAbbreviature(requirementName),
									costModification);
						} else {
							throw new InvalidTrainingException("Unknown requirement '" + lines.get(index) + "' for training '" + getName() + "'.");
						}
					} catch (NumberFormatException nfe) {
						throw new InvalidTrainingException("Malformed requirement '" + lines.get(index) + "' for training '" + getName() + "'.");
					}
				}
			}
			index++;
		}
		return index;
	}

	private int setSpecialSkills(List<String> lines, int index, List<ChooseSkillGroup> skillCategory, ChooseType chooseType) {
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}

		while (lines.get(index).length() != 0 && !lines.get(index).startsWith("#")) {
			String skillLine = lines.get(index);
			if (skillLine.toLowerCase().contains("ningun") || skillLine.toLowerCase().contains("nothing")) {
				index++;
				break;
			}
			String[] skillColumns = skillLine.split(", ");
			for (int i = 0; i < skillColumns.length; i++) {
				if (!skillColumns[i].contains("{")) {
					Skill skill = SkillFactory.getSkill(skillColumns[i]);
					ChooseSkillGroup chooseSkills = new ChooseSkillGroup(1, skill, chooseType);
					skillCategory.add(chooseSkills);
				} else {
					String[] skills = skillColumns[i].replace("{", "").replace("}", "").split(";");
					List<String> skillList = new ArrayList<String>(Arrays.asList(skills));
					ChooseSkillGroup chooseSkills = new ChooseSkillGroup(1, SkillFactory.getSkills(skillList), chooseType);
					skillCategory.add(chooseSkills);
				}
			}
			index++;
		}
		return index;
	}

	private int getProfessionCosts(List<String> lines, int index) throws InvalidTrainingException {
		characteristicRequirements = new HashMap<>();
		characteristicRequirementsCostModification = new HashMap<>();
		skillRequirements = new HashMap<>();
		skillRequirementsCostModification = new HashMap<>();

		while (index < lines.size() && (lines.get(index).equals("") || lines.get(index).startsWith("#"))) {
			index++;
		}

		while (index < lines.size() && (lines.get(index).length() != 0 && !lines.get(index).startsWith("#"))) {
			if (!lines.get(index).toLowerCase().contains("ningun")) {
				String professionLine = lines.get(index);
				String[] professionColumns = professionLine.split("\t");

				try {
					TrainingType type;
					if (professionColumns[0].contains("+") || professionColumns[1].contains("+")) {
						type = TrainingType.FAVOURITE;
					} else if (professionColumns[0].contains("-") || professionColumns[1].contains("-")) {
						type = TrainingType.FORBIDDEN;
					} else {
						type = TrainingType.STANDAR;
					}

					String professionName = professionColumns[0].replace("+", "").replace("-", "");
					Integer cost = Integer.parseInt(professionColumns[1].replace("+", "").replace("-", "").trim());
					professionCosts.put(professionName, cost);
					professionPreferences.put(professionName, type);
				} catch (Exception e) {
					throw new InvalidTrainingException("Invalid training cost for '" + getName() + "' in '" + lines.get(index) + "'.", e);
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
		return new ArrayList<>(getSkillRequirements().keySet());
	}

	public HashMap<String, Integer> getSkillRequirements() {
		return skillRequirements;
	}

	public HashMap<String, Integer> getSkillRequirementsCostModification() {
		return skillRequirementsCostModification;
	}

	public HashMap<CharacteristicsAbbreviature, Integer> getCharacteristicRequirements() {
		return characteristicRequirements;
	}

	public HashMap<CharacteristicsAbbreviature, Integer> getCharacteristicRequirementsCostModification() {
		return characteristicRequirementsCostModification;
	}

	public List<TrainingCategory> getCategoriesWithRanks() {
		return categoriesWithRanks;
	}

	public String getName() {
		return name;
	}

	public List<List<CharacteristicsAbbreviature>> getUpdateCharacteristics() {
		return updateCharacteristics;
	}

	public List<TrainingItem> getObjects() {
		// Create new TrainingObjects to avoid two characters to have the same
		// object.
		List<TrainingItem> trainingObjects = new ArrayList<>();
		for (TrainingItem object : objects) {
			trainingObjects.add(new TrainingItem(object));
		}
		return trainingObjects;
	}

	public Integer getTrainingTime() {
		return trainingTime;
	}

	public List<ChooseSkillGroup> getCommonSkills() {
		if (commonSkills == null) {
			return new ArrayList<>();
		}
		return commonSkills;
	}

	public List<ChooseSkillGroup> getProfessionalSkills() {
		List<ChooseSkillGroup> skills = new ArrayList<>();
		if (professionalSkills != null) {
			skills.addAll(professionalSkills);
		}
		if (lifeSkills != null) {
			skills.addAll(lifeSkills);
		}
		return skills;
	}

	public List<ChooseSkillGroup> getRestrictedSkills() {
		if (restrictedSkills == null) {
			return new ArrayList<>();
		}
		return restrictedSkills;
	}

	public Integer getTrainingCost(String profession) {
		if (professionCosts.get(profession) != null) {
			return professionCosts.get(profession);
		}
		return CharacterPlayer.INVALID_COST;
	}

	public HashMap<String, TrainingType> getProfessionPreferences() {
		return professionPreferences;
	}
}
