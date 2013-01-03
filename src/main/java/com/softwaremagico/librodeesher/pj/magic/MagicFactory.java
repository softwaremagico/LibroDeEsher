package com.softwaremagico.librodeesher.pj.magic;

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
	private static final String ESSENCE_DARK_LIST_TAG = "Esencia Maligna";
	private static final String CANALIZATION_DARK_LIST_TAG = "Canalización Maligna";
	private static final String MENTALISM_DARK_LIST_TAG = "Mentalismo Maligno";
	private static final String ELEMENTALIST_PROFESSION = "Elementalista";
	private static final String ELEMENTALIST_INITIAL_TAG = "mago de";

	private static Hashtable<RealmOfMagic, Hashtable<String, List<String>>> spellsByGroup;

	static {
		spellsByGroup = new Hashtable<>();
		for (RealmOfMagic realms : RealmOfMagic.values()) {
			spellsByGroup.put(realms, new Hashtable<String, List<String>>());
		}
		readSpellsFromFiles();
	}

	public static List<String> getListOfProfession(List<RealmOfMagic> realmsOfMagic, String profession) {
		if (profession == null) {
			return null;
		}

		List<String> allRealmSpells = new ArrayList<>();
		List<String> spells = null;
		// Add all spells of all realms if it is a hybrid wizard.
		for (RealmOfMagic realm : realmsOfMagic) {
			spells = spellsByGroup.get(realm).get(profession);

			if (spells == null) {
				// No spells found. If it has only one realm it is a wizard and
				// must
				// have spells.
				if (!profession.equals(OPEN_LIST_TAG) && !profession.equals(CLOSED_LIST_TAG)
						&& !profession.equals(ELEMENTALIST_PROFESSION)
						&& ProfessionFactory.getProfession(profession).getMagicRealmsAvailable().size() < 2) {
					ShowMessage.showErrorMessage("No existen listas de hechizos para " + profession,
							"Leer Magia");
				}
			} else {
				allRealmSpells.addAll(spells);
			}
		}
		return allRealmSpells;
	}

	public static List<String> getListOfOtherProfessions(List<String> ownProfessionLists,
			List<RealmOfMagic> realmsOfMagic, String currentProfession, boolean includeElementalist) {
		List<String> lists = new ArrayList<String>();
		for (RealmOfMagic realm : realmsOfMagic) {
			for (String profession : spellsByGroup.get(realm).keySet()) {
				if (!profession.equals(OPEN_LIST_TAG) && !profession.equals(CLOSED_LIST_TAG)
						&& !profession.equals(currentProfession)
						// It is a profession, not a training (except
						// elementalist).
						&& (ProfessionFactory.availableProfessions().contains(profession)
								|| (includeElementalist && isElementalistTraining(profession)) || profession
									.equals(getDarkSpellTag(realm)))) {
					List<String> otherProfessionList = spellsByGroup.get(realm).get(profession);
					// Avoid to add basic list shared with other professions or
					// dark list if are considered as basic.
					for (String spellList : otherProfessionList) {
						if (ownProfessionLists == null || !ownProfessionLists.contains(spellList)) {
							lists.add(spellList);
						}
					}
				}
			}
		}
		return lists;
	}

	public static List<String> getListOfOtherProfessionsOtherRealm(List<String> ownProfessionLists,
			List<RealmOfMagic> realmsOfMagic, String currentProfession, boolean includeElementalist) {
		List<String> lists = new ArrayList<String>();
		try {
			for (RealmOfMagic otherRealm : RealmOfMagic.values()) {
				if (!realmsOfMagic.contains(otherRealm)) {
					for (String profession : spellsByGroup.get(otherRealm).keySet()) {
						if (!profession.equals(OPEN_LIST_TAG)
								&& !profession.equals(CLOSED_LIST_TAG)
								// It is a profession, not a training
								&& (ProfessionFactory.availableProfessions().contains(profession)
										|| (includeElementalist && isElementalistTraining(profession)) || profession
											.equals(getDarkSpellTag(otherRealm)))) {
							List<String> otherProfessionList = spellsByGroup.get(otherRealm).get(
									profession);
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
			npe.printStackTrace();
		}
		return lists;
	}

	public static List<String> getListOfOwnTriad(List<RealmOfMagic> realmsOfMagic, List<String> trainings) {
		List<String> lists = new ArrayList<String>();
		String trainingName = getElementalistTraining(trainings);
		if (trainingName != null) {
			String[] words = trainingName.split(" ");
			Element element = Element.getElement(words[words.length - 1].toLowerCase());
			// Get other trainings spells
			List<String> otherTrainings = Triad.getSameTriadTrainings(element, trainingName.toLowerCase());
			for (String otherTraining : otherTrainings) {
				lists.addAll(getListOfProfession(realmsOfMagic, otherTraining));
			}
		}
		return lists;
	}

	public static List<String> getListOfOtherTriad(List<RealmOfMagic> realmsOfMagic, List<String> trainings) {
		List<String> lists = new ArrayList<String>();
		String trainingName = getElementalistTraining(trainings);
		if (trainingName != null) {
			String[] words = trainingName.split(" ");
			Element element = Element.getElement(words[words.length - 1].toLowerCase());
			// Get other trainings spells
			List<String> otherTrainings = Triad.getOtherTriadTrainings(element, trainingName.toLowerCase());
			for (String otherTraining : otherTrainings) {
				lists.addAll(getListOfProfession(realmsOfMagic, otherTraining));
			}
		}
		return lists;
	}

	public static boolean isElementalistTraining(String trainingName) {
		return trainingName.toLowerCase().contains(ELEMENTALIST_INITIAL_TAG);
	}

	public static String getElementalistTraining(List<String> trainings) {
		for (String trainingName : trainings) {
			if (isElementalistTraining(trainingName)) {
				return trainingName;
			}
		}
		return null;
	}

	private static String getDarkSpellTag(RealmOfMagic realm) {
		switch (realm) {
		case ESSENCE:
			return ESSENCE_DARK_LIST_TAG;
		case CANALIZATION:
			return CANALIZATION_DARK_LIST_TAG;
		case MENTALISM:
			return MENTALISM_DARK_LIST_TAG;
		default:
			return "";
		}
	}

	public static List<String> getDarkLists(List<RealmOfMagic> realmsOfMagic) {
		List<String> result = new ArrayList<>();
		for (RealmOfMagic realm : realmsOfMagic) {
			List<RealmOfMagic> newRealms = new ArrayList<>();
			newRealms.add(realm);
			List<String> lists = getListOfProfession(newRealms, getDarkSpellTag(realm));
			if (lists != null) {
				result.addAll(lists);
			}
		}
		return result;
	}

	public static List<String> getOpenLists(List<RealmOfMagic> realmsOfMagic) {
		return getListOfProfession(realmsOfMagic, OPEN_LIST_TAG);
	}

	public static List<String> getArchanumOpenLists() {
		List<RealmOfMagic> realmsOfMagic = new ArrayList<RealmOfMagic>();
		realmsOfMagic.add(RealmOfMagic.ARCHANUM);
		return getListOfProfession(realmsOfMagic, OPEN_LIST_TAG);
	}

	public static List<String> getOtherRealmOpenLists(List<RealmOfMagic> realmsOfMagic) {
		List<RealmOfMagic> otherRealms = getOtherRealms(realmsOfMagic);
		List<String> lists = getListOfProfession(otherRealms, OPEN_LIST_TAG);
		return lists;
	}

	public static List<String> getClosedLists(List<RealmOfMagic> realms) {
		return getListOfProfession(realms, CLOSED_LIST_TAG);
	}

	public static List<String> getOtherRealmClosedLists(List<RealmOfMagic> realmsOfMagic) {
		List<RealmOfMagic> otherRealms = getOtherRealms(realmsOfMagic);
		List<String> lists = getListOfProfession(otherRealms, CLOSED_LIST_TAG);
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

	private static List<RealmOfMagic> getOtherRealms(List<RealmOfMagic> realmsOfMagic) {
		List<RealmOfMagic> otherRealms = new ArrayList<>();
		for (RealmOfMagic realm : RealmOfMagic.values()) {
			if (!realmsOfMagic.contains(realm) && !realm.equals(RealmOfMagic.ARCHANUM)) {
				otherRealms.add(realm);
			}
		}
		return otherRealms;
	}

}
