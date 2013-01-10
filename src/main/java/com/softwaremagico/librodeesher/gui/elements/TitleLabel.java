package com.softwaremagico.librodeesher.gui.elements;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class TitleLabel extends JLabel {
	private static final long serialVersionUID = 1929208979868170704L;
	private static Color fontColor = Color.LIGHT_GRAY;

	public TitleLabel(String text) {
		super(text);
		defineLabelStyle(SwingConstants.CENTER);
	}

	public TitleLabel(String text, int position) {
		super(text);
		defineLabelStyle(position);
	}
	
	public TitleLabel(String text, int position, int width, int height) {
		super(text);
		defineLabelStyle(position);
		setNewSize(width, height);
	}

	public TitleLabel(String text, int width, int height) {
		super(text);
		defineLabelStyle(SwingConstants.CENTER);
		setNewSize(width, height);
	}

	private void defineLabelStyle(int position) {
		setFont(getFont().deriveFont(Font.BOLD));
		setForeground(fontColor);
		setHorizontalAlignment(position);
	}

	private void setNewSize(int width, int height) {
		setMinimumSize(new Dimension(width, height));
		setPreferredSize(new Dimension(width, height));
	}
}
