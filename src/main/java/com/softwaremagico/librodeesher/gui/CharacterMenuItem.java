package com.softwaremagico.librodeesher.gui;

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
