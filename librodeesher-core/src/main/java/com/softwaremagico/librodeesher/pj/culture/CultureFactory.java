package com.softwaremagico.librodeesher.pj.culture;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.softwaremagico.files.RolemasterFolderStructure;
import com.softwaremagico.log.EsherLog;

public class CultureFactory {
	public final static String CULTURE_FOLDER = "culturas";
	private static HashMap<String, Culture> culturesAvailable = new HashMap<>();

	public static List<String> availableCultures() {
		try {
			return RolemasterFolderStructure.getFilesAvailable(CULTURE_FOLDER);
		} catch (Exception e) {
			EsherLog.errorMessage(CultureFactory.class.getName(), e);
			e.printStackTrace();
		}
		return null;
	}

	public static List<String> availableCultures(List<String> subset) {
		List<String> intersectionSet = new ArrayList<>();
		List<String> allCultures = availableCultures();

		for (String culture : subset) {
			if (allCultures.contains(culture)) {
				intersectionSet.add(culture);
			}
		}

		return intersectionSet;
	}

	/**
	 * All "Urban" cultures, etc.
	 * 
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

	public static Culture getCulture(String cultureName) throws InvalidCultureException {
		try {
			if (availableCultures().contains(cultureName)) {
				Culture culture = culturesAvailable.get(cultureName);
				if (culture == null) {
					culture = new Culture(cultureName);
					culturesAvailable.put(cultureName, culture);
				}
				return culture;
			}
		} catch (Exception e) {
		}
		throw new InvalidCultureException("Culture does not exist: " + cultureName);
	}
}
