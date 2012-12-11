package com.softwaremagico.librodeesher.pj.race;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import com.softwaremagico.files.Folder;
import com.softwaremagico.files.RolemasterFolderStructure;
import com.softwaremagico.librodeesher.gui.MostrarMensaje;
import com.softwaremagico.librodeesher.pj.ProgressionCostType;
import com.softwaremagico.librodeesher.pj.culture.CultureFactory;
import com.softwaremagico.librodeesher.pj.resistance.ResistancesType;
import com.softwaremagico.librodeesher.pj.skills.Skill;
import com.softwaremagico.librodeesher.pj.skills.SkillFactory;

public class Race {
	private String name;
	private int apperanceBonus;
	private Hashtable<String, Integer> characteristicBonus;
	private Hashtable<ResistancesType, Integer> resistancesBonus;
	private Hashtable<ProgressionCostType, Integer> progressionCosts;
	private List<String> restrictedProfessions;
	private Integer soulDepartTime;
	private Integer raceType;
	private String size;
	private Float restorationTime;
	private Integer languagePoints;
	private Integer historialPoints;
	private List<RaceLanguage> raceLanguages;
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

	private void readRaceFile(String raceName) throws Exception {
		int lineaLeida = 0;

		String raceFile = RolemasterFolderStructure.searchDirectoryModule(RaceFactory.RACE_FOLDER + File.separator
				+ raceName + ".txt");
		if (raceFile.length() > 0) {
			List<String> lines = Folder.readFileLines(raceFile, false);

			lineaLeida = setCharacteristicsBonus(lines, lineaLeida);
			lineaLeida = setResistanceBonus(lines, lineaLeida);
			lineaLeida = setProgressionCost(lines, lineaLeida);
			lineaLeida = setRestrictedProfessions(lines, lineaLeida);
			lineaLeida = setOtherRaceInformation(lines, lineaLeida);
			lineaLeida = setRaceLanguages(lines, lineaLeida);
			lineaLeida = setSpecialSkills(lines, lineaLeida, commonSkills);
			lineaLeida = setSpecialSkills(lines, lineaLeida, restrictedSkills);
			lineaLeida = setCultures(lines, lineaLeida);
			lineaLeida = setOtherSpecials(lines, lineaLeida);
			lineaLeida = setTalents(lines, lineaLeida);
			lineaLeida = AsignarNombres(lines, lineaLeida);
		}
	}

	private int setCharacteristicsBonus(List<String> lines, int index) {
		characteristicBonus = new Hashtable<>();
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}
		try {
			while (!lines.get(index).equals("") || !lines.get(index).startsWith("#")) {
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
			MostrarMensaje.showErrorMessage("Problema al leer las características de la raza " + name
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
			while (!lines.get(index).equals("") || !lines.get(index).startsWith("#")) {
				String resistanceLine = lines.get(index);
				String[] resistanceColumns = resistanceLine.split("\t");

				resistancesBonus.put(ResistancesType.getResistancesType(resistanceColumns[0]),
						Integer.parseInt(resistanceColumns[1]));

				index++;
			}
		} catch (Exception e) {
			MostrarMensaje.showErrorMessage("Problema al leer las resistencias de la raza " + name
					+ ". Los bonus pueden no ser correctos.", "Leer Raza");
		}
		return index;
	}

	private int setProgressionCost(List<String> lines, int index) {
		progressionCosts = new Hashtable<>();
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}
		try {
			while (!lines.get(index).equals("") || !lines.get(index).startsWith("#")) {
				String progressionLine = lines.get(index);
				String[] progressionColumn = progressionLine.split("\t");
				progressionCosts.put(ProgressionCostType.getProgressionCostType(progressionColumn[0]),
						Integer.parseInt(progressionColumn[1]));
				index++;
			}
		} catch (Exception e) {
			MostrarMensaje.showErrorMessage("Problema al leer los costes de progresiones de la raza "
					+ name + ".", "Leer Raza");
		}
		return index;
	}

