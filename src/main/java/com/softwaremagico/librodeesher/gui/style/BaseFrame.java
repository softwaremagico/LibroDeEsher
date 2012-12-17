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

import java.awt.Color;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.border.Border;

public abstract class BaseFrame extends JFrame {
	private static final String FRAME_TITLE = "Libro de Esher";
	private static final long serialVersionUID = 3812264811213028556L;
	protected static final int margin = 5;
	protected Integer textDefaultWidth = 80;
	protected Integer textDefaultHeight = 25;
	protected Integer inputDefaultWidth = 160;
	protected Integer inputColumns = 12;
	protected Integer xPadding = 5;
	protected Integer yPadding = 10;
	private Border border = BorderFactory.createLineBorder(Color.LIGHT_GRAY);

	/**
	 * Create the frame.
	 */
	public BaseFrame() {
		setTitle(FRAME_TITLE);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setIconImage(new ImageIcon(this.getClass().getResource("/librodeesher.png")).getImage());
	}

	protected void defineWindow(Integer width, Integer height) {
		setSize(width, height);
		setLocation((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2
				- (int) (this.getWidth() / 2), (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()
				/ 2 - (int) (this.getHeight() / 2));
	}

	public Border getBorder() {
		return border;
	}
	
	public abstract void update();

}
