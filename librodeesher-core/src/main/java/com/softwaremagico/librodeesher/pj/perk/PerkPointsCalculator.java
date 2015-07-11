package com.softwaremagico.librodeesher.pj.perk;

import com.softwaremagico.librodeesher.pj.characteristic.CharacteristicsAbbreviature;
import com.softwaremagico.librodeesher.pj.magic.RealmOfMagic;
import com.softwaremagico.librodeesher.pj.race.Race;
import com.softwaremagico.librodeesher.pj.resistance.ResistanceType;

public class PerkPointsCalculator {
	private Race race;

	public PerkPointsCalculator(Race race) {
		this.race = race;
	}

	public void getPerkPoints() {
		getRacePoints();
	}

	private int getRacePoints() {
		int racePoints = 0;
		racePoints += getPhysicalDevelopmentRacePoints();
		racePoints += getPowerPointsRacePoints(RealmOfMagic.CANALIZATION);
		racePoints += getPowerPointsRacePoints(RealmOfMagic.ESSENCE);
		racePoints += getPowerPointsRacePoints(RealmOfMagic.MENTALISM);
		racePoints += getLifeExpectationRacePoints();
		for (ResistanceType resistance : ResistanceType.values()) {
			racePoints += getResisteceRacePoints(resistance);
		}
		racePoints += getSoulDepartTimeRacePoints();
		racePoints += getStartingLanguagesRacePoints();
		for (CharacteristicsAbbreviature characteristic : CharacteristicsAbbreviature.values()) {
			racePoints += getCharactersiticBonusRacePoints(characteristic);
		}
		racePoints += getSleepingTimeRacePoints();
		racePoints += getEnduranceRacePoints();
		racePoints += getRecoveryRacePoints();
		racePoints += getRaceTypeRacePoints();
		racePoints += getCommonSkillsRacePoints();
		racePoints += getRestrictedSkillsRacePoints();
		return racePoints;
	}

	private int getRestrictedSkillsRacePoints() {
		// TODO Auto-generated method stub
		return 0;
	}

	private int getCommonSkillsRacePoints() {
		// TODO Auto-generated method stub
		return 0;
	}

	private int getRaceTypeRacePoints() {
		// TODO Auto-generated method stub
		return 0;
	}

	private int getRecoveryRacePoints() {
		// TODO Auto-generated method stub
		return 0;
	}

	private int getEnduranceRacePoints() {
		// TODO Auto-generated method stub
		return 0;
	}

	private int getSleepingTimeRacePoints() {
		// TODO Auto-generated method stub
		return 0;
	}

	private int getCharactersiticBonusRacePoints(CharacteristicsAbbreviature characteristic) {
		// TODO Auto-generated method stub
		return 0;
	}

	private int getStartingLanguagesRacePoints() {
		// TODO Auto-generated method stub
		return 0;
	}

	private int getSoulDepartTimeRacePoints() {
		// TODO Auto-generated method stub
		return 0;
	}

	private int getResisteceRacePoints(ResistanceType canalization) {
		// TODO Auto-generated method stub
		return 0;
	}

	private int getLifeExpectationRacePoints() {
		// TODO Auto-generated method stub
		return 0;
	}

	private int getPowerPointsRacePoints(RealmOfMagic canalization) {
		// TODO Auto-generated method stub
		return 0;
	}

	private int getPhysicalDevelopmentRacePoints() {
		// TODO Auto-generated method stub
		return 0;
	}
}
