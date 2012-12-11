package com.softwaremagico.librodeesher.pj.weapons;

public class Weapon {
	private String name;
	private WeaponType type;

	Weapon(String name, WeaponType type) {
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}
}
