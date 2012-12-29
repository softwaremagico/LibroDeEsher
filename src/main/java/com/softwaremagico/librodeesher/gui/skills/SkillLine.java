package com.softwaremagico.librodeesher.gui.skills;

/*
 * #%L
 * Libro de Esher
 * %%
 * Copyright (C) 2007 - 2012 Softwaremagico
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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.softwaremagico.librodeesher.gui.style.BasicLine;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.skills.Skill;

public class SkillLine extends BasicLine {
	private static final long serialVersionUID = -3194401962061016906L;
	private static final Integer columnWidth = 30;
	private static final Integer columnHeight = 20;
	private CharacterPlayer character;

	public SkillLine(CharacterPlayer character, Skill skill, Color background) {
		this.character = character;
		setElements(skill, background);
		setBackground(background);
	}

	private void setElements(Skill skill, Color background) {
		this.removeAll();
		Integer ranks = character.getProfession().getMaxRanksPerLevel(skill.getCategory().getName());

		setLayout(new GridBagLayout());
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		Font defaultFont = new Font(font, Font.PLAIN, fontSize);

		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.weighty = 0;

		JLabel skillNameLabel = new JLabel(skill.getName());
		skillNameLabel.setFont(defaultFont);
		skillNameLabel.setHorizontalAlignment(SwingConstants.LEFT);
		skillNameLabel.setMinimumSize(new Dimension(200, columnHeight));
		skillNameLabel.setPreferredSize(new Dimension(200, columnHeight));
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 0.3;
		add(skillNameLabel, gridBagConstraints);

		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 0.1;
		JLabel rankCostLabel = new JLabel("");
		rankCostLabel.setMinimumSize(new Dimension(columnWidth * 2, columnHeight));
		rankCostLabel.setPreferredSize(new Dimension(columnWidth * 2, columnHeight));
		add(rankCostLabel, gridBagConstraints);

		JLabel prevRanksLabel = new JLabel(character.getPreviousRanks(skill).toString());
		prevRanksLabel.setFont(defaultFont);
		prevRanksLabel.setHorizontalAlignment(SwingConstants.CENTER);
		prevRanksLabel.setMinimumSize(new Dimension(columnWidth, columnHeight));
		prevRanksLabel.setPreferredSize(new Dimension(columnWidth, columnHeight));
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 0.1;
		add(prevRanksLabel, gridBagConstraints);

		JPanel checkBoxPane = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		checkBoxPane.setBackground(background);
		JCheckBox firstRank = new JCheckBox("");
		firstRank.setBackground(background);
		if (ranks > 0) {
			firstRank.setEnabled(true);
		} else {
			firstRank.setEnabled(false);
		}
		checkBoxPane.add(firstRank);

		JCheckBox secondRank = new JCheckBox("");
		secondRank.setBackground(background);
		if (ranks > 1) {
			secondRank.setEnabled(true);
		} else {
			secondRank.setEnabled(false);
		}
		checkBoxPane.add(secondRank);

		JCheckBox thirdRank = new JCheckBox("");
		thirdRank.setBackground(background);
		if (ranks > 2) {
			thirdRank.setEnabled(true);
		} else {
			thirdRank.setEnabled(false);
		}
		checkBoxPane.add(thirdRank);

		gridBagConstraints.gridx = 3;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 0.1;
		checkBoxPane.setMinimumSize(new Dimension(columnWidth * 2, columnHeight));
		checkBoxPane.setPreferredSize(new Dimension(columnWidth * 2, columnHeight));
		add(checkBoxPane, gridBagConstraints);

		JLabel bonusRankLabel = new JLabel(character.getRanksValue(skill).toString());
		bonusRankLabel.setHorizontalAlignment(SwingConstants.CENTER);
		bonusRankLabel.setMinimumSize(new Dimension(columnWidth, columnHeight));
		bonusRankLabel.setPreferredSize(new Dimension(columnWidth, columnHeight));
		bonusRankLabel.setFont(defaultFont);
		gridBagConstraints.gridx = 4;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 0.1;
		add(bonusRankLabel, gridBagConstraints);

		JLabel bonusCategory = new JLabel(character.getTotalValue(skill.getCategory()).toString());
		bonusCategory.setHorizontalAlignment(SwingConstants.CENTER);
		bonusCategory.setMinimumSize(new Dimension(columnWidth, columnHeight));
		bonusCategory.setPreferredSize(new Dimension(columnWidth, columnHeight));
		bonusCategory.setFont(defaultFont);
		gridBagConstraints.gridx = 5;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 0.1;
		add(bonusCategory, gridBagConstraints);

		JLabel bonusMagicObject = new JLabel(character.getBonus(skill).toString());
		bonusMagicObject.setHorizontalAlignment(SwingConstants.CENTER);
		bonusMagicObject.setMinimumSize(new Dimension(columnWidth, columnHeight));
		bonusMagicObject.setPreferredSize(new Dimension(columnWidth, columnHeight));
		bonusMagicObject.setFont(defaultFont);
		gridBagConstraints.gridx = 6;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 0.1;
		add(bonusMagicObject, gridBagConstraints);

		JLabel otherBonus = new JLabel("0");
		otherBonus.setHorizontalAlignment(SwingConstants.CENTER);
		otherBonus.setMinimumSize(new Dimension(columnWidth, columnHeight));
		otherBonus.setPreferredSize(new Dimension(columnWidth, columnHeight));
		otherBonus.setFont(defaultFont);
		gridBagConstraints.gridx = 7;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 0.1;
		add(otherBonus, gridBagConstraints);

		JLabel totalLabel = new JLabel(character.getTotalValue(skill).toString());
		totalLabel.setHorizontalAlignment(SwingConstants.CENTER);
		totalLabel.setMinimumSize(new Dimension(columnWidth, columnHeight));
		totalLabel.setPreferredSize(new Dimension(columnWidth, columnHeight));
		totalLabel.setFont(defaultFont);
		gridBagConstraints.gridx = 8;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 0.1;
		add(totalLabel, gridBagConstraints);
	}
}