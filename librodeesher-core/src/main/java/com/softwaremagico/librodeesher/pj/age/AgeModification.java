package com.softwaremagico.librodeesher.pj.age;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.softwaremagico.librodeesher.basics.Roll;
import com.softwaremagico.librodeesher.pj.characteristic.CharacteristicsAbbreviature;
import com.softwaremagico.persistence.StorableObject;

@Entity
@Table(name = "T_AGE")
public class AgeModification extends StorableObject {
	private static final long serialVersionUID = 5573878575330834408L;

	public static final int INITIAL_AGE = 10;

	@Expose
	private int age;

	@Expose
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private Roll roll;

	@Expose
	@Enumerated(EnumType.STRING)
	private CharacteristicsAbbreviature characteristicsAbbreviature;

	private int raceType;

	public AgeModification(int age, int raceType) {
		roll = new Roll();
		this.raceType = raceType;
		this.age = age;
		characteristicsAbbreviature = AgeRules.getRandomAgeSelectedCharacteristic();
	}

	public int getCharacteristicModification() {
		switch (raceType) {
		case 1:
			return ((roll.getFirstDice() + 1) / 2) - 1;
		case 2:
			return ((roll.getFirstDice() + 1) / 2) + 1;
		case 3:
			return roll.getFirstDice();
		case 4:
			return roll.getFirstDice() + 1;
		case 5:
			return roll.getFirstDice() + roll.getSecondDice() - 1;
		}
		// Default value;
		return ((roll.getFirstDice() + 1) / 2) + 1;
	}

	public CharacteristicsAbbreviature getCharacteristicsAbbreviature() {
		return characteristicsAbbreviature;
	}

	@Override
	public void resetIds() {
		resetIds(this);
	}

	@Override
	public void resetComparationIds() {
		resetComparationIds(this);
	}

	@Override
	public String toString() {
		return characteristicsAbbreviature + " " + getCharacteristicModification();
	}
}
