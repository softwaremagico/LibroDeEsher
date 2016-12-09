package com.softwaremagico.librodeesher.pj.age;

import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.characteristic.Characteristics;
import com.softwaremagico.librodeesher.pj.characteristic.CharacteristicsAbbreviature;
import com.softwaremagico.librodeesher.pj.level.LevelUp;

public class AgeRules {
	private final static int PROBABILITY_PHYSICAL_CHARACTERISTIC = 60;

	public static void increaseAge(CharacterPlayer characterPlayer) {
		while (characterPlayer.getCurrentAge() > characterPlayer.getFinalAge()) {
			if (hasCharacteristicDecrease(characterPlayer)) {
				characterPlayer.getCurrentLevel().addAgeModifications(new AgeModification(characterPlayer.getCurrentAge()));
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
		return false;
	}

	public static CharacteristicsAbbreviature getRandomAgeSelectedCharacteristic() {
		// Selct if is physical or psychical
		if ((int) (Math.random() * 100 + 1) < PROBABILITY_PHYSICAL_CHARACTERISTIC) {
			return Characteristics.PHYSICAL_CHARACTERISTICS.get(((int) (Math.random() * Characteristics.PHYSICAL_CHARACTERISTICS.size())));
		} else {
			return Characteristics.MENTAL_CHARACTERISTICS.get(((int) (Math.random() * Characteristics.MENTAL_CHARACTERISTICS.size())));
		}
	}

	public static int getAgeCharactersiticTemporalValueChange(CharacterPlayer characterPlayer, CharacteristicsAbbreviature characteristicsAbbreviature) {
		int modification = 0;
		for (LevelUp levelUp : characterPlayer.getLevelUps()) {
			for (AgeModification ageModification : levelUp.getAgeModifications()) {
				if (ageModification.getCharacteristicsAbbreviature().equals(characteristicsAbbreviature)) {
					modification += ageModification.getCharacteristicModification();
				}
			}
		}
		return modification;
	}

	public static int getAgeCharactersiticPotentialValueChange(CharacterPlayer characterPlayer, CharacteristicsAbbreviature characteristicsAbbreviature) {
		return getAgeCharactersiticTemporalValueChange(characterPlayer, characteristicsAbbreviature) / 3;
	}
}
