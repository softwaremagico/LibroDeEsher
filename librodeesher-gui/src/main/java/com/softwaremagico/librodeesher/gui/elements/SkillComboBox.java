package com.softwaremagico.librodeesher.gui.elements;

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

import java.util.ArrayList;
import java.util.List;

import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.categories.Category;
import com.softwaremagico.librodeesher.pj.skills.Skill;

public class SkillComboBox extends BaseComboBox<Skill> {
	private static final long serialVersionUID = 7160132975462619054L;
	private List<SkillChangedListener> skillListeners;
	private CharacterPlayer character;

	public SkillComboBox(CharacterPlayer character) {
		this.character = character;
		skillListeners = new ArrayList<>();
	}

	public void setSkills(Category category) {
		if (category != null) {
			this.removeAllItems();
			Category realCategory = (character != null ? character.getCategory(category) : category);
			for (Skill skill : realCategory.getSkills()) {
				addItem(skill);
			}
		}
	}

	public void setSkills(List<Skill> skills) {
		if (skills != null) {
			this.removeAllItems();
			for (Skill skill : skills) {
				addItem(skill);
			}
		}
	}

	@Override
	public void doAction() {
		for (SkillChangedListener listener : skillListeners) {
			listener.skillChanged((Skill) this.getSelectedItem());
		}
	}

	public void addSkillChangedListener(SkillChangedListener listener) {
		skillListeners.add(listener);
	}

	public void removeSkillChangedListener(SkillChangedListener listener) {
		skillListeners.remove(listener);
	}
}
