package com.softwaremagico.librodeesher.pj.race;

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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.softwaremagico.files.Folder;
import com.softwaremagico.files.RolemasterFolderStructure;
import com.softwaremagico.librodeesher.basics.Spanish;
import com.softwaremagico.librodeesher.pj.ProgressionCostType;
import com.softwaremagico.librodeesher.pj.SexType;
import com.softwaremagico.librodeesher.pj.categories.Category;
import com.softwaremagico.librodeesher.pj.categories.CategoryFactory;
import com.softwaremagico.librodeesher.pj.characteristic.CharacteristicsAbbreviature;
import com.softwaremagico.librodeesher.pj.culture.CultureFactory;
import com.softwaremagico.librodeesher.pj.culture.InvalidCultureException;
import com.softwaremagico.librodeesher.pj.magic.MagicFactory;
import com.softwaremagico.librodeesher.pj.magic.RealmOfMagic;
import com.softwaremagico.librodeesher.pj.perk.Perk;
import com.softwaremagico.librodeesher.pj.perk.PerkFactory;
import com.softwaremagico.librodeesher.pj.perk.PerkPointsCalculator;
import com.softwaremagico.librodeesher.pj.profession.ProfessionFactory;
import com.softwaremagico.librodeesher.pj.race.exceptions.InvalidRaceDefinition;
import com.softwaremagico.librodeesher.pj.race.exceptions.InvalidRaceException;
import com.softwaremagico.librodeesher.pj.resistance.ResistanceType;
import com.softwaremagico.librodeesher.pj.skills.Skill;
import com.softwaremagico.librodeesher.pj.skills.SkillFactory;

public class Race {
	private String name;
	private int apperanceBonus;
	private Map<CharacteristicsAbbreviature, Integer> characteristicBonus;
	private Map<ResistanceType, Integer> resistancesBonus;
	private Map<ProgressionCostType, List<Float>> progressionRankValues;
	private List<String> restrictedProfessions;
	private Integer soulDepartTime;
	private Integer raceType;
	private RaceSize size;
	private Float restorationTime;
	private Integer languagePoints;
	private Integer historialPoints;
	private Map<String, Integer> initialRaceLanguages;
	private Map<String, Integer> maxRaceLanguages;
	private Map<String, Integer> maxHistoryLanguages;
	private List<Skill> commonSkills;
	private List<Category> commonCategories;
	private List<Skill> restrictedSkills;
	private List<Category> restrictedCategories;
	private List<String> availableCultures;
	private List<String> specials;
	private Map<String, Integer> specialsRacePoints;
	private Integer perksPoints;
	private List<String> maleNames;
	private List<String> femaleNames;
	private List<String> familyNames;
	private String raceLanguage = null;
	private Integer expectedLifeYears = null;
	private int naturalArmourType = 1;
	private Map<String, Integer> bonusSkills;
	private Set<Perk> racePerks;

	public Race(String name) throws InvalidRaceException {
		this.name = name;
		apperanceBonus = 0;
		commonSkills = new ArrayList<>();
		commonCategories = new ArrayList<>();
		restrictedSkills = new ArrayList<>();
		restrictedCategories = new ArrayList<>();
		specialsRacePoints = new HashMap<>();
		initialRaceLanguages = new HashMap<>();
		maxRaceLanguages = new HashMap<>();
		maxHistoryLanguages = new HashMap<>();
		bonusSkills = new HashMap<>();
		racePerks = new HashSet<>();
		readRaceFile(name);
	}

	public String getName() {
		return name;
	}

	public Integer getLanguagePoints() {
		return languagePoints;
	}

