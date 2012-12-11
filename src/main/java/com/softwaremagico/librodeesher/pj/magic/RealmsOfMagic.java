package com.softwaremagico.librodeesher.pj.magic;

public enum RealmsOfMagic {
	CANALIZATION("Canalización"), ESSENCE("Esencia"), MENTALISM("Mentalismo"), PSIONIC("Psiónico");

	private String tag;

	RealmsOfMagic(String tag) {
		this.tag = tag;
	}

	public static RealmsOfMagic getMagicRealm(String tag) {
		for (RealmsOfMagic type : RealmsOfMagic.values()) {
			if (type.tag.equals(tag)) {
				return type;
			}
		}
		return null;
	}
}
