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

import java.awt.Component;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.softwaremagico.librodeesher.gui.style.BasePanel;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.training.Training;
import com.softwaremagico.librodeesher.pj.training.TrainingCategory;
import com.softwaremagico.librodeesher.pj.training.TrainingSkill;

public class TrainingCategoryPanel extends BasePanel {
	private static final long serialVersionUID = -1784471371595517238L;
	private CharacterPlayer character;
	private Map<TrainingCategory, List<TrainingSkillLine>> trainingSkillLinesPerCategory = new HashMap<>();
	private List<TrainingCategoryLine> trainingCategoryLines;
	private Training training;

	public TrainingCategoryPanel(CharacterPlayer character, CompleteCategoryPanel parent) {
		this.character = character;
		setElements();
	}

	private void setElements() {
		this.removeAll();
		setLayout(new GridLayout(0, 1));
		int i = 0;

		trainingCategoryLines = new ArrayList<>();

		if (training != null) {
			for (TrainingCategory trainingCategory : training.getCategoriesWithRanks()) {
				TrainingCategoryLine categoryLine = new TrainingCategoryLine(character, trainingCategory, this,
						getLineBackgroundColor(i));
				add(categoryLine);
				trainingCategoryLines.add(categoryLine);
				trainingSkillLinesPerCategory.put(trainingCategory, new ArrayList<TrainingSkillLine>());

				i++;
				String selectedCategory = trainingCategory.getCategoryOptions().get(0);
				for (TrainingSkill skill : trainingCategory.getSkills(selectedCategory)) {
					TrainingSkillLine skillLine = new TrainingSkillLine(character, trainingCategory, skill, this,
							getLineBackgroundColor(i));
					add(skillLine);
					i++;
					trainingSkillLinesPerCategory.get(trainingCategory).add(skillLine);
				}
			}
		}
	}

	public void setTraining(Training training) {
		this.training = training;
	}

	@Override
	public void update() {
		setElements();
	}

	protected Integer getSpinnerValues() {
		Integer total = 0;
		for (List<TrainingSkillLine> skills : trainingSkillLinesPerCategory.values()) {
			for (TrainingSkillLine lines : skills) {
				total += lines.getSelectedRanks();
			}
		}
		return total;
	}

	protected Integer getSpinnerValues(TrainingCategory trainingCategory) {
		Integer total = 0;
		if (trainingSkillLinesPerCategory.get(trainingCategory) != null) {
			for (TrainingSkillLine line : trainingSkillLinesPerCategory.get(trainingCategory)) {
				total += line.getSelectedRanks();
			}
			return total;
		}
		return 0;
	}

	protected Integer getSkillsWithRanks(TrainingCategory trainingCategory) {
		Integer total = 0;
		if (trainingSkillLinesPerCategory.get(trainingCategory) != null) {
			for (TrainingSkillLine line : trainingSkillLinesPerCategory.get(trainingCategory)) {
				if (line.getSelectedRanks() > 0) {
					total++;
				}
			}
			return total;
		}
		return 0;
	}

	protected Integer getSkillsPerCategory(TrainingCategory trainingCategory) {
		if (trainingSkillLinesPerCategory.get(trainingCategory) != null) {
			return trainingSkillLinesPerCategory.get(trainingCategory).size();
		}
		return 0;
	}

	public Training getTraining() {
		return training;
	}

	protected void removeSkillLinesOfCategory(TrainingCategory trainingCategory) {
		if (trainingSkillLinesPerCategory.get(trainingCategory) != null) {
			for (TrainingSkillLine line : trainingSkillLinesPerCategory.get(trainingCategory)) {
				this.remove(line);
			}
			trainingSkillLinesPerCategory.put(trainingCategory, new ArrayList<TrainingSkillLine>());
		}
	}

	protected void addSkillLinesOfCategory(TrainingCategory trainingCategory, int selectedCategory) {
		// Get the index of the category.
		int index = -1;
		for (int i = 0; i < this.getComponents().length; i++) {
			Component component = this.getComponents()[i];
			if (component instanceof TrainingCategoryLine) {
				if (((TrainingCategoryLine) component).getTrainingCategory().equals(trainingCategory)) {
					index = i + 1;
					break;
				}
			}
		}
		if (index >= 0 && selectedCategory < trainingCategory.getCategoryOptions().size()) {
			String selectedCategoryName = trainingCategory.getCategoryOptions().get(selectedCategory);
			for (TrainingSkill skill : trainingCategory.getSkills(selectedCategoryName)) {
				TrainingSkillLine skillLine = new TrainingSkillLine(character, trainingCategory, skill, this,
						getLineBackgroundColor(index));
				add(skillLine, index);
				index++;
				trainingSkillLinesPerCategory.get(trainingCategory).add(skillLine);
			}
		}
		this.revalidate();
		this.repaint();
	}
	
	public void setSkillRanks(){
		for(TrainingCategory trainingCategory : trainingSkillLinesPerCategory.keySet()){
			for(TrainingSkillLine skillLine : trainingSkillLinesPerCategory.get(trainingCategory)){
				skillLine.setSkillRanks();
			}
		}
	}

	public boolean repeatedCategory() {
		List<String> categories = new ArrayList<>();
		if (trainingCategoryLines != null) {
			for (TrainingCategoryLine categoryLine : trainingCategoryLines) {
				if (categoryLine.getTrainingCategory().needToChooseOneCategory()) {
					if (categories.contains(categoryLine.getChoosedCategory())) {
						return true;
					}
					categories.add(categoryLine.getChoosedCategory());
				}
			}
		}
		return false;
	}
}
