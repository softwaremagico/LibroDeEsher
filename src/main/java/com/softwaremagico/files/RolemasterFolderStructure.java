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

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;

import com.softwaremagico.librodeesher.gui.MainMenu;
import com.softwaremagico.librodeesher.gui.ShowMessage;

public class RolemasterFolderStructure implements Serializable {
	private static final long serialVersionUID = -528684854404121115L;
	public static final String DIRECTORIO_CATEGORIAS = "";
	private static final String APPLICATION_FOLDER = getApplicationInstallationDirectory();
	public static final String ROLEMASTER_FOLDER = APPLICATION_FOLDER + File.separator + "rolemaster";
	public final static String DIRECTORIO_TALENTOS = "talentos";
	public final static String CULTURE_FOLDER = "culturas";
	public static final String SPELL_FOLDER = "hechizos";
	public final static String DIRECTORIO_ADIESTRAMIENTOS = "adiestramientos";
	public final static String DIRECTORIO_MODULOS = ROLEMASTER_FOLDER + File.separator + "modulos";
	public static final String DIRECTORIO_CONFIGURACION = "configuracion";
	private static final String FOLDER_STORE_USER_DATA = "librodeesher";
	public static final String ARCHIVO_CATEGORIAS = "categorias.txt";
	private static final String CONFIG_FILE = "configuracion.conf";
	private static List<String> ficherosOcultos = getIgnoredFiles();
	// Modulos configurados para obtener los ficheros adecuados */
	private static List<String> modulosRolemaster = getRolemasterModulesAvailable();
	public static List<String> disabledModules = new ArrayList<>();
	public static final boolean verbose = false;

	public static List<String> getAvailableModules() {
		List<String> modulosReales = new ArrayList<>();
		modulosReales.addAll(modulosRolemaster);
		modulosReales.removeAll(disabledModules);
		return modulosReales;
	}

	public static List<String> modulosDisponibles() {
		return modulosRolemaster;
	}

	public static String getVersion() {
		String text;
		text = MyFile.readTextFile(RolemasterFolderStructure.class.getResource("/version.txt").getPath(),
				false);
		if (text != null && text.length() > 0) {
			return text;
		}
		return MyFile.readTextFromJar("/version.txt");
	}

	private static String getApplicationInstallationDirectory() {
		File directory = new File(RolemasterFolderStructure.class.getProtectionDomain().getCodeSource()
				.getLocation().getPath());
		String path;
		// Carga local mediante netbeans
		if (directory.getParentFile().getPath().contains("target")) {
			path = directory.getParentFile().getParentFile().getPath();
		} else {
			// Carga desde el instalador.
			path = directory.getParentFile().getPath();
		}
		try {
			// Delete '%20' if whitespaces are present.
			path = URLDecoder.decode(path, "utf-8");
		} catch (UnsupportedEncodingException ex) {
			return path;
		}
		return path;
	}

	private static List<String> getIgnoredFiles() {
		List<String> ignoredFiles = new ArrayList<>();
		ignoredFiles.add("plantilla");
		ignoredFiles.add("costes");
		ignoredFiles.add("Raciales");
		ignoredFiles.add(".txt~");
		return ignoredFiles;
	}

	public static List<String> getFilesAvailable(String folder) throws Exception {
		List<String> files = new ArrayList<>();
		List<String> modulosPermitidos = getAvailableModules();
		for (int i = 0; i < modulosPermitidos.size(); i++) {
			files.addAll(Folder.ObtainfilesSubdirectory(DIRECTORIO_MODULOS + File.separator
					+ modulosPermitidos.get(i) + File.separator + folder));
		}
		List<String> fileList = new ArrayList<>();
		for (int i = 0; i < files.size(); i++) {
			for (int j = 0; j < ficherosOcultos.size(); j++) {
				if (!files.get(i).contains(ficherosOcultos.get(j)) && !fileList.contains(files.get(i))) {
					fileList.add(files.get(i));
				}
			}
		}
		return fileList;
	}

	public static List<String> getFilesAvailableCompletePath(String folder) throws Exception {
		List<String> files = new ArrayList<>();
		List<String> modulosPermitidos = getAvailableModules();
		for (int i = 0; i < modulosPermitidos.size(); i++) {
			List<String> fileList = Folder.ObtainfilesSubdirectory(DIRECTORIO_MODULOS + File.separator
					+ modulosPermitidos.get(i) + File.separator + folder);
			for (String file : fileList) {
				files.add(DIRECTORIO_MODULOS + File.separator + modulosPermitidos.get(i) + File.separator
						+ folder + File.separator + file);
			}
		}
		List<String> fileList = new ArrayList<>();
		for (int i = 0; i < files.size(); i++) {
			for (int j = 0; j < ficherosOcultos.size(); j++) {
				if (!files.get(i).contains(ficherosOcultos.get(j)) && !fileList.contains(files.get(i))) {
					fileList.add(files.get(i));
				}
			}
		}
		return fileList;
	}

