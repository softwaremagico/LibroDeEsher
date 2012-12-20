package com.softwaremagico.librodeesher.pj.weapons;

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

import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Pattern;

import com.softwaremagico.files.Folder;
import com.softwaremagico.files.MyFile;
import com.softwaremagico.files.RolemasterFolderStructure;
import com.softwaremagico.librodeesher.gui.ShowMessage;

public class WeaponFactory {
	public final static String WEAPON_FOLDER = "armas";
	private static Hashtable<WeaponType, List<Weapon>> weaponsByType;

	static {
		try {
			weaponsByType = availableWeapons();
		} catch (Exception e) {
			ShowMessage.showErrorMessage("Problemas al obtener las armas.", "Creación de armas");
			e.printStackTrace();
		}
	}

	private static Hashtable<WeaponType, List<Weapon>> availableWeapons() throws Exception {

		// Init variables.
		Hashtable<WeaponType, List<Weapon>> obtainedWeaponsByType = new Hashtable<>();
		for (WeaponType typeW : WeaponType.values()) {
			List<Weapon> weapons = new ArrayList<>();
			obtainedWeaponsByType.put(typeW, weapons);
		}

		// Find all files with weapons.
		List<String> weaponFiles = RolemasterFolderStructure.filesAvailableCompletePath(WEAPON_FOLDER);

		// Read each file.
		for (String weaponFile : weaponFiles) {
			List<String> weaponsInFile = Folder.readFileLines(weaponFile + ".txt", false);

			File file = new File(weaponFile + ".txt");
			String weaponTypeName = file.getName();

			WeaponType weaponFileType = WeaponType.getWeaponType(MyFile.fileWithouExtension(weaponTypeName));
			for (String weaponName : weaponsInFile) {
				if (!weaponName.startsWith("#")) {
					Weapon weapon = new Weapon(weaponName, weaponFileType);
					obtainedWeaponsByType.get(weaponFileType).add(weapon);
				}
			}
		}

		return obtainedWeaponsByType;
	}

	public static List<Weapon> getAllWeapons() {
		List<Weapon> weapons = new ArrayList<>();
		for (WeaponType type : WeaponType.values()) {
			weapons.addAll(getWeaponsByType(type));
		}
		return weapons;
	}

	public static List<Weapon> getWeaponsByType(WeaponType type) {
		return weaponsByType.get(type);
	}

	public static Weapon getWeapon(String name) {
		for (WeaponType type : WeaponType.values()) {
			List<Weapon> weapons = getWeaponsByType(type);
			for (Weapon weapon : weapons) {
				if (weapon.getName().equals(name)) {
					return weapon;
				}
			}
		}
		return null;
	}

}
