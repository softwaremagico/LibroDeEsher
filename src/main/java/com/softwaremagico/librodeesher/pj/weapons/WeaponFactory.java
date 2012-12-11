package com.softwaremagico.librodeesher.pj.weapons;

import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.softwaremagico.files.Folder;
import com.softwaremagico.files.RolemasterFolderStructure;
import com.softwaremagico.librodeesher.gui.MostrarMensaje;

public class WeaponFactory {
	public final static String WEAPON_FOLDER = "armas";
	private static Hashtable<WeaponType, List<Weapon>> weaponsByType;

	static {
		try {
			weaponsByType = availableWeapons();
		} catch (Exception e) {
			MostrarMensaje.showErrorMessage("Problemas al obtener las armas.", "Creación de armas.");
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
			List<String> weaponsInFile = Folder.readFileLines(weaponFile, false);
			String weaponTypeName = weaponFile.substring(weaponFile.lastIndexOf(File.separator),
					weaponFile.lastIndexOf(".") - 1);
			WeaponType weaponFileType = WeaponType.getWeaponType(weaponTypeName);
			for (String weaponName : weaponsInFile) {
				Weapon weapon = new Weapon(weaponName, weaponFileType);
				obtainedWeaponsByType.get(weaponFileType).add(weapon);
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
