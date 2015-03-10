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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.softwaremagico.librodeesher.basics.Dice;
import com.softwaremagico.librodeesher.basics.Roll;

public class Characteristics {
	private final static String allowedCharacteristics = "AgCoMeRaAdEmInPrRpFu";
	public static Map<String, String> characteristicAbbreviatureList;
	public final static int TOTAL_CHARACTERISTICS_POINTS = 660;
	public final static Integer INITIAL_CHARACTERISTIC_VALUE = 31;
	public final static Integer MAX_INITIAL_CHARACTERISTIC_VALUE = 100;

	private static List<Characteristic> characteristics = new ArrayList<>();

	static {
		characteristics.add(new Characteristic(CharacteristicsAbbreviature.AGILITY));
		characteristics.add(new Characteristic(CharacteristicsAbbreviature.CONSTITUTION));
		characteristics.add(new Characteristic(CharacteristicsAbbreviature.MEMORY));
		characteristics.add(new Characteristic(CharacteristicsAbbreviature.REASON));
		characteristics.add(new Characteristic(CharacteristicsAbbreviature.SELFDISCIPLINE));
		characteristics.add(new Characteristic(CharacteristicsAbbreviature.EMPATHY));
		characteristics.add(new Characteristic(CharacteristicsAbbreviature.INTUITION));
		characteristics.add(new Characteristic(CharacteristicsAbbreviature.PRESENCE));
		characteristics.add(new Characteristic(CharacteristicsAbbreviature.SPEED));
		characteristics.add(new Characteristic(CharacteristicsAbbreviature.STRENGTH));
	}

	public static List<Characteristic> getCharacteristics() {
		return characteristics;
	}

	public static boolean isCharacteristicValid(String abbrev) {
		return allowedCharacteristics.contains(abbrev);
	}

	public static Characteristic getCharacteristicFromAbbreviature(CharacteristicsAbbreviature abbreviature) {
		for (int i = 0; i < characteristics.size(); i++) {
			Characteristic characteristic = characteristics.get(i);
			if (characteristic.getAbbreviature().equals(abbreviature)) {
				return characteristic;
			}
		}
		return null;
	}

	public static Integer getTemporalBonus(Integer temporalValue) {
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

	public static Integer getPotencial(Integer temporalValue) {
		if (temporalValue >= 100) {
			return 99 + Dice.getRoll(1, 2);
		}
		if (temporalValue >= 99) {
			return 98 + Dice.getRoll(1, 2);
		}
		if (temporalValue >= 98) {
			return 97 + Dice.getRoll(1, 3);
		}
		if (temporalValue >= 97) {
			return 96 + Dice.getRoll(1, 4);
		}
		if (temporalValue >= 96) {
			return 95 + Dice.getRoll(1, 5);
		}
		if (temporalValue >= 95) {
			return 94 + Dice.getRoll(1, 6);
		}
		if (temporalValue >= 94) {
			return 93 + Dice.getRoll(1, 7);
		}
		if (temporalValue >= 93) {
			return 92 + Dice.getRoll(1, 8);
		}
		if (temporalValue >= 92) {
			return 91 + Dice.getRoll(1, 9);
		}
		if (temporalValue >= 85) {
			return 90 + Dice.getRoll(1, 10);
		}
		if (temporalValue >= 75) {
			return 80 + Dice.getRoll(2, 10);
		}
		if (temporalValue >= 65) {
			return 70 + Dice.getRoll(3, 10);
		}
		if (temporalValue >= 55) {
			return 60 + Dice.getRoll(4, 10);
		}
		if (temporalValue >= 45) {
			return 50 + Dice.getRoll(5, 10);
		}
		if (temporalValue >= 35) {
			return 40 + Dice.getRoll(6, 10);
		}
		if (temporalValue >= 25) {
			return 30 + Dice.getRoll(7, 10);
		}
		if (temporalValue >= 20) {
			return 20 + Dice.getRoll(8, 10);
		}
		return 0;
	}

	public static Integer setTemporalIncrease(Integer currentTemporalValue, Integer potentialValue, Roll twoDices) {
		Integer increase;
		if (potentialValue - currentTemporalValue <= 10) {
			if (!twoDices.getFirstDice().equals(twoDices.getSecondDice())) {
				increase = Math.min(twoDices.getFirstDice(), twoDices.getSecondDice());
			} else {
				if (twoDices.getFirstDice() < 6) {
					increase = -twoDices.getFirstDice();
				} else {
					increase = twoDices.getFirstDice() * 2;
				}
			}
		} else if (potentialValue - currentTemporalValue <= 20) {
			if (!twoDices.getFirstDice().equals(twoDices.getSecondDice())) {
				increase = Math.max(twoDices.getFirstDice(), twoDices.getSecondDice());
			} else {
				if (twoDices.getFirstDice() < 6) {
					increase = -twoDices.getFirstDice();
				} else {
					increase = twoDices.getFirstDice() * 2;
				}
			}
		} else {
			if (!twoDices.getFirstDice().equals(twoDices.getSecondDice())) {
				increase = twoDices.getFirstDice() + twoDices.getSecondDice();
			} else {
				if (twoDices.getFirstDice() < 6) {
					increase = -twoDices.getFirstDice();
				} else {
					increase = twoDices.getFirstDice() * 2;
				}
			}
		}
		return increase;
	}

}
