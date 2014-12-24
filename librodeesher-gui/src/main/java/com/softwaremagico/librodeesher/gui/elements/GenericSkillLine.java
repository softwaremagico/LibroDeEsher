package com.softwaremagico.librodeesher.gui.elements;

/*
 * #%L
 * Libro de Esher
 * %%
 * Copyright (C) 2007 - 2012 Softwaremagico
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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.skills.Skill;

public class GenericSkillLine extends BaseSkillLine {
	private static final long serialVersionUID = -3194401962061016906L;
	protected ListLabel skillNameLabel, bonusRankLabel, totalLabel, bonusCategory, otherBonus,
			prevRanksLabel, bonusMagicObject;
	protected Skill skill;
	private Color background;

	public GenericSkillLine(CharacterPlayer character, Skill skill, Color background,
			BaseSkillPanel parentWindow) {
		this.character = character;
		this.skill = skill;
		this.category = skill.getCategory();
		this.parentWindow = parentWindow;
		this.background = background;

		setBackground(background);
		setRanksSelected(character.getCurrentLevelRanks(skill));
	}

	@Override
	protected boolean hasRanks() {
		return true;
	}

	protected void setElements() {
		this.removeAll();
		Integer previousRanks = character.getPreviousRanks(skill);

		setLayout(new GridBagLayout());
		GridBagConstraints gridBagConstraints = new GridBagConstraints();

		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.weighty = 0;

		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 0.3;
		skillNameLabel = new ListLabel(skill.getName(), SwingConstants.LEFT, 200, columnHeight);
		add(new ListBackgroundPanel(skillNameLabel, background), gridBagConstraints);

		if (costPanel) {
			gridBagConstraints.gridx = 3;
			gridBagConstraints.gridwidth = 1;
			gridBagConstraints.weightx = 0.1;
			ListLabel rankCostLabel = new ListLabel("", columnWidth * 2, columnHeight);
			add(new ListBackgroundPanel(rankCostLabel, background), gridBagConstraints);
		}

		if (oldRanksPanel) {
			gridBagConstraints.gridx = 5;
			gridBagConstraints.gridwidth = 1;
			gridBagConstraints.weightx = 0.1;
			prevRanksLabel = new ListLabel(previousRanks.toString(), SwingConstants.CENTER, columnWidth,
					columnHeight);
			add(new ListBackgroundPanel(prevRanksLabel, background), gridBagConstraints);
		}

		if (chooseRanksPanel) {
			JPanel checkBoxPane = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
			checkBoxPane.setBackground(background);
			firstRank = new JCheckBox("");
			firstRank.setBackground(background);
			firstRank.addItemListener(new CheckBoxListener());
			checkBoxPane.add(firstRank);

			secondRank = new JCheckBox("");
			secondRank.setBackground(background);
			secondRank.addItemListener(new CheckBoxListener());
			checkBoxPane.add(secondRank);

			thirdRank = new JCheckBox("");
			thirdRank.setBackground(background);
			thirdRank.addItemListener(new CheckBoxListener());
			checkBoxPane.add(thirdRank);

			gridBagConstraints.gridx = 7;
			gridBagConstraints.gridwidth = 1;
			gridBagConstraints.weightx = 0.1;
			checkBoxPane.setMinimumSize(new Dimension(columnWidth * 2, columnHeight));
			checkBoxPane.setPreferredSize(new Dimension(columnWidth * 2, columnHeight));
			add(checkBoxPane, gridBagConstraints);
		}

		if (ranksValuePanel) {
			gridBagConstraints.gridx = 9;
			gridBagConstraints.gridwidth = 1;
			gridBagConstraints.weightx = 0.1;
			bonusRankLabel = new ListLabel(character.getRanksValue(skill).toString(), columnWidth,
					columnHeight);
			add(new ListBackgroundPanel(bonusRankLabel, background), gridBagConstraints);
		}

		if (bonusCategoryPanel) {
			gridBagConstraints.gridx = 11;
			gridBagConstraints.gridwidth = 1;
			gridBagConstraints.weightx = 0.1;
			bonusCategory = new ListLabel(character.getTotalValue(skill.getCategory()).toString(),
					columnWidth, columnHeight);
			add(new ListBackgroundPanel(bonusCategory, background), gridBagConstraints);
		}

		if (otherBonusPanel) {
			gridBagConstraints.gridx = 13;
			gridBagConstraints.gridwidth = 1;
			gridBagConstraints.weightx = 0.1;
			otherBonus = new ListLabel((character.getProfession().getSkillBonus(skill.getName()) + character.getHistorial().getBonus(skill)
					+ character.getPerkBonus(skill))+"", columnWidth, columnHeight);
			add(otherBonus, gridBagConstraints);
		}

		if (objectBonusPanel) {
			gridBagConstraints.gridx = 15;
			gridBagConstraints.gridwidth = 1;
			gridBagConstraints.weightx = 0.1;
			bonusMagicObject = new ListLabel(character.getItemBonus(skill) + "", columnWidth, columnHeight);
			add(new ListBackgroundPanel(bonusMagicObject, background), gridBagConstraints);
		}

		if (totalPanel) {
			gridBagConstraints.gridx = 17;
			gridBagConstraints.gridwidth = 1;
			gridBagConstraints.weightx = 0.1;
			totalLabel = new ListLabel(character.getTotalValue(skill).toString(), columnWidth, columnHeight);
			add(new ListBackgroundPanel(totalLabel, background), gridBagConstraints);
		}

		enableRanks(previousRanks);
	}

	public void update() {
		if (bonusCategoryPanel) {
			bonusRankLabel.setText(character.getRanksValue(skill).toString());
		}
		if (totalPanel) {
			totalLabel.setText(character.getTotalValue(skill).toString());
		}
		parentWindow.update();
	}

	public void updateRankValues() {
		prevRanksLabel.setText(character.getPreviousRanks(skill).toString());
		bonusRankLabel.setText(character.getRanksValue(skill).toString());
		bonusCategory.setText(character.getTotalValue(skill.getCategory()).toString());
		otherBonus.setText(character.getBonus(skill).toString());
		bonusMagicObject.setText("0");
		totalLabel.setText(character.getTotalValue(skill).toString());
	}

	@Override
	protected void updateCharacterPlayerWithCurrentLevelRanks() {

	}

	protected void addColumn(JPanel panel, Integer column) {
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.CENTER;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.weighty = 0;
		gridBagConstraints.gridx = column * 2;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 0.1;
		panel.setBackground(background);
		add(panel, gridBagConstraints);
	}

	protected void disableRankCheckBox() {
		try {
			firstRank.setEnabled(false);
			secondRank.setEnabled(false);
			thirdRank.setEnabled(false);
		} catch (NullPointerException npe) {
		}
	}

	public Skill getSkill() {
		return skill;
	}
}
