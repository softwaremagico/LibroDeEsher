package com.softwaremagico.librodeesher.pj.profession;

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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import com.softwaremagico.files.Folder;
import com.softwaremagico.files.RolemasterFolderStructure;
import com.softwaremagico.librodeesher.basics.ChooseType;
import com.softwaremagico.librodeesher.basics.ShowMessage;
import com.softwaremagico.librodeesher.pj.categories.Category;
import com.softwaremagico.librodeesher.pj.categories.CategoryCost;
import com.softwaremagico.librodeesher.pj.categories.CategoryFactory;
import com.softwaremagico.librodeesher.pj.characteristic.Characteristic;
import com.softwaremagico.librodeesher.pj.characteristic.Characteristics;
import com.softwaremagico.librodeesher.pj.magic.MagicLevelRange;
import com.softwaremagico.librodeesher.pj.magic.MagicListType;
import com.softwaremagico.librodeesher.pj.skills.ChooseSkillGroup;
import com.softwaremagico.librodeesher.pj.skills.Skill;
import com.softwaremagico.librodeesher.pj.skills.SkillFactory;
import com.softwaremagico.librodeesher.pj.training.TrainingType;

public class Profession {
	private final static Integer HOW_MANY_EXTRA_COSTS = 5;
	private String name;
	private HashMap<String, Integer> categoriesBonus;
	private HashMap<String, Integer> skillBonus;
	private List<String> characteristicPreferences;
	private List<ProfessionalRealmsOfMagicOptions> magicRealmsAvailable;
	private List<CategoryCost> weaponCategoryCost;
	private List<CategoryCost> extraWeaponCategoryCost; // For firearms or any
														// new weapon category
	private HashMap<String, CategoryCost> categoryCost;
	private List<ChooseSkillGroup> commonSkillsToChoose;
	private List<ChooseSkillGroup> professionalSkillsToChoose;
	private List<ChooseSkillGroup> restrictedSkillsToChoose;
	private List<String> commonSkills;
	private List<String> professionalSkills;
	private List<String> restrictedSkills;

	private MagicCosts magicCosts;
	private HashMap<String, Integer> trainingCosts;
	private HashMap<String, TrainingType> trainingTypes;

