package com.softwaremagico.librodeesher.pj.training;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.softwaremagico.persistence.StorableObject;

@Entity
@Table(name = "T_TRAINING_CATEGORIES_SELECTED")
class TrainingCategoriesSelected extends StorableObject {

	@ElementCollection
	@CollectionTable(name = "T_TRAINING_CATEGORIES")
	private List<String> categories;

	public TrainingCategoriesSelected() {
		categories = new ArrayList<>();
	}

	public void add(String categoryName) {
		categories.add(categoryName);
	}

	public List<String> getAll() {
		return categories;
	}

	protected List<String> getCategories() {
		return categories;
	}

	protected void setCategories(List<String> categories) {
		this.categories = categories;
	}
}
