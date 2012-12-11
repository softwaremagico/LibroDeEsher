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

import java.util.Hashtable;

public class SkillFactory {
	private static Hashtable<String, Skill> availableSkills = new Hashtable<>();

	public static Skill getSkill(String skillNameAndType) {
		Skill skill = availableSkills.get(skillNameAndType);
		if (skill == null) {
			skill = createSkill(skillNameAndType);
			availableSkills.put(skillNameAndType, skill);
		}
		return skill;
	}
	
	public static Skill getAvailableSkill(String skillName){
		return availableSkills.get(skillName);
	}
	
	public static boolean existSkill(String skillName) {
		return (getAvailableSkill(skillName) != null);
	}
	
	public static Skill getSkill(String skillName, SkillType skillType) {
		Skill skill = availableSkills.get(skillName);
		if (skill == null) {
			skill = createSkill(skillName, skillType);
			availableSkills.put(skillName, skill);
		}
		return skill;
	}

	private static Skill createSkill(String skillNameAndType) {
		SkillType skillType = SkillType.getSkillType(skillNameAndType);
		return createSkill(removeTypeFromName(skillNameAndType), skillType);
	}

	private static Skill createSkill(String skillName, SkillType skillType) {
		switch (skillType) {
		case STANDAR:
			return new StandardSkill(skillName);
		default:
			return null;
		}
	}

	private static String removeTypeFromName(String skillName) {
		return skillName.replace("(R)", "").replace("*", "").trim();
	}
}
