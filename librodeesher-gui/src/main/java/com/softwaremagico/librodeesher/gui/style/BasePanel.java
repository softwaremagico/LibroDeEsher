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
import java.awt.Dimension;

import javax.swing.JPanel;

public abstract class BasePanel extends JPanel {
	private static final long serialVersionUID = -2976171792134626544L;
	protected Integer textDefaultWidth = 80;
	protected Integer textDefaultHeight = 25;
	protected Integer inputDefaultWidth = 160;
	protected Integer inputColumns = 12;
	protected Integer xPadding = 5;
	protected Integer yPadding = 10;

	protected BasePanel() {
		// setBorder(BorderFactory.createLineBorder(Color.black));
	}

	protected void setDefaultSize() {
		setMinimumSize(new Dimension(textDefaultWidth + inputDefaultWidth + 10, textDefaultHeight
				* getComponentCount() / 2 + 20));
		setPreferredSize(new Dimension(textDefaultWidth + inputDefaultWidth + 10, textDefaultHeight
				* getComponentCount() / 2 + 20));
	}

	protected static Color getLineBackgroundColor(int index) {
		if (index % 2 == 0) {
			return Color.WHITE;
		} else {
			return Color.LIGHT_GRAY;
		}
	}

	public abstract void update();

}
