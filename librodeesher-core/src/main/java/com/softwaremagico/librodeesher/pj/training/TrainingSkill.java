package com.softwaremagico.librodeesher.pj.training;

import java.util.List;

public class TrainingSkill {
	private List<String> skillOptions; // List to choose from.
	private Integer ranks;

	public TrainingSkill(List<String> skillOptions, Integer ranks) {
		this.skillOptions = skillOptions;
		this.ranks = ranks;
	}
}
