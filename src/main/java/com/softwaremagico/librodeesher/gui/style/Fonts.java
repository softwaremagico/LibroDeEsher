package com.softwaremagico.librodeesher.gui.style;

import java.awt.Font;

import javax.swing.JLabel;

public class Fonts {
	private static Fonts INSTANCE = new Fonts();
	private Font defaultSystemFont;

	private Fonts() {
		JLabel label = new JLabel();
		defaultSystemFont = label.getFont();
	}

	public static Fonts getInstance() {
		return INSTANCE;
	}
	
	public Font getDefaultFont(){
		return defaultSystemFont;
	}
	
	public Font getBoldFont(){
		return defaultSystemFont.deriveFont(Font.BOLD);
	}
	
	
	
}
