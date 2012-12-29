package com.softwaremagico.librodeesher.pj.categories;

public class LimitedCategory extends Category {
	private static Float[] skillRankValues = { (float) 0, (float) 1, (float) 1, (float) 0.5, (float) 0 };

	public LimitedCategory(String name, String abbreviature, String characteristicsTag) {
		super(name, abbreviature, CategoryType.LIMITED, skillRankValues);
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
		if (ranksNumber <= 20) {
			return ranksNumber;
		}
		if (ranksNumber > 20 && ranksNumber < 30) {
			return 20 + (ranksNumber - 20) / 2;
		}
		if (ranksNumber >= 30) {
			return 25;
		}
		return 0;
	}

	@Override
	public boolean hasRanks() {
		return false;
	}

}
