package com.softwaremagico.librodeesher.pj.categories;

import java.util.Comparator;

import com.softwaremagico.librodeesher.pj.CharacterPlayer;

/**
 * Sort categories by cost.
 */
public class CategoryComparatorByCost implements Comparator<Category> {
	private CharacterPlayer player;

	public CategoryComparatorByCost(CharacterPlayer player) {
		this.player = player;
	}

	@Override
	public int compare(Category category1, Category category2) {
		int costCat1 = getValue(category1);
		int costCat2 = getValue(category2);

		//Spells are too expensive. 
		if (category1.getCategoryGroup().equals(CategoryGroup.SPELL)) {
			if (player.isHybridWizard()) {
				costCat1 -= 4;
			}
			if (player.isWizard()) {
				costCat1 -= 2;
			}
		}
		
		if (category2.getCategoryGroup().equals(CategoryGroup.SPELL)) {
			if (player.isHybridWizard()) {
				costCat2 -= 4;
			}
			if (player.isWizard()) {
				costCat2 -= 2;
			}
		}

		if (costCat1 < costCat2)
			return -1;
		if (costCat1 > costCat2)
			return 1;
		// Equal cost, select one randomly.
		return 0;
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
