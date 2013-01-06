package com.softwaremagico.librodeesher.gui.history;

import com.softwaremagico.librodeesher.gui.style.BasePanel;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;

public class HistorySkillPointsPanel extends BasePanel {
	private static final long serialVersionUID = 4044886584364311850L;
	private HistoryWindow parent;
	CharacterPlayer character;

	public HistorySkillPointsPanel(CharacterPlayer character, HistoryWindow parent) {
		this.character = character;
		this.parent = parent;
	}
}
