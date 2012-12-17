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
import com.softwaremagico.librodeesher.core.Dice;

public class Characteristic {

	private String abbreviature;

	public Characteristic(String ab) {
		abbreviature = ab;
	}

	
	public static Integer getCharacteristicUpgrade(Integer temporalValue, Integer potentialValue) {
		int dice1 = Dice.getRoll(10);
		int dice2 = Dice.getRoll(10);

		if (dice1 == dice2) {
			if (dice1 < 6) {
				return -dice1;
			} else {
				return dice1 * 2;
			}
		} else {
			if (potentialValue - temporalValue <= 10) {
				return Math.min(dice1, dice2);
			} else if (potentialValue - temporalValue <= 20) {
				return Math.max(dice1, dice2);
			} else {
				return dice1 + dice2;
			}
		}
	}

	public static Integer getTemporalCost(Integer temporalValue) {
		Double d;
		if (temporalValue < 91) {
			return temporalValue;
		} else{
			d = Math.pow(temporalValue - 90, 2) + 90;
		}
		return d.intValue();
	}

	public String getAbbreviature() {
		return abbreviature;
	}

}
