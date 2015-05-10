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
import java.util.List;
import java.util.Map;

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
		List<String> basicSpells = new ArrayList<>();
		basicSpells.addAll(MagicFactory.getListOfProfession(character.getRealmOfMagic().getRealmsOfMagic(),
				character.getProfession().getName()));
		// Dark spells can be basic lists.
		if (character.isDarkSpellsAsBasicListsAllowed()) {
			List<String> darklistList = MagicFactory.getDarkLists(character.getRealmOfMagic()
					.getRealmsOfMagic());
			if (darklistList != null) {
				basicSpells.addAll(darklistList);
			}
		}
		// For elementalist, the training lists are basic lists.
		List<String> elementalistList = MagicFactory.getListOfProfession(character.getRealmOfMagic()
				.getRealmsOfMagic(), MagicFactory.getElementalistTraining(character.getSelectedTrainings()));
		if (elementalistList != null) {
			basicSpells.addAll(elementalistList);
		}
		
		// Open list.
		List<String> openSpells = new ArrayList<>();
		openSpells = MagicFactory.getOpenLists(character.getRealmOfMagic().getRealmsOfMagic());

		// Close lists
		List<String> closeSpells = new ArrayList<>();
		closeSpells = MagicFactory.getClosedLists(character.getRealmOfMagic().getRealmsOfMagic());

		// Triad.
		List<String> triadSpells = new ArrayList<>();
		triadSpells = MagicFactory.getListOfOwnTriad(character.getRealmOfMagic().getRealmsOfMagic(),
				character.getSelectedTrainings());
		List<String> otherTriadSpells = new ArrayList<>();
		otherTriadSpells = MagicFactory.getListOfOtherTriad(character.getRealmOfMagic().getRealmsOfMagic(),
				character.getSelectedTrainings());

		// Other professions.
		// Only no elementalist has elementalist list as other professions.
		List<String> otherProfession = new ArrayList<>();
		List<String> notSelectableSpells = new ArrayList<>();
		notSelectableSpells.addAll(basicSpells);
		// List as other triads are not in other profession spell lists. 
		notSelectableSpells.addAll(elementalistList);
		notSelectableSpells.addAll(otherTriadSpells);
		notSelectableSpells.addAll(triadSpells);
		otherProfession = MagicFactory.getListOfOtherProfessions(notSelectableSpells, character.getRealmOfMagic()
				.getRealmsOfMagic(), character.getProfession().getName(), MagicFactory
				.getElementalistTraining(character.getSelectedTrainings()));

		// Other Realms professions.
		List<String> otherRealmsProfession = new ArrayList<>();
		otherRealmsProfession = MagicFactory.getListOfOtherProfessionsOtherRealm(notSelectableSpells, character
				.getRealmOfMagic().getRealmsOfMagic(), character.getProfession().getName(), MagicFactory
				.getElementalistTraining(character.getSelectedTrainings()));

		// Open list other realm.
		List<String> otherRealmsOpen = new ArrayList<>();
		otherRealmsOpen = MagicFactory.getOtherRealmOpenLists(character.getRealmOfMagic().getRealmsOfMagic());

		// Close list other realm.
		List<String> otherRealmsClosed = new ArrayList<>();
		otherRealmsClosed = MagicFactory.getOtherRealmClosedLists(character.getRealmOfMagic()
				.getRealmsOfMagic());

		// Archanum Open lists
		List<String> archanumOpenLists = new ArrayList<>();
		archanumOpenLists = MagicFactory.getArchanumOpenLists();

		// Race lists
		List<String> raceLists = new ArrayList<>();
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
				List<String> otherRaceLists = MagicFactory.getRaceLists(otherRace);
				if (otherRaceLists != null) {
					otherRealmsProfession.addAll(otherRaceLists);
				}
			}
		}
		
		// Store lists.
		magicCategories.get(MagicListType.BASIC).setSkillsFromName(basicSpells);
		magicCategories.get(MagicListType.OPEN).setSkillsFromName(openSpells);
		magicCategories.get(MagicListType.CLOSED).setSkillsFromName(closeSpells);
		magicCategories.get(MagicListType.TRIAD).setSkillsFromName(triadSpells);
		magicCategories.get(MagicListType.COMPLEMENTARY_TRIAD).setSkillsFromName(otherTriadSpells);
		magicCategories.get(MagicListType.OTHER_PROFESSION).setSkillsFromName(otherProfession);
		magicCategories.get(MagicListType.OTHER_REALM_OTHER_PROFESSION).setSkillsFromName(
				otherRealmsProfession);
		magicCategories.get(MagicListType.OTHER_REALM_OPEN).setSkillsFromName(otherRealmsOpen);
		magicCategories.get(MagicListType.OTHER_REALM_CLOSED).setSkillsFromName(otherRealmsClosed);
		magicCategories.get(MagicListType.ARCHANUM).setSkillsFromName(archanumOpenLists);
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
