package com.softwaremagico.librodeesher.gui.culture;

/*
 * #%L
 * Libro de Esher
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

import java.util.Set;

import com.softwaremagico.librodeesher.basics.Spanish;
import com.softwaremagico.librodeesher.gui.ShowMessage;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.magic.MagicDefinitionException;
import com.softwaremagico.librodeesher.pj.magic.MagicFactory;
import com.softwaremagico.librodeesher.pj.profession.InvalidProfessionException;

public class SpellPanel extends CulturePanel {
	private static final long serialVersionUID = -9203104559414795802L;

	public SpellPanel(CharacterPlayer character, CultureTitleLine title) {
		this.character = character;
		this.title = title;
		setElements(character);
	}

	@Override
	protected void createElements() {
		int i = 0;

		if (character.getCulture().getSpellRanks() != 0) {
			Set<String> spellLists;
			try {
				spellLists = MagicFactory.getListOfProfession(character.getRealmOfMagic()
						.getRealmsOfMagic(), Spanish.OPEN_LIST_TAG);
				for (String spell : spellLists) {
					SpellLine hobbyLine = new SpellLine(character, spell, this, getLineBackgroundColor(i));
					add(hobbyLine);
					hobbyLines.add(hobbyLine);
					i++;
				}
			} catch (MagicDefinitionException | InvalidProfessionException e) {
				ShowMessage.showErrorMessage(e.getMessage(), "Error");
			}

			
		}
	}

	@Override
	protected void setRankTitle(String rankLabelText) {
		title.setRankTitle(rankLabelText);
	}

}
