package com.softwaremagico.librodeesher.gui.background;

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
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import com.softwaremagico.librodeesher.gui.background.SelectBackgroundLanguagesWindow.LanguageSelectionUpdateListener;
import com.softwaremagico.librodeesher.gui.culture.CultureWindowMenu;
import com.softwaremagico.librodeesher.gui.elements.BaseCheckBox;
import com.softwaremagico.librodeesher.gui.elements.BaseLabel;
import com.softwaremagico.librodeesher.gui.elements.CloseButton;
import com.softwaremagico.librodeesher.gui.elements.PointsCounterTextField;
import com.softwaremagico.librodeesher.gui.elements.RandomButton;
import com.softwaremagico.librodeesher.gui.style.BaseFrame;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.random.RandomCharacterPlayer;

public class BackgroundWindow extends BaseFrame {
	private static final long serialVersionUID = -2770063842107842255L;
	private static final int SKILL_PANEL_INDEX = 0;
	private static final int LANGUAGES_PANEL_INDEX = 1;
	private static final int CHARACTERISTICS_PANEL_INDEX = 2;
	private static final int USELESS_SKILLS_PANEL_INDEX = 3;
	private static final int DEVELOPMENT_POINTS_PANEL_INDEX = 4;

	private CharacterPlayer character;
	private BackgroundCompleteSkillPointsPanel skillPanel;
	private BackgroundCompleteCharacteristicPanel characteristicPanel;
	private BackgroundLanguageCompletePanel languagePanel;
	private BaseLabel historyPointsLabel;
	private PointsCounterTextField historyPoints;
	private BaseCheckBox hideUselessSkillsCheckBox;
	private CultureWindowMenu mainMenu;
	private SelectBackgroundLanguagesWindow selectWindow;

	public BackgroundWindow(CharacterPlayer character) {
		this.character = character;
		defineWindow(800, 420);
		historyPoints = new PointsCounterTextField();
		setResizable(false);
		setElements();
		if (!character.getRace().getOptionalBackgroundLanguages().isEmpty()) {
			mainMenu.enableLanguageWindowMenuItem(true);
		} else {
			mainMenu.enableLanguageWindowMenuItem(false);
		}
	}

	private void setBackgroundPointText() {
		historyPoints.setPoints(character.getRemainingBackgroundPoints());
	}

	private void openLanguageWindow() {
		try {
			selectWindow.dispose();
		} catch (Exception e) {

		}
		selectWindow = new SelectBackgroundLanguagesWindow(character);
		selectWindow.addLanguageSelectionUpdateListener(new LanguageSelectionUpdateListener() {
			@Override
			public void updated() {
				createLanguagePanel();
				revalidate();
				repaint();
			}
		});
		selectWindow.setVisible(true);
	}

