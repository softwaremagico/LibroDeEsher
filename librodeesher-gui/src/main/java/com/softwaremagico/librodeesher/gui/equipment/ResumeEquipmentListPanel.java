package com.softwaremagico.librodeesher.gui.equipment;

import com.softwaremagico.librodeesher.gui.elements.BaseScrollPanel;
import com.softwaremagico.librodeesher.gui.item.magic.BonusObjectList;
import com.softwaremagico.librodeesher.pj.equipment.MagicObject;

public class ResumeEquipmentListPanel extends BaseScrollPanel {
	private static final long serialVersionUID = 4077686958927513421L;
	private EquipmentTitle title;
	private BonusObjectList bonusPanel;

	public ResumeEquipmentListPanel() {
		title = new EquipmentTitle();
		addTitle(title);
		bonusPanel = new BonusObjectList();
		setBody(bonusPanel);
	}

	public void add(MagicObject magicItem) {
		bonusPanel.add(magicItem);
		this.repaint();
	}

}
