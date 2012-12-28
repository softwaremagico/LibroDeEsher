package com.softwaremagico.librodeesher.pj.categories;

public class CombinedCategory extends Category {
	public CombinedCategory(String name, String abbreviature, String characteristicsTag) {
		super(name, abbreviature, CategoryType.STANDARD);
	}

	@Override
	public void addSkills(String skills) {
		String[] skillsArray = skills.split(",");
		for (String skill : skillsArray) {
			addSkill(skill);
		}
	}

	@Override
	public Integer getRankValue() {
		// TODO Auto-generated method stub
		return 0;
	}
}
