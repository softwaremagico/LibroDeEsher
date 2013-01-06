package com.softwaremagico.librodeesher.gui.skills;

import java.awt.Color;

import com.softwaremagico.librodeesher.gui.elements.GenericCategoryLine;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.categories.Category;

public class CategoryLine extends GenericCategoryLine {
	private static final long serialVersionUID = -4406447607170722318L;

	public CategoryLine(CharacterPlayer character, Category category, Color background,
			SkillPanel parentWindow) {
		super(character, category, background, parentWindow);
		enableColumns(true, true, true, true, true, true, true, true);
	}

}
