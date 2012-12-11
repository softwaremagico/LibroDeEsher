package com.softwaremagico.librodeesher.pj.magic;

public enum MagicListType {
	BASIC("lista bBásica"), OPEN("lista abierta"), CLOSED("lista cerrada"), OTHER_PROFESSION(
			"listas básicas de otras profesiones"), OTHER_REALM_OPEN("listas abiertas de otros reinos"), OTHER_REALM_CLOSED(
			"listas cerradas de otros reinos"), OTHER_REALM_OTHER_PROFESSION("listas básicas de otros reinos"), ARCHANUM(
			"listas abiertas arcanas"), TRIAD("listas básicas de la tríada"), COMPLEMENTARY_TRIAD(
			"listas básicas elementales complementarias"), TRAINING("listas propias de adiestramiento"), OTHER_TRAINING(
			"listas de otros adiestramientos");

	private String tag;

	MagicListType(String tag) {
		this.tag = tag;
	}

	public static MagicListType getMagicType(String tag) {
		tag = tag.toLowerCase().trim();
		for (MagicListType type : MagicListType.values()) {
			if (type.tag.equals(tag)) {
				return type;
			}
		}
		return null;
	}
}
