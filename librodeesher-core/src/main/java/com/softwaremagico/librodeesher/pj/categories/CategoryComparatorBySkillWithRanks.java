package com.softwaremagico.librodeesher.pj.categories;

import java.util.Comparator;

import com.softwaremagico.librodeesher.pj.CharacterPlayer;

public class CategoryComparatorBySkillWithRanks implements Comparator<String> {
	private CharacterPlayer characterPlayer;

	public CategoryComparatorBySkillWithRanks(CharacterPlayer characterPlayer) {
		this.characterPlayer = characterPlayer;
	}

	public int compare(String category1, String category2) {
		return characterPlayer.getSkillsWithRanks(CategoryFactory.getCategory(category1)).size()
				- characterPlayer.getSkillsWithRanks(CategoryFactory.getCategory(category2)).size();
	}
}