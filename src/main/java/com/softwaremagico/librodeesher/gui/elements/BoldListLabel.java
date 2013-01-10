package com.softwaremagico.librodeesher.gui.elements;

import java.awt.Font;

public class BoldListLabel extends ListLabel {
	private static final long serialVersionUID = -2810990111309979379L;

	public BoldListLabel(String text) {
		super(text);
	}

	public BoldListLabel(String text, int position) {
		super(text, position);
	}

	public BoldListLabel(String text, int position, int width, int height) {
		super(text, position, width, height);
	}

	public BoldListLabel(String text, int width, int height) {
		super(text, width, height);
	}

	protected void setDefaultFont() {
		setFont(getFont().deriveFont(Font.BOLD));
	}

}
