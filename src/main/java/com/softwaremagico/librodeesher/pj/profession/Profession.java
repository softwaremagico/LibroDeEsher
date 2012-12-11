package com.softwaremagico.librodeesher.pj.profession;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

import com.softwaremagico.files.Folder;
import com.softwaremagico.files.RolemasterFolderStructure;
import com.softwaremagico.librodeesher.Categoria;
import com.softwaremagico.librodeesher.Habilidad;
import com.softwaremagico.librodeesher.Magia;
import com.softwaremagico.librodeesher.Personaje;
import com.softwaremagico.librodeesher.gui.MostrarMensaje;
import com.softwaremagico.librodeesher.pj.categories.Category;
import com.softwaremagico.librodeesher.pj.categories.CategoryCost;
import com.softwaremagico.librodeesher.pj.categories.CategoryFactory;
import com.softwaremagico.librodeesher.pj.characteristic.Characteristic;
import com.softwaremagico.librodeesher.pj.characteristic.Characteristics;
import com.softwaremagico.librodeesher.pj.magic.Magic;
import com.softwaremagico.librodeesher.pj.magic.MagicLevelRange;
import com.softwaremagico.librodeesher.pj.magic.MagicListType;
import com.softwaremagico.librodeesher.pj.magic.RealmsOfMagic;
import com.softwaremagico.librodeesher.pj.skills.Skill;
import com.softwaremagico.librodeesher.pj.skills.SkillFactory;
import com.softwaremagico.librodeesher.pj.skills.SkillType;
import com.softwaremagico.librodeesher.pj.training.Training;
import com.softwaremagico.librodeesher.pj.training.TrainingFactory;
import com.softwaremagico.librodeesher.pj.training.TrainingType;

