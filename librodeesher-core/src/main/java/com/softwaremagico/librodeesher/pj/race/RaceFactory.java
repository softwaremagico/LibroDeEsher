package com.softwaremagico.librodeesher.pj.race;

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
import com.softwaremagico.librodeesher.core.ShowMessage;

public class RaceFactory {
	public static final String RACE_FOLDER = "razas";
	private static Hashtable<String, Race> racesAvailable = new Hashtable<>();

	public static List<String> availableRaces() {
		try {
			return RolemasterFolderStructure.getFilesAvailable(RACE_FOLDER);
		} catch (Exception e) {
			ShowMessage.showErrorMessage("Problema al obtener las razas disponibles.", "Razas disponibles");
		}
		return null;
	}

	public static Race getRace(String raceName) {
		try {
			if (availableRaces().contains(raceName)) {
				Race race = racesAvailable.get(raceName);
				if (race == null) {
					race = new Race(raceName);
					racesAvailable.put(raceName, race);
				}
				return race;
			}
		} catch (Exception e) {
		}
		ShowMessage.showErrorMessage("Raza no existente: " + raceName, "Creación de raza");
		return null;

	}
}
