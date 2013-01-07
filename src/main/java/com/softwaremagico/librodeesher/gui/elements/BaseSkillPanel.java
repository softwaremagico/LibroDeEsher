package com.softwaremagico.librodeesher.gui.elements;

import com.softwaremagico.librodeesher.gui.style.BasePanel;
import com.softwaremagico.librodeesher.pj.categories.Category;

public abstract class BaseSkillPanel extends BasePanel {
	private static final long serialVersionUID = -6096684050785223493L;

	public abstract void update();

	public abstract void updateSkillsOfCategory(Category category);
}
