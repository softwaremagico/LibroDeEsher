package com.softwaremagico.librodeesher.pj.training;

import java.util.ArrayList;
import java.util.List;

public class TrainingSkillList {

	private List<TrainingSkill> trainingSkills;

	public TrainingSkillList() {
		trainingSkills = new ArrayList<>();
	}

	public List<TrainingSkill> getTrainingSkills() {
		return trainingSkills;
	}

	protected void setTrainingSkills(List<TrainingSkill> trainingSkills) {
		this.trainingSkills = trainingSkills;
	}

	public void add(TrainingSkill skill) {
		trainingSkills.add(skill);
	}

	public List<TrainingSkill> getAll() {
		return trainingSkills;
	}

	public int size() {
		return trainingSkills.size();
	}

	public boolean isEmpty() {
		return trainingSkills.isEmpty();
	}
}
