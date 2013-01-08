package com.softwaremagico.librodeesher.pj;
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

import com.softwaremagico.librodeesher.pj.categories.Category;
import com.softwaremagico.librodeesher.pj.skills.Skill;

public class Historial {
	private static final Integer SKILL_BONUS = 10;
	private static final Integer CATEGORY_BONUS = 5;
	private List<String> categories;
	private List<String> skills;

	public Historial() {
		categories = new ArrayList<>();
		skills = new ArrayList<>();
	}

	public void setPoint(Skill skill, boolean value) {
		if (value) {
			if (!skills.contains(skill.getName())) {
				skills.add(skill.getName());
			}
		} else {
			skills.remove(skill.getName());
		}
	}

	public void setPoint(Category category, boolean value) {
		if (value) {
			if (!categories.contains(category.getName())) {
				categories.add(category.getName());
			}
		} else {
			categories.remove(category.getName());
		}
	}

	public Integer getBonus(Skill skill) {
		if (skills.contains(skill.getName())) {
			return SKILL_BONUS;
		}
		return 0;
	}

	public Integer getBonus(Category category) {
		if (categories.contains(category.getName())) {
			return CATEGORY_BONUS;
		}
		return 0;
	}

	public Integer getSpentHistoryPoints() {
		return skills.size() + categories.size();
	}
}
