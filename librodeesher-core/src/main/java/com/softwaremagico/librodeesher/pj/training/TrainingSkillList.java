package com.softwaremagico.librodeesher.pj.training;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_TRAINING_SKILL_LIST")
public class TrainingSkillList {
	@Id
	@GeneratedValue
	private Long id; // database id.
	@ElementCollection
	@CollectionTable(name = "T_TRAINING_SKILL_LIST_OF_SKILLS")
	private List<TrainingSkill> trainingSkills;

	public TrainingSkillList() {
		trainingSkills = new ArrayList<>();
	}

	protected Long getId() {
		return id;
	}

	protected void setId(Long id) {
		this.id = id;
	}

	protected List<TrainingSkill> getTrainingSkills() {
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
