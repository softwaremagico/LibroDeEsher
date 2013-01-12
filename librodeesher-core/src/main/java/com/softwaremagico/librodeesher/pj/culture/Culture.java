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
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;

import com.softwaremagico.files.Folder;
import com.softwaremagico.files.RolemasterFolderStructure;
import com.softwaremagico.librodeesher.core.ShowMessage;
import com.softwaremagico.librodeesher.core.Spanish;
import com.softwaremagico.librodeesher.pj.Language;
import com.softwaremagico.librodeesher.pj.LanguageComparator;
import com.softwaremagico.librodeesher.pj.categories.Category;
import com.softwaremagico.librodeesher.pj.categories.CategoryFactory;
import com.softwaremagico.librodeesher.pj.skills.Skill;
import com.softwaremagico.librodeesher.pj.skills.SkillFactory;
import com.softwaremagico.librodeesher.pj.weapons.Weapon;
import com.softwaremagico.librodeesher.pj.weapons.WeaponFactory;

public class Culture {
	private String name;
	private List<Weapon> cultureWeapons;
	private List<String> cultureArmours;
	private Hashtable<String, CultureCategory> categories;
	private Integer hobbyRanks;
	private List<String> hobbySkills;
	// private List<CultureLanguage> languages;
	private Hashtable<String, Language> languages;

	public Culture(String name) {
		this.name = name;
		try {
			readCultureFile(name);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Language> getLanguages() {
		List<Language> sortedLanguages = new ArrayList<Language>(languages.values());
		Collections.sort(sortedLanguages, new LanguageComparator());
		return sortedLanguages;
	}

	public List<String> getHobbySkills() {
		return hobbySkills;
	}

	public List<Weapon> getCultureWeapons() {
		return cultureWeapons;
	}

	public Integer getCultureRanks(Category category) {
		for (String categoryName : categories.keySet()) {
			if (categoryName.equals(category.getName())) {
				return categories.get(categoryName).getRanks();
			}
		}
		return 0;
	}

	public Integer getCultureRanks(Skill skill) {
		Integer total = 0;
		// Culture ranks
		for (CultureCategory cultureCategory : categories.values()) {
			for (CultureSkill cultureSkill : cultureCategory.getSkills()) {
				if (cultureSkill.getName().equals(skill.getName())) {
					total += cultureSkill.getRanks();
				}
			}
		}
		return total;
	}

	private void readCultureFile(String cultureName) throws Exception {
		int lineIndex = 0;
		String cultureFile = RolemasterFolderStructure.getDirectoryModule(CultureFactory.CULTURE_FOLDER
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
						Weapon weapon = WeaponFactory.getWeapon(weaponName);
						if (weapon != null) {
							cultureWeapons.add(weapon);
						}
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
		categories = new Hashtable<>();
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
				ShowMessage.showErrorMessage(
						"Error al obtener los rangos de la aficiones culturales.\n Línea: " + hobbyLine
								+ " en cultura" + getName(), "Añadir aficiones de cultura");
			}
			index++;
		}
		return index;
	}

	private int setHobbySkillsAndCategories(List<String> lines, int index) {
		hobbySkills = new ArrayList<>();
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}
		while (!lines.get(index).equals("") && !lines.get(index).startsWith("#")) {
			String[] hobbySkillColumns = lines.get(index).split(", ");
			for (String hobby : hobbySkillColumns) {
				// If it is a category.
				Category category;
				if ((category = CategoryFactory.getAvailableCategory(hobby)) != null) {
					for (Skill skill : category.getSkills()) {
						// CultureSkill cultureSkill = new
						// CultureSkill(skill.getName());
						hobbySkills.add(skill.getName());
					}
					// Is a skill.
				} else if (SkillFactory.existSkill(hobby)) {
					hobbySkills.add(hobby);
					// It is a special tag for a group of skills. Add it.
				} else if (hobby.toLowerCase().equals(Spanish.CULTURE_WEAPON)
						|| hobby.toLowerCase().equals(Spanish.CULTURE_ARMOUR)
						|| hobby.toLowerCase().equals(Spanish.CULTURE_SPELLS)) {
					hobbySkills.add(hobby);
					// Is a culture skill: add it;
				} else if (hobby.contains("Conocimiento de la Fauna")
						|| hobby.contains("Conocimiento de la Flora")
						|| hobby.contains("Conocimiento Cultural") || hobby.contains("Conocimiento Regional")) {
					Category cat = CategoryFactory.getAvailableCategory("Conocimiento·General");
					cat.addSkill(hobby);
					// CultureSkill skill = new CultureSkill(hobby);
					hobbySkills.add(hobby);
				} else if (hobby.contains("Supervivencia")) {
					Category cat = CategoryFactory.getAvailableCategory("Exteriores·Entorno");
					cat.addSkill(hobby);
					// CultureSkill skill = new CultureSkill(hobby);
					hobbySkills.add(hobby);
				} else { // Not recognized.
					ShowMessage.showErrorMessage("Aficion no encontrada en cultura \"" + getName() + "\": "
							+ hobby, "Añadir aficiones de cultura");
				}
			}
			index++;
		}
		Collections.sort(hobbySkills);
		return index;
	}

	private int setCultureLanguages(List<String> lines, int index) {
		languages = new Hashtable<>();
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}
		while (!lines.get(index).equals("") && !lines.get(index).startsWith("#")) {
			String[] languageColumn = lines.get(index).split("\t");
			String[] languageRanks = languageColumn[1].split("/");
			try {
				Language language = new Language(Language.SPOKEN_TAG + " " + languageColumn[0],
						Integer.parseInt(languageRanks[0]));
				languages.put(language.getName(), language);
				language = new Language(Language.WRITTEN_TAG + " " + languageColumn[0],
						Integer.parseInt(languageRanks[1]));
				languages.put(language.getName(), language);
			} catch (NumberFormatException nfe) {
				ShowMessage.showErrorMessage("Error al obtener los rangos escritos del idioma: " + name,
						"Añadir lenguajes de cultura");
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

	public Integer getLanguageRank(Language language) {
		Language langCult = languages.get(language.getName());
		if (langCult == null) {
			return 0;
		}
		return langCult.getRanks();
	}

	public List<String> getCultureArmours() {
		return cultureArmours;
	}
}
