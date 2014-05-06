package com.softwaremagico.librodeesher.pj.training;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_TRAINING_DECISION")
public class TrainingDecision {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name = "ID", unique = true, nullable = false)
	private Long id; // database id.
	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "T_TRAINING_DECISION_CATEGORY_SELECTED")
	private Map<TrainingCategory, TrainingCategoriesSelected> categoriesSelected;
	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "T_TRAINING_DECISION_SKILLS_SELECTED")
	private Map<TrainingCategory, TrainingSkillsSelected> skillsSelected;

	public TrainingDecision() {
		categoriesSelected = new HashMap<>();
		skillsSelected = new HashMap<>();
	}

	public void addSelectedCategory(TrainingCategory trainingCategory, String categoryName) {
		TrainingCategoriesSelected categories = categoriesSelected.get(trainingCategory);
		if (categories == null) {
			categories = new TrainingCategoriesSelected();
		}
		categories.add(categoryName);
		categoriesSelected.put(trainingCategory, categories);
	}

	public List<String> getSelectedCategory(TrainingCategory trainingCategory) {
		TrainingCategoriesSelected categories = categoriesSelected.get(trainingCategory);
		if (categories == null) {
			categories = new TrainingCategoriesSelected();
		}
		return categories.getAll();
	}
	
	public void addSkillRank(TrainingCategory trainingCategory, TrainingSkill skill, int ranks){
		//TODO
		//skillsSelected.get(trainingCategory)
	}

	protected Long getId() {
		return id;
	}

	protected void setId(Long id) {
		this.id = id;
	}

	protected Map<TrainingCategory, TrainingCategoriesSelected> getCategoriesSelected() {
		return categoriesSelected;
	}

	protected void setCategoriesSelected(Map<TrainingCategory, TrainingCategoriesSelected> categoriesSelected) {
		this.categoriesSelected = categoriesSelected;
	}

	protected Map<TrainingCategory, TrainingSkillsSelected> getSkillsSelected() {
		return skillsSelected;
	}

	protected void setSkillsSelected(Map<TrainingCategory, TrainingSkillsSelected> skillsSelected) {
		this.skillsSelected = skillsSelected;
	}
}
