package com.softwaremagico.librodeesher.pj.training;

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

import java.util.Hashtable;
import java.util.List;

import com.softwaremagico.files.RolemasterFolderStructure;
import com.softwaremagico.librodeesher.gui.ShowMessage;

public class TrainingFactory {
	public final static String TRAINING_FOLDER = "adiestramientos";
	private static Hashtable<String, Training> trainingsAvailable = new Hashtable<>();

	public static List<String> availableTrainings() {
		try {
			return RolemasterFolderStructure.filesAvailable(TRAINING_FOLDER);
		} catch (Exception e) {
			ShowMessage.showErrorMessage("Problema al obtener los adiestramientos disponibles.",
					"Adiestramientos disponibles");
		}
		return null;
	}

	public static Training getTraining(String trainingName) {
		try {
			if (availableTrainings().contains(trainingName)) {
				Training training = trainingsAvailable.get(trainingName);
				if (training == null) {
					training = new Training(trainingName);
					trainingsAvailable.put(trainingName, training);
				}
				return training;
			}
		} catch (Exception e) {
		}
		/*ShowMessage.showErrorMessage("Adiestramiento no existente: " + trainingName,
				"Creación de adiestramientos.");*/
		return null;
	}

}
