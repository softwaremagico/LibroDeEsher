package com.softwaremagico.librodeesher.gui.culture;

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

import java.util.ArrayList;
import java.util.List;

import com.softwaremagico.librodeesher.basics.Spanish;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.categories.CategoryFactory;
import com.softwaremagico.librodeesher.pj.magic.MagicFactory;
import com.softwaremagico.librodeesher.pj.skills.Skill;
import com.softwaremagico.librodeesher.pj.weapons.Weapon;

public class HobbyPanel extends CulturePanel {
	private static final long serialVersionUID = -1080201556731377298L;
	private Integer lineIndex;

	public HobbyPanel(CharacterPlayer character, CultureTitleLine title) {
		this.character = character;
		this.title = title;
		setElements(character);
	}

	@Override
	protected void createElements() {
		lineIndex = 0;
		for (String skill : character.getCulture().getHobbySkills()) {
			if (skill.toLowerCase().equals(Spanish.CULTURE_WEAPON)) {
				for (Weapon weapon : character.getCulture().getCultureWeapons()) {
					addHobbyLine(weapon.getName());
				}
			} else if (skill.toLowerCase().equals(Spanish.CULTURE_ARMOUR)) {
				for (String armour : character.getCulture().getCultureArmours()) {
					addHobbyLine(armour);
				}
			} else if (skill.toLowerCase().equals(Spanish.CULTURE_SPELLS)) {
				List<String> spells = new ArrayList<>();
				// Add open lists.
				for (Skill spell : character.getCategory(
						CategoryFactory.getAvailableCategory(Spanish.OPEN_LISTS)).getSkills()) {
					// addHobbyLine(spell.getName());
					spells.add(spell.getName());
				}
				// Add race lists. Note than spell casters has race lists as
				// basic and not as open. Therefore are not included in the
				// previous step.
				for (String spell : MagicFactory.getRaceLists(character.getRace().getName())) {
					// avoid to add a race list two times.
					if (!spells.contains(spell)) {
						spells.add(spell);
					}
				}
				// Create line.
				for (String spell : spells) {
					addHobbyLine(spell);
				}

			} else {
				addHobbyLine(skill);
			}
		}
	}

	private void addHobbyLine(String skill) {
		HobbyLine hobbyLine = new HobbyLine(character, skill, this, getLineBackgroundColor(lineIndex));
		add(hobbyLine);
		hobbyLines.add(hobbyLine);
		lineIndex++;
	}

	protected void setRankTitle(String rankLabelText) {
		title.setRankTitle(rankLabelText);
	}
}
