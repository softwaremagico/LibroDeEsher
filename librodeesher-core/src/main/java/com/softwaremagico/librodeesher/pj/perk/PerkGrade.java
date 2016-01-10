package com.softwaremagico.librodeesher.pj.perk;

public enum PerkGrade {
	MINIMUM("mínimo", 0), MINOR("menor", 1), MAJOR("mayor", 2), MAXIMUM("máximo", 3);

	private String tag;

	private int level;

	PerkGrade(String tag, int level) {
		this.tag = tag;
		this.level = level;
	}

	public static PerkGrade getPerkCategory(String tag) {
		for (PerkGrade perkGrade : PerkGrade.values()) {
			if (perkGrade.tag.equals(tag.toLowerCase())) {
				return perkGrade;
			}
		}
		return MAXIMUM;
	}

	public String toString() {
		return tag.substring(0, 1).toUpperCase() + tag.substring(1);
	}

	/**
	 * Returns the grade pondered as a number
	 * 
	 * @return
	 */
	public int asNumber() {
		return level;
	}
}
