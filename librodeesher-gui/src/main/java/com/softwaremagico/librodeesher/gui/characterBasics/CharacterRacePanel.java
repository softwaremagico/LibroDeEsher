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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JComboBox;

import com.softwaremagico.librodeesher.gui.elements.BaseLabel;
import com.softwaremagico.librodeesher.gui.style.BasePanel;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.race.RaceFactory;
import com.softwaremagico.librodeesher.pj.race.exceptions.InvalidRaceException;
import com.softwaremagico.log.EsherLog;

public class CharacterRacePanel extends BasePanel {
	private static final long serialVersionUID = 178890486518380989L;
	private BaseLabel raceLabel;
	private BaseLabel cultureLabel;
	private JComboBox<String> raceComboBox;
	private JComboBox<String> cultureComboBox;
	private CharacterProfessionPanel professionPanel;
	private CharacterPlayer character;
	private CharacterPanel parent;
	private boolean enableRaceComboBox = true, enableCultureComboBox = true;

	protected CharacterRacePanel(CharacterPanel parent) {
		this.parent = parent;
		setElements();
		setDefaultSize();
	}

	protected void setElements() {
		this.removeAll();
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		raceLabel = new BaseLabel("Raza:");
		c.anchor = GridBagConstraints.LINE_START;
		c.ipadx = xPadding;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0;
		add(raceLabel, c);

		raceComboBox = new JComboBox<>();
		updateRaceComboBox();
		raceComboBox.addActionListener(new ChangeRaceListener());
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 0;
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 1;
		add(raceComboBox, c);

		cultureLabel = new BaseLabel("Cultura:");
		c.anchor = GridBagConstraints.LINE_START;
		c.ipadx = xPadding;
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 0;
		add(cultureLabel, c);

		cultureComboBox = new JComboBox<>();
		updateCultureComboBox();
		cultureComboBox.addActionListener(new ChangeCultureListener());
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 0;
		c.gridx = 1;
		c.gridy = 1;
		c.weightx = 1;
		add(cultureComboBox, c);
	}

	public String getSelectedRace() {
		if (raceComboBox != null) {
			return (String) raceComboBox.getSelectedItem();
		}
		return null;
	}

	public String getSelectedCulture() {
		if (cultureComboBox != null) {
			return (String) cultureComboBox.getSelectedItem();
		}
		return null;
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

	private void updateRaceComboBox() {
		if (raceComboBox != null) {
			List<String> races = new ArrayList<>(RaceFactory.getAvailableRaces());
			Collections.sort(races);
			for (String race : races) {
				raceComboBox.addItem(race);
			}
			if (character != null) {
				character.setRace(getSelectedRace());
			}
		}
	}

	private void updateCultureComboBox() {
		enableCultureComboBox = false;
		if (cultureComboBox != null) {
			cultureComboBox.removeAllItems();
			try {
				List<String> cultures;
				try {
					cultures = RaceFactory.getRace(getSelectedRace()).getAvailableCultures();
					Collections.sort(cultures);
					for (String culture : cultures) {
						cultureComboBox.addItem(culture);
					}
				} catch (InvalidRaceException e) {
					// ShowMessage.showErrorMessage(e.getMessage(), "Error");
				}
			} catch (NullPointerException npe) {
				EsherLog.errorMessage(CharacterRacePanel.class.getName(), npe);
			}
			if (character != null && character.getCulture() != null) {
				cultureComboBox.setSelectedItem(character.getCulture().getName());
				if (!getSelectedCulture().equals(character.getCulture().getName())) {
					updateCulture();
				}
			}
		}
		enableCultureComboBox = true;
	}

	public void setProfessionPanel(CharacterProfessionPanel professionPanel) {
		this.professionPanel = professionPanel;
	}

	public void setCharacter(CharacterPlayer character) {
		this.character = character;
		// character.setRace(getSelectedRace());
		// character.setCulture(getSelectedCulture());
		if (character.getRace() != null) {
			enableRaceComboBox = false;
			raceComboBox.setSelectedItem(character.getRace().getName());
			enableRaceComboBox = true;
		} else {
			character.setRace(getSelectedRace());
		}
		if (character.getCulture() != null) {
			enableCultureComboBox = false;
			cultureComboBox.setSelectedItem(character.getCulture().getName());
			enableCultureComboBox = true;
		} else {
			character.setCulture(getSelectedCulture());
		}
	}

	private void updateProfessionPanel() {
		if (professionPanel != null) {
			professionPanel.update();
		}
	}

	private void updateCulture() {
		if (character != null) {
			character.setCulture(getSelectedCulture());
			updateSkillList();
		}
	}

	@Override
	public void update() {
		enableRaceComboBox = false;
		raceComboBox.setEnabled(!character.areCharacteristicsConfirmed());
		enableRaceComboBox = true;
		updateCultureComboBox();
		updateProfessionPanel();
		if (character.getCulture() != null) {
			enableCultureComboBox = false;
			cultureComboBox.setSelectedItem(character.getCulture().getName());
			enableCultureComboBox = true;
		}
		cultureComboBox.setEnabled(!character.areCharacteristicsConfirmed());
	}

	private void updateSkillList() {
		parent.updateSkills();
	}

	class ChangeRaceListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (enableRaceComboBox) {
				if (character != null) {
					character.setRace(getSelectedRace());
				}
				updateCultureComboBox();
				updateProfessionPanel();
				updateSkillList();
			}
		}
	}

	class ChangeCultureListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (enableCultureComboBox && character != null && cultureComboBox.getSelectedItem() != null) {
				updateCulture();
			}
		}
	}

}
