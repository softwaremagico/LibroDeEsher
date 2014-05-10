package com.softwaremagico.librodeesher.pj.training;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_TRAINING_SKILLS_SELECTED")
class TrainingSkillsSelected {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name = "ID", unique = true, nullable = false)
	private Long id; // database id.
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

	protected Long getId() {
		return id;
	}

	protected void setId(Long id) {
		this.id = id;
	}

	protected Map<TrainingSkill, Integer> getSkills() {
		return skills;
	}

	protected void setSkills(Map<TrainingSkill, Integer> skills) {
		this.skills = skills;
	}

}
