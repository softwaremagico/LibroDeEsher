package com.softwaremagico.librodeesher.gui.item.magic;
/*
 * #%L
 * Libro de Esher (GUI)
 * %%
 * Copyright (C) 2007 - 2014 Softwaremagico
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

import com.softwaremagico.librodeesher.gui.style.BasePanel;
import com.softwaremagico.librodeesher.pj.equipment.MagicObject;
import com.softwaremagico.librodeesher.pj.equipment.ObjectBonus;

public class BonusObjectList extends BasePanel {
	private static final long serialVersionUID = 6877410491444149606L;
	private static final int MIN_ROWS = 5;

	@Override
	public void update() {

	}

	public void add(MagicObject magicItem) {
		this.removeAll();
		setLayout(new GridLayout(0, 1));
		int linesAdded = 0;
		if (magicItem != null) {
			for (ObjectBonus bonus : magicItem.getBonus()) {
				BonusLine line = new BonusLine(bonus, getLineBackgroundColor(linesAdded));
				add(line);
				linesAdded++;
			}
		}
		// Add empty lines.
		for (int i = linesAdded; i < MIN_ROWS; i++) {
			BonusLine line = new BonusLine(null, getLineBackgroundColor(i));
			add(line);
		}

		this.repaint();
		this.revalidate();
	}
}
