package com.softwaremagico.librodeesher.gui;
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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.softwaremagico.librodeesher.pj.race.Race;

public class CharacterProfessionPanel extends BasePanel {
	private static final long serialVersionUID = -4572529165916501939L;
	private JLabel professionLabel;
	private JLabel trainingLabel;

	protected CharacterProfessionPanel() {
		setElements();
		setDefaultSize();
	}

	protected void setElements() {
		this.removeAll();
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		professionLabel = new JLabel("Profesión:");
		c.anchor = GridBagConstraints.LINE_START;
		c.ipadx = xPadding;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0;
		add(professionLabel, c);

		JComboBox<Race> professionComboBox = new JComboBox();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 0;
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 1;
		add(professionComboBox, c);

		trainingLabel = new JLabel("Adiestramiento:");
		c.anchor = GridBagConstraints.LINE_START;
		c.ipadx = xPadding;
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 0;
		add(trainingLabel, c);

		JTextField trainingTextField = new JTextField();;
		trainingTextField.setEditable(false);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 0;
		c.gridx = 1;
		c.gridy = 1;
		c.weightx = 1;
		add(trainingTextField, c);
	}
	
	public void sizeChanged() {
		if (this.getWidth() < 230) {
			professionLabel.setText("Prf.:");
			trainingLabel.setText("Adst.:");
		} else if (this.getWidth() < 280) {
			professionLabel.setText("Prof.:");
			trainingLabel.setText("Adist.:");
		} else {
			professionLabel.setText("Profesión:");
			trainingLabel.setText("Adiestramiento:");
		}
	}
}
