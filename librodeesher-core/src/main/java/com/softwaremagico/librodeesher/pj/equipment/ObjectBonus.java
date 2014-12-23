package com.softwaremagico.librodeesher.pj.equipment;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.softwaremagico.persistence.StorableObject;

@Entity
@Table(name = "T_MAGIC_OBJECT_BONUS")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class ObjectBonus extends StorableObject {
	@Expose
	public int bonus;

	public int getBonus() {
		return bonus;
	}

	public void setBonus(int bonus) {
		this.bonus = bonus;
	}

	public void resetIds() {
		setId(null);
	}
	
	public abstract String getBonusName();

	public abstract void setBonusName(String value);

}
