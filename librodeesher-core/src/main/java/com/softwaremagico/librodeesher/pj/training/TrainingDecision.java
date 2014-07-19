package com.softwaremagico.librodeesher.pj.training;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.softwaremagico.librodeesher.basics.Roll;
import com.softwaremagico.librodeesher.pj.characteristic.CharacteristicRoll;
import com.softwaremagico.persistence.StorableObject;

@Entity
@Table(name = "T_TRAINING_DECISION")
public class TrainingDecision extends StorableObject {

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@CollectionTable(name = "T_TRAINING_DECISION_CATEGORY_SELECTED")
	// For avoiding error org.hibernate.loader.MultipleBagFetchException: cannot
	// simultaneously fetch multiple bags
	// (http://stackoverflow.com/questions/4334970/hibernate-cannot-simultaneously-fetch-multiple-bags)
	@LazyCollection(LazyCollectionOption.FALSE)
	//TrainingCategoryIndex -> TrainingCategoriesSelected
	private Map<Integer, TrainingCategoriesSelected> categoriesSelected;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@LazyCollection(LazyCollectionOption.FALSE)
	@CollectionTable(name = "T_TRAINING_DECISION_SKILLS_SELECTED")
	//TrainingCategoryIndex -> TrainingSkillsSelected
	private Map<Integer, TrainingSkillsSelected> skillsSelected;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@LazyCollection(LazyCollectionOption.FALSE)
	@CollectionTable(name = "T_TRAINING_CHARACTERISTICS_UPDATES")
	private List<CharacteristicRoll> characteristicsUpdates;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@LazyCollection(LazyCollectionOption.FALSE)
	@CollectionTable(name = "T_TRAINING_OBJECTS")
	private List<TrainingItem> equipment;

	public TrainingDecision() {
		categoriesSelected = new HashMap<>();
		skillsSelected = new HashMap<>();
		characteristicsUpdates = new ArrayList<>();
		equipment = new ArrayList<>();
	}

	public void addSelectedCategory(Integer trainingCategory,
			String categoryName) {
		TrainingCategoriesSelected categories = categoriesSelected
				.get(trainingCategory);
		if (categories == null) {
			categories = new TrainingCategoriesSelected();
		}
		categories.add(categoryName);
		categoriesSelected.put(trainingCategory, categories);
	}

	public List<String> getSelectedCategory(Integer trainingCategory) {
		TrainingCategoriesSelected categories = categoriesSelected
				.get(trainingCategory);
		if (categories == null) {
			categories = new TrainingCategoriesSelected();
		}
		return categories.getAll();
	}

	public void addSkillRank(Integer trainingCategory,
			TrainingSkill skill, int ranks) {
		if (skillsSelected.get(trainingCategory) == null) {
			skillsSelected.put(trainingCategory, new TrainingSkillsSelected());
		}
		skillsSelected.get(trainingCategory).put(skill, ranks);
	}

	protected Map<Integer, TrainingCategoriesSelected> getCategoriesSelected() {
		return categoriesSelected;
	}

	protected void setCategoriesSelected(
			Map<Integer, TrainingCategoriesSelected> categoriesSelected) {
		this.categoriesSelected = categoriesSelected;
	}

	protected Map<Integer, TrainingSkillsSelected> getSkillsSelected() {
		return skillsSelected;
	}

	public Map<TrainingSkill, Integer> getSkillRanks(
			TrainingCategory trainingCategory) {
		Map<TrainingSkill, Integer> result = new HashMap<>();
		TrainingCategoriesSelected categorySelected = categoriesSelected
				.get(trainingCategory);
		if (categorySelected != null) {
			TrainingSkillsSelected trainingSkills = skillsSelected
					.get(categorySelected);
			if (trainingSkills != null) {
				result = trainingSkills.getSkills();
			}
		}
		return result;
	}

	public Map<TrainingSkill, Integer> getSkillRanks(
			TrainingCategoriesSelected categorySelected) {
		TrainingSkillsSelected trainingSkills = skillsSelected
				.get(categorySelected);
		Map<TrainingSkill, Integer> result = new HashMap<>();
		if (trainingSkills != null) {
			result = trainingSkills.getSkills();
		}
		return result;
	}

	protected void setSkillsSelected(
			Map<Integer, TrainingSkillsSelected> skillsSelected) {
		this.skillsSelected = skillsSelected;
	}

	public void removeSkillsSelected(TrainingCategory trainingCategory) {
		skillsSelected.remove(trainingCategory);
	}

	public CharacteristicRoll addCharactersiticUpdate(String abbreviature,
			Integer currentTemporalValue, Integer currentPotentialValue,
			Roll roll) {
		CharacteristicRoll characteristicRoll = new CharacteristicRoll(
				abbreviature, currentTemporalValue, currentPotentialValue, roll);
		characteristicsUpdates.add(characteristicRoll);
		return characteristicRoll;
	}

	public List<CharacteristicRoll> getCharacteristicsUpdates(
			String abbreviature) {
		List<CharacteristicRoll> returnList = new ArrayList<>();
		for (CharacteristicRoll characteristicRollGroup : characteristicsUpdates) {
			if (characteristicRollGroup.getCharacteristicAbbreviature().equals(
					abbreviature)) {
				returnList.add(characteristicRollGroup);
			}
		}

		return returnList;
	}

	public List<CharacteristicRoll> getCharacteristicsUpdates() {
		return characteristicsUpdates;
	}

	protected void setCharacteristicsUpdates(
			List<CharacteristicRoll> characteristicsUpdates) {
		this.characteristicsUpdates = characteristicsUpdates;
	}

	public void addCharacteristicUpdate(CharacteristicRoll characteristicRoll) {
		characteristicsUpdates.add(characteristicRoll);
	}

	public List<TrainingItem> getEquipment() {
		return equipment;
	}
}
