package com.softwaremagico.librodeesher.gui;

import java.awt.Color;

import com.softwaremagico.librodeesher.gui.elements.BaseSkillPanel;
import com.softwaremagico.librodeesher.gui.elements.GenericCategoryLine;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.categories.Category;

public class ResumedCategoryLine extends GenericCategoryLine {
	private static final long serialVersionUID = 7701060122798000781L;

	public ResumedCategoryLine(CharacterPlayer character, Category category, Color background,
			BaseSkillPanel parentWindow) {
		super(character, category, background, parentWindow);
		enableColumns(true, true, true, true, true, true, true, true);
		setRanksSelected(character.getCurrentLevelRanks(category));
	}

	@Override
	protected void setCurrentLevelRanks() {
		setRanksSelected(character.getCurrentLevelRanks(category));
	}

}
