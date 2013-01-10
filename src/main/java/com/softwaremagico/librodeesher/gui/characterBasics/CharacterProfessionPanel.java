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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import com.softwaremagico.librodeesher.gui.elements.BaseLabel;
import com.softwaremagico.librodeesher.gui.style.BasePanel;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;

public class CharacterProfessionPanel extends BasePanel {
	private static final long serialVersionUID = -4572529165916501939L;
	private BaseLabel professionLabel;
	private BaseLabel trainingLabel;
	private JComboBox<String> professionComboBox;
	private CharacterPlayer character;
	private CharacterLevelPanel levelPanel;
	private boolean updatingProfession = false;
	private CharacterPanel parent;

	protected CharacterProfessionPanel(CharacterPanel parent) {
		this.parent = parent;
		setElements();
		setDefaultSize();
	}

	protected void setElements() {
		this.removeAll();
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		professionLabel = new BaseLabel("Profesión:");
		c.anchor = GridBagConstraints.LINE_START;
		c.ipadx = xPadding;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0;
		add(professionLabel, c);

		professionComboBox = new JComboBox<>();
		professionComboBox.addActionListener(new ChangeProfessionListener());
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 0;
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 1;
		add(professionComboBox, c);

		trainingLabel = new BaseLabel("Adiestramiento:");
		c.anchor = GridBagConstraints.LINE_START;
		c.ipadx = xPadding;
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 0;
		add(trainingLabel, c);

		JTextField trainingTextField = new JTextField();
		trainingTextField.setEditable(false);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 0;
		c.gridx = 1;
		c.gridy = 1;
		c.weightx = 1;
		add(trainingTextField, c);
	}

	public String getSelectedProfession() {
		if (professionComboBox != null) {
			return (String) professionComboBox.getSelectedItem();
		}
		return null;
	}

	public void sizeChanged() {
		if (this.getWidth() < 230) {
			professionLabel.setText("Prf.:");
			trainingLabel.setText("Ad.:");
		} else if (this.getWidth() < 280) {
			professionLabel.setText("Prof.:");
			trainingLabel.setText("Adst.:");
		} else {
			professionLabel.setText("Profesión:");
			trainingLabel.setText("Adiestram.:");
		}
	}

	public void update() {
		if (character != null && character.getRace() != null) {
			updateProfessionComboBox(character.getRace().availableProfessions());
		}
		professionComboBox.setEnabled(!character.areCharacteristicsConfirmed());
	}

	private void updateProfessionComboBox(List<String> professions) {
		updatingProfession = true;
		if (professionComboBox != null) {
			professionComboBox.removeAllItems();
			Collections.sort(professions);
			for (String profession : professions) {
				professionComboBox.addItem(profession);
			}
			if (character != null) {
				if (character.getProfession() != null) {
					professionComboBox.setSelectedItem(character.getProfession().getName());
					if (getSelectedProfession() != character.getProfession().getName()) {
						updateProfession();
					}
				}
			}
		}
		updatingProfession = false;
	}

	public void setLevelPanel(CharacterLevelPanel levelPanel) {
		this.levelPanel = levelPanel;
	}

	private void updateLevelPanel() {
		if (levelPanel != null) {
			levelPanel.update();
		}
	}

	private void updateProfession() {
		if (character != null) {
			character.setProfession(getSelectedProfession());
			updateLevelPanel();
		}
		parent.updateProfession();
	}

	public void setCharacter(CharacterPlayer character) {
		this.character = character;
		if (character.getRace() != null) {
			updateProfessionComboBox(character.getRace().availableProfessions());
			character.setProfession(getSelectedProfession());
		}
	}

	class ChangeProfessionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (!updatingProfession) {
				updateProfession();
			}
		}
	}
}
