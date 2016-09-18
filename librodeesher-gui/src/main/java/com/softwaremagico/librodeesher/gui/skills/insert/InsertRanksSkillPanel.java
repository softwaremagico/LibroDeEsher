package com.softwaremagico.librodeesher.gui.skills.insert;
/*
 * #%L
 * Libro de Esher (GUI)
 * %%
 * Copyright (C) 2007 - 2016 Softwaremagico
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

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.softwaremagico.librodeesher.gui.elements.BaseSkillPanel;
import com.softwaremagico.librodeesher.gui.elements.CategoryChangedListener;
import com.softwaremagico.librodeesher.gui.elements.GenericCategoryLine;
import com.softwaremagico.librodeesher.gui.elements.GenericSkillLine;
import com.softwaremagico.librodeesher.gui.elements.SkillChangedListener;
import com.softwaremagico.librodeesher.gui.skills.CompleteSkillPanel;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.categories.Category;
import com.softwaremagico.librodeesher.pj.categories.CategoryFactory;
import com.softwaremagico.librodeesher.pj.skills.Skill;

public class InsertRanksSkillPanel extends BaseSkillPanel {
	private static final long serialVersionUID = -248424072379426497L;
	private final static int NAME_LENGTH = 200;
	private CompleteSkillPanel parentWindow;
	private HashMap<Category, List<GenericSkillLine>> skillLinesPerCategory;
	private HashMap<Skill, List<GenericSkillLine>> skillSpecializationLines;
	private List<GenericCategoryLine> categoryLines;
	private CharacterPlayer characterPlayer;

	public InsertRanksSkillPanel(CharacterPlayer character, CompleteSkillPanel parentWindow) {
		this.characterPlayer = character;
		this.parentWindow = parentWindow;
		skillLinesPerCategory = new HashMap<>();
		skillSpecializationLines = new HashMap<>();
		setElements(character);
	}

	private void setElements(CharacterPlayer character) {
		this.removeAll();
		setLayout(new GridLayout(0, 1));
		int i = 0;

		categoryLines = new ArrayList<>();
		// Add extra weapons cost if new weapons categories exists.
		character.getProfession().extendCategoryCost(character.isFirearmsAllowed());
		for (Category category : CategoryFactory.getCategories()) {
			// Translate general category to player specific category.
			category = character.getCategory(category);
			if (character.isCategoryUseful(category)) {
				final InsertRanksCategoryLine categoryLine = new InsertRanksCategoryLine(character, category,
						getLineBackgroundColor(i), this);
				categoryLine.addCategoryChangedListener(new CategoryChangedListener() {

					@Override
					public void categoryChanged(Category category) {
						categoryLine.updateRankValues();
					}
				});
				categoryLines.add(categoryLine);
				add(categoryLine);
				i++;

				List<GenericSkillLine> skillLines = new ArrayList<>();
				for (Skill skill : category.getSkills()) {
					if (!character.isSkillDisabled(skill)) {
						GenericSkillLine skillLine = createSkillLine(i, skill);
						skillSpecializationLines.put(skill, new ArrayList<GenericSkillLine>());
						if (skillLine != null) {
							add(skillLine);
							skillLines.add(skillLine);
							i++;
						}
					}
				}
				skillLinesPerCategory.put(category, skillLines);
			}
		}
	}

	private InsertRanksSkillLine createSkillLine(int backgroundIndex, Skill skill) {
		if (!characterPlayer.isSkillDisabled(skill)) {
			final InsertRanksSkillLine skillLine = new InsertRanksSkillLine(characterPlayer, skill, NAME_LENGTH,
					getLineBackgroundColor(backgroundIndex), this);
			add(skillLine);
			skillLine.addSkillChangedListener(new SkillChangedListener() {

				@Override
				public void skillChanged(Skill skill) {
					skillLine.updateRankValues();
				}
			});
			return skillLine;
		}
		return null;
	}

	@Override
	public void update() {
		parentWindow.update();
	}

	@Override
	public void updateSkillsOfCategory(Category category) {
		List<GenericSkillLine> skillLines = skillLinesPerCategory.get(category);
		if (skillLines != null) {
			for (GenericSkillLine skillLine : skillLines) {
				skillLine.updateCategory();
			}
		}
	}

	@Override
	public void updateRanks() {
		for (GenericCategoryLine categoryLine : categoryLines) {
			categoryLine.updateCurrentRanks();
			categoryLine.updateRankValues();
		}
		for (Category category : skillLinesPerCategory.keySet()) {
			for (GenericSkillLine skillLine : skillLinesPerCategory.get(category)) {
				skillLine.updateRanks();
				skillLine.updateRankValues();
			}
		}
	}

	@Override
	public void updateWeaponCost() {
	}
}
