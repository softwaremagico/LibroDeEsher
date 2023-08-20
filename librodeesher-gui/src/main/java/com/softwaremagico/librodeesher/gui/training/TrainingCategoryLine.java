package com.softwaremagico.librodeesher.gui.training;

/*
 * #%L
 * Libro de Esher GUI
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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.SwingConstants;

import com.softwaremagico.librodeesher.gui.elements.BaseCategoryComboBox;
import com.softwaremagico.librodeesher.gui.elements.BoldListLabel;
import com.softwaremagico.librodeesher.gui.elements.ListBackgroundPanel;
import com.softwaremagico.librodeesher.gui.elements.ListLabel;
import com.softwaremagico.librodeesher.gui.style.BaseLine;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.training.TrainingCategory;

public class TrainingCategoryLine extends BaseLine {
	private static final long serialVersionUID = 5291774966832383971L;
	private CharacterPlayer character;
	private TrainingCategory trainingCategory;
	private TrainingCategoryPanel parentPanel;
	private ListLabel ranksLabel;
	private CategoryComboBox<String> chooseCategoryComboBox = null;

	protected TrainingCategoryLine(CharacterPlayer character, TrainingCategory category, TrainingCategoryPanel parent,
			Color background) {
		this.character = character;
		this.trainingCategory = category;
		this.parentPanel = parent;
		setDefaultBackground(background);
		setElements();
	}

	private void addItemsToComboBox() {
		chooseCategoryComboBox.removeAllItems();
		for (String categoryName : trainingCategory.getCategoryOptions()) {
			chooseCategoryComboBox.addItem(categoryName);
		}
	}

	private ListBackgroundPanel getCategoryOrGroup() {
		if (!trainingCategory.needToChooseOneCategory()) {
			ListLabel categoryLabel = new BoldListLabel(trainingCategory.getCategoryOptions().get(0),
					SwingConstants.LEFT, 100, columnHeight);
			return new ListBackgroundPanel(categoryLabel, getDefaultBackground());
		} else {
			chooseCategoryComboBox = new CategoryComboBox<>();
			chooseCategoryComboBox.setSize(new Dimension(100, columnHeight));
			addItemsToComboBox();
			return new ListBackgroundPanel(chooseCategoryComboBox, getDefaultBackground());
		}
	}

	protected void setElements() {
		this.removeAll();
		setLayout(new GridLayout(1, 0));
		setBackground(getDefaultBackground());

		ListBackgroundPanel categoryPanel = getCategoryOrGroup();
		add(categoryPanel);

		ListLabel ranks = new BoldListLabel(trainingCategory.getCategoryRanks().toString(), SwingConstants.CENTER);
		add(new ListBackgroundPanel(ranks, getDefaultBackground()));

		ListLabel minHab = new BoldListLabel(trainingCategory.getMinSkills().toString(), SwingConstants.CENTER);
		add(new ListBackgroundPanel(minHab, getDefaultBackground()));

		ListLabel maxHab = new BoldListLabel(trainingCategory.getMaxSkills().toString(), SwingConstants.CENTER);
		add(new ListBackgroundPanel(maxHab, getDefaultBackground()));

		ranksLabel = new BoldListLabel(trainingCategory.getSkillRanks().toString(), SwingConstants.CENTER);
		add(new ListBackgroundPanel(ranksLabel, getDefaultBackground()));
	}

	private void setRanksNumber() {
		ranksLabel.setText(trainingCategory.getSkillRanks().toString());
	}

	protected class CategoryComboBox<E> extends BaseCategoryComboBox<E> {
		private static final long serialVersionUID = -2235910396163201201L;

		@Override
		public void doAction() {
			// Remove skills of old category.
			parentPanel.removeSkillLinesOfCategory(trainingCategory);
			character.removeTrainingSkill(parentPanel.getTraining(), trainingCategory);
			// add new skills in the correct place.
			parentPanel.addSkillLinesOfCategory(trainingCategory, chooseCategoryComboBox.getSelectedIndex());
		}
	}

	@Override
	public void update() {
		setRanksNumber();
	}

	public TrainingCategory getTrainingCategory() {
		return trainingCategory;
	}

	public String getChoosedCategory() {
		if (!trainingCategory.needToChooseOneCategory()) {
			return trainingCategory.getCategoryOptions().get(0).toString();
		}
		return chooseCategoryComboBox.getSelectedItem().toString();
	}

}
