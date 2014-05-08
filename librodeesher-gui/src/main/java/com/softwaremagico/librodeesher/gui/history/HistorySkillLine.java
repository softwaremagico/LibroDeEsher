package com.softwaremagico.librodeesher.gui.history;

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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JPanel;

import com.softwaremagico.librodeesher.gui.elements.BaseCheckBox;
import com.softwaremagico.librodeesher.gui.elements.BaseSkillPanel;
import com.softwaremagico.librodeesher.gui.elements.GenericSkillLine;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.skills.Skill;

public class HistorySkillLine extends GenericSkillLine {
	private static final long serialVersionUID = 5951462195062999304L;
	private BaseCheckBox historyCheckBox;

	public HistorySkillLine(CharacterPlayer character, Skill skill, Color background,
			BaseSkillPanel parentWindow) {
		super(character, skill, background, parentWindow);
		enableColumns(false, false, false, false, false, false, false, true);
		addHistoryCheckBox();
	}

	private void addHistoryCheckBox() {
		JPanel panel = new JPanel();
		historyCheckBox = new BaseCheckBox("");
		historyCheckBox.setSelected(character.isHistoryPointSelected(skill));
		panel.add(historyCheckBox);
		historyCheckBox.addItemListener(new CheckBoxListener());
		addColumn(panel, 0);
	}

	class CheckBoxListener implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent e) {
			character.setHistoryPoints(skill, historyCheckBox.isSelected());
			if (character.getRemainingHistorialPoints() < 0) {
				historyCheckBox.setSelected(false);
			}
			update();
		}
	}
	
	public void updateComboBox() {
		historyCheckBox.setSelected(character.isHistoryPointSelected(getSkill()));
	}

}
