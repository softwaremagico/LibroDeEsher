package com.softwaremagico.librodeesher.gui.skills.favourite;

/*
 * #%L
 * Libro de Esher GUI
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

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import com.softwaremagico.librodeesher.gui.style.BasePanel;

public class SkillsListPanel extends BasePanel {
	private static final long serialVersionUID = -4518185385799868381L;
	private SelectedSkillsPanel parent;
	private List<FavouriteSkillLine> optionLines;
	private static final int MIN_ROWS = 6;

	public SkillsListPanel(SelectedSkillsPanel parent) {
		this.parent = parent;
		optionLines = new ArrayList<>();
	}

	public SkillsListPanel(List<String> options, SelectedSkillsPanel parent) {
		this.parent = parent;
		optionLines = new ArrayList<>();
		setElements(options);
	}

	public void setElements(List<String> options) {
		this.removeAll();
		setLayout(new GridLayout(0, 1));
		int linesAdded = 0;
		for (String option : options) {
			FavouriteSkillLine optionLine = new FavouriteSkillLine(option, getLineBackgroundColor(linesAdded));
			add(optionLine);
			optionLines.add(optionLine);
			linesAdded++;
		}
		// Add empty lines.
		for (int i = linesAdded; i < MIN_ROWS; i++) {
			FavouriteSkillLine line = new FavouriteSkillLine(null, getLineBackgroundColor(i));
			add(line);
		}
	}

	public void update() {
		parent.update();
	}

	@Override
	public void setEnabled(boolean enabled) {
		for (FavouriteSkillLine line : optionLines) {
			line.setEnabled(enabled);
		}
	}
}
