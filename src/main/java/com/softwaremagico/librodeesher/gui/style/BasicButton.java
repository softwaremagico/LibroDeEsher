package com.softwaremagico.librodeesher.gui.style;

import java.awt.Dimension;

import javax.swing.JButton;

public abstract class BasicButton extends JButton{
	private static final long serialVersionUID = 2134660390585477867L;

	protected void setDefaultStyle(){
		setMinimumSize(new Dimension(0, 40));
	}
}
