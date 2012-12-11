package com.softwaremagico.librodeesher.pj.culture;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.softwaremagico.files.RolemasterFolderStructure;
import com.softwaremagico.librodeesher.gui.MostrarMensaje;

public class CultureFactory {
	public final static String CULTURE_FOLDER = "culturas";
	private static Hashtable<String, Culture> culturesAvailable = new Hashtable<>();

	public static List<String> availableCultures() throws Exception {
		return RolemasterFolderStructure.filesAvailable(CULTURE_FOLDER);
	}
	
	/**
	 * All "Urban" cultures, etc. 
	 * @param substring
	 * @return
	 * @throws Exception
	 */
    public static List<String> availableCulturesSubString(String substring) throws Exception {
        List<String> cultures = new ArrayList<>();
        List<String> allAvailableCultures = availableCultures();
        for (int i = 0; i < allAvailableCultures.size(); i++) {
            if (allAvailableCultures.get(i).contains(substring)) {
                cultures.add(allAvailableCultures.get(i));
            }
        }
        return cultures;
    }

	public static Culture getCulture(String cultureName) {
		try {
			if (availableCultures().contains(cultureName + ".txt")) {
				Culture culture = culturesAvailable.get(cultureName);
				if (culture == null) {
					culture = new Culture(cultureName);
					culturesAvailable.put(cultureName, culture);
				}
				return culture;
			}
		} catch (Exception e) {
		}
		MostrarMensaje.showErrorMessage("Cultura no existente: " + cultureName,
				"Creación de adiestramientos.");
		return null;

	}
}
