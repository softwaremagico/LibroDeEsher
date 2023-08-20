package com.softwaremagico.librodeesher.pj.profession;

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
import com.softwaremagico.log.EsherLog;
import com.softwaremagico.utils.ListWithoutCase;

public class ProfessionFactory {
	public static final String PROFESSION_FOLDER = "profesiones";
	private static HashMap<String, Profession> professionStored = new HashMap<>();
	private final static List<String> availableProfessions = Collections
			.unmodifiableList(availableProfessions());

	public ProfessionFactory() {

	}

	private static List<String> availableProfessions() {
		try {
			List<String> listIgnoringCase = new ListWithoutCase();
			listIgnoringCase.addAll(RolemasterFolderStructure.getFilesAvailable(PROFESSION_FOLDER));
			return listIgnoringCase;
		} catch (Exception e) {
			EsherLog.errorMessage(ProfessionFactory.class.getName(), e);
		}
		return null;
	}

	public static List<String> getAvailableProfessions() {
		return availableProfessions;
	}

	public static Profession getProfession(String professionName) throws InvalidProfessionException {
		try {
			if (availableProfessions().contains(professionName)) {
				Profession profession = professionStored.get(professionName);
				if (profession == null) {
					profession = new Profession(professionName);
					professionStored.put(professionName, profession);
				}
				return profession;
			}
		} catch (Exception e) {
		}
		throw new InvalidProfessionException("Profesion no existente: " + professionName);
	}
}
