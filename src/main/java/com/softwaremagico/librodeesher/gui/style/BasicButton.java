package com.softwaremagico.librodeesher.gui.style;

import java.awt.Dimension;

import javax.swing.JButton;

public class BasicButton extends JButton{
	private static final long serialVersionUID = 2134660390585477867L;
	
	public BasicButton(){
		
	}
	
	public BasicButton(String text){
		this.setText(text);
	}

	protected void setDefaultStyle(){
		setMinimumSize(new Dimension(80, 40));
	}
}
