package com.softwaremagico.librodeesher.gui.profession;
/*
 * #%L
 * Libro de Esher GUI
 * %%
 * Copyright (C) 2007 - 2013 Softwaremagico
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

import java.awt.GridLayout;
import java.util.List;

import com.softwaremagico.librodeesher.gui.elements.BaseSkillPanel;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.categories.Category;
import com.softwaremagico.librodeesher.pj.skills.ChooseSkillGroup;

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
		List<ChooseSkillGroup> commonSkillsToChoose = character.getProfession().getCommonSkillsToChoose();
		
		
		
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