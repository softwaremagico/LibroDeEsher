package com.softwaremagico.librodeesher.gui.history;

import java.awt.Color;

import com.softwaremagico.librodeesher.gui.elements.BaseSkillPanel;
import com.softwaremagico.librodeesher.gui.elements.GenericSkillLine;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.skills.Skill;

public class HistorySkillLine extends GenericSkillLine {
	private static final long serialVersionUID = 5951462195062999304L;

	public HistorySkillLine(CharacterPlayer character, Skill skill, Color background,
			BaseSkillPanel parentWindow) {
		super(character, skill, background, parentWindow);
		enableColumns(true, false, false, false, false, false, false, true);
	}

}
