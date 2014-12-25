package com.softwaremagico.librodeesher.pj.training;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.softwaremagico.librodeesher.basics.Spanish;
import com.softwaremagico.librodeesher.pj.categories.CategoryFactory;
import com.softwaremagico.librodeesher.pj.skills.SkillFactory;
import com.softwaremagico.persistence.StorableObject;

@Entity
@Table(name = "T_TRAINING_OBJECT")
public class TrainingItem extends StorableObject {
	@Expose
	private String name;
	@Expose
	private String description;
	@Expose
	private int bonus;
	@Expose
	@Enumerated(EnumType.STRING) 
	private TrainingItemType type;
	@Expose
	private String skill;
	@Expose
	private int probability;

	protected TrainingItem() {

	}

	@Override
	public void resetIds() {
		resetIds(this);
	}

	public TrainingItem(String name, int bonus, String skill, int probability) {
		if (name.contains("(")) {
			this.name = name.substring(0, name.indexOf("("));
			this.description = name.substring(name.indexOf("("), name.indexOf(")"));
		} else {
			this.name = name;
			this.description = "";
		}
		this.probability = probability;
		this.bonus = bonus;
		this.skill = skill;
		setType(skill);
	}

	private void setType(String tag) {
		if (tag.toLowerCase().equals(Spanish.WEAPON) || tag.toLowerCase().equals(Spanish.ANY_WEAPON)) {
			type = TrainingItemType.WEAPON;
		} else if (tag.toLowerCase().equals(Spanish.CLOSE_COMBAT_WEAPON)) {
			type = TrainingItemType.WEAPON_CLOSE_COMBAT;
		} else if (tag.toLowerCase().equals(Spanish.PROJECTILE_WEAPON)) {
			type = TrainingItemType.WEAPON_RANGED;
		} else if (tag.toLowerCase().equals(Spanish.ARMOUR)) {
			type = TrainingItemType.ARMOUR;
		} else if (tag.toLowerCase().equals(Spanish.ANY_SKILL)) {
			type = TrainingItemType.ANY;
		} else if (SkillFactory.existSkill(tag)) {
			type = TrainingItemType.SKILL;
		} else if (CategoryFactory.existCategory(tag)) {
			type = TrainingItemType.CATEGORY;
		} else {
			type = TrainingItemType.UNKNOWN;
		}
	}

	public String getName() {
		return name;
	}

	public int getBonus() {
		return bonus;
	}

	public int getProbability() {
		return probability;
	}

	public String getSkill() {
		return skill;
	}

	public TrainingItemType getType() {
		return type;
	}

}
