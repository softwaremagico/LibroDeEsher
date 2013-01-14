package com.softwaremagico.librodeesher.pj.perk;

import java.util.Comparator;

public class PerkComparator implements Comparator<Perk> {

	@Override
	public int compare(Perk o1, Perk o2) {
		return o1.getName().compareTo(o2.getName());
	}
}