package com.softwaremagico.librodeesher.pj.characteristic;

/*
 * #%L
 * Libro de Esher
 * %%
 * Copyright (C) 2007 - 2012 Softwaremagico
 * %%
 * This software is designed by Jorge Hortelano Otero. Jorge Hortelano Otero
 * <softwaremagico@gmail.com> C/Quart 89, 3. Valencia CP:46008 (Spain).
 *  
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *  
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *  
 * You should have received a copy of the GNU General Public License along with
 * this program; If not, see <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
import java.util.Hashtable;

import com.softwaremagico.librodeesher.basics.TwoDices;

public class Characteristic {
	private String abbreviature;
	private String name;

	public Characteristic(String ab, String name) {
		abbreviature = ab;
		this.name = name;
	}

	public static Integer getCharacteristicUpgrade(Integer temporalValue, Integer potentialValue,
			TwoDices roll) {
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

}
