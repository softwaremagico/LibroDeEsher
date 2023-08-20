package com.softwaremagico.librodeesher.pj.perk;

import java.util.Comparator;

public class PerkComparatorByCost implements Comparator<Perk> {

	@Override
	public int compare(Perk o1, Perk o2) {
		if (o1.getCost() != o2.getCost()) {
			return o1.getCost().compareTo(o2.getCost());
		}
		return o1.getName().compareTo(o2.getName());
	}
}
