package com.softwaremagico.files;

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
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RolemasterFolderStructure implements Serializable {
	private static final long serialVersionUID = -528684854404121115L;
	private static final String APPLICATION_FOLDER = getApplicationInstallationDirectory();
	private static final String ROLEMASTER_FOLDER = APPLICATION_FOLDER
			+ File.separator + "rolemaster";
	private static final String DATABASE_FOLDER = "database";
	private static final String SPELL_FOLDER = "hechizos";
	private final static String MODULES_DIRECTORY = ROLEMASTER_FOLDER
			+ File.separator + "modulos";
	private static final String FOLDER_STORE_USER_DATA = "librodeesher";
	private static final String CATEGORIES_FILE = "categorias.txt";
	private static final String CONFIG_FILE = "configuracion.conf";
	private static final String PERKS_FILE = "talentos.txt";
	private static final String PERKS_FOLDER = "talentos";
	private static final String SHEET_FOLDER = "fichas";
	private static List<String> ignoredFiles = getIgnoredFiles();
	// Configured modules to obtain desired files.
	private static List<String> availableModules = getRolemasterModulesAvailable();
	private static List<String> disabledModules = new ArrayList<>();
	private static final boolean verbose = false;

	public static List<String> getAllModules() {
		return availableModules;
	}

	public static List<String> getAvailableModules() {
		List<String> modules = new ArrayList<>();
		modules.addAll(availableModules);
		modules.removeAll(disabledModules);
		return modules;
	}

	public static void removeDisabledModule(String module) {
		disabledModules.remove(module);
	}

	public static List<String> getDisabledModules() {
		return disabledModules;
	}

	private static String getApplicationInstallationDirectory() {
		File directory = new File(RolemasterFolderStructure.class
				.getProtectionDomain().getCodeSource().getLocation().getPath());
		String path;
		// load from de IDE
		if (directory.getParentFile().getPath().contains("target")) {
			path = directory.getParentFile().getParentFile().getParentFile()
					.getPath();
			// load from librodeesher-core as dependency
		} else if (directory.getParentFile().getPath().contains("lib")) {
			path = directory.getParentFile().getParentFile().getPath();
		} else {
			path = directory.getParentFile().getPath();
		}
		try {
			// Delete '%20' if whitespaces are present.
			path = URLDecoder.decode(path, "utf-8");
		} catch (UnsupportedEncodingException ex) {
			return path;
		}
		// If it is a maven dependency (i.e. when executing maven exec java)
		if (path.contains(".m2")) {
			path = "../";
		}
		return path;
	}

	private static List<String> getIgnoredFiles() {
		List<String> ignoredFiles = new ArrayList<>();
		ignoredFiles.add("plantilla");
		ignoredFiles.add("costes");
		ignoredFiles.add("raciales");
		ignoredFiles.add(".txt~");
		return ignoredFiles;
	}

	public static List<String> getFilesAvailable(String folder) {
		List<String> files = new ArrayList<>();
		List<String> modulosPermitidos = getAvailableModules();
		for (int i = 0; i < modulosPermitidos.size(); i++) {
			files.addAll(Folder.obtainfilesSubdirectory(MODULES_DIRECTORY
					+ File.separator + modulosPermitidos.get(i)
					+ File.separator + folder));
		}
		List<String> fileList = new ArrayList<>();
		for (int i = 0; i < files.size(); i++) {
			for (int j = 0; j < ignoredFiles.size(); j++) {
				if (!files.get(i).toLowerCase().contains(ignoredFiles.get(j))
						&& !fileList.contains(files.get(i))) {
					fileList.add(files.get(i));
				}
			}
		}
		return fileList;
	}

	public static List<String> getFilesAvailableCompletePath(String folder)
			throws Exception {
		List<String> files = new ArrayList<>();
		List<String> modulosPermitidos = getAvailableModules();
		for (int i = 0; i < modulosPermitidos.size(); i++) {
			List<String> fileList = Folder
					.obtainfilesSubdirectory(MODULES_DIRECTORY + File.separator
							+ modulosPermitidos.get(i) + File.separator
							+ folder);
			for (String file : fileList) {
				files.add(MODULES_DIRECTORY + File.separator
						+ modulosPermitidos.get(i) + File.separator + folder
						+ File.separator + file);
			}
		}
		List<String> fileList = new ArrayList<>();
		for (int i = 0; i < files.size(); i++) {
			for (int j = 0; j < ignoredFiles.size(); j++) {
				if (!files.get(i).contains(ignoredFiles.get(j))
						&& !fileList.contains(files.get(i))) {
					fileList.add(files.get(i));
				}
			}
		}
		return fileList;
	}

	public static List<String> getAvailableCategoriesFiles() {
		List<String> categoriesFiles = new ArrayList<>();
		if (new File(ROLEMASTER_FOLDER + File.separator + CATEGORIES_FILE)
				.exists()) {
			categoriesFiles.add(ROLEMASTER_FOLDER + File.separator
					+ CATEGORIES_FILE);
		}
		List<String> modulosPermitidos = getAvailableModules();
		for (int i = 0; i < modulosPermitidos.size(); i++) {
			if (Folder.obtainfilesSubdirectory(
					MODULES_DIRECTORY + File.separator
							+ modulosPermitidos.get(i)).size() > 0) {
				if (new File(MODULES_DIRECTORY + File.separator
						+ modulosPermitidos.get(i) + File.separator
						+ CATEGORIES_FILE).exists()) {
					categoriesFiles.add(MODULES_DIRECTORY + File.separator
							+ modulosPermitidos.get(i) + File.separator
							+ CATEGORIES_FILE);
				}
			}
		}
		return categoriesFiles;
	}

	public static List<String> getAvailablePerksFiles() throws Exception {
		List<String> perksFile = new ArrayList<>();
		List<String> modulosPermitidos = getAvailableModules();
		for (int i = 0; i < modulosPermitidos.size(); i++) {
			if (Folder.obtainfilesSubdirectory(
					MODULES_DIRECTORY + File.separator
							+ modulosPermitidos.get(i)).size() > 0) {
				if (new File(MODULES_DIRECTORY + File.separator
						+ modulosPermitidos.get(i) + File.separator
						+ PERKS_FOLDER + File.separator + PERKS_FILE).exists()) {
					perksFile.add(MODULES_DIRECTORY + File.separator
							+ modulosPermitidos.get(i) + File.separator
							+ PERKS_FOLDER + File.separator + PERKS_FILE);
				}
			}
		}
		return perksFile;
	}

	public static List<String> getSpellLines(String file) {
		List<String> spellLines = new ArrayList<>();
		List<String> spellFile = getPathOfFile(SPELL_FOLDER + File.separator
				+ file);
		for (int i = 0; i < spellFile.size(); i++) {
			try {
				spellLines.addAll(Folder.readFileLines(spellFile.get(i),
						verbose));
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return spellLines;
	}

	public static List<String> getCategoryFile(String file) {
		try {
			return Folder.readFileLines(file, verbose);
		} catch (IOException ex) {
			Logger.getLogger(RolemasterFolderStructure.class.getName()).log(
					Level.SEVERE, null, ex);
		}
		return new ArrayList<>();
	}

	public static List<String> getRolemasterModulesAvailable() {
		List<String> pathModulos;
		try {
			pathModulos = Folder.obtainFolders(MODULES_DIRECTORY
					+ File.separator);
			return pathModulos;
		} catch (Exception ex) {
			return new ArrayList<>();
		}
	}

	public static String getDirectoryModule(String fichero) {
		File file;
		for (int i = 0; i < availableModules.size(); i++) {
			file = new File(MODULES_DIRECTORY + File.separator
					+ availableModules.get(i) + File.separator + fichero);
			if (file.exists()) {
				return MODULES_DIRECTORY + File.separator
						+ availableModules.get(i) + File.separator + fichero;
			}
		}
		return "";
	}

	public static String getPathFolderInHome() {
		String folder = System.getProperty("user.home") + File.separator + "."
				+ FOLDER_STORE_USER_DATA;
		Folder.makeFolderIfNotExist(folder);
		return folder;
	}

	public static String getDatabaseFolderInHome() {
		String folder = getPathFolderInHome() + File.separator
				+ DATABASE_FOLDER;
		Folder.makeFolderIfNotExist(folder);
		return folder;
	}

	/**
	 * Return the path of the configuration file.
	 * 
	 * @param write
	 *            True if is for writting a new configuration file. False for
	 *            reading.
	 * @return
	 */
	public static String getConfigurationFilePath() {
		return getPathFolderInHome() + File.separator + CONFIG_FILE;
	}

	private static List<String> getPathOfFile(String fileName) {
		List<String> ficheros = new ArrayList<>();
		List<String> modulosPermitidos = getAvailableModules();
		for (int i = 0; i < modulosPermitidos.size(); i++) {
			File file = new File(MODULES_DIRECTORY + File.separator
					+ modulosPermitidos.get(i) + File.separator + fileName);
			if (file.exists()) {
				ficheros.add(file.toString());
			}
		}
		return ficheros;
	}

	public static void setDisabledModules(List<String> disabledModules) {
		RolemasterFolderStructure.disabledModules = disabledModules;
	}

	public static String getSheetFolder() {
		return ROLEMASTER_FOLDER + File.separator + SHEET_FOLDER;
	}
}
