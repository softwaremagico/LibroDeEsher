package com.softwaremagico.librodeesher.pj.training;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.google.gson.annotations.Expose;
import com.softwaremagico.persistence.StorableObject;

@Entity
@Table(name = "T_TRAINING_SKILLS_SELECTED")
class TrainingSkillsSelected extends StorableObject {
	
	@Expose
	@ElementCollection
	@CollectionTable(name = "T_TRAINING_SKILLS_RANKS_PER_SKILL")
	@LazyCollection(LazyCollectionOption.FALSE)
	private Map<String, Integer> skillsRanks;
	
//	@Expose
//	@ElementCollection
//	@CollectionTable(name = "T_TRAINING_SKILLS_SELECTED_LIST_OF_SKILLS")
//	@LazyCollection(LazyCollectionOption.FALSE)
//	private List<TrainingSkill> trainingSkills;

	public TrainingSkillsSelected() {
		skillsRanks = new HashMap<>();
	}
	
	@Override
	public void resetIds(){
		resetIds(this);
		resetIds(skillsRanks);
	}

	public void put(TrainingSkill skill, int ranks) {
		if (ranks == 0) {
			skillsRanks.remove(skill);
		} else {
			skillsRanks.put(skill.getName(), ranks);
		}
	}

	protected Map<String, Integer> getSkills() {
		return skillsRanks;
	}

}
