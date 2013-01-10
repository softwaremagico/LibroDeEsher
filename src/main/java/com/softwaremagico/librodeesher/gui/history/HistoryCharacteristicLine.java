package com.softwaremagico.librodeesher.gui.history;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.SwingConstants;

import com.softwaremagico.librodeesher.gui.elements.BoldListLabel;
import com.softwaremagico.librodeesher.gui.elements.ListBackgroundPanel;
import com.softwaremagico.librodeesher.gui.style.BaseFrame;
import com.softwaremagico.librodeesher.gui.style.BasicLine;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.characteristic.Characteristic;

public class HistoryCharacteristicLine extends BasicLine {
	private static final long serialVersionUID = 5817155091343950674L;
	protected CharacterPlayer character;
	protected Characteristic characteristic;
	private BoldListLabel characteristicLabel, temporalText, potentialText;
	private BaseFrame parentWindow;
	private JButton updateButton;

	public HistoryCharacteristicLine(CharacterPlayer character, Characteristic characteristic,
			Color background) {
		this.character = character;
		this.characteristic = characteristic;
		setElements(background);
		setBackground(background);
	}

	protected void setElements(Color background) {
		this.removeAll();
		setLayout(new GridLayout(1, 0, 3, 3));

		characteristicLabel = new BoldListLabel(characteristic.getAbbreviature(), SwingConstants.LEFT);
		add(new ListBackgroundPanel(characteristicLabel, background));

		temporalText = new BoldListLabel("0");
		add(new ListBackgroundPanel(temporalText, background));

		potentialText = new BoldListLabel("0");
		add(new ListBackgroundPanel(potentialText, background));

		updateButton = new JButton("Subir");
		add(updateButton);

		update();
	}

	public void update() {
		temporalText.setText(character.getCharacteristicTemporalValues(characteristic.getAbbreviature())
				.toString());
		potentialText.setText(character.getCharacteristicPotentialValues(characteristic.getAbbreviature())
				.toString());
		updateButton.setEnabled(character.getRemainingHistorialPoints() > 0);
	}

	public void setParentWindow(BaseFrame window) {
		parentWindow = window;
	}

}
