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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.categories.Category;
import com.softwaremagico.librodeesher.pj.categories.CategoryFactory;
import com.softwaremagico.librodeesher.pj.profession.InvalidProfessionException;
import com.softwaremagico.librodeesher.pj.race.RaceFactory;

public class MagicSpellLists implements Serializable {
	private static final long serialVersionUID = 1353935959992023640L;
	private Map<MagicListType, Category> magicCategories; // Spells

	public MagicSpellLists() {
		magicCategories = new HashMap<>();
		for (MagicListType magicType : MagicListType.values()) {
			createMagicCategories(magicType);
		}
	}

	private void createMagicCategories(MagicListType magicType) {
		Category realCategory = CategoryFactory.getCategory(magicType.getCategoryName());
		Category category = (Category) CategoryFactory.createCategory(realCategory.getName(), realCategory
				.getAbbreviature(), realCategory.getCharacterisitcsTags(), realCategory.getCategoryType()
				.getTag(), null);
		magicCategories.put(magicType, category);
	}

	public void orderSpellListsByCategory(CharacterPlayer character) throws MagicDefinitionException,
			InvalidProfessionException {
		// For each profession, the own profession list are basic lists.
		Set<String> basicSpells = new HashSet<>();
		basicSpells.addAll(MagicFactory.getListOfProfession(character.getRealmOfMagic().getRealmsOfMagic(),
				character.getProfession().getName()));
		// Dark spells can be basic lists.
		if (character.isDarkSpellsAsBasicListsAllowed()) {
			Set<String> darklistList = MagicFactory.getDarkLists(character.getRealmOfMagic()
					.getRealmsOfMagic());
			if (darklistList != null) {
				basicSpells.addAll(darklistList);
			}
		}
		// For elementalist, the training lists are basic lists.
		Set<String> elementalistList = MagicFactory.getListOfProfession(character.getRealmOfMagic()
				.getRealmsOfMagic(), MagicFactory.getElementalistTraining(character.getSelectedTrainings()));
		if (elementalistList != null) {
			basicSpells.addAll(elementalistList);
		}

		// Open list.
		Set<String> openSpells = new HashSet<>();
		openSpells = MagicFactory.getOpenLists(character.getRealmOfMagic().getRealmsOfMagic());

		// Close lists
		Set<String> closeSpells = new HashSet<>();
		closeSpells = MagicFactory.getClosedLists(character.getRealmOfMagic().getRealmsOfMagic());

		// List as other triads are not in other profession spell lists.
		Set<String> notSelectableSpells = new HashSet<>();
		notSelectableSpells.addAll(basicSpells);
		notSelectableSpells.addAll(elementalistList);
		notSelectableSpells.addAll(closeSpells);
		notSelectableSpells.addAll(openSpells);

		// Triad.
		Set<String> triadSpells = new HashSet<>();
		triadSpells = MagicFactory.getListOfOwnTriad(character.getRealmOfMagic().getRealmsOfMagic(),
				character.getSelectedTrainings());
		triadSpells.removeAll(notSelectableSpells);
		notSelectableSpells.addAll(triadSpells);

		Set<String> otherTriadSpells = new HashSet<>();
		otherTriadSpells = MagicFactory.getListOfOtherTriad(character.getRealmOfMagic().getRealmsOfMagic(),
				character.getSelectedTrainings());
		otherTriadSpells.removeAll(notSelectableSpells);
		notSelectableSpells.addAll(otherTriadSpells);

		// Training
		Set<String> trainingSpells = new HashSet<>();
		for (String training : character.getTrainings()) {
			trainingSpells.addAll(MagicFactory.getListOfTraining(character.getRealmOfMagic()
					.getRealmsOfMagic(), training));
		}
		notSelectableSpells.addAll(trainingSpells);
		
		// Other realm Training
		Set<String> otherRealmTrainingSpells = new HashSet<>();
		for (String training : character.getTrainings()) {
			otherRealmTrainingSpells.addAll(MagicFactory.getListOfTrainingOtherRealms(character.getRealmOfMagic()
					.getRealmsOfMagic(), training));
		}
		notSelectableSpells.addAll(otherRealmTrainingSpells);

		// Other professions.
		// Only no elementalist has elementalist list as other professions.
		Set<String> otherProfession = new HashSet<>();
		otherProfession = MagicFactory.getListOfOtherProfessions(notSelectableSpells, character
				.getRealmOfMagic().getRealmsOfMagic(), character.getProfession().getName());
		otherProfession.removeAll(notSelectableSpells);
		notSelectableSpells.addAll(otherProfession);

		// Other Realms professions.
		Set<String> otherRealmsProfession = new HashSet<>();
		otherRealmsProfession = MagicFactory.getListOfOtherProfessionsOtherRealm(notSelectableSpells,
				character.getRealmOfMagic().getRealmsOfMagic(), character.getProfession().getName(),
				MagicFactory.getElementalistTraining(character.getSelectedTrainings()));
		otherRealmsProfession.removeAll(notSelectableSpells);
		notSelectableSpells.addAll(otherRealmsProfession);

		// Open list other realm.
		Set<String> otherRealmsOpen = new HashSet<>();
		otherRealmsOpen = MagicFactory.getOtherRealmOpenLists(character.getRealmOfMagic().getRealmsOfMagic());
		otherRealmsOpen.removeAll(notSelectableSpells);
		notSelectableSpells.addAll(otherRealmsOpen);

		// Close list other realm.
		Set<String> otherRealmsClosed = new HashSet<>();
		otherRealmsClosed = MagicFactory.getOtherRealmClosedLists(character.getRealmOfMagic()
				.getRealmsOfMagic());

		// Archanum Open lists
		Set<String> archanumOpenLists = new HashSet<>();
		archanumOpenLists = MagicFactory.getArchanumOpenLists();

		// Race lists
		Set<String> raceLists = new HashSet<>();
		raceLists = MagicFactory.getRaceLists(character.getRace().getName());
		// Race lists are basic lists if character is a spell caster or open
		// list if not.
		if (raceLists != null) {
			if (basicSpells.size() > 0) {
				basicSpells.addAll(raceLists);
			} else {
				openSpells.addAll(raceLists);
			}
		}
		// Other race lists are considered other profession of other realms.
		List<String> races = RaceFactory.getAvailableRaces();
		for (String otherRace : races) {
			if (!otherRace.equals(character.getRace().getName())) {
				Set<String> otherRaceLists = MagicFactory.getRaceLists(otherRace);
				if (otherRaceLists != null) {
					otherRealmsProfession.addAll(otherRaceLists);
				}
			}
		}

		// Store lists.
		magicCategories.get(MagicListType.BASIC).setSkillsFromName(new ArrayList<>(basicSpells));
		magicCategories.get(MagicListType.OPEN).setSkillsFromName(new ArrayList<>(openSpells));
		magicCategories.get(MagicListType.CLOSED).setSkillsFromName(new ArrayList<>(closeSpells));
		magicCategories.get(MagicListType.TRIAD).setSkillsFromName(new ArrayList<>(triadSpells));
		magicCategories.get(MagicListType.COMPLEMENTARY_TRIAD).setSkillsFromName(
				new ArrayList<>(otherTriadSpells));
		magicCategories.get(MagicListType.OTHER_PROFESSION).setSkillsFromName(
				new ArrayList<>(otherProfession));
		magicCategories.get(MagicListType.OTHER_REALM_OTHER_PROFESSION).setSkillsFromName(
				new ArrayList<>(otherRealmsProfession));
		magicCategories.get(MagicListType.OTHER_REALM_OPEN).setSkillsFromName(
				new ArrayList<>(otherRealmsOpen));
		magicCategories.get(MagicListType.OTHER_REALM_CLOSED).setSkillsFromName(
				new ArrayList<>(otherRealmsClosed));
		magicCategories.get(MagicListType.ARCHANUM).setSkillsFromName(new ArrayList<>(archanumOpenLists));
		magicCategories.get(MagicListType.TRAINING).setSkillsFromName(new ArrayList<>(trainingSpells));
		magicCategories.get(MagicListType.OTHER_REALM_TRAINING).setSkillsFromName(new ArrayList<>(otherRealmTrainingSpells));
	}

	public Category getMagicCategory(String categoryName) {
		for (Category category : magicCategories.values()) {
			if (category.getName().equals(categoryName)) {
				return category;
			}
		}
		return null;
	}

	protected Map<MagicListType, Category> getMagicCategories() {
		return magicCategories;
	}

	protected void setMagicCategories(Map<MagicListType, Category> magicCategories) {
		this.magicCategories = magicCategories;
	}

}
