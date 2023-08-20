package com.softwaremagico.librodeesher.pj.training;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.google.gson.annotations.Expose;
import com.softwaremagico.librodeesher.basics.Roll;
import com.softwaremagico.librodeesher.pj.characteristic.CharacteristicRoll;
import com.softwaremagico.librodeesher.pj.characteristic.CharacteristicsAbbreviature;
import com.softwaremagico.librodeesher.pj.equipment.MagicObject;
import com.softwaremagico.librodeesher.pj.skills.Skill;
import com.softwaremagico.persistence.StorableObject;

/**
 * Categories that are already defined are not stored in this class, can be
 * obtained from training class. Skills that are already defined are stored in
 * this class as a list with one element.
 */
@Entity
@Table(name = "T_TRAINING_DECISION")
public class TrainingDecision extends StorableObject {
	private static final long serialVersionUID = 3914227163981778627L;

	@Expose
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@CollectionTable(name = "T_TRAINING_DECISION_CATEGORY_SELECTED")
	// For avoiding error org.hibernate.loader.MultipleBagFetchException: cannot
	// simultaneously fetch multiple bags
	// (http://stackoverflow.com/questions/4334970/hibernate-cannot-simultaneously-fetch-multiple-bags)
	@LazyCollection(LazyCollectionOption.FALSE)
	// TrainingCategoryIndex -> TrainingCategoriesSelected
	private Map<Integer, TrainingCategoriesSelected> categoriesSelected;

	@Expose
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@LazyCollection(LazyCollectionOption.FALSE)
	@CollectionTable(name = "T_TRAINING_DECISION_SKILLS_SELECTED")
	// TrainingCategoryIndex -> TrainingSkillsSelected
	// Defines all ranks in a skill for a training. If no skills selected
	// options is a list with one skill.
	private Map<Integer, TrainingSkillsSelected> skillsSelected;

	@Expose
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@LazyCollection(LazyCollectionOption.FALSE)
	@CollectionTable(name = "T_TRAINING_CHARACTERISTICS_UPDATES")
	private List<CharacteristicRoll> characteristicsUpdates;

	@Expose
	@ElementCollection
	@LazyCollection(LazyCollectionOption.FALSE)
	@CollectionTable(name = "T_TRAINING_DECISION_COMMON_SKILLS")
	private Set<String> commonSkillsChosen;

	@Expose
	@ElementCollection
	@LazyCollection(LazyCollectionOption.FALSE)
	@CollectionTable(name = "T_TRAINING_DECISION_PROFESSIONAL_SKILLS")
	private Set<String> professionalSkillsChosen;

	@Expose
	@ElementCollection
	@LazyCollection(LazyCollectionOption.FALSE)
	@CollectionTable(name = "T_TRAINING_DECISION_RESTRICTED_SKILLS")
	private Set<String> restrictedSkillsChosen;

	@Expose
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@LazyCollection(LazyCollectionOption.FALSE)
	@CollectionTable(name = "T_TRAINING_OBJECTS")
	private List<TrainingItem> equipment;

	@Expose
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@CollectionTable(name = "T_TRAINING_MAGIC_ITEMS")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<MagicObject> magicItems; // Equipment converted to magic
											// objects.

	public TrainingDecision() {
		categoriesSelected = new HashMap<>();
		skillsSelected = new HashMap<>();
		characteristicsUpdates = new ArrayList<>();
		equipment = new ArrayList<>();
		magicItems = new ArrayList<>();
		commonSkillsChosen = new HashSet<>();
		professionalSkillsChosen = new HashSet<>();
		restrictedSkillsChosen = new HashSet<>();
	}

	@Override
	public void resetIds() {
		resetIds(this);
		resetIds(equipment);
		resetIds(characteristicsUpdates);
		resetIds(skillsSelected);
		resetIds(categoriesSelected);
	}

	@Override
	public void resetComparationIds() {
		resetComparationIds(this);
		resetComparationIds(equipment);
		resetComparationIds(characteristicsUpdates);
		resetComparationIds(skillsSelected);
		resetComparationIds(categoriesSelected);
	}

