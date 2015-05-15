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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import com.softwaremagico.files.RolemasterFolderStructure;
import com.softwaremagico.librodeesher.basics.Spanish;
import com.softwaremagico.librodeesher.pj.profession.InvalidProfessionException;
import com.softwaremagico.librodeesher.pj.profession.ProfessionFactory;

public class MagicFactory {

	private static HashMap<RealmOfMagic, HashMap<String, Set<String>>> spellsByGroup;

	static {
		spellsByGroup = new HashMap<>();
		for (RealmOfMagic realms : RealmOfMagic.values()) {
			spellsByGroup.put(realms, new HashMap<String, Set<String>>());
		}
		readSpellsFromFiles();
	}

	public static Set<String> getListOfProfession(List<RealmOfMagic> realmsOfMagic, String profession)
			throws MagicDefinitionException, InvalidProfessionException {
		Set<String> allRealmSpells = new HashSet<>();
		if (profession == null) {
			return allRealmSpells;
		}

		// Add all spells of all realms if it is a hybrid wizard.
		for (RealmOfMagic realm : realmsOfMagic) {
			Set<String> spells = spellsByGroup.get(realm).get(profession.toLowerCase());

			if (spells == null) {
				// No spells found. If it has only one realm it is a wizard and
				// must have spells.
				if (!profession.toLowerCase().startsWith(Spanish.ELEMENTALIST_INITIAL_TAG)
						&& !profession.equals(Spanish.OPEN_LIST_TAG)
						&& !profession.equals(Spanish.CLOSED_LIST_TAG)
						&& !profession.toLowerCase().equals(Spanish.ELEMENTALIST_PROFESSION)
						&& ProfessionFactory.getProfession(profession).getMagicRealmsAvailable().size() < 2) {
					throw new MagicDefinitionException("No existen listas de hechizos para " + profession);
				}
			} else {
				allRealmSpells.addAll(spells);
			}
		}
		return allRealmSpells;
	}

	public static Set<String> getListOfOtherProfessions(Set<String> ownProfessionLists,
			List<RealmOfMagic> realmsOfMagic, String currentProfession) {
		Set<String> lists = new HashSet<String>();
		for (RealmOfMagic realm : realmsOfMagic) {
			for (String profession : spellsByGroup.get(realm).keySet()) {
				if (!profession.equals(Spanish.OPEN_LIST_TAG) && !profession.equals(Spanish.CLOSED_LIST_TAG)
						&& !profession.equals(currentProfession)) {
					// It is a profession, not a training (except elementalist).
					if (((ProfessionFactory.getAvailableProfessions().contains(profession)
							|| profession.equals(getDarkSpellTag(realm)) || isElementalistTraining(profession)))) {
						Set<String> otherProfessionList = spellsByGroup.get(realm).get(
								profession.toLowerCase());
						// Avoid to add basic list shared with other professions
						// or dark list if are considered as basic.
						for (String spellList : otherProfessionList) {
							if (ownProfessionLists == null || !ownProfessionLists.contains(spellList)) {
								lists.add(spellList);
							}
						}
					}
				}
			}
		}
		return lists;
	}

