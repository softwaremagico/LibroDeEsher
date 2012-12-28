package com.softwaremagico.librodeesher.gui.skills;

import java.awt.GridLayout;

import com.softwaremagico.librodeesher.gui.elements.CategoryLine;
import com.softwaremagico.librodeesher.gui.elements.SkillLine;
import com.softwaremagico.librodeesher.gui.style.BasePanel;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.categories.Category;
import com.softwaremagico.librodeesher.pj.categories.CategoryFactory;
import com.softwaremagico.librodeesher.pj.skills.Skill;

public class SkillPanel extends BasePanel {
	private static final long serialVersionUID = 544393371168606333L;

	public SkillPanel(CharacterPlayer character) {
		setElements(character);
	}

	private void setElements(CharacterPlayer character) {
		this.removeAll();
		setLayout(new GridLayout(0, 1));
		int i = 0;

		for (Category category : CategoryFactory.getCategories()) {
			add(new CategoryLine(category, getLineBackgroundColor(i)));
			i++;

			for (Skill skill : category.getSkills()) {
				SkillLine skillLine = new SkillLine(skill, getLineBackgroundColor(i));
				add(skillLine);
				i++;
			}
		}
	}
}
