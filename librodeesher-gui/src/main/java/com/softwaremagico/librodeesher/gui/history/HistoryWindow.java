package com.softwaremagico.librodeesher.gui.history;

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

import com.softwaremagico.librodeesher.gui.elements.BaseCheckBox;
import com.softwaremagico.librodeesher.gui.elements.BaseLabel;
import com.softwaremagico.librodeesher.gui.elements.CloseButton;
import com.softwaremagico.librodeesher.gui.elements.PointsCounterTextField;
import com.softwaremagico.librodeesher.gui.elements.RandomButton;
import com.softwaremagico.librodeesher.gui.style.BaseFrame;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.random.RandomCharacterPlayer;

public class HistoryWindow extends BaseFrame {
	private static final long serialVersionUID = -2770063842107842255L;
	private CharacterPlayer character;
	private HistoryCompleteSkillPointsPanel skillPanel;
	private HistoryCompleteCharacteristicPanel characteristicPanel;
	private BaseLabel historyPointsLabel;
	private PointsCounterTextField historyPoints;
	private BaseCheckBox hideUselessSkillsCheckBox;

	public HistoryWindow(CharacterPlayer character) {
		this.character = character;
		defineWindow(700, 420);
		historyPoints = new PointsCounterTextField();
		// setResizable(false);
		setElements();
	}

	private void setHistorialPointText() {
		historyPoints.setPoints(character.getRemainingHistorialPoints());
	}

	private void setElements() {
		setLayout(new GridBagLayout());
		GridBagConstraints gridBagConstraints = new GridBagConstraints();

		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.weightx = 0.7;
		gridBagConstraints.weighty = 1;
		gridBagConstraints.insets = new Insets(2, 2, 2, 2);
		skillPanel = new HistoryCompleteSkillPointsPanel(character, (BaseFrame) this);
		getContentPane().add(skillPanel, gridBagConstraints);

		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.weightx = 0.4;
		gridBagConstraints.weighty = 1;
		gridBagConstraints.insets = new Insets(2, 2, 2, 2);
		characteristicPanel = new HistoryCompleteCharacteristicPanel(character, (BaseFrame) this);
		getContentPane().add(characteristicPanel, gridBagConstraints);

		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.weightx = 0.7;
		gridBagConstraints.weighty = 0;
		gridBagConstraints.insets = new Insets(2, 2, 2, 2);
		JPanel hideUselessSkillsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		hideUselessSkillsCheckBox = new BaseCheckBox("Mostrar todas las habilidades.");
		hideUselessSkillsCheckBox.addActionListener(new HideUselessSkillsActionListener());
		hideUselessSkillsPanel.add(hideUselessSkillsCheckBox);
		hideUselessSkillsPanel.add(hideUselessSkillsCheckBox);
		getContentPane().add(hideUselessSkillsPanel, gridBagConstraints);

		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
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
		setHistorialPointText();
		developmentPointsPanel.add(historyPoints);
		getContentPane().add(developmentPointsPanel, gridBagConstraints);

		JPanel buttonPanel = new JPanel(new GridLayout(1, 2));

		RandomButton randomButton = new RandomButton() {
			private static final long serialVersionUID = 1L;

			@Override
			public void RandomAction() {
				Integer tries = 0;
				while (character.getRemainingHistorialPoints() > 0 && tries <= RandomCharacterPlayer.MAX_TRIES) {
					RandomCharacterPlayer.setHistoryPoints(character, 0);
					tries++;
				}
				updateFrame();
				updateHistoryLines();
			}
		};
		buttonPanel.add(randomButton);

		CloseButton closeButton = new CloseButton(this);
		buttonPanel.add(closeButton);
		gridBagConstraints.anchor = GridBagConstraints.LINE_END;
		gridBagConstraints.fill = GridBagConstraints.NONE;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 0.3;
		gridBagConstraints.weighty = 0;
		gridBagConstraints.insets = new Insets(2, 2, 2, 2);
		getContentPane().add(buttonPanel, gridBagConstraints);
	}

	@Override
	public void updateFrame() {
		setHistorialPointText();
		characteristicPanel.update();
	}

	public void updateHistoryLines() {
		skillPanel.updateHistoryLines();
	}

	class HideUselessSkillsActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			skillPanel.hideUselessSkills(!hideUselessSkillsCheckBox.isSelected());
		}
	};
}
