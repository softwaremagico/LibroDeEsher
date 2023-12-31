package com.softwaremagico.librodeesher.gui.culture;
/*
 * #%L
 * Libro de Esher
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
import java.awt.GridLayout;

import javax.swing.SwingConstants;

import com.softwaremagico.librodeesher.gui.elements.BaseLabel;
import com.softwaremagico.librodeesher.gui.style.BaseTitleLine;

public class CultureTitleLine extends BaseTitleLine {
	private static final long serialVersionUID = 4480268296161276440L;
	private BaseLabel rankLabel;

	public CultureTitleLine(String titleLabelText, String rankLabelText) {
		setElements(title_background, titleLabelText, rankLabelText);
		setBackground(title_background);
	}

	protected void setElements(Color background, String labelText, String rankLabelText) {
		this.removeAll();
		setLayout(new GridLayout(1, 2));

		BaseLabel weaponCategoryLabel = new BaseLabel(labelText);
		add(createLabelInsidePanel(weaponCategoryLabel, SwingConstants.CENTER, background, title_fontColor));

		rankLabel = new BaseLabel(rankLabelText);
		add(createLabelInsidePanel(rankLabel, SwingConstants.CENTER, background, title_fontColor));

	}
	
	protected void setRankTitle(String rankLabelText){
		rankLabel.setText(rankLabelText);
	}
}
