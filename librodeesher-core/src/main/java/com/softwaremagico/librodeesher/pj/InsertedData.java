package com.softwaremagico.librodeesher.pj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.google.gson.annotations.Expose;
import com.softwaremagico.librodeesher.pj.characteristic.Characteristic;
import com.softwaremagico.librodeesher.pj.characteristic.Characteristics;
import com.softwaremagico.librodeesher.pj.characteristic.CharacteristicsAbbreviature;
import com.softwaremagico.librodeesher.pj.skills.Skill;
import com.softwaremagico.librodeesher.pj.training.TrainingDecision;
import com.softwaremagico.persistence.StorableObject;

@Entity
@Table(name = "T_INSERTED_DATA")
public class InsertedData extends StorableObject {

	@Expose
	private Integer createdAtLevel = 0;

	@Expose
	private Integer instertedLevels;

	@Expose
	@ElementCollection
	@CollectionTable(name = "T_INSERTED_TEMPORAL_VALUES")
	private Map<CharacteristicsAbbreviature, Integer> characteristicsTemporalValuesModification;

	@Expose
	@ElementCollection
	@CollectionTable(name = "T_INSERTED_POTENTIAL_VALUES")
	private Map<CharacteristicsAbbreviature, Integer> characteristicsPotentialValuesModification;

	@Expose
	@ElementCollection
	@CollectionTable(name = "T_INSERTED_CATEGORIES_RANKS")
	@LazyCollection(LazyCollectionOption.FALSE)
	private Map<String, Integer> categoriesRanksModification;

	@Expose
	@ElementCollection
	@CollectionTable(name = "T_INSERTED_SKILLS_RANKS")
	@LazyCollection(LazyCollectionOption.FALSE)
	private Map<String, Integer> skillsRanksModification;

	@Expose
	@ElementCollection
	@CollectionTable(name = "T_INSERTED_GENERALIZED_SKILLS")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<String> generalizedSkillsAdded;

	@Expose
	@ElementCollection
	@CollectionTable(name = "T_INSERTED_TRAININGS_ADQUIRED")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<String> trainingsAdded;

	@Expose
	@ElementCollection
	@CollectionTable(name = "T_INSERTED_SKILL_SPECIALIZATIONS")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<String> skillSpecializationsAdded;

	@Expose
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@CollectionTable(name = "T_INSERTED_TRAINING_DECISIONS")
	private Map<String, TrainingDecision> trainingDecisions;

	public InsertedData() {
		characteristicsTemporalValuesModification = new HashMap<>();
		characteristicsPotentialValuesModification = new HashMap<>();
		categoriesRanksModification = new HashMap<>();
		skillsRanksModification = new HashMap<>();
		trainingsAdded = new ArrayList<>();
		generalizedSkillsAdded = new ArrayList<>();
		skillSpecializationsAdded = new ArrayList<>();
		trainingDecisions = new HashMap<>();
		initializeCharacteristics();
	}

	/**
	 * We set as null values to facilitate the edition of the JSON file. 
	 */
	private void initializeCharacteristics() {
		for (Characteristic characteristic : Characteristics.getCharacteristics()) {
			characteristicsTemporalValuesModification.put(characteristic.getAbbreviature(), null);
			characteristicsPotentialValuesModification.put(characteristic.getAbbreviature(), null);
		}
	}

	public boolean isEnabled() {
		return createdAtLevel != null && createdAtLevel > 0;
	}

	@Override
	public void resetIds() {
		resetIds(this);
		resetIds(trainingDecisions);
	}

	public int getInstertedLevels() {
		if (instertedLevels != null) {
			return instertedLevels;
		}
		return 0;
	}

	public void setInstertedLevels(int instertedLevels) {
		this.instertedLevels = instertedLevels;
	}

	public Integer getCreatedAtLevel() {
		return createdAtLevel;
	}

	public List<String> getSkillSpecializations(Skill skill) {
		List<String> specialities = new ArrayList<>();
		for (String speciality : skill.getSpecialities()) {
			if (skillSpecializationsAdded.contains(speciality)) {
				specialities.add(speciality);
			}
		}
		return specialities;
	}

	public Integer getSkillsRanksModification(String skillName) {
		Integer ranks = skillsRanksModification.get(skillName);
		if (ranks == null) {
			return 0;
		}
		return ranks;
	}

	public void setSkillsRanksModification(Skill skill, Integer ranks) {
		if (ranks == 0) {
			skillsRanksModification.remove(skill.getName());
		} else {
			skillsRanksModification.put(skill.getName(), ranks);
		}
	}

	public void setCategoryRanksModification(String categoryName, Integer ranks) {
		if (ranks <= 0) {
			categoriesRanksModification.remove(categoryName);
		} else {
			categoriesRanksModification.put(categoryName, ranks);
		}
	}

	public Integer getCategoryRanksModification(String categoryName) {
		Integer ranks = categoriesRanksModification.get(categoryName);
		if (ranks == null) {
			return 0;
		}
		return ranks;
	}

	public List<String> getGeneralizedSkills() {
		return generalizedSkillsAdded;
	}

	public List<String> getTrainings() {
		return trainingsAdded;
	}

	public void addTraining(String trainingName) {
		trainingsAdded.add(trainingName);
	}

	public void removeTraining(String trainingName) {
		trainingsAdded.remove(trainingName);
		trainingDecisions.remove(trainingName);
	}

	public Map<String, TrainingDecision> getTrainingDecisions() {
		return trainingDecisions;
	}

	public Integer getCharacteristicsTemporalValuesModification(CharacteristicsAbbreviature abbreviature) {
		return characteristicsTemporalValuesModification.get(abbreviature);
	}

	public Integer getCharacteristicsPotentialValuesModification(CharacteristicsAbbreviature abbreviature) {
		return characteristicsPotentialValuesModification.get(abbreviature);
	}

}