	public void addSelectedCategory(Integer trainingCategory, String categoryName) {
		TrainingCategoriesSelected categories = categoriesSelected.get(trainingCategory);
		if (categories == null) {
			categories = new TrainingCategoriesSelected();
		}
		categories.add(categoryName);
		categoriesSelected.put(trainingCategory, categories);
	}

	public List<String> getSelectedCategory(Integer trainingCategory) {
		TrainingCategoriesSelected categories = categoriesSelected.get(trainingCategory);
		if (categories == null) {
			categories = new TrainingCategoriesSelected();
		}
		return categories.getAll();
	}

	public void setSkillRank(Integer trainingCategory, String skill, int ranks) {
		if (skillsSelected.get(trainingCategory) == null) {
			skillsSelected.put(trainingCategory, new TrainingSkillsSelected());
		}
		skillsSelected.get(trainingCategory).put(skill, ranks);
	}

	public int getSkillRank(Integer trainingCategory, String skill) {
		if (skillsSelected.get(trainingCategory) == null || skillsSelected.get(trainingCategory).getSkillsRanks().get(skill) == null) {
			return 0;
		}
		return skillsSelected.get(trainingCategory).getSkillsRanks().get(skill);
	}

	public int getSkillRank(Integer trainingCategory, Skill skill) {
		if (skill == null || skillsSelected.get(trainingCategory) == null) {
			return 0;
		}
		for (String trainingSkill : skillsSelected.get(trainingCategory).getSkillsRanks().keySet()) {
			if (trainingSkill != null && trainingSkill.equals(skill.getName())) {
				return getSkillRank(trainingCategory, trainingSkill);
			}
		}
		return 0;
	}

	public void clearSkillRanks() {
		skillsSelected.clear();
	}

	protected Map<Integer, TrainingCategoriesSelected> getCategoriesSelected() {
		return categoriesSelected;
	}

	protected void setCategoriesSelected(Map<Integer, TrainingCategoriesSelected> categoriesSelected) {
		this.categoriesSelected = categoriesSelected;
	}

	protected Map<Integer, TrainingSkillsSelected> getSkillsSelected() {
		return skillsSelected;
	}

	protected void setSkillsSelected(Map<Integer, TrainingSkillsSelected> skillsSelected) {
		this.skillsSelected = skillsSelected;
	}

	public void removeSkillsSelected(Integer trainingCategory) {
		skillsSelected.remove(trainingCategory);
	}

	public CharacteristicRoll addCharactersiticUpdate(CharacteristicsAbbreviature abbreviature, Integer currentTemporalValue, Integer currentPotentialValue,
			Roll roll) {
		CharacteristicRoll characteristicRoll = new CharacteristicRoll(abbreviature, currentTemporalValue, currentPotentialValue, roll);
		characteristicsUpdates.add(characteristicRoll);
		return characteristicRoll;
	}

	public List<CharacteristicRoll> getCharacteristicsUpdates(CharacteristicsAbbreviature abbreviature) {
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

	public void addCharacteristicUpdate(CharacteristicRoll characteristicRoll) {
		characteristicsUpdates.add(characteristicRoll);
	}

	public List<TrainingItem> getStandardEquipment() {
		return equipment;
	}

	public List<MagicObject> getMagicItems() {
		return magicItems;
	}

	public Set<String> getCommonSkillsChosen() {
		return commonSkillsChosen;
	}

	public void setCommonSkillsChosen(Set<String> commonSkillsChosen) {
		this.commonSkillsChosen = commonSkillsChosen;
	}

	public Set<String> getProfessionalSkillsChosen() {
		return professionalSkillsChosen;
	}

	public void setProfessionalSkillsChosen(Set<String> professionalSkillsChosen) {
		this.professionalSkillsChosen = professionalSkillsChosen;
	}

	public Set<String> getRestrictedSkillsChosen() {
		return restrictedSkillsChosen;
	}

	public void setRestrictedSkillsChosen(Set<String> restrictedSkillsChosen) {
		this.restrictedSkillsChosen = restrictedSkillsChosen;
	}

}
