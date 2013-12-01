package com.softwaremagico.librodeesher.pj.training;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TrainingDecision {
	private HashMap<TrainingCategory, List<String>> categoriesSelected;
	private HashMap<TrainingCategory, List<String>> skillsSelected;

	public TrainingDecision() {
		categoriesSelected = new HashMap<>();
		skillsSelected = new HashMap<>();
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
