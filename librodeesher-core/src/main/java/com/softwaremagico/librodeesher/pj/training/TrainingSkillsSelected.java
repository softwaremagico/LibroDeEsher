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
import com.softwaremagico.log.EsherLog;
import com.softwaremagico.persistence.StorableObject;

@Entity
@Table(name = "T_TRAINING_SKILLS_SELECTED")
class TrainingSkillsSelected extends StorableObject {
	private static final long serialVersionUID = -4043581569918215711L;

	@Expose
	@ElementCollection
	@CollectionTable(name = "T_TRAINING_SKILLS_RANKS_PER_SKILL")
	@LazyCollection(LazyCollectionOption.FALSE)
	private Map<String, Integer> skillsRanks;

	public TrainingSkillsSelected() {
		skillsRanks = new HashMap<>();
	}

	@Override
	public void resetIds() {
		resetIds(this);
		resetIds(skillsRanks);
	}
	
	@Override
	public void resetComparationIds() {
		resetComparationIds(this);
		resetComparationIds(skillsRanks);
	}

	public void put(String skill, int ranks) {
		if (skill == null) {
			EsherLog.severe(this.getClass().getName(), "Skill null or without name.");
		} else {
			if (ranks == 0) {
				skillsRanks.remove(skill);
			} else {
				skillsRanks.put(skill, new Integer(ranks));
			}
		}
	}

	protected Map<String, Integer> getSkillsRanks() {
		return skillsRanks;
	}

}