	public Profession(String name) {
		this.name = name;
		commonSkillsToChoose = new ArrayList<>();
		professionalSkillsToChoose = new ArrayList<>();
		restrictedSkillsToChoose = new ArrayList<>();
		commonSkills = new ArrayList<>();
		professionalSkills = new ArrayList<>();
		restrictedSkills = new ArrayList<>();

		try {
			readProfessionFile(name);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<ChooseSkillGroup> getCommonSkillsToChoose() {
		return commonSkillsToChoose;
	}

	public List<ChooseSkillGroup> getProfessionalSkillsToChoose() {
		return professionalSkillsToChoose;
	}

	public List<ChooseSkillGroup> getRestrictedSkillsToChoose() {
		return restrictedSkillsToChoose;
	}

	public boolean isCharacteristicProfessional(Characteristic characteristic) {
		if (characteristicPreferences.size() > 0
				&& characteristic.getAbbreviature().equals(characteristicPreferences.get(0))) {
			return true;
		}
		if (characteristicPreferences.size() > 1
				&& characteristic.getAbbreviature().equals(characteristicPreferences.get(1))) {
			return true;
		}
		return false;
	}

	public Integer getCategoryBonus(String categoryName) {
		Integer bonus = categoriesBonus.get(categoryName);
		if (bonus == null) {
			return 0;
		}
		return bonus;
	}

	public Integer getSkillBonus(String skillName) {
		Integer bonus = skillBonus.get(skillName);
		if (bonus == null) {
			return 0;
		}
		return bonus;
	}

	private void readProfessionFile(String professionName) throws Exception {
		int lineIndex = 0;

		String professionFile = RolemasterFolderStructure
				.getDirectoryModule(ProfessionFactory.PROFESSION_FOLDER + File.separator + professionName
						+ ".txt");
		if (professionFile.length() > 0) {
			List<String> lines = Folder.readFileLines(professionFile, false);
			lineIndex = setBasicCharacteristics(lines, lineIndex);
			lineIndex = setMagicRealmsAvailable(lines, lineIndex);
			lineIndex = setProfessionBonus(lines, lineIndex);
			lineIndex = setCategoryCost(lines, lineIndex);
			lineIndex = setSpecialSkills(lines, lineIndex, commonSkills, commonSkillsToChoose,
					ChooseType.COMMON);
			lineIndex = setSpecialSkills(lines, lineIndex, professionalSkills, professionalSkillsToChoose,
					ChooseType.PROFESSIONAL);
			lineIndex = setSpecialSkills(lines, lineIndex, restrictedSkills, restrictedSkillsToChoose,
					ChooseType.RESTRICTED);
			lineIndex = setMagicCost(lines, lineIndex);
			lineIndex = setTrainingCosts(lines, lineIndex);
		}
	}

	public CategoryCost getMagicCost(MagicListType listType, Integer currentListranks) {
		return magicCosts.getListCost(listType, MagicLevelRange.getLevelRange(currentListranks + 1));
	}

	public CategoryCost getMagicCost(MagicListType listType, MagicLevelRange levelRange) {
		return magicCosts.getListCost(listType, levelRange);
	}

	private int setBasicCharacteristics(List<String> lines, int index) {
		characteristicPreferences = new ArrayList<>();
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}
		String preferenciasCaracteristicas = lines.get(index);
		if (!lines.get(index).toLowerCase().contains("indiferente")) {
			String[] characteristicsTags = preferenciasCaracteristicas.split(" ");

			for (String abbrev : characteristicsTags) {
				if (Characteristics.isCharacteristicValid(abbrev)) {
					characteristicPreferences.add(abbrev);
				} else {
					ShowMessage.showErrorMessage("Caracteristica " + abbrev + " mostrada en el archivo "
							+ name + ".txt no existente.", "Leer Profesion");
				}
			}
		}
		return ++index;
	}

	private int setMagicRealmsAvailable(List<String> lines, int index) {
		magicRealmsAvailable = new ArrayList<>();
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}
		while (!lines.get(index).equals("") && !lines.get(index).startsWith("#")) {
			try {
				String realmLine = lines.get(index);
				String[] realmsColumns = realmLine.split(", ");
				for (String realms : realmsColumns) {
					ProfessionalRealmsOfMagicOptions realmMagicOptions = new ProfessionalRealmsOfMagicOptions();
					realmMagicOptions.add(realms, name);
					magicRealmsAvailable.add(realmMagicOptions);
				}
			} catch (Exception e) {
				ShowMessage.showErrorMessage("Problemas con el reino de magia " + lines.get(index)
						+ " mostrada en el archivo " + name + ".txt.", "Leer Profesion");
			}
			index++;
		}
		return index;
	}

