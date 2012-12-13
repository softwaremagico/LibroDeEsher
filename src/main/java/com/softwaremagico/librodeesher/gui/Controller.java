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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import com.softwaremagico.librodeesher.pj.CharacterPlayer;

public class Controller {
	MainWindow mainGui;
	AboutWindow aboutWindow;
	CharacteristicsWindow characteristicWindow;
	List<CharacterPlayer> characters;
	CharacterPlayer selectedCharacter;

	public Controller() {
		characters = new ArrayList<>();
		selectedCharacter = new CharacterPlayer();
		characters.add(selectedCharacter);
		mainGui = new MainWindow();
		mainGui.setCharacter(selectedCharacter);
		mainGui.setVisible(true);
		addMainMenuActionListeners();

	}

	private void addMainMenuActionListeners() {
		mainGui.getMainMenu().addAboutMenuItemListener(new AboutBoxListener());
		mainGui.getMainMenu().addCharacteristicsWindowMenuItemListener(new CharacteristicWindowsListener());
	}
	
	class CharacteristicWindowsListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				characteristicWindow.dispose();
			} catch (NullPointerException npe) {
			}
			characteristicWindow = new CharacteristicsWindow();
			characteristicWindow.setCharacter(selectedCharacter);
			characteristicWindow.setVisible(true);
		}
	}

	class AboutBoxListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				aboutWindow.dispose();
			} catch (NullPointerException npe) {
			}
			aboutWindow = new AboutWindow();
			aboutWindow.setVisible(true);
		}
	}
}
