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

import com.softwaremagico.librodeesher.gui.characteristic.CompleteCharacteristicUpPanel;
import com.softwaremagico.librodeesher.gui.style.BaseFrame;
import com.softwaremagico.librodeesher.gui.style.BasePanel;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;

public class HistoryCompleteCharacteristicPanel extends CompleteCharacteristicUpPanel {
	private static final long serialVersionUID = 3944923090293710832L;

	public HistoryCompleteCharacteristicPanel(CharacterPlayer character, BaseFrame parent) {
		super(character, parent);
	}

	@Override
	public BasePanel createBodyPanel() {
		return new HistoryCharacteristicPanel(getCharacter());
	}

}
