package com.softwaremagico.librodeesher.pj;

public enum ProgressionCostType {
	PHYSICAL_DEVELOPMENT("Desarrollo Físico"), ARCANUM_POWER_DEVELOPMENT("PP Arcano"), CANALIZATION_POWER_DEVELOPMENT(
			"PP Canalización"), ESSENCE_POWER_DEVELOPMENT("PP Esencia"), MENTALISM_POWER_DEVELOPMENT(
			"PP Mentalismo"), PSIONIC_POWER_DEVELOPMENT("PP Psiónico"), STANDAR("Estándar"), LIMITED(
			"Limitada"), COMBINED("Combinada");

	private String tag;

	ProgressionCostType(String tag) {
		this.tag = tag;
	}

	public static ProgressionCostType getProgressionCostType(String tag) {
		for (ProgressionCostType type : ProgressionCostType.values()) {
			if (type.tag.equals(tag)) {
				return type;
			}
		}
		return null;
	}
}
