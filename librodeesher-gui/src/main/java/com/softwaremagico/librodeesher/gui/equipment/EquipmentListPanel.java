package com.softwaremagico.librodeesher.gui.equipment;

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

import java.awt.GridLayout;
import java.util.HashSet;
import java.util.Set;

import com.softwaremagico.librodeesher.gui.equipment.EquipmentLine.EquipmentRemovedListener;
import com.softwaremagico.librodeesher.gui.style.BasePanel;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.equipment.Equipment;

public class EquipmentListPanel extends BasePanel {
	private static final long serialVersionUID = 6877410491444149606L;
	private static final int MIN_ROWS = 10;
	private CharacterPlayer characterPlayer;
	private Set<EquipmentLine> lines;

	protected EquipmentListPanel(CharacterPlayer characterPlayer) {
		this.characterPlayer = characterPlayer;
		refreshAll();
	}

	@Override
	public void update() {
		refreshAll();
	}

	public void refreshAll() {
		lines = new HashSet<>();
		this.removeAll();
		setLayout(new GridLayout(0, 1));
		int linesAdded = 0;
		if (characterPlayer != null) {
			for (Equipment equipment : characterPlayer.getAllNotMagicEquipment()) {
				if (equipment != null) {
					EquipmentLine line = new EquipmentLine(equipment, getLineBackgroundColor(linesAdded));
					add(line);
					lines.add(line);
					linesAdded++;
				}
			}
		}
		// Add empty lines.
		for (int i = linesAdded; i < MIN_ROWS; i++) {
			EquipmentLine line = new EquipmentLine(null, getLineBackgroundColor(i));
			add(line);
		}
		// this.revalidate();
		// this.repaint();
	}

	public Set<Equipment> getSelectedLines() {
		Set<Equipment> items = new HashSet<>();
		for (EquipmentLine line : lines) {
			if (line.isSelected()) {
				items.add(line.getEquipment());
			}
		}
		return items;
	}

	public void addEquipmentRemovedListener(EquipmentRemovedListener equipmentRemovedListener) {
		for (EquipmentLine line : lines) {
			line.addEquipmentRemovedListener(equipmentRemovedListener);
		}
	}
}