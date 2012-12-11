package com.softwaremagico.librodeesher.pj.profession;

import com.softwaremagico.librodeesher.pj.training.Training;
import com.softwaremagico.librodeesher.pj.training.TrainingType;

public class TrainingCost {
	public Training training;
	public Integer cost;
	public TrainingType type;

	TrainingCost(Training training, Integer cost, TrainingType type) {
		this.training = training;
		this.cost = cost;
		this.type = type;
	}
}