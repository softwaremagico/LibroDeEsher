package com.softwaremagico.librodeesher.gui.training;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import com.softwaremagico.librodeesher.gui.elements.BaseComboBox;
import com.softwaremagico.librodeesher.gui.elements.BaseSpinner;
import com.softwaremagico.librodeesher.gui.elements.ListBackgroundPanel;
import com.softwaremagico.librodeesher.gui.elements.ListLabel;
import com.softwaremagico.librodeesher.gui.style.BaseLine;
import com.softwaremagico.librodeesher.gui.training.TrainingCategoryLine.CategoryComboBox;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.training.TrainingCategory;
import com.softwaremagico.librodeesher.pj.training.TrainingSkill;

/*
 * #%L
 * Libro de Esher GUI
 * %%
 * Copyright (C) 2007 - 2013 Softwaremagico
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

public class TrainingSkillLine extends BaseLine {
	private CharacterPlayer character;
	private TrainingCategory category;
	private TrainingSkill skill;
	private CategoryPanel parent;
	private SkillComboBox<String> chooseSkillsComboBox = null;
	protected BaseSpinner rankSpinner;

	protected TrainingSkillLine(CharacterPlayer character, TrainingCategory category, TrainingSkill skill,
			CategoryPanel parent, Color background) {
		this.character = character;
		this.skill = skill;
		this.parent = parent;
		this.category = category;
		this.background = background;
		setElements();
	}

	private void addItemsToComboBox() {
		chooseSkillsComboBox.removeAllItems();
		for (String categoryName : skill.getSkillOptions()) {
			chooseSkillsComboBox.addItem(categoryName);
		}
	}

	private ListBackgroundPanel getSkillOrGroup() {
		if (!skill.needToChooseOneSkill()) {
			ListLabel categoryLabel = new ListLabel(skill.getName(), SwingConstants.LEFT, 150, columnHeight);
			return new ListBackgroundPanel(categoryLabel, background);
		} else {
			chooseSkillsComboBox = new SkillComboBox<>();
			addItemsToComboBox();
			return new ListBackgroundPanel(chooseSkillsComboBox, background);
		}
	}

	protected void setElements() {
		this.removeAll();
		setLayout(new GridBagLayout());
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		setBackground(background);

		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 0;

		gridBagConstraints.gridx = 0;
		ListBackgroundPanel categoryPanel = getSkillOrGroup();
		add(categoryPanel, gridBagConstraints);

		gridBagConstraints.gridx = 1;
		ListLabel minHab = new ListLabel("", SwingConstants.CENTER);
		add(new ListBackgroundPanel(minHab, background), gridBagConstraints);

		gridBagConstraints.gridx = 2;
		ListLabel maxHab = new ListLabel("", SwingConstants.CENTER);
		add(new ListBackgroundPanel(maxHab, background), gridBagConstraints);

		gridBagConstraints.gridx = 3;

		SpinnerModel sm = new SpinnerNumberModel(0, 0, (int) category.getSkillRanks()
				- category.getMinSkills() + 1, 1);
		rankSpinner = new BaseSpinner(sm);
		add(createSpinnerInsidePanel(rankSpinner, background), gridBagConstraints);

	}

	protected class SkillComboBox<E> extends BaseComboBox<E> {

		@Override
		public void doAction() {

		}
	}

	@Override
	public void update() {
		// setRanksNumber();
	}

}
