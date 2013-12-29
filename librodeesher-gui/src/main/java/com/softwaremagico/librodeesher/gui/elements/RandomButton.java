package com.softwaremagico.librodeesher.gui.elements;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.softwaremagico.librodeesher.gui.style.BaseButton;

public abstract class RandomButton extends BaseButton {
	private static final long serialVersionUID = 5849998316894630638L;
	
	public RandomButton() {
		setDefaultStyle();
		this.setText("Aleatorio");
		this.setPreferredSize(new Dimension(80, 40));
		addActionListener(new RandomListener());
	}
	
	public abstract void RandomAction();
	
	class RandomListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			RandomAction();
		}
	}

}
