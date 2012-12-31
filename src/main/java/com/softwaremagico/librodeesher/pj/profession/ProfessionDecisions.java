package com.softwaremagico.librodeesher.pj.profession;

import java.util.Hashtable;

import com.softwaremagico.librodeesher.pj.categories.Category;
import com.softwaremagico.librodeesher.pj.categories.CategoryCost;

public class ProfessionDecisions {
	private Hashtable<Category, CategoryCost> weaponsCost;

	public ProfessionDecisions() {
		weaponsCost = new Hashtable<>();
	}

	public void setWeaponCost(Category category, CategoryCost cost) {
		weaponsCost.put(category, cost);
	}

	public CategoryCost getWeaponCost(Category category) {
		return weaponsCost.get(category);
	}
}
