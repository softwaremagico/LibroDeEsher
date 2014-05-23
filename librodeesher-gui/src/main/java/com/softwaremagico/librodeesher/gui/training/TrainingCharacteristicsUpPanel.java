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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.JPanel;

import com.softwaremagico.librodeesher.gui.elements.CloseButton;
import com.softwaremagico.librodeesher.gui.style.BaseDialog;
import com.softwaremagico.librodeesher.gui.style.BasePanel;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.characteristic.Characteristic;

public class TrainingCharacteristicsUpPanel extends BasePanel {
	private static final long serialVersionUID = -8559166972944477169L;
	private CharacterPlayer characterPlayer;
	private TrainingCompleteCharacteristicPanel characteristicPanel;
	private BaseDialog dialog;

	public TrainingCharacteristicsUpPanel(CharacterPlayer characterPlayer,
			List<Characteristic> availableCharacteristics, BaseDialog dialog) {
		this.characterPlayer = characterPlayer;
		this.dialog = dialog;
		// setResizable(false);
		setElements(availableCharacteristics);
	}

	private void setElements(List<Characteristic> availableCharacteristics) {
		setLayout(new GridBagLayout());
		GridBagConstraints gridBagConstraints = new GridBagConstraints();

		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		gridBagConstraints.insets = new Insets(2, 2, 2, 2);
		characteristicPanel = new TrainingCompleteCharacteristicPanel(characterPlayer, null, availableCharacteristics);
		characteristicPanel.setParent(dialog);
		add(characteristicPanel, gridBagConstraints);

		JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
		CloseButton closeButton = new CloseButton(dialog);
		buttonPanel.add(closeButton);
		gridBagConstraints.anchor = GridBagConstraints.LINE_END;
		gridBagConstraints.fill = GridBagConstraints.NONE;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 0;
		gridBagConstraints.insets = new Insets(2, 2, 2, 2);
		add(buttonPanel, gridBagConstraints);

	}

	public void setTraining(String training) {
		characteristicPanel.setTraining(training);
	}

	@Override
	public void update() {

	}

}
