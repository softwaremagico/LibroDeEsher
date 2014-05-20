package com.softwaremagico.librodeesher.gui.training;

import java.util.List;

import com.softwaremagico.librodeesher.gui.characteristic.CompleteCharacteristicUpPanel;
import com.softwaremagico.librodeesher.gui.style.BaseFrame;
import com.softwaremagico.librodeesher.gui.style.BasePanel;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.characteristic.Characteristic;

public class TrainingCompleteCharacteristicPanel extends CompleteCharacteristicUpPanel {
	private static final long serialVersionUID = -8366887951953401212L;

	public TrainingCompleteCharacteristicPanel(CharacterPlayer character, BaseFrame parent,
			List<Characteristic> availableCharacteristics) {
		super(character, parent, availableCharacteristics);
	}

	@Override
	public BasePanel createBodyPanel(List<Characteristic> availableCharacteristics) {
		return new TrainingCharacteristicPanel(getCharacter(), availableCharacteristics);
	}
}
