package com.softwaremagico.librodeesher.pj.training;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.softwaremagico.persistence.StorableObject;

@Entity
@Table(name = "T_TRAINING_SKILL")
public class TrainingSkill extends StorableObject {

	@ElementCollection
	@CollectionTable(name = "T_TRAINING_SKILLS_OPTIONS")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<String> skillOptions; // List to choose from.
	private Integer ranks;

	public TrainingSkill(List<String> skillOptions, Integer ranks) {
		this.skillOptions = skillOptions;
		this.ranks = ranks;
	}

	@Override
	public void resetIds() {
		resetIds(this);
	}

	public List<String> getSkillOptions() {
		return skillOptions;
	}

	public boolean needToChooseOneSkill() {
		return skillOptions.size() > 1;
	}

	public String getName() {
		if (skillOptions.size() == 1) {
			return skillOptions.get(0);
		}
		return null;
	}

	public Integer getRanks() {
		return ranks;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((skillOptions == null) ? 0 : skillOptions.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TrainingSkill other = (TrainingSkill) obj;
		if (skillOptions == null) {
			if (other.skillOptions != null)
				return false;
		} else if (!skillOptions.equals(other.skillOptions))
			return false;
		return true;
	}

	protected void setSkillOptions(List<String> skillOptions) {
		this.skillOptions = skillOptions;
	}

	protected void setRanks(Integer ranks) {
		this.ranks = ranks;
	}
}
