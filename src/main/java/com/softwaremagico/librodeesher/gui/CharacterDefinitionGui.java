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
import javax.swing.JRadioButton;

public class CharacterDefinitionGui extends JPanel {
	private Integer textDefaultWidth = 80;
	private Integer textDefaultHeight = 20;
	private Integer inputDefaultWidth = 180;

	/**
	 * Create the panel.
	 */
	public CharacterDefinitionGui() {
		setBorder(null);

		JLabel nameLabel = new JLabel("Nombre:");
		nameLabel.setMinimumSize(new Dimension(textDefaultWidth, textDefaultHeight));
		nameLabel.setPreferredSize(new Dimension(textDefaultWidth, textDefaultHeight));
		add(nameLabel);

		JTextField textField = new JTextField();
		textField.setMinimumSize(new Dimension(inputDefaultWidth, textDefaultHeight));
		textField.setPreferredSize(new Dimension(inputDefaultWidth, textDefaultHeight));
		add(textField);

		JLabel raceLabel = new JLabel("Raza:");
		raceLabel.setMinimumSize(new Dimension(textDefaultWidth, textDefaultHeight));
		raceLabel.setPreferredSize(new Dimension(textDefaultWidth, textDefaultHeight));
		add(raceLabel);

		JComboBox raceComboBox = new JComboBox();
		raceComboBox.setMinimumSize(new Dimension(inputDefaultWidth, textDefaultHeight));
		raceComboBox.setPreferredSize(new Dimension(inputDefaultWidth, textDefaultHeight));
		add(raceComboBox);

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

		JLabel sexLabel = new JLabel("Sexo:");
		sexLabel.setMinimumSize(new Dimension(textDefaultWidth, textDefaultHeight));
		sexLabel.setPreferredSize(new Dimension(textDefaultWidth, textDefaultHeight));
		add(sexLabel);

		JRadioButton maleRadioButton = new JRadioButton("Masculino");
		maleRadioButton.setMinimumSize(new Dimension(inputDefaultWidth / 2 - 5, textDefaultHeight));
		maleRadioButton.setPreferredSize(new Dimension(inputDefaultWidth / 2 - 5, textDefaultHeight));
		add(maleRadioButton);

		JRadioButton femaleRadioButton = new JRadioButton("Femenino");
		femaleRadioButton.setMinimumSize(new Dimension(inputDefaultWidth / 2 - 5, textDefaultHeight));
		femaleRadioButton.setPreferredSize(new Dimension(inputDefaultWidth / 2 - 5, textDefaultHeight));
		add(femaleRadioButton);

		setMinimumSize(new Dimension(textDefaultWidth + inputDefaultWidth + 10, textDefaultHeight
				* getComponentCount() / 2 + 20));
		setPreferredSize(new Dimension(textDefaultWidth + inputDefaultWidth + 10, textDefaultHeight
				* getComponentCount() / 2 + 20));
	}

}
