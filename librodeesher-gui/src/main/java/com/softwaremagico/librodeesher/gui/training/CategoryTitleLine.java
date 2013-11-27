package com.softwaremagico.librodeesher.gui.training;
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

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.SwingConstants;

import com.softwaremagico.librodeesher.gui.elements.BaseLabel;
import com.softwaremagico.librodeesher.gui.style.BaseTitleLine;

public class CategoryTitleLine extends BaseTitleLine {
	private BaseLabel categoryLabel, minHabLabel, maxHabLabel, rankLabel;

	public CategoryTitleLine() {
		setElements(background);
		setBackground(background);
	}

	protected void setElements(Color background) {
		this.removeAll();
		setLayout(new GridLayout(1, 0));

		categoryLabel = new BaseLabel("Opt");
		add(createLabelInsidePanel(categoryLabel, SwingConstants.LEFT, background, fontColor));

		minHabLabel = new BaseLabel("mH");
		add(createLabelInsidePanel(minHabLabel, SwingConstants.CENTER, background, fontColor));

		maxHabLabel = new BaseLabel("MH)");
		add(createLabelInsidePanel(maxHabLabel, SwingConstants.CENTER, background, fontColor));

		rankLabel = new BaseLabel("Rk");
		add(createLabelInsidePanel(rankLabel, SwingConstants.CENTER, background, fontColor));

	}

	public void sizeChanged() {
		if (this.getWidth() < 200) {
			categoryLabel.setText("Op");
			minHabLabel.setText("Hm");
			maxHabLabel.setText("HM");
			rankLabel.setText("Rk)");
		} else if (this.getWidth() < 400) {
			categoryLabel.setText("Opcion.");
			minHabLabel.setText("Hb. min");
			maxHabLabel.setText("Hb. max");
			rankLabel.setText("Rangos");
		} else {
			categoryLabel.setText("Opciones");
			minHabLabel.setText("Habilidades mínimas");
			maxHabLabel.setText("Habilidades máximas");
			rankLabel.setText("Rangos");
		}
	}

}