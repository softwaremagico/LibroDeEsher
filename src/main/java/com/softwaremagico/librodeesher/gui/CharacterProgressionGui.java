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

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;

public class CharacterProgressionGui extends JPanel {
	private Integer textDefaultWidth = 80;
	private Integer textDefaultHeight = 20;
	private Integer inputDefaultWidth = 140;

	/**
	 * Create the panel.
	 */
	public CharacterProgressionGui() {

		JLabel levelLabel = new JLabel("Nivel:");
		levelLabel.setMinimumSize(new Dimension(textDefaultWidth, textDefaultHeight));
		levelLabel.setPreferredSize(new Dimension(textDefaultWidth, textDefaultHeight));
		add(levelLabel);

		JTextField levelTextField = new JTextField();
		levelTextField.setMinimumSize(new Dimension(inputDefaultWidth, textDefaultHeight));
		levelTextField.setPreferredSize(new Dimension(inputDefaultWidth, textDefaultHeight));
		add(levelTextField);

		JLabel realmLabel = new JLabel("Reino:");
		realmLabel.setMinimumSize(new Dimension(textDefaultWidth, textDefaultHeight));
		realmLabel.setPreferredSize(new Dimension(textDefaultWidth, textDefaultHeight));
		add(realmLabel);

		JComboBox realmComboBox = new JComboBox();
		realmComboBox.setMinimumSize(new Dimension(inputDefaultWidth, textDefaultHeight));
		realmComboBox.setPreferredSize(new Dimension(inputDefaultWidth, textDefaultHeight));
		add(realmComboBox);

		JLabel cultureLabel = new JLabel("Cultura:");
		cultureLabel.setMinimumSize(new Dimension(textDefaultWidth, textDefaultHeight));
		cultureLabel.setPreferredSize(new Dimension(textDefaultWidth, textDefaultHeight));
		add(cultureLabel);

		JComboBox cultureComboBox = new JComboBox();
		cultureComboBox.setMinimumSize(new Dimension(inputDefaultWidth, textDefaultHeight));
		cultureComboBox.setPreferredSize(new Dimension(inputDefaultWidth, textDefaultHeight));
		add(cultureComboBox);

		JLabel professionLabel = new JLabel("Profesión:");
		professionLabel.setMinimumSize(new Dimension(textDefaultWidth, textDefaultHeight));
		professionLabel.setPreferredSize(new Dimension(textDefaultWidth, textDefaultHeight));
		add(professionLabel);

		JComboBox professionComboBox = new JComboBox();
		professionComboBox.setMinimumSize(new Dimension(inputDefaultWidth, textDefaultHeight));
		professionComboBox.setPreferredSize(new Dimension(inputDefaultWidth, textDefaultHeight));
		add(professionComboBox);

		setMinimumSize(new Dimension(textDefaultWidth + inputDefaultWidth + 10, textDefaultHeight
				* getComponentCount() / 2 + 20));
		setPreferredSize(new Dimension(textDefaultWidth + inputDefaultWidth + 10, textDefaultHeight
				* getComponentCount() / 2 + 20));
	}

}
