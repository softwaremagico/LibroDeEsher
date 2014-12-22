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

	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

	@Override
	public String getBonusName() {
		return skill;
	}
}
