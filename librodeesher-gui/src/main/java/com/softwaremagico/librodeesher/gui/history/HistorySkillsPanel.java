package com.softwaremagico.librodeesher.gui.history;
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
import java.util.HashMap;
import java.util.List;

import com.softwaremagico.librodeesher.gui.elements.BaseSkillPanel;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.categories.Category;
import com.softwaremagico.librodeesher.pj.categories.CategoryFactory;
import com.softwaremagico.librodeesher.pj.skills.Skill;

public class HistorySkillsPanel extends BaseSkillPanel {
	private static final long serialVersionUID = -1612700951233838060L;
	private HashMap<Category, List<HistorySkillLine>> skillLinesPerCategory;
	private HistoryCompleteSkillPointsPanel parent;

	public HistorySkillsPanel(CharacterPlayer character, HistoryCompleteSkillPointsPanel parent) {
		skillLinesPerCategory = new HashMap<>();
		this.parent = parent;
		setElements(character);
	}

	public void setElements(CharacterPlayer character) {
		this.removeAll();
		setLayout(new GridLayout(0, 1));
		int i = 0;
		for (Category category : CategoryFactory.getCategories()) {
			// Translate general category to player specific category.
			category = character.getCategory(category);
			if (character.isCategoryUseful(category)) {
				add(new HistoryCategoryLine(character, category, getLineBackgroundColor(i), this));
				i++;

				List<HistorySkillLine> skillLines = new ArrayList<>();
				for (Skill skill : category.getSkills()) {
					if (character.isSkillInteresting(skill)) {
						HistorySkillLine skillLine = new HistorySkillLine(character, skill,
								getLineBackgroundColor(i), this);
						add(skillLine);
						skillLines.add(skillLine);
						i++;
					}
				}
				skillLinesPerCategory.put(category, skillLines);
			}
		}
	}

	@Override
	public void update() {
		parent.update();

	}

	@Override
	public void updateSkillsOfCategory(Category category) {
		List<HistorySkillLine> skillLines = skillLinesPerCategory.get(category);
		if (skillLines != null) {
			for (HistorySkillLine skillLine : skillLines) {
				skillLine.update();
			}
		}
	}
}
