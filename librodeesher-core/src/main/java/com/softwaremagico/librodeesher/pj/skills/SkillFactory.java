package com.softwaremagico.librodeesher.pj.skills;

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
import java.util.HashMap;
import java.util.List;

import com.softwaremagico.librodeesher.basics.Spanish;
import com.softwaremagico.librodeesher.pj.categories.Category;
import com.softwaremagico.librodeesher.pj.categories.CategoryFactory;

public class SkillFactory {
	private static HashMap<String, Skill> availableSkills = new HashMap<>();
	private static List<String> availableSkillsByName = new ArrayList<>();

	public static Skill getSkill(String skillNameAndType) {
		Skill skill = availableSkills.get(skillNameAndType);
		if (skill == null) {
			skill = createSkill(skillNameAndType);
			addSkill(skill);
		}
		return skill;
	}

	public static Skill getSkill(String skillNameAndType, List<String> specialities) {
		Skill skill = availableSkills.get(skillNameAndType);
		if (skill == null) {
			skill = createSkill(skillNameAndType);
			skill.addSpecialities(specialities);
			addSkill(skill);
		}
		return skill;
	}

	public static void setAvailableSkill(String[] skills) {
		for (String skillParameter : skills) {
			String skillName = skillParameter.trim();
			Skill skill = createSkill(skillName);
			addSkill(skill);
		}
	}

	public static List<String> getAvailableSkills() {
		return availableSkillsByName;
	}

	public static void addSkill(Skill skill) {
		availableSkills.put(skill.getName(), skill);
		availableSkillsByName.add(skill.getName());
		Collections.sort(availableSkillsByName);
	}

	public static Skill getAvailableSkill(String skillName) {
		return availableSkills.get(skillName);
	}

	public static boolean existSkill(String skillName) {
		return (getAvailableSkill(skillName.trim()) != null);
	}

	public static Skill getSkill(String skillName, SkillType skillType) {
		Skill skill = availableSkills.get(skillName);
		if (skill == null) {
			skill = createSkill(skillName, skillType);
			addSkill(skill);
		}
		return skill;
	}

	public static Skill getSkill(String skillPrefix, String containText) {
		for (Skill skill : availableSkills.values()) {
			if (skill.getName().toLowerCase().startsWith(skillPrefix)
					&& skill.getName().toLowerCase().contains((containText))) {
				return skill;
			}
		}
		return null;
	}

	private static Skill createSkill(String skillNameAndType) {
		SkillType skillType = SkillType.getSkillType(skillNameAndType);
		Skill skill = createSkill(removeTypeFromName(skillNameAndType), skillType);
		// "*" is used to avoid skills to be used in random characters. 
		skill.setUsedInRandom(!skillNameAndType.contains("*"));
		return skill;
	}

	private static Skill createSkill(String skillName, SkillType skillType) {
		SkillGroup group = SkillGroup.STANDARD;
		if (skillName.toLowerCase().startsWith(Spanish.CHI_SUFIX)) {
			group = SkillGroup.CHI;
		}
		if (skillName.startsWith(Spanish.FIREARMS_SKILL1) || skillName.startsWith(Spanish.FIREARMS_SKILL2)
				|| skillName.startsWith(Spanish.FIREARMS_SKILL3)) {
			group = SkillGroup.FIREARM;
		}
		return new Skill(skillName, skillType, group);
	}

	private static String removeTypeFromName(String skillName) {
		// String pattern = Pattern.quote("*");
		return skillName.replace("*", "").replace("(R)", "").replace("(r)", "").replace("(C)", "")
				.replace("(c)", "").replace("(P)", "").replace("(p)", "");
	}

	public static List<Skill> getSkills() {
		List<Skill> skills = new ArrayList<>(availableSkills.values());
		Collections.sort(skills, new SkillComparator());
		return skills;
	}

	public static List<Skill> getSkills(List<String> skillsNames) {
		List<Skill> allSkills = getSkills();
		List<Skill> skills = new ArrayList<>();
		for (Skill skill : allSkills) {
			if (skillsNames.contains(skill.getName())) {
				skills.add(skill);
			}
		}
		return skills;
	}

	public static List<Skill> getWeaponSkills() {
		List<Skill> skills = new ArrayList<>();
		List<Category> weaponCategories = CategoryFactory.getWeaponsCategories();
		for (Category weapon : weaponCategories) {
			skills.addAll(getSkills(weapon));
		}
		Collections.sort(skills, new SkillComparator());
		return skills;
	}

	public static List<Skill> getSkills(Category category) {
		List<Skill> skills = new ArrayList<>();
		for (Skill skill : availableSkills.values()) {
			if (skill.getCategory().equals(category)) {
				skills.add(skill);
			}
		}
		Collections.sort(skills, new SkillComparator());
		return skills;
	}
}
