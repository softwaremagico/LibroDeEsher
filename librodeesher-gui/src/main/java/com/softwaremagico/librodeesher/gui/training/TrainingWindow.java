package com.softwaremagico.librodeesher.gui.training;

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

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import com.softwaremagico.librodeesher.gui.elements.BaseLabel;
import com.softwaremagico.librodeesher.gui.elements.BaseTextField;
import com.softwaremagico.librodeesher.gui.elements.CloseButton;
import com.softwaremagico.librodeesher.gui.elements.PointsCounterTextField;
import com.softwaremagico.librodeesher.gui.style.BaseButton;
import com.softwaremagico.librodeesher.gui.style.BaseFrame;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.training.Training;
import com.softwaremagico.librodeesher.pj.training.TrainingFactory;

public class TrainingWindow extends BaseFrame {
	private static final long serialVersionUID = 3835272249277413846L;
	private CharacterPlayer character;
	private JComboBox<String> trainingsAvailable;
	private BaseButton addTraining;
	private PointsCounterTextField remainingDevelopmentPoints;
	private PointsCounterTextField costDevelopmentPoints;
	private BaseLabel pointsLabel;
	private CompleteCategoryPanel categoryPanel;
	private Training lastSelectedTraining = null;
	private BaseTextField selectedTrainingName;

	public TrainingWindow(CharacterPlayer character) {
		this.character = character;
		defineWindow(750, 500);
		setElements();
		setEvents();
	}

	private void setDevelopmentPointText() {
		if (remainingDevelopmentPoints != null) {
			remainingDevelopmentPoints.setPoints(character.getRemainingDevelopmentPoints());
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
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 0;		
		getContentPane().add(createChooseTrainingPanel(), gridBagConstraints);
		gridBagConstraints.weightx = 0;
		gridBagConstraints.gridx = 1;
		getContentPane().add(createDevelopmentPointsPanel(), gridBagConstraints);

		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 0;
		JPanel selectedTraining = createSelectedTrainingPanel();
		getContentPane().add(selectedTraining, gridBagConstraints);

		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		categoryPanel = new CompleteCategoryPanel(character);
		getContentPane().add(categoryPanel, gridBagConstraints);

		JPanel buttonPanel = new JPanel(new GridLayout(1, 2));

		BaseButton acceptButton = new BaseButton("Aceptar");
		acceptButton.addActionListener(new AcceptListener());
		buttonPanel.add(acceptButton);

		BaseButton cancelButton = new BaseButton("Cancelar");
		cancelButton.addActionListener(new CancelListener());
		buttonPanel.add(cancelButton);

		CloseButton closeButton = new CloseButton(this);
		buttonPanel.add(closeButton);

		gridBagConstraints.anchor = GridBagConstraints.LINE_END;
		gridBagConstraints.fill = GridBagConstraints.NONE;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
		gridBagConstraints.weightx = 0;
		gridBagConstraints.weighty = 0;

		getContentPane().add(buttonPanel, gridBagConstraints);

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
		addTraining = new BaseButton(" Añadir ");
		addTraining.addActionListener(new AddListener());
		container.add(addTraining);
		setTrainingCost();
		return container;
	}

	private JPanel createDevelopmentPointsPanel() {
		JPanel developmentPointsPanel = new JPanel();
		pointsLabel = new BaseLabel("  PD Restantes: ");
		developmentPointsPanel.add(pointsLabel);
		remainingDevelopmentPoints = new PointsCounterTextField();
		developmentPointsPanel.add(remainingDevelopmentPoints);
		setDevelopmentPointText();
		return developmentPointsPanel;
	}

	private JPanel createSelectedTrainingPanel() {
		JPanel selectedPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		BaseLabel training = new BaseLabel("Adiestramiento Seleccionado:");
		selectedPanel.add(training);
		selectedTrainingName = new BaseTextField();
		selectedTrainingName.setEditable(false);
		selectedTrainingName.setColumns(15);
		selectedPanel.add(selectedTrainingName);
		return selectedPanel;
	}

	@Override
	public void updateFrame() {
		setDevelopmentPointText();
		lastSelectedTraining = TrainingFactory.getTraining(trainingsAvailable.getSelectedItem().toString());
		categoryPanel.setTraining(lastSelectedTraining);
		selectedTrainingName.setText(lastSelectedTraining.getName());
		fillTrainingComboBox();
	}

	private void clearData() {
		setDevelopmentPointText();
		lastSelectedTraining = null;
		categoryPanel.setTraining(lastSelectedTraining);
		selectedTrainingName.setText("");
		fillTrainingComboBox();
	}

	private void setTrainingCost() {
		if (trainingsAvailable.getSelectedIndex() >= 0) {
			setDevelopmentPointCostText(character.getTrainingCost(trainingsAvailable.getSelectedItem()
					.toString()));
		} else {
			setDevelopmentPointCostText(0);
		}
	}

	private void setEvents() {
		addComponentListener(new ComponentListener() {
			@Override
			public void componentResized(ComponentEvent evt) {
				categoryPanel.sizeChanged();
			}

			@Override
			public void componentMoved(ComponentEvent e) {

			}

			@Override
			public void componentShown(ComponentEvent e) {

			}

			@Override
			public void componentHidden(ComponentEvent e) {

			}
		});
	}

	class AddListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (trainingsAvailable.getSelectedIndex() >= 0) {
				character.addTraining(trainingsAvailable.getSelectedItem().toString());
				updateFrame();
			}
		}
	}

	class ChangeTrainingListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			setTrainingCost();
		}
	}

	class AcceptListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			clearData();
		}
	}

	class CancelListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			character.removeTraining(lastSelectedTraining);
			clearData();
		}
	}
}
