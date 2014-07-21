package com.softwaremagico.librodeesher.pj.characteristic;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.softwaremagico.librodeesher.basics.Roll;
import com.softwaremagico.persistence.StorableObject;

@Entity
@Table(name = "T_CHARACTERISTIC_ROLL_GROUP")
public class CharacteristicRoll extends StorableObject {
	@Enumerated(EnumType.STRING)
	private CharacteristicsAbbreviature characteristicAbbreviature;
	private Integer characteristicPotentialValue;
	private Integer characteristicTemporalValue;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private Roll roll;

	public CharacteristicRoll(CharacteristicsAbbreviature characteristicAbbreviature,
			Integer characteristicTemporalValue,
			Integer characteristicPotentialValue, Roll roll) {
		this.characteristicAbbreviature = characteristicAbbreviature;
		this.characteristicTemporalValue = characteristicTemporalValue;
		this.characteristicPotentialValue = characteristicPotentialValue;
		this.roll = roll;
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

}
