package com.softwaremagico.librodeesher.gui.equipment;

import java.awt.GridLayout;

import com.softwaremagico.librodeesher.gui.style.BasePanel;
import com.softwaremagico.librodeesher.pj.equipment.Equipment;

public class EquipmentListPanel extends BasePanel {
	private static final long serialVersionUID = 6877410491444149606L;
	private static final int MIN_ROWS = 10;

	@Override
	public void update() {

	}

	public void add(Equipment equipment) {
		this.removeAll();
		setLayout(new GridLayout(0, 1));
		int linesAdded = 0;
		if (equipment != null) {
			EquipmentLine line = new EquipmentLine(equipment, getLineBackgroundColor(linesAdded));
			add(line);
			linesAdded++;
		}
		// Add empty lines.
		for (int i = linesAdded; i < MIN_ROWS; i++) {
			EquipmentLine line = new EquipmentLine(null, getLineBackgroundColor(i));
			add(line);
		}

		this.repaint();
		this.revalidate();
	}
}