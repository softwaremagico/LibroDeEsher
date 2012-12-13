package com.softwaremagico.librodeesher.gui.characterBasics;
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

import com.softwaremagico.librodeesher.gui.style.BasePanel;
import com.softwaremagico.librodeesher.pj.race.Race;

public class CharacterRacePanel extends BasePanel {
	private static final long serialVersionUID = 178890486518380989L;
	private JLabel raceLabel;
	private JLabel cultureLabel;

	protected CharacterRacePanel() {
		setElements();
		setDefaultSize();
	}

	protected void setElements() {
		this.removeAll();
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		raceLabel = new JLabel("Raza:");
		c.anchor = GridBagConstraints.LINE_START;
		c.ipadx = xPadding;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0;
		add(raceLabel, c);

		JComboBox<Race> raceComboBox = new JComboBox();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 0;
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 1;
		add(raceComboBox, c);

		cultureLabel = new JLabel("Cultura:");
		c.anchor = GridBagConstraints.LINE_START;
		c.ipadx = xPadding;
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 0;
		add(cultureLabel, c);

		JComboBox<Race> cultureComboBox = new JComboBox();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 0;
		c.gridx = 1;
		c.gridy = 1;
		c.weightx = 1;
		add(cultureComboBox, c);

	}
	
	public void sizeChanged() {
		if (this.getWidth() < 230) {
			raceLabel.setText("Rz.:");
			cultureLabel.setText("Clt.:");
		} else if (this.getWidth() < 280) {
			raceLabel.setText("Raza:");
			cultureLabel.setText("Cult.:");
		} else {
			raceLabel.setText("Raza:");
			cultureLabel.setText("Cultura:");
		}
	}

}
