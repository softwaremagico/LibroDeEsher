package com.softwaremagico.librodeesher.gui.style;
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

import java.awt.Color;


public abstract class BasicTitleLine extends BasicLine {
	private static final long serialVersionUID = 7901507705885692683L;
	protected Color background = Color.BLACK;
	protected Color foreground = Color.WHITE;
	protected Color fontColor = Color.LIGHT_GRAY;
	protected String font = "Dialog Bold";

	/**
	 * Create the panel.
	 */
	public BasicTitleLine() {
		setDefaultDesign();
	}

	protected void setDefaultDesign() {
		//setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		setBackground(background);
		setForeground(foreground);
	}

}