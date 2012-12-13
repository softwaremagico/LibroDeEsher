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

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JSpinner;

import com.softwaremagico.librodeesher.gui.style.BasicLine;
import com.softwaremagico.librodeesher.pj.characteristic.Characteristic;

public class CharacteristicLine extends BasicLine {
	private static final long serialVersionUID = 1855952180568184802L;
	protected JLabel characteristicLabel;
	private JSpinner temporalSpinner;
	private JLabel potentialText;
	private JLabel basicBonusText;
	private JLabel raceBonusText;
	protected JLabel totalLabel;

	public CharacteristicLine(Characteristic charact, Color background) {
		setElements(charact, background);
		setBackground(background);
	}

	protected void setElements(Characteristic charact, Color background) {
		this.removeAll();
		setLayout(new GridLayout(1,6));

		characteristicLabel = new JLabel(charact.getAbbreviation());
		add(createLabelInsidePanel(characteristicLabel, false, background, fontColor));

		temporalSpinner = new JSpinner();
		add(createSpinnerInsidePanel(temporalSpinner, true, background));

		potentialText = new JLabel("0");
		add(createLabelInsidePanel(potentialText, true, background, fontColor));

		basicBonusText = new JLabel("100");
		add(createLabelInsidePanel(basicBonusText, true, background, fontColor));


		raceBonusText = new JLabel("0");
		add(createLabelInsidePanel(raceBonusText, true, background, fontColor));
		
		totalLabel = new JLabel(charact.getTotal().toString());
		add(createLabelInsidePanel(totalLabel, true, background, fontColor));
		
	}

}
