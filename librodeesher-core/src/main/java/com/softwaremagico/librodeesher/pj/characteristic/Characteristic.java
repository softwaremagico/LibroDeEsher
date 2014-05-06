package com.softwaremagico.librodeesher.pj.characteristic;

import com.softwaremagico.librodeesher.basics.Roll;

public class Characteristic {
	private String abbreviature;
	private String name;

	public Characteristic(String ab, String name) {
		abbreviature = ab;
		this.name = name;
	}

	public static Integer getCharacteristicUpgrade(Integer temporalValue, Integer potentialValue,
			Roll roll) {
		if (roll.getFirstDice() == roll.getSecondDice()) {
			if (roll.getFirstDice() < 6) {
				return -roll.getFirstDice();
			} else {
				return Math.min(roll.getFirstDice() * 2, potentialValue - temporalValue);
			}
		} else {
			if (potentialValue - temporalValue <= 10) {
				return Math.min(Math.min(roll.getFirstDice(), roll.getSecondDice()), potentialValue
						- temporalValue);
			} else if (potentialValue - temporalValue <= 20) {
				return Math.min(Math.max(roll.getFirstDice(), roll.getSecondDice()), potentialValue
						- temporalValue);
			} else {
				return Math.min(roll.getFirstDice() + roll.getSecondDice(), potentialValue - temporalValue);
			}
		}
	}

	public static Integer getTemporalCost(Integer temporalValue) {
		Double d;
		if (temporalValue < 91) {
			return temporalValue;
		} else {
			d = Math.pow(temporalValue - 90, 2) + 90;
		}
		return d.intValue();
	}

	public String getAbbreviature() {
		return abbreviature;
	}

	public String getName() {
		return name;
	}

	public String toString() {
		return getName() + " (" + abbreviature + ")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((abbreviature == null) ? 0 : abbreviature.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Characteristic other = (Characteristic) obj;
		if (abbreviature == null) {
			if (other.abbreviature != null)
				return false;
		} else if (!abbreviature.equals(other.abbreviature))
			return false;
		return true;
	}

	public void setAbbreviature(String abbreviature) {
		this.abbreviature = abbreviature;
	}

	public void setName(String name) {
		this.name = name;
	}

}
