package com.softwaremagico.librodeesher.gui.history;
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

import com.softwaremagico.librodeesher.gui.elements.BaseScrollPanel;
import com.softwaremagico.librodeesher.gui.style.BaseFrame;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;

public class HistoryCompleteCharacteristicPanel extends BaseScrollPanel {
	private static final long serialVersionUID = 3944923090293710832L;
	private BaseFrame parent;
	CharacterPlayer character;
	private HistoryCharacteristicTitle title;
	private HistoryCharacteristicPanel characteristicPanel;

	public HistoryCompleteCharacteristicPanel(CharacterPlayer character, BaseFrame parent) {
		this.character = character;
		this.parent = parent;
		title = new HistoryCharacteristicTitle();
		addTitle(title);
		characteristicPanel = new HistoryCharacteristicPanel(character, this);
		characteristicPanel.setParentWindow(parent);
		addBody(characteristicPanel);
	}

	public void update() {
		characteristicPanel.update();
	}

}
