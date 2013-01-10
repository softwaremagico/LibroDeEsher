package com.softwaremagico.librodeesher.gui.elements;

import javax.swing.JLabel;

import com.softwaremagico.librodeesher.gui.style.Fonts;

public class BaseLabel extends JLabel {
	private static final long serialVersionUID = -6466954441056939761L;

	public BaseLabel(String text){
		super(text);
		setDefaultFont();
	}
	
	protected void setDefaultFont() {
		setFont(Fonts.getInstance().getBoldFont());
	}
}
