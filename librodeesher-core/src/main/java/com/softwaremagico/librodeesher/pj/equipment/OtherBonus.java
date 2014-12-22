package com.softwaremagico.librodeesher.pj.equipment;

import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "T_TRAINING_OBJECT_OTHER_BONUS")
public class OtherBonus extends ObjectBonus {

	@Override
	public String getBonusName() {
		return "";
	}
	
}
