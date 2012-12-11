package com.softwaremagico.librodeesher.pj.categories;

import java.util.Hashtable;
import java.util.List;

import com.softwaremagico.files.RolemasterFolderStructure;
import com.softwaremagico.librodeesher.gui.MostrarMensaje;

public class CategoryFactory {

	private static Hashtable<String, Category> categoriesAvailable = new Hashtable<>();

	static {
		try {
			getCategoriesFromFiles();
		} catch (Exception e) {
			MostrarMensaje.showErrorMessage("Error al obtener las categorías y habilidades.",
					"Categorías.");
			e.printStackTrace();
		}
	}

	public static Category getCategory(String categoryName, String abbrev, String characteristics,
			String type, String skills) throws Exception {

		Category cat = categoriesAvailable.get(categoryName);
		if (cat == null) {
			cat = createCategory(categoryName, abbrev, characteristics, type, skills);
			categoriesAvailable.put(categoryName, cat);
		}
		return cat;
	}

	public static Category getAvailableCategory(String categoryName) {
		return categoriesAvailable.get(categoryName);
	}

	public static boolean existCategory(String categoryName) {
		return (getAvailableCategory(categoryName) != null);
	}

	private static Category createCategory(String categoryName, String abbreviature,
			String characteristicsTag, String type, String skills) {
		CategoryType catType = CategoryType.getCategoryType(type);
		Category cat;
		switch (catType) {
		case STANDARD:
			cat = new StandardCategory(categoryName, abbreviature, characteristicsTag);
			break;
		default:
			return null;
		}
		cat.addSkills(skills);
		return cat;
	}

	/**
	 * Lee el fichero de categorías.
	 */
	public static void getCategoriesFromFiles() throws Exception {
		List<String> categoriesFile = RolemasterFolderStructure.availableCategoriesFiles();
		for (int j = 0; j < categoriesFile.size(); j++) {
			List<String> lines = RolemasterFolderStructure.readCategoryFileInLines(categoriesFile.get(j));

			for (String oneLine : lines) {
				if (!oneLine.startsWith("#")) {
					String[] descomposed_line = oneLine.split("\t");
					String[] categoryAbbrevName = descomposed_line[0].split("\\(");
					String categoryName = categoryAbbrevName[0];
					try {
						String abrevCat = categoryAbbrevName[1].replace(")", "");
						Category cat = categoriesAvailable.get(categoryName);
						if (cat == null) {
							cat = createCategory(categoryName, abrevCat, descomposed_line[1],
									descomposed_line[2], descomposed_line[3]);
						} else {
							cat.addSkills(descomposed_line[3]);
						}
					} catch (ArrayIndexOutOfBoundsException aiofb) {
						MostrarMensaje.showErrorMessage("Abreviatura de categoria mal definida en "
								+ categoryName, "Lectura de Categorías");
					}
				}
			}
		}
	}
}
