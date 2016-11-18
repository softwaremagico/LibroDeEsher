package com.softwaremagico.librodeesher.gui.elements;

import java.awt.Font;

public abstract class BaseCategoryComboBox<E> extends BaseComboBox<E> {
	private static final long serialVersionUID = -7716708680287086972L;

	protected BaseCategoryComboBox() {
		super();
		setFont(getFont().deriveFont(Font.BOLD));
	}

}
