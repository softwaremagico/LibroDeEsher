package com.softwaremagico.librodeesher.gui;

/*
 * #%L
 * Libro de Esher (GUI)
 * %%
 * Copyright (C) 2007 - 2014 Softwaremagico
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

import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import com.softwaremagico.librodeesher.gui.elements.BaseLabel;
import com.softwaremagico.librodeesher.gui.style.BaseWindow;

public class RandomSplashScreen extends BaseWindow {
	private static final long serialVersionUID = -2043246958721137042L;
	private BaseLabel textLabel;

	private void setElements() {
		FlowLayout layout = new FlowLayout(FlowLayout.LEFT, 5, 5);
		setLayout(layout);
		ImageIcon image = new ImageIcon(RandomSplashScreen.class.getResource("/images/random_character.png"));
		image = new ImageIcon(image.getImage().getScaledInstance(58, 50, Image.SCALE_SMOOTH));
		JLabel imagelabel = new JLabel(image);
		add(imagelabel);

		textLabel = new BaseLabel("Creando personaje aleatorio.");
		add(textLabel);
	}

	public RandomSplashScreen() {
		setElements();
		defineWindow(350, 60);
		setAlwaysOnTop(true);
	}
}
