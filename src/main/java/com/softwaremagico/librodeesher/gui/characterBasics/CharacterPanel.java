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
import java.awt.Insets;

import com.softwaremagico.librodeesher.gui.style.BasePanel;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;

public class CharacterPanel extends BasePanel {
	private static final long serialVersionUID = 3922505445539868950L;
	private CharacterBasicsPanel characterBasics;
	private CharacterRacePanel characterRacePanel;
	private CharacterProfessionPanel characterProfessionPanel;
	private CharacterLevelPanel characterLevelPanel;

	public CharacterPanel() {
		setElements();
	}

	protected void setElements() {
		this.removeAll();
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		characterBasics = new CharacterBasicsPanel();
		characterBasics
				.setBounds(xPadding, xPadding, characterBasics.getWidth(), characterBasics.getHeight());
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 5;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.5;
		c.insets = new Insets(1, 5, 1, 5);
		add(characterBasics, c);

		characterRacePanel = new CharacterRacePanel();
		characterRacePanel.setBounds(xPadding, xPadding, characterBasics.getWidth(),
				characterBasics.getHeight());
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 5;
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 0.5;
		c.insets = new Insets(1, 5, 1, 5);
		add(characterRacePanel, c);

		characterProfessionPanel = new CharacterProfessionPanel();
		characterRacePanel.setProfessionPanel(characterProfessionPanel);
		characterProfessionPanel.setBounds(xPadding, xPadding, characterBasics.getWidth(),
				characterBasics.getHeight());
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 5;
		c.gridx = 2;
		c.gridy = 0;
		c.weightx = 0.5;
		c.insets = new Insets(1, 5, 1, 5);
		add(characterProfessionPanel, c);
		//characterProfessionPanel.update();

		characterLevelPanel = new CharacterLevelPanel();
		characterLevelPanel.setBounds(xPadding, xPadding, characterBasics.getWidth(),
				characterBasics.getHeight());
		characterProfessionPanel.setLevelPanel(characterLevelPanel);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 5;
		c.gridx = 3;
		c.gridy = 0;
		c.weightx = 0.5;
		c.insets = new Insets(1, 5, 1, 5);
		add(characterLevelPanel, c);
	}
	
	public void setCharacter(CharacterPlayer character){
		characterBasics.setCharacter(character);
		characterRacePanel.setCharacter(character);
		characterProfessionPanel.setCharacter(character);
		characterLevelPanel.setCharacter(character);
	}

	public void sizeChanged() {
		if (characterBasics != null) {
			characterBasics.sizeChanged();
		}
		if (characterProfessionPanel != null) {
			characterProfessionPanel.sizeChanged();
		}
		if (characterRacePanel != null) {
			characterRacePanel.sizeChanged();
		}
		if (characterLevelPanel != null) {
			characterLevelPanel.sizeChanged();
		}
	}
	
	public void update(){
		if(characterBasics!=null){
			characterBasics.update();
		}
		if (characterRacePanel != null) {
			characterRacePanel.update();
		}
		if (characterProfessionPanel != null) {
			characterProfessionPanel.update();
		}
		if (characterLevelPanel != null) {
			characterLevelPanel.update();
		}
	}

}
