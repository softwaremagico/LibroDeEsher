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