	private int setRestrictedProfessions(List<String> lines, int index) {
		restrictedProfessions = new ArrayList<>();
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}
		try {
			while (!lines.get(index).equals("") || !lines.get(index).startsWith("#")) {
				String restrictedProfessionsLine = lines.get(index);
				String[] restrictedProfession = restrictedProfessionsLine.split(", ");
				for (String profession : restrictedProfession) {
					restrictedProfessions.add(profession.trim());
				}
				index++;
			}
		} catch (Exception e) {
			MostrarMensaje.showErrorMessage("Problema al leer las profesiones restringidas de la raza "
					+ name + ".", "Leer Raza");
		}
		return index;
	}

	private int setOtherRaceInformation(List<String> lines, int index) {
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}
		try {
			soulDepartTime = Integer.parseInt(lines.get(index));
		} catch (NumberFormatException nfe) {
			MostrarMensaje.showErrorMessage("Numero de partida del alma irreconocible.", "Leer Raza");
			soulDepartTime = new Integer(0);
		}
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}
		try {
			raceType = Integer.parseInt(lines.get(index));
		} catch (NumberFormatException nfe) {
			MostrarMensaje.showErrorMessage("Numero de tipo de raza irreconocible.", "Leer Raza");
			raceType = new Integer(0);
		}
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}
		size = lines.get(index);
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}
		try {
			restorationTime = Float.parseFloat(lines.get(index));
		} catch (NumberFormatException nfe) {
			MostrarMensaje.showErrorMessage("Numero de tiempo de recuperación irreconocible.", "Leer Raza");
			restorationTime = new Float(0);
		}
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}
		try {
			languagePoints = Integer.parseInt(lines.get(index));
		} catch (NumberFormatException nfe) {
			MostrarMensaje.showErrorMessage("Numero de puntos de idiomas irreconocible.", "Leer Raza");
			languagePoints = new Integer(0);
		}
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}
		try {
			historialPoints = Integer.parseInt(lines.get(index));
		} catch (NumberFormatException nfe) {
			MostrarMensaje.showErrorMessage("Numero de puntos de historial irreconocible.", "Leer Raza");
			historialPoints = new Integer(0);
		}
		index++;
		return index;
	}

	private int setRaceLanguages(List<String> lines, int index) {
		raceLanguages = new ArrayList<>();
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}
		while (!lines.get(index).equals("") || !lines.get(index).startsWith("#")) {
			try {
				String languageLine = lines.get(index);
				String[] languageInformation = languageLine.split("\t");
				String[] languageRank = languageInformation[1].split("/");
				String[] maxCultureLanguage = languageInformation[2].split("/");
				
				RaceLanguage language = new RaceLanguage(languageInformation[0], languageRank[0], languageRank[1], maxCultureLanguage[0], maxCultureLanguage[1]);
				raceLanguages.add(language);
				
			} catch (NumberFormatException nfe) {
				MostrarMensaje.showErrorMessage("Valor de Idioma irreconocible en " + lines.get(index),
						"Leer Raza");
			} catch (Exception e) {
				MostrarMensaje.showErrorMessage("Error leyendo la linea de idiomas: " + lines.get(index),
						"Leer Raza");
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

		while (!lines.get(index).equals("") || !lines.get(index).startsWith("#")) {
			String skillLine = lines.get(index);
			if (skillLine.toLowerCase().contains("ninguna") || skillLine.toLowerCase().contains("nothing")) {
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
		while (!lines.get(index).equals("") || !lines.get(index).startsWith("#")) {
			String cultureLine = lines.get(index);
			if (cultureLine.toLowerCase().contains("todas") || cultureLine.toLowerCase().contains("All")) {
				availableCultures.addAll(CultureFactory.availableCultures());
				index++;
				break;
			}
			String[] cultureList = cultureLine.split(", ");
			for (int i = 0; i < cultureList.length; i++) {
				if (cultureList[i].contains("{")) {
					//All "Urban" cultures.
					String cult = cultureList[i].replace("{", "").replace("}", "");
					availableCultures.addAll(CultureFactory.availableCulturesSubString(cult));
				} else {
					//Standard culture.
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
				MostrarMensaje.showErrorMessage("Numero de puntos de talento irreconocible.", "Leer Raza");
				talentPoints = new Integer(0);
			}
			index++;
		}
		return index;
	}

	private int AsignarNombres(List<String> lines, int index) {
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
}
