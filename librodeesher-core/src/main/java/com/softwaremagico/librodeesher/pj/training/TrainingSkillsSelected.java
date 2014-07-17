package com.softwaremagico.librodeesher.pj.training;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.softwaremagico.persistence.StorableObject;

@Entity
@Table(name = "T_TRAINING_SKILLS_SELECTED")
class TrainingSkillsSelected extends StorableObject {
	@ElementCollection
	@CollectionTable(name = "T_TRAINING_SKILLS_SELECTED_LIST_OF_SKILLS")
	private Map<TrainingSkill, Integer> skills;

	public TrainingSkillsSelected() {
		skills = new HashMap<>();
	}

	public void put(TrainingSkill skill, int ranks) {
		if (ranks == 0) {
			skills.remove(skill);
		} else {
			skills.put(skill, ranks);
		}
	}

	protected Map<TrainingSkill, Integer> getSkills() {
		return skills;
	}

	protected void setSkills(Map<TrainingSkill, Integer> skills) {
		this.skills = skills;
	}

}
