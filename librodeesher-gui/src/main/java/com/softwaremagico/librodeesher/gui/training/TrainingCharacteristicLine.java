package com.softwaremagico.librodeesher.gui.training;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.softwaremagico.librodeesher.gui.characteristic.CharacteristicUpLine;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.characteristic.Characteristic;

public class TrainingCharacteristicLine extends CharacteristicUpLine {
	private static final long serialVersionUID = -9002920559029367594L;

	public TrainingCharacteristicLine(CharacterPlayer character, Characteristic characteristic,
			Color background) {
		super(character, characteristic, background);
	}

	@Override
	public void addAcceptListener() {
		getUpdateButton().addActionListener(new UpdateButtonListener());
	}

	public class UpdateButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {

		}
	}

	@Override
	public void setAcceptEnabled() {
		getUpdateButton().setEnabled(true);
	}
}
