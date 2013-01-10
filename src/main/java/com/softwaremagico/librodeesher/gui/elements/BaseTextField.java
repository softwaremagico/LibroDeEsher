package com.softwaremagico.librodeesher.gui.elements;

import javax.swing.JTextField;

public class BaseTextField extends JTextField {
	private static final long serialVersionUID = -1603922361590650160L;

	public BaseTextField() {
		setHorizontalAlignment(JTextField.CENTER);
	}

	public BaseTextField(String value) {
		super(value);
		setHorizontalAlignment(JTextField.CENTER);
	}
}
