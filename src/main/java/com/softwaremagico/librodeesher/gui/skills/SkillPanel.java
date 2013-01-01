package com.softwaremagico.librodeesher.gui.skills;

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
