package com.softwaremagico.librodeesher.gui.culture;
/*
 * #%L
 * Libro de Esher
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

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import com.softwaremagico.librodeesher.gui.style.BasicTitleLine;

public class CultureTitleLine extends BasicTitleLine {
	private static final long serialVersionUID = 4480268296161276440L;
	private JLabel rankLabel;

	public CultureTitleLine(String titleLabelText, String rankLabelText) {
		setElements(background, titleLabelText, rankLabelText);
		setBackground(background);
	}

	protected void setElements(Color background, String labelText, String rankLabelText) {
		this.removeAll();
		setLayout(new GridLayout(1, 2));

		JLabel weaponCategoryLabel = new JLabel(labelText);
		add(createLabelInsidePanel(weaponCategoryLabel, SwingConstants.CENTER, background, fontColor));

		rankLabel = new JLabel(rankLabelText);
		add(createLabelInsidePanel(rankLabel, SwingConstants.CENTER, background, fontColor));

	}
	
	protected void setRankTitle(String rankLabelText){
		rankLabel.setText(rankLabelText);
	}
}