	public static List<String> getAvailableCategoriesFiles() throws Exception {
		List<String> categories = new ArrayList<>();
		if (new File(ROLEMASTER_FOLDER + File.separator + ARCHIVO_CATEGORIAS).exists()) {
			categories.add(ROLEMASTER_FOLDER + File.separator + ARCHIVO_CATEGORIAS);
		}
		List<String> modulosPermitidos = getAvailableModules();
		for (int i = 0; i < modulosPermitidos.size(); i++) {
			if (Folder
					.ObtainfilesSubdirectory(DIRECTORIO_MODULOS + File.separator + modulosPermitidos.get(i))
					.size() > 0) {
				if (new File(DIRECTORIO_MODULOS + File.separator + modulosPermitidos.get(i) + File.separator
						+ ARCHIVO_CATEGORIAS).exists()) {
					categories.add(DIRECTORIO_MODULOS + File.separator + modulosPermitidos.get(i)
							+ File.separator + ARCHIVO_CATEGORIAS);
				}
			}
		}
		return categories;
	}

	public static List<String> getSpellLines(String file) {
		List<String> spellLines = new ArrayList<>();
		List<String> spellFile = getPathOfFile(SPELL_FOLDER + File.separator + file);
		for (int i = 0; i < spellFile.size(); i++) {
			try {
				spellLines.addAll(Folder.readFileLines(spellFile.get(i), verbose));
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return spellLines;
	}

	public static List<String> LeerLineasTalentos(String file) {
		List<String> lineasFicheroTalentos = new ArrayList<>();
		List<String> ficherosTalentos = getPathOfFile(DIRECTORIO_TALENTOS + File.separator + file);
		for (int i = 0; i < ficherosTalentos.size(); i++) {
			try {
				lineasFicheroTalentos.addAll(Folder.readFileLines(ficherosTalentos.get(i), verbose));
			} catch (IOException ex) {
			}
		}
		return lineasFicheroTalentos;
	}

	public static List<String> getCategoryFile(String file) {
		try {
			return Folder.readFileLines(file, verbose);
		} catch (IOException ex) {
			Logger.getLogger(RolemasterFolderStructure.class.getName()).log(Level.SEVERE, null, ex);
		}
		return new ArrayList<>();
	}

	public static List<String> getRolemasterModulesAvailable() {
		List<String> pathModulos;
		try {
			pathModulos = Folder.obtainFolders(DIRECTORIO_MODULOS + File.separator);
			return pathModulos;
		} catch (Exception ex) {
			return new ArrayList<>();
		}
	}

	public static String getDirectoryModule(String fichero) {
		File file;
		for (int i = 0; i < modulosRolemaster.size(); i++) {
			file = new File(DIRECTORIO_MODULOS + File.separator + modulosRolemaster.get(i) + File.separator
					+ fichero);
			if (file.exists()) {
				return DIRECTORIO_MODULOS + File.separator + modulosRolemaster.get(i) + File.separator
						+ fichero;
			}
		}
		return "";
	}

	public static String getPathFolderInHome() {
		String folder = System.getProperty("user.home") + File.separator + "." + FOLDER_STORE_USER_DATA;
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

	private static List<String> getPathOfFile(String fichero) {
		List<String> ficheros = new ArrayList<>();
		List<String> modulosPermitidos = getAvailableModules();
		for (int i = 0; i < modulosPermitidos.size(); i++) {
			File file = new File(DIRECTORIO_MODULOS + File.separator + modulosPermitidos.get(i)
					+ File.separator + fichero);
			if (file.exists()) {
				ficheros.add(file.toString());
			}
		}
		return ficheros;
	}

	public static ImageIcon getIcon(String iconName) {
		try {
			ImageIcon icon = new ImageIcon(MainMenu.class.getResource("/icons/" + iconName));
			return new ImageIcon(icon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));
		} catch (Exception e) {
			ShowMessage.showErrorMessage("Icon not found: " + iconName, "Main menu");
			return null;
		}
	}
}
