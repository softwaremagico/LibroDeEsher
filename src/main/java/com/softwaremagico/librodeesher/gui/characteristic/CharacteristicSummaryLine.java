package com.softwaremagico.librodeesher.gui.characteristic;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.characteristic.Characteristic;

public class CharacteristicSummaryLine extends CharacteristicLine {
	private static final long serialVersionUID = 3396829275749133929L;

	public CharacteristicSummaryLine(CharacterPlayer character, Characteristic characteristic, Color background) {
		super(character, characteristic, background);
	}
	
	protected void setElements(Color background) {
		this.removeAll();
		setLayout(new GridLayout(1,2));

		characteristicLabel = new JLabel(characteristic.getAbbreviature());
		add(createLabelInsidePanel(characteristicLabel, SwingConstants.LEFT, background, fontColor));

		totalLabel = new JLabel("0");
		add(createLabelInsidePanel(totalLabel, SwingConstants.RIGHT, background, fontColor));		
	}
	
	public void update() {
		if (totalLabel != null && character.areCharacteristicsConfirmed()) {
			totalLabel.setText(character.getCharacteristicTotalBonus(characteristic.getAbbreviature())
					.toString());
		}
	}

}
