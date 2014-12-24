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
import com.softwaremagico.librodeesher.pj.categories.Category;

public abstract class GenericCategoryLine extends BaseSkillLine {
	private static final long serialVersionUID = 2914665641808878141L;
	protected BoldListLabel categoryNameLabel, bonusRankLabel, totalLabel, prevRanksLabel, bonusCharLabel,
			otherBonus, bonusMagicObject;

	public GenericCategoryLine(CharacterPlayer character, Category category, Color background,
			BaseSkillPanel parentWindow) {
		this.character = character;
		this.category = category;
		this.parentWindow = parentWindow;
		this.background = background;

		setBackground(background);
		setRanksSelected(character.getCurrentLevelRanks(category));
	}

	protected JPanel createCostPanel() {
		JPanel costPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 1, 1));
		// The default cost of a category is when it has no ranks. For spells
		// this label is not updated but the new cost will be used when
		// necessary.
		ListLabel rankCostLabel;
		try {
			rankCostLabel = new BoldListLabel(character.getCategoryCost(category, 0).getCostTag());
			// Weapons maybe have not defined cost.
		} catch (NullPointerException npe) {
			rankCostLabel = new BoldListLabel("--/--");
		}
		costPanel.setMinimumSize(new Dimension(columnWidth * 2, columnHeight));
		costPanel.setPreferredSize(new Dimension(columnWidth * 2, columnHeight));
		costPanel.add(rankCostLabel);
		return costPanel;
	}

	@Override
	protected boolean hasRanks() {
		return category.hasRanks();
	}

	protected void setElements() {
		this.removeAll();
		Integer previousRanks = character.getPreviousRanks(category);

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
		categoryNameLabel = new BoldListLabel(category.getName(), SwingConstants.LEFT, 200, columnHeight);
		add(new ListBackgroundPanel(categoryNameLabel, background), gridBagConstraints);

		if (costPanel) {
			gridBagConstraints.gridx = 3;
			gridBagConstraints.gridwidth = 1;
			gridBagConstraints.weightx = 0.1;
			JPanel costPanel = createCostPanel();
			costPanel.setBackground(background);
			add(costPanel, gridBagConstraints);
		}

		if (oldRanksPanel) {
			gridBagConstraints.gridx = 5;
			gridBagConstraints.gridwidth = 1;
			gridBagConstraints.weightx = 0.1;
			prevRanksLabel = new BoldListLabel(previousRanks.toString(), columnWidth, columnHeight);
			add(new ListBackgroundPanel(prevRanksLabel, background), gridBagConstraints);
		}

		if (chooseRanksPanel) {
			JPanel checkBoxPane = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
			checkBoxPane.setBackground(background);
			if (category.hasRanks()) {
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

		if (ranksValuePanel) {
			gridBagConstraints.gridx = 9;
			gridBagConstraints.gridwidth = 1;
			gridBagConstraints.weightx = 0.1;
			bonusRankLabel = new BoldListLabel(character.getRanksValue(category).toString(), columnWidth,
					columnHeight);
			add(new ListBackgroundPanel(bonusRankLabel, background), gridBagConstraints);
		}

		if (bonusCategoryPanel) {
			gridBagConstraints.gridx = 11;
			gridBagConstraints.gridwidth = 1;
			gridBagConstraints.weightx = 0.1;
			bonusCharLabel = new BoldListLabel(character.getCharacteristicsBonus(category).toString(),
					columnWidth, columnHeight);
			add(new ListBackgroundPanel(bonusCharLabel, background), gridBagConstraints);
		}

		if (otherBonusPanel) {
			gridBagConstraints.gridx = 13;
			gridBagConstraints.gridwidth = 1;
			gridBagConstraints.weightx = 0.1;
			otherBonus = new BoldListLabel(
					(category.getBonus() + character.getProfession().getCategoryBonus(category.getName())
							+ character.getHistorial().getBonus(category) + character.getPerkBonus(category) + character.getConditionalPerkBonus(category))
							+ "", columnWidth, columnHeight);
			add(new ListBackgroundPanel(otherBonus, background), gridBagConstraints);
		}

		if (objectBonusPanel) {
			gridBagConstraints.gridx = 15;
			gridBagConstraints.gridwidth = 1;
			gridBagConstraints.weightx = 0.1;
			bonusMagicObject = new BoldListLabel(character.getItemBonus(category) + "", columnWidth,
					columnHeight);
			add(new ListBackgroundPanel(bonusMagicObject, background), gridBagConstraints);
		}

		if (totalPanel) {
			gridBagConstraints.gridx = 17;
			gridBagConstraints.gridwidth = 1;
			gridBagConstraints.weightx = 0.1;
			totalLabel = new BoldListLabel(character.getTotalValue(category).toString(), columnWidth,
					columnHeight);
			add(new ListBackgroundPanel(totalLabel, background), gridBagConstraints);
		}

		enableRanks(previousRanks);
	}

	public void update() {
		if (bonusCategoryPanel) {
			bonusRankLabel.setText(character.getRanksValue(category).toString());
		}
		if (totalPanel) {
			totalLabel.setText(character.getTotalValue(category).toString());
		}
		parentWindow.update();
		parentWindow.updateSkillsOfCategory(category);
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

	public void updateCurrentRanks() {
		setRanksSelected(character.getCurrentLevelRanks(category));
	}

	public void updateRankValues() {
		prevRanksLabel.setText(character.getPreviousRanks(category).toString());
		bonusRankLabel.setText(character.getRanksValue(category).toString());
		bonusCharLabel.setText(character.getCharacteristicsBonus(category).toString());
		otherBonus.setText(character.getBonus(category).toString());
		bonusMagicObject.setText("0");
		totalLabel.setText(character.getTotalValue(category).toString());
	}

	public Category getCategory() {
		return category;
	}

}
