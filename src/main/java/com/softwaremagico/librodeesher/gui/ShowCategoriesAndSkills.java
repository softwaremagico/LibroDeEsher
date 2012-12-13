package com.softwaremagico.librodeesher.gui;
/*
 * #%L
 * Libro de Esher
 * %%
 * Copyright (C) 2007 - 2012 Softwaremagico
 * %%
 * This software is designed by Jorge Hortelano Otero. Jorge Hortelano Otero
 * <softwaremagico@gmail.com> C/Quart 89, 3. Valencia CP:46008 (Spain).
 *  
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *  
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *  
 * You should have received a copy of the GNU General Public License along with
 * this program; If not, see <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.softwaremagico.librodeesher.gui.style.BasicLine;
import com.softwaremagico.librodeesher.pj.categories.Category;
import com.softwaremagico.librodeesher.pj.skills.Skill;

public class ShowCategoriesAndSkills extends BasicLine {
	private static final long serialVersionUID = -4730374712329547378L;
	private JPanel displayPanel = new JPanel();

	/**
	 * Create the panel.
	 */
	public ShowCategoriesAndSkills(List<Category> categories) {
		setElements();
		setCategoriesAndSkills(categories);
	}

	private void setElements() {
		displayPanel.setPreferredSize(this.getPreferredSize());
		JScrollPane displayScrollPane = new JScrollPane(displayPanel);
		add(displayScrollPane);
	}

	private void setCategoriesAndSkills(List<Category> categories) {
		for (Category category : categories) {
			CategoryLine categoryLine = new CategoryLine(category.getName());
			displayPanel.add(categoryLine);
			for (Skill skill : category.getSkills()) {
				SkillLine skillLine = new SkillLine(skill.getName());
				displayPanel.add(skillLine);
			}
		}
	}

}
