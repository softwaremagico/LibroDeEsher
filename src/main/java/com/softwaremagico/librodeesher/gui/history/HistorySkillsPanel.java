package com.softwaremagico.librodeesher.gui.history;

import java.awt.GridLayout;

import com.softwaremagico.librodeesher.gui.elements.BaseSkillPanel;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.categories.Category;
import com.softwaremagico.librodeesher.pj.categories.CategoryFactory;
import com.softwaremagico.librodeesher.pj.skills.Skill;

public class HistorySkillsPanel extends BaseSkillPanel {
	private static final long serialVersionUID = -1612700951233838060L;
	
	public HistorySkillsPanel(CharacterPlayer character){
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

				for (Skill skill : category.getSkills()) {
					if (character.isSkillInteresting(skill)) {
						add(new HistorySkillLine(character, skill, getLineBackgroundColor(i), this));
						i++;
					}
				}
			}
		}
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateSkillsOfCategory(Category category) {
		// TODO Auto-generated method stub

	}
}
