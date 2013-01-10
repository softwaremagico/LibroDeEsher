package com.softwaremagico.librodeesher.gui.elements;

import javax.swing.JRadioButton;

import com.softwaremagico.librodeesher.gui.style.Fonts;

public class BaseRadioButton extends JRadioButton {
	private static final long serialVersionUID = 95310709047395444L;

	public BaseRadioButton(String text) {
		super(text);
		setDefaultFont();
	}
	
	protected void setDefaultFont() {
		setFont(Fonts.getInstance().getBoldFont());
	}

}
