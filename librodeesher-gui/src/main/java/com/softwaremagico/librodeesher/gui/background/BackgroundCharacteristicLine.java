package com.softwaremagico.librodeesher.gui.background;

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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.softwaremagico.librodeesher.gui.ShowMessage;
import com.softwaremagico.librodeesher.gui.characteristic.CharacteristicUpLine;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.characteristic.Characteristic;
import com.softwaremagico.librodeesher.pj.characteristic.CharacteristicRoll;

public class BackgroundCharacteristicLine extends CharacteristicUpLine {
	private static final long serialVersionUID = 5817155091343950674L;

	public BackgroundCharacteristicLine(CharacterPlayer character, Characteristic characteristic, Color background) {
		super(character, characteristic, background);
	}

	@Override
	public void addAcceptListener() {
		getUpdateButton().addActionListener(new UpdateButtonListener());
	}

	public class UpdateButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (ShowMessage.showQuestionMessage(
					getParentWindow(),
					"Esta acción invertirá un punto de historial para subir la característica \""
							+ characteristic.getName()
							+ "\" con una diferencia entre el valor temporal y potencial de: "
							+ (character.getCharacteristicPotentialValue(characteristic.getAbbreviature()) - character
									.getCharacteristicTemporalValue(characteristic.getAbbreviature()))
							+ ".\n Esta acción es permante. ¿Está seguro de continuar?", "Aumento de característica")) {

				CharacteristicRoll characteristicRoll = character.setCharacteristicBackgroundUpdate(characteristic
						.getAbbreviature());
				ShowMessage.showInfoMessage(
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

				getParentWindow().updateFrame();
			}
		}
	}

	@Override
	public void setAcceptEnabled() {
		getUpdateButton().setEnabled(
				character.getRemainingBackgroundPoints() > 0
						&& (character.getCharacteristicPotentialValue(characteristic.getAbbreviature())
								- character.getCharacteristicTemporalValue(characteristic.getAbbreviature()) > 0));
	}
}
