package com.softwaremagico.librodeesher.pj.perk;

public enum PerkType {
	
	PHYSIC("Físico"),
	MENTAL("Mental"),
	MAGICAL("Capacidad Mágica"),
	SPECIAL("Capacidad Especial"),
	TRAINING("Adiestramiento Especial"),
	//No official perks
	OTHER("Otros");
	
	private String tag;
	
	private PerkType(String tag){
		this.tag = tag;
	}
	
	public static PerkType getPerkType(String tag) {
		for (PerkType perkType : PerkType.values()) {
			if (perkType.tag.toLowerCase().equals(tag.toLowerCase())) {
				return perkType;
			}
		}
		return OTHER;
	}

}
