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
import com.softwaremagico.librodeesher.gui.elements.BaseTextField;
import com.softwaremagico.librodeesher.gui.elements.CloseButton;
import com.softwaremagico.librodeesher.gui.elements.PointsCounterTextField;
import com.softwaremagico.librodeesher.gui.elements.RandomButton;
import com.softwaremagico.librodeesher.gui.style.BaseButton;
import com.softwaremagico.librodeesher.gui.style.BaseFrame;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.characteristic.Characteristics;
import com.softwaremagico.librodeesher.pj.random.RandomCharacterPlayer;

public class CharacteristicsWindow extends BaseFrame {
	private static final long serialVersionUID = -2484205144800968016L;
	private CompleteCharacteristicPanel characteristicPanel;
	private BaseLabel spentPointsLabel, totalPointsLabel;
	private CharacterPlayer character;
	private BaseButton acceptButton;
	private RandomButton randomButton;
	private PointsCounterTextField characteristicsPointsTextField;
	private BaseTextField characteristicsTotalPointsTextField;

	public CharacteristicsWindow() {
		defineWindow(550, 400);
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

		totalPointsLabel = new BaseLabel("Puntos: ");
		characteristicPointsPanel.add(totalPointsLabel);

		characteristicsTotalPointsTextField = new BaseTextField();
		characteristicsTotalPointsTextField.setColumns(3);
		characteristicsTotalPointsTextField.setMaximumSize(new Dimension(60, 25));
		characteristicsTotalPointsTextField.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				updateTotalTemporalPoints();
			}
		});
		characteristicPointsPanel.add(characteristicsTotalPointsTextField);

		spentPointsLabel = new BaseLabel(" Restantes: ");
		characteristicPointsPanel.add(spentPointsLabel);

		characteristicsPointsTextField = new PointsCounterTextField();
		characteristicsPointsTextField.setColumns(3);
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

		randomButton = new RandomButton() {
			private static final long serialVersionUID = -191742354787985492L;

			@Override
			public void RandomAction() {
				updateTotalTemporalPoints();
				RandomCharacterPlayer.setCharacteristics(character, 0);
				setCharacter(character);
			}
		};
		buttonPanel.add(randomButton);

		acceptButton = new BaseButton("Aceptar");
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

	private void updateTotalTemporalPoints() {
		if (character != null) {
			try {
				character.setCharacteristicsTemporalTotalPoints(Integer.parseInt(characteristicsTotalPointsTextField.getText()));
			} catch (Exception except) {
				character.setCharacteristicsTemporalTotalPoints(Characteristics.TOTAL_CHARACTERISTICS_POINTS);
			}
			setRemainingPoints(character.getCharacteristicsTemporalTotalPoints() - character.getCharacteristicsTemporalPointsSpent());
		}
	}

	private void setRemainingPoints(Integer value) {
		characteristicsPointsTextField.setPoints(value);
	}

	public void setCharacter(CharacterPlayer character) {
		this.character = character;
		characteristicPanel.setCharacter(character, false);
		characteristicPanel.setParentWindow(this);
		characteristicsTotalPointsTextField.setText(character.getCharacteristicsTemporalTotalPoints() + "");
		setRemainingPoints(character.getCharacteristicsTemporalTotalPoints() - character.getCharacteristicsTemporalPointsSpent());
		acceptButton.setEnabled(!character.areCharacteristicsConfirmed());
		randomButton.setEnabled(acceptButton.isEnabled());
		characteristicsPointsTextField.setVisible(!character.isCharacteristicsConfirmed());
		spentPointsLabel.setVisible(!character.isCharacteristicsConfirmed());
		characteristicsTotalPointsTextField.setEditable(!character.isCharacteristicsConfirmed());
		setPotential();
	}

	@Override
	public void updateFrame() {
		setRemainingPoints(character.getCharacteristicsTemporalTotalPoints() - character.getCharacteristicsTemporalPointsSpent());
		acceptButton.setEnabled(!character.areCharacteristicsConfirmed());
		randomButton.setEnabled(acceptButton.isEnabled());
	}

	public void setPotential() {
		characteristicPanel.setPotential();
	}

	class AcceptListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			character.setCharacteristicsAsConfirmed();
			acceptButton.setEnabled(!character.areCharacteristicsConfirmed());
			randomButton.setEnabled(acceptButton.isEnabled());
			setPotential();
		}
	}
}