public class Profession {
	private String name;
	private Hashtable<Category, Integer> categoriesBonus;
	private List<Characteristic> characteristicPreferences;
	private List<RealmsOfMagic> magicRealmsAvailable;
	private List<CategoryCost> weaponCategoryCost;
	private Hashtable<Category, CategoryCost> categoryCost;
	private List<Skill> commonSkills;
	private List<ChooseSkillGroup> commonSkillsToChoose;
	private List<Skill> professionalSkills;
	private List<ChooseSkillGroup> professionalSkillsToChoose;
	private List<Skill> restrictedSkills;
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
			lineIndex = setSpecialSkills(lines, lineIndex, commonSkills, commonSkillsToChoose);
			lineIndex = setSpecialSkills(lines, lineIndex, professionalSkills, professionalSkillsToChoose);
			lineIndex = setSpecialSkills(lines, lineIndex, restrictedSkills, restrictedSkillsToChoose);
			lineIndex = setMagicCost(lines, lineIndex);
			lineIndex = setTrainingCosts(lines, lineIndex);
		}
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
					MostrarMensaje.showErrorMessage("Caracteristica " + abbrev + " mostrada en el archivo "
							+ Personaje.getInstance().profesion + ".txt no existente.", "Leer Profesion");
				}
			}
		}
		return index++;
	}

	private int setAvailableMagicRealms(List<String> lines, int index) {
		magicRealmsAvailable = new ArrayList<>();
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}
		while (!lines.get(index).equals("") || !lines.get(index).startsWith("#")) {
			try {
				String realmLine = lines.get(index);
				String[] realmsColumns = realmLine.split(", ");
				for (String realms : realmsColumns) {
					RealmsOfMagic realmType = RealmsOfMagic.getMagicRealm(realms);
					if (realmType != null) {
						magicRealmsAvailable.add(realmType);
					} else {
						MostrarMensaje.showErrorMessage("Problemas con el reino de magia " + lines.get(index)
								+ " mostrada en el archivo " + Personaje.getInstance().profesion + ".txt.",
								"Leer Profesion");
					}
				}
			} catch (Exception e) {
				MostrarMensaje.showErrorMessage("Problemas con el reino de magia " + lines.get(index)
						+ " mostrada en el archivo " + Personaje.getInstance().profesion + ".txt.",
						"Leer Profesion");
			}
			index++;
		}
		return index;
	}

	private int setProfessionBonus(List<String> lines, int index) {
		categoriesBonus = new Hashtable<>();
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}
		while (!lines.get(index).equals("") || !lines.get(index).startsWith("#")) {
			String categoryLine = lines.get(index);
			String[] categoryColumns = categoryLine.split("\t");
			String categoryName = categoryColumns[0];
			Integer bonus = Integer.parseInt(categoryColumns[1]);
			try {
				categoriesBonus.put(CategoryFactory.getAvailableCategory(categoryName), bonus);
			} catch (NullPointerException npe) {
				MostrarMensaje.showErrorMessage("Bonus de " + categoryName + " en "
						+ Personaje.getInstance().profesion + ".txt mal definido.", "Leer Profesion");
			}
			index++;
		}
		return index;
	}

	private int setCategoryCost(List<String> lines, int index) {
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}

		while (!lines.get(index).equals("") || !lines.get(index).startsWith("#")) {
			String categoryLine = lines.get(index);
			String[] categoryColumns = categoryLine.split("\t");
			String categoryName = categoryColumns[0];
			if (categoryName.startsWith("Armas·")) {
				weaponCategoryCost.add(new CategoryCost(categoryColumns[1]));
			} else {
				try {
					Category cat = CategoryFactory.getAvailableCategory(categoryName);
					categoryCost.put(cat, new CategoryCost(categoryColumns[1]));
				} catch (Exception e) {
					MostrarMensaje.showErrorMessage("Categoría mal definida: " + categoryName,
							"Leer Profesion");
				}
			}
			index++;
		}
		return index;
	}

	private int setSpecialSkills(List<String> lines, int index, List<Skill> skillCategory,
			List<ChooseSkillGroup> groupSkillsToChoose) {
		skillCategory = new ArrayList<>();
		groupSkillsToChoose = new ArrayList<>();
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
				// Un grupo de habilidades de una categoria.
				if (skillColumns[i].contains("#")) {
					String[] categoryColumns = skillColumns[i].split("#");
					Category cat = CategoryFactory.getAvailableCategory(categoryColumns[0]);
					if (cat != null) {
						ChooseSkillGroup chooseSkills = new ChooseSkillGroup(
								Integer.parseInt(categoryColumns[1]), cat.getSkills());
						groupSkillsToChoose.add(chooseSkills);
					} else {
						MostrarMensaje.showErrorMessage("Error leyendo una categoría en habilidad común: "
								+ lines.get(index), "Leer Profesión");
					}
				} else if (skillColumns[i].startsWith("{")) { // Una habilidad
																// de un
																// conjunto
					String skillGroup = skillColumns[i].replace("{", "").replace("}", "");
					ChooseSkillGroup chooseSkills = new ChooseSkillGroup(1, skillGroup.split(", "));
					groupSkillsToChoose.add(chooseSkills);
				} else {
					// Una habilidad.
					Skill skill = SkillFactory.getSkill(skillColumns[i]);
					skillCategory.add(skill);
				}
			}
			index++;
		}
		return index;
	}

	private int setMagicCost(List<String> lines, int index) {
		Magic magic = new Magic();
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}
		while (!lines.get(index).equals("")) {
			String listLine = lines.get(index);
			String[] spellsColumn = listLine.split("\t");
			String[] spellList = spellsColumn[0].split(" (");
			String listName = spellList[0].trim();
			String listLevel = spellList[1].replace(")", "");
			String listCost = spellsColumn[1];

			magic.setMagicCost(MagicListType.getMagicType(listName),
					MagicLevelRange.getLevelRange(listLevel), listCost);
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
					Training training = TrainingFactory.getTraining(trainingColumns[0]);
					if (training != null) {
						TrainingCost trainingCost = new TrainingCost(training, cost, type);
						trainingCosts.put(trainingColumns[0], trainingCost);
					} else {
						throw new Exception();
					}
				} catch (Exception e) {
					MostrarMensaje.showErrorMessage(
							"Coste de Adiestramiento mal formado: " + lines.get(index), "Leer Profesión");
				}

				index++;
			}
		}
		return index;
	}
}
