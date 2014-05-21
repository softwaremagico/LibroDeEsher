package com.softwaremagico.librodeesher.gui.characteristic;

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

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import com.softwaremagico.librodeesher.gui.style.BaseFrame;
import com.softwaremagico.librodeesher.gui.style.BasePanel;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.characteristic.Characteristic;

public abstract class CharacteristicUpPanel extends BasePanel {
	private static final long serialVersionUID = 3613694602175558891L;
	private List<CharacteristicUpLine> lines;
	private List<Characteristic> characteristics;

	public CharacteristicUpPanel(CharacterPlayer character, List<Characteristic> characteristics) {
		lines = new ArrayList<>();
		this.characteristics = characteristics;
		if (characteristics != null && !characteristics.isEmpty()) {
			setElements(character);
		}
	}

	private void setElements(CharacterPlayer character) {
		this.removeAll();
		setLayout(new GridLayout(0, 1));

		Color background;
		for (int i = 0; i < characteristics.size(); i++) {
			if (i % 2 == 0) {
				background = Color.WHITE;
			} else {
				background = Color.LIGHT_GRAY;
			}

			CharacteristicUpLine characteristicLine = createLine(character, characteristics.get(i),
					background);
			lines.add(characteristicLine);
			add(characteristicLine);
		}
	}

	public abstract CharacteristicUpLine createLine(CharacterPlayer character, Characteristic characteristic,
			Color background);

	public void update() {
		for (CharacteristicUpLine line : lines) {
			line.update();
		}
	}

	public void setParentWindow(BaseFrame window) {
		for (CharacteristicUpLine line : lines) {
			line.setParentWindow(window);
		}
	}

	public List<CharacteristicUpLine> getLines() {
		return lines;
	}
}
