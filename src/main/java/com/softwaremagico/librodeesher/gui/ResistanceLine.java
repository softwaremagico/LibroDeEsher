package com.softwaremagico.librodeesher.gui;
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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;

import com.softwaremagico.librodeesher.gui.style.BasicLine;
import com.softwaremagico.librodeesher.pj.resistance.ResistanceType;

public class ResistanceLine extends BasicLine {
	private static final long serialVersionUID = -5493663863154163209L;
	private JLabel resistanceLabel;
	private JLabel resistanceTotalLabel;
	

	public ResistanceLine(ResistanceType resistance, Integer total,  Color background) {
		setElements(resistance, total);
		setBackground(background);
	}
	
	private void setElements(ResistanceType resistance, Integer total) {
		this.removeAll();
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		resistanceLabel = new JLabel(resistance.getAbbreviature());
		resistanceLabel.setMinimumSize(new Dimension(20, textDefaultHeight));
		c.anchor = GridBagConstraints.LINE_START;
		c.ipadx = xPadding;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		add(resistanceLabel, c);	
		
		resistanceTotalLabel = new JLabel(total.toString());
		resistanceTotalLabel.setMinimumSize(new Dimension(20, textDefaultHeight));
		c.anchor = GridBagConstraints.LINE_START;
		c.ipadx = xPadding;
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 1;
		add(resistanceTotalLabel, c);
	}
}
