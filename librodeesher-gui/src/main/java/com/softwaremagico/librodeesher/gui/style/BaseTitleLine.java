package com.softwaremagico.librodeesher.gui.style;

/*
 * #%L
 * Libro de Esher
 * %%
 * Copyright (C) 2007 - 2012 Softwaremagico
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

public abstract class BaseTitleLine extends BaseLine {
	private static final long serialVersionUID = 7901507705885692683L;
	protected Color title_background = Color.BLACK;
	protected Color title_foreground = Color.WHITE;
	protected Color title_fontColor = Color.LIGHT_GRAY;
	protected static String font = "Dialog Bold";

	/**
	 * Create the panel.
	 */
	public BaseTitleLine() {
		setDefaultDesign();
	}

	@Override
	public void update() {

	}

	@Override
	public Color getDefaultBackground() {
		return title_background;
	}

	@Override
	public Color getDefaultForegorund() {
		return title_foreground;
	}

	@Override
	public Color getDefaultFontColor() {
		return title_fontColor;
	}

}
