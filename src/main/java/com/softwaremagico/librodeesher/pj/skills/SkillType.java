package com.softwaremagico.librodeesher.pj.skills;

public enum SkillType {
	RESTRICTED("(R)"), SPECIAL("*"), STANDAR("");

	private String tag;

	SkillType(String tag) {
		this.tag = tag;
	}

	public static SkillType getSkillType(String skillName) {
		if (skillName.contains("(R)")) {
			return RESTRICTED;
		} else if (skillName.contains("*")) {
			return SPECIAL;
		}
		return STANDAR;
	}
}
