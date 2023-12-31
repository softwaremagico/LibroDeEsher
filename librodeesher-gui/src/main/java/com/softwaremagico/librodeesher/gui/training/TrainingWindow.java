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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;

import com.softwaremagico.librodeesher.gui.ShowMessage;
import com.softwaremagico.librodeesher.gui.components.SelectSkillWindow;
import com.softwaremagico.librodeesher.gui.components.SelectSkillWindow.WindowClosedListener;
import com.softwaremagico.librodeesher.gui.elements.BaseLabel;
import com.softwaremagico.librodeesher.gui.elements.BaseTextField;
import com.softwaremagico.librodeesher.gui.elements.CloseButton;
import com.softwaremagico.librodeesher.gui.elements.PointsCounterTextField;
import com.softwaremagico.librodeesher.gui.style.BaseButton;
import com.softwaremagico.librodeesher.gui.style.BaseDialog;
import com.softwaremagico.librodeesher.gui.style.BaseFrame;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.categories.CategoryFactory;
import com.softwaremagico.librodeesher.pj.characteristic.Characteristic;
import com.softwaremagico.librodeesher.pj.characteristic.CharacteristicRoll;
import com.softwaremagico.librodeesher.pj.characteristic.Characteristics;
import com.softwaremagico.librodeesher.pj.characteristic.CharacteristicsAbbreviature;
import com.softwaremagico.librodeesher.pj.equipment.BonusType;
import com.softwaremagico.librodeesher.pj.equipment.MagicObject;
import com.softwaremagico.librodeesher.pj.random.TrainingProbability;
import com.softwaremagico.librodeesher.pj.skills.ChooseSkillGroup;
import com.softwaremagico.librodeesher.pj.skills.Skill;
import com.softwaremagico.librodeesher.pj.training.InvalidTrainingException;
import com.softwaremagico.librodeesher.pj.training.Training;
import com.softwaremagico.librodeesher.pj.training.TrainingFactory;
import com.softwaremagico.librodeesher.pj.training.TrainingItem;
import com.softwaremagico.librodeesher.pj.training.TrainingItemType;
import com.softwaremagico.log.EsherLog;

public class TrainingWindow extends BaseFrame {
	private static final long serialVersionUID = 3835272249277413846L;
	private CharacterPlayer characterPlayer;
	private JComboBox<String> trainingsAvailable;
	private BaseButton addTraining;
	private PointsCounterTextField remainingDevelopmentPoints;
	private PointsCounterTextField costDevelopmentPoints;
	private BaseLabel pointsLabel;
	private CompleteCategoryPanel categoryPanel;
	private Training lastSelectedTraining = null;
	private BaseTextField selectedTrainingName;
	private BaseDialog selectCharacteristicDialog;

	public TrainingWindow(CharacterPlayer character) {
		this.characterPlayer = character;
		defineWindow(750, 500);
		setElements();
		setEvents();
	}

