package com.softwaremagico.librodeesher.gui.elements;

import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;

import com.softwaremagico.librodeesher.gui.style.Fonts;

public class BaseSpinner extends JSpinner {
	private static final long serialVersionUID = -5360733915257515036L;

	public BaseSpinner() {
		setDefaultFont();
	}

	public BaseSpinner(SpinnerModel sm) {
		super(sm);
		setDefaultFont();
	}

	protected void setDefaultFont() {
		setFont(Fonts.getInstance().getBoldFont());
	}
	
	public void setColumns(int value){
		JComponent editor = getEditor();
		JFormattedTextField tf = ((JSpinner.DefaultEditor) editor).getTextField();
		tf.setColumns(value);
	}
}
