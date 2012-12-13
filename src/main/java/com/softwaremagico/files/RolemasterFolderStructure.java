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

	public static final String DIRECTORIO_CATEGORIAS = "";
	private static final String APPLICATION_FOLDER = getApplicationInstallationDirectory();
	public static final String ROLEMASTER_FOLDER = APPLICATION_FOLDER + File.separator + "rolemaster";
	public final static String DIRECTORIO_TALENTOS = "talentos";
	public final static String CULTURE_FOLDER = "culturas";
	public static final String DIRECTORIO_HECHIZOS = "hechizos";
	public final static String DIRECTORIO_ADIESTRAMIENTOS = "adiestramientos";
	public final static String DIRECTORIO_MODULOS = ROLEMASTER_FOLDER + File.separator + "modulos";
	public static final String DIRECTORIO_CONFIGURACION = "configuracion";
	public static final String DIRECTORIO_STORE_USER_DATA = ".librodeesher";
	public static final String ARCHIVO_CATEGORIAS = "categorias.txt";
	private static final String CONFIG = "configuracion.txt";
	// private Folder directorio;
	private static List<String> ficherosOcultos = ignoredFiles();
	// Modulos configurados para obtener los ficheros adecuados */
	private static List<String> modulosRolemaster = ObtenerModulosRolemaster();
	public static List<String> disabledModules = new ArrayList<>();
	public static final boolean verbose = false;

	/**
	 * Creates a new instance of DirectorioRolemaster
	 */
	public RolemasterFolderStructure() throws Exception {
	}

	public static List<String> modulosPermitidos() {
		List<String> modulosReales = new ArrayList<>();
		modulosReales.addAll(modulosRolemaster);
		modulosReales.removeAll(disabledModules);
		return modulosReales;
	}

	public static List<String> modulosDisponibles() {
		return modulosRolemaster;
	}
	
    public static String getVersion() {
        return MyFile.readTextFile(RolemasterFolderStructure.class.getResource("/version.txt").getPath(), false);
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

	private static List<String> ignoredFiles() {
		List<String> ignoredFiles = new ArrayList<>();
		ignoredFiles.add("plantilla");
		ignoredFiles.add("costes");
		ignoredFiles.add("Raciales");
		ignoredFiles.add(".txt~");
		return ignoredFiles;
	}

	public static List<String> filesAvailable(String folder) throws Exception {
		List<String> files = new ArrayList<>();
		List<String> modulosPermitidos = modulosPermitidos();
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

	public static List<String> filesAvailableCompletePath(String folder) throws Exception {
		List<String> files = new ArrayList<>();
		List<String> modulosPermitidos = modulosPermitidos();
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

	public static List<String> availableCategoriesFiles() throws Exception {
		List<String> categories = new ArrayList<>();
		if (new File(ROLEMASTER_FOLDER + File.separator + ARCHIVO_CATEGORIAS).exists()) {
			categories.add(ROLEMASTER_FOLDER + File.separator + ARCHIVO_CATEGORIAS);
		}
		List<String> modulosPermitidos = modulosPermitidos();
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

	public static List<String> LeerLineasHechizos(String file) {
		List<String> lineasFicheroHechizos = new ArrayList<>();
		List<String> ficherosHechizos = DevolverPathsFichero(DIRECTORIO_HECHIZOS + File.separator + file);
		for (int i = 0; i < ficherosHechizos.size(); i++) {
			try {
				lineasFicheroHechizos.addAll(Folder.readFileLines(ficherosHechizos.get(i), verbose));
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return lineasFicheroHechizos;
	}

	public static List<String> LeerLineasTalentos(String file) {
		List<String> lineasFicheroTalentos = new ArrayList<>();
		List<String> ficherosTalentos = DevolverPathsFichero(DIRECTORIO_TALENTOS + File.separator + file);
		for (int i = 0; i < ficherosTalentos.size(); i++) {
			try {
				lineasFicheroTalentos.addAll(Folder.readFileLines(ficherosTalentos.get(i), verbose));
			} catch (IOException ex) {
			}
		}
		return lineasFicheroTalentos;
	}

	public static List<String> readFileInLines(String file) {
		try {
			return Folder.readFileLines(file, verbose);
		} catch (IOException ex) {
		}
		return new ArrayList<>();
	}

	public static List<String> LeerLineasProfesion(String file) {
		try {
			return Folder.readFileLines(file, verbose);
		} catch (IOException ex) {
		}
		return new ArrayList<>();
	}

	public static List<String> readCategoryFileInLines(String file) {
		try {
			return Folder.readFileLines(file, verbose);
		} catch (IOException ex) {
			Logger.getLogger(RolemasterFolderStructure.class.getName()).log(Level.SEVERE, null, ex);
		}
		return new ArrayList<>();
	}

	public static String readFileAsText(String folder) {
		try {
			return Folder.readFileAsText(folder, verbose);
		} catch (IOException ex) {
		}
		return new String();
	}

	public static void GuardarEnFichero(String texto, String file) {
		Folder.saveTextInFile(texto, file);
	}

	public static List<String> ObtenerModulosRolemaster() {
		List<String> pathModulos;
		try {
			pathModulos = Folder.obtainFolders(DIRECTORIO_MODULOS + File.separator);
			return pathModulos;
		} catch (Exception ex) {
			return new ArrayList<>();
		}
	}

	public static String searchDirectoryModule(String fichero) {
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

	public static List<String> ObtieneConfiguracionGuardada() {
		String file = ObtenerPathConfiguracion();
		try {
			return Folder.readFileLines(file, verbose);
		} catch (IOException ex) {
		}
		return new ArrayList<>();
	}

	public static String ObtenerPathConfigEnHome() {
		String userHomeFolder = System.getProperty("user.home");
		if (!new File(userHomeFolder + File.separator + DIRECTORIO_STORE_USER_DATA + File.separator).exists()) {
			Folder.generateFolder(userHomeFolder + File.separator + DIRECTORIO_STORE_USER_DATA
					+ File.separator);
		}
		if (!new File(userHomeFolder + File.separator + DIRECTORIO_STORE_USER_DATA + File.separator
				+ DIRECTORIO_CONFIGURACION + File.separator).exists()) {
			Folder.generateFolder(userHomeFolder + File.separator + DIRECTORIO_STORE_USER_DATA
					+ File.separator + DIRECTORIO_CONFIGURACION + File.separator);
		}
		return userHomeFolder + File.separator + DIRECTORIO_STORE_USER_DATA + File.separator
				+ DIRECTORIO_CONFIGURACION + File.separator;
	}

	/**
	 * Return the path of the configuration file.
	 * 
	 * @param write
	 *            True if is for writting a new configuration file. False for
	 *            reading.
	 * @return
	 */
	public static String ObtenerPathConfiguracion() {
		return ObtenerPathConfigEnHome() + CONFIG;
	}

	public static void saveListInFile(List<String> modulos, String file) {
		Folder.saveListInFile(modulos, file);
	}

	private static List<String> DevolverPathsFichero(String fichero) {
		List<String> ficheros = new ArrayList<>();
		List<String> modulosPermitidos = modulosPermitidos();
		for (int i = 0; i < modulosPermitidos.size(); i++) {
			File file = new File(DIRECTORIO_MODULOS + File.separator + modulosPermitidos.get(i)
					+ File.separator + fichero);
			if (file.exists()) {
				ficheros.add(file.toString());
			}
		}
		return ficheros;
	}
}
