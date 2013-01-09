package com.softwaremagico.librodeesher.gui.history;
/*
 * #%L
 * Libro de Esher
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

import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

import com.softwaremagico.librodeesher.gui.elements.BaseSkillPanel;
import com.softwaremagico.librodeesher.gui.elements.GenericCategoryLine;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.categories.Category;

public class HistoryCategoryLine extends GenericCategoryLine {
	private static final long serialVersionUID = -3523895407174764934L;
	private JCheckBox historyCheckBox;

	public HistoryCategoryLine(CharacterPlayer character, Category category, Color background,
			BaseSkillPanel parentWindow) {
		super(character, category, background, parentWindow);
		enableColumns(false, false, false, false, false, false, false, true);
		addHistoryCheckBox();
	}

	private void addHistoryCheckBox() {
		JPanel panel = new JPanel();
		historyCheckBox = new JCheckBox("H");
		panel.add(historyCheckBox);
		historyCheckBox.addItemListener(new CheckBoxListener());
		addColumn(panel, 1);
	}
	
	class CheckBoxListener implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent e) {
			character.setHistoryPoints(category, historyCheckBox.isSelected());
			if (character.getRemainingHistorialPoints() < 0) {
				historyCheckBox.setSelected(false);
			}
			update();
		}
	}

	@Override
	protected void setCurrentLevelRanks() {
		// NO RANKS
	}

}
