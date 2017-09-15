package com.softwaremagico.librodeesher.gui.perk;

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

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;

import com.softwaremagico.files.MessageManager;
import com.softwaremagico.librodeesher.config.Config;
import com.softwaremagico.librodeesher.gui.elements.BaseLabel;
import com.softwaremagico.librodeesher.gui.elements.BaseRadioButton;
import com.softwaremagico.librodeesher.gui.elements.CloseButton;
import com.softwaremagico.librodeesher.gui.elements.PointsCounterTextField;
import com.softwaremagico.librodeesher.gui.style.BaseFrame;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.perk.Perk;
import com.softwaremagico.librodeesher.pj.perk.PerkFactory;
import com.softwaremagico.librodeesher.pj.perk.PerkGrade;
import com.softwaremagico.librodeesher.pj.race.exceptions.InvalidRaceDefinition;
import com.softwaremagico.log.EsherLog;

public class PerkWindow extends BaseFrame {
	private static final long serialVersionUID = 4804191786722149503L;
	private final static String NOTHING = "ninguno";
	private static boolean sortByCost = false;

	private CharacterPlayer character;
	private PerksListCompletePanel perksPanel;
	private BaseLabel perksPointsLabel;
	private PointsCounterTextField perksPoints;
	private PointsCounterTextField historyPoints;
	private BaseRadioButton sortByNameRadioButton, sortByCostRadioButton;

	public PerkWindow(CharacterPlayer character) {
		this.character = character;
		defineWindow(750, 400);
		perksPoints = new PointsCounterTextField();
		setElements();
		sortPerks();
	}

	private void setBackgroundPointText() {
		if (historyPoints != null) {
			historyPoints.setPoints(character.getRemainingBackgroundPoints());
		}
	}

	private void setElements() {
		PerksWindowsMenu mainMenu = new PerksWindowsMenu();
		if (Config.getPerksCostHistoryPoints()) {
			setJMenuBar(mainMenu.createMenu());
			mainMenu.addRandomPerkMenuListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					Perk perk = selectRandomPerk();
					if (perk != null) {
						try {
							if (perk.getCost() <= character.getRemainingPerksPoints()) {
								MessageManager.infoMessage(this.getClass().getName(), "El talento adquirido es '" + perk.getName() + "'",
										"¡Nuevo talento adquirido!");
								character.setAsRandomPerk(perk);
								perksPanel.selectRandomPerk(perk, true);
								updateFrame();
							} else {
								MessageManager.basicErrorMessage(this.getClass().getName(), "El talento obtenido es '" + perk.getName()
										+ "' pero no tienes puntos suficientes para adquirilo.", "¡Nuevo talento adquirido!");
							}
						} catch (InvalidRaceDefinition e) {
							MessageManager.basicErrorMessage(this.getClass().getName(), e.getMessage(), "Raza incorrectamente definida.");
							EsherLog.errorMessage(this.getClass().getName(), e);
						}
					}
				}
			});
		}

		setLayout(new GridBagLayout());
		GridBagConstraints gridBagConstraints = new GridBagConstraints();

		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 0;
		gridBagConstraints.insets = new Insets(2, 2, 2, 2);

		JPanel perksPointPanel = new JPanel();
		perksPointPanel.setLayout(new BoxLayout(perksPointPanel, BoxLayout.X_AXIS));
		perksPointPanel.add(Box.createRigidArea(new Dimension(20, 0)));
		perksPointsLabel = new BaseLabel("Puntos de Talentos: ");
		perksPointPanel.add(perksPointsLabel);

		perksPoints.setColumns(3);
		perksPoints.setEditable(false);
		perksPoints.setMaximumSize(new Dimension(60, 25));
		setPerksPointsText();
		perksPointPanel.add(perksPoints);

		// Group the radio buttons.
		sortByNameRadioButton = new BaseRadioButton("Nombre");
		sortByCostRadioButton = new BaseRadioButton("Coste");
		sortByNameRadioButton.addActionListener(new ChangeSortingListener());
		sortByCostRadioButton.addActionListener(new ChangeSortingListener());

		perksPointPanel.add(Box.createRigidArea(new Dimension(50, 0)));
		perksPointPanel.add(new BaseLabel("Ordenar: "));
		ButtonGroup sortGroup = new ButtonGroup();
		sortGroup.add(sortByNameRadioButton);
		sortGroup.add(sortByCostRadioButton);
		sortByNameRadioButton.setSelected(true);
		perksPointPanel.add(sortByNameRadioButton);
		perksPointPanel.add(sortByCostRadioButton);

		if (Config.getPerksCostHistoryPoints()) {
			perksPointPanel.add(Box.createRigidArea(new Dimension(10, 0)));
			BaseLabel historyPointsLabel = new BaseLabel("Puntos de Historial: ");
			perksPointPanel.add(historyPointsLabel);
			historyPoints = new PointsCounterTextField();
			historyPoints.setColumns(3);
			historyPoints.setEditable(false);
			historyPoints.setMaximumSize(new Dimension(60, 25));
			setBackgroundPointText();
			perksPointPanel.add(historyPoints);
		}

		getContentPane().add(perksPointPanel, gridBagConstraints);

		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		gridBagConstraints.insets = new Insets(2, 2, 2, 2);
		perksPanel = new PerksListCompletePanel(character, (BaseFrame) this);
		getContentPane().add(perksPanel, gridBagConstraints);

		JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
		CloseButton closeButton = new CloseButton(this);
		buttonPanel.add(closeButton);
		gridBagConstraints.anchor = GridBagConstraints.LINE_END;
		gridBagConstraints.fill = GridBagConstraints.NONE;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 0;
		gridBagConstraints.insets = new Insets(2, 2, 2, 2);
		getContentPane().add(buttonPanel, gridBagConstraints);
	}

	private void setPerksPointsText() {
		try {
			perksPoints.setPoints(character.getRemainingPerksPoints());
		} catch (InvalidRaceDefinition ex) {
			MessageManager.basicErrorMessage(this.getClass().getName(), ex.getMessage(), "Raza incorrectamente definida.");
			MessageManager.errorMessage(this.getClass().getName(), ex);
		}
	}

	@Override
	public void updateFrame() {
		setBackgroundPointText();
		setPerksPointsText();
	}

	private void sortPerks() {
		perksPanel.sortElements(sortByCost);
	}

	private Perk selectRandomPerk() {
		List<String> tags = new ArrayList<String>();
		if (PerkGrade.values().length > 0) {
			for (PerkGrade grade : PerkGrade.values()) {
				tags.add(grade.getTag());
			}
			// add none option.
			tags.add(NOTHING);
			int selected = MessageManager.questionMessage("¿Desea escoger un talento aleatorio? Esta acción es irreversible.", "Añadir un talento aleatorio.",
					tags.toArray());
			if (selected < 0 || tags.get(selected).equals(NOTHING)) {
				return null;
			}

			PerkGrade perkGrade = PerkGrade.getPerkCategory(tags.get(selected));
			Perk randomPerk = null;
			if (perkGrade != null) {
				randomPerk = PerkFactory.getRandomPerk(perkGrade);
				// Select a not selected already weakness.
				while (character.getPerks().contains(randomPerk)) {
					randomPerk = PerkFactory.getRandomPerk(perkGrade);
				}
			}
			return randomPerk;
		}
		return null;
	}

	class ChangeSortingListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (sortByNameRadioButton.isSelected()) {
				sortByCost = false;
			} else {
				sortByCost = true;
			}
			sortPerks();
		}
	}
}
