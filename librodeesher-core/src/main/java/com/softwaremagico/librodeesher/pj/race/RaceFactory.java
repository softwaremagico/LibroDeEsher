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

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.softwaremagico.files.RolemasterFolderStructure;
import com.softwaremagico.librodeesher.pj.race.exceptions.InvalidRaceException;
import com.softwaremagico.log.EsherLog;
import com.softwaremagico.utils.ListWithoutCase;

public class RaceFactory {
	public static final String RACE_FOLDER = "razas";
	private static HashMap<String, Race> racesStored = new HashMap<>();
	private final static List<String> availableRaces = Collections.unmodifiableList(availableRaces());

	private static List<String> availableRaces() {
		try {
			List<String> listIgnoringCase = new ListWithoutCase();
			listIgnoringCase.addAll(RolemasterFolderStructure.getFilesAvailable(RACE_FOLDER));
			return listIgnoringCase;
		} catch (Exception e) {
			EsherLog.errorMessage(RaceFactory.class.getName(), e);
		}
		return null;
	}

	public static List<String> getAvailableRaces() {
		return availableRaces;
	}

	public static Race getRace(String raceName) throws InvalidRaceException {
		try {
			if (availableRaces.contains(raceName)) {
				Race race = racesStored.get(raceName);
				if (race == null) {
					race = new Race(raceName);
					racesStored.put(raceName, race);
				}
				return race;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		throw new InvalidRaceException("Raza no existente: " + raceName);
	}
}
