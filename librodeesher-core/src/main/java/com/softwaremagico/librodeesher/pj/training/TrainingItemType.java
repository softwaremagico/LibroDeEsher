package com.softwaremagico.librodeesher.pj.training;

public enum TrainingItemType {
	WEAPON,

	WEAPON_CLOSE_COMBAT,

	WEAPON_RANGED,
	
	ARMOUR,

	SKILL,

	CATEGORY, 
	
	ANY,
	
	UNKNOWN;

	private String details;

	private TrainingItemType() {

	}

	public String getDetails() {
		return details;
	}

}