	private int setProfessionBonus(List<String> lines, int index) {
		categoriesBonus = new HashMap<>();
		skillBonus = new HashMap<>();
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}
		while (!lines.get(index).equals("") && !lines.get(index).startsWith("#")) {
			String bonusLine = lines.get(index);
			String[] categoryOrSkillColumns = bonusLine.split("\t");
			String categoryOrSkillName = categoryOrSkillColumns[0];
			Integer bonus = Integer.parseInt(categoryOrSkillColumns[1]);
			try {
				if (CategoryFactory.getCategory(categoryOrSkillName) != null) {
					categoriesBonus.put(categoryOrSkillName, bonus);
				} else if (SkillFactory.getAvailableSkill(categoryOrSkillName) != null) {
					skillBonus.put(categoryOrSkillName, bonus);
				}
			} catch (NullPointerException npe) {
				ShowMessage.showErrorMessage("Bonus de " + categoryOrSkillName + " en " + name
						+ ".txt mal definido.", "Leer Profesion");
			}
			index++;
		}
		return index;
	}

	private int setCategoryCost(List<String> lines, int index) {
		categoryCost = new HashMap<>();
		weaponCategoryCost = new ArrayList<>();
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}

		while (!lines.get(index).equals("") && !lines.get(index).startsWith("#")) {
			String categoryLine = lines.get(index);
			String[] categoryColumns = categoryLine.split("\t");
			String categoryName = categoryColumns[0];
			if (categoryName.startsWith("Armas·")) {
				weaponCategoryCost.add(new CategoryCost(categoryColumns[1]));
			} else {
				try {
					Category cat = CategoryFactory.getCategory(categoryName);
					categoryCost.put(cat.getName(), new CategoryCost(categoryColumns[1]));
				} catch (Exception e) {
					e.printStackTrace();
					ShowMessage.showErrorMessage("Categoría mal definida: " + categoryName, "Leer Profesion");
				}
			}
			index++;
		}
		createExtraWeaponsCosts();
		Collections.sort(weaponCategoryCost, new CategoryCostComparator());
		return index;
	}

	private void createExtraWeaponsCosts() {
		extraWeaponCategoryCost = new ArrayList<>();
		for (int i = 0; i < HOW_MANY_EXTRA_COSTS; i++) {
			extraWeaponCategoryCost.add(new CategoryCost(weaponCategoryCost
					.get(weaponCategoryCost.size() - 1).getRankCost()));
		}
	}

	/**
	 * Add some extra weaponCost for new Weapons Categories
	 * 
	 */
	public void extendCategoryCost(boolean fireArmsEnabled) {
		Integer totalCategories = CategoryFactory.getWeaponsCategory().size();
		int extraCostAdded = 0;
		// Extend if it is necessary.
		for (int i = weaponCategoryCost.size(); i < totalCategories; i++) {
			weaponCategoryCost.add(extraWeaponCategoryCost.get(extraCostAdded));
			extraCostAdded++;
		}
		Collections.sort(weaponCategoryCost, new CategoryCostComparator());

		// Calculate real number of allowed categories.
		if (!fireArmsEnabled) {
			totalCategories -= 2;
		}
		// Delete excessive categories costs.
		for (int i = CategoryFactory.getWeaponsCategory().size() - 1; i >= totalCategories; i--) {
			weaponCategoryCost.remove(i);
		}
	}

	public CategoryCost getCategoryCost(String categoryName) {
		try {
			CategoryCost cost = categoryCost.get(categoryName);
			return cost;
		} catch (NullPointerException npe) {
			return null;
		}
	}

	public CategoryCost getWeaponsCategoryCost(Integer index) {
		try {
			CategoryCost cost = weaponCategoryCost.get(index);
			return cost;
		} catch (NullPointerException npe) {
			return null;
		}
	}

	private int setSpecialSkills(List<String> lines, int index, List<String> groupSkills,
			List<ChooseSkillGroup> groupSkillsToChoose, ChooseType chooseType) {
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}

		while (!lines.get(index).equals("") && !lines.get(index).startsWith("#")) {
			String skillLine = lines.get(index);
			if (skillLine.toLowerCase().contains("ninguna") || skillLine.toLowerCase().contains("nothing")) {
				return ++index;
			}
			String[] skillColumns = skillLine.split(", ");
			for (int i = 0; i < skillColumns.length; i++) {
				// A skill from category.
				if (skillColumns[i].contains("#")) {
					String[] categoryColumns = skillColumns[i].split("#");
					Category cat = CategoryFactory.getCategory(categoryColumns[0]);
					if (cat != null) {
						ChooseSkillGroup chooseSkills = new ChooseSkillGroup(
								Integer.parseInt(categoryColumns[1]), cat.getSkills(), chooseType);
						groupSkillsToChoose.add(chooseSkills);
					} else {
						ShowMessage.showErrorMessage("Error leyendo una categoría en habilidad común: "
								+ lines.get(index), "Leer Profesión");
					}
					// One skill of a set
				} else if (skillColumns[i].startsWith("{")) {
					String skillGroup = skillColumns[i].replace("{", "").replace("}", "");
					ChooseSkillGroup chooseSkills = new ChooseSkillGroup(1, skillGroup.replace(";", ",")
							.split(", "), chooseType);
					groupSkillsToChoose.add(chooseSkills);
				} else {
					// One skill.
					// Skill skill = SkillFactory.getSkill(skillColumns[i]);
					// ChooseSkillGroup chooseSkills = new ChooseSkillGroup(1,
					// skill, chooseType);
					// groupSkillsToChoose.add(chooseSkills);
					groupSkills.add(skillColumns[i]);
				}
			}
			index++;
		}
		return index;
	}

	private int setMagicCost(List<String> lines, int index) {
		magicCosts = new MagicCosts();
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}
		while (!lines.get(index).equals("")) {
			try {
				String listLine = lines.get(index);
				String[] spellsColumn = listLine.split("\t");
				String pattern = Pattern.quote(" (");
				String[] spellList = spellsColumn[0].split(pattern);
				String listName = spellList[0].trim();
				String listLevel = spellList[1].replace(")", "");
				String listCost = spellsColumn[1];

				magicCosts.setMagicCost(MagicListType.getMagicType(listName),
						MagicLevelRange.getLevelRange(listLevel), listCost);
			} catch (Exception e) {
				ShowMessage.showErrorMessage("Coste de magia mal formado: " + lines.get(index),
						"Leer Profesión");
			}
			index++;
		}
		return index;
	}

	private int setTrainingCosts(List<String> lines, int index) {
		trainingCosts = new HashMap<>();
		trainingTypes = new HashMap<>();
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}
		while (!lines.get(index).equals("")) {
			while (!lines.get(index).equals("")) {
				String trainingLine = lines.get(index);
				String[] trainingColumns = trainingLine.split("\t");

				TrainingType type;
				if (trainingColumns[1].contains("+")) {
					type = TrainingType.FAVOURITE;
				} else if (trainingColumns[1].contains("-")) {
					type = TrainingType.FORBIDDEN;
				} else {
					type = TrainingType.STANDAR;
				}

				try {
					Integer cost = Integer.parseInt(trainingColumns[1].replace("+", "").replace("-", "")
							.trim());
					trainingCosts.put(trainingColumns[0], cost);
					trainingTypes.put(trainingColumns[0], type);
				} catch (Exception e) {
					ShowMessage.showErrorMessage("Coste de Adiestramiento mal formado: " + lines.get(index),
							"Leer Profesión");
				}

				index++;
			}
		}
		return index;
	}

	public String getName() {
		return name;
	}

	public List<ProfessionalRealmsOfMagicOptions> getMagicRealmsAvailable() {
		return magicRealmsAvailable;
	}

	public List<CategoryCost> getWeaponCategoryCost() {
		return weaponCategoryCost;
	}

	public class CategoryCostComparator implements Comparator<CategoryCost> {

		@Override
		public int compare(CategoryCost o1, CategoryCost o2) {
			if (o1.getMaxRanksPerLevel() > o2.getMaxRanksPerLevel()) {
				return -1;
			} else if (o1.getMaxRanksPerLevel() < o2.getMaxRanksPerLevel()) {
				return 1;
			} else if (o1.getRankCost(0) < o2.getRankCost(0)) {
				return -1;
			} else if (o1.getRankCost(0) > o2.getRankCost(0)) {
				return 1;
			} else if (o1.getMaxRanksPerLevel() > 1 && o1.getRankCost(1) < o2.getRankCost(1)) {
				return -1;
			} else if (o1.getMaxRanksPerLevel() > 1 && o1.getRankCost(1) > o2.getRankCost(1)) {
				return 1;
			} else if (o1.getMaxRanksPerLevel() > 2 && o1.getRankCost(2) < o2.getRankCost(2)) {
				return -1;
			} else if (o1.getMaxRanksPerLevel() > 2 && o1.getRankCost(2) > o2.getRankCost(2)) {
				return 1;
			} else {
				return 0;
			}
		}
	}

	public boolean isCommon(Skill skill) {
		try {
			return commonSkills.contains(skill.getName());
		} catch (NullPointerException npe) {
			return false;
		}
	}

	public boolean isRestricted(Skill skill) {
		try {
			return restrictedSkills.contains(skill.getName());
		} catch (NullPointerException npe) {
			return false;
		}
	}

	public boolean isProfessional(Skill skill) {
		try {
			return professionalSkills.contains(skill.getName());
		} catch (NullPointerException npe) {
			return false;
		}
	}

	public Integer getTrainingCost(String trainingName) {
		Integer cost = trainingCosts.get(trainingName);
		if (cost != null) {
			return cost;
		}
		return Integer.MAX_VALUE;
	}
}
