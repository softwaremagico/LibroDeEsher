package com.softwaremagico.librodeesher.pj.categories;

public class SpecialCategory extends Category {
	private static Float[] skillRankValues = { (float) 0, (float) 6, (float) 5, (float) 4, (float) 3 };

	public SpecialCategory(String name, String abbreviature, String characteristicsTag) {
		super(name, abbreviature, CategoryType.SPECIAL, skillRankValues);
	}

	@Override
	public void addSkills(String skills) {
		String[] skillsArray = skills.split(",");
		for (String skill : skillsArray) {
			addSkill(skill);
		}
	}

	@Override
	public Integer getSkillRankValues(Integer ranksNumber) {
		if (ranksNumber == 0) {
			return 0;
		}
		if (ranksNumber <= 10) {
			return ranksNumber * 6;
		}
		if (ranksNumber > 10 && ranksNumber <= 20) {
			return (60 + (ranksNumber - 10) * 5);
		}
		if (ranksNumber > 20 && ranksNumber <= 30) {
			return 110 + (ranksNumber - 20) * 4;
		}
		if (ranksNumber > 30) {
			return 150 + (ranksNumber - 30) * 3;
		}
		return 0;
	}

	@Override
	public boolean hasRanks() {
		return false;
	}

}
