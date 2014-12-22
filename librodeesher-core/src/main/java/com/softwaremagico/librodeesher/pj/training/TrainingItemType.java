package com.softwaremagico.librodeesher.pj.training;

public enum TrainingItemType {
	WEAPON,

	WEAPON_CLOSE_COMBAT,

	WEAPON_RANGED,

	SKILL,

	CATEGORY;

	private String details;

	private TrainingItemType() {

	}

	public String getDetails() {
		return details;
	}

}
