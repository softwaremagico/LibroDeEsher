package com.softwaremagico.librodeesher.pj.skills;

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
