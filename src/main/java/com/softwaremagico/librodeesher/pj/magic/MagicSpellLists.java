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

import java.util.Hashtable;

import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.categories.Category;
import com.softwaremagico.librodeesher.pj.categories.CategoryFactory;

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
		magicCategories.get(MagicListType.BASIC).setSkillsFromName(
				MagicFactory.getListOfProfession(character.getRealmOfMagic(), character.getProfession()
						.getName()));
		magicCategories.get(MagicListType.OPEN).setSkillsFromName(
				MagicFactory.getOpenLists(character.getRealmOfMagic()));
		magicCategories.get(MagicListType.CLOSED).setSkillsFromName(
				MagicFactory.getClosedLists(character.getRealmOfMagic()));
		magicCategories.get(MagicListType.OTHER_PROFESSION).setSkillsFromName(
				MagicFactory.getListOfOtherProfessions(character.getRealmOfMagic(), character.getProfession()
						.getName()));
		magicCategories.get(MagicListType.OTHER_REALM_OTHER_PROFESSION).setSkillsFromName(
				MagicFactory.getListOfOtherProfessionsOtherRealm(character.getRealmOfMagic(), character
						.getProfession().getName()));
		magicCategories.get(MagicListType.OTHER_REALM_OPEN).setSkillsFromName(
				MagicFactory.getOtherRealmOpenLists(character.getRealmOfMagic()));
		magicCategories.get(MagicListType.OTHER_REALM_CLOSED).setSkillsFromName(
				MagicFactory.getOtherRealmClosedLists(character.getRealmOfMagic()));
		magicCategories.get(MagicListType.ARCHANUM).setSkillsFromName(
				MagicFactory.getArchanumOpenLists());
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
