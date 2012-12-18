package com.softwaremagico.librodeesher.gui.characteristic;

import java.awt.Color;

import com.softwaremagico.librodeesher.pj.CharacterPlayer;

public class CharacteristicSummaryPanel extends CharacteristicPanel {
	private static final long serialVersionUID = 3269760175720338648L;

	protected CharacteristicLine createLine(CharacterPlayer character, Integer characteristicIndex,
			Color background) {
		return new CharacteristicSummaryLine(character, character.getCharacteristics().get(
				characteristicIndex), background);
	}

}
