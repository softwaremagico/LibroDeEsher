package com.softwaremagico.librodeesher.gui.skills.insert;

/*
 * #%L
 * Libro de Esher (GUI)
 * %%
 * Copyright (C) 2007 - 2016 Softwaremagico
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
import com.softwaremagico.librodeesher.gui.elements.BaseSkillPanel;
import com.softwaremagico.librodeesher.gui.elements.SkillTitleLine;
import com.softwaremagico.librodeesher.gui.skills.CompleteSkillPanel;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;

public class InsertRanksWindow extends SkillWindow {
	private static final long serialVersionUID = 1285242972167122110L;

	public InsertRanksWindow(CharacterPlayer character) {
		super(character);
		setElements();
	}

	@Override
	protected void setElements() {
		super.setElements();
	}

	@Override
	public void updateFrame() {

	}

	@Override
	protected CompleteSkillPanel createSkillPanel() {
		return new CompleteSkillPanel(character, this) {
			private static final long serialVersionUID = -8992775247823001768L;

			@Override
			public BaseSkillPanel createSkillPanel() {
				return new InsertRanksSkillPanel(character, this);
			}

			@Override
			public SkillTitleLine createSkillTitle() {
				return new InsertRanksSkillTitle();
			}

		};
	}

}
