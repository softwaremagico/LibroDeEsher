package com.softwaremagico.librodeesher.gui.history;

import com.softwaremagico.librodeesher.gui.style.BasePanel;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;

public class HistoryCharacteristicPanel extends BasePanel {
	private static final long serialVersionUID = -3311606513343600118L;
	private HistoryCompleteCharacteristicPanel parent;

	public HistoryCharacteristicPanel(CharacterPlayer character, HistoryCompleteCharacteristicPanel parent) {
		this.parent = parent;
	}

}
