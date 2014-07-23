package com.softwaremagico.librodeesher.pj.training;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.softwaremagico.librodeesher.pj.equipment.MagicObject;
import com.softwaremagico.persistence.StorableObject;

@Entity
@Table(name = "T_TRAINING_OBJECT")
public class TrainingItem extends StorableObject implements MagicObject {
	private String name;
	private int bonus;
	private int probability;
	
	protected TrainingItem(){
		
	}

	public TrainingItem(String name, int bonus, int probability) {
		this.name = name;
		this.probability = probability;
		this.bonus = bonus;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getBonus() {
		return bonus;
	}

	public int getProbability() {
		return probability;
	}

}