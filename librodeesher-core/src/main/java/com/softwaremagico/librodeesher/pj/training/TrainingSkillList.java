package com.softwaremagico.librodeesher.pj.training;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.softwaremagico.persistence.StorableObject;

@Entity
@Table(name = "T_TRAINING_SKILL_LIST")
public class TrainingSkillList extends StorableObject {

	@ElementCollection
	@CollectionTable(name = "T_TRAINING_SKILL_LIST_OF_SKILLS")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<TrainingSkill> trainingSkills;

	public TrainingSkillList() {
		trainingSkills = new ArrayList<>();
	}
	
	@Override
	public void resetIds(){
		resetIds(this);
		resetIds(trainingSkills);
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
