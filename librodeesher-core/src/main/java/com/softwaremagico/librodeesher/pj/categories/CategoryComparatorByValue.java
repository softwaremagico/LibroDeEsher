package com.softwaremagico.librodeesher.pj.categories;

import java.util.Comparator;

import com.softwaremagico.librodeesher.pj.CharacterPlayer;

/**
 * Compare two skills by total value
 */
public class CategoryComparatorByValue implements Comparator<Category> {
	private CharacterPlayer characterPlayer;
	
	public CategoryComparatorByValue(CharacterPlayer characterPlayer) {
		this.characterPlayer = characterPlayer;
	}
	
    @Override
	public int compare(Category category1, Category category2) {
    	int categoryValue1 = characterPlayer.getTotalValue(category1) - characterPlayer.getItemBonus(category1);
		int categoryValue2 = characterPlayer.getTotalValue(category2) - characterPlayer.getItemBonus(category2);
		if (categoryValue1 == categoryValue2) {
			return 0;
		}
		return categoryValue1 - categoryValue2;
    }
}
