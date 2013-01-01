package com.softwaremagico.librodeesher.gui.skills;
/*
 * #%L
 * Libro de Esher
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

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.softwaremagico.librodeesher.gui.style.BasePanel;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.categories.Category;
import com.softwaremagico.librodeesher.pj.categories.CategoryFactory;
import com.softwaremagico.librodeesher.pj.categories.CategoryGroup;
import com.softwaremagico.librodeesher.pj.skills.Skill;

public class SkillPanel extends BasePanel {
	private static final long serialVersionUID = 544393371168606333L;
	private CompleteSkillPanel parentWindow;
	private Hashtable<Category, List<SkillLine>> skillLinesPerCategory;
	private List<WeaponCategoryLine> weaponsLines;

	public SkillPanel(CharacterPlayer character, CompleteSkillPanel parentWindow) {
		this.parentWindow = parentWindow;
		skillLinesPerCategory = new Hashtable<>();
		setElements(character);
	}

	private void setElements(CharacterPlayer character) {
		this.removeAll();
		setLayout(new GridLayout(0, 1));
		int i = 0;
		int weapon = 0;

		weaponsLines = new ArrayList<>();
		for (Category category : CategoryFactory.getCategories()) {
			// Translate general category to player specific category.
			category = character.getCategory(category);
			if (character.isCategoryUseful(category)) {
				if (category.getGroup().equals(CategoryGroup.WEAPON)) {
					WeaponCategoryLine wl = new WeaponCategoryLine(character, category,
							getLineBackgroundColor(i), this, weapon);
					add(wl);
					weaponsLines.add(wl);
					weapon++;
				} else {
					add(new CategoryLine(character, category, getLineBackgroundColor(i), this));
				}
				i++;

				List<SkillLine> skillLines = new ArrayList<>();
				for (Skill skill : category.getSkills()) {
					SkillLine skillLine = new SkillLine(character, skill, getLineBackgroundColor(i), this);
					add(skillLine);
					skillLines.add(skillLine);
					i++;
				}
				skillLinesPerCategory.put(category, skillLines);
			}
		}
	}

	protected void update() {
		parentWindow.update();
	}

	protected void updateSkillsOfCategory(Category category) {
		List<SkillLine> skillLines = skillLinesPerCategory.get(category);
		if (skillLines != null) {
			for (SkillLine skillLine : skillLines) {
				skillLine.updateCategory();
			}
		}
	}

	protected void updateWeaponsCost(Integer newUsedItemIndex, Integer oldUsedItemIndex,
			Integer skipedWeaponLine) {

		for (int i = 0; i < weaponsLines.size(); i++) {
			if (i != skipedWeaponLine) {
				if (weaponsLines.get(i).getSelectedIndex() == newUsedItemIndex) {
					weaponsLines.get(i).setSelectedIndex(oldUsedItemIndex);
				}
			}
		}
	}
}
