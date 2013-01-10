package com.softwaremagico.librodeesher.gui.elements;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;

public class TitleBackgroundPanel extends JPanel {
	private static final long serialVersionUID = -8594058841560988067L;
	private Color bgColor = Color.BLACK;

	public TitleBackgroundPanel(TitleLabel label) {
		setLayout(new BorderLayout());
		setBackground(bgColor);
		add(label);
	}
}
