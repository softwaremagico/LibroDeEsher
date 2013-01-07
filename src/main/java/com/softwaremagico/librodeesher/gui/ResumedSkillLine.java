package com.softwaremagico.librodeesher.gui;

import java.awt.Color;

import com.softwaremagico.librodeesher.gui.elements.BaseSkillPanel;
import com.softwaremagico.librodeesher.gui.elements.GenericSkillLine;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.skills.Skill;

public class ResumedSkillLine extends GenericSkillLine {
	private static final long serialVersionUID = 4671606813162808731L;

	public ResumedSkillLine(CharacterPlayer character, Skill skill, Color background,
			BaseSkillPanel parentWindow) {
		super(character, skill, background, parentWindow);
		enableColumns(true, true, true, true, true, true, true, true);
		setRanksSelected(character.getCurrentLevelRanks(skill));
	}
	
	@Override
	protected void setCurrentLevelRanks() {
		setRanksSelected(character.getCurrentLevelRanks(skill));
	}

}