	private void readRaceFile(String raceName) throws InvalidRaceException {
		int lineIndex = 0;

		String raceFile = RolemasterFolderStructure.getDirectoryModule(RaceFactory.RACE_FOLDER + File.separator + raceName + ".txt");
		if (raceFile.length() > 0) {
			List<String> lines;
			try {
				lines = Folder.readFileLines(raceFile, false);
			} catch (IOException e) {
				throw new InvalidRaceException("Invalid race file: " + RaceFactory.RACE_FOLDER + File.separator + raceName + ".txt");
			}

			lineIndex = setCharacteristicsBonus(lines, lineIndex);
			lineIndex = setLifeExpectation(lines, lineIndex);
			lineIndex = setResistanceBonus(lines, lineIndex);
			lineIndex = setProgressionRankValues(lines, lineIndex);
			lineIndex = setRestrictedProfessions(lines, lineIndex);
			lineIndex = setOtherRaceInformation(lines, lineIndex);
			lineIndex = setRaceLanguages(lines, lineIndex);
			lineIndex = setHistoryLanguages(lines, lineIndex);
			lineIndex = setSpecialSkills(lines, lineIndex, commonSkills, commonCategories);
			lineIndex = setSpecialSkills(lines, lineIndex, restrictedSkills, restrictedCategories);
			lineIndex = setCultures(lines, lineIndex);
			lineIndex = setOtherSpecials(lines, lineIndex);
			lineIndex = setNames(lines, lineIndex);
		} else {
			throw new InvalidRaceException("Invalid race file: " + RaceFactory.RACE_FOLDER + File.separator + raceName + ".txt");
		}
	}

