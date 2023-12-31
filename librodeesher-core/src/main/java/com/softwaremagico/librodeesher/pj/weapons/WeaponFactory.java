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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.softwaremagico.files.Folder;
import com.softwaremagico.files.MyFile;
import com.softwaremagico.files.RolemasterFolderStructure;
import com.softwaremagico.log.EsherLog;

public class WeaponFactory {
	public final static String WEAPON_FOLDER = "armas";
	private static HashMap<WeaponType, List<Weapon>> weaponsByType;

	static {
		try {
			weaponsByType = availableWeapons();
		} catch (Exception e) {
			EsherLog.errorMessage(WeaponFactory.class.getName(), e);
			e.printStackTrace();
		}
	}

	private static HashMap<WeaponType, List<Weapon>> availableWeapons()
			throws Exception {
		// Init variables.
		HashMap<WeaponType, List<Weapon>> obtainedWeaponsByType = new HashMap<>();
		for (WeaponType typeW : WeaponType.values()) {
			List<Weapon> weapons = new ArrayList<>();
			obtainedWeaponsByType.put(typeW, weapons);
		}

		// Find all files with weapons.
		List<String> weaponFiles = RolemasterFolderStructure
				.getFilesAvailableCompletePath(WEAPON_FOLDER);

		// Read each file.
		for (String weaponFile : weaponFiles) {
			List<String> weaponsInFile = Folder.readFileLines(weaponFile
					+ ".txt", false);

			File file = new File(weaponFile + ".txt");
			String weaponTypeName = file.getName();

			WeaponType weaponFileType = WeaponType.getWeaponType(MyFile
					.fileWithouExtension(weaponTypeName));
			for (String line : weaponsInFile) {
				if (!line.startsWith("#")) {
					try {
						String[] weaponName = line.split("\t");
						Weapon weapon = new Weapon(weaponName[0],
								weaponFileType, weaponName[1]);
						if (!obtainedWeaponsByType.get(weaponFileType)
								.contains(weapon)) {
							obtainedWeaponsByType.get(weaponFileType).add(
									weapon);
						}
					} catch (ArrayIndexOutOfBoundsException aiob) {
						EsherLog.severe(WeaponFactory.class.getName(),
								"Error en el arma: " + line);
						EsherLog.errorMessage(WeaponFactory.class.getName(),
								aiob);
					}
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

	/**
	 * Return all weapons that are not marked as rare.
	 * 
	 * @return
	 */
	public static List<Weapon> getAllStandardWeapons() {
		List<Weapon> weapons = new ArrayList<>();
		for (WeaponType type : WeaponType.values()) {
			weapons.addAll(getWeaponsByType(type));
		}
		List<Weapon> filteredWeapons = new ArrayList<>();
		for (Weapon weapon : weapons) {
			if (!weapon.isRare()) {
				filteredWeapons.add(weapon);
			}
		}
		return filteredWeapons;
	}

	public static List<Weapon> getWeaponsByType(WeaponType type) {
		return weaponsByType.get(type);
	}

	public static List<Weapon> getWeaponsByTypeNonRare(WeaponType type) {
		List<Weapon> weapons = weaponsByType.get(type);
		List<Weapon> filteredWeapons = new ArrayList<>();
		for (Weapon weapon : weapons) {
			if (!weapon.isRare()) {
				filteredWeapons.add(weapon);
			}
		}
		return filteredWeapons;
	}

	public static Weapon getWeapon(String name) throws InvalidWeaponException {
		String nameInLower = name.toLowerCase();
		for (WeaponType type : WeaponType.values()) {
			List<Weapon> weapons = getWeaponsByType(type);
			for (Weapon weapon : weapons) {
				if (weapon.getName().toLowerCase().equals(nameInLower)) {
					return weapon;
				}
			}
		}
		throw new InvalidWeaponException("Weapon '" + name
				+ "' abbreviature not found!");
	}
	
	
	/**
	 * Returns a subset of weapons by a prefix. Something like gets all revolvers.	
	 * @param prefix
	 * @return
	 */
	public static Set<Weapon> getWeaponsByPrefix(String prefix) {
		Set<Weapon> filteredWeapons = new HashSet<>();
		prefix = prefix.replaceAll("\\{", "").replaceAll("\\}", "").toLowerCase();
		for (WeaponType type : WeaponType.values()) {
			List<Weapon> weapons = weaponsByType.get(type);			
			for (Weapon weapon : weapons) {
				if (weapon.getName().toLowerCase().startsWith(prefix)) {
					filteredWeapons.add(weapon);
				}
			}
		}
		return filteredWeapons;
	}

}
