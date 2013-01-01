package com.softwaremagico.librodeesher.gui;
/*
 * #%L
 * Libro de Esher
 * %%
 * Copyright (C) 2007 - 2013 Softwaremagico
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

import java.awt.Font;

import javax.swing.Icon;
import javax.swing.JMenuItem;

import com.softwaremagico.files.RolemasterFolderStructure;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.SexType;

public class CharacterMenuItem extends JMenuItem {
	private static final long serialVersionUID = 6981333043794608235L;
	private CharacterPlayer characterInMenu;

	CharacterMenuItem(CharacterPlayer characterInMenu, CharacterPlayer currentSelectedPlayer) {
		super(characterInMenu.getName());
		this.characterInMenu = characterInMenu;

		if (characterInMenu.equals(currentSelectedPlayer)) {
			if (characterInMenu.getSex() == SexType.MALE) {
				setIcon((Icon) RolemasterFolderStructure.getIcon("character_male.png"));
			} else {
				setIcon((Icon) RolemasterFolderStructure.getIcon("character_female.png"));
			}
			Font font = getFont();
			setFont(new Font(font.getFontName(), Font.BOLD, font.getSize()));
		} else {
			if (characterInMenu.getSex() == SexType.MALE) {
				setIcon((Icon) RolemasterFolderStructure.getIcon("character_male_shadow.png"));
			} else {
				setIcon((Icon) RolemasterFolderStructure.getIcon("character_female_shadow.png"));
			}
			Font font = getFont();
			setFont(new Font(font.getFontName(), Font.PLAIN + Font.ITALIC, font.getSize()));
		}
	}

	public CharacterPlayer getCharacter() {
		return characterInMenu;
	}
}
