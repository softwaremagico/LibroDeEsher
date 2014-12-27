package com.softwaremagico.librodeesher.pj.characteristic;

import com.google.gson.annotations.SerializedName;

public enum CharacteristicsAbbreviature {

	// JSON enums that override toString() method must specify the serializedName.
	@SerializedName("NULL")
	NULL("", ""),

	@SerializedName("Ag")
	AGILITY("Ag", "Agilidad"),

	@SerializedName("Co")
	CONSTITUTION("Co", "Constitución"),

	@SerializedName("Me")
	MEMORY("Me", "Memoria"),

	@SerializedName("Ra")
	REASON("Ra", "Razón"),

	@SerializedName("Ad")
	SELFDISCIPLINE("Ad", "Autodisciplina"),

	@SerializedName("Em")
	EMPATHY("Em", "Empatía"),

	@SerializedName("In")
	INTUITION("In", "Intuición"),

	@SerializedName("Pr")
	PRESENCE("Pr", "Presencia"),

	@SerializedName("Rp")
	SPEED("Rp", "Rapidez"),

	@SerializedName("Fu")
	STRENGTH("Fu", "Fuerza"),

	@SerializedName("Ap")
	APPEARENCE("Ap", "Apariencia"),

	@SerializedName("Mr")
	MAGIC_REALM("*", "*");

	private String tag;
	private String name;

	CharacteristicsAbbreviature(String abbreviature, String name) {
		this.tag = abbreviature;
		this.name = name;
	}

	public String getTag() {
		return tag;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return getTag();
	}

	public static CharacteristicsAbbreviature getCharacteristicsAbbreviature(String abbreviature) {
		for (CharacteristicsAbbreviature characteristic : CharacteristicsAbbreviature.values()) {
			if (characteristic.getTag().trim().equals(abbreviature)) {
				return characteristic;
			}
		}
		return CharacteristicsAbbreviature.NULL;
	}
}
