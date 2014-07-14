package com.softwaremagico.librodeesher.pj.random;

import java.util.Comparator;

import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.categories.Category;

/**
 * Sort categories by cost.
 */
public class SortCategoryByCost implements Comparator<Category> {
	private CharacterPlayer player;

	public SortCategoryByCost(CharacterPlayer player) {
		this.player = player;
	}

	@Override
	public int compare(Category category1, Category category2) {
		int costCat1 = getValue(category1);
		int costCat2 = getValue(category2);

		if (costCat1 < costCat2)
			return -1;
		if (costCat1 > costCat2)
			return 1;
		// Equal cost, select one randomly.
		return Math.random() > 0.5 ? 1 : -1;
	}

	private int getValue(Category category) {
		int mod;
		switch (category.getCategoryGroup()) {
		case SPELL:
			mod = -3;
			break;
		default:
			mod = 0;
		}
		return player.getNewRankCost(category) + mod;
	}

}
