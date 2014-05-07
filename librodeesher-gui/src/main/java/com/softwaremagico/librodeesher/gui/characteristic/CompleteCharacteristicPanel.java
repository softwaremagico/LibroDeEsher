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

import com.softwaremagico.librodeesher.gui.elements.BaseScrollPanel;
import com.softwaremagico.librodeesher.gui.style.BaseFrame;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;

public class CompleteCharacteristicPanel extends BaseScrollPanel {
	private static final long serialVersionUID = -1990965342534961094L;
	private CharacteristicPanel characteristicPanel;
	private CharacteristicTitleLine title;

	public CompleteCharacteristicPanel() {
		title = new CharacteristicTitleLine();
		addTitle(title);
		characteristicPanel = new CharacteristicPanel();
		addBody(characteristicPanel);
	}

	public void setPotential() {
		characteristicPanel.setPotential();
	}

	public void setCharacter(CharacterPlayer character, boolean summaryMode) {
		characteristicPanel.setCharacter(character, summaryMode);
		title.setSummaryMode(summaryMode);
	}

	public void setParentWindow(BaseFrame characteristicsWindow) {
		characteristicPanel.setParentWindow(characteristicsWindow);
	}

}
