package com.softwaremagico.librodeesher.gui.equipment;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.SwingConstants;

import com.softwaremagico.librodeesher.gui.elements.BoldListLabel;
import com.softwaremagico.librodeesher.gui.elements.ListBackgroundPanel;
import com.softwaremagico.librodeesher.gui.style.BaseLine;
import com.softwaremagico.librodeesher.pj.equipment.Equipment;

public class EquipmentLine extends BaseLine {
	private static final long serialVersionUID = -6440213804132215064L;
	private BoldListLabel equipmentNameLabel, equipmentDescription;
	private Equipment equipment;

	public EquipmentLine(Equipment equipment, Color background) {
		this.equipment = equipment;
		setDefaultBackground(background);
		setElements();
		setBackground(background);
	}

	@Override
	public void update() {

	}

	protected void setElements() {
		this.removeAll();
		setLayout(new GridBagLayout());
		GridBagConstraints gridBagConstraints = new GridBagConstraints();

		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.weighty = 0;

		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.weightx = 0.6;
		if (equipment != null) {
			equipmentNameLabel = new BoldListLabel(equipment.getName(), SwingConstants.LEFT, columnHeight);
		} else {
			equipmentNameLabel = new BoldListLabel("", SwingConstants.LEFT, columnHeight);
		}
		add(new ListBackgroundPanel(equipmentNameLabel, getDefaultBackground()), gridBagConstraints);

		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 0.3;
		if (equipment != null) {
			equipmentDescription = new BoldListLabel(equipment.getDescription(), columnHeight);
		} else {
			equipmentDescription = new BoldListLabel("", 50, columnHeight);
		}
		add(new ListBackgroundPanel(equipmentDescription, getDefaultBackground()), gridBagConstraints);
	}

}
