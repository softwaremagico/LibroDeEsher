package com.softwaremagico.librodeesher.gui.equipment;

import com.softwaremagico.librodeesher.gui.style.BaseFrame;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;

public class InsertEquipmentWindow extends BaseFrame {
	private static final long serialVersionUID = 2715820195499102991L;
	private CharacterPlayer characterPlayer;

	public InsertEquipmentWindow(CharacterPlayer characterPlayer) {
		this.characterPlayer = characterPlayer;
		defineWindow(400, 420);
		setResizable(false);
		setElements();
	}

	private void setElements() {

	}

	@Override
	public void updateFrame() {
	}

}
