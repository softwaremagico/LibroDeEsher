package com.softwaremagico.librodeesher.pj.categories;
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

import java.util.ArrayList;
import java.util.List;

import com.softwaremagico.librodeesher.pj.characteristic.Characteristic;
import com.softwaremagico.librodeesher.pj.skills.Skill;
import com.softwaremagico.librodeesher.pj.skills.SkillFactory;

public abstract class Category extends SimpleCategory{
	protected String abbreviature;
	protected CategoryType type;
	public List<Characteristic> characteristics;

	public Category(String name, String abbreviature, CategoryType type) {
		super(name);
		this.abbreviature = abbreviature;
		this.type = type;
		skills = new ArrayList<>();
		characteristics = new ArrayList<>();
	}

	public abstract void addSkills(String skills);

	public List<Skill> getSkills() {
		return skills;
	}

	public String getAbbreviature() {
		return abbreviature;
	}
}
