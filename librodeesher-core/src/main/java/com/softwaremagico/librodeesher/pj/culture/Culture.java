package com.softwaremagico.librodeesher.pj.culture;

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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.softwaremagico.files.Folder;
import com.softwaremagico.files.RolemasterFolderStructure;
import com.softwaremagico.librodeesher.basics.Spanish;
import com.softwaremagico.librodeesher.pj.categories.Category;
import com.softwaremagico.librodeesher.pj.categories.CategoryFactory;
import com.softwaremagico.librodeesher.pj.skills.Skill;
import com.softwaremagico.librodeesher.pj.skills.SkillFactory;
import com.softwaremagico.librodeesher.pj.training.Training;
import com.softwaremagico.librodeesher.pj.training.TrainingFactory;
import com.softwaremagico.librodeesher.pj.weapons.InvalidWeaponException;
import com.softwaremagico.librodeesher.pj.weapons.Weapon;
import com.softwaremagico.librodeesher.pj.weapons.WeaponFactory;
import com.softwaremagico.log.EsherLog;

public class Culture {
	private String name;
	private List<Weapon> cultureWeapons;
	private List<String> cultureArmours;
	private HashMap<String, CultureCategory> categories;
	private Integer hobbyRanks;
	private List<String> hobbySkills;
	private HashMap<String, Integer> languagesMaxRanks;
	private HashMap<String, Float> trainingPrice;

	public Culture(String name) throws InvalidCultureException {
		this.name = name;
		readCultureFile(name);
	}

	public List<String> getLanguagesMaxRanks() {
		List<String> sortedLanguages = new ArrayList<>(languagesMaxRanks.keySet());
		Collections.sort(sortedLanguages);
		return sortedLanguages;
	}

	public List<String> getHobbySkills() {
		return hobbySkills;
	}

	public List<Weapon> getCultureWeapons() {
		return cultureWeapons;
	}

	public Integer getCultureRanks(Category category) {
		if (category != null) {
			for (String categoryName : categories.keySet()) {
				if (categoryName.equals(category.getName())) {
					return categories.get(categoryName).getRanks();
				}
			}
		}
		return 0;
	}

	public Integer getCultureRanks(Skill skill) {
		Integer total = 0;
		if (skill != null) {
			// Culture ranks
			for (CultureCategory cultureCategory : categories.values()) {
				for (CultureSkill cultureSkill : cultureCategory.getSkills()) {
					if (cultureSkill.getName().equals(skill.getName())) {
						total += cultureSkill.getRanks();
					}
				}
			}
		}
		return total;
	}

	private void readCultureFile(String cultureName) throws InvalidCultureException {
		int lineIndex = 0;
		String cultureFile = RolemasterFolderStructure.getDirectoryModule(CultureFactory.CULTURE_FOLDER
				+ File.separator + cultureName + ".txt");
		if (cultureFile.length() > 0) {
			List<String> lines;
			try {
				lines = Folder.readFileLines(cultureFile, false);
			} catch (IOException e) {
				throw new InvalidCultureException("Invalid culture file '" + CultureFactory.CULTURE_FOLDER
						+ File.separator + cultureName + ".txt'", e);
			}
			lineIndex = setCultureWeapons(lines, lineIndex);
			lineIndex = setCultureArmour(lines, lineIndex);
			lineIndex = setSkillRanks(lines, lineIndex);
			lineIndex = setHobbyRanks(lines, lineIndex);
			lineIndex = setHobbySkillsAndCategories(lines, lineIndex);
			lineIndex = setCultureMaxLanguages(lines, lineIndex);
			lineIndex = setTrainingDiscount(lines, lineIndex);
		} else {
			throw new InvalidCultureException("Invalid culture file '" + CultureFactory.CULTURE_FOLDER
					+ File.separator + cultureName + ".txt'");
		}
	}

