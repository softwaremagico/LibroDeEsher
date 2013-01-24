package com.softwaremagico.librodeesher.gui.training;

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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import com.softwaremagico.librodeesher.gui.elements.BaseLabel;
import com.softwaremagico.librodeesher.gui.elements.PointsCounterTextField;
import com.softwaremagico.librodeesher.gui.style.BaseButton;
import com.softwaremagico.librodeesher.gui.style.BaseFrame;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;

public class TrainingWindow extends BaseFrame {
	private CharacterPlayer character;
	private JComboBox<String> trainingsAvailable;
	private BaseButton addTraining;
	private PointsCounterTextField remainingDevelopmentPoints;
	private PointsCounterTextField costDevelopmentPoints;
	private BaseLabel pointsLabel;

	public TrainingWindow(CharacterPlayer character) {
		this.character = character;
		defineWindow(750, 400);
		remainingDevelopmentPoints = new PointsCounterTextField();
		setElements();
	}

	private void setDevelopmentPointText() {
		if (remainingDevelopmentPoints != null) {
			remainingDevelopmentPoints.setPoints(character.getDevelopmentPoints());
		}
	}

	private void setDevelopmentPointCostText(Integer value) {
		if (costDevelopmentPoints != null) {
			costDevelopmentPoints.setPoints(value);
		}
	}

	private void setElements() {
		setLayout(new GridBagLayout());
		GridBagConstraints gridBagConstraints = new GridBagConstraints();

		// gridBagConstraints.anchor = GridBagConstraints.LINE_END;
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.insets = new Insets(2, 2, 2, 2);

		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 0;
		gridBagConstraints.weighty = 0;
		getContentPane().add(createChooseTrainingPanel(), gridBagConstraints);
		gridBagConstraints.gridx = 1;
		getContentPane().add(createDevelopmentPointsPanel(), gridBagConstraints);

	}

	private void fillTrainingComboBox() {
		trainingsAvailable.removeAllItems();
		for (String trainingName : character.getAvailableTrainings()) {
			trainingsAvailable.addItem(trainingName);
		}
	}

	private JPanel createChooseTrainingPanel() {
		JPanel container = new JPanel();
		BaseLabel label = new BaseLabel("Adiestramientos: ");
		container.add(label);
		trainingsAvailable = new JComboBox<String>();
		trainingsAvailable.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXXX");
		trainingsAvailable.addActionListener(new ChangeTrainingListener());
		fillTrainingComboBox();
		container.add(trainingsAvailable);
		label = new BaseLabel(" Coste: ");
		container.add(label);
		costDevelopmentPoints = new PointsCounterTextField();
		container.add(costDevelopmentPoints);
		addTraining = new BaseButton("Añadir");
		addTraining.addActionListener(new AddListener());
		container.add(addTraining);
		setTrainingCost();
		return container;
	}

	private JPanel createDevelopmentPointsPanel() {
		JPanel developmentPointsPanel = new JPanel();
		developmentPointsPanel.setLayout(new BoxLayout(developmentPointsPanel, BoxLayout.X_AXIS));
		pointsLabel = new BaseLabel("  Puntos de Desarrollo: ");
		developmentPointsPanel.add(pointsLabel);
		setDevelopmentPointText();
		developmentPointsPanel.add(remainingDevelopmentPoints);
		setDevelopmentPointText();
		return developmentPointsPanel;
	}

	@Override
	public void update() {
		setDevelopmentPointText();
		fillTrainingComboBox();
	}

	private void addTraining() {
		if (trainingsAvailable.getSelectedIndex() >= 0) {
			character.addTraining(trainingsAvailable.getSelectedItem().toString());
			update();
		}
	}

	private void setTrainingCost() {
		if (trainingsAvailable.getSelectedIndex() >= 0) {
			setDevelopmentPointCostText(character.getTrainingCost(trainingsAvailable.getSelectedItem()
					.toString()));
		} else {
			setDevelopmentPointCostText(0);
		}
	}

	class AddListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			addTraining();
		}
	}

	class ChangeTrainingListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			setTrainingCost();
		}
	}
}
