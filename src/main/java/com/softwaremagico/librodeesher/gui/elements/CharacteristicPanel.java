package com.softwaremagico.librodeesher.gui.elements;

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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

import com.softwaremagico.librodeesher.gui.style.BasePanel;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;

public class CharacteristicPanel extends BasePanel {
	private static final long serialVersionUID = -8184859092551925077L;
	private List<CharacteristicLine> lines;

	public CharacteristicPanel() {
		lines = new ArrayList<>();
	}

	private void setElements(CharacterPlayer character, boolean summaryMode) {
		this.removeAll();
		setLayout(new GridBagLayout());
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.ipady = yPadding;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.gridx = 0;

		Color background;
		for (int i = 0; i < character.getCharacteristics().size(); i++) {
			if (i % 2 == 0) {
				background = Color.WHITE;
			} else {
				background = Color.LIGHT_GRAY;
			}
			CharacteristicLine characteristicLine = new CharacteristicLine(character.getCharacteristics()
					.get(i), background);
			gridBagConstraints.gridy = i;
			characteristicLine.summaryMode(summaryMode);
			lines.add(characteristicLine);
			add(characteristicLine, gridBagConstraints);
		}
	}

	public void setCharacter(CharacterPlayer character, boolean summaryMode) {
		setElements(character, summaryMode);
	}
}
