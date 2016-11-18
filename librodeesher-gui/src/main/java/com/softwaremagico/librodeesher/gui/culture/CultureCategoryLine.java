package com.softwaremagico.librodeesher.gui.culture;

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

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.SwingConstants;

import com.softwaremagico.librodeesher.gui.elements.BaseCategoryComboBox;
import com.softwaremagico.librodeesher.gui.elements.ListBackgroundPanel;
import com.softwaremagico.librodeesher.gui.elements.ListLabel;
import com.softwaremagico.librodeesher.gui.style.BaseLine;
import com.softwaremagico.librodeesher.gui.style.Fonts;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.culture.CultureCategory;

public class CultureCategoryLine extends BaseLine {
	private static final long serialVersionUID = 4480268296161276440L;
	private CultureCategory cultureCategory;
	private CategoryComboBox<String> chooseCategoryComboBox = null;
	private ChooseCategoryPanel parentPanel;
	private CharacterPlayer character;

	public CultureCategoryLine(CharacterPlayer character, CultureCategory cultureCategory, Color background, ChooseCategoryPanel parentPanel) {
		this.cultureCategory = cultureCategory;
		this.parentPanel = parentPanel;
		this.character = character;
		setElements(background);
		setBackground(background);
	}

	private void addItemsToComboBox() {
		chooseCategoryComboBox.removeAllItems();
		for (String categoryName : cultureCategory.getCategoryOptions()) {
			chooseCategoryComboBox.addItem(categoryName);
		}
	}

	protected void setElements(Color background) {
		this.removeAll();
		setLayout(new GridBagLayout());
		GridBagConstraints gridBagConstraints = new GridBagConstraints();

		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 0;

		if (cultureCategory.getCategoryOptions().size() == 1) {
			ListLabel cultureCategoryLabel = new ListLabel(cultureCategory.getCategoryOptions().get(0), SwingConstants.LEFT);
			cultureCategoryLabel.setFont(Fonts.getInstance().getBoldFont());
			add(cultureCategoryLabel, gridBagConstraints);
		} else {
			// User can choose one of the options.
			chooseCategoryComboBox = new CategoryComboBox<>();
			addItemsToComboBox();
			add(new ListBackgroundPanel(chooseCategoryComboBox, getDefaultBackground()), gridBagConstraints);
		}

		gridBagConstraints.anchor = GridBagConstraints.CENTER;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 0;
		gridBagConstraints.gridx = 1;
		ListLabel rankLabel = new ListLabel(cultureCategory.getRanks().toString() + " (" + cultureCategory.getSkillRanksToChoose().toString() + ")");
		add(rankLabel, gridBagConstraints);
	}

	@Override
	public void update() {

	}

	protected class CategoryComboBox<E> extends BaseCategoryComboBox<E> {
		private static final long serialVersionUID = -2235910396163201201L;

		@Override
		public void doAction() {
			// Remove skills of old category.
			parentPanel.removeSkillLinesOfCategory(cultureCategory);
			character.removeCultureSkills(cultureCategory);
			// add new skills in the correct place.
			parentPanel.addSkillLinesOfCategory(cultureCategory, (String) chooseCategoryComboBox.getSelectedItem());
		}
	}

	public CultureCategory getCultureCategory() {
		return cultureCategory;
	}
}
