package com.softwaremagico.librodeesher.pj.training;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
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

}
