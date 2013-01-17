package com.softwaremagico.librodeesher.pj.perk;

public enum PerkCategory {
	MINIMUM("mínimo"), MINOR("menor"), MAJOR("mayor"), MAXIMUM("máximo");

	private String tag;

	PerkCategory(String tag) {
		this.tag = tag;
	}

	public static PerkCategory getPerkCategory(String tag) {
		for (PerkCategory perkCategory : PerkCategory.values()) {
			if (perkCategory.tag.equals(tag.toLowerCase())) {
				return perkCategory;
			}
		}
		return MAXIMUM;
	}

	public String toString() {
		return tag.substring(0, 1).toUpperCase() + tag.substring(1);
	}
}