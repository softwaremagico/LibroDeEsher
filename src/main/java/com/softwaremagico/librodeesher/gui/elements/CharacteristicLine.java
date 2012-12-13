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
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;

import com.softwaremagico.librodeesher.gui.style.BasicLine;
import com.softwaremagico.librodeesher.pj.characteristic.Characteristic;

public class CharacteristicLine extends BasicLine {
	private static final long serialVersionUID = 1855952180568184802L;
	private JLabel characteristicLabel;
	private JSpinner temporalSpinner;
	private JLabel potentialText;
	private JLabel basicBonusText;
	private JLabel raceBonusText;
	private JLabel totalLabel;

	public CharacteristicLine(Characteristic charact, Color background) {
		setElements(charact);
		setBackground(background);
	}

	private void setElements(Characteristic charact) {
		this.removeAll();
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		characteristicLabel = new JLabel(charact.getAbbreviation());
		characteristicLabel.setMinimumSize(new Dimension(labelDefaultWidth, textDefaultHeight));
		c.anchor = GridBagConstraints.LINE_START;
		c.ipadx = xPadding;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0;
		add(characteristicLabel, c);

		temporalSpinner = new JSpinner();
		((JSpinner.DefaultEditor)temporalSpinner.getEditor()).getTextField().setColumns(2);
		c.anchor = GridBagConstraints.CENTER;
		c.ipadx = xPadding;
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 0.2;
		add(temporalSpinner, c);

		potentialText = new JLabel("0");
		potentialText.setMinimumSize(new Dimension(labelDefaultWidth, textDefaultHeight));
		c.anchor = GridBagConstraints.CENTER;
		c.ipadx = xPadding;
		c.gridx = 2;
		c.gridy = 0;
		c.weightx = 0.2;
		add(potentialText, c);

		basicBonusText = new JLabel("100");
		basicBonusText.setMinimumSize(new Dimension(labelDefaultWidth, textDefaultHeight));
		c.anchor = GridBagConstraints.CENTER;
		c.ipadx = xPadding;
		c.gridx = 3;
		c.gridy = 0;
		c.weightx = 0.2;
		add(basicBonusText, c);

		raceBonusText = new JLabel("0");
		raceBonusText.setMinimumSize(new Dimension(labelDefaultWidth, textDefaultHeight));
		c.anchor = GridBagConstraints.CENTER;
		c.ipadx = xPadding;
		c.gridx = 4;
		c.gridy = 0;
		c.weightx = 0.2;
		add(raceBonusText, c);
		
		totalLabel = new JLabel(charact.getTotal().toString());
		totalLabel.setMinimumSize(new Dimension(labelDefaultWidth, textDefaultHeight));
		c.anchor = GridBagConstraints.CENTER;
		c.ipadx = xPadding;
		c.gridx = 5;
		c.gridy = 0;
		c.weightx = 0.2;
		add(totalLabel, c);
		
	}

	public void summaryMode(boolean activated) {
		temporalSpinner.setVisible(!activated);
		potentialText.setVisible(!activated);
		basicBonusText.setVisible(!activated);
		raceBonusText.setVisible(!activated);
	}

}
