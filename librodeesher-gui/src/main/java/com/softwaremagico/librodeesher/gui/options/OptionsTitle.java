package com.softwaremagico.librodeesher.gui.options;
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

public class OptionsTitle  extends BaseTitleLine {
	private static final long serialVersionUID = -5046443003006886227L;

	protected OptionsTitle() {
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
		gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
		gridBagConstraints.weightx = 1;
		TitleLabel optionsLabel = new TitleLabel("Listado", SwingConstants.LEFT);
		add(new TitleBackgroundPanel(optionsLabel), gridBagConstraints);
	}

}
