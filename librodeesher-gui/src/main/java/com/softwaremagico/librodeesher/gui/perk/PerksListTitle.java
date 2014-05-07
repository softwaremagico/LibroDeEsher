package com.softwaremagico.librodeesher.gui.perk;

/*
 * #%L
 * Libro de Esher GUI
 * %%
 * Copyright (C) 2007 - 2013 Softwaremagico
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

public class PerksListTitle extends BaseTitleLine {
	private final static Integer DEFAULT_COLUMN_WIDTH = 50;

	public PerksListTitle() {
		setElements();
	}

	private void setElements() {
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
		TitleLabel checkBoxLabel = new TitleLabel("", SwingConstants.LEFT, 35,
				columnHeight);
		add(new TitleBackgroundPanel(checkBoxLabel), gridBagConstraints);

		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 0;
		TitleLabel categoryNameLabel = new TitleLabel("Nombre", SwingConstants.LEFT,
				DEFAULT_COLUMN_WIDTH * 6, columnHeight);
		add(new TitleBackgroundPanel(categoryNameLabel), gridBagConstraints);

		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 0;
		TitleLabel costLabel = new TitleLabel("Coste", SwingConstants.CENTER, DEFAULT_COLUMN_WIDTH,
				columnHeight);
		add(new TitleBackgroundPanel(costLabel), gridBagConstraints);

		gridBagConstraints.gridx = 3;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 0;
		TitleLabel typeLabel = new TitleLabel("Tipo", SwingConstants.CENTER, DEFAULT_COLUMN_WIDTH * 2,
				columnHeight);
		add(new TitleBackgroundPanel(typeLabel), gridBagConstraints);

		gridBagConstraints.gridx = 4;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 1;
		TitleLabel descriptionLabel = new TitleLabel("Descripción", SwingConstants.LEFT);
		add(new TitleBackgroundPanel(descriptionLabel), gridBagConstraints);

	}
}
