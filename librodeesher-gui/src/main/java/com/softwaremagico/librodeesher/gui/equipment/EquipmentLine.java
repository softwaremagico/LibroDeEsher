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

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.SwingConstants;

import com.softwaremagico.librodeesher.gui.elements.BoldListLabel;
import com.softwaremagico.librodeesher.gui.elements.ListBackgroundPanel;
import com.softwaremagico.librodeesher.gui.style.BaseLine;
import com.softwaremagico.librodeesher.pj.equipment.Equipment;

public class EquipmentLine extends BaseLine {
	private static final long serialVersionUID = -6440213804132215064L;
	private BoldListLabel equipmentNameLabel, equipmentDescription;
	private Equipment equipment;

	public EquipmentLine(Equipment equipment, Color background) {
		this.equipment = equipment;
		setDefaultBackground(background);
		setElements();
		setBackground(background);
	}

	@Override
	public void update() {

	}

	protected void setElements() {
		this.removeAll();
		setLayout(new GridBagLayout());
		GridBagConstraints gridBagConstraints = new GridBagConstraints();

		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.weighty = 0;

		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.weightx = 0.6;
		if (equipment != null) {
			equipmentNameLabel = new BoldListLabel(equipment.getName(), SwingConstants.LEFT, columnHeight);
		} else {
			equipmentNameLabel = new BoldListLabel("", SwingConstants.LEFT, columnHeight);
		}
		add(new ListBackgroundPanel(equipmentNameLabel, getDefaultBackground()), gridBagConstraints);

		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 0.3;
		if (equipment != null) {
			equipmentDescription = new BoldListLabel(equipment.getDescription(), columnHeight);
		} else {
			equipmentDescription = new BoldListLabel("", 50, columnHeight);
		}
		add(new ListBackgroundPanel(equipmentDescription, getDefaultBackground()), gridBagConstraints);
	}

}
