package com.softwaremagico.librodeesher.pj.perk;

import com.softwaremagico.librodeesher.pj.ProgressionCostType;
import com.softwaremagico.librodeesher.pj.characteristic.CharacteristicsAbbreviature;
import com.softwaremagico.librodeesher.pj.magic.RealmOfMagic;
import com.softwaremagico.librodeesher.pj.race.Race;
import com.softwaremagico.librodeesher.pj.race.exceptions.InvalidRaceDefinition;
import com.softwaremagico.librodeesher.pj.resistance.ResistanceType;

public class PerkPointsCalculator {
	private Race race;

	public PerkPointsCalculator(Race race) {
		this.race = race;
	}

	public int getPerkPoints() throws InvalidRaceDefinition {
		int racePoints = getRacePoints();
		if (racePoints < -75) {
			return 65;
		} else if (racePoints <= 25) {
			return 60;
		} else if (racePoints <= 75) {
			return 55;
		} else if (racePoints <= 100) {
			return 50;
		} else if (racePoints <= 125) {
			return 45;
		} else if (racePoints <= 150) {
			return 40;
		} else if (racePoints <= 175) {
			return 35;
		} else if (racePoints <= 200) {
			return 30;
		} else if (racePoints <= 225) {
			return 25;
		} else if (racePoints <= 250) {
			return 20;
		} else if (racePoints <= 275) {
			return 15;
		} else if (racePoints <= 300) {
			return 10;
		} else if (racePoints <= 325) {
			return 5;
		} else {
			return 0;
		}
	}

	private int getRacePoints() throws InvalidRaceDefinition {
		int racePoints = 0;
		racePoints += getPhysicalDevelopmentRacePoints();
		for (RealmOfMagic realm : RealmOfMagic.values()) {
			racePoints += getPowerPointsRacePoints(realm);
		}
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
		int restrictedSkills = race.getTotalCommonSkills();
		if (restrictedSkills == 0) {
			return 0;
		} else if (restrictedSkills <= 5) {
			return restrictedSkills * -2;
		} else if (restrictedSkills <= 10) {
			return ((restrictedSkills - 5) * -1) - 10;
		} else {
			return -15;
		}
	}

	private int getCommonSkillsRacePoints() {
		int commonSkills = race.getTotalCommonSkills();
		if (commonSkills == 0) {
			return 0;
		}
		return commonSkills * (((commonSkills - 1) / 10) + 1);
	}

	private int getRaceTypeRacePoints() throws InvalidRaceDefinition {
		switch (race.getRaceType()) {
		case 1:
			return 10;
		case 2:
			return -5;
		case 3:
			return -0;
		case 4:
			return -5;
		case 5:
			return -10;
		default:
			throw new InvalidRaceDefinition("Unknown race type!");
		}
	}

	private int getRecoveryRacePoints() throws InvalidRaceDefinition {
		if (race.getRestorationTime() <= 3) {
			return -45;
		} else if ((float) race.getRestorationTime() == 2) {
			return -25;
		} else if ((float) race.getRestorationTime() == 1.5f) {
			return -10;
		} else if ((float) race.getRestorationTime() == 1) {
			return 0;
		} else if ((float) race.getRestorationTime() == 0.9) {
			return 3;
		} else if ((float) race.getRestorationTime() == 0.75) {
			return 5;
		} else if ((float) race.getRestorationTime() == 0.5) {
			return 10;
		} else {
			throw new InvalidRaceDefinition("Unknown Recovery bonus!");
		}
	}

	private int getEnduranceRacePoints() {
		// Not implemented yet.
		return 0;
	}

	private int getSleepingTimeRacePoints() {
		// Not implemented yet.
		return 0;
	}

