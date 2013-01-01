package com.softwaremagico.librodeesher.pj.magic;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Pattern;

import com.softwaremagico.files.RolemasterFolderStructure;
import com.softwaremagico.librodeesher.gui.ShowMessage;
import com.softwaremagico.librodeesher.pj.profession.ProfessionFactory;

public class MagicFactory {
	private static final String OPEN_LIST_TAG = "Lista Abierta";
	private static final String CLOSED_LIST_TAG = "Lista Cerrada";

	private static Hashtable<RealmOfMagic, Hashtable<String, List<String>>> spellsByGroup;

	static {
		spellsByGroup = new Hashtable<>();
		for (RealmOfMagic realms : RealmOfMagic.values()) {
			spellsByGroup.put(realms, new Hashtable<String, List<String>>());
		}
		readSpellsFromFiles();
	}

	public static List<String> getListOfProfession(RealmOfMagic realm, String profession) {
		List<String> spells = spellsByGroup.get(realm).get(profession);
		if (spells == null) {
			// No spells found. If it has only one realm it is a wizard and must
			// have spells.
			if (!profession.equals(OPEN_LIST_TAG) && !profession.equals(CLOSED_LIST_TAG)
					&& ProfessionFactory.getProfession(profession).getMagicRealmsAvailable().size() < 2) {
				ShowMessage
						.showErrorMessage("No existen listas de hechizos para " + profession, "Leer Magia");
			}
		}
		return spells;
	}

	public static List<String> getListOfOtherProfessions(RealmOfMagic realm, String currentProfession) {
		List<String> lists = new ArrayList<String>();
		List<String> ownProfessionLists = getListOfProfession(realm, currentProfession);
		for (String profession : spellsByGroup.get(realm).keySet()) {
			if (!profession.equals(OPEN_LIST_TAG) && !profession.equals(CLOSED_LIST_TAG)
					&& !profession.equals(currentProfession)) {
				List<String> otherProfessionList = spellsByGroup.get(realm).get(profession);
				// Avoid to add list shared with other professions.
				for (String spellList : otherProfessionList) {
					if (ownProfessionLists == null || !ownProfessionLists.contains(spellList)) {
						lists.add(spellList);
					}
				}
			}
		}
		return lists;
	}

	public static List<String> getListOfOtherProfessionsOtherRealm(RealmOfMagic realm,
			String currentProfession) {
		List<String> lists = new ArrayList<String>();
		List<String> ownProfessionLists = getListOfProfession(realm, currentProfession);
		try {
			for (RealmOfMagic otherRealm : RealmOfMagic.values()) {
				if (!otherRealm.equals(realm)) {
					for (String profession : spellsByGroup.get(realm).keySet()) {
						if (!profession.equals(OPEN_LIST_TAG) && !profession.equals(CLOSED_LIST_TAG)
								&& !profession.equals(currentProfession)) {
							List<String> otherProfessionList = spellsByGroup.get(realm).get(profession);
							// Avoid to add list shared with other professions.
							for (String spellList : otherProfessionList) {
								if (ownProfessionLists == null || !ownProfessionLists.contains(spellList)) {
									lists.add(spellList);
								}
							}
						}
					}
				}
			}
		} catch (NullPointerException npe) {
		}
		return lists;
	}

	public static List<String> getOpenLists(RealmOfMagic realm) {
		return getListOfProfession(realm, OPEN_LIST_TAG);
	}
	
	public static List<String> getArchanumOpenLists() {
		return getListOfProfession(RealmOfMagic.ARCHANUM, OPEN_LIST_TAG);
	}

	public static List<String> getOtherRealmOpenLists(RealmOfMagic realm) {
		List<String> lists = new ArrayList<String>();
		for (RealmOfMagic otherRealm : RealmOfMagic.values()) {
			if (!otherRealm.equals(realm)) {
				List<String> newLists = getListOfProfession(otherRealm, OPEN_LIST_TAG);
				if (newLists != null) {
					lists.addAll(newLists);
				}
			}
		}
		return lists;
	}

	public static List<String> getClosedLists(RealmOfMagic realm) {
		return getListOfProfession(realm, CLOSED_LIST_TAG);
	}

	public static List<String> getOtherRealmClosedLists(RealmOfMagic realm) {
		List<String> lists = new ArrayList<String>();
		for (RealmOfMagic otherRealm : RealmOfMagic.values()) {
			if (!otherRealm.equals(realm)) {
				List<String> newLists = getListOfProfession(otherRealm, CLOSED_LIST_TAG);
				if (newLists != null) {
					lists.addAll(newLists);
				}
			}
		}
		return lists;
	}

	private static void getSpellsFromLines(List<String> lines, RealmOfMagic realm) {
		try {
			for (int i = 0; i < lines.size(); i++) {
				String spellLine = lines.get(i);
				if (!spellLine.equals("")) {
					String[] spellColumns = spellLine.split("\t");
					String spellName = spellColumns[0];
					String professionName = spellColumns[1];
					// More than one profession can have a list.
					String[] professions = professionName.split(Pattern.quote("/"));
					for (String oneProfession : professions) {
						List<String> spellLists = spellsByGroup.get(realm).get(oneProfession);
						if (spellLists == null) {
							spellLists = new ArrayList<>();
							spellsByGroup.get(realm).put(oneProfession, spellLists);
						}
						if (!spellLists.contains(spellName)) {
							spellLists.add(spellName);
						}
					}
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

}
