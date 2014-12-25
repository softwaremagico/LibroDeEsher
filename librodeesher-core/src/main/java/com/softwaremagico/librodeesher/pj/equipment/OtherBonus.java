package com.softwaremagico.librodeesher.pj.equipment;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "T_TRAINING_OBJECT_OTHER_BONUS")
public class OtherBonus extends ObjectBonus {
	
	@Expose
	@Enumerated(EnumType.STRING) 
	private OtherBonusType type;

	@Override
	public String getBonusName() {
		if (type != null) {
			return type.getTranslation();
		}
		return "";
	}

	@Override
	public void setBonusName(String value) {
	}

	public OtherBonusType getType() {
		return type;
	}

	public void setType(OtherBonusType type) {
		this.type = type;
	}

}
