package com.softwaremagico.librodeesher.gui.skills;

/*
 * #%L
 * Libro de Esher
 * %%
 * Copyright (C) 2007 - 2013 Softwaremagico
 * %%
 * This software is designed by Jorge Hortelano Otero. Jorge Hortelano Otero
 * <softwaremagico@gmail.com> Valencia (Spain).
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

import java.awt.Color;

import com.softwaremagico.librodeesher.gui.elements.BaseSkillPanel;
import com.softwaremagico.librodeesher.gui.elements.GenericCategoryLine;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.categories.Category;

public class CategoryLine extends GenericCategoryLine {
	private static final long serialVersionUID = -4406447607170722318L;
	private final static int NAME_LENGTH = 200;

	public CategoryLine(CharacterPlayer character, Category category, Color background,
			BaseSkillPanel parentWindow) {
		super(character, category, NAME_LENGTH, background, parentWindow);
		enableColumns(true, true, false, true, false, true, true, true, true, true);
		setRanksSelected(character.getCurrentLevelRanks(category));
	}

	@Override
	protected void updateCharacterPlayerWithCurrentLevelRanks() {
		Integer ranks = getRanksSelected();
		setRanksSelected(ranks); // order the ranks.
		character.setCurrentLevelRanks(category, ranks);
	}

}
