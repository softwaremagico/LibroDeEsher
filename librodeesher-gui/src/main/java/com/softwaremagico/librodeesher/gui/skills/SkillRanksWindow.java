package com.softwaremagico.librodeesher.gui.skills;

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
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import com.softwaremagico.librodeesher.gui.components.SkillWindow;
import com.softwaremagico.librodeesher.gui.elements.BaseLabel;
import com.softwaremagico.librodeesher.gui.elements.PointsCounterTextField;
import com.softwaremagico.librodeesher.gui.elements.RandomButton;
import com.softwaremagico.librodeesher.gui.elements.SkillChangedListener;
import com.softwaremagico.librodeesher.gui.elements.SkillTitleLine;
import com.softwaremagico.librodeesher.gui.skills.favourite.SelectFavouriteSkillsWindow;
import com.softwaremagico.librodeesher.gui.skills.generic.SelectSkillOptionsWindow;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.categories.Category;
import com.softwaremagico.librodeesher.pj.random.RandomCharacterPlayer;
import com.softwaremagico.librodeesher.pj.skills.Skill;

public class SkillRanksWindow extends SkillWindow {
	private static final long serialVersionUID = 3505731416535837471L;
	private BaseLabel pointsLabel;
	private PointsCounterTextField developmentPoints;
	private SelectSkillOptionsWindow selectSkillOptionsWindow;
	private SelectFavouriteSkillsWindow selectFavouriteSkillsWindow;

	public SkillRanksWindow(CharacterPlayer character) {
		super(character);
		developmentPoints = new PointsCounterTextField();
		// setResizable(false);
		setElements();
	}

	private void setDevelopmentPointText() {
		developmentPoints.setPoints(character.getRemainingDevelopmentPoints());
	}

	@Override
	protected void setElements() {
		super.setElements();
		SkillWindowMenu mainMenu = new SkillWindowMenu();
		setJMenuBar(mainMenu.createMenu());
		mainMenu.addOptionsMenuListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (selectSkillOptionsWindow != null) {
					selectSkillOptionsWindow.dispose();
				}
				selectSkillOptionsWindow = new SelectSkillOptionsWindow(character);
				selectSkillOptionsWindow.setVisible(true);
				selectSkillOptionsWindow.addSkillChangedListener(new SkillChangedListener() {

					@Override
					public void skillChanged(Skill skill) {
						// Add new skills.
						addSkillPanel();
						revalidate();
						repaint();
					}
				});
			}
		});
		mainMenu.addFavouriteSkillsMenuListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (selectFavouriteSkillsWindow != null) {
					selectFavouriteSkillsWindow.dispose();
				}
				selectFavouriteSkillsWindow = new SelectFavouriteSkillsWindow(character);
				selectFavouriteSkillsWindow.setVisible(true);
			}
		});

		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.BOTH;

		JPanel developmentPointsPanel = new JPanel();
		developmentPointsPanel.setLayout(new BoxLayout(developmentPointsPanel, BoxLayout.X_AXIS));
		pointsLabel = new BaseLabel("  Puntos de Desarrollo restantes: ");
		developmentPointsPanel.add(pointsLabel);

		developmentPoints.setColumns(3);
		developmentPoints.setEditable(false);
		developmentPoints.setMaximumSize(new Dimension(60, 25));
		developmentPointsPanel.add(developmentPoints);

		gridBagConstraints.anchor = GridBagConstraints.CENTER;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 0;
		gridBagConstraints.insets = new Insets(2, 2, 2, 2);
		getContentPane().add(developmentPointsPanel, gridBagConstraints);
		setDevelopmentPointText();

		RandomButton randomButton = new RandomButton() {
			private static final long serialVersionUID = 1L;

			@Override
			public void RandomAction() {
				Integer tries = 0;
				RandomCharacterPlayer.setWeaponCosts(character);
				// Store probability to increase speed.
				Map<Skill, Integer> skillProbabilityStored = new HashMap<>();
				Map<Category, Integer> categoryProbabilityStored = new HashMap<>();

				while (character.getRemainingDevelopmentPoints() > 0 && tries <= RandomCharacterPlayer.MAX_TRIES) {
					RandomCharacterPlayer.setRandomRanks(character, 0, new HashMap<String, Integer>(), tries,
							character.getCurrentLevelNumber(), categoryProbabilityStored, skillProbabilityStored);
					tries++;
				}
				updateFrame();
				updateSkillFrame();
			}
		};
		buttonPanel.add(randomButton, 0);
	}

	@Override
	public void updateFrame() {
		setDevelopmentPointText();
	}

	@Override
	protected CompleteSkillPanel createSkillPanel() {
		return new CompleteSkillPanel(character, this) {
			private static final long serialVersionUID = -8992775247823001768L;

			@Override
			public SkillPanel createSkillPanel() {
				return new SkillPanel(character, this);
			}

			@Override
			public SkillTitleLine createSkillTitle() {
				return new SkillTitle();
			}

		};
	}
}