	private int getCharactersiticBonusRacePoints(CharacteristicsAbbreviature characteristic)
			throws InvalidRaceDefinition {
		switch (race.getCharacteristicBonus(characteristic)) {
		case -10:
			return -45;
		case -9:
			return -35;
		case -8:
			return -30;
		case -7:
			return -25;
		case -6:
			return -20;
		case -5:
			return -13;
		case -4:
			return -10;
		case -3:
			return -7;
		case -2:
			return -5;
		case -1:
			return -3;
		case 0:
			return 0;
		case 1:
			return 3;
		case 2:
			return 5;
		case 3:
			return 7;
		case 4:
			return 10;
		case 5:
			return 25;
		case 6:
			return 45;
		case 7:
			return 55;
		case 8:
			return 65;
		case 9:
			return 73;
		case 10:
			return 80;
		default:
			throw new InvalidRaceDefinition("Unknown Characteristic bonus!");
		}
	}

	private int getStartingLanguagesRacePoints() {
		// TODO Auto-generated method stub
		return 0;
	}

	private int getSoulDepartTimeRacePoints() {
		switch (race.getSoulDepartTime()) {
		case 1:
			return -25;
		case 2:
			return -20;
		case 3:
			return -15;
		case 4:
			return -11;
		case 5:
			return -9;
		case 6:
			return -7;
		case 7:
			return -5;
		case 8:
			return -4;
		case 9:
			return -3;
		case 10:
			return -2;
		case 11:
			return -1;
		case 12:
			return 0;
		case 13:
			return 2;
		case 14:
			return 4;
		case 15:
			return 6;
		case 16:
			return 8;
		case 17:
			return 10;
		case 18:
			return 15;
		default:
			return ((race.getSoulDepartTime() - 18) * 5) + 15;
		}
	}

	private int getResisteceRacePoints(ResistanceType resistance) throws InvalidRaceDefinition {
		Integer bonus = race.getResistancesBonus(resistance);
		if (bonus < 10) {
			return ((bonus - 10) * 2) - 10;
		} else {
			switch (bonus) {
			case -10:
				return -10;
			case -5:
				return -5;
			case 0:
				return 0;
			case 5:
				return 3;
			case 10:
				return 5;
			case 15:
				return 7;
			case 20:
				return 10;
			case 30:
				return 13;
			case 40:
				return 16;
			case 50:
				return 20;
			case 60:
				return 25;
			case 70:
				return 35;
			case 80:
				return 50;
			case 90:
				return 70;
			case 100:
				return 95;
			default:
				throw new InvalidRaceDefinition("Unknown Resistence value!");
			}
		}
	}

	private int getLifeExpectationRacePoints() {
		// Not implemented yet!
		return 0;
	}

	private int getPowerPointsRacePoints(RealmOfMagic realm) throws InvalidRaceDefinition {
		String cost = race
				.getProgressionRankValuesAsString(ProgressionCostType.getProgressionCostType(realm));
		switch (cost) {
		case "0/2/1/1/1":
			return -25;
		case "0/3/2/1/1":
			return -20;
		case "0/4/3/2/1":
			return -10;
		case "0/5/3/2/2":
			return 0;
		case "0/6/4/3/2":
			return 5;
		case "0/6/5/4/3":
			return 10;
		case "0/6/6/4/3":
			return 13;
		case "0/7/6/5/4":
			return 15;
		default:
			throw new InvalidRaceDefinition("Unknown Power Points value!");
		}
	}

	private int getPhysicalDevelopmentRacePoints() throws InvalidRaceDefinition {
		String cost = race.getProgressionRankValuesAsString(ProgressionCostType.PHYSICAL_DEVELOPMENT);
		switch (cost) {
		case "0/6/2/2/1":
			return -17;
		case "0/6/3/1/1":
			return -15;
		case "0/6/3/2/1":
			return -10;
		case "0/6/4/2/1":
			return 0;
		case "0/7/3/2/1":
			return 5;
		case "0/6/5/2/1":
			return 10;
		case "0/7/4/2/1":
			return 13;
		case "0/7/5/3/1":
			return 15;
		default:
			throw new InvalidRaceDefinition("Unknown Development Points value!");
		}
	}
}