	private void setDevelopmentPointText() {
		if (remainingDevelopmentPoints != null) {
			remainingDevelopmentPoints.setPoints(characterPlayer.getRemainingDevelopmentPoints());
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
		categoryPanel = new CompleteCategoryPanel(characterPlayer);
		getContentPane().add(categoryPanel, gridBagConstraints);

		JPanel buttonPanel = new JPanel(new GridLayout(1, 2));

		BaseButton acceptButton = new BaseButton("Aceptar");
		acceptButton.addActionListener(new AcceptListener(this));
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

	public boolean repeatedCategory() {
		return categoryPanel.repeatedCategory();
	}

	private void fillTrainingComboBox() {
		trainingsAvailable.removeAllItems();
		for (String trainingName : characterPlayer.getAvailableTrainings()) {
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
		try {
			lastSelectedTraining = TrainingFactory.getTraining(trainingsAvailable.getSelectedItem().toString());
			categoryPanel.setTraining(lastSelectedTraining);
			selectedTrainingName.setText(lastSelectedTraining.getName());
		} catch (InvalidTrainingException e) {
			EsherLog.errorMessage(TrainingWindow.class.getName(), e);
		}
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
			setDevelopmentPointCostText(characterPlayer.getTrainingCost(trainingsAvailable.getSelectedItem().toString()));
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

	private void obtainCharacteristicsUpdates() {
		if (lastSelectedTraining != null) {
			List<List<CharacteristicsAbbreviature>> characteristicsUpdates = lastSelectedTraining.getUpdateCharacteristics();
			// for (List<String> characteristicSet : characteristicsUpdates) {
			while (characterPlayer.getTrainingCharacteristicsUpdates(lastSelectedTraining.getName()).size() < characteristicsUpdates.size()) {
				List<CharacteristicsAbbreviature> characteristicSet = characteristicsUpdates.get(characterPlayer.getTrainingCharacteristicsUpdates(
						lastSelectedTraining.getName()).size());
				// List is sorted from small to biggest list.
				if (characteristicSet.size() == 1) {
					CharacteristicRoll characteristicRoll = characterPlayer.addNewCharacteristicTrainingUpdate(characteristicSet.get(0),
							lastSelectedTraining.getName());
					ShowMessage.showInfoMessage(
							"Hay una aumento para la característica '"
									+ characteristicSet.get(0)
									+ "'.\n El resultado de los dados es: ["
									+ characteristicRoll.getRoll().getFirstDice()
									+ ","
									+ characteristicRoll.getRoll().getSecondDice()
									+ "]\n"
									+ "Por tanto, la característica ha cambiado en: "
									+ Characteristic.getCharacteristicUpgrade(characteristicRoll.getCharacteristicTemporalValue(),
											characteristicRoll.getCharacteristicPotentialValue(), characteristicRoll.getRoll()), "¡Característica aumentada!");
				} else {
					// Select chars
					List<Characteristic> availableCharacteristics = new ArrayList<>();
					for (CharacteristicsAbbreviature charTag : characteristicSet) {
						availableCharacteristics.add(Characteristics.getCharacteristicFromAbbreviature(charTag));
					}
					// characteristicWindow.setVisible(true);
					createDialog(availableCharacteristics);
				}
			}
		}
	}

	private void setTrainingObjects() {
		final String trainingName = lastSelectedTraining.getName();
		try {
			TrainingProbability.setRandomObjects(characterPlayer, trainingName);
		} catch (InvalidTrainingException e) {
			EsherLog.errorMessage(TrainingWindow.class.getName(), e);
		}

		final Map<TrainingItem, MagicObject> createdObjects = new HashMap<>();
		// Select skills.
		for (final TrainingItem trainingItem : characterPlayer.getTrainingEquipment(trainingName)) {
			if (!Objects.equals(trainingItem.getType(), TrainingItemType.SKILL)) {
				final JDialog dialog = new JDialog(this, "Selecciona una habilidad:", true);
				SelectSkillWindow selectSkillWindow = null;
				switch (trainingItem.getType()) {
				case WEAPON:
					selectSkillWindow = new SelectSkillWindow(dialog, characterPlayer, CategoryFactory.getWeaponsCategories());
					break;
				case WEAPON_CLOSE_COMBAT:
					selectSkillWindow = new SelectSkillWindow(dialog, characterPlayer, CategoryFactory.getCloseCombatWeapons());
					break;
				case WEAPON_RANGED:
					selectSkillWindow = new SelectSkillWindow(dialog, characterPlayer, CategoryFactory.getLongRangeWeapons());
					break;
				case ARMOUR:
					MagicObject magicArmour = new MagicObject();
					magicArmour.setObjectBonus(trainingItem.getName(), BonusType.DEFENSIVE_BONUS, trainingItem.getBonus());
					break;
				case SKILL:
				case ANY:
					selectSkillWindow = new SelectSkillWindow(dialog, characterPlayer, CategoryFactory.getCategories());
					break;
				case CATEGORY:
				case UNKNOWN:
					// Nothing.
					break;
				default:
					// Nothing.
					break;
				}

				if (selectSkillWindow != null) {
					selectSkillWindow.setTitle(trainingItem.getName() + " " + trainingItem.getDescription());
					selectSkillWindow.addWindowClosedListener(new WindowClosedListener() {

						@Override
						public void setSelectedSkill(Skill skill) {
							MagicObject magicObject = MagicObject.createMagicObjectFor(skill, trainingItem);
							createdObjects.put(trainingItem, magicObject);
							characterPlayer.addMagicItem(magicObject, trainingName);
							EsherLog.info(this.getClass().getName(), "Se ha definido el objeto '" + magicObject + "' para el adiestramiento '" + trainingName
									+ "'.");
						}

					});
					// Modal window.
					dialog.setContentPane(selectSkillWindow);
					dialog.setResizable(false);
					dialog.setSize(350, 130);
					dialog.setLocationRelativeTo(null);
					dialog.setVisible(true);
				}
			}
		}

		// Show message to user.
		StringBuilder trainingText = new StringBuilder();
		for (TrainingItem trainingItem : characterPlayer.getTrainingEquipment(trainingName)) {
			if (trainingText.length() == 0) {
				trainingText.append("Durante el adiestramiento '" + trainingName + "' se ha obtenido:\n");
			}
			trainingText
					.append("  -  "
							+ trainingItem.getName()
							+ (trainingItem.getBonus() != 0 ? " (" + (trainingItem.getBonus() > 0 ? "+" + trainingItem.getBonus() : trainingItem.getBonus())
									+ ")" : "") + (createdObjects.get(trainingItem) != null ? ": " + createdObjects.get(trainingItem).getBonus() : "") + ".\n");
		}

		if (trainingText.length() > 0) {
			ShowMessage.showInfoMessage(trainingText.toString(), "¡Nuevos elementos!");
		}
	}

	private BaseDialog createDialog(List<Characteristic> availableCharacteristics) {
		selectCharacteristicDialog = new BaseDialog(this, "El Libro de Esher", true);

		TrainingCharacteristicsUpPanel characteristicWindow = new TrainingCharacteristicsUpPanel(characterPlayer, availableCharacteristics,
				selectCharacteristicDialog);
		characteristicWindow.setTraining(lastSelectedTraining.getName());

		selectCharacteristicDialog.setContentPane(characteristicWindow);
		selectCharacteristicDialog.pack();
		selectCharacteristicDialog.center();
		selectCharacteristicDialog.setVisible(true);
		return selectCharacteristicDialog;
	}

	class AddListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (trainingsAvailable.getSelectedIndex() >= 0) {
				characterPlayer.addTraining(trainingsAvailable.getSelectedItem().toString());
				updateFrame();
				selectSkills();
			}
		}
	}

	class ChangeTrainingListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			setTrainingCost();
		}
	}

