package com.softwaremagico.librodeesher.gui.elements;

/*
 * #%L
 * Libro de Esher GUI
 * %%
 * Copyright (C) 2007 - 2013 Softwaremagico
 * %%
 * This software is designed by Jorge Hortelano Otero. Jorge Hortelano Otero
 * <softwaremagico@gmail.com> Valencia (Spain).
 *  
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *  
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *  
 * You should have received a copy of the GNU General Public License along with
 * this program; If not, see <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;


public abstract class BaseComboBox<E> extends JComboBox<E> {
	private static final int COMBO_BOX_WIDTH = 55;
	private static final int COMBO_BOX_SLIM_HEIGHT = 20;
	private static final int COMBO_BOX_NORMAL_HEIGHT = 25;
	private static final long serialVersionUID = -8969953615732925312L;


	public BaseComboBox() {
		setSlimStyle();
		addActionListener(new ComboBoxListener());
	}

	public BaseComboBox(ComboBoxModel<E> aModel) {
		super(aModel);
		setSlimStyle();
		addActionListener(new ComboBoxListener());
	}

	public BaseComboBox(final E[] items) {
		super(items);
		setSlimStyle();
		addActionListener(new ComboBoxListener());
	}

	public BaseComboBox(Vector<E> items) {
		super(items);
		setSlimStyle();
		addActionListener(new ComboBoxListener());
	}

	public void setBackgroundColor(Color background) {
		this.setBackground(background);
	}

	public void setSlimStyle() {
		setPreferredSize(new Dimension(COMBO_BOX_WIDTH, COMBO_BOX_SLIM_HEIGHT));
	}
	
	public void setNormalStyle() {
		setPreferredSize(new Dimension(COMBO_BOX_WIDTH, COMBO_BOX_NORMAL_HEIGHT));
	}

	@Override
	public void doLayout() {
		try {
			super.doLayout();
		} finally {

		}
	}

	public abstract void doAction();

	public class ComboBoxListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			doAction();
		}
	}
}
