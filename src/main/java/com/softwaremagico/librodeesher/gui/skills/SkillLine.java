package com.softwaremagico.librodeesher.gui.skills;

import java.awt.Color;

import com.softwaremagico.librodeesher.gui.elements.BaseSkillPanel;
import com.softwaremagico.librodeesher.gui.elements.GenericSkillLine;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.skills.Skill;

public class SkillLine extends GenericSkillLine {

	private static final long serialVersionUID = -4551393729801208171L;

	public SkillLine(CharacterPlayer character, Skill skill, Color background, BaseSkillPanel parentWindow) {
		super(character, skill, background, parentWindow);
		enableColumns(true, true, true, true, true, true, true, true);
	}
	
	@Override
	protected void setCurrentLevelRanks() {
		Integer ranks = getRanksSelected();
		// order the ranks.
		setRanksSelected(ranks);
		character.setCurrentLevelRanks(skill, ranks);
	}
	
	public void updateCategory() {
		if (bonusCategoryPanel) {
			bonusCategory.setText(character.getTotalValue(skill.getCategory()).toString());
		}
		if (totalPanel) {
			totalLabel.setText(character.getTotalValue(skill).toString());
		}
	}

}
