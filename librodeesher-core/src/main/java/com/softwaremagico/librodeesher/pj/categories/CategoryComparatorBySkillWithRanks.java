package com.softwaremagico.librodeesher.pj.categories;

import java.util.Comparator;

import com.softwaremagico.librodeesher.pj.CharacterPlayer;

/**
 * Order a list of categories for characters with high level of specialization. If equals, order randomly.
 */
public class CategoryComparatorBySkillWithRanks implements Comparator<String> {
	private CharacterPlayer characterPlayer;

	public CategoryComparatorBySkillWithRanks(CharacterPlayer characterPlayer) {
		this.characterPlayer = characterPlayer;
	}

	public int compare(String category1, String category2) {
		int skillsIn1 = characterPlayer.getSkillsWithRanks(CategoryFactory.getCategory(category1)).size();
		int skillsIn2 = characterPlayer.getSkillsWithRanks(CategoryFactory.getCategory(category2)).size();
		if (skillsIn1 == skillsIn2) {
			// Return -1, 0 or 1 randomly.
			return (int) (Math.random() * 3 + 1) - 2;
		}
		return skillsIn1 - skillsIn2;
	}
}