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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

import com.softwaremagico.files.Folder;
import com.softwaremagico.files.RolemasterFolderStructure;
import com.softwaremagico.librodeesher.basics.ShowMessage;
import com.softwaremagico.librodeesher.pj.Language;
import com.softwaremagico.librodeesher.pj.ProgressionCostType;
import com.softwaremagico.librodeesher.pj.SexType;
import com.softwaremagico.librodeesher.pj.categories.Category;
import com.softwaremagico.librodeesher.pj.culture.CultureFactory;
import com.softwaremagico.librodeesher.pj.profession.ProfessionFactory;
import com.softwaremagico.librodeesher.pj.resistance.ResistanceType;
import com.softwaremagico.librodeesher.pj.skills.Skill;
import com.softwaremagico.librodeesher.pj.skills.SkillFactory;

public class Race {
	private String name;
	private int apperanceBonus;
	private Hashtable<String, Integer> characteristicBonus;
	private Hashtable<ResistanceType, Integer> resistancesBonus;
	private Hashtable<ProgressionCostType, Float[]> progressionRankValues;
	private List<String> restrictedProfessions;
	private Integer soulDepartTime;
	private Integer raceType;
	private String size;
	private Float restorationTime;
	private Integer languagePoints;
	private Integer historialPoints;
	private Hashtable<String, Language> initialRaceLanguages;
	private Hashtable<String, Language> maxRaceLanguages;
	private List<Skill> commonSkills;
	private List<Skill> restrictedSkills;
	private List<String> availableCultures;
	private List<String> specials;
	private Integer talentPoints;
	private List<String> maleNames;
	private List<String> femaleNames;
	private List<String> familyNames;

	public Race(String name) {
		this.name = name;
		apperanceBonus = 0;
		try {
			readRaceFile(name);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getName() {
		return name;
	}
	
	public Integer getLanguagePoints(){
		return languagePoints;
	}

	private void readRaceFile(String raceName) throws Exception {
		int lineIndex = 0;

		String raceFile = RolemasterFolderStructure.getDirectoryModule(RaceFactory.RACE_FOLDER
				+ File.separator + raceName + ".txt");
		if (raceFile.length() > 0) {
			List<String> lines = Folder.readFileLines(raceFile, false);

			lineIndex = setCharacteristicsBonus(lines, lineIndex);
			lineIndex = setResistanceBonus(lines, lineIndex);
			lineIndex = setProgressionRankValues(lines, lineIndex);
			lineIndex = setRestrictedProfessions(lines, lineIndex);
			lineIndex = setOtherRaceInformation(lines, lineIndex);
			lineIndex = setRaceLanguages(lines, lineIndex);
			lineIndex = setSpecialSkills(lines, lineIndex, commonSkills);
			lineIndex = setSpecialSkills(lines, lineIndex, restrictedSkills);
			lineIndex = setCultures(lines, lineIndex);
			lineIndex = setOtherSpecials(lines, lineIndex);
			lineIndex = setTalents(lines, lineIndex);
			lineIndex = setNames(lines, lineIndex);
		}
	}

	private int setCharacteristicsBonus(List<String> lines, int index) {
		characteristicBonus = new Hashtable<>();
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}
		try {
			while (!lines.get(index).equals("") && !lines.get(index).startsWith("#")) {
				String characteristicLine = lines.get(index);
				String[] characteristicValue = characteristicLine.split("\t");
				if (characteristicValue[0].equals("Ap")) {
					apperanceBonus = Integer.parseInt(characteristicValue[1]);
				} else {
					characteristicBonus.put(characteristicValue[0], Integer.parseInt(characteristicValue[1]));
				}
				index++;
			}
		} catch (Exception e) {
			ShowMessage.showErrorMessage("Problema al leer las características de la raza " + name
					+ ". Los bonus pueden no ser correctos.", "Leer Raza");
		}
		return index;
	}

	private int setResistanceBonus(List<String> lines, int index) {
		resistancesBonus = new Hashtable<>();
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
			ShowMessage.showErrorMessage("Problema al leer las resistencias de la raza " + name
					+ ". Los bonus pueden no ser correctos.", "Leer Raza");
		}
		return index;
	}

