package com.softwaremagico.librodeesher.gui.style;
/*
 * #%L
 * Libro de Esher
 * %%
 * Copyright (C) 2007 - 2012 Softwaremagico
 * %%
 * This software is designed by Jorge Hortelano Otero. Jorge Hortelano Otero
 * <softwaremagico@gmail.com> C/Quart 89, 3. Valencia CP:46008 (Spain).
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

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SwingConstants;


public abstract class BasicLine extends BasePanel {
	private static final long serialVersionUID = 7901507705885692683L;
	protected Color background = Color.WHITE;
	protected Color foregorund = Color.BLACK;
	protected Color fontColor = Color.DARK_GRAY;
	protected Integer defaultHeight = 25;
	protected Integer defaultWidth = 500;
	protected Integer labelDefaultWidth = 40;
	protected Integer nameTextDefaultWidth = 140;
	protected String font = "Dialog";
	protected int fontSize = 12;

	/**
	 * Create the panel.
	 */
	public BasicLine() {
		setDefaultDesign();
	}

	protected void setDefaultDesign() {
		//setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		setBackground(background);
		setForeground(foregorund);
	}
	
	public JPanel createLabelInsidePanel(JLabel label, boolean centered, Color bgColor, Color fgColor) {
		if (centered) {
			label.setHorizontalAlignment(SwingConstants.CENTER);
		}
		JPanel container = new JPanel(new BorderLayout());
		label.setForeground(fgColor);
		container.add(label);
		container.setBackground(bgColor);
		return container;
	}
	
	public JPanel createSpinnerInsidePanel(JSpinner spinner, boolean centered, Color bgColor) {
		JPanel container = new JPanel(new BorderLayout());
		container.add(spinner);
		container.setBackground(bgColor);
		return container;
	}

}
