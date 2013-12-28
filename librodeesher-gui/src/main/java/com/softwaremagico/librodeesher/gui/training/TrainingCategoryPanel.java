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

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import com.softwaremagico.librodeesher.gui.culture.WeaponSkillLine;
import com.softwaremagico.librodeesher.gui.style.BasePanel;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.training.Training;
import com.softwaremagico.librodeesher.pj.training.TrainingCategory;
import com.softwaremagico.librodeesher.pj.training.TrainingSkill;

public class TrainingCategoryPanel extends BasePanel {
	private static final long serialVersionUID = -1784471371595517238L;
	private CharacterPlayer character;
	private CompleteCategoryPanel parent;
	private List<TrainingSkillLine> trainingSkillLines = new ArrayList<>();
	private Training training;

	public TrainingCategoryPanel(CharacterPlayer character, Training training, CompleteCategoryPanel parent) {
		this.character = character;
		this.parent = parent;
		this.training = training;
		setElements();
	}

	private void setElements() {
		this.removeAll();
		setLayout(new GridLayout(0, 1));
		int i = 0;

		if (training != null) {
			for (TrainingCategory category : training.getCategoriesWithRanks()) {
				TrainingCategoryLine categoryLine = new TrainingCategoryLine(character, category, this,
						getLineBackgroundColor(i));
				add(categoryLine);
				i++;
				String selectedCategory = category.getCategoryOptions().get(0);
				for (TrainingSkill skill : category.getSkills(selectedCategory)) {
					TrainingSkillLine skillLine = new TrainingSkillLine(character, category, skill, this,
							getLineBackgroundColor(i));
					add(skillLine);
					i++;
					trainingSkillLines.add(skillLine);
				}
			}
		}
	}

	@Override
	public void update() {
	}

	protected Integer getSpinnerValues() {
		Integer total = 0;
		for (TrainingSkillLine lines : trainingSkillLines) {
			total += lines.getSelectedRanks();
		}
		return total;
	}

	public Training getTraining() {
		return training;
	}

}
