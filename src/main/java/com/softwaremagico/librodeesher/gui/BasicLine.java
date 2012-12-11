package com.softwaremagico.librodeesher.gui;
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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

public class BasicLine extends JPanel {
	private static final long serialVersionUID = 7901507705885692683L;
	protected Color Background = Color.white;
	protected Integer defaultHeight = 25;
	protected Integer defaultWidth = 500;
	protected Integer textDefaultWidth = 40;
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
		setMinimumSize(new Dimension(defaultWidth, defaultHeight));
		setPreferredSize(new Dimension(defaultWidth, defaultHeight));
		setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		setBackground(Background);
		setForeground(Color.BLACK);
		setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
	}

}
