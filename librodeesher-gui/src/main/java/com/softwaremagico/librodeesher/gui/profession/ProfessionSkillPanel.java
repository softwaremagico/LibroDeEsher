package com.softwaremagico.librodeesher.gui.profession;

import java.awt.GridLayout;

import com.softwaremagico.librodeesher.gui.elements.BaseSkillPanel;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.categories.Category;

public class ProfessionSkillPanel extends BaseSkillPanel {
	private ProfessionCompleteSkillPointsPanel parent;

	public ProfessionSkillPanel(CharacterPlayer character, ProfessionCompleteSkillPointsPanel parent) {
		this.parent = parent;
		setElements(character);
	}

	public void setElements(CharacterPlayer character) {
		this.removeAll();
		setLayout(new GridLayout(0, 1));
		int i = 0;
		
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
