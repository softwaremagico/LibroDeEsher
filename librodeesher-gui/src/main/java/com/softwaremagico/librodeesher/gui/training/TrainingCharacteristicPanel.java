package com.softwaremagico.librodeesher.gui.training;
/*
 * #%L
 * Libro de Esher (GUI)
 * %%
 * Copyright (C) 2007 - 2014 Softwaremagico
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
import java.util.List;

import com.softwaremagico.librodeesher.gui.characteristic.CharacteristicUpLine;
import com.softwaremagico.librodeesher.gui.characteristic.CharacteristicUpPanel;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.characteristic.Characteristic;

public class TrainingCharacteristicPanel extends CharacteristicUpPanel {
	private static final long serialVersionUID = -3419196143471099107L;

	public TrainingCharacteristicPanel(CharacterPlayer character, List<Characteristic> characteristics) {
		super(character, characteristics);
	}

	@Override
	public CharacteristicUpLine createLine(CharacterPlayer character, Characteristic characteristic,
			Color background) {
		return new TrainingCharacteristicLine(character, characteristic, background);
	}

	public void setTraining(String training) {
		for (CharacteristicUpLine line : getLines()) {
			((TrainingCharacteristicLine) line).setTraining(training);
		}
	}
}
