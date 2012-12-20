package com.softwaremagico.librodeesher.pj.categories;

public class PpdCategory extends Category {
	public PpdCategory(String name, String abbreviature, String characteristicsTag) {
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