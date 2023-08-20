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

import javax.swing.SwingConstants;

import com.softwaremagico.librodeesher.gui.elements.TitleBackgroundPanel;
import com.softwaremagico.librodeesher.gui.elements.TitleLabel;
import com.softwaremagico.librodeesher.gui.style.BaseTitleLine;

public class EquipmentTitle extends BaseTitleLine {
	private static final long serialVersionUID = -6194140531984171426L;

	public EquipmentTitle() {
		super();
		setElements();
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
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 0;
		TitleLabel selectedLabel = new TitleLabel("Borrar", SwingConstants.LEFT, EquipmentLine.SELECTION_COLUMN_WIDTH, columnHeight);
		add(new TitleBackgroundPanel(selectedLabel), gridBagConstraints);

		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.weightx = 0;
		TitleLabel itemNameLabel = new TitleLabel("Equipo", SwingConstants.LEFT, EquipmentLine.EQUIPMENT_NAME_WIDTH, columnHeight);
		add(new TitleBackgroundPanel(itemNameLabel), gridBagConstraints);

		gridBagConstraints.gridx = 32;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 1;
		TitleLabel itemDescriptionLabel = new TitleLabel("Descripción", SwingConstants.LEFT, EquipmentLine.EQUIPMENT_DESCRIPTION_WIDTH, columnHeight);
		add(new TitleBackgroundPanel(itemDescriptionLabel), gridBagConstraints);
	}

}