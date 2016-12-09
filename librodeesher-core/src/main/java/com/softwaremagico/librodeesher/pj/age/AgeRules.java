package com.softwaremagico.librodeesher.pj.age;

import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.characteristic.Characteristics;
import com.softwaremagico.librodeesher.pj.characteristic.CharacteristicsAbbreviature;
import com.softwaremagico.librodeesher.pj.level.LevelUp;
import com.softwaremagico.log.EsherLog;

public class AgeRules {
	private final static int PROBABILITY_PHYSICAL_CHARACTERISTIC = 60;

	public static void increaseAge(CharacterPlayer characterPlayer) {
		while (characterPlayer.getCurrentAge() < characterPlayer.getFinalAge()) {
			if (hasCharacteristicDecrease(characterPlayer)) {
				AgeModification ageModification = new AgeModification(characterPlayer.getCurrentAge());
				characterPlayer.getCurrentLevel().addAgeModifications(ageModification);
				EsherLog.debug(AgeRules.class.getName(), "Age modification for year '" + characterPlayer.getCurrentAge() + "' is '"
						+ ageModification + "'.");
			}
			characterPlayer.setCurrentAge(characterPlayer.getCurrentAge() + 1);
		}
	}

	/**
	 * This year the character must roll a characteristic decreased.
	 * 
	 * @param characterPlayer
	 * @return
	 */
	public static boolean hasCharacteristicDecrease(CharacterPlayer characterPlayer) {
		int value = (int) ((Math.random() * 100 + 1)
				+ (characterPlayer.getCharacteristicTotalBonus(CharacteristicsAbbreviature.CONSTITUTION) * 2)
				+ characterPlayer.getCharacteristicTotalBonus(CharacteristicsAbbreviature.SELFDISCIPLINE) + getAgeModification(characterPlayer));
		EsherLog.debug(AgeRules.class.getName(), "Age modification probability for year '" + characterPlayer.getCurrentAge() + "' is '"
				+ value + "'.");
		return value < 100;
	}

	private static int getAgeModification(CharacterPlayer characterPlayer) {
		float periodOfLife = ((float) characterPlayer.getCurrentAge()) / characterPlayer.getRace().getExpectedLifeYears();
		// Young
		if (periodOfLife < 0.17) {
			// return +50;
			return 100;
		}
		// Adult
		if (periodOfLife < 0.35) {
			// return +20;
			return 100;
		}
		// Middle age
		if (periodOfLife < 0.75) {
			// return +5;
			return 100;
		}
		// Old
		if (periodOfLife < 0.80) {
			return -5;
		}
		// Very Old
		if (periodOfLife < 0.94) {
			return -30;
		}
		// Venerable
		if (periodOfLife < 1) {
			return -50;
		}
		return -75;
	}

	public static CharacteristicsAbbreviature getRandomAgeSelectedCharacteristic() {
		// Selct if is physical or psychical
		if ((int) (Math.random() * 100 + 1) < PROBABILITY_PHYSICAL_CHARACTERISTIC) {
			return Characteristics.PHYSICAL_CHARACTERISTICS.get(((int) (Math.random() * Characteristics.PHYSICAL_CHARACTERISTICS.size())));
		} else {
			return Characteristics.MENTAL_CHARACTERISTICS.get(((int) (Math.random() * Characteristics.MENTAL_CHARACTERISTICS.size())));
		}
	}

	public static int getAgeCharactersiticTemporalValueChange(CharacterPlayer characterPlayer,
			CharacteristicsAbbreviature characteristicsAbbreviature) {
		int modification = 0;
		for (LevelUp levelUp : characterPlayer.getLevelUps()) {
			modification += getAgeCharactersiticTemporalValueChange(levelUp, characteristicsAbbreviature);
		}
		return modification;
	}

	public static int getAgeCharactersiticTemporalValueChange(LevelUp levelUp, CharacteristicsAbbreviature characteristicsAbbreviature) {
		int modification = 0;
		for (AgeModification ageModification : levelUp.getAgeModifications()) {
			if (ageModification.getCharacteristicsAbbreviature().equals(characteristicsAbbreviature)) {
				modification += ageModification.getCharacteristicModification();
			}
		}
		System.out.println(characteristicsAbbreviature + " modification is " + modification);
		return modification;
	}

	public static int getAgeCharactersiticPotentialValueChange(CharacterPlayer characterPlayer,
			CharacteristicsAbbreviature characteristicsAbbreviature) {
		int modification = 0;
		for (LevelUp levelUp : characterPlayer.getLevelUps()) {
			for (AgeModification ageModification : levelUp.getAgeModifications()) {
				if (ageModification.getCharacteristicsAbbreviature().equals(characteristicsAbbreviature)) {
					modification += ageModification.getCharacteristicModification() / 3;
				}
			}
		}
		return modification;
	}
}
