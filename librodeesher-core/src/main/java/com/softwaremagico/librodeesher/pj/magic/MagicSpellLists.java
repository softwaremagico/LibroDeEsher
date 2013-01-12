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

import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.categories.Category;
import com.softwaremagico.librodeesher.pj.categories.CategoryFactory;
import com.softwaremagico.librodeesher.pj.race.RaceFactory;

public class MagicSpellLists {
	private Hashtable<MagicListType, Category> magicCategories; // Spells

	public MagicSpellLists() {
		magicCategories = new Hashtable<>();
		for (MagicListType magicType : MagicListType.values()) {
			createMagicCategories(magicType);
		}
	}

	private void createMagicCategories(MagicListType magicType) {
		Category realCategory = CategoryFactory.getAvailableCategory(magicType.getCategoryName());
		Category category = (Category) CategoryFactory.createCategory(realCategory.getName(), realCategory
				.getAbbreviature(), realCategory.getCharacterisitcsTags(), realCategory.getType().getTag(),
				null);
		magicCategories.put(magicType, category);
	}

	public void orderSpellListsByCategory(CharacterPlayer character) {
		// For each profession, the own profession list are basic lists.
		List<String> basicSpells = new ArrayList<>();
		basicSpells.addAll(MagicFactory.getListOfProfession(character.getProfessionalRealmsOfMagicChoosen()
				.getRealmsOfMagic(), character.getProfession().getName()));
		// Dark spells can be basic lists.
		if (character.isDarkSpellsAsBasicListsAllowed()) {
			List<String> darklistList = MagicFactory.getDarkLists(character
					.getProfessionalRealmsOfMagicChoosen().getRealmsOfMagic());
			if (darklistList != null) {
				basicSpells.addAll(darklistList);
			}
		}
		// For elementalist, the training lists are basic lists.
		List<String> elementalistList = MagicFactory.getListOfProfession(character
				.getProfessionalRealmsOfMagicChoosen().getRealmsOfMagic(), MagicFactory
				.getElementalistTraining(character.getTrainingsNames()));
		if (elementalistList != null) {
			basicSpells.addAll(elementalistList);
		}

		// Open list.
		List<String> openSpells = new ArrayList<>();
		openSpells = MagicFactory.getOpenLists(character.getProfessionalRealmsOfMagicChoosen()
				.getRealmsOfMagic());

		// Close lists
		List<String> closeSpells = new ArrayList<>();
		closeSpells = MagicFactory.getClosedLists(character.getProfessionalRealmsOfMagicChoosen()
				.getRealmsOfMagic());

		// Triad.
		List<String> triadSpells = new ArrayList<>();
		triadSpells = MagicFactory.getListOfOwnTriad(character.getProfessionalRealmsOfMagicChoosen()
				.getRealmsOfMagic(), character.getTrainingsNames());
		List<String> otherTriadSpells = new ArrayList<>();
		otherTriadSpells = MagicFactory.getListOfOtherTriad(character.getProfessionalRealmsOfMagicChoosen()
				.getRealmsOfMagic(), character.getTrainingsNames());

		// Other professions.
		// Only no elementalist has elementalis list as other professions.
		List<String> otherProfession = new ArrayList<>();
		otherProfession = MagicFactory.getListOfOtherProfessions(basicSpells, character
				.getProfessionalRealmsOfMagicChoosen().getRealmsOfMagic(), character.getProfession()
				.getName(), MagicFactory.getElementalistTraining(character.getTrainingsNames()) == null);

		// Other Realms professions.
		List<String> otherRealmsProfession = new ArrayList<>();
		otherRealmsProfession = MagicFactory.getListOfOtherProfessionsOtherRealm(basicSpells, character
				.getProfessionalRealmsOfMagicChoosen().getRealmsOfMagic(), character.getProfession()
				.getName(), MagicFactory.getElementalistTraining(character.getTrainingsNames()) == null);

		// Open list other realm.
		List<String> otherRealmsOpen = new ArrayList<>();
		otherRealmsOpen = MagicFactory.getOtherRealmOpenLists(character.getProfessionalRealmsOfMagicChoosen()
				.getRealmsOfMagic());

		// Close list other realm.
		List<String> otherRealmsClosed = new ArrayList<>();
		otherRealmsClosed = MagicFactory.getOtherRealmClosedLists(character
				.getProfessionalRealmsOfMagicChoosen().getRealmsOfMagic());

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
		List<String> races = RaceFactory.availableRaces();
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

}