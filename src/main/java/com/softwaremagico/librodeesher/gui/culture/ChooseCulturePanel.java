package com.softwaremagico.librodeesher.gui.culture;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import com.softwaremagico.librodeesher.gui.style.BasePanel;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;

public abstract class ChooseCulturePanel extends BasePanel {
	private static final long serialVersionUID = 1005307178713098657L;
	protected List<CultureLine> hobbyLines = new ArrayList<>();
	protected CultureTitleLine title;
	protected CharacterPlayer character;

	protected Integer getSpinnerValues() {
		Integer total = 0;
		for (CultureLine lines : hobbyLines) {
			total += lines.getSelectedRanks();
		}

		return total;
	}

	protected void setElements(CharacterPlayer character) {
		this.removeAll();
		setLayout(new GridLayout(0, 1));
		createElements();
	}

	protected abstract void createElements();

	protected abstract void setRankTitle(String rankLabelText);
}
