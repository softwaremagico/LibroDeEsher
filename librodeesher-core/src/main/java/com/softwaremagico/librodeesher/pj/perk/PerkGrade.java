package com.softwaremagico.librodeesher.pj.perk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public enum PerkGrade {
	MINIMUM("mínimo", 0), MINOR("menor", 1), MAJOR("mayor", 2), MAXIMUM("máximo", 3);

	private String tag;

	private int level;

	PerkGrade(String tag, int level) {
		this.tag = tag;
		this.level = level;
	}

	public static PerkGrade getPerkCategory(String tag) {
		for (PerkGrade perkGrade : PerkGrade.values()) {
			if (perkGrade.tag.equals(tag.toLowerCase())) {
				return perkGrade;
			}
		}
		return MAXIMUM;
	}

	public String getTag() {
		return tag;
	}

	@Override
	public String toString() {
		return tag.substring(0, 1).toUpperCase() + tag.substring(1);
	}

	/**
	 * Returns the grade pondered as a number
	 * 
	 * @return
	 */
	public Integer asNumber() {
		return level;
	}

	/**
	 * Return a list of grades with less level ordered from bigger to lesser.
	 * 
	 * @param numberOfgrades
	 * @return
	 */
	public List<PerkGrade> getLesserGrades(int numberOfgrades) {
		GradeComparator comparator = new GradeComparator();
		List<PerkGrade> availableElements = new ArrayList<PerkGrade>(Arrays.asList(PerkGrade.values()));
		Collections.sort(availableElements, comparator);
		Collections.reverse(availableElements);
		try {
			List<PerkGrade> lesserElements = availableElements.subList(availableElements.indexOf(this),
					availableElements.size());
			return lesserElements.subList(0, Math.min(lesserElements.size(), numberOfgrades));
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}

	class GradeComparator implements Comparator<PerkGrade> {
		public int compare(PerkGrade o1, PerkGrade o2) {
			return o1.asNumber().compareTo(o2.asNumber());
		}
	}
}
