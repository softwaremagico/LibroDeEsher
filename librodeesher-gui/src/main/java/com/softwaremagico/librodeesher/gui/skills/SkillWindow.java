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
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JPanel;

import com.softwaremagico.librodeesher.gui.elements.BaseLabel;
import com.softwaremagico.librodeesher.gui.elements.CloseButton;
import com.softwaremagico.librodeesher.gui.elements.PointsCounterTextField;
import com.softwaremagico.librodeesher.gui.elements.RandomButton;
import com.softwaremagico.librodeesher.gui.style.BaseFrame;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.categories.Category;
import com.softwaremagico.librodeesher.pj.random.RandomCharacterPlayer;
import com.softwaremagico.librodeesher.pj.skills.Skill;

public class SkillWindow extends BaseFrame {
	private static final long serialVersionUID = 3505731416535837471L;
	private CharacterPlayer character;
	private CompleteSkillPanel skillPanel;
	private BaseLabel pointsLabel;
	private PointsCounterTextField developmentPoints;
	JCheckBoxMenuItem fireArmsMenuItem, darkSpellsMenuItem;

	public SkillWindow(CharacterPlayer character) {
		this.character = character;
		defineWindow(750, 450);
		developmentPoints = new PointsCounterTextField();
		// setResizable(false);
		setElements();
		setEvents();
	}

	private void setDevelopmentPointText() {
		developmentPoints.setPoints(character.getRemainingDevelopmentPoints());
	}

	private void setElements() {
		setLayout(new GridBagLayout());
		GridBagConstraints gridBagConstraints = new GridBagConstraints();

		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.gridwidth = 3;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		gridBagConstraints.insets = new Insets(2, 2, 2, 2);
		skillPanel = new CompleteSkillPanel(character, this);
		getContentPane().add(skillPanel, gridBagConstraints);

		JPanel developmentPointsPanel = new JPanel();
		developmentPointsPanel.setLayout(new BoxLayout(developmentPointsPanel,
				BoxLayout.X_AXIS));
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

		JPanel buttonPanel = new JPanel(new GridLayout(1, 2));

		RandomButton randomButton = new RandomButton() {
			private static final long serialVersionUID = 1L;

			@Override
			public void RandomAction() {
				Integer tries = 0;
				RandomCharacterPlayer.setWeaponCosts(character);
				// Store probability to increase speed.
				Map<Skill, Integer> skillProbabilityStored = new HashMap<>();
				Map<Category, Integer> categoryProbabilityStored = new HashMap<>();

				while (character.getRemainingDevelopmentPoints() > 0
						&& tries <= RandomCharacterPlayer.MAX_TRIES) {
					RandomCharacterPlayer.setRandomRanks(character, 0,
							new HashMap<String, Integer>(), tries,
							character.getCurrentLevelNumber(),
							categoryProbabilityStored, skillProbabilityStored);
					tries++;
				}
				updateFrame();
				updateSkillFrame();
			}
		};
		buttonPanel.add(randomButton);

		CloseButton closeButton = new CloseButton(this);
		buttonPanel.add(closeButton);

		gridBagConstraints.anchor = GridBagConstraints.LINE_END;
		gridBagConstraints.fill = GridBagConstraints.NONE;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 0;
		gridBagConstraints.weighty = 0;
		gridBagConstraints.insets = new Insets(2, 2, 2, 2);
		getContentPane().add(buttonPanel, gridBagConstraints);

	}

	private void updateSkillFrame() {
		if (skillPanel != null) {
			skillPanel.updateRanks();
			skillPanel.updateWeaponCost();
		}
	}

	private void setEvents() {
		addComponentListener(new ComponentListener() {
			@Override
			public void componentHidden(ComponentEvent e) {

			}

			@Override
			public void componentMoved(ComponentEvent e) {

			}

			@Override
			public void componentResized(ComponentEvent evt) {
				skillPanel.sizeChanged();
			}

			@Override
			public void componentShown(ComponentEvent e) {

			}
		});
	}

	@Override
	public void updateFrame() {
		setDevelopmentPointText();
	}

	class AcceptListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

		}
	}
}