	private int setCultureWeapons(List<String> lines, int index) throws InvalidCultureException {
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}
		try {
			cultureWeapons = new ArrayList<>();
			if (!lines.get(index).toLowerCase().equals(Spanish.ALL_TAG)) {
				while (!lines.get(index).equals("") && !lines.get(index).startsWith("#")) {
					String lineaArmasCultura = lines.get(index);
					String[] weapons = lineaArmasCultura.split(", ");
					for (String weaponName : weapons) {
						Weapon weapon;
						try {
							weapon = WeaponFactory.getWeapon(weaponName);
							if (weapon != null) {
								cultureWeapons.add(weapon);
							}
						} catch (InvalidWeaponException e) {
							EsherLog.warning(WeaponFactory.class.getName(), "Weapon '" + name
									+ "' abbreviature not found!");
						}
					}
					index++;
				}
			} else {
				cultureWeapons = WeaponFactory.getAllStandardWeapons();
				index++;
			}
		} catch (IndexOutOfBoundsException iob) {
			throw new InvalidCultureException("Error in line: " + lines.get(index), iob);
		}
		return index;
	}

	private int setCultureArmour(List<String> lines, int index) throws InvalidCultureException {
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}

		cultureArmours = new ArrayList<>();
		try {
			while (!lines.get(index).equals("") && !lines.get(index).startsWith("#")) {
				String lineaArmadurasCultura = lines.get(index);
				String[] arrayArmaduras = lineaArmadurasCultura.split(", ");
				cultureArmours.addAll(Arrays.asList(arrayArmaduras));
				index++;
			}
		} catch (IndexOutOfBoundsException iob) {
			throw new InvalidCultureException("Error in line: " + lines.get(index), iob);
		}
		return index;
	}

	private int setSkillRanks(List<String> lines, int index) throws InvalidCultureException {
		categories = new HashMap<>();
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}
		try {
			CultureCategory category = null;
			while (!lines.get(index).equals("") && !lines.get(index).startsWith("#")) {
				String cultureLine = lines.get(index);
				if (!cultureLine.startsWith("  *  ")) {
					String[] categoryRow = cultureLine.split("\t");
					String categoryName = categoryRow[0].trim();
					category = new CultureCategory(categoryName, categoryRow[1]);
					categories.put(categoryName, category);
				} else {
					category.addSkillFromLine(cultureLine);
				}
				index++;
			}
		} catch (IndexOutOfBoundsException iob) {
			throw new InvalidCultureException("Error in line: " + lines.get(index), iob);
		}
		return index;
	}

	private int setHobbyRanks(List<String> lines, int index) throws InvalidCultureException {
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}
		while (!lines.get(index).equals("") && !lines.get(index).startsWith("#")) {
			String hobbyLine = lines.get(index);
			try {
				hobbyRanks = Integer.parseInt(hobbyLine);
			} catch (NumberFormatException nfe) {
				throw new InvalidCultureException("Error obtaining hobby ranks '" + hobbyLine + "' for culture '" + getName() + "'. ", nfe);
			}
			index++;
		}
		return index;
	}

	private int setHobbySkillsAndCategories(List<String> lines, int index) throws InvalidCultureException {
		hobbySkills = new ArrayList<>();
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}
		Set<String> exceptions = new HashSet<>();
		while (!lines.get(index).equals("") && !lines.get(index).startsWith("#")) {
			String[] hobbySkillColumns = lines.get(index).split(", ");
			for (String hobby : hobbySkillColumns) {
				// If it is a category.
				Category category;
				if ((category = CategoryFactory.getCategory(hobby)) != null) {
					for (Skill skill : category.getNonRareSkills()) {
						// CultureSkill cultureSkill = new
						// CultureSkill(skill.getName());
						hobbySkills.add(skill.getName());
					}
					// Is a skill.
				} else if (hobby.startsWith("-")) {
					hobby = hobby.substring(1);
					if (SkillFactory.existSkill(hobby)) {
						exceptions.add(hobby);
					} else {
						throw new InvalidCultureException("Hobby not found in culture '" + getName()
								+ "' with name '" + hobby + "'.");
					}

				} else if (SkillFactory.existSkill(hobby)) {
					hobbySkills.add(hobby);
					// It is a special tag for a group of skills. Add it.
				} else if (hobby.toLowerCase().equals(Spanish.WEAPON)
						|| hobby.toLowerCase().equals(Spanish.ARMOUR)
						|| hobby.toLowerCase().equals(Spanish.CULTURE_SPELLS)) {
					hobbySkills.add(hobby);
					// Is a culture skill: add it;
				} else if (hobby.contains(Spanish.FAUNA_KNOWNLEDGE_TAG)
						|| hobby.contains(Spanish.FLORA_KNOWNLEDGE_TAG)
						|| hobby.contains(Spanish.CULTURAL_KNOWNLEDGE_TAG)
						|| hobby.contains(Spanish.REGIONAL_KNOWNLEDGE_TAG)) {
					Category cat = CategoryFactory.getCategory(Spanish.GENERAL_KNOWLEDGE_TAG);
					cat.addSkill(hobby);
					// CultureSkill skill = new CultureSkill(hobby);
					hobbySkills.add(hobby);
				} else if (hobby.contains(Spanish.SURVIVAL_TAG)) {
					Category cat = CategoryFactory.getCategory(Spanish.OUTDOORS_ENVIRONMENT_TAG);
					cat.addSkill(hobby);
					// CultureSkill skill = new CultureSkill(hobby);
					hobbySkills.add(hobby);
				} else if (hobby.toLowerCase().equals(Spanish.CULTURE_LANGUAGE_TAG.toLowerCase())) {
					// TODO select a language
				} else { // Not recognized.
					throw new InvalidCultureException("Hobby '" + hobby + "' not found in culture '" + getName() + "' line '"
							+ lines.get(index) + "'.");
				}
			}
			index++;
		}
		hobbySkills.removeAll(exceptions);
		Collections.sort(hobbySkills);
		return index;
	}

	private int setCultureMaxLanguages(List<String> lines, int index) throws InvalidCultureException {
		languagesMaxRanks = new HashMap<>();
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}
		while (!lines.get(index).equals("") && !lines.get(index).startsWith("#")) {
			String[] languageColumn = lines.get(index).split("\t");
			String[] languageRanks = languageColumn[1].split("/");
			try {
				String language = Spanish.SPOKEN_TAG + " " + languageColumn[0];

				// Add language to category.
				if (CategoryFactory.getCategory(Spanish.COMUNICATION_CATEGORY).getSkill(language) == null) {
					CategoryFactory.getCategory(Spanish.COMUNICATION_CATEGORY).addSkill(language);
				}

				languagesMaxRanks.put(language, Integer.parseInt(languageRanks[0]));
				language = Spanish.WRITTEN_TAG + " " + languageColumn[0];

				// Add language to category.
				if (CategoryFactory.getCategory(Spanish.COMUNICATION_CATEGORY).getSkill(language) == null) {
					CategoryFactory.getCategory(Spanish.COMUNICATION_CATEGORY).addSkill(language);
				}

				languagesMaxRanks.put(language, Integer.parseInt(languageRanks[1]));
			} catch (NumberFormatException nfe) {
				throw new InvalidCultureException("Error obtaining ranks for language '" + lines.get(index)
						+ "'.", nfe);
			}
			index++;
		}
		return index;
	}

	private int setTrainingDiscount(List<String> lines, int index) throws InvalidCultureException {
		trainingPrice = new HashMap<>();
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}
		while (!lines.get(index).equals("") && !lines.get(index).startsWith("#")) {
			String[] trainingColumn = lines.get(index).split("\t");
			if (trainingColumn[0].toLowerCase().startsWith(Spanish.NOTHING_TAG)) {
				break;
			}
			Training training = TrainingFactory.getTraining(trainingColumn[0]);
			if (training != null) {
				try {
					Float value = Float.parseFloat(trainingColumn[1].replace("%", "").replace(".", "")
							.replace(",", "").trim());
					trainingPrice.put(training.getName(), value / 100);
				} catch (NumberFormatException nfe) {
					throw new InvalidCultureException("Invalid training value in '" + lines.get(index)
							+ "' for culture '" + getName() + "'.", nfe);
				}
			} else {
<<<<<<< HEAD
				EsherLog.warning(this.getClass().getName(), "Invalid training '" + trainingColumn[0] + "' for culture '" + getName() + "'.");
				// throw new InvalidCultureException("Invalid training '" +
				// trainingColumn[0] + "' for culture '" + getName() + "'.");
=======
				throw new InvalidCultureException("Invalid training '" + trainingColumn[0]
						+ "' for culture '" + getName() + "'.");
>>>>>>> 325ce4d09e08bfe5028c0acdcee05aa6ad048f07
			}
			index++;
		}
		return index;
	}

	public String getName() {
		return name;
	}

	public Integer getHobbyRanks() {
		return hobbyRanks;
	}

	public Integer getSpellRanks() {
		return categories.get("Listas Abiertas de Hechizos").getChooseRanks();
	}

	public Integer getLanguageRanksToChoose() {
		return categories.get("Comunicación").getChooseRanks();
	}

	public Integer getLanguageMaxRanks(String language) {
		Integer ranks = languagesMaxRanks.get(language);
		if (ranks == null) {
			return 0;
		}
		return ranks;
	}

	public List<String> getCultureArmours() {
		return cultureArmours;
	}

	public float getTrainingPricePercentage(String trainingName) {
		if (trainingPrice.get(trainingName) != null) {
			return trainingPrice.get(trainingName);
		}
		return 1f;
	}
}
