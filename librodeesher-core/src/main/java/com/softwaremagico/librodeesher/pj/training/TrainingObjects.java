package com.softwaremagico.librodeesher.pj.training;

import com.softwaremagico.librodeesher.pj.equipment.MagicObject;

public class TrainingObjects implements MagicObject{
	private String name;
	private int bonus;
	private int probability;

	public TrainingObjects(String name, int bonus, int probability) {
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