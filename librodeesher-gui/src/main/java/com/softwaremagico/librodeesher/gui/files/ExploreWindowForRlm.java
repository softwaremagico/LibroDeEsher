package com.softwaremagico.librodeesher.gui.files;
/*
 * #%L
 * Libro de Esher GUI
 * %%
 * Copyright (C) 2007 - 2013 Softwaremagico
 * %%
 * This software is designed by Jorge Hortelano Otero. Jorge Hortelano Otero
 * <softwaremagico@gmail.com> Valencia (Spain).
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

public class ExploreWindowForRlm extends ExploreWindow  {

	public ExploreWindowForRlm(String defaultFileName) {
		super(defaultFileName);
	}

	public String exploreWindows(String title, int mode, String file) {
		return super.exploreWindows(title, mode, file, new RlmFilter());
	}

	private class RlmFilter extends javax.swing.filechooser.FileFilter {

		@Override
		public boolean accept(File file) {
			String filename = file.getName();
			return file.isDirectory() || filename.endsWith(".rlm");
		}

		@Override
		public String getDescription() {
			return "Fichero del Libro de Esher";
		}
	}
}
