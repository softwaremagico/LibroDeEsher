package com.softwaremagico.librodeesher.pj.equipment;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "T_TRAINING_OBJECT_SKILL_BONUS")
public class SkillBonus extends ObjectBonus {

	@Expose
	public String skill;

	public SkillBonus() {

	}

	@Override
	public String getBonusName() {
		return skill;
	}

	@Override
	public void setBonusName(String value) {
		this.skill = value;
	}
}
