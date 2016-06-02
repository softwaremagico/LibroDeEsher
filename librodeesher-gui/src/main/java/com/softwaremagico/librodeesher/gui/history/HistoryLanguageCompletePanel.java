package com.softwaremagico.librodeesher.gui.history;

import com.softwaremagico.librodeesher.gui.elements.BaseScrollPanel;
import com.softwaremagico.librodeesher.gui.style.BaseFrame;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;

public class HistoryLanguageCompletePanel extends BaseScrollPanel {
	private static final long serialVersionUID = -9212875782853105580L;
	private BaseFrame parent;
	private HistoryLanguageTitle title;
	private HistoryLanguagePanel languagePanel;

	public HistoryLanguageCompletePanel(CharacterPlayer character, BaseFrame parent) {
		this.parent = parent;
		title = new HistoryLanguageTitle();
		addTitle(title);
		languagePanel = new HistoryLanguagePanel(character, this);
		setBody(languagePanel);
	}

	@Override
	public void update() {
		parent.updateFrame();
	}

	public void sizeChanged() {
		if (title != null) {
			title.sizeChanged();
		}
	}

	public void setTitleRanks(int ranks) {
		title.setRanks(ranks);
	}
}
