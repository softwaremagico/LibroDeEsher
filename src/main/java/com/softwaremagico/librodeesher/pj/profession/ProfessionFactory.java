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

import java.util.Hashtable;
import java.util.List;

import com.softwaremagico.files.RolemasterFolderStructure;
import com.softwaremagico.librodeesher.gui.ShowMessage;

public class ProfessionFactory {
	public static final String PROFESSION_FOLDER = "profesiones";
	private static Hashtable<String, Profession> professionsAvailable = new Hashtable<>();

	public static List<String> availableProfessions() {
		try {
			return RolemasterFolderStructure.filesAvailable(PROFESSION_FOLDER);
		} catch (Exception e) {
			ShowMessage.showErrorMessage("Problema al obtener las profesiones disponibles.", "Profesiones disponibles");
		}
		return null;
	}

	public static Profession getProfession(String professionName) {
		try {
			if (availableProfessions().contains(professionName)) {
				Profession profession = professionsAvailable.get(professionName);
				if (profession == null) {
					profession = new Profession(professionName);
					professionsAvailable.put(professionName, profession);
				}
				return profession;
			}
		} catch (Exception e) {
		}
		ShowMessage.showErrorMessage("Profesion no existente: " + professionName, "Creación de profesiones.");
		return null;

	}
}
