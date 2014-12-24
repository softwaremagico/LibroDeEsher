package com.softwaremagico.librodeesher.pj.equipment;

import com.softwaremagico.librodeesher.basics.Spanish;

public enum OtherBonusType {
	DEFENSIVE_BONUS(Spanish.DEFENSIVE_BONUS);

	private String translation;

	private OtherBonusType(String translation) {
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
