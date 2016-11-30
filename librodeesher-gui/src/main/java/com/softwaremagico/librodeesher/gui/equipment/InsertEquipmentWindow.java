package com.softwaremagico.librodeesher.gui.equipment;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import com.softwaremagico.librodeesher.gui.style.BaseFrame;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;

public class InsertEquipmentWindow extends BaseFrame {
	private static final long serialVersionUID = 2715820195499102991L;
	private CharacterPlayer characterPlayer;
	private EquipmentListPanel equipmentListPanel;

	public InsertEquipmentWindow(CharacterPlayer characterPlayer) {
		this.characterPlayer = characterPlayer;
		defineWindow(400, 420);
		setResizable(false);
		setElements();
	}

	private void setElements() {
		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
		GridBagConstraints constraints = new GridBagConstraints();

		equipmentListPanel = new EquipmentListPanel();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.LINE_START;
		constraints.gridx = 0;
		constraints.gridy = 8;
		constraints.gridwidth = 5;
		constraints.gridheight = 3;
		constraints.weightx = 1;
		constraints.weighty = 1;
		add(equipmentListPanel, constraints);

	}

	@Override
	public void updateFrame() {

	}

}
