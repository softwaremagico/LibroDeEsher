package com.softwaremagico.librodeesher.pj.training;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.softwaremagico.librodeesher.basics.Spanish;
import com.softwaremagico.librodeesher.pj.categories.CategoryFactory;
import com.softwaremagico.librodeesher.pj.equipment.Equipment;
import com.softwaremagico.librodeesher.pj.skills.SkillFactory;

@Entity
@Table(name = "T_TRAINING_OBJECT")
public class TrainingItem extends Equipment {
	private static final long serialVersionUID = 6296887781180082031L;

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
		super();
	}

	public TrainingItem(TrainingItem trainingItem) {
		super();
		setName(trainingItem.getName());
		setDescription(trainingItem.getDescription());
		bonus = trainingItem.getBonus();
		type = trainingItem.getType();
		skill = trainingItem.getSkill();
		probability = trainingItem.getProbability();
	}

	public TrainingItem(String name, int bonus, String skill, int probability) {
		if (name.contains("(")) {
			setName(name.substring(0, name.indexOf("(")).trim());
			setDescription(name.substring(name.indexOf("(") + 1, name.indexOf(")")).trim());
		} else {
			setName(name);
			setDescription("");
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
		} else if (SkillFactory.existsSkill(tag)) {
			type = TrainingItemType.SKILL;
		} else if (CategoryFactory.existCategory(tag)) {
			type = TrainingItemType.CATEGORY;
		} else {
			type = TrainingItemType.UNKNOWN;
		}
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

	@Override
	public String toString() {
		return getName();
	}

	public boolean isMagic() {
		return getBonus() > 0;
	}
}
