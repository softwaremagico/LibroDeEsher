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
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Pattern;

import com.softwaremagico.files.Folder;
import com.softwaremagico.files.RolemasterFolderStructure;
import com.softwaremagico.librodeesher.gui.ShowMessage;
import com.softwaremagico.librodeesher.pj.categories.Category;
import com.softwaremagico.librodeesher.pj.categories.CategoryCost;
import com.softwaremagico.librodeesher.pj.categories.CategoryFactory;
import com.softwaremagico.librodeesher.pj.characteristic.Characteristic;
import com.softwaremagico.librodeesher.pj.characteristic.Characteristics;
import com.softwaremagico.librodeesher.pj.magic.Magic;
import com.softwaremagico.librodeesher.pj.magic.MagicLevelRange;
import com.softwaremagico.librodeesher.pj.magic.MagicListType;
import com.softwaremagico.librodeesher.pj.magic.RealmOfMagic;
import com.softwaremagico.librodeesher.pj.skills.ChooseSkillGroup;
import com.softwaremagico.librodeesher.pj.skills.Skill;
import com.softwaremagico.librodeesher.pj.skills.SkillFactory;
import com.softwaremagico.librodeesher.pj.training.TrainingType;

public class Profession {
	private String name;
	private Hashtable<String, Integer> categoriesBonus;
	private Hashtable<String, Integer> skillBonus;
	private List<Characteristic> characteristicPreferences;
	private List<RealmOfMagic> magicRealmsAvailable;
	private List<CategoryCost> weaponCategoryCost;
	private Hashtable<String, CategoryCost> categoryCost;
	private List<ChooseSkillGroup> commonSkillsToChoose;
	private List<ChooseSkillGroup> professionalSkillsToChoose;
	private List<ChooseSkillGroup> restrictedSkillsToChoose;
	private Magic magic;
	private Hashtable<String, TrainingCost> trainingCosts;