	public static Set<String> getListOfOtherProfessionsOtherRealm(Set<String> ownProfessionLists,
			List<RealmOfMagic> realmsOfMagic, String currentProfession, String includeElementalist) {
		Set<String> lists = new HashSet<>();
		try {
			for (RealmOfMagic otherRealm : RealmOfMagic.values()) {
				if (!realmsOfMagic.contains(otherRealm)) {
					for (String profession : spellsByGroup.get(otherRealm).keySet()) {
						if (!profession.equals(Spanish.OPEN_LIST_TAG.toLowerCase())
								&& !profession.equals(Spanish.CLOSED_LIST_TAG.toLowerCase())
								// It is a profession, not a training
								&& (ProfessionFactory.getAvailableProfessions().contains(profession)
										|| (includeElementalist != null && isElementalistTraining(includeElementalist)) || profession
											.equals(getDarkSpellTag(otherRealm)))) {
							Set<String> otherProfessionList = spellsByGroup.get(otherRealm).get(
									profession.toLowerCase());
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

	public static Set<String> getListOfOwnTriad(List<RealmOfMagic> realmsOfMagic, List<String> trainings)
			throws MagicDefinitionException, InvalidProfessionException {
		Set<String> lists = new HashSet<String>();
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

	public static Set<String> getListOfOtherTriad(List<RealmOfMagic> realmsOfMagic, List<String> trainings)
			throws MagicDefinitionException, InvalidProfessionException {
		Set<String> lists = new HashSet<String>();
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
		return trainingName.toLowerCase().startsWith(Spanish.ELEMENTALIST_INITIAL_TAG);
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
			return Spanish.ESSENCE_DARK_LIST_TAG;
		case CANALIZATION:
			return Spanish.CANALIZATION_DARK_LIST_TAG;
		case MENTALISM:
			return Spanish.MENTALISM_DARK_LIST_TAG;
		default:
			return "";
		}
	}

	public static Set<String> getDarkLists(List<RealmOfMagic> realmsOfMagic)
			throws MagicDefinitionException, InvalidProfessionException {
		Set<String> result = new HashSet<>();
		for (RealmOfMagic realm : realmsOfMagic) {
			List<RealmOfMagic> newRealms = new ArrayList<>();
			newRealms.add(realm);
			Set<String> lists = getListOfProfession(newRealms, getDarkSpellTag(realm));
			if (lists != null) {
				result.addAll(lists);
			}
		}
		return result;
	}

	public static Set<String> getOpenLists(List<RealmOfMagic> realmsOfMagic)
			throws MagicDefinitionException, InvalidProfessionException {
		return getListOfProfession(realmsOfMagic, Spanish.OPEN_LIST_TAG);
	}

	public static Set<String> getRaceLists(String race) {
		Set<String> spells = spellsByGroup.get(RealmOfMagic.RACE).get(race);
		if (spells == null) {
			spells = new HashSet<>();
		}
		return spells;
	}

	public static Set<String> getArchanumOpenLists() throws MagicDefinitionException,
			InvalidProfessionException {
		List<RealmOfMagic> realmsOfMagic = new ArrayList<RealmOfMagic>();
		realmsOfMagic.add(RealmOfMagic.ARCHANUM);
		return getListOfProfession(realmsOfMagic, Spanish.OPEN_LIST_TAG);
	}

	public static Set<String> getOtherRealmOpenLists(List<RealmOfMagic> realmsOfMagic)
			throws MagicDefinitionException, InvalidProfessionException {
		List<RealmOfMagic> otherRealms = getOtherRealms(realmsOfMagic);
		Set<String> lists = getListOfProfession(otherRealms, Spanish.OPEN_LIST_TAG);
		return lists;
	}

	public static Set<String> getClosedLists(List<RealmOfMagic> realms) throws MagicDefinitionException,
			InvalidProfessionException {
		return getListOfProfession(realms, Spanish.CLOSED_LIST_TAG);
	}

	public static Set<String> getOtherRealmClosedLists(List<RealmOfMagic> realmsOfMagic)
			throws MagicDefinitionException, InvalidProfessionException {
		List<RealmOfMagic> otherRealms = getOtherRealms(realmsOfMagic);
		Set<String> lists = getListOfProfession(otherRealms, Spanish.CLOSED_LIST_TAG);
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
						Set<String> spellLists = spellsByGroup.get(realm).get(oneProfession.toLowerCase());
						if (spellLists == null) {
							spellLists = new HashSet<>();
							spellsByGroup.get(realm).put(oneProfession.toLowerCase(), spellLists);
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
		for (RealmOfMagic realm : RealmOfMagic.values()) {
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
