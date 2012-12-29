package com.softwaremagico.librodeesher.pj.categories;

public class CombinedCategory extends Category {
	private static Float[] skillRankValues = { (float) -30, (float) 5, (float) 3, (float) 1.5, (float) 0.5 };
	
	public CombinedCategory(String name, String abbreviature, String characteristicsTag) {
		super(name, abbreviature, CategoryType.COMBINED, skillRankValues);
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
			return -30;
		}
		if (ranksNumber <= 10) {
			return ranksNumber * 5;
		}
		if (ranksNumber > 10 && ranksNumber <= 20) {
			return (50 + (ranksNumber - 10) * 3);
		}
		if (ranksNumber > 20 && ranksNumber <= 25) {
			return 80 + (ranksNumber - 20) * 2;
		}
		if (ranksNumber > 25 && ranksNumber <= 30) {
			return 80 + (int) ((ranksNumber - 20) * 1.5);
		}
		if (ranksNumber > 30) {
			return 95 + (ranksNumber - 30) / 2;
		}
		return -30;
	}

	@Override
	public boolean hasRanks() {
		return false;
	}

}
