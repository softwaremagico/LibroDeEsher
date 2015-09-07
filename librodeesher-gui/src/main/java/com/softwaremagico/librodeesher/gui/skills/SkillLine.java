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
import java.util.ArrayList;
import java.util.List;

import com.softwaremagico.librodeesher.gui.elements.BaseSkillPanel;
import com.softwaremagico.librodeesher.gui.elements.GenericSkillLine;
import com.softwaremagico.librodeesher.gui.skills.SelectSkillWindow.SkillEnabledListener;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.skills.Skill;
import com.softwaremagico.librodeesher.pj.skills.SkillForEnablingMustBeSelected;

public class SkillLine extends GenericSkillLine {
	private static final long serialVersionUID = -4551393729801208171L;
	private SelectSkillWindow selectSkillWindow;
	private List<SkillEnabledListener> listeners;

	public SkillLine(CharacterPlayer character, Skill skill, Color background, BaseSkillPanel parentWindow) {
		super(character, skill, background, parentWindow);
		enableColumns(true, true, true, true, true, true, true, true);
		setRanksSelected(character.getCurrentLevelRanks(skill));
		listeners = new ArrayList<>();
	}

	@Override
	protected void updateCharacterPlayerWithCurrentLevelRanks() {
		Integer ranks = getRanksSelected();
		setRanksSelected(ranks); // order the ranks.
		try {
			character.setCurrentLevelRanks(skill, ranks);
			// If ranks set to zero, removed enabled skills
			if (ranks == 0) {
				for (String skillSelected : skill.getEnableSkills()) {
					for (SkillEnabledListener listener : listeners) {
						listener.skillEnabledEvent(skill, skillSelected, false);
					}
				}
			}
		} catch (SkillForEnablingMustBeSelected e) {
			// Select skill for enabling.
			selectSkillWindow = new SelectSkillWindow(character, skill);
			selectSkillWindow.setVisible(true);
			selectSkillWindow.addSkillEnabledListener(new SkillEnabledListener() {

				@Override
				public void skillEnabledEvent(Skill skill, String skillSelected, boolean selected) {
					for (SkillEnabledListener listener : listeners) {
						listener.skillEnabledEvent(skill, skillSelected, selected);
					}
				}
			});
		}
	}

	public void addSkillEnabledListener(SkillEnabledListener listener) {
		listeners.add(listener);
	}

}
