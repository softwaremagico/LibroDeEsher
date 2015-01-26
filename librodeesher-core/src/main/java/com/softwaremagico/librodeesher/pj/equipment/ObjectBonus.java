package com.softwaremagico.librodeesher.pj.equipment;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.softwaremagico.persistence.StorableObject;

@Entity
@Table(name = "T_MAGIC_OBJECT_BONUS")
public class ObjectBonus extends StorableObject {
	private static final long serialVersionUID = 6799822840993688414L;

	@Expose
	private int bonus;

	@Expose
	private String bonusName;

	@Expose
	@Enumerated(EnumType.STRING)
	private BonusType type;

	//Is created using MagicObject methods.
	ObjectBonus() {

	}

	public String getBonusName() {
		if (type != null && type != BonusType.SKILL && type != BonusType.CATEGORY) {
			return type.getTranslation();
		}
		return bonusName;
	}

	public int getBonus() {
		return bonus;
	}

	public void setBonus(int bonus) {
		this.bonus = bonus;
	}

	public void resetIds() {
		setId(null);
	}

	public void setBonusName(String value) {
		this.bonusName = value;
	}

	@Override
	public String toString() {
		return getBonusName() + " (" + getBonus() + ")";
	}

	public BonusType getType() {
		return type;
	}

	public void setType(BonusType type) {
		this.type = type;
	}

}
