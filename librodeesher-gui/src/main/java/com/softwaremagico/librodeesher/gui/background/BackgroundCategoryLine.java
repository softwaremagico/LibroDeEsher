package com.softwaremagico.librodeesher.gui.background;

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
import com.softwaremagico.librodeesher.gui.elements.GenericCategoryLine;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.categories.Category;

public class BackgroundCategoryLine extends GenericCategoryLine {
	private static final long serialVersionUID = -3523895407174764934L;
	private final static int NAME_LENGTH = 200;
	private BaseCheckBox backgroundCheckBox;

	public BackgroundCategoryLine(CharacterPlayer character, Category category, Color background, BaseSkillPanel parentWindow) {
		super(character, category, NAME_LENGTH,  background, parentWindow);
		enableColumns(false, false, false, false, false, false, false, false, false, true);
		addHistoryCheckBox();
	}

	private void addHistoryCheckBox() {
		JPanel panel = new JPanel();
		backgroundCheckBox = new BaseCheckBox("");
		backgroundCheckBox.setSelected(character.isBackgroundPointSelected(category));
		panel.add(backgroundCheckBox);
		backgroundCheckBox.addItemListener(new CheckBoxListener());
		addColumn(panel, 0, 0.1f);
	}

	class CheckBoxListener implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent e) {
			character.setHistoryPoints(category, backgroundCheckBox.isSelected());
			if (character.getRemainingBackgroundPoints() < 0) {
				backgroundCheckBox.setSelected(false);
			}
			update();
		}
	}

	public void updateComboBox() {
		backgroundCheckBox.setSelected(character.isBackgroundPointSelected(getCategory()));
	}

	@Override
	protected void updateCharacterPlayerWithCurrentLevelRanks() {
		// NO RANKS
	}

}
