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

import com.softwaremagico.librodeesher.gui.components.SkillWindow;
import com.softwaremagico.librodeesher.gui.elements.BaseScrollPanel;
import com.softwaremagico.librodeesher.gui.elements.BaseSkillPanel;
import com.softwaremagico.librodeesher.gui.elements.SkillTitleLine;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;

public abstract class CompleteSkillPanel extends BaseScrollPanel {
	private static final long serialVersionUID = -6707835769716507229L;
	private SkillTitleLine title;
	private BaseSkillPanel skillPanel;
	private SkillWindow parentWindow;

	public CompleteSkillPanel(CharacterPlayer character, SkillWindow parentWindow) {
		this.parentWindow = parentWindow;
		title = createSkillTitle();
		addTitle(title);
		skillPanel = createSkillPanel();
		setBody(skillPanel);
	}

	public abstract BaseSkillPanel createSkillPanel();
	
	public abstract SkillTitleLine createSkillTitle();

	public void sizeChanged() {
		if (title != null) {
			title.sizeChanged();
		}
	}

	@Override
	public void update() {
		parentWindow.updateFrame();
	}

	public void updateCategories() {
		setElements();
	}

	public void updateRanks() {
		skillPanel.updateRanks();
	}

	public void updateWeaponCost() {
		skillPanel.updateWeaponCost();
	}

}
