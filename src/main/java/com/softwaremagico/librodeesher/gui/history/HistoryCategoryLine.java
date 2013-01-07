package com.softwaremagico.librodeesher.gui.history;

import java.awt.Color;

import com.softwaremagico.librodeesher.gui.elements.BaseSkillPanel;
import com.softwaremagico.librodeesher.gui.elements.GenericCategoryLine;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.categories.Category;

public class HistoryCategoryLine extends GenericCategoryLine {
	private static final long serialVersionUID = -3523895407174764934L;

	public HistoryCategoryLine(CharacterPlayer character, Category category, Color background,
			BaseSkillPanel parentWindow) {
		super(character, category, background, parentWindow);
		enableColumns(false, false, false, false, false, false, false, true);
	}

	@Override
	protected void setCurrentLevelRanks() {
		//NO RANKS		
	}

}
