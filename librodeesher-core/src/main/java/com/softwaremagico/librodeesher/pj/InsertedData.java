package com.softwaremagico.librodeesher.pj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.google.gson.annotations.Expose;
import com.softwaremagico.librodeesher.pj.characteristic.CharacteristicsAbbreviature;
import com.softwaremagico.librodeesher.pj.skills.Skill;
import com.softwaremagico.persistence.StorableObject;

@Entity
@Table(name = "T_INSERTED_DATA")
public class InsertedData extends StorableObject {

	@Expose
	private int createdAtLevel;

	private int instertedLevels;

	@Expose
	@ElementCollection
	@CollectionTable(name = "T_INSERTED_TEMPORAL_VALUES")
	private Map<CharacteristicsAbbreviature, Integer> characteristicsTemporalValues;

	@Expose
	@ElementCollection
	@CollectionTable(name = "T_INSERTED_POTENTIAL_VALUES")
	private Map<CharacteristicsAbbreviature, Integer> characteristicsPotentialValues;

	@Expose
	@ElementCollection
	@CollectionTable(name = "T_INSERTED_CATEGORIES_RANKS")
	@LazyCollection(LazyCollectionOption.FALSE)
	private Map<String, Integer> categoriesRanks;

	@Expose
	@ElementCollection
	@CollectionTable(name = "T_INSERTED_SKILLS_RANKS")
	@LazyCollection(LazyCollectionOption.FALSE)
	private Map<String, Integer> skillsRanks;

	@Expose
	@ElementCollection
	@CollectionTable(name = "T_INSERTED_GENERALIZED_SKILLS")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<String> generalizedSkills;

	@Expose
	@ElementCollection
	@CollectionTable(name = "T_INSERTED_TRAININGS_ADQUIRED")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<String> trainings;

	@Expose
	@ElementCollection
	@CollectionTable(name = "T_INSERTED_SKILL_SPECIALIZATIONS")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<String> skillSpecializations;

	public InsertedData() {
		this.createdAtLevel = 1;
		characteristicsTemporalValues = new HashMap<>();
		characteristicsPotentialValues = new HashMap<>();
		categoriesRanks = new HashMap<>();
		skillsRanks = new HashMap<>();
		trainings = new ArrayList<>();
		generalizedSkills = new ArrayList<>();
		skillSpecializations = new ArrayList<>();
	}

	public InsertedData(int createdAtLevel) {
		this.createdAtLevel = createdAtLevel;
	}

	@Override
	public void resetIds() {

	}

	public int getInstertedLevels() {
		return instertedLevels;
	}

	public void setInstertedLevels(int instertedLevels) {
		this.instertedLevels = instertedLevels;
	}

	public int getCreatedAtLevel() {
		return createdAtLevel;
	}

	public List<String> getSkillSpecializations(Skill skill) {
		List<String> specialities = new ArrayList<>();
		for (String speciality : skill.getSpecialities()) {
			if (skillSpecializations.contains(speciality)) {
				specialities.add(speciality);
			}
		}
		return specialities;
	}

	public Integer getRanksSpentInSpecializations(Skill skill) {
		int total = 0;
		for (String speciality : skill.getSpecialities()) {
			if (skillSpecializations.contains(speciality)) {
				total++;
			}
		}
		return total;
	}

	public Integer getSkillsRanks(String skillName) {
		Integer ranks = skillsRanks.get(skillName);
		if (ranks == null) {
			return 0;
		}
		return ranks;
	}

	public void setSkillsRanks(Skill skill, Integer ranks) {
		if (ranks == 0) {
			skillsRanks.remove(skill.getName());
		} else {
			skillsRanks.put(skill.getName(), ranks);
		}
	}

	public void setCategoryRanks(String categoryName, Integer ranks) {
		if (ranks <= 0) {
			categoriesRanks.remove(categoryName);
		} else {
			categoriesRanks.put(categoryName, ranks);
		}
	}

	public Integer getCategoryRanks(String categoryName) {
		Integer ranks = categoriesRanks.get(categoryName);
		if (ranks == null) {
			return 0;
		}
		return ranks;
	}

	public List<String> getGeneralizedSkills() {
		return generalizedSkills;
	}

	public List<String> getTrainings() {
		return trainings;
	}

	public void addTraining(String trainingName) {
		trainings.add(trainingName);
	}

}
