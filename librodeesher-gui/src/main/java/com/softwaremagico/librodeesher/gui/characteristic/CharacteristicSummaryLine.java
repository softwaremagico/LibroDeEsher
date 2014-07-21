package com.softwaremagico.librodeesher.gui.characteristic;

/*
 * #%L
 * Libro de Esher
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

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.SwingConstants;

import com.softwaremagico.librodeesher.gui.elements.BoldListLabel;
import com.softwaremagico.librodeesher.gui.elements.ListBackgroundPanel;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.characteristic.Characteristic;

public class CharacteristicSummaryLine extends CharacteristicLine {
	private static final long serialVersionUID = 3396829275749133929L;

	public CharacteristicSummaryLine(CharacterPlayer character, Characteristic characteristic,
			Color background) {
		super(character, characteristic, background);
	}

	@Override
	protected void setElements(Color background) {
		this.removeAll();
		setLayout(new GridLayout(1, 2));

		characteristicLabel = new BoldListLabel(characteristic.getAbbreviature().toString(), SwingConstants.LEFT);
		add(new ListBackgroundPanel(characteristicLabel, background));

		totalLabel = new BoldListLabel("0", SwingConstants.RIGHT);
		add(new ListBackgroundPanel(totalLabel, background));
	}

	@Override
	public void update() {
		if (totalLabel != null && character.areCharacteristicsConfirmed()) {
			totalLabel.setText(character.getCharacteristicTotalBonus(characteristic.getAbbreviature())
					.toString());
		}
	}

}
