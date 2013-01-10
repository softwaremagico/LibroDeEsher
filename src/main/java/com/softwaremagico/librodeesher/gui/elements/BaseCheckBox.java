package com.softwaremagico.librodeesher.gui.elements;

import javax.swing.JCheckBox;

import com.softwaremagico.librodeesher.gui.style.Fonts;

public class BaseCheckBox extends JCheckBox {
	private static final long serialVersionUID = 2103084613671287820L;

	public BaseCheckBox(String text){
		super(text);
		setDefaultFont();
	}
	
	protected void setDefaultFont() {
		setFont(Fonts.getInstance().getBoldFont());
	}
}
