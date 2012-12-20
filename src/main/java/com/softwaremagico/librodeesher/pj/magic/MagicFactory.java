package com.softwaremagico.librodeesher.pj.magic;

import java.util.Hashtable;
import java.util.List;

import com.softwaremagico.files.RolemasterFolderStructure;

public class MagicFactory {
	private static Hashtable<RealmOfMagic, RealmLists> spellsByRealms;

	public static RealmLists getListOfRealm(RealmOfMagic realm) {
		return spellsByRealms.get(realm);
	}

	public static List<SpellList> getListOfProfession(RealmOfMagic realm, String profession) {
		return spellsByRealms.get(realm).getSpellsOfType(profession);
	}

	static {
		spellsByRealms = new Hashtable<>();
		for (RealmOfMagic realms : RealmOfMagic.values()) {
			spellsByRealms.put(realms, new RealmLists(realms));
		}
		readSpellsFromFiles();
	}

	private static void addSpell(SpellList spell) {
		spellsByRealms.get(spell.getRealm()).addSpell(spell);
	}

	private static void getSpellsFromLines(List<String> lines, RealmOfMagic realm) {
		try {
			for (int i = 0; i < lines.size(); i++) {
				String spellLine = lines.get(i);
				if (!spellLine.equals("")) {
					String[] spellColumns = spellLine.split("\t");
					String spellName = spellColumns[0];
					String professionName = spellColumns[1];
					addSpell(new SpellList(spellName, professionName, realm));
				}
			}
		} catch (IndexOutOfBoundsException iobe) {
			iobe.printStackTrace();
		}
	}

	private static void readSpellsFromFiles() {
		RealmOfMagic[] realms = RealmOfMagic.values();

		for (RealmOfMagic realm : realms) {
			List<String> lines = RolemasterFolderStructure.getSpellLines(realm.getName() + ".txt");
			getSpellsFromLines(lines, realm);
		}
	}
	
	public SpellList getSpell(String spellListName, RealmOfMagic realm){
		return spellsByRealms.get(realm).getSpell(spellListName);
	}

}
