package com.softwaremagico.librodeesher.gui.history;

import java.awt.GridLayout;

import com.softwaremagico.librodeesher.gui.elements.TitleLabel;
import com.softwaremagico.librodeesher.gui.style.BasicTitleLine;

public class HistoryCharacteristicTitle extends BasicTitleLine {
	private static final long serialVersionUID = -1457813839985757112L;
	private TitleLabel characteristicLabel, temporalLabel, potentialTextField, updateLabel;

	public HistoryCharacteristicTitle() {
		setElements();
	}

	private void setElements() {
		this.removeAll();
		setLayout(new GridLayout(1, 0));

		characteristicLabel = new TitleLabel("Caract");
		add(characteristicLabel);

		temporalLabel = new TitleLabel("Tem");
		add(temporalLabel);

		potentialTextField = new TitleLabel("Pot");
		add(potentialTextField);

		updateLabel = new TitleLabel("");
		add(updateLabel);
	}
}
