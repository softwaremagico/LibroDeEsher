package com.softwaremagico.librodeesher.gui.skills;

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
import java.awt.FlowLayout;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JPanel;

import com.softwaremagico.librodeesher.gui.elements.BaseCategoryComboBox;
import com.softwaremagico.librodeesher.gui.elements.GenericCategoryLine;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.categories.Category;
import com.softwaremagico.librodeesher.pj.categories.CategoryCost;

public class WeaponCategoryLine extends GenericCategoryLine {
	private static final long serialVersionUID = -4281133156537443212L;
	private final static int NAME_LENGTH = 200;
	private WeaponComboBox<CategoryCost> costComboBox;
	private Integer previousSelectedIndex = 0;
	private Integer weaponLineNumber;
	private boolean updatingWeaponCost = true;

	public WeaponCategoryLine(CharacterPlayer character, Category category, Color background, SkillPanel parentWindow,
			CategoryCost cost, Integer weaponIndex) {
		super(character, category, NAME_LENGTH, background, parentWindow);
		updatingWeaponCost = true;
		enableColumns(true, true, false, true, false, true, true, true, true, true);
		setDefaultBackground(background);
		this.weaponLineNumber = weaponIndex;
		previousSelectedIndex = weaponIndex;
		if (costPanel) {
			costComboBox.setEnabled(true);
			costComboBox.setSelectedItem(cost);
		}
		costComboBox.setEnabled(character.getCurrentLevelNumber() == 1);
		updatePlayerWeaponCost();
		enableRanks(0); // Weapon cost does not change. Current ranks calculus
						// is not necessary.
		updatingWeaponCost = false;
		setRanksSelected(character.getCurrentLevelRanks(category));
	}

	private void addItemsToComboBox() {
		updatingWeaponCost = true;

		for (CategoryCost cost : character.getProfession().getWeaponCategoryCost()) {
			costComboBox.addItem(cost);
		}
		updatingWeaponCost = false;
	}

	@Override
	protected JPanel createCostPanel() {
		JPanel costPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 1, 1));
		costComboBox = new WeaponComboBox<>();
		costComboBox.setBackground(getDefaultBackground());

		DefaultListCellRenderer dlcr = new DefaultListCellRenderer();
		dlcr.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
		costComboBox.setRenderer(dlcr);

		addItemsToComboBox();
		costPanel.add(costComboBox);
		return costPanel;
	}

	protected Integer getSelectedIndex() {
		return costComboBox.getSelectedIndex();
	}

	protected void setSelectedIndex(Integer value) {
		costComboBox.setSelectedIndex(value);
	}

	protected void setSelected(CategoryCost categoryCost) {
		updatingWeaponCost = true;
		costComboBox.setEnabled(true);
		costComboBox.setSelectedItem(categoryCost);
		costComboBox.setEnabled(character.getCurrentLevelNumber() == 1);
		updatingWeaponCost = false;
	}

	private void updatePlayerWeaponCost() {
		if (costPanel) {
			character.getProfessionDecisions().setWeaponCost(category, (CategoryCost) costComboBox.getSelectedItem());
		}
	}

	@Override
	protected void updateCharacterPlayerWithCurrentLevelRanks() {
		Integer ranks = getRanksSelected();
		// order the ranks.
		setRanksSelected(ranks);
		character.setCurrentLevelRanks(category, ranks);
	}

	public class WeaponComboBox<E> extends BaseCategoryComboBox<E> {
		private static final long serialVersionUID = -6417842795397244151L;

		@Override
		public void doAction() {
			if (!updatingWeaponCost) {
				if (weaponLineNumber != null) {
					((SkillPanel) parentWindow).updateWeaponsCost(costComboBox.getSelectedIndex(),
							previousSelectedIndex, weaponLineNumber);
				}
				previousSelectedIndex = costComboBox.getSelectedIndex();
				updatePlayerWeaponCost();
				enableRanks(0); // Weapon cost does not change. Current ranks
								// calculus is not necessary.
				parentWindow.update();
			}
		}
	}
}
