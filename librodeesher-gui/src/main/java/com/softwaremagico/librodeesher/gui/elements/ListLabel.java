package com.softwaremagico.librodeesher.gui.elements;
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

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class ListLabel extends JLabel {
	private static final long serialVersionUID = 1929208979868170704L;
	private static Color fontColor = Color.DARK_GRAY;

	public ListLabel(String text) {
		super(text);
		defineLabelStyle(SwingConstants.CENTER);
	}

	public ListLabel(String text, int position) {
		super(text);
		defineLabelStyle(position);
	}

	public ListLabel(String text, int position, int width, int height) {
		super(text);
		defineLabelStyle(position);
		setNewSize(width, height);
	}

	public ListLabel(String text, int width, int height) {
		super(text);
		defineLabelStyle(SwingConstants.CENTER);
		setNewSize(width, height);
	}
	
	protected void setDefaultFont(){
		//DEFAULT SYSTEM FONT IS OK;
	}

	private void defineLabelStyle(int position) {
		setDefaultFont();
		setForeground(fontColor);
		setHorizontalAlignment(position);
	}

	private void setNewSize(int width, int height) {
		setMinimumSize(new Dimension(width, height));
		setPreferredSize(new Dimension(width, height));
	}

}
