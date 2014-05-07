package com.softwaremagico.librodeesher.gui.style;
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

import java.awt.Font;

import javax.swing.JLabel;

public class Fonts {
	private static Fonts INSTANCE = new Fonts();
	private Font defaultSystemFont;

	private Fonts() {
		JLabel label = new JLabel();
		defaultSystemFont = label.getFont();
	}

	public static Fonts getInstance() {
		return INSTANCE;
	}
	
	public Font getDefaultFont(){
		return defaultSystemFont;
	}
	
	public Font getBoldFont(){
		return defaultSystemFont.deriveFont(Font.BOLD);
	}
	
	
	
}
