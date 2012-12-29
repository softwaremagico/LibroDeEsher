package com.softwaremagico.librodeesher.pj.categories;

public class PpdCategory extends Category {
	private static Float[] skillRankValues = { (float) 0, (float) 0, (float) 0, (float) 0, (float) 0 };
	
	public PpdCategory(String name, String abbreviature, String characteristicsTag) {
		super(name, abbreviature, CategoryType.PPD, skillRankValues);
	}

	@Override
	public void addSkills(String skills) {
		String[] skillsArray = skills.split(",");
		for (String skill : skillsArray) {
			addSkill(skill);
		}
	}

	@Override
	public boolean hasRanks() {
		return true;
	}
}
