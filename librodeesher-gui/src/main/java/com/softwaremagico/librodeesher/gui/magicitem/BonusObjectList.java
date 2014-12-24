package com.softwaremagico.librodeesher.gui.magicitem;

import java.awt.GridLayout;

import com.softwaremagico.librodeesher.gui.style.BasePanel;
import com.softwaremagico.librodeesher.pj.equipment.MagicObject;
import com.softwaremagico.librodeesher.pj.equipment.ObjectBonus;

public class BonusObjectList extends BasePanel {
	private static final long serialVersionUID = 6877410491444149606L;

	@Override
	public void update() {

	}

	public void update(MagicObject magicItem) {
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
		for (int i = linesAdded; i < 3; i++) {
			BonusLine line = new BonusLine(null, getLineBackgroundColor(i));
			add(line);
		}

		this.repaint();
		this.revalidate();
	}
}
