package com.softwaremagico.librodeesher.gui.skills;

/*
 * #%L
 * Libro de Esher (GUI)
 * %%
 * Copyright (C) 2007 - 2015 Softwaremagico
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
import com.softwaremagico.librodeesher.gui.elements.GenericSkillLine;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.skills.Skill;

public class SpecializedSkillLine extends GenericSkillLine {
	private static final long serialVersionUID = -8691152095881115131L;
	private String specialization;
	private final static int NAME_LENGTH = 200;

	public SpecializedSkillLine(CharacterPlayer character, Skill skill, String specialization,
			Color background, BaseSkillPanel parentWindow) {
		super(character, skill, NAME_LENGTH, background, parentWindow);
		this.specialization = specialization;
		enableColumns(false, false, false, true, true, true, true, true);
	}

	@Override
	protected String getSkillName() {
		return "    " + specialization;
	}

	@Override
	protected String getRanksValue() {
		return character.getSpecializedRanksValue(skill).toString();
	}

	@Override
	protected String getTotalValue() {
		return character.getSpecializedTotalValue(skill).toString();
	}
}