	private int setCharacteristicsBonus(List<String> lines, int index) throws InvalidRaceException {
		characteristicBonus = new HashMap<>();
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}
		try {
			while (!lines.get(index).equals("") && !lines.get(index).startsWith("#")) {
				String characteristicLine = lines.get(index);
				String[] characteristicValue = characteristicLine.split("\t");
				if (characteristicValue[0].equals(CharacteristicsAbbreviature.APPEARENCE.getTag())) {
					apperanceBonus = Integer.parseInt(characteristicValue[1]);
				} else {
					characteristicBonus.put(CharacteristicsAbbreviature.getCharacteristicsAbbreviature(characteristicValue[0]),
							Integer.parseInt(characteristicValue[1]));
				}
				index++;
			}
		} catch (Exception e) {
			throw new InvalidRaceException("Error al leer las características de la raza " + name + ". Los bonus pueden no ser correctos.");
		}
		return index;
	}

	private int setLifeExpectation(List<String> lines, int index) throws InvalidRaceException {
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}

		try {
			if (lines.get(index).equals(Spanish.INNMORTALS)) {
				expectedLifeYears = 10000;
			} else {
				expectedLifeYears = Integer.parseInt(lines.get(index));
			}
			index++;
		} catch (NumberFormatException nfe) {
			throw new InvalidRaceException("Numero de esperanza de vida irreconocible en '" + name + "'.");
		}

		return index;
	}

	private int setResistanceBonus(List<String> lines, int index) throws InvalidRaceException {
		resistancesBonus = new HashMap<>();
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}

		try {
			while (!lines.get(index).equals("") && !lines.get(index).startsWith("#")) {
				String resistanceLine = lines.get(index);
				String[] resistanceColumns = resistanceLine.split("\t");

				resistancesBonus.put(ResistanceType.getResistancesType(resistanceColumns[0]), Integer.parseInt(resistanceColumns[1]));

				index++;
			}
		} catch (Exception e) {
			throw new InvalidRaceException("Problema al leer las resistencias de la raza " + name + ". Los bonus pueden no ser correctos.");
		}
		return index;
	}

	private int setProgressionRankValues(List<String> lines, int index) throws InvalidRaceException {
		progressionRankValues = new HashMap<>();
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}
		try {
			while (!lines.get(index).equals("") && !lines.get(index).startsWith("#")) {
				String progressionLine = lines.get(index);
				String[] progressionColumn = progressionLine.split("\t");
				progressionRankValues.put(ProgressionCostType.getProgressionCostType(progressionColumn[0]),
						Category.getConvertedProgressionString(progressionColumn[1]));
				index++;
			}
		} catch (Exception e) {
			throw new InvalidRaceException("Problema al leer los costes de progresiones de la raza " + name + ".");
		}
		return index;
	}

	public List<Float> getProgressionRankValues(ProgressionCostType type) {
		return progressionRankValues.get(type);
	}

	public String getProgressionRankValuesAsString(ProgressionCostType type) {
		return getProgressionRankValuesAsString(progressionRankValues.get(type));
	}

	public static String getProgressionRankValuesAsString(List<Float> rankValues) {
		String tag = "";
		if (rankValues != null) {
			for (Float value : rankValues) {
				if (tag.length() > 0) {
					tag += "/";
				}
				tag += ((Integer) (Math.round(value))).toString();
			}
		}
		return tag;
	}

	private int setRestrictedProfessions(List<String> lines, int index) throws InvalidRaceException {
		restrictedProfessions = new ArrayList<>();
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}
		try {
			Set<String> definedProfessions = new HashSet<>();
			boolean exception = false;
			while (!lines.get(index).equals("") && !lines.get(index).startsWith("#")) {
				String restrictedProfessionsLine = lines.get(index);
				String[] restrictedProfession = restrictedProfessionsLine.split(", ");
				for (String profession : restrictedProfession) {
					if (profession.startsWith("-")) {
						exception = true;
						profession = profession.substring(1).trim();
					}
					// All sorcerers of a magic realm.
					if (profession.contains("{")) {
						String magicRealm = profession.replace("{", "").replace("}", "").trim();
						try {
							definedProfessions.addAll(MagicFactory.getSpellCasters(RealmOfMagic.getMagicRealm(magicRealm)));
						} catch (Exception e) {
							throw new InvalidRaceException("Profesión restringida '" + profession + "' inválida para raza '" + name + "'.");
						}
					} else {
						definedProfessions.add(profession.trim());
					}
				}
				index++;
			}
			if (!exception) {
				restrictedProfessions.addAll(definedProfessions);
			} else {
				// Add all cultures except the defined one.
				List<String> allProfessions = ProfessionFactory.getAvailableProfessions();
				allProfessions.removeAll(definedProfessions);
				restrictedProfessions.addAll(allProfessions);
			}
		} catch (Exception e) {
			throw new InvalidRaceException("Problema al leer las profesiones restringidas de la raza " + name + ".");
		}
		return index;
	}

	private int setOtherRaceInformation(List<String> lines, int index) throws InvalidRaceException {
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}
		try {
			soulDepartTime = Integer.parseInt(lines.get(index));
			index++;
		} catch (NumberFormatException nfe) {
			throw new InvalidRaceException("Numero de partida del alma irreconocible en '" + name + "'.");
		}
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}
		try {
			raceType = Integer.parseInt(lines.get(index));
			index++;
		} catch (NumberFormatException nfe) {
			throw new InvalidRaceException("Numero de tipo de raza irreconocible en '" + name + "'.");
		}
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}
		size = RaceSize.getRaceSize(lines.get(index));
		index++;
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}
		try {
			restorationTime = Float.parseFloat(lines.get(index).replace(",", "."));
			index++;
		} catch (NumberFormatException nfe) {
			throw new InvalidRaceException("Numero de tiempo de recuperación irreconocible en línea " + index + ".");
		}
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}
		try {
			languagePoints = Integer.parseInt(lines.get(index));
			index++;
		} catch (NumberFormatException nfe) {
			throw new InvalidRaceException("Numero de puntos de idiomas irreconocible en '" + name + "'.");
		}
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}
		try {
			historialPoints = Integer.parseInt(lines.get(index));
			index++;
		} catch (NumberFormatException nfe) {
			throw new InvalidRaceException("Numero de puntos de historial irreconocible en '" + name + "'.");
		}
		return index;
	}

	private int setLanguages(List<String> lines, int index, Map<String, Integer> initialLanguages, Map<String, Integer> maxLanguages)
			throws InvalidRaceException {
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}
		while (!lines.get(index).equals("") && !lines.get(index).startsWith("#")) {
			try {
				String languageLine = lines.get(index);
				String[] languageInformation = languageLine.split("\t");
				String[] languageRank = languageInformation[1].split("/");
				String[] maxCultureLanguage = languageInformation[2].split("/");

				// First language is main language.
				if (raceLanguage == null) {
					raceLanguage = languageInformation[0];
				}

				String language = Spanish.SPOKEN_TAG + " " + languageInformation[0];
				initialLanguages.put(language, Integer.parseInt(languageRank[0]));

				// Add language to category.
				if (CategoryFactory.getCategory(Spanish.COMUNICATION_CATEGORY).getSkill(language) == null) {
					CategoryFactory.getCategory(Spanish.COMUNICATION_CATEGORY).addSkill(language);
				}

				language = Spanish.WRITTEN_TAG + " " + languageInformation[0];
				initialLanguages.put(language, Integer.parseInt(languageRank[1]));

				// Add language to category.
				if (CategoryFactory.getCategory(Spanish.COMUNICATION_CATEGORY).getSkill(language) == null) {
					CategoryFactory.getCategory(Spanish.COMUNICATION_CATEGORY).addSkill(language);
				}

				language = Spanish.SPOKEN_TAG + " " + languageInformation[0];
				maxLanguages.put(language, Integer.parseInt(maxCultureLanguage[0]));

				language = Spanish.WRITTEN_TAG + " " + languageInformation[0];
				maxLanguages.put(language, Integer.parseInt(maxCultureLanguage[1]));

			} catch (NumberFormatException nfe) {
				throw new InvalidRaceException("Valor de Idioma irreconocible en " + lines.get(index));
			} catch (Exception e) {
				throw new InvalidRaceException("Error leyendo la linea de idiomas \"" + lines.get(index) + "\" en línea " + index + ".");
			}
			index++;
		}
		return index;
	}

	private int setRaceLanguages(List<String> lines, int index) throws InvalidRaceException {
		return setLanguages(lines, index, initialRaceLanguages, maxRaceLanguages);
	}

	private int setHistoryLanguages(List<String> lines, int index) throws InvalidRaceException {
		return setLanguages(lines, index, maxHistoryLanguages, new HashMap<String, Integer>());
	}

	private int setSpecialSkills(List<String> lines, int index, List<Skill> skillsList, List<Category> categoriesList)
			throws InvalidRaceException {
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}

		while (!lines.get(index).equals("") && !lines.get(index).startsWith("#")) {
			String skillLine = lines.get(index);
			if (skillLine.toLowerCase().contains("ningun") || skillLine.toLowerCase().contains("nothing")) {
				index++;
				break;
			}
			String[] skillColumns = skillLine.split(", ");
			for (int i = 0; i < skillColumns.length; i++) {
				// A category defined means that all its skills are special
				if (CategoryFactory.getCategory(skillColumns[i]) != null) {
					categoriesList.add(CategoryFactory.getCategory(skillColumns[i]));
				} else {
					// Only one skill is special.
					Skill skill = SkillFactory.getAvailableSkill(skillColumns[i]);
					if (skill != null) {
						skillsList.add(skill);
					} else {
						throw new InvalidRaceException("Error leyendo la habilidad común '" + skillColumns[i] + "' para la raza '"
								+ getName() + "'.");
					}
				}
			}
			index++;
		}
		return index;
	}

	private int setCultures(List<String> lines, int index) throws InvalidRaceException {
		availableCultures = new ArrayList<>();
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}
		Set<String> definedCultures = new HashSet<>();
		boolean exception = false;
		while (!lines.get(index).equals("") && !lines.get(index).startsWith("#")) {
			String cultureLine = lines.get(index);
			if (cultureLine.toLowerCase().contains("todas") || cultureLine.toLowerCase().contains("all")) {
				definedCultures.addAll(CultureFactory.getAvailableCultures());
				index++;
				break;
			}
			String[] cultureList = cultureLine.split(", ");
			for (int i = 0; i < cultureList.length; i++) {
				String cultureName = cultureList[i];
				// All except this culture.
				if (cultureName.startsWith("-")) {
					exception = true;
					cultureName = cultureName.substring(1);
				}
				if (cultureName.contains("{")) {
					// All "Urban" cultures.
					String cult = cultureList[i].replace("{", "").replace("}", "");
					definedCultures.addAll(CultureFactory.getAvailableCulturesSubString(cult));
				} else {
					// Standard culture.
					try {
						if (CultureFactory.getCulture(cultureName) != null) {
							definedCultures.add(cultureName);
						}
					} catch (InvalidCultureException e) {
						// Not all cultures are implemented.
						// throw new
						// InvalidRaceException("Cultura no existente '"
						// + cultureList[i] + "' para la raza '"
						// + getName() + "'.");
					}
				}
			}
			index++;
		}
		if (!exception) {
			availableCultures.addAll(definedCultures);
		} else {
			// Add all cultures except the defined one.
			List<String> allCultures = CultureFactory.getAvailableCultures();
			allCultures.removeAll(definedCultures);
			availableCultures.addAll(allCultures);
		}
		return index;
	}

	private int setOtherSpecials(List<String> lines, int index) throws InvalidRaceException {
		specials = new ArrayList<>();
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}
		while (!lines.get(index).equals("")) {
			String specialLine = lines.get(index);
			if (!specialLine.toLowerCase().contains(Spanish.NOTHING_TAG)) {
				int racePoints = 0;
				// Try to check if it is a skill bonus.
				if (specialLine.contains("\t")) {
					String[] skillBonus = specialLine.split("\t");
					// Is a skill bonus
					if (skillBonus.length <= 2) {
						// Bonus.
						try {
							Integer bonus = Integer.parseInt(skillBonus[0]);
							// Check skill exists.
							Skill skill = SkillFactory.getAvailableSkill(skillBonus[1]);
							if (skill != null) {
								bonusSkills.put(skill.getName(), bonus);
								index++;
								continue;
								// Is a category?
							} else {
								Category category = CategoryFactory.getCategory(skillBonus[1]);
								if (category != null) {
									for (Skill categorySkill : category.getSkills()) {
										bonusSkills.put(categorySkill.getName(), bonus);
									}
								}
							}
						} catch (NumberFormatException nfe) {
							// Not a number, is not a skill bonus, continue
							// checks.
						}
					}
				}
				// Add perks as race specials.
				try {
					if (specialLine.contains("[")) {
						racePoints = Integer.parseInt(specialLine.substring(specialLine.indexOf('[') + 1, specialLine.indexOf(']')));
						Perk perk = PerkFactory.getPerk(specialLine.split("\t")[0].replaceFirst(":", ""));
						if (perk != null) {
							racePerks.add(perk);
						}
					}
				} catch (Exception e) {
					throw new InvalidRaceException(e.getMessage());
				}
				// Remove cost of training.
				if (specialLine.contains("[")) {
					specialLine = specialLine.substring(0, specialLine.indexOf("["));
				}
				if (!specials.contains(specialLine)) {
					specials.add(specialLine);
				}
				if (racePoints != 0) {
					specialsRacePoints.put(specialLine, racePoints);
				}
				// Set Natural Armour
				if (specialLine.toLowerCase().startsWith(Spanish.ARMOUR_TAG_COMPLETE.toLowerCase())) {
					try {
						naturalArmourType = Integer.parseInt(specialLine.replaceAll("[^\\d.]", ""));
					} catch (Exception e) {
						throw new InvalidRaceException("Invalid natural armour:" + specialLine);
					}
				}
			}
			index++;
		}
		return index;
	}

	private int setNames(List<String> lines, int index) throws InvalidRaceException {
		maleNames = new ArrayList<>();
		femaleNames = new ArrayList<>();
		familyNames = new ArrayList<>();

		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}
		// Male Names.
		try {
			while (!lines.get(index).equals("")) {
				String nameLine = lines.get(index);
				String[] names = nameLine.split(", ");
				maleNames.addAll(Arrays.asList(names));
				index++;
			}
		} catch (IndexOutOfBoundsException iob) {
			throw new InvalidRaceException("Invalid name definition in race '" + name + "'.");
		}

		try {
			while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
				index++;
			}
		} catch (IndexOutOfBoundsException iob) {
			throw new InvalidRaceException("Invalid name definition in race '" + name + "'.");
		}

		// Female Names.
		try {
			while (!lines.get(index).equals("")) {
				String nameLine = lines.get(index);
				String[] names = nameLine.split(", ");
				femaleNames.addAll(Arrays.asList(names));
				index++;
			}
		} catch (IndexOutOfBoundsException iob) {
			throw new InvalidRaceException("Invalid name definition in race '" + name + "'.");
		}

		try {
			while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
				index++;
			}
		} catch (IndexOutOfBoundsException iob) {
			throw new InvalidRaceException("Invalid name definition in race '" + name + "'.");
		}

		// Surnames
		try {
			while (!lines.get(index).equals("")) {
				String surnameLine = lines.get(index);
				String[] surnames = surnameLine.split(", ");
				familyNames.addAll(Arrays.asList(surnames));
				index++;
			}
		} catch (IndexOutOfBoundsException iob) {
			throw new InvalidRaceException("Invalid name definition in race '" + name + "'.");
		}

		return index;
	}

	/**
	 * Only cultures of the race that also exists in the application.
	 * 
	 * @return
	 */
	public List<String> getAvailableCultures() {
		return CultureFactory.availableCultures(availableCultures);
	}

	public List<String> getAvailableProfessions() {
		List<String> allProfessions = new ArrayList<>(ProfessionFactory.getAvailableProfessions());
		allProfessions.removeAll(restrictedProfessions);
		return allProfessions;
	}

	public String getRandonName(SexType sex) {
		String name, surname;
		Random randomGenerator = new Random();
		if (sex.equals(SexType.MALE)) {
			int index = randomGenerator.nextInt(maleNames.size());
			name = maleNames.get(index);
		} else {
			int index = randomGenerator.nextInt(femaleNames.size());
			name = femaleNames.get(index);
		}

		int index = randomGenerator.nextInt(familyNames.size());
		surname = familyNames.get(index);

		return name + " " + surname;
	}

	public Integer getCharacteristicBonus(CharacteristicsAbbreviature abbreviature) {
		if (characteristicBonus.get(abbreviature) == null) {
			return 0;
		}
		return characteristicBonus.get(abbreviature);
	}

	public Integer getLanguageInitialRanks(String language) {
		Integer ranks = initialRaceLanguages.get(language);
		if (ranks == null) {
			return 0;
		}
		return ranks;
	}

	public Integer getLanguageMaxRanks(String language) {
		Integer ranks = maxRaceLanguages.get(language);
		if (ranks == null) {
			return 10;
		}
		return ranks;
	}

	public List<String> getAvailableLanguages() {
		return new ArrayList<>(maxRaceLanguages.keySet());
	}

	public Integer getHistorialPoints() {
		return historialPoints;
	}

	public Integer getPerksPoints() throws InvalidRaceDefinition {
		if (perksPoints == null) {
			perksPoints = new PerkPointsCalculator(this).getPerkPoints();
		}
		return perksPoints;
	}

	public int getApperanceBonus() {
		return apperanceBonus;
	}

	public boolean isRestricted(Skill skill) {
		try {
			return restrictedSkills.contains(skill) || restrictedCategories.contains(skill.getCategory());
		} catch (NullPointerException npe) {
			return false;
		}
	}

	public boolean isCommon(Skill skill) {
		try {
			return commonSkills.contains(skill) || commonCategories.contains(skill.getCategory());
		} catch (NullPointerException npe) {
			return false;
		}
	}

	public Integer getSoulDepartTime() {
		return soulDepartTime;
	}

	public Integer getRaceType() {
		return raceType;
	}

	public RaceSize getSize() {
		return size;
	}

	public Float getRestorationTime() {
		return restorationTime;
	}

	public List<String> getSpecials() {
		return specials;
	}

	public Integer getResistancesBonus(ResistanceType resistence) {
		if (resistancesBonus.get(resistence) != null) {
			return resistancesBonus.get(resistence);
		}
		return 0;
	}

	public int getTotalCommonSkills() {
		int skillsOfCategories = 0;
		for (Category category : commonCategories) {
			skillsOfCategories += category.getSkills().size();
		}
		return commonSkills.size() + skillsOfCategories;
	}

	public int getTotalRestrictedSkills() {
		int skillsOfCategories = 0;
		for (Category category : restrictedCategories) {
			skillsOfCategories += category.getSkills().size();
		}
		return restrictedSkills.size() + skillsOfCategories;
	}

	public Map<String, Integer> getInitialRaceLanguages() {
		return initialRaceLanguages;
	}

	public String getRaceLanguage() {
		return raceLanguage;
	}

	public List<Perk> getRacePerks() {
		List<Perk> racePerks = new ArrayList<>();
		for (String special : getSpecials()) {
			String[] columns = special.split(":");
			if (columns.length > 1) {
				Perk perk = PerkFactory.getPerk(columns[0]);
				if (perk != null) {
					racePerks.add(perk);
				}
			}
		}
		return racePerks;
	}

	public Map<String, Integer> getSpecialsRacePoints() {
		return specialsRacePoints;
	}

	public Integer getExpectedLifeYears() {
		return expectedLifeYears;
	}

	public Map<String, Integer> getMaxHistoryLanguages() {
		return maxHistoryLanguages;
	}

	public int getNaturalArmourType() {
		return naturalArmourType;
	}

	public int getBonus(Skill skill) {
		Integer bonus = bonusSkills.get(skill.getName());
		if (bonus != null) {
			return bonus;
		}
		return 0;
	}

	public Set<Perk> getPerks() {
		return racePerks;
	}
}
