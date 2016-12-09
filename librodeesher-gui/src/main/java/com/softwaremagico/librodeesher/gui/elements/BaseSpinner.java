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
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.softwaremagico.librodeesher.gui.style.Fonts;

public class BaseSpinner extends JSpinner {
	private static final long serialVersionUID = -5360733915257515036L;
	private List<SpinnerValueChangedListener> valueChangedListeners;

	public BaseSpinner() {
		setDefaultFont();
		valueChangedListeners = new ArrayList<>();
		enableCustomEvents();
	}

	public BaseSpinner(SpinnerModel sm) {
		super(sm);
		setDefaultFont();
		valueChangedListeners = new ArrayList<>();
		enableCustomEvents();
	}

	protected void setDefaultFont() {
		setFont(Fonts.getInstance().getBoldFont());
	}

	public void setColumns(int value) {
		JComponent editor = getEditor();
		JFormattedTextField tf = ((JSpinner.DefaultEditor) editor)
				.getTextField();
		tf.setColumns(value);
	}

	public void addSpinnerValueChangedListener(
			SpinnerValueChangedListener listener) {
		valueChangedListeners.add(listener);
	}

	public void removeSpinnerValueChangedListener(
			SpinnerValueChangedListener listener) {
		valueChangedListeners.remove(listener);
	}

	private void enableCustomEvents() {
		addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				for (SpinnerValueChangedListener listener : valueChangedListeners) {
					listener.valueChanged((Integer) getValue());
				}
			}
		});
	}
	
	public void setColor(Color color){
		Component c = getEditor().getComponent(0);
		c.setForeground(color);
	}

}
