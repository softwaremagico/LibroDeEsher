package com.softwaremagico.files;

/*
 * #%L
 * KendoTournamentGenerator
 * %%
 * Copyright (C) 2008 - 2012 Softwaremagico
 * %%
 * This software is designed by Jorge Hortelano Otero.
 * Jorge Hortelano Otero <softwaremagico@gmail.com>
 * C/Quart 89, 3. Valencia CP:46008 (Spain).
 *  
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *  
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *  
 * You should have received a copy of the GNU General Public License
 * along with this program; If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class MyFile {
	public static final String UTF8_BOM = "\uFEFF";

	/**
	 * Creates a new instance of MyFile
	 */
	private MyFile() {
	}

	/**
	 * Devuelve las lineas de un fichero leido anteriormente.
	 */
	public static List<String> inLines(String filename, boolean verbose) throws IOException {
		/*
		 * String OS = System.getProperty("os.name"); if
		 * (OS.contains("Windows Vista")) { return readTextFileInLines(filename,
		 * "ISO8859_1", verbose); } else if (OS.contains("Windows")) { return
		 * readTextFileInLines(filename, "Cp1252", verbose); }
		 */
		return readTextFileInLines(filename, "UTF8", verbose);
	}

	/**
	 * Devuelve las lineas de un fichero leido anteriormente.
	 */
	public static List<String> inLines(String filename, boolean verbose, String mode) throws IOException {
		return readTextFileInLines(filename, mode, verbose);
	}

	/**
	 * Return the text of a file in one string.
	 * 
	 * @param filename
	 * @param verbose
	 * @return
	 * @throws IOException
	 */
	public static String inString(String filename, boolean verbose) throws IOException {
		// String OS = System.getProperty("os.name");
		return readTextFileFromLines(filename, "ISO8859_1", verbose);
		/*
		 * if (OS.contains("Windows Vista") || (OS.contains("Windows 7"))) {
		 * return ReadTextFile("ISO8859_1", verbose); } else if
		 * (OS.contains("Windows")) { return ReadTextFile("Cp1252", verbose); }
		 * return ReadTextFile("UTF8", verbose);
		 */
	}

	/**
	 * Devuelve el fichero leido como una lista de lineas.
	 */
	private static List<String> readTextFileInLines(String filename, String mode, boolean verbose) {
		List<String> contents = new ArrayList<>();

		BufferedReader input = null;
		try {
			input = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filename)), mode));
			String line;
			while ((line = input.readLine()) != null) {
				if (line.startsWith(UTF8_BOM)) {
					line = line.substring(1);
				}
				contents.add(line);
			}
		} catch (FileNotFoundException ex) {
			if (verbose) {
				MessageManager.customMessage(MyFile.class.getName(), "Impossible to read the file: " + filename, "Error", JOptionPane.ERROR_MESSAGE);
			}
		} catch (IOException ex) {
		} finally {
			try {
				if (input != null) {
					input.close();
				}
			} catch (IOException ex) {
			}

		}
		return contents;
	}

	/**
	 * Devuelve el fichero leido como un unico string.
	 */
	public static String readTextFile(String filename, Charset encoding, boolean verbose) {
		try {
			byte[] encoded = Files.readAllBytes(Paths.get(filename));
			return new String(encoded, encoding);
		} catch (IOException ex) {
			if (verbose) {
				MessageManager.basicErrorMessage(MyFile.class.getName(), "Error opening the file:" + filename, "File Error");
			}
		}
		return "";
	}

	/**
	 * Devuelve el fichero leido como un unico string.
	 */
	private static String readTextFileFromLines(String filename, String mode, boolean verbose) {
		String text = "";
		List<String> doc = readTextFileInLines(filename, mode, verbose);

		for (int i = 0; i < doc.size(); i++) {
			if (!doc.get(i).startsWith("[") && !doc.get(i).startsWith("]") && !doc.get(i).startsWith("<")) {
				text += doc.get(i) + "\n";
			}
		}

		return text;
	}

	public static String readTextFromJar(String s) {
		String totalText = "";
		String thisLine;
		try {
			InputStream is = MyFile.class.getResourceAsStream(s);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			while ((thisLine = br.readLine()) != null) {
				totalText += thisLine;
			}
		} catch (Exception e) {
		}
		return totalText;
	}

	/**
	 * Removes a file.
	 * 
	 * @param filename
	 */
	public static void deleteFile(String filename) {
		File f = new File(filename);
		if (f.exists() && f.canWrite()) {
			f.delete();
		}
	}

	public static String convertStreamToString(InputStream is) throws IOException {
		/*
		 * To convert the InputStream to String we use the Reader.read(char[]
		 * buffer) method. We iterate until the Reader return -1 which means
		 * there's no more data to read. We use the StringWriter class to
		 * produce the string.
		 */
		if (is != null) {
			Writer writer = new StringWriter();

			char[] buffer = new char[1024];
			try {
				Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				int n;
				while ((n = reader.read(buffer)) != -1) {
					writer.write(buffer, 0, n);
				}
			} finally {
				is.close();
			}
			return writer.toString();
		} else {
			return "";
		}
	}

	/**
	 * Check if the file already exists.
	 * 
	 * @param path
	 * @return
	 */
	public static boolean fileExist(String path) {
		File f = new File(path);
		if (f.exists()) {
			return true;
		}
		return false;
	}

	public static String fileWithouExtension(String fileWithExtension) {
		return fileWithExtension.replaceFirst("[.][^.]+$", "");
	}
}
