package com.softwaremagico.librodeesher.pj.training;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class TrainingDecision {
	private Hashtable<TrainingCategory, List<String>> categoriesSelected;
	private Hashtable<TrainingCategory, List<String>> skillsSelected;

	public TrainingDecision() {
		categoriesSelected = new Hashtable<>();
		skillsSelected = new Hashtable<>();
	}

	public void addSelectedCategory(TrainingCategory trainingCategory, String categoryName) {
		List<String> categories = categoriesSelected.get(trainingCategory);
		if (categories == null) {
			categories = new ArrayList<>();
		}
		categories.add(categoryName);
		categoriesSelected.put(trainingCategory, categories);
	}

	public List<String> getSelectedCategory(TrainingCategory trainingCategory) {
		List<String> categories = categoriesSelected.get(trainingCategory);
		if (categories == null) {
			categories = new ArrayList<>();
		}
		return categories;
	}
}
