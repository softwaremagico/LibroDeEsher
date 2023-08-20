package com.softwaremagico.librodeesher.pj.equipment;

import com.softwaremagico.librodeesher.basics.Spanish;

public enum BonusType {
	CATEGORY("Categoria"),
	
	SKILL("Habilidad"),
	
	DEFENSIVE_BONUS(Spanish.DEFENSIVE_BONUS);

	private String translation;

	private BonusType(String translation) {
		this.translation = translation;
	}
	
	public String getTranslation(){
		return translation;
	}
	
	@Override
	public String toString(){
		return getTranslation();
	}
}
