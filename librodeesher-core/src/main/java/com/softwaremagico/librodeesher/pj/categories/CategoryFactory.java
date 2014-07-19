package com.softwaremagico.librodeesher.pj.categories;

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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.softwaremagico.files.RolemasterFolderStructure;
import com.softwaremagico.librodeesher.basics.ShowMessage;
import com.softwaremagico.librodeesher.basics.Spanish;
import com.softwaremagico.librodeesher.pj.culture.CultureFactory;
import com.softwaremagico.librodeesher.pj.skills.Skill;
import com.softwaremagico.librodeesher.pj.skills.SkillFactory;
import com.softwaremagico.librodeesher.pj.weapons.Weapon;
import com.softwaremagico.librodeesher.pj.weapons.WeaponFactory;
import com.softwaremagico.librodeesher.pj.weapons.WeaponType;
import com.softwaremagico.log.Log;

public class CategoryFactory {

	private static Map<String, Category> availableCategories = new HashMap<>();
	private static List<Category> weaponsCategory = new ArrayList<>();
	private static List<String> availableCategoriesByName = new ArrayList<>();

	static {
		try {
			getCategoriesFromFiles();
		} catch (Exception e) {
			ShowMessage.showErrorMessage("Error al obtener las categorías y habilidades.", "Categorías");
			e.printStackTrace();
		}
	}

	public static List<String> getAvailableCategories() {
		return availableCategoriesByName;
	}

	private static void addCultureSkills() {
		Category cat = availableCategories.get(Spanish.GENERAL_KNOWLEDGE_TAG);
		for (String culture : CultureFactory.availableCultures()) {
			if (!culture.contains("(")) {
				cat.addSkill(Spanish.FAUNA_KNOWNLEDGE_TAG + " (" + culture + ")");
				cat.addSkill(Spanish.FLORA_KNOWNLEDGE_TAG + " (" + culture + ")");
				cat.addSkill(Spanish.CULTURAL_KNOWNLEDGE_TAG + " (" + culture + ")");
				cat.addSkill(Spanish.REGIONAL_KNOWNLEDGE_TAG + " (" + culture + ")");
			}
		}
	}

	private static void addWeaponsAsSkills() {
		for (WeaponType weaponType : WeaponType.values()) {
			List<Weapon> weaponsOfType = WeaponFactory.getWeaponsByType(weaponType);
			Category categoryOfWeapon = availableCategories.get(weaponType.getWeaponCategoryName());
			weaponsCategory.add(categoryOfWeapon);
			try{
			categoryOfWeapon.setSkills(convertWeaponsToSkills(weaponsOfType));
			}catch(NullPointerException npe){
				Log.errorMessage(CategoryFactory.class.getName(), npe);
			}
		}

	}

	public static List<Skill> convertWeaponsToSkills(List<Weapon> weapons) {
		List<Skill> skills = new ArrayList<>();
		for (Weapon weapon : weapons) {
			skills.add(SkillFactory.getSkill(weapon.getName()));
		}
		return skills;
	}

	public static Category createCategory(String categoryName, String abbreviature,
			String characteristicsTag, String type, String skills) {
		CategoryType catType = CategoryType.getCategoryType(type);
		Category cat;
		switch (catType) {
		case STANDARD:
			cat = new StandardCategory(categoryName, abbreviature, characteristicsTag);
			break;
		case COMBINED:
			cat = new CombinedCategory(categoryName, abbreviature, characteristicsTag);
			break;
		case PPD:
			cat = new PpdCategory(categoryName, abbreviature, characteristicsTag);
			break;
		case PD:
			cat = new FdCategory(categoryName, abbreviature, characteristicsTag);
			break;
		case LIMITED:
			cat = new LimitedCategory(categoryName, abbreviature, characteristicsTag);
			break;
		default:
			return null;
		}
		if (skills != null) {
			cat.addSkills(skills);
		}
		return cat;
	}

	public static boolean existCategory(String categoryName) {
		return (getCategory(categoryName) != null);
	}

	public static List<Category> getCategories() {
		List<Category> categories = new ArrayList<>(availableCategories.values());
		Collections.sort(categories, new CategoryComparatorByName());
		return categories;
	}

	public static Category getCategory(String categoryName) {
		Category category = availableCategories.get(categoryName);
		if (category != null) {
			return category;
		}
		// Category name in different case;
		for (Category availableCategory : availableCategories.values()) {
			if (availableCategory.getName().toLowerCase().equals(categoryName.toLowerCase())) {
				return availableCategory;
			}
		}
		return null;
	}

	public static Category getCategory(String categoryPrefix, String containText) {
		for (Category availableCategory : availableCategories.values()) {
			if (availableCategory.getName().toLowerCase().startsWith(categoryPrefix)
					&& availableCategory.getName().toLowerCase().contains((containText))) {
				return availableCategory;
			}
		}
		return null;
	}

	/**
	 * Lee el fichero de categorías.
	 */
	public static void getCategoriesFromFiles() throws Exception {
		List<String> categoriesFile = RolemasterFolderStructure.getAvailableCategoriesFiles();
		for (int j = 0; j < categoriesFile.size(); j++) {
			List<String> lines = RolemasterFolderStructure.getCategoryFile(categoriesFile.get(j));

			for (String oneLine : lines) {
				if (!oneLine.startsWith("#")) {
					String[] descomposed_line = oneLine.split("\t");
					String[] categoryAbbrevName = descomposed_line[0].split("\\(");
					String categoryName = categoryAbbrevName[0];
					try {
						String abrevCat = categoryAbbrevName[1].replace(")", "");
						Category cat = availableCategories.get(categoryName);
						if (cat == null) {
							cat = createCategory(categoryName, abrevCat, descomposed_line[1],
									descomposed_line[2], descomposed_line[3]);
							availableCategories.put(categoryName, cat);
							availableCategoriesByName.add(categoryName);
						} else {
							cat.addSkills(descomposed_line[3]);
						}
					} catch (ArrayIndexOutOfBoundsException aiofb) {
						ShowMessage.showErrorMessage("Abreviatura de categoria mal definida en "
								+ categoryName, "Lectura de Categorías");
					}
				}
			}
		}
		// Replace dummy skills with real obtained from other files.
		addWeaponsAsSkills();

		// Create culture specific skills
		addCultureSkills();

		Collections.sort(availableCategoriesByName);
	}

	public static Category getCategory(String categoryName, String abbrev, String characteristics,
			String type, String skills) throws Exception {

		Category cat = availableCategories.get(categoryName);
		if (cat == null) {
			cat = createCategory(categoryName, abbrev, characteristics, type, skills);
			availableCategories.put(categoryName, cat);
			availableCategoriesByName.add(categoryName);
			Collections.sort(availableCategoriesByName);
		}
		return cat;
	}

	public static List<Category> getWeaponsCategories() {
		List<Category> weaponsCategories = new ArrayList<>();

		for (Category category : availableCategories.values()) {
			if (category.getCategoryGroup().equals(CategoryGroup.WEAPON)) {
				weaponsCategories.add(category);
			}
		}

		Collections.sort(weaponsCategories, new CategoryComparatorByName());
		return weaponsCategories;
	}

	public static List<Category> getWeaponsCategory() {
		return weaponsCategory;
	}

}
