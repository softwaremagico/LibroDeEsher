package com.softwaremagico.librodeesher.gui.history;

import com.softwaremagico.librodeesher.gui.elements.BaseScrollPanel;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;

public class HistoryCompleteCharacteristicPanel extends BaseScrollPanel {
	private static final long serialVersionUID = 3944923090293710832L;
	private HistoryWindow parent;
	CharacterPlayer character;
	private HistorySkillTitle title;
	private HistoryCharacteristicPanel characteristicPanel;

	public HistoryCompleteCharacteristicPanel(CharacterPlayer character, HistoryWindow parent) {
		this.character = character;
		this.parent = parent;
		title = new HistorySkillTitle();
		addTitle(title);
		characteristicPanel = new HistoryCharacteristicPanel(character, this);
		addBody(characteristicPanel);
	}

	public void update() {
		parent.update();
	}

	public void sizeChanged() {
		if (title != null) {
			title.sizeChanged();
		}
	}

	

}
