package com.softwaremagico.librodeesher.pj.training;

import java.util.Comparator;

public class TrainingSkillComparator implements Comparator<TrainingSkill> {

	@Override
	public int compare(TrainingSkill o1, TrainingSkill o2) {
		return o1.getSkillOptions().get(0).compareTo(o2.getSkillOptions().get(0));
	}
}