	public Profession(String name) {
		this.name = name;
		try {
			readProfessionFile(name);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isCharacteristicProfessional(Characteristic characteristic) {
		if (characteristicPreferences.size() > 0 && characteristic.equals(characteristicPreferences.get(0))) {
			return true;
		}
		if (characteristicPreferences.size() > 1 && characteristic.equals(characteristicPreferences.get(1))) {
			return true;
		}
		return false;
	}

	private void readProfessionFile(String professionName) throws Exception {
		int lineIndex = 0;

		String professionFile = RolemasterFolderStructure
				.searchDirectoryModule(ProfessionFactory.PROFESSION_FOLDER + File.separator + professionName
						+ ".txt");
		if (professionFile.length() > 0) {
			List<String> lines = Folder.readFileLines(professionFile, false);
			lineIndex = setBasicCharacteristics(lines, lineIndex);
			lineIndex = setAvailableMagicRealms(lines, lineIndex);
			lineIndex = setProfessionBonus(lines, lineIndex);
			lineIndex = setCategoryCost(lines, lineIndex);
			lineIndex = setSpecialSkills(lines, lineIndex, commonSkillsToChoose);
			lineIndex = setSpecialSkills(lines, lineIndex, professionalSkillsToChoose);
			lineIndex = setSpecialSkills(lines, lineIndex, restrictedSkillsToChoose);
			lineIndex = setMagicCost(lines, lineIndex);
			lineIndex = setTrainingCosts(lines, lineIndex);
		}
	}

	public Magic getMagic() {
		return magic;
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
					characteristicPreferences.add(new Characteristic(abbrev));
				} else {
					ShowMessage.showErrorMessage("Caracteristica " + abbrev + " mostrada en el archivo "
							+ name + ".txt no existente.", "Leer Profesion");
				}
			}
		}
		return ++index;
	}

	private int setAvailableMagicRealms(List<String> lines, int index) {
		magicRealmsAvailable = new ArrayList<>();
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}
		while (!lines.get(index).equals("") && !lines.get(index).startsWith("#")) {
			try {
				String realmLine = lines.get(index);
				String[] realmsColumns = realmLine.split(", ");
				for (String realms : realmsColumns) {
					RealmOfMagic realmType = RealmOfMagic.getMagicRealm(realms);
					if (realmType != null) {
						magicRealmsAvailable.add(realmType);
					} else {
						ShowMessage.showErrorMessage("Problemas con el reino de magia " + lines.get(index)
								+ " mostrada en el archivo " + name + ".txt.", "Leer Profesion");
					}
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
		categoriesBonus = new Hashtable<>();
		skillBonus = new Hashtable<>();
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}
		while (!lines.get(index).equals("") && !lines.get(index).startsWith("#")) {
			String bonusLine = lines.get(index);
			String[] categoryOrSkillColumns = bonusLine.split("\t");
			String categoryOrSkillName = categoryOrSkillColumns[0];
			Integer bonus = Integer.parseInt(categoryOrSkillColumns[1]);
			try {
				if (CategoryFactory.getAvailableCategory(categoryOrSkillName) != null) {
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
		categoryCost = new Hashtable<>();
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
					Category cat = CategoryFactory.getAvailableCategory(categoryName);
					categoryCost.put(cat.getName(), new CategoryCost(categoryColumns[1]));
				} catch (Exception e) {
					e.printStackTrace();
					ShowMessage.showErrorMessage("Categoría mal definida: " + categoryName, "Leer Profesion");
				}
			}
			index++;
		}
		addRepeatedCategoryCost();
		Collections.sort(weaponCategoryCost, new CategoryCostComparator());
		return index;
	}

	/**
	 * Add some extra weaponCost for new Weapons Categories
	 * 
	 */
	private void addRepeatedCategoryCost() {
		for (int i = weaponCategoryCost.size(); i < CategoryFactory.getWeaponsCategory().size(); i++) {
			weaponCategoryCost.add(weaponCategoryCost.get(weaponCategoryCost.size() - 1));
		}
	}

	/*
	 * public String getCategoryCostTag(String categoryName) { try { String
	 * cost; if (categoryName.contains("Armas·")) { // TODO seleccionar el grupo
	 * de armas correspondiente. cost =
	 * weaponCategoryCost.get("Armas·Categoría1").getCostTag(); } else { cost =
	 * categoryCost.get(categoryName).getCostTag(); } if (cost != null) { return
	 * cost; } else { return ""; } } catch (NullPointerException npe) { return
	 * ""; } }
	 */

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

	public Integer getCategoryRanksCost(String categoryName, Integer rank) {
		CategoryCost cost = categoryCost.get(categoryName);
		if (cost == null) {
			return Integer.MAX_VALUE;
		}
		return cost.getTotalRanksCost(rank);
	}

	public Integer getMaxRanksPerLevel(String categoryName) {
		try {
			Integer ranks;
			if (categoryName.contains("Armas·")) {
				// TODO seleccionar el grupo de armas correspondiente.
				ranks = weaponCategoryCost.get(0).getMaxRanksPerLevel();
			} else {
				ranks = categoryCost.get(categoryName).getMaxRanksPerLevel();
			}
			if (ranks != null) {
				return ranks;
			} else {
				return 0;
			}
		} catch (NullPointerException npe) {
			return 0;
		}
	}

	private int setSpecialSkills(List<String> lines, int index, List<ChooseSkillGroup> groupSkillsToChoose) {
		groupSkillsToChoose = new ArrayList<>();
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
				// Un grupo de habilidades de una categoria.
				if (skillColumns[i].contains("#")) {
					String[] categoryColumns = skillColumns[i].split("#");
					Category cat = CategoryFactory.getAvailableCategory(categoryColumns[0]);
					if (cat != null) {
						ChooseSkillGroup chooseSkills = new ChooseSkillGroup(
								Integer.parseInt(categoryColumns[1]), cat.getSkills());
						groupSkillsToChoose.add(chooseSkills);
					} else {
						ShowMessage.showErrorMessage("Error leyendo una categoría en habilidad común: "
								+ lines.get(index), "Leer Profesión");
					}
				} else if (skillColumns[i].startsWith("{")) { // Una habilidad
																// de un
																// conjunto
					String skillGroup = skillColumns[i].replace("{", "").replace("}", "");
					ChooseSkillGroup chooseSkills = new ChooseSkillGroup(1, skillGroup.replace(";", ",")
							.split(", "));
					groupSkillsToChoose.add(chooseSkills);
				} else {
					// Una habilidad.
					Skill skill = SkillFactory.getSkill(skillColumns[i]);
					ChooseSkillGroup chooseSkills = new ChooseSkillGroup(1, skill);
					groupSkillsToChoose.add(chooseSkills);
				}
			}
			index++;
		}
		return index;
	}

	private int setMagicCost(List<String> lines, int index) {
		magic = new Magic();
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

				magic.setMagicCost(MagicListType.getMagicType(listName),
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
		trainingCosts = new Hashtable<>();
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
					TrainingCost trainingCost = new TrainingCost(trainingColumns[0], cost, type);
					trainingCosts.put(trainingColumns[0], trainingCost);
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

	public List<RealmOfMagic> getMagicRealmsAvailable() {
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
}
