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

public class CharacterLevelPanel extends BasePanel {

	private static final long serialVersionUID = -8063970435094018287L;
	private JLabel levelLabel;
	private JLabel developmentLabel;
	private JTextField developmentTextField;
	private JTextField levelTextField;

	protected CharacterLevelPanel() {
		setElements();
		setDefaultSize();
	}

	protected void setElements() {
		this.removeAll();
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		levelLabel = new JLabel("Nivel:");
		c.anchor = GridBagConstraints.LINE_START;
		c.ipadx = xPadding;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0;
		add(levelLabel, c);

		levelTextField = new JTextField();
		levelTextField.setEditable(false);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 0;
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 1;
		add(levelTextField, c);

		developmentLabel = new JLabel("Pts Desar.:");
		c.anchor = GridBagConstraints.LINE_START;
		c.ipadx = xPadding;
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 0;
		add(developmentLabel, c);

		developmentTextField = new JTextField();
		developmentTextField.setEditable(false);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 0;
		c.gridx = 1;
		c.gridy = 1;
		c.weightx = 1;
		add(developmentTextField, c);
	}

	public void sizeChanged() {
		if (this.getWidth() < 230) {
			levelLabel.setText("Nvl:");
			developmentLabel.setText("PD.:");
		} else if (this.getWidth() < 280) {
			levelLabel.setText("Nivel:");
			developmentLabel.setText("Pts. D.:");
		} else {
			levelLabel.setText("Nivel:");
			developmentLabel.setText("Pts. Des.:");
		}
	}

	public void setLevel(Integer level) {
		levelTextField.setText(level.toString());
	}

	public void setDevelopmentPoints(Integer remainingDevelopmentPoints) {
		developmentTextField.setText(remainingDevelopmentPoints.toString());
	}
}
