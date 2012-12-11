package com.softwaremagico.librodeesher.pj.categories;

import java.util.List;

import com.softwaremagico.librodeesher.pj.skills.Skill;
import com.softwaremagico.librodeesher.pj.skills.SkillFactory;

public class SimpleCategory {
	private String name;
	protected List<Skill> skills;

	public SimpleCategory(String name) {
		this.name = name;
	}

	public Skill addSkill(String skillName) {
		Skill skill = SkillFactory.getSkill(skillName);
		if (!skills.contains(skill)) {
			skills.add(skill);
		}
		return skill;
	}

	public String getName() {
		return name;
	}
}
