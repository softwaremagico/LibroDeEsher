package com.softwaremagico.librodeesher.gui.characteristic;
/*
 * #%L
 * Libro de Esher
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

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import com.softwaremagico.librodeesher.gui.elements.BaseLabel;
import com.softwaremagico.librodeesher.gui.elements.CloseButton;
import com.softwaremagico.librodeesher.gui.elements.PointsCounterTextField;
import com.softwaremagico.librodeesher.gui.style.BaseFrame;
import com.softwaremagico.librodeesher.gui.style.BasicButton;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.characteristic.Characteristics;

public class CharacteristicsWindow extends BaseFrame {
	private static final long serialVersionUID = -2484205144800968016L;
	private CompleteCharacteristicPanel characteristicPanel;
	private BaseLabel spentPointsLabel;
	private CharacterPlayer character;
	private BasicButton acceptButton;
	private PointsCounterTextField characteristicsPointsTextField;

	public CharacteristicsWindow() {
		defineWindow(500, 400);
		setResizable(false);
		setElements();
	}

	private void setElements() {
		setLayout(new GridBagLayout());
		GridBagConstraints gridBagConstraints = new GridBagConstraints();

		characteristicPanel = new CompleteCharacteristicPanel();
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.gridwidth = 3;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		gridBagConstraints.insets = new Insets(5, 5, 5, 5);
		getContentPane().add(characteristicPanel, gridBagConstraints);

		JPanel characteristicPointsPanel = new JPanel();
		characteristicPointsPanel.setLayout(new BoxLayout(characteristicPointsPanel, BoxLayout.X_AXIS));

		spentPointsLabel = new BaseLabel("  Puntos restantes: ");
		characteristicPointsPanel.add(spentPointsLabel);

		characteristicsPointsTextField = new PointsCounterTextField();
		characteristicsPointsTextField.setColumns(3);
		characteristicsPointsTextField.setEditable(false);
		characteristicsPointsTextField.setMaximumSize(new Dimension(60, 25));
		setRemainingPoints(0);
		characteristicPointsPanel.add(characteristicsPointsTextField);

		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 0.8;
		gridBagConstraints.weighty = 0;
		getContentPane().add(characteristicPointsPanel, gridBagConstraints);

		JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
		acceptButton = new BasicButton("Aceptar");
		acceptButton.addActionListener(new AcceptListener());
		buttonPanel.add(acceptButton);

		CloseButton closeButton = new CloseButton(this);
		buttonPanel.add(closeButton);

		gridBagConstraints.anchor = GridBagConstraints.LINE_END;
		gridBagConstraints.fill = GridBagConstraints.NONE;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 0.2;
		gridBagConstraints.weighty = 0;
		getContentPane().add(buttonPanel, gridBagConstraints);
	}

	private void setRemainingPoints(Integer value) {
		characteristicsPointsTextField.setPoints(value);
	}

	public void setCharacter(CharacterPlayer character) {
		this.character = character;
		characteristicPanel.setCharacter(character, false);
		characteristicPanel.setParentWindow(this);
		setRemainingPoints(Characteristics.TOTAL_CHARACTERISTICS_POINTS
				- character.getCharacteristicsTemporalPointsSpent());
		acceptButton.setEnabled(!character.areCharacteristicsConfirmed());
		setPotential();
	}

	@Override
	public void update() {
		setRemainingPoints(Characteristics.TOTAL_CHARACTERISTICS_POINTS
				- character.getCharacteristicsTemporalPointsSpent());
		acceptButton.setEnabled(!character.areCharacteristicsConfirmed());
	}

	public void setPotential() {
		characteristicPanel.setPotential();
	}

	class AcceptListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			character.setCharacteristicsAsConfirmed();
			acceptButton.setEnabled(!character.areCharacteristicsConfirmed());
			setPotential();
		}
	}
}
