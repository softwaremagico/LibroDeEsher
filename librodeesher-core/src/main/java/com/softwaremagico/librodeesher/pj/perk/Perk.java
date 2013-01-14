package com.softwaremagico.librodeesher.pj.perk;

public class Perk {
	private String name;
	private Integer cost;
	private String description;
	private String avalibleTo;

	public Perk(String name, Integer cost, String classification, String description, String avalibleTo) {
		this.name = name;
		this.cost = cost;
		this.description = description;
		this.avalibleTo = avalibleTo;
	}

	public void addBonuses(String bonuses) {

	}

	public String getName() {
		return name;
	}
}
