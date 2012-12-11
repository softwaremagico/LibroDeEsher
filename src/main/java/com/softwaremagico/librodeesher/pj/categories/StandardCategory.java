package com.softwaremagico.librodeesher.pj.categories;

import java.util.ArrayList;
import java.util.List;

import com.softwaremagico.librodeesher.Habilidad;
import com.softwaremagico.librodeesher.pj.skills.Skill;
import com.softwaremagico.librodeesher.pj.skills.SkillFactory;

public class StandardCategory extends Category {

	public StandardCategory(String name, String abbreviature,
			String characteristicsTag) {
		super(name, abbreviature, CategoryType.STANDARD);
	}

	@Override
	public void addSkills(String skills) {
		String[] skillsArray = skills.split(",");
		for (String skill : skillsArray) {
			addSkill(skill);
		}
	}
}

