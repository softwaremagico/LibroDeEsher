package com.softwaremagico.librodeesher.gui.history;
/*
 * #%L
 * Libro de Esher GUI
 * %%
 * Copyright (C) 2007 - 2013 Softwaremagico
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
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.SwingConstants;

import com.softwaremagico.librodeesher.basics.ShowMessage;
import com.softwaremagico.librodeesher.gui.elements.BoldListLabel;
import com.softwaremagico.librodeesher.gui.elements.ListBackgroundPanel;
import com.softwaremagico.librodeesher.gui.style.BaseFrame;
import com.softwaremagico.librodeesher.gui.style.BaseLine;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.characteristic.Characteristic;

public class HistoryCharacteristicLine extends BaseLine {
	private static final long serialVersionUID = 5817155091343950674L;
	protected CharacterPlayer character;
	protected Characteristic characteristic;
	private BoldListLabel characteristicLabel, temporalText, potentialText;
	private BaseFrame parentWindow;
	private JButton updateButton;

	public HistoryCharacteristicLine(CharacterPlayer character, Characteristic characteristic,
			Color background) {
		this.character = character;
		this.characteristic = characteristic;
		setElements(background);
		setBackground(background);
	}

	protected void setElements(Color background) {
		this.removeAll();
		setLayout(new GridLayout(1, 0, 3, 3));

		characteristicLabel = new BoldListLabel(characteristic.getAbbreviature(), SwingConstants.LEFT);
		add(new ListBackgroundPanel(characteristicLabel, background));

		temporalText = new BoldListLabel("0");
		add(new ListBackgroundPanel(temporalText, background));

		potentialText = new BoldListLabel("0");
		add(new ListBackgroundPanel(potentialText, background));

		updateButton = new JButton("Subir");
		updateButton.addActionListener(new UpdateButtonListener());
		add(updateButton);

		update();
	}

	public void update() {
		temporalText.setText(character.getCharacteristicTemporalValue(characteristic.getAbbreviature())
				.toString());
		potentialText.setText(character.getCharacteristicPotentialValues(characteristic.getAbbreviature())
				.toString());
		updateButton.setEnabled(character.getRemainingHistorialPoints() > 0
				&& (character.getCharacteristicPotentialValues(characteristic.getAbbreviature())
						- character.getCharacteristicTemporalValue(characteristic.getAbbreviature()) > 0));
	}

	public void setParentWindow(BaseFrame window) {
		parentWindow = window;
	}

	public class UpdateButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (ShowMessage
					.showQuestionMessage(
							parentWindow,
							"Esta acción invertirá un punto de historial para subr la característica \""
									+ characteristic.getName()
									+ "\" con una diferencia entre el valor temporal y potencial de: "
									+ (character.getCharacteristicPotentialValues(characteristic
											.getAbbreviature()) - character
											.getCharacteristicTemporalValue(characteristic.getAbbreviature()))
									+ ".\n Esta acción es permante. ¿Está seguro de continuar?",
							"Aumento de característica")) {
				character.setCharacteristicHistorialUpdate(characteristic.getAbbreviature());
				parentWindow.updateFrame();
			}
		}
	}
}
