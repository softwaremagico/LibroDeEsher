package com.softwaremagico.librodeesher.pj.culture;

import java.util.Hashtable;

import com.softwaremagico.librodeesher.pj.magic.SpellList;
import com.softwaremagico.librodeesher.pj.skills.Skill;

public class CultureDecisions {
	Hashtable<Skill, Integer> weaponRanks;
	Hashtable<CultureSkill, Integer> hobbyRanks;
	Hashtable<SpellList, Integer> spellRanks;

	public CultureDecisions() {
		weaponRanks = new Hashtable<>();
		hobbyRanks = new Hashtable<>();
		spellRanks = new Hashtable<>();
	}

	public void setWeaponRank(Skill weaponSkill, Integer ranks) {
		if (ranks <= 0) {
			weaponRanks.remove(weaponSkill);
		} else {
			weaponRanks.put(weaponSkill, ranks);
		}
	}

	public Integer getWeaponRank(Skill weaponSkill) {
		Integer value = weaponRanks.get(weaponSkill);
		if (value == null) {
			return 0;
		}
		return value;
	}

	public void setHobbyRank(CultureSkill hobby, Integer ranks) {
		if (ranks <= 0) {
			hobbyRanks.remove(hobby);
		} else {
			hobbyRanks.put(hobby, ranks);
		}
	}

	public Integer getHobbyRank(CultureSkill hobby) {
		Integer value = hobbyRanks.get(hobby);
		if (value == null) {
			return 0;
		}
		return value;
	}

	public void setSpellRank(SpellList spellList, Integer ranks) {
		if (ranks <= 0) {
			spellRanks.remove(spellList);
		} else {
			spellRanks.put(spellList, ranks);
		}
	}

	public Integer getSpellRank(SpellList spellList) {
		Integer value = spellRanks.get(spellList);
		if (value == null) {
			return 0;
		}
		return value;
	}

	public Integer getTotalHobbyRanks() {
		Integer total = 0;
		for (Integer value : hobbyRanks.values()) {
			total += value;
		}
		return total;
	}

	public Integer getTotalSpellRanks() {
		Integer total = 0;
		for (Integer value : spellRanks.values()) {
			total += value;
		}
		return total;
	}
}
