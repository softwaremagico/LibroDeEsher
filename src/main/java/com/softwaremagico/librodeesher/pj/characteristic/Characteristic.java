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

	private String abbreviation;

	public Characteristic(String ab) {
		abbreviation = ab;
	}


	public static Integer getPotencial(Integer temporalValue) {
		if (temporalValue >= 100) {
			return 99 + Dice.roll(1, 2);
		}
		if (temporalValue >= 99) {
			return 98 + Dice.roll(1, 2);
		}
		if (temporalValue >= 98) {
			return 97 + Dice.roll(1, 3);
		}
		if (temporalValue >= 97) {
			return 96 + Dice.roll(1, 4);
		}
		if (temporalValue >= 96) {
			return 95 + Dice.roll(1, 5);
		}
		if (temporalValue >= 95) {
			return 94 + Dice.roll(1, 6);
		}
		if (temporalValue >= 94) {
			return 93 + Dice.roll(1, 7);
		}
		if (temporalValue >= 93) {
			return 92 + Dice.roll(1, 8);
		}
		if (temporalValue >= 92) {
			return 91 + Dice.roll(1, 9);
		}
		if (temporalValue >= 85) {
			return 90 + Dice.roll(1, 10);
		}
		if (temporalValue >= 75) {
			return 80 + Dice.roll(2, 10);
		}
		if (temporalValue >= 65) {
			return 70 + Dice.roll(3, 10);
		}
		if (temporalValue >= 55) {
			return 60 + Dice.roll(4, 10);
		}
		if (temporalValue >= 45) {
			return 50 + Dice.roll(5, 10);
		}
		if (temporalValue >= 35) {
			return 40 + Dice.roll(6, 10);
		}
		if (temporalValue >= 25) {
			return 30 + Dice.roll(7, 10);
		}
		if (temporalValue >= 20) {
			return 20 + Dice.roll(8, 10);
		}
		return 0;
	}

	public static Integer getCharacteristicUpgrade(Integer temporalValue, Integer potentialValue) {
		int dice1 = Dice.roll(10);
		int dice2 = Dice.roll(10);

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

	public Integer getTemporalBonus(Integer temporalValue) {
		if (temporalValue >= 102) {
			return 14;
		}
		if (temporalValue >= 101) {
			return 12;
		}
		if (temporalValue >= 100) {
			return 10;
		}
		if (temporalValue >= 98) {
			return 9;
		}
		if (temporalValue >= 96) {
			return 8;
		}
		if (temporalValue >= 94) {
			return 7;
		}
		if (temporalValue >= 92) {
			return 6;
		}
		if (temporalValue >= 90) {
			return 5;
		}
		if (temporalValue >= 85) {
			return 4;
		}
		if (temporalValue >= 80) {
			return 3;
		}
		if (temporalValue >= 75) {
			return 2;
		}
		if (temporalValue >= 70) {
			return 1;
		}
		if (temporalValue >= 31) {
			return 0;
		}
		if (temporalValue >= 26) {
			return -1;
		}
		if (temporalValue >= 21) {
			return -2;
		}
		if (temporalValue >= 16) {
			return -3;
		}
		if (temporalValue >= 11) {
			return -4;
		}
		if (temporalValue >= 10) {
			return -5;
		}
		if (temporalValue >= 8) {
			return -6;
		}
		if (temporalValue >= 6) {
			return -7;
		}
		if (temporalValue >= 4) {
			return -8;
		}
		if (temporalValue >= 2) {
			return -9;
		}
		return -10;
	}

	public Integer getTemporalCost(Integer temporalValue) {
		Double d;
		if (temporalValue < 91) {
			return temporalValue;
		} else {
			d = Math.pow(temporalValue - 90, 2) + 90;
		}
		return d.intValue();
	}

	public String getAbbreviation() {
		return abbreviation;
	}

}
