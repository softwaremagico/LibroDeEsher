package com.softwaremagico.librodeesher.pj.categories;

import java.util.Comparator;

import com.softwaremagico.librodeesher.pj.CharacterPlayer;

/**
 * Order a list of categories for characters with low level of specialization.
 */
public class CategoryComparatorBySkillWithLessRanks implements Comparator<String> {
	private CharacterPlayer characterPlayer;

	public CategoryComparatorBySkillWithLessRanks(CharacterPlayer characterPlayer) {
		this.characterPlayer = characterPlayer;
	}

	public int compare(String category1, String category2) {
		return characterPlayer.getSkillsWithRanks(CategoryFactory.getCategory(category2)).size()
				- characterPlayer.getSkillsWithRanks(CategoryFactory.getCategory(category1)).size();
	}
}