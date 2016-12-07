package com.softwaremagico.librodeesher.gui.equipment;
/*
 * #%L
 * Libro de Esher (GUI)
 * %%
 * Copyright (C) 2007 - 2016 Softwaremagico
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

import com.softwaremagico.librodeesher.gui.style.BaseFrame;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;

public class InsertEquipmentWindow extends BaseFrame {
	private static final long serialVersionUID = 2715820195499102991L;
	private CharacterPlayer characterPlayer;
	private ResumeEquipmentListPanel equipmentListPanel;
	private EquipmentCreatorPanel equipmentCreatorPanel;

	public InsertEquipmentWindow(CharacterPlayer characterPlayer) {
		this.characterPlayer = characterPlayer;
		defineWindow(600, 420);
		//setResizable(false);
		setElements();
	}

	private void setElements() {
		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
		GridBagConstraints constraints = new GridBagConstraints();
		
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.LINE_START;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 2;
		constraints.gridheight = 3;
		constraints.weightx = 1;
		constraints.weighty = 1;
		equipmentCreatorPanel = new EquipmentCreatorPanel();
		add(equipmentCreatorPanel, constraints);

		equipmentListPanel = new ResumeEquipmentListPanel(characterPlayer);
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.LINE_START;
		constraints.gridy = 3;
		constraints.gridwidth = 2;
		constraints.gridheight = 3;
		add(equipmentListPanel, constraints);

	}

	@Override
	public void updateFrame() {
		
	}
}
