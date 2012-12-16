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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.softwaremagico.files.Folder;
import com.softwaremagico.files.RolemasterFolderStructure;
import com.softwaremagico.librodeesher.gui.ShowMessage;
import com.softwaremagico.librodeesher.pj.categories.CategoryFactory;
import com.softwaremagico.librodeesher.pj.skills.SkillFactory;
import com.softwaremagico.librodeesher.pj.weapons.Weapon;
import com.softwaremagico.librodeesher.pj.weapons.WeaponFactory;

public class Culture {
	private String name;
	private List<Weapon> cultureWeapons;
	private List<String> cultureArmours;
	private List<CultureCategory> categories;
	private int hobbyRanks;
	private List<CultureCategory> hobbyCategories;
	private List<CultureSkill> hobbySkills;
	private List<CultureLanguage> languages;

	public Culture(String name) {
		this.name = name;
		try {
			readCultureFile(name);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void readCultureFile(String cultureName) throws Exception {
		int lineIndex = 0;
		String cultureFile = RolemasterFolderStructure.searchDirectoryModule(CultureFactory.CULTURE_FOLDER
				+ File.separator + cultureName + ".txt");
		if (cultureFile.length() > 0) {
			List<String> lines = Folder.readFileLines(cultureFile, false);
			lineIndex = setCultureWeapons(lines, lineIndex);
			lineIndex = setCultureArmour(lines, lineIndex);
			lineIndex = setSkillRanks(lines, lineIndex);
			lineIndex = setHobbyRanks(lines, lineIndex);
			lineIndex = setHobbySkillsAndCategories(lines, lineIndex);
			lineIndex = setCultureLanguages(lines, lineIndex);
		}
	}

	private int setCultureWeapons(List<String> lines, int index) throws Exception {
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}
		try {
			cultureWeapons = new ArrayList<>();
			if (!lines.get(index).toLowerCase().equals("todas")
					&& !lines.get(index).toLowerCase().equals("all")) {
				while (!lines.get(index).equals("") && !lines.get(index).startsWith("#")) {
					String lineaArmasCultura = lines.get(index);
					String[] weapons = lineaArmasCultura.split(", ");
					for (String weaponName : weapons) {
						cultureWeapons.add(WeaponFactory.getWeapon(weaponName));
					}
					index++;
				}
			} else {
				cultureWeapons = WeaponFactory.getAllWeapons();
				index++;
			}
		} catch (IndexOutOfBoundsException iof) {
		}
		return index;
	}

	private int setCultureArmour(List<String> lines, int index) {
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
		}
		return index;
	}

	private int setSkillRanks(List<String> lines, int index) throws Exception {
		categories = new ArrayList<>();
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}
		try {
			CultureCategory category = null;
			while (!lines.get(index).equals("") && !lines.get(index).startsWith("#")) {
				String cultureLine = lines.get(index);
				if (!cultureLine.startsWith("  *  ")) {
					String[] categoryRow = cultureLine.split("\t");
					category = new CultureCategory(categoryRow[0], categoryRow[1]);
					categories.add(category);
				} else {
					category.addSkillFromLine(cultureLine);
				}
				index++;
			}
		} catch (IndexOutOfBoundsException iobe) {
		}
		return index;
	}

	private int setHobbyRanks(List<String> lines, int index) {
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}
		while (!lines.get(index).equals("") && !lines.get(index).startsWith("#")) {
			String hobbyLine = lines.get(index);
			try {
				hobbyRanks = Integer.parseInt(hobbyLine);
			} catch (NumberFormatException nfe) {
				ShowMessage.showErrorMessage("Error al obtener los rangos de la aficiones cultural: "
						+ hobbyLine, "Añadir aficiones de cultura.");
			}
			index++;
		}
		return index;
	}

	private int setHobbySkillsAndCategories(List<String> lines, int index) {
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}
		while (!lines.get(index).equals("") && !lines.get(index).startsWith("#")) {
			String[] hobbySkillColumns = lines.get(index).split(", ");
			for (String hobby : hobbySkillColumns) {
				// If it is a category.
				if (CategoryFactory.existCategory(hobby)) {
					CultureCategory category = new CultureCategory(hobby, 0);
					hobbyCategories.add(category);
				} else if (SkillFactory.existSkill(hobby)) { // Is a skill.
					CultureSkill skill = new CultureSkill(hobby);
					hobbySkills.add(skill);
				} else { //Not recognized. 
					ShowMessage.showErrorMessage("Aficion no encontrada: " + hobby,
							"Añadir aficiones de cultura.");
				}
			}
			index++;
		}
		return index;
	}

	private int setCultureLanguages(List<String> lines, int index) {
		languages = new ArrayList<>();
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}
		while (!lines.get(index).equals("") && !lines.get(index).startsWith("#")) {
			String[] languageColumn = lines.get(index).split("\t");
			String[] languageRanks = languageColumn[1].split("/");
			try {
				CultureLanguage language = new CultureLanguage(languageColumn[0], languageRanks[0],
						languageRanks[1]);
				languages.add(language);
			} catch (NumberFormatException nfe) {
				ShowMessage.showErrorMessage("Error al obtener los rangos escritos del idioma: " + name,
						"Añadir lenguajes de cultura.");
			}
			index++;
		}
		return index;
	}

	public String getName() {
		return name;
	}

}
