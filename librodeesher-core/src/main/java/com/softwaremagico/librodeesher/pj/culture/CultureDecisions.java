package com.softwaremagico.librodeesher.pj.culture;
/*
 * #%L
 * Libro de Esher
 * %%
 * Copyright (C) 2007 - 2013 Softwaremagico
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