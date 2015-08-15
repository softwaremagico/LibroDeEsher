package com.softwaremagico.librodeesher.pj.characteristic;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.softwaremagico.librodeesher.basics.Roll;
import com.softwaremagico.persistence.StorableObject;

@Entity
@Table(name = "T_CHARACTERISTIC_ROLL_GROUP")
public class CharacteristicRoll extends StorableObject {
	private static final long serialVersionUID = 5426437093866622745L;
	@Expose
	@Enumerated(EnumType.STRING)
	private CharacteristicsAbbreviature characteristicAbbreviature;
	@Expose
	private Integer characteristicPotentialValue;
	@Expose
	private Integer characteristicTemporalValue;

	@Expose
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private Roll roll;

	protected CharacteristicRoll() {

	}

	public CharacteristicRoll(CharacteristicsAbbreviature characteristicAbbreviature,
			Integer characteristicTemporalValue, Integer characteristicPotentialValue, Roll roll) {
		this.characteristicAbbreviature = characteristicAbbreviature;
		this.characteristicTemporalValue = characteristicTemporalValue;
		this.characteristicPotentialValue = characteristicPotentialValue;
		this.roll = roll;
	}

	@Override
	public void resetIds() {
		resetIds(this);
		resetIds(roll);
	}

	@Override
	public void resetComparationIds() {
		resetComparationIds(this);
		resetComparationIds(roll);
	}

	public Integer getCharacteristicPotentialValue() {
		return characteristicPotentialValue;
	}

	public Integer getCharacteristicTemporalValue() {
		return characteristicTemporalValue;
	}

	public CharacteristicsAbbreviature getCharacteristicAbbreviature() {
		return characteristicAbbreviature;
	}

	public Roll getRoll() {
		return roll;
	}

	@Override
	public String toString() {
		return characteristicAbbreviature + "[" + characteristicTemporalValue + " ->"
				+ characteristicPotentialValue + "]: " + roll;
	}

	public void setCharacteristicPotentialValue(Integer characteristicPotentialValue) {
		this.characteristicPotentialValue = characteristicPotentialValue;
	}
	
	public void setCharacteristicTemporalValue(Integer characteristicPotentialValue) {
		this.characteristicTemporalValue = characteristicPotentialValue;
	}

}
