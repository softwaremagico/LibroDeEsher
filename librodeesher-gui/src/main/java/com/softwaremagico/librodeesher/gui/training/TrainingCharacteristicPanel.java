package com.softwaremagico.librodeesher.gui.training;

import java.awt.Color;
import java.util.List;

import com.softwaremagico.librodeesher.gui.characteristic.CharacteristicUpLine;
import com.softwaremagico.librodeesher.gui.characteristic.CharacteristicUpPanel;
import com.softwaremagico.librodeesher.gui.history.HistoryCharacteristicLine;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.characteristic.Characteristic;

public class TrainingCharacteristicPanel extends CharacteristicUpPanel {
	private static final long serialVersionUID = -3419196143471099107L;

	public TrainingCharacteristicPanel(CharacterPlayer character, List<Characteristic> characteristics) {
		super(character, characteristics);
	}

	@Override
	public CharacteristicUpLine createLine(CharacterPlayer character, Characteristic characteristic,
			Color background) {
		return new HistoryCharacteristicLine(character, characteristic, background);
	}
}