	private void setElements() {
		mainMenu = new CultureWindowMenu();
		setJMenuBar(mainMenu.createMenu());
		mainMenu.addLanguageMenuListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				openLanguageWindow();
			}
		});
		setLayout(new GridBagLayout());

		createSkillPanel();
		createLanguagePanel();
		createCharacteristicsPanel();
		createHideUselessSkillsPanel();
		createDevelopmentPointsPanel();

		JPanel buttonPanel = new JPanel(new GridLayout(1, 2));

		RandomButton randomButton = new RandomButton() {
			private static final long serialVersionUID = 1L;

			@Override
			public void RandomAction() {
				Integer tries = 0;
				while (character.getRemainingBackgroundPoints() > 0 && tries <= RandomCharacterPlayer.MAX_TRIES) {
					RandomCharacterPlayer.setBackgroundPoints(character, 0);
					tries++;
				}
				updateFrame();
				updateHistoryLines();
				updateLanguageLines();
			}
		};
		buttonPanel.add(randomButton);

		CloseButton closeButton = new CloseButton(this);
		buttonPanel.add(closeButton);
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.LINE_END;
		gridBagConstraints.fill = GridBagConstraints.NONE;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.gridwidth = 3;
		gridBagConstraints.weightx = 0.3;
		gridBagConstraints.weighty = 0;
		gridBagConstraints.insets = new Insets(2, 2, 2, 2);
		getContentPane().add(buttonPanel, gridBagConstraints);
	}

	private void createSkillPanel() {
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.weightx = 0.8;
		gridBagConstraints.weighty = 1;
		gridBagConstraints.insets = new Insets(2, 2, 2, 2);
		skillPanel = new BackgroundCompleteSkillPointsPanel(character, (BaseFrame) this);
		try {
			getContentPane().remove(SKILL_PANEL_INDEX);
		} catch (Exception e) {
		}
		getContentPane().add(skillPanel, gridBagConstraints, SKILL_PANEL_INDEX);
	}

	private void createLanguagePanel() {
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.weightx = 0.4;
		gridBagConstraints.weighty = 1;
		gridBagConstraints.insets = new Insets(2, 2, 2, 2);
		languagePanel = new BackgroundLanguageCompletePanel(character, (BaseFrame) this);
		try {
			getContentPane().remove(LANGUAGES_PANEL_INDEX);
		} catch (Exception e) {
		}
		getContentPane().add(languagePanel, gridBagConstraints, LANGUAGES_PANEL_INDEX);
	}

	private void createCharacteristicsPanel() {
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.weightx = 0.6;
		gridBagConstraints.weighty = 1;
		gridBagConstraints.insets = new Insets(2, 2, 2, 2);
		characteristicPanel = new BackgroundCompleteCharacteristicPanel(character, (BaseFrame) this);
		try {
			getContentPane().remove(CHARACTERISTICS_PANEL_INDEX);
		} catch (Exception e) {
		}
		getContentPane().add(characteristicPanel, gridBagConstraints, CHARACTERISTICS_PANEL_INDEX);
	}

	private void createHideUselessSkillsPanel() {
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.weightx = GridBagConstraints.REMAINDER;
		gridBagConstraints.weighty = 0;
		gridBagConstraints.insets = new Insets(2, 2, 2, 2);
		JPanel hideUselessSkillsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		hideUselessSkillsCheckBox = new BaseCheckBox("Mostrar todas las habilidades.");
		hideUselessSkillsCheckBox.addActionListener(new HideUselessSkillsActionListener());
		hideUselessSkillsPanel.add(hideUselessSkillsCheckBox);
		hideUselessSkillsPanel.add(hideUselessSkillsCheckBox);
		try {
			getContentPane().remove(USELESS_SKILLS_PANEL_INDEX);
		} catch (Exception e) {
		}
		getContentPane().add(hideUselessSkillsPanel, gridBagConstraints, USELESS_SKILLS_PANEL_INDEX);
	}

	private void createDevelopmentPointsPanel() {
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.weightx = 0.4;
		gridBagConstraints.weighty = 0;
		gridBagConstraints.insets = new Insets(2, 2, 2, 2);
		JPanel developmentPointsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		historyPointsLabel = new BaseLabel("Puntos de Historial: ");
		developmentPointsPanel.add(historyPointsLabel);
		historyPoints.setColumns(3);
		historyPoints.setEditable(false);
		historyPoints.setMaximumSize(new Dimension(60, 25));
		setBackgroundPointText();
		developmentPointsPanel.add(historyPoints);
		try {
			getContentPane().remove(DEVELOPMENT_POINTS_PANEL_INDEX);
		} catch (Exception e) {
		}
		getContentPane().add(developmentPointsPanel, gridBagConstraints, DEVELOPMENT_POINTS_PANEL_INDEX);
	}

	@Override
	public void updateFrame() {
		setBackgroundPointText();
		characteristicPanel.update();
	}

	public void updateHistoryLines() {
		skillPanel.updateHistoryLines();
	}

	public void updateLanguageLines() {
		languagePanel.updateHistoryLines();
	}

	class HideUselessSkillsActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			skillPanel.hideUselessSkills(!hideUselessSkillsCheckBox.isSelected());
		}
	};
}
