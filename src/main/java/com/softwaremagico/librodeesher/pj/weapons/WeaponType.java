package com.softwaremagico.librodeesher.pj.weapons;

public enum WeaponType {
	EDGE("file"), BLUNT("contundentes"), THROWING("arrojadizas"), PROJECTILE("proyectiles"), HANDLE("asta"), TWO_HANDS(
			"2manos"), SIEGE("artillería"), FIREARM("fuego 1mano"), TWO_HANDS_FIREARM("fuego 2manos");

	private String tag;

	WeaponType(String tag) {
		this.tag = tag;
	}

	public static WeaponType getWeaponType(String tag) {
		tag = tag.toLowerCase().trim();
		for (WeaponType type : WeaponType.values()) {
			if (type.tag.equals(tag)) {
				return type;
			}
		}
		return null;
	}

}
