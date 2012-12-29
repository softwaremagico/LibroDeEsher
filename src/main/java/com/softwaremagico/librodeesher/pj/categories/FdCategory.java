package com.softwaremagico.librodeesher.pj.categories;

public class FdCategory extends Category  {
	private static Float[] skillRankValues = { (float) 0, (float) 0, (float) 0, (float) 0, (float) 0 };
	
	public FdCategory(String name, String abbreviature, String characteristicsTag) {
		super(name, abbreviature, CategoryType.FD, skillRankValues);
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
