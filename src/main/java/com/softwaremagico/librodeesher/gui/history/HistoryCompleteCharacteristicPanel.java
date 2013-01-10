package com.softwaremagico.librodeesher.gui.history;

import com.softwaremagico.librodeesher.gui.elements.BaseScrollPanel;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;

public class HistoryCompleteCharacteristicPanel extends BaseScrollPanel {
	private static final long serialVersionUID = 3944923090293710832L;
	private HistoryWindow parent;
	CharacterPlayer character;
	private HistoryCharacteristicTitle title;
	private HistoryCharacteristicPanel characteristicPanel;

	public HistoryCompleteCharacteristicPanel(CharacterPlayer character, HistoryWindow parent) {
		this.character = character;
		this.parent = parent;
		title = new HistoryCharacteristicTitle();
		addTitle(title);
		characteristicPanel = new HistoryCharacteristicPanel(character, this);
		characteristicPanel.setParentWindow(parent);
		addBody(characteristicPanel);
	}

	public void update() {
		characteristicPanel.update();
	}

}
