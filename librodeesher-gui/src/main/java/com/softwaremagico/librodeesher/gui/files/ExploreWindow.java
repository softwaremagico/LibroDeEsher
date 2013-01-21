package com.softwaremagico.librodeesher.gui.files;
/*
 * #%L
 * Libro de Esher GUI
 * %%
 * Copyright (C) 2007 - 2013 Softwaremagico
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

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import com.softwaremagico.librodeesher.basics.ShowMessage;

public abstract class ExploreWindow {
	private JFileChooser fc;
	private static String explorationFolder;
	private String defaultFileName;

	ExploreWindow(String defaultFileName) {
		this.defaultFileName = defaultFileName;
	}

	public String getDefaultFileName() {
		return defaultFileName;
	}

	public String exploreWindows(String title, int mode, String file,
			javax.swing.filechooser.FileFilter filter) {
		JFrame frame = null;
		try {
			fc = new JFileChooser(new File(getDefaultExplorationFolder() + File.separator));
			fc.setFileFilter(filter);
			fc.setFileSelectionMode(mode);
			if (file.length() == 0 && !title.equals("Load")) {
				fc.setSelectedFile(new File(getDefaultFileName()));
			} else {
				fc.setSelectedFile(new File(file));
			}
			int fcReturn = fc.showDialog(frame, title);
			if (fcReturn == JFileChooser.APPROVE_OPTION) {
				setDefaultExplorationFolder(fc.getSelectedFile().toString());
				if (fc.getSelectedFile().isDirectory()) {
					return fc.getSelectedFile().toString() + File.pathSeparator + getDefaultFileName();
				}
				return fc.getSelectedFile().toString();
			}
		} catch (NullPointerException npe) {
			ShowMessage.showErrorMessage("Valor nulo.", "Error al abrir un fichero.");
		}
		return "";
	}

	public static String getDefaultExplorationFolder() {
		if (explorationFolder == null) {
			return System.getProperty("user.home");
		} else {
			return explorationFolder;
		}
	}

	public static void setDefaultExplorationFolder(String path) {
		explorationFolder = path;
	}

}