	/**
	 * Add from the scratch any skill rank.
	 */
	private void setSkillRanks() {
		if (characterPlayer.clearTrainingSkillRanks(selectedTrainingName.getText())) {
			categoryPanel.setSkillRanks();
		}
	}

	class AcceptListener implements ActionListener {
		BaseFrame window;

		AcceptListener(BaseFrame window) {
			this.window = window;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (repeatedCategory()) {
				ShowMessage.showErrorMessage("No puede seleccionarse dos veces la misma categoría.", "Categoría repetida");
			} else {
				if (lastSelectedTraining != null) {
					if (ShowMessage.showQuestionMessage(window, "Esta acción confirmará el adistramiento \"" + lastSelectedTraining.getName()
							+ "\".\n Esta acción es permante. ¿Está seguro de continuar?", "Adiestramiento")) {
						setSkillRanks();
						setTrainingObjects();
						obtainCharacteristicsUpdates();
						clearData();
					}
				}
			}
		}
	}

	/**
	 * Lets the user to choose the common, professional or restricted skills needed.
	 */
	private void selectSkills() {
		for (ChooseSkillGroup options : lastSelectedTraining.getCommonSkills()) {
			if (options.getOptionsGroup().size() > 1) {
				TrainingOptionsWindow<Skill> optionsWindow = new TrainingOptionsWindow<Skill>(characterPlayer, lastSelectedTraining, options, this);
				optionsWindow.setPointCounterLabel("Habilidades comunes: ");
				optionsWindow.setVisible(true);
			}
		}
		for (ChooseSkillGroup options : lastSelectedTraining.getProfessionalSkills()) {
			if (options.getOptionsGroup().size() > 1) {
				TrainingOptionsWindow<Skill> optionsWindow = new TrainingOptionsWindow<Skill>(characterPlayer, lastSelectedTraining, options, this);
				optionsWindow.setPointCounterLabel("Habilidades profesionales: ");
				optionsWindow.setVisible(true);
			}
		}
		for (ChooseSkillGroup options : lastSelectedTraining.getRestrictedSkills()) {
			if (options.getOptionsGroup().size() > 1) {
				TrainingOptionsWindow<Skill> optionsWindow = new TrainingOptionsWindow<Skill>(characterPlayer, lastSelectedTraining, options, this);
				optionsWindow.setPointCounterLabel("Habilidades restringidas: ");
				optionsWindow.setVisible(true);
			}
		}
	}

	class CancelListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			removeTraining();
			clearData();
		}
	}

	protected void removeTraining() {
		characterPlayer.removeTraining(lastSelectedTraining);
	}
}
