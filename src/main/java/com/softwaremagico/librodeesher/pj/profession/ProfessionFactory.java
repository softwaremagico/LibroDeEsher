package com.softwaremagico.librodeesher.pj.profession;

import java.util.Hashtable;
import java.util.List;

import com.softwaremagico.files.RolemasterFolderStructure;
import com.softwaremagico.librodeesher.gui.MostrarMensaje;

public class ProfessionFactory {
	public static final String PROFESSION_FOLDER = "profesiones";
	private static Hashtable<String, Profession> professionsAvailable = new Hashtable<>();

	public static List<String> availableProfessions() throws Exception {
		return RolemasterFolderStructure.filesAvailable(PROFESSION_FOLDER);
	}

	public static Profession getProfession(String professionName) {
		try {
			if (availableProfessions().contains(professionName + ".txt")) {
				Profession profession = professionsAvailable.get(professionName);
				if (profession == null) {
					profession = new Profession(professionName);
					professionsAvailable.put(professionName, profession);
				}
				return profession;
			}
		} catch (Exception e) {
		}
		MostrarMensaje.showErrorMessage("Profesion no existente: " + professionName, "Creación de profesiones.");
		return null;

	}
}
