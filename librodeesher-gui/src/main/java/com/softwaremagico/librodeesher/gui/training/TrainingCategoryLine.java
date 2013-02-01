package com.softwaremagico.librodeesher.gui.training;

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

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.softwaremagico.librodeesher.gui.elements.BaseComboBox;
import com.softwaremagico.librodeesher.gui.elements.BaseComboBox.ComboBoxListener;
import com.softwaremagico.librodeesher.gui.elements.ListBackgroundPanel;
import com.softwaremagico.librodeesher.gui.elements.ListLabel;
import com.softwaremagico.librodeesher.gui.style.BaseLine;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.training.TrainingCategory;

public class TrainingCategoryLine extends BaseLine {
	private CharacterPlayer character;
	private TrainingCategory category;
	private CategoryPanel parent;
	private ListLabel ranksLabel;
	private CategoryComboBox<TrainingCategory> chooseCategoryComboBox = null;

	protected TrainingCategoryLine(CharacterPlayer character, TrainingCategory category,
			CategoryPanel parent, Color background) {
		this.character = character;
		this.category = category;
		this.parent = parent;
		setElements(background);
	}

	private void addItemsToComboBox() {
	}

	private ListBackgroundPanel getCategoryOrGroup() {
		if (category.needToChooseOneCategory()) {
			ListLabel categoryLabel = new ListLabel(category.getName(), SwingConstants.LEFT);
			return new ListBackgroundPanel(categoryLabel, background);
		} else {
			chooseCategoryComboBox = new CategoryComboBox<>();
			addItemsToComboBox();
			return new ListBackgroundPanel(chooseCategoryComboBox, background);
		}
	}

	protected void setElements(Color background) {
		this.removeAll();
		setLayout(new GridBagLayout());
		GridBagConstraints gridBagConstraints = new GridBagConstraints();

		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 0;

		gridBagConstraints.gridx = 0;
		ListBackgroundPanel categoryPanel = getCategoryOrGroup();
		add(categoryPanel, gridBagConstraints);

		gridBagConstraints.gridx = 1;
		ListLabel minHab = new ListLabel(category.getMinSkills().toString(), SwingConstants.CENTER);
		add(new ListBackgroundPanel(minHab, background), gridBagConstraints);

		gridBagConstraints.gridx = 2;
		ListLabel maxHab = new ListLabel(category.getMaxSkills().toString(), SwingConstants.CENTER);
		add(new ListBackgroundPanel(maxHab, background), gridBagConstraints);

		gridBagConstraints.gridx = 3;
		ranksLabel = new ListLabel(category.getSkillRanks().toString(), SwingConstants.CENTER);
		add(new ListBackgroundPanel(ranksLabel, background), gridBagConstraints);

	}

	private void setRanksNumber() {
		ranksLabel.setText(category.getSkillRanks().toString());
	}

	public class CategoryComboBox<E> extends BaseComboBox<E> {

		@Override
		public void doAction() {

		}
	}

	@Override
	public void update() {
		setRanksNumber();
	}

}
