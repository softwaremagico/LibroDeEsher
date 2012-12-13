package com.softwaremagico.librodeesher.gui.elements;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;

import com.softwaremagico.librodeesher.pj.characteristic.Characteristic;

public class CharacteristicSummaryLine extends CharacteristicLine {
	private static final long serialVersionUID = 3396829275749133929L;

	public CharacteristicSummaryLine(Characteristic charact, Color background) {
		super(charact, background);
	}
	
	protected void setElements(Characteristic charact, Color background) {
		this.removeAll();
		setLayout(new GridLayout(1,2));

		characteristicLabel = new JLabel(charact.getAbbreviation());
		add(createLabelInsidePanel(characteristicLabel, false, background, fontColor));

		totalLabel = new JLabel(charact.getTotal().toString());
		add(createLabelInsidePanel(totalLabel, true, background, fontColor));
		
	}

}
