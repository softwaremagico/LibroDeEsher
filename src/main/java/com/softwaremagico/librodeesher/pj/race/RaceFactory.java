package com.softwaremagico.librodeesher.pj.race;

import java.util.Hashtable;
import java.util.List;

import com.softwaremagico.files.RolemasterFolderStructure;
import com.softwaremagico.librodeesher.gui.MostrarMensaje;

public class RaceFactory {
	public static final String RACE_FOLDER = "profesiones";
	private static Hashtable<String, Race> racesAvailable = new Hashtable<>();

	public static List<String> availableRaces() throws Exception {
		return RolemasterFolderStructure.filesAvailable(RACE_FOLDER);
	}

	public static Race getRace(String raceName) {
		try {
			if (availableRaces().contains(raceName + ".txt")) {
				Race race = racesAvailable.get(raceName);
				if (race == null) {
					race = new Race(raceName);
					racesAvailable.put(raceName, race);
				}
				return race;
			}
		} catch (Exception e) {
		}
		MostrarMensaje.showErrorMessage("Raza no existente: " + raceName, "Creación de raza");
		return null;

	}
}
