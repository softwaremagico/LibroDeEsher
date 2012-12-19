package com.softwaremagico.librodeesher.pj.culture;

import java.util.Hashtable;

import com.softwaremagico.librodeesher.pj.skills.Skill;

public class CultureDecisions {
	Hashtable<Skill, Integer> weaponRanks;

	public CultureDecisions() {
		weaponRanks = new Hashtable<>();
	}

	public void setWeaponRank(Skill weaponSkill, Integer ranks) {
		if (ranks <= 0) {
			weaponRanks.remove(weaponSkill);
		} else {
			weaponRanks.put(weaponSkill, ranks);
		}
	}
}
