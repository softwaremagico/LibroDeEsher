package com.softwaremagico.librodeesher.pj.characteristic;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.softwaremagico.librodeesher.basics.Roll;

@Entity
@Table(name = "T_CHARACTERISTIC_ROLL_GROUP")
public class CharacteristicRoll {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name = "ID", unique = true, nullable = false)
	private Long rollGroupId; // database id.

	private String characteristicAbbreviature;
	private Integer characteristicPotentialValue;
	private Integer characteristicTemporalValue;

	@OneToOne(fetch = FetchType.EAGER)
	private Roll roll;

	public CharacteristicRoll(String characteristicAbbreviature, Integer characteristicTemporalValue,
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

	public String getCharacteristicAbbreviature() {
		return characteristicAbbreviature;
	}

	public Roll getRoll() {
		return roll;
	}

}
