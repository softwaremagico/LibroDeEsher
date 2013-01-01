package com.softwaremagico.librodeesher.pj.culture;

import java.util.Hashtable;

public class CultureDecisions {
	Hashtable<String, Integer> weaponRanks;
	Hashtable<String, Integer> hobbyRanks;
	Hashtable<String, Integer> spellRanks;

	public CultureDecisions() {
		weaponRanks = new Hashtable<>();
		hobbyRanks = new Hashtable<>();
		spellRanks = new Hashtable<>();
	}

	public void setWeaponRanks(String weaponSkill, Integer ranks) {
		if (ranks <= 0) {
			weaponRanks.remove(weaponSkill);
		} else {
			weaponRanks.put(weaponSkill, ranks);
		}
	}

	public Integer getWeaponRanks(String weaponSkill) {
		Integer value = weaponRanks.get(weaponSkill);
		if (value == null) {
			return 0;
		}
		return value;
	}

	public void setHobbyRanks(String hobby, Integer ranks) {
		if (ranks <= 0) {
			hobbyRanks.remove(hobby);
		} else {
			hobbyRanks.put(hobby, ranks);
		}
	}

	public Integer getHobbyRanks(String hobby) {
		Integer value = hobbyRanks.get(hobby);
		if (value == null) {
			return 0;
		}
		return value;
	}

	public void setSpellRanks(String spellList, Integer ranks) {
		if (ranks <= 0) {
			spellRanks.remove(spellList);
		} else {
			spellRanks.put(spellList, ranks);
		}
	}

	public Integer getSpellRanks(String spellList) {
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
