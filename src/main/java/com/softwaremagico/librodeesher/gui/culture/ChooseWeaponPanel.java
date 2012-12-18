package com.softwaremagico.librodeesher.gui.culture;

import java.awt.Color;
import java.awt.GridLayout;

import com.softwaremagico.librodeesher.gui.style.BasePanel;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.categories.Category;
import com.softwaremagico.librodeesher.pj.categories.CategoryFactory;
import com.softwaremagico.librodeesher.pj.skills.Skill;

public class ChooseWeaponPanel extends BasePanel {

	private static final long serialVersionUID = 544393371168606333L;

	public ChooseWeaponPanel(CharacterPlayer character) {
		setElements(character);
	}

	private void setElements(CharacterPlayer character) {
		this.removeAll();
		setLayout(new GridLayout(0, 1));
		int i = 0;

		for (Category category : CategoryFactory.getWeaponsCategories()) {
			
			add(new WeaponCategoryLine(category, 2, getBgColor(i)));
			i++;

			for (Skill skill : category.getSkills()) {
				add(new WeaponSkillLine(skill, 2, getBgColor(i)));
				i++;
			}
		}
	}
	
	private Color getBgColor(int i){
		if (i % 2 == 0) {
			return Color.WHITE;
		} else {
			return Color.LIGHT_GRAY;
		}
	}
}
