package com.softwaremagico.librodeesher.gui;
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

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class SkillLine extends BasicLine {
	private static final long serialVersionUID = -3194401962061016906L;
	

	public SkillLine(String skillName) {
		setElements(skillName);
	}

	private void setElements(String skillName) {
		this.removeAll();
		JLabel skillNameLabel = new JLabel(skillName);
		skillNameLabel.setHorizontalAlignment(SwingConstants.LEFT);
		skillNameLabel.setMinimumSize(new Dimension(nameTextDefaultWidth, defaultHeight));
		skillNameLabel.setPreferredSize(new Dimension(nameTextDefaultWidth, defaultHeight));
		add(skillNameLabel);

		JLabel prevRanksLabel = new JLabel("0");
		prevRanksLabel.setHorizontalAlignment(SwingConstants.CENTER);
		prevRanksLabel.setMinimumSize(new Dimension(textDefaultWidth, defaultHeight));
		prevRanksLabel.setPreferredSize(new Dimension(textDefaultWidth, defaultHeight));
		add(prevRanksLabel);

		JCheckBox firstRank = new JCheckBox("");
		firstRank.setBackground(Background);
		add(firstRank);

		JCheckBox secondRank = new JCheckBox("");
		secondRank.setBackground(Background);
		add(secondRank);

		JCheckBox thirdRank = new JCheckBox("");
		thirdRank.setBackground(Background);
		add(thirdRank);

		JLabel bonusRankLabel = new JLabel("10");
		bonusRankLabel.setFont(new Font(font, Font.PLAIN, fontSize));
		bonusRankLabel.setHorizontalAlignment(SwingConstants.CENTER);
		bonusRankLabel.setMinimumSize(new Dimension(textDefaultWidth, defaultHeight));
		bonusRankLabel.setPreferredSize(new Dimension(textDefaultWidth, defaultHeight));
		add(bonusRankLabel);

		JLabel bonusCategory = new JLabel("10");
		bonusCategory.setFont(new Font(font, Font.PLAIN, fontSize));
		bonusCategory.setHorizontalAlignment(SwingConstants.CENTER);
		bonusCategory.setMinimumSize(new Dimension(textDefaultWidth, defaultHeight));
		bonusCategory.setPreferredSize(new Dimension(textDefaultWidth, defaultHeight));
		add(bonusCategory);

		JLabel bonusMagicObject = new JLabel("20");
		bonusMagicObject.setFont(new Font(font, Font.PLAIN, fontSize));
		bonusMagicObject.setHorizontalAlignment(SwingConstants.CENTER);
		bonusMagicObject.setMinimumSize(new Dimension(textDefaultWidth, defaultHeight));
		bonusMagicObject.setPreferredSize(new Dimension(textDefaultWidth, defaultHeight));
		add(bonusMagicObject);
		
		JLabel otherBonus = new JLabel("20");
		otherBonus.setFont(new Font(font, Font.PLAIN, fontSize));
		otherBonus.setHorizontalAlignment(SwingConstants.CENTER);
		otherBonus.setMinimumSize(new Dimension(textDefaultWidth, defaultHeight));
		otherBonus.setPreferredSize(new Dimension(textDefaultWidth, defaultHeight));
		add(otherBonus);

		JLabel totalLabel = new JLabel("50");
		totalLabel.setHorizontalAlignment(SwingConstants.CENTER);
		totalLabel.setMinimumSize(new Dimension(textDefaultWidth, defaultHeight));
		totalLabel.setPreferredSize(new Dimension(textDefaultWidth, defaultHeight));
		add(totalLabel);
	}

}
