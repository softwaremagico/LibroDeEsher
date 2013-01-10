package com.softwaremagico.librodeesher.gui.elements;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;

public class ListBackgroundPanel extends JPanel {
	private static final long serialVersionUID = 8111566323041219062L;

	public ListBackgroundPanel(ListLabel label, Color bgColor) {
		setLayout(new BorderLayout());
		setBackground(bgColor);
		add(label);
	}
}
