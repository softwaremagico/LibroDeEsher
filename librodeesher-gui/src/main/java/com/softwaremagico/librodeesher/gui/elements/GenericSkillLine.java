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
import java.util.HashSet;
import java.util.Set;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.skills.Skill;

public class GenericSkillLine extends BaseSkillLine {
	private static final long serialVersionUID = -3194401962061016906L;
	protected ListLabel skillNameLabel, bonusRankLabel, totalLabel, bonusCategory, otherBonusLabel, prevRanksLabel,
			bonusMagicObject;
	protected Skill skill;
	private Color background;
	private Set<SkillChangedListener> listeners;
	private int nameLength;

	public GenericSkillLine(CharacterPlayer character, Skill skill, int nameLength, Color background,
			BaseSkillPanel parentWindow) {
		listeners = new HashSet<>();
		this.character = character;
		this.skill = skill;
		this.category = skill.getCategory();
		this.parentWindow = parentWindow;
		this.background = background;
		this.nameLength = nameLength;

		setBackground(background);
		setDefaultBackground(background);
		setRanksSelected(character.getCurrentLevelRanks(skill));
	}

	@Override
	protected boolean hasRanks() {
		return true;
	}

	@Override
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
		skillNameLabel = new ListLabel(getSkillName(), SwingConstants.LEFT, nameLength, columnHeight);
		add(new ListBackgroundPanel(skillNameLabel, background), gridBagConstraints);

		if (costPanel || !isEmptyColumns()) {
			gridBagConstraints.gridx = 3;
			gridBagConstraints.gridwidth = 1;
			gridBagConstraints.weightx = 0.1;
			ListLabel rankCostLabel = new ListLabel("", columnWidth * 2, columnHeight);
			add(new ListBackgroundPanel(rankCostLabel, background), gridBagConstraints);
		}

		if (oldRanksPanel || !isEmptyColumns()) {
			gridBagConstraints.gridx = 5;
			gridBagConstraints.gridwidth = 1;
			gridBagConstraints.weightx = 0.1;
			if (oldRanksPanel) {
				prevRanksLabel = new ListLabel(previousRanks.toString(), SwingConstants.CENTER, columnWidth,
						columnHeight);
			} else {
				prevRanksLabel = new ListLabel("", SwingConstants.CENTER, columnWidth, columnHeight);
			}
			add(new ListBackgroundPanel(prevRanksLabel, background), gridBagConstraints);
		}

		if (chooseRanksPanel || !isEmptyColumns()) {
			JPanel checkBoxPane = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
			checkBoxPane.setBackground(background);
			if (chooseRanksPanel) {
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
			}

			gridBagConstraints.gridx = 7;
			gridBagConstraints.gridwidth = 1;
			gridBagConstraints.weightx = 0.1;
			checkBoxPane.setMinimumSize(new Dimension(columnWidth * 2, columnHeight));
			checkBoxPane.setPreferredSize(new Dimension(columnWidth * 2, columnHeight));
			add(checkBoxPane, gridBagConstraints);
		}

		if (ranksValuePanel || !isEmptyColumns()) {
			gridBagConstraints.gridx = 9;
			gridBagConstraints.gridwidth = 1;
			gridBagConstraints.weightx = 0.1;
			if (ranksValuePanel) {
				bonusRankLabel = new ListLabel(getRanksValue(), columnWidth, columnHeight);
			} else {
				bonusRankLabel = new ListLabel("", columnWidth, columnHeight);
			}
			add(new ListBackgroundPanel(bonusRankLabel, background), gridBagConstraints);
		}

		if (bonusCategoryPanel || !isEmptyColumns()) {
			gridBagConstraints.gridx = 11;
			gridBagConstraints.gridwidth = 1;
			gridBagConstraints.weightx = 0.1;
			if (bonusCategoryPanel) {
				bonusCategory = new ListLabel(character.getTotalValue(skill.getCategory()).toString(), columnWidth,
						columnHeight);
			} else {
				bonusCategory = new ListLabel("", columnWidth, columnHeight);
			}
			add(new ListBackgroundPanel(bonusCategory, background), gridBagConstraints);
		}

		if (otherBonusPanel || !isEmptyColumns()) {
			gridBagConstraints.gridx = 13;
			gridBagConstraints.gridwidth = 1;
			gridBagConstraints.weightx = 0.1;
			if (otherBonusPanel) {
				otherBonusLabel = new ListLabel(character.getBonus(skill).toString(), columnWidth, columnHeight);
			} else {
				otherBonusLabel = new ListLabel("", columnWidth, columnHeight);
			}
			add(otherBonusLabel, gridBagConstraints);
		}

		if (objectBonusPanel || !isEmptyColumns()) {
			gridBagConstraints.gridx = 15;
			gridBagConstraints.gridwidth = 1;
			gridBagConstraints.weightx = 0.1;
			if (objectBonusPanel) {
				bonusMagicObject = new ListLabel(character.getItemBonus(skill) + "", columnWidth, columnHeight);
			} else {
				bonusMagicObject = new ListLabel("", columnWidth, columnHeight);
			}
			add(new ListBackgroundPanel(bonusMagicObject, background), gridBagConstraints);
		}

		if (totalPanel || !isEmptyColumns()) {
			gridBagConstraints.gridx = 17;
			gridBagConstraints.gridwidth = 1;
			gridBagConstraints.weightx = 0.1;
			if (totalPanel) {
				totalLabel = new ListLabel(getTotalValue(), columnWidth, columnHeight);
			} else {
				totalLabel = new ListLabel("", columnWidth, columnHeight);
			}
			add(new ListBackgroundPanel(totalLabel, background), gridBagConstraints);
		}

		enableRanks(previousRanks);
	}

	protected String getSkillName() {
		return character.getSkillNameWithSufix(skill);
	}

	protected String getRanksValue() {
		return character.getRanksValue(skill).toString();
	}

	protected String getTotalValue() {
		return character.getTotalValue(skill).toString();
	}

	@Override
	public void update() {
		updateRanksValue();
		for (SkillChangedListener listener : listeners) {
			listener.skillChanged(skill);
		}
		parentWindow.update();
	}

	public void updateRanksValue() {
		if (ranksValuePanel) {
			bonusRankLabel.setText(getRanksValue());
		}
		if (totalPanel) {
			totalLabel.setText(getTotalValue());
		}
		// Some perks has bonus for each rank
		if (otherBonusPanel) {
			otherBonusLabel.setText(character.getBonus(skill).toString());
		}
	}

	public void updateRankValues() {
		prevRanksLabel.setText(character.getPreviousRanks(skill).toString());
		bonusRankLabel.setText(getRanksValue());
		bonusCategory.setText(character.getTotalValue(skill.getCategory()).toString());
		otherBonusLabel.setText(character.getBonus(skill).toString());
		bonusMagicObject.setText("0");
		totalLabel.setText(getTotalValue());
	}

	@Override
	protected void updateCharacterPlayerWithCurrentLevelRanks() {

	}

	protected void addColumn(JPanel panel, Integer column, float weightx) {
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.CENTER;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.weighty = 0;
		gridBagConstraints.gridx = column * 2;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = weightx;
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

	public void updateCategory() {
		if (bonusCategoryPanel) {
			bonusCategory.setText(character.getTotalValue(skill.getCategory()).toString());
		}
		if (totalPanel) {
			totalLabel.setText(getTotalValue());
		}
	}

	public void updateRanks() {
		setRanksSelected(character.getCurrentLevelRanks(skill));
	}

	public void AddSkillChangedListener(SkillChangedListener listener) {
		this.listeners.add(listener);
	}
}
