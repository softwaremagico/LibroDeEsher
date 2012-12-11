package com.softwaremagico.librodeesher.pj.training;

import java.util.Hashtable;
import java.util.List;

import com.softwaremagico.files.RolemasterFolderStructure;
import com.softwaremagico.librodeesher.gui.MostrarMensaje;

public class TrainingFactory {
	public final static String TRAINING_FOLDER = "adiestramientos";
	private static Hashtable<String, Training> trainingsAvailable = new Hashtable<>();

	public static List<String> availableTrainings() throws Exception {
		return RolemasterFolderStructure.filesAvailable(TRAINING_FOLDER);
	}

	public static Training getTraining(String trainingName) {
		try {
			if (availableTrainings().contains(trainingName + ".txt")) {
				Training training = trainingsAvailable.get(trainingName);
				if (training == null) {
					training = new Training(trainingName);
					trainingsAvailable.put(trainingName, training);
				}
				return training;
			}
		} catch (Exception e) {
		}
		MostrarMensaje.showErrorMessage("Adiestramiento no existente: " + trainingName,
				"Creación de adiestramientos.");
		return null;
	}
	
	
}
