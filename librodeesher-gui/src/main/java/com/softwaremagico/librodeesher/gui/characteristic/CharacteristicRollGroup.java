package com.softwaremagico.librodeesher.gui.characteristic;

import com.softwaremagico.librodeesher.basics.RollGroup;

public class CharacteristicRollGroup extends RollGroup {
	private Integer characteristicPotentialValue;
	private Integer characteristicTemporalValue;

	public CharacteristicRollGroup(String characteristicAbbreviature, Integer characteristicTemporalValue,
			Integer characteristicPotentialValue) {
		super(characteristicAbbreviature);
		this.characteristicTemporalValue = characteristicTemporalValue;
		this.characteristicTemporalValue = characteristicTemporalValue;
	}

	public Integer getCharacteristicPotentialValue() {
		return characteristicPotentialValue;
	}

	public Integer getCharacteristicTemporalValue() {
		return characteristicTemporalValue;
	}
}