	private int setProgressionRankValues(List<String> lines, int index) {
		progressionRankValues = new Hashtable<>();
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
			e.printStackTrace();
			ShowMessage.showErrorMessage("Problema al leer los costes de progresiones de la raza " + name
					+ ".", "Leer Raza");
		}
		return index;
	}
	
	public Float[] getProgressionRankValues(ProgressionCostType type){
		return progressionRankValues.get(type);
	}

	private int setRestrictedProfessions(List<String> lines, int index) {
		restrictedProfessions = new ArrayList<>();
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}
		try {
			while (!lines.get(index).equals("") && !lines.get(index).startsWith("#")) {
				String restrictedProfessionsLine = lines.get(index);
				String[] restrictedProfession = restrictedProfessionsLine.split(", ");
				for (String profession : restrictedProfession) {
					restrictedProfessions.add(profession.trim());
				}
				index++;
			}
		} catch (Exception e) {
			ShowMessage.showErrorMessage("Problema al leer las profesiones restringidas de la raza " + name
					+ ".", "Leer Raza");
		}
		return index;
	}

	private int setOtherRaceInformation(List<String> lines, int index) {
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}
		try {
			soulDepartTime = Integer.parseInt(lines.get(index));
			index++;
		} catch (NumberFormatException nfe) {
			ShowMessage.showErrorMessage("Numero de partida del alma irreconocible.", "Leer Raza");
			soulDepartTime = new Integer(0);
		}
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}
		try {
			raceType = Integer.parseInt(lines.get(index));
			index++;
		} catch (NumberFormatException nfe) {
			ShowMessage.showErrorMessage("Numero de tipo de raza irreconocible.", "Leer Raza");
			raceType = new Integer(0);
		}
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}
		size = lines.get(index);
		index++;
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}
		try {
			restorationTime = Float.parseFloat(lines.get(index).replace(",", "."));
			index++;
		} catch (NumberFormatException nfe) {
			ShowMessage.showErrorMessage("Numero de tiempo de recuperación irreconocible en línea " + index
					+ ".", "Leer Raza");
			restorationTime = new Float(0);
		}
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}
		try {
			languagePoints = Integer.parseInt(lines.get(index));
			index++;
		} catch (NumberFormatException nfe) {
			ShowMessage.showErrorMessage("Numero de puntos de idiomas irreconocible.", "Leer Raza");
			languagePoints = new Integer(0);
		}
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}
		try {
			historialPoints = Integer.parseInt(lines.get(index));
			index++;
		} catch (NumberFormatException nfe) {
			ShowMessage.showErrorMessage("Numero de puntos de historial irreconocible.", "Leer Raza");
			historialPoints = new Integer(0);
		}
		return index;
	}

	private int setRaceLanguages(List<String> lines, int index) {
		initialRaceLanguages = new Hashtable<>();
		maxRaceLanguages = new Hashtable<>();
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}
		while (!lines.get(index).equals("") && !lines.get(index).startsWith("#")) {
			try {
				String languageLine = lines.get(index);
				String[] languageInformation = languageLine.split("\t");
				String[] languageRank = languageInformation[1].split("/");
				String[] maxCultureLanguage = languageInformation[2].split("/");

				Language language = new Language(Language.SPOKEN_TAG + " " + languageInformation[0],
						Integer.parseInt(languageRank[0]));
				initialRaceLanguages.put(language.getName(), language);

				language = new Language(Language.WRITTEN_TAG + " " + languageInformation[0],
						Integer.parseInt(languageRank[1]));
				initialRaceLanguages.put(language.getName(), language);

				language = new Language(Language.SPOKEN_TAG + " " + languageInformation[0],
						Integer.parseInt(maxCultureLanguage[0]));
				maxRaceLanguages.put(language.getName(), language);

				language = new Language(Language.WRITTEN_TAG + " " + languageInformation[0],
						Integer.parseInt(maxCultureLanguage[1]));
				maxRaceLanguages.put(language.getName(), language);

			} catch (NumberFormatException nfe) {
				ShowMessage.showErrorMessage("Valor de Idioma irreconocible en " + lines.get(index),
						"Leer Raza");
			} catch (Exception e) {
				ShowMessage.showErrorMessage("Error leyendo la linea de idiomas \"" + lines.get(index)
						+ "\" en línea " + index + ".", "Leer Raza");
			}
			index++;
		}
		return index;
	}

	private int setSpecialSkills(List<String> lines, int index, List<Skill> skillCategory) {
		skillCategory = new ArrayList<>();
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
				Skill skill = SkillFactory.getSkill(skillColumns[i]);
				skillCategory.add(skill);
			}
			index++;
		}
		return index;
	}

	private int setCultures(List<String> lines, int index) throws Exception {
		availableCultures = new ArrayList<>();
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}
		while (!lines.get(index).equals("") && !lines.get(index).startsWith("#")) {
			String cultureLine = lines.get(index);
			if (cultureLine.toLowerCase().contains("todas") || cultureLine.toLowerCase().contains("all")) {
				availableCultures.addAll(CultureFactory.availableCultures());
				index++;
				break;
			}
			String[] cultureList = cultureLine.split(", ");
			for (int i = 0; i < cultureList.length; i++) {
				if (cultureList[i].contains("{")) {
					// All "Urban" cultures.
					String cult = cultureList[i].replace("{", "").replace("}", "");
					availableCultures.addAll(CultureFactory.availableCulturesSubString(cult));
				} else {
					// Standard culture.
					availableCultures.add(cultureList[i]);
				}
			}
			index++;
		}
		return index;
	}

	private int setOtherSpecials(List<String> lines, int index) {
		specials = new ArrayList<>();
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}
		while (!lines.get(index).equals("")) {
			String specialLine = lines.get(index);
			if (!specialLine.toLowerCase().equals("ninguno") && !specialLine.toLowerCase().equals("ninguna")
					&& !specialLine.toLowerCase().equals("nothing")) {
				if (!specials.contains(specialLine)) {
					specials.add(specialLine);
				}
			}
			index++;
		}
		return index;
	}

	private int setTalents(List<String> lines, int index) {
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}
		while (!lines.get(index).equals("")) {
			String talentLine = lines.get(index);
			try {
				talentPoints = Integer.parseInt(talentLine);
			} catch (NumberFormatException nfe) {
				ShowMessage.showErrorMessage("Numero de puntos de talento irreconocible.", "Leer Raza");
				talentPoints = new Integer(0);
			}
			index++;
		}
		return index;
	}

	private int setNames(List<String> lines, int index) {
		maleNames = new ArrayList<>();
		femaleNames = new ArrayList<>();
		familyNames = new ArrayList<>();

		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}
		// Male Names.
		while (!lines.get(index).equals("")) {
			String nameLine = lines.get(index);
			String[] names = nameLine.split(", ");
			maleNames.addAll(Arrays.asList(names));
			index++;
		}
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}

		// Female Names.
		while (!lines.get(index).equals("")) {
			String nameLine = lines.get(index);
			String[] names = nameLine.split(", ");
			femaleNames.addAll(Arrays.asList(names));
			index++;
		}

		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}
		// Surnames
		while (!lines.get(index).equals("")) {
			String surnameLine = lines.get(index);
			String[] surnames = surnameLine.split(", ");
			familyNames.addAll(Arrays.asList(surnames));
			index++;
		}
		return index;
	}

	/**
	 * Only cultures of the race that also exists in the application.
	 * 
	 * @return
	 */
	public List<String> availableCultures() {
		return CultureFactory.availableCultures(availableCultures);
	}

	public List<String> availableProfessions() {
		List<String> allProfessions = ProfessionFactory.getAvailableProfessions();
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

	public Integer getCharacteristicBonus(String abbreviature) {
		return characteristicBonus.get(abbreviature);
	}

	public Integer getLanguageInitialRanks(Language language) {
		Language langCult = initialRaceLanguages.get(language.getName());
		if (langCult == null) {
			return 0;
		}
		return langCult.getRanks();
	}

	public Integer getLanguageMaxRanks(Language language) {
		Language langCult = maxRaceLanguages.get(language.getName());
		if (langCult == null) {
			return 10;
		}
		return langCult.getRanks();
	}
	
	public Integer getHistorialPoints(){
		return historialPoints;
	}

}
