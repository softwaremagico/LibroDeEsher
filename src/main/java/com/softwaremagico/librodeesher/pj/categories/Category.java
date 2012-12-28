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
import java.util.Collections;
import java.util.List;

import com.softwaremagico.librodeesher.pj.characteristic.Characteristic;
import com.softwaremagico.librodeesher.pj.skills.Skill;
import com.softwaremagico.librodeesher.pj.skills.SkillFactory;

public abstract class Category {
	private String name;
	protected String abbreviature;
	protected CategoryType type;
	public List<Characteristic> characteristics;
	protected List<Skill> skills;

	public Category(String name, String abbreviature, CategoryType type) {
		this.name = name;
		this.abbreviature = abbreviature;
		this.type = type;
		skills = new ArrayList<>();
		characteristics = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	public abstract void addSkills(String skills);

	public String getAbbreviature() {
		return abbreviature;
	}

	public List<Skill> getSkills() {
		return skills;
	}

	public CategoryType getType() {
		return type;
	}

	public void setSkills(List<Skill> skills) {
		this.skills = skills;
		for (Skill skill : skills) {
			skill.setCategory(this);
		}
		Collections.sort(skills, new SkillComparator());
	}

	public void addSkills(List<Skill> skills) {
		this.skills.addAll(skills);
		for (Skill skill : skills) {
			skill.setCategory(this);
		}
		Collections.sort(skills, new SkillComparator());
	}

	public Skill addSkill(String skillName) {
		Skill skill = SkillFactory.getSkill(skillName);
		skill.setCategory(this);
		if (!skills.contains(skill)) {
			skills.add(skill);
			Collections.sort(skills, new SkillComparator());
		}
		return skill;
	}

	public abstract Integer getRankValue();
}
