package com.softwaremagico.librodeesher.gui.history;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import com.softwaremagico.librodeesher.gui.style.BaseFrame;
import com.softwaremagico.librodeesher.gui.style.BasePanel;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;

public class HistoryCharacteristicPanel extends BasePanel {
	private static final long serialVersionUID = -3311606513343600118L;
	private HistoryCompleteCharacteristicPanel parent;
	private List<HistoryCharacteristicLine> lines;

	public HistoryCharacteristicPanel(CharacterPlayer character, HistoryCompleteCharacteristicPanel parent) {
		this.parent = parent;
		lines = new ArrayList<>();
		setElements(character);
	}

	private void setElements(CharacterPlayer character) {
		this.removeAll();
		setLayout(new GridLayout(10, 1));

		Color background;
		for (int i = 0; i < character.getCharacteristics().size(); i++) {
			if (i % 2 == 0) {
				background = Color.WHITE;
			} else {
				background = Color.LIGHT_GRAY;
			}

			HistoryCharacteristicLine characteristicLine = createLine(character, i, background);
			lines.add(characteristicLine);
			add(characteristicLine);
		}
	}

	protected HistoryCharacteristicLine createLine(CharacterPlayer character, Integer characteristicIndex,
			Color background) {
		return new HistoryCharacteristicLine(character, character.getCharacteristics().get(
				characteristicIndex), background);
	}

	public void update() {
		for (HistoryCharacteristicLine line : lines) {
			line.update();
		}
	}

	public void setParentWindow(BaseFrame window) {
		for (HistoryCharacteristicLine line : lines) {
			line.setParentWindow(window);
		}
	}

}
