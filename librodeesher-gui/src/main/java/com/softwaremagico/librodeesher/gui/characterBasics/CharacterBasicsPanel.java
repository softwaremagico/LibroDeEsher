package com.softwaremagico.librodeesher.gui.characterBasics;

/*
 * #%L
 * Libro de Esher
 * %%
 * Copyright (C) 2007 - 2012 Softwaremagico
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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;

import com.softwaremagico.librodeesher.gui.elements.BaseLabel;
import com.softwaremagico.librodeesher.gui.elements.BaseRadioButton;
import com.softwaremagico.librodeesher.gui.elements.BaseTextField;
import com.softwaremagico.librodeesher.gui.style.BasePanel;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.SexType;

public class CharacterBasicsPanel extends BasePanel {
	private static final long serialVersionUID = -6925539216225561309L;
	private BaseRadioButton maleRadioButton;
	private BaseRadioButton femaleRadioButton;
	private BaseTextField nameTextField;
	private CharacterPlayer character;
	private BaseLabel nameLabel;
	private Set<SexChangedListener> sexChangedListeners;

	public interface SexChangedListener {
		void sexChanged(CharacterPlayer character);
	}

	public CharacterBasicsPanel() {
		sexChangedListeners = new HashSet<>();
		setElements();
		setDefaultSize();
	}

	protected void setElements() {
		this.removeAll();
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		nameLabel = new BaseLabel("Nombre:");
		c.anchor = GridBagConstraints.LINE_START;
		c.ipadx = xPadding;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0;
		add(nameLabel, c);

		nameTextField = new BaseTextField();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 0;
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 1;
		add(nameTextField, c);
		nameTextField.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				character.setName(nameTextField.getText());
			}

			@Override
			public void keyPressed(KeyEvent e) {
				character.setName(nameTextField.getText());
			}

			@Override
			public void keyReleased(KeyEvent e) {
				character.setName(nameTextField.getText());
			}

		});

		BaseLabel sexLabel = new BaseLabel("Sexo:");
		c.anchor = GridBagConstraints.LINE_START;
		c.ipadx = xPadding;
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 0;
		add(sexLabel, c);

		JPanel sexPanel = new JPanel();
		sexPanel.setLayout(new GridBagLayout());

		// Group the radio buttons.
		maleRadioButton = new BaseRadioButton("Masc.");
		femaleRadioButton = new BaseRadioButton("Feme.");
		maleRadioButton.addActionListener(new ChangeSexListener());
		femaleRadioButton.addActionListener(new ChangeSexListener());

		ButtonGroup sexGroup = new ButtonGroup();
		sexGroup.add(femaleRadioButton);
		sexGroup.add(maleRadioButton);
		maleRadioButton.setSelected(true);

		c.anchor = GridBagConstraints.LINE_START;
		c.ipadx = 0;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.5;
		sexPanel.add(maleRadioButton, c);
		c.gridx = 1;
		sexPanel.add(femaleRadioButton, c);

		// c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 0;
		c.gridx = 1;
		c.gridy = 1;
		c.weightx = 1;
		add(sexPanel, c);
	}

	public void sizeChanged() {
		if (this.getWidth() < 230) {
			nameLabel.setText("Nmb.:");
			maleRadioButton.setText("M");
			femaleRadioButton.setText("F");
		} else if (this.getWidth() < 280) {
			nameLabel.setText("Nomb.:");
			maleRadioButton.setText("Masc.");
			femaleRadioButton.setText("Feme.");
		} else {
			nameLabel.setText("Nombre:");
			maleRadioButton.setText("Masculino");
			femaleRadioButton.setText("Femenino");
		}
	}

	public void update() {
		nameTextField.setText(character.getName());
	}

	public void setCharacter(CharacterPlayer character) {
		this.character = character;
		nameTextField.setText(character.getName());
		if (character.getSex() == SexType.MALE) {
			maleRadioButton.setSelected(true);
		} else {
			femaleRadioButton.setSelected(true);
		}
	}

	public void addSexChangedListener(SexChangedListener listener) {
		sexChangedListeners.add(listener);
	}

	class ChangeSexListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (maleRadioButton.isSelected()) {
				character.setSex(SexType.MALE);
			} else {
				character.setSex(SexType.FEMALE);
			}
			for (SexChangedListener listener : sexChangedListeners) {
				listener.sexChanged(character);
			}
		}
	}

}
