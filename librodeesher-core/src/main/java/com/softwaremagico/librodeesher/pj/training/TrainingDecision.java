package com.softwaremagico.librodeesher.pj.training;

import java.util.ArrayList;
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

import com.softwaremagico.librodeesher.basics.Roll;
import com.softwaremagico.librodeesher.basics.RollGroup;
import com.softwaremagico.librodeesher.pj.characteristic.CharacteristicRoll;

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
	@ElementCollection
	@CollectionTable(name = "T_HISTORIAL_CHARACTERISTICS_UPDATES")
	private List<CharacteristicRoll> characteristicsUpdates;

	public TrainingDecision() {
		categoriesSelected = new HashMap<>();
		skillsSelected = new HashMap<>();
		characteristicsUpdates = new ArrayList<>();
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

	public void addSkillRank(TrainingCategory trainingCategory, TrainingSkill skill, int ranks) {
		if (skillsSelected.get(trainingCategory) == null) {
			skillsSelected.put(trainingCategory, new TrainingSkillsSelected());
		}
		skillsSelected.get(trainingCategory).put(skill, ranks);
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

	public void removeSkillsSelected(TrainingCategory trainingCategory) {
		skillsSelected.remove(trainingCategory);
	}

	public void addCharactersiticUpdate(String abbreviature, Integer currentTemporalValue,
			Integer currentPotentialValue, Roll roll) {
		characteristicsUpdates.add(new CharacteristicRoll(abbreviature, currentTemporalValue, currentPotentialValue,
				roll));
	}

	public List<CharacteristicRoll> getCharacteristicsUpdates(String abbreviature) {
		List<CharacteristicRoll> returnList = new ArrayList<>();
		for (CharacteristicRoll characteristicRollGroup : characteristicsUpdates) {
			if (characteristicRollGroup.getCharacteristicAbbreviature().equals(abbreviature)) {
				returnList.add(characteristicRollGroup);
			}
		}

		return returnList;
	}

	public List<CharacteristicRoll> getCharacteristicsUpdates() {
		return characteristicsUpdates;
	}

	protected void setCharacteristicsUpdates(List<CharacteristicRoll> characteristicsUpdates) {
		this.characteristicsUpdates = characteristicsUpdates;
	}
}
