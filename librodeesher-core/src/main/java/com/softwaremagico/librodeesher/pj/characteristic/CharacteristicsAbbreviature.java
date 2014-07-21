package com.softwaremagico.librodeesher.pj.characteristic;

public enum CharacteristicsAbbreviature {
	
	NULL("", ""),

	AGILITY("Ag", "Agilidad"),

	CONSTITUTION("Co", "Constitución"),

	MEMORY("Me", "Memoria"),

	REASON("Ra", "Razón"),

	SELFDISCIPLINE("Ad", "Autodisciplina"),

	EMPATHY("Em", "Empatía"),

	INTUITION("In", "Intuición"),

	PRESENCE("Pr", "Presencia"),

	SPEED("Rp", "Rapidez"),

	STRENGTH("Fu", "Fuerza"),
	
	APPEARENCE("Ap", "Apariencia"),
	
	MAGIC_REALM("*", "*");

	private String abbreviature;
	private String name;

	CharacteristicsAbbreviature(String abbreviature, String name) {
		this.abbreviature = abbreviature;
		this.name = name;
	}

	public String getAbbreviature() {
		return abbreviature;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return getAbbreviature();
	}

	public static CharacteristicsAbbreviature getCharacteristicsAbbreviature(
			String abbreviature) {
		for (CharacteristicsAbbreviature characteristic : CharacteristicsAbbreviature
				.values()) {
			if (characteristic.getAbbreviature().trim().equals(abbreviature)) {
				return characteristic;
			}
		}
		return CharacteristicsAbbreviature.NULL;
	}
}
