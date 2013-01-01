package com.softwaremagico.librodeesher.pj.magic;

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
