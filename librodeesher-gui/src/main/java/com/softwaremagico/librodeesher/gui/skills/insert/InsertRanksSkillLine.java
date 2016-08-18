package com.softwaremagico.librodeesher.gui.skills.insert;

import java.awt.Color;

import com.softwaremagico.librodeesher.gui.elements.BaseSkillPanel;
import com.softwaremagico.librodeesher.gui.elements.GenericSkillLine;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.skills.Skill;

public class InsertRanksSkillLine extends GenericSkillLine {
	private static final long serialVersionUID = -7339945564702313803L;

	public InsertRanksSkillLine(CharacterPlayer character, Skill skill, int nameLength, Color background,
			BaseSkillPanel parentWindow) {
		super(character, skill, nameLength, background, parentWindow);
	}

}
