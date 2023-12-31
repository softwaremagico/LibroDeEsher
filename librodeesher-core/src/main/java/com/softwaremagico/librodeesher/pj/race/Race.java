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
import com.softwaremagico.librodeesher.pj.language.OptionalLanguage;
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
import com.softwaremagico.log.EsherLog;

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
	private Integer backgroundPoints;
	private Map<String, Integer> initialRaceLanguages;
	private Map<String, Integer> maxRaceLanguages;
	private Map<String, Integer> maxBackgroundLanguages;
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
	private Map<String, Integer> bonusCategory;
	private Set<Perk> racePerks;
	private List<OptionalLanguage> optionalRaceLanguages;
	private List<OptionalLanguage> optionalBackgroundLanguages;

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
		maxBackgroundLanguages = new HashMap<>();
		bonusSkills = new HashMap<>();
		bonusCategory = new HashMap<>();
		racePerks = new HashSet<>();
		optionalRaceLanguages = new ArrayList<>();
		optionalBackgroundLanguages = new ArrayList<>();
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

		String raceFile = RolemasterFolderStructure.getDirectoryModule(RaceFactory.RACE_FOLDER
				+ File.separator + raceName + ".txt");
		if (raceFile.length() > 0) {
			List<String> lines;
			try {
				lines = Folder.readFileLines(raceFile, false);
			} catch (IOException e) {
				throw new InvalidRaceException("Invalid race file: " + RaceFactory.RACE_FOLDER
						+ File.separator + raceName + ".txt", e);
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
			throw new InvalidRaceException("Invalid race file: " + RaceFactory.RACE_FOLDER + File.separator
					+ raceName + ".txt");
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
					characteristicBonus.put(CharacteristicsAbbreviature
							.getCharacteristicsAbbreviature(characteristicValue[0]), Integer
							.parseInt(characteristicValue[1]));
				}
				index++;
			}
		} catch (Exception e) {
			throw new InvalidRaceException("Error al leer las características de la raza " + name
					+ ". Los bonus pueden no ser correctos.", e);
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
			throw new InvalidRaceException("Life length number not valid for race '" + name + "'.", nfe);
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

				resistancesBonus.put(ResistanceType.getResistancesType(resistanceColumns[0]),
						Integer.parseInt(resistanceColumns[1]));

				index++;
			}
		} catch (Exception e) {
			throw new InvalidRaceException("Race resistances are invalid in race " + name
					+ ". Bonuses must be wrong.", e);
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
			throw new InvalidRaceException("Progression cost invalid in race '" + name + "'.", e);
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
							definedProfessions.addAll(MagicFactory.getSpellCasters(RealmOfMagic
									.getMagicRealm(magicRealm)));
						} catch (Exception e) {
							throw new InvalidRaceException("Restricted profession '" + profession
									+ "' invalid for race '" + name + "'.", e);
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
			throw new InvalidRaceException("Restricted professions reading problem for race '" + name + "'.",
					e);
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
			throw new InvalidRaceException("Soul departure invalid for race '" + name + "'.", nfe);
		}
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}
		try {
			raceType = Integer.parseInt(lines.get(index));
			index++;
		} catch (NumberFormatException nfe) {
			throw new InvalidRaceException("Race type invalid in race '" + name + "'.", nfe);
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
			throw new InvalidRaceException("Healing rate invalid for race '" + getName() + "'.", nfe);
		}
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}
		try {
			languagePoints = Integer.parseInt(lines.get(index));
			index++;
		} catch (NumberFormatException nfe) {
			throw new InvalidRaceException("Langauge points invalid for race '" + name + "'.", nfe);
		}
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}
		try {
			backgroundPoints = Integer.parseInt(lines.get(index));
			index++;
		} catch (NumberFormatException nfe) {
			throw new InvalidRaceException("History points invalid in race '" + name + "'.", nfe);
		}
		return index;
	}

	private int setLanguages(List<String> lines, int index, Map<String, Integer> initialLanguages,
			Map<String, Integer> maxLanguages, List<OptionalLanguage> optionalLanguages)
			throws InvalidRaceException {
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}
		while (!lines.get(index).equals("") && !lines.get(index).startsWith("#")) {
			if (!lines.get(index).toLowerCase().startsWith(Spanish.NOTHING_TAG)) {
				try {
					String languageLine = lines.get(index);
					String[] languageInformation = languageLine.split("\t");
					String[] languageRank = languageInformation[1].split("/");
					String[] maxCultureLanguage = languageInformation[2].split("/");

					// First language is main language.
					if (raceLanguage == null) {
						raceLanguage = languageInformation[0];
					}

					// User selection language.
					if (languageInformation[0].startsWith(Spanish.ANY_RACE_LANGUAGE)
							|| languageInformation[0].startsWith(Spanish.ANY_CULTURE_LANGUAGE)) {
						OptionalLanguage optionLanguage = new OptionalLanguage();
						optionLanguage.setStartingSpeakingRanks(Integer.parseInt(languageRank[0]));
						optionLanguage.setStartingWrittingRanks(Integer.parseInt(languageRank[1]));
						optionLanguage.setMaxSpeakingRanks(Integer.parseInt(maxCultureLanguage[0]));
						optionLanguage.setMaxWritingRanks(Integer.parseInt(maxCultureLanguage[1]));
						optionalLanguages.add(optionLanguage);
					} else {
						// Standard language.
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
					}
				} catch (NumberFormatException nfe) {
					throw new InvalidRaceException("Language value invalid in '" + lines.get(index) + "'.",
							nfe);
				} catch (Exception e) {
					throw new InvalidRaceException("Language line invalid '" + lines.get(index)
							+ "' in line '" + index + "'.", e);
				}
			}
			index++;
		}
		return index;
	}

	private int setRaceLanguages(List<String> lines, int index) throws InvalidRaceException {
		return setLanguages(lines, index, initialRaceLanguages, maxRaceLanguages, optionalRaceLanguages);
	}

	private int setHistoryLanguages(List<String> lines, int index) throws InvalidRaceException {
		return setLanguages(lines, index, maxBackgroundLanguages, new HashMap<String, Integer>(),
				optionalBackgroundLanguages);
	}

	private int setSpecialSkills(List<String> lines, int index, List<Skill> skillsList,
			List<Category> categoriesList) throws InvalidRaceException {
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
						throw new InvalidRaceException("Invalid common skill '" + skillColumns[i]
								+ "' for race '" + getName() + "'.");
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
			if (cultureLine.toLowerCase().contains(Spanish.ALL_TAG)
					|| cultureLine.toLowerCase().contains("all")) {
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
							definedCultures.addAll(CultureFactory.getAvailableCulturesPrefix(cultureName));
						}
					} catch (InvalidCultureException e) {
						EsherLog.warning(this.getClass().getName(), "Culture '" + cultureName
								+ "' does not exists in race '" + getName() + "'.");
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
							} else if (CategoryFactory.getCategory(skillBonus[1]) != null) {
								bonusCategory.put(skillBonus[1], bonus);
								index++;
								continue;
							} else {
								EsherLog.warning(this.getClass().getName(), "Unknown skill '" + skillBonus[1]
										+ "' in race '" + getName() + "'.");
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
						racePoints = Integer.parseInt(specialLine.substring(specialLine.indexOf('[') + 1,
								specialLine.indexOf(']')));
						Perk perk = PerkFactory.getPerk(specialLine.split("\t")[0].replaceFirst(":", ""));
						if (perk != null) {
							racePerks.add(perk);
						}
					}
				} catch (Exception e) {
					throw new InvalidRaceException(e);
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
						throw new InvalidRaceException("Invalid natural armour:" + specialLine, e);
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
			throw new InvalidRaceException("Invalid name definition in race '" + name + "'.", iob);
		}

		try {
			while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
				index++;
			}
		} catch (IndexOutOfBoundsException iob) {
			throw new InvalidRaceException("Invalid name definition in race '" + name + "'.", iob);
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
			throw new InvalidRaceException("Invalid name definition in race '" + name + "'.", iob);
		}

		try {
			while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
				index++;
			}
		} catch (IndexOutOfBoundsException iob) {
			throw new InvalidRaceException("Invalid name definition in race '" + name + "'.", iob);
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
			throw new InvalidRaceException("Invalid name definition in race '" + name + "'.", iob);
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

	public Integer getBackgroundPoints() {
		return backgroundPoints;
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
		return maxBackgroundLanguages;
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

	public int getBonus(Category category) {
		Integer bonus = bonusCategory.get(category.getName());
		if (bonus != null) {
			return bonus;
		}
		return 0;
	}

	public Set<Perk> getPerks() {
		return racePerks;
	}

	public List<OptionalLanguage> getOptionalRaceLanguages() {
		return optionalRaceLanguages;
	}

	public List<OptionalLanguage> getOptionalBackgroundLanguages() {
		return optionalBackgroundLanguages;
	}
}
