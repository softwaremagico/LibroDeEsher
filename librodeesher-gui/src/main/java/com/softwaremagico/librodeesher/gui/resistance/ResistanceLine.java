package com.softwaremagico.librodeesher.gui.resistance;

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
import java.awt.GridLayout;

import javax.swing.SwingConstants;

import com.softwaremagico.librodeesher.gui.elements.BoldListLabel;
import com.softwaremagico.librodeesher.gui.elements.ListBackgroundPanel;
import com.softwaremagico.librodeesher.gui.style.BasicLine;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.resistance.ResistanceType;

public class ResistanceLine extends BasicLine {
	private static final long serialVersionUID = -5493663863154163209L;
	private BoldListLabel resistanceLabel, resistanceTotalLabel;
	private CharacterPlayer character;
	private ResistanceType resistance;

	public ResistanceLine(ResistanceType resistance, CharacterPlayer character, Color background) {
		this.character = character;
		this.resistance = resistance;
		setElements(background);
		setBackground(background);
	}

	private void setElements(Color background) {
		this.removeAll();
		setLayout(new GridLayout(1, 2));

		resistanceLabel = new BoldListLabel(resistance.getAbbreviature(), SwingConstants.LEFT);
		add(new ListBackgroundPanel(resistanceLabel, background));

		resistanceTotalLabel = new BoldListLabel("0", SwingConstants.RIGHT);
		add(new ListBackgroundPanel(resistanceTotalLabel, background));

	}

	public void update() {
		if (resistanceTotalLabel != null && character != null && character.areCharacteristicsConfirmed()) {
			resistanceTotalLabel.setText(character.getResistanceBonus(resistance).toString());
		}
	}
}
