package com.softwaremagico.librodeesher.gui.history;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.softwaremagico.librodeesher.gui.elements.BaseSkillPanel;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.categories.Category;
import com.softwaremagico.librodeesher.pj.categories.CategoryFactory;
import com.softwaremagico.librodeesher.pj.skills.Skill;

public class HistorySkillsPanel extends BaseSkillPanel {
	private static final long serialVersionUID = -1612700951233838060L;
	private Hashtable<Category, List<HistorySkillLine>> skillLinesPerCategory;
	private HistorySkillPointsPanel parent;

	public HistorySkillsPanel(CharacterPlayer character, HistorySkillPointsPanel parent) {
		skillLinesPerCategory = new Hashtable<>();
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
