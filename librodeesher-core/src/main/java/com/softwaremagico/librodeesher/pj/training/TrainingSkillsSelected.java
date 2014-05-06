package com.softwaremagico.librodeesher.pj.training;

import java.util.ArrayList;
import java.util.List;

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
	private List<String> skills;

	public TrainingSkillsSelected() {
		skills = new ArrayList<>();
	}

	public void add(String categoryName) {
		skills.add(categoryName);
	}

	public List<String> getAll() {
		return skills;
	}

	protected Long getId() {
		return id;
	}

	protected void setId(Long id) {
		this.id = id;
	}

	protected List<String> getSkills() {
		return skills;
	}

	protected void setSkills(List<String> skills) {
		this.skills = skills;
	}
	
}
