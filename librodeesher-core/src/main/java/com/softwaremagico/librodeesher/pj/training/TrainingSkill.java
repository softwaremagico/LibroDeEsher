package com.softwaremagico.librodeesher.pj.training;

import java.util.List;

public class TrainingSkill {
	private List<String> skillOptions; // List to choose from.
	private Integer ranks;

	public TrainingSkill(List<String> skillOptions, Integer ranks) {
		this.skillOptions = skillOptions;
		this.ranks = ranks;
	}

	public List<String> getSkillOptions() {
		return skillOptions;
	}

	public boolean needToChooseOneSkill() {
		return skillOptions.size() > 1;
	}

	public String getName() {
		if (skillOptions.size() == 1) {
			return skillOptions.get(0);
		}
		return null;
	}
}
