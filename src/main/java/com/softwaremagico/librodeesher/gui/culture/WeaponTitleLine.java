package com.softwaremagico.librodeesher.gui.culture;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import com.softwaremagico.librodeesher.gui.style.BasicTitleLine;

public class WeaponTitleLine extends BasicTitleLine {
	private static final long serialVersionUID = 4480268296161276440L;

	public WeaponTitleLine() {
		setElements(background);
		setBackground(background);
	}

	protected void setElements(Color background) {
		this.removeAll();
		setLayout(new GridLayout(1, 2));

		JLabel weaponCategoryLabel = new JLabel("Armas");
		add(createLabelInsidePanel(weaponCategoryLabel, SwingConstants.CENTER, background, fontColor));

		JLabel rankLabel = new JLabel("Rangos");
		add(createLabelInsidePanel(rankLabel, SwingConstants.CENTER, background, fontColor));

	}
}
