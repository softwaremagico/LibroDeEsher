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

import com.softwaremagico.librodeesher.gui.elements.BaseScrollPanel;
import com.softwaremagico.librodeesher.gui.style.BaseFrame;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;

public abstract class CompleteCharacteristicUpPanel extends BaseScrollPanel {
	private static final long serialVersionUID = 3944923090293710832L;
	private CharacterPlayer character;
	private CharacteristicUpTitle title;
	private CharacteristicUpPanel characteristicPanel;

	public CompleteCharacteristicUpPanel(CharacterPlayer character, BaseFrame parent) {
		this.character = character;
		title = new CharacteristicUpTitle();
		addTitle(title);
		characteristicPanel = createBodyPanel();
		characteristicPanel.setParentWindow(parent);
		setBody(characteristicPanel);
	}

	public abstract CharacteristicUpPanel createBodyPanel();

	public CharacterPlayer getCharacter() {
		return character;
	}

	@Override
	public void update() {
		characteristicPanel.update();
	}

	public void sizeChanged() {
		if (title != null) {
			title.sizeChanged();
		}
	}

}
