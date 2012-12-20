package com.softwaremagico.librodeesher.pj.culture;

import java.util.Hashtable;

import com.softwaremagico.librodeesher.pj.skills.Skill;

public class CultureDecisions {
	Hashtable<Skill, Integer> weaponRanks;
	Hashtable<Skill, Integer> hobbyRanks;

	public CultureDecisions() {
		weaponRanks = new Hashtable<>();
		hobbyRanks = new Hashtable<>();
	}

	public void setWeaponRank(Skill weaponSkill, Integer ranks) {
		if (ranks <= 0) {
			weaponRanks.remove(weaponSkill);
		} else {
			weaponRanks.put(weaponSkill, ranks);
		}
	}

	public void setHobbyRank(Skill hobby, Integer ranks) {
		if (ranks <= 0) {
			hobbyRanks.remove(hobby);
		} else {
			hobbyRanks.put(hobby, ranks);
		}
	}
}
