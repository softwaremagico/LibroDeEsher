package com.softwaremagico.librodeesher.gui.elements;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class ListLabel extends JLabel {
	private static final long serialVersionUID = 1929208979868170704L;
	private static Color fontColor = Color.DARK_GRAY;

	public ListLabel(String text) {
		super(text);
		defineLabelStyle(SwingConstants.CENTER);
	}

	public ListLabel(String text, int position) {
		super(text);
		defineLabelStyle(position);
	}

	public ListLabel(String text, int position, int width, int height) {
		super(text);
		defineLabelStyle(position);
		setNewSize(width, height);
	}

	public ListLabel(String text, int width, int height) {
		super(text);
		defineLabelStyle(SwingConstants.CENTER);
		setNewSize(width, height);
	}
	
	protected void setDefaultFont(){
		//DEFAULT SYSTEM FONT IS OK;
	}

	private void defineLabelStyle(int position) {
		setDefaultFont();
		setForeground(fontColor);
		setHorizontalAlignment(position);
	}

	private void setNewSize(int width, int height) {
		setMinimumSize(new Dimension(width, height));
		setPreferredSize(new Dimension(width, height));
	}

}
