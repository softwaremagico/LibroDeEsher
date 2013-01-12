package com.softwaremagico.librodeesher.gui.elements;
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

import java.awt.Font;

public class BoldListLabel extends ListLabel {
	private static final long serialVersionUID = -2810990111309979379L;

	public BoldListLabel(String text) {
		super(text);
	}

	public BoldListLabel(String text, int position) {
		super(text, position);
	}

	public BoldListLabel(String text, int position, int width, int height) {
		super(text, position, width, height);
	}

	public BoldListLabel(String text, int width, int height) {
		super(text, width, height);
	}

	protected void setDefaultFont() {
		setFont(getFont().deriveFont(Font.BOLD));
	}

}
