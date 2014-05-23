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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.softwaremagico.librodeesher.basics.ShowMessage;
import com.softwaremagico.librodeesher.gui.characteristic.CharacteristicUpLine;
import com.softwaremagico.librodeesher.gui.style.BaseDialog;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.characteristic.Characteristic;
import com.softwaremagico.librodeesher.pj.characteristic.CharacteristicRoll;

public class TrainingCharacteristicLine extends CharacteristicUpLine {
	private static final long serialVersionUID = -9002920559029367594L;
	private String training;
	private BaseDialog parent;

	public TrainingCharacteristicLine(CharacterPlayer character, Characteristic characteristic, Color background) {
		super(character, characteristic, background);
	}

	@Override
	public void addAcceptListener() {
		getUpdateButton().addActionListener(new UpdateButtonListener());
	}

	public class UpdateButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			CharacteristicRoll characteristicRoll = character.setCharacteristicTrainingUpdate(
					characteristic.getAbbreviature(), training);
			ShowMessage
					.showInfoMessage(
							"El resultado de los dados es: ["
									+ characteristicRoll.getRoll().getFirstDice()
									+ ","
									+ characteristicRoll.getRoll().getSecondDice()
									+ "]\n"
									+ "Por tanto, la característica ha cambiado en: "
									+ Characteristic.getCharacteristicUpgrade(
											characteristicRoll.getCharacteristicTemporalValue(),
											characteristicRoll.getCharacteristicPotentialValue(),
											characteristicRoll.getRoll()), "Característica aumentada!");
			if (parent != null) {
				parent.dispose();
			}
		}
	}

	@Override
	public void setAcceptEnabled() {
		getUpdateButton().setEnabled(true);
	}

	public void setTraining(String training) {
		this.training = training;
	}

	public void setParent(BaseDialog parent) {
		this.parent = parent;
	}
}
