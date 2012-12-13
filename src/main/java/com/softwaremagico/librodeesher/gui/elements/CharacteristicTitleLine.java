package com.softwaremagico.librodeesher.gui.elements;

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

import java.awt.GridLayout;

import javax.swing.JLabel;

import com.softwaremagico.librodeesher.gui.style.BasicTitleLine;

public class CharacteristicTitleLine extends BasicTitleLine {
	private static final long serialVersionUID = 1855952180568184802L;
	private JLabel characteristicLabel;
	private JLabel temporalLabel;
	private JLabel potentialTextField;
	private JLabel basicBonusTextField;
	private JLabel raceBonusTextField;
	private JLabel totalLabel;

	public CharacteristicTitleLine() {
		setElements();
	}

	private void setElements() {
		this.removeAll();
		setLayout(new GridLayout(1, 6));

		characteristicLabel = new JLabel("Caracter.");
		add(createLabelInsidePanel(characteristicLabel, false, background, fontColor));

		temporalLabel = new JLabel("Temp.");
		add(createLabelInsidePanel(temporalLabel, true, background, fontColor));

		potentialTextField = new JLabel("Pot.");
		add(createLabelInsidePanel(potentialTextField, true, background, fontColor));

		basicBonusTextField = new JLabel("Bonus");
		add(createLabelInsidePanel(basicBonusTextField, true, background, fontColor));

		raceBonusTextField = new JLabel("Raza");
		add(createLabelInsidePanel(raceBonusTextField, true, background, fontColor));

		totalLabel = new JLabel("Total");
		add(createLabelInsidePanel(totalLabel, true, background, fontColor));

	}

	public void setSummaryMode(boolean activated) {
		temporalLabel.setVisible(!activated);
		potentialTextField.setVisible(!activated);
		basicBonusTextField.setVisible(!activated);
		raceBonusTextField.setVisible(!activated);
	}

}
