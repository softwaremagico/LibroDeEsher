package com.softwaremagico.librodeesher.gui.elements;

import java.awt.Color;

import javax.swing.JTextField;

public class DevelopmentTextField extends JTextField {
	private static final long serialVersionUID = -830671781809359034L;

	public void setDevelopmentPoints(Integer points) {
		setText(points.toString());
		if (points < 0) {
			setForeground(Color.RED);
		} else {
			setForeground(Color.DARK_GRAY);
		}
	}

}
