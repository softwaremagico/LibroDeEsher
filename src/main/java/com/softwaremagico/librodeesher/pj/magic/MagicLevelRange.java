package com.softwaremagico.librodeesher.pj.magic;

public enum MagicLevelRange {
	FIRST_FIVE_LEVELS("1-5"),
	SECOND_FIVE_LEVELS("6-10"),
	THIRD_FIVE_LEVELS("11-15"),
	FOURTH_FIVE_LEVELS("16-20"),
	MORE_LEVELS("21+");
	
	private String tag;

	MagicLevelRange(String tag) {
		this.tag = tag;
	}

	public static MagicLevelRange getLevelRange(String tag) {
		tag = tag.toLowerCase().trim();
		for (MagicLevelRange type : MagicLevelRange.values()) {
			if (type.tag.equals(tag)) {
				return type;
			}
		}
		return null;
	}
}
