package com.softwaremagico.librodeesher.gui.magicitem;

/*
 * #%L
 * Libro de Esher (GUI)
 * %%
 * Copyright (C) 2007 - 2014 Softwaremagico
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
import com.softwaremagico.librodeesher.pj.equipment.ObjectBonus;

public class BonusLine extends BaseLine {
	private static final long serialVersionUID = -6440213804132215064L;
	private BoldListLabel bonusNameLabel, bonusValue;
	private ObjectBonus bonus;

	public BonusLine(ObjectBonus bonus, Color background) {
		this.bonus = bonus;
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
		if (bonus != null) {
			bonusNameLabel = new BoldListLabel(bonus.getBonusName(), SwingConstants.LEFT, 200, columnHeight);
		} else {
			bonusNameLabel = new BoldListLabel("", SwingConstants.LEFT, 200, columnHeight);
		}
		add(new ListBackgroundPanel(bonusNameLabel, getDefaultBackground()), gridBagConstraints);

		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 0.3;
		if (bonus != null) {
			bonusValue = new BoldListLabel(bonus.getBonus() + "", 50, columnHeight);
		} else {
			bonusValue = new BoldListLabel("", 50, columnHeight);
		}
		add(new ListBackgroundPanel(bonusValue, getDefaultBackground()), gridBagConstraints);
	}

}
