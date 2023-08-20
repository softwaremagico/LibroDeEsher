package com.softwaremagico.librodeesher.gui.perk;
/*
 * #%L
 * Libro de Esher (GUI)
 * %%
 * Copyright (C) 2007 - 2017 Softwaremagico
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

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.Icon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.softwaremagico.librodeesher.gui.MainMenu;

public class PerksWindowsMenu {
	private JMenuItem randomPerkMenu;

	public JMenuBar createMenu() {
		JMenuBar mainMenu = new JMenuBar();

		mainMenu.add(createOptionsMenu());
		return mainMenu;
	}

	private JMenu createOptionsMenu() {
		JMenu configMenu = new JMenu("Opciones");
		configMenu.setMnemonic(KeyEvent.VK_O);
		configMenu.setIcon((Icon) MainMenu.getIcon("config.png"));
		configMenu.getAccessibleContext().setAccessibleDescription("Opciones.");

		randomPerkMenu = new JMenuItem("Añadir Talentos Aleatorios", KeyEvent.VK_O);
		randomPerkMenu.setIcon((Icon) MainMenu.getIcon("random.png"));
		randomPerkMenu.getAccessibleContext().setAccessibleDescription("Añade un talento escogido aleatoriamente.");
		configMenu.add(randomPerkMenu);

		return configMenu;
	}

	public void addRandomPerkMenuListener(ActionListener al) {
		randomPerkMenu.addActionListener(al);
	}
}
