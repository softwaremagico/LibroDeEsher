package com.softwaremagico.librodeesher.gui.magicitem;

import com.softwaremagico.librodeesher.gui.elements.BaseScrollPanel;
import com.softwaremagico.librodeesher.pj.equipment.MagicObject;

public class ResumeMagicObjectPanel extends BaseScrollPanel {
	private static final long serialVersionUID = 4077686958927513421L;
	private BonusObjectTitle title;
	private BonusObjectList bonusPanel;
	
	public ResumeMagicObjectPanel() {
		title = new BonusObjectTitle();
		addTitle(title);
		bonusPanel = new BonusObjectList();
		setBody(bonusPanel);
	}

	public void update(MagicObject magicItem) {
		bonusPanel.update(magicItem);
		this.repaint();
	}

}
