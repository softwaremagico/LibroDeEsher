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
import com.softwaremagico.librodeesher.pj.categories.Category;

public class CategoryLine extends BasicLine {
	private static final long serialVersionUID = 2914665641808878141L;
	private static final Integer columnWidth = 30;
	private static final Integer columnHeight = 20;
	private CharacterPlayer character;

	public CategoryLine(CharacterPlayer character, Category category, Color background) {
		this.character = character;
		setContent(category, background);
		setBackground(background);
	}

	private void setContent(Category category, Color background) {
		Integer ranks = character.getProfession().getMaxRanksPerLevel(category.getName());
		this.removeAll();
		setLayout(new GridBagLayout());
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		Font defaultFont = new Font(font, Font.BOLD, fontSize);

		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.weighty = 0;

		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 0.3;
		JLabel categoryNameLabel = new JLabel(category.getName());
		categoryNameLabel.setFont(defaultFont);
		categoryNameLabel.setHorizontalAlignment(SwingConstants.LEFT);
		categoryNameLabel.setMinimumSize(new Dimension(200, columnHeight));
		categoryNameLabel.setPreferredSize(new Dimension(200, columnHeight));
		add(categoryNameLabel, gridBagConstraints);

		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 0.1;
		JLabel rankCostLabel = new JLabel(character.getProfession().getCategoryCostTag(category.getName()));
		rankCostLabel.setMinimumSize(new Dimension(columnWidth * 2, columnHeight));
		rankCostLabel.setPreferredSize(new Dimension(columnWidth * 2, columnHeight));
		rankCostLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(rankCostLabel, gridBagConstraints);

		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 0.1;
		JLabel prevRanksLabel = new JLabel(character.getPreviousRanks(category).toString());
		prevRanksLabel.setFont(defaultFont);
		prevRanksLabel.setMinimumSize(new Dimension(columnWidth, columnHeight));
		prevRanksLabel.setPreferredSize(new Dimension(columnWidth, columnHeight));
		prevRanksLabel.setHorizontalAlignment(SwingConstants.CENTER);
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

		JLabel bonusRankLabel = new JLabel("10");
		bonusRankLabel.setFont(defaultFont);
		gridBagConstraints.gridx = 4;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 0.1;
		bonusRankLabel.setMinimumSize(new Dimension(columnWidth, columnHeight));
		bonusRankLabel.setPreferredSize(new Dimension(columnWidth, columnHeight));
		bonusRankLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(bonusRankLabel, gridBagConstraints);

		JLabel bonusCharLabel = new JLabel("10");
		bonusCharLabel.setFont(defaultFont);
		bonusCharLabel.setMinimumSize(new Dimension(columnWidth, columnHeight));
		bonusCharLabel.setPreferredSize(new Dimension(columnWidth, columnHeight));
		bonusCharLabel.setHorizontalAlignment(SwingConstants.CENTER);
		gridBagConstraints.gridx = 5;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 0.1;
		add(bonusCharLabel, gridBagConstraints);

		JLabel bonusMagicObject = new JLabel("20");
		bonusMagicObject.setFont(defaultFont);
		bonusMagicObject.setMinimumSize(new Dimension(columnWidth, columnHeight));
		bonusMagicObject.setPreferredSize(new Dimension(columnWidth, columnHeight));
		bonusMagicObject.setHorizontalAlignment(SwingConstants.CENTER);
		gridBagConstraints.gridx = 6;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 0.1;
		add(bonusMagicObject, gridBagConstraints);

		JLabel otherBonus = new JLabel("20");
		otherBonus.setMinimumSize(new Dimension(columnWidth, columnHeight));
		otherBonus.setPreferredSize(new Dimension(columnWidth, columnHeight));
		otherBonus.setHorizontalAlignment(SwingConstants.CENTER);
		otherBonus.setFont(defaultFont);
		gridBagConstraints.gridx = 7;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 0.1;
		add(otherBonus, gridBagConstraints);

		JLabel totalLabel = new JLabel("50");
		totalLabel.setMinimumSize(new Dimension(columnWidth, columnHeight));
		totalLabel.setPreferredSize(new Dimension(columnWidth, columnHeight));
		totalLabel.setHorizontalAlignment(SwingConstants.CENTER);
		totalLabel.setFont(defaultFont);
		gridBagConstraints.gridx = 8;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 0.1;
		add(totalLabel, gridBagConstraints);
	}
}
