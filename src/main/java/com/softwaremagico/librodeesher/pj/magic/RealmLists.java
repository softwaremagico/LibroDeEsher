package com.softwaremagico.librodeesher.pj.magic;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class RealmLists {
	private RealmOfMagic realm;
	private Hashtable<String, List<SpellList>> spellsPerType;

	public RealmLists(RealmOfMagic realms) {
		this.realm = realms;
		spellsPerType = new Hashtable<>();
	}

	public void addSpell(SpellList spell) {
		for (String professionsWithThisList : spell.getProfessions()) {
			List<SpellList> spells = spellsPerType.get(professionsWithThisList);
			if (spells == null) {
				spells = new ArrayList<>();
				spellsPerType.put(professionsWithThisList, new ArrayList<SpellList>());
			}
			spellsPerType.get(professionsWithThisList).add(spell);
		}
	}

	public List<SpellList> getSpellsOfType(String profession) {
		return spellsPerType.get(profession);
	}

	public SpellList getSpell(String spellListName) {
		for (List<SpellList> spellLists : spellsPerType.values()) {
			for (SpellList spellList : spellLists) {
				if (spellList.getName().equals(spellListName)) {
					return spellList;
				}
			}
		}
		return null;
	}
}
