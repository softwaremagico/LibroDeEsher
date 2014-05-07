package com.softwaremagico.librodeesher.gui.resistance;

/*
 * #%L
 * Libro de Esher
 * %%
 * Copyright (C) 2007 - 2012 Softwaremagico
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
import java.util.ArrayList;
import java.util.List;

import com.softwaremagico.librodeesher.gui.style.BasePanel;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.resistance.ResistanceType;

public class ResistancePanel extends BasePanel {
	private List<ResistanceLine> lines;

	private static final long serialVersionUID = -8596963992689639881L;

	public ResistancePanel() {
		lines = new ArrayList<>();
	}

	private void setElements(CharacterPlayer character) {
		this.removeAll();
		setLayout(new GridLayout(0, 1));

		Color background;
		for (int i = 0; i < ResistanceType.values().length; i++) {
			if (i % 2 == 0) {
				background = Color.WHITE;
			} else {
				background = Color.LIGHT_GRAY;
			}
			ResistanceLine resistanceLine = new ResistanceLine(ResistanceType.values()[i], character,
					background);
			lines.add(resistanceLine);
			add(resistanceLine);
		}
	}

	public void setCharacter(CharacterPlayer character) {
		setElements(character);
	}

	public void update() {
		for (ResistanceLine line : lines) {
			line.update();
		}
	}
}
