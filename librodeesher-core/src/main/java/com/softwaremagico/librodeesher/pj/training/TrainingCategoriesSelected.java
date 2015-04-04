package com.softwaremagico.librodeesher.pj.training;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.google.gson.annotations.Expose;
import com.softwaremagico.persistence.StorableObject;

@Entity
@Table(name = "T_TRAINING_CATEGORIES_SELECTED")
class TrainingCategoriesSelected extends StorableObject {
	private static final long serialVersionUID = 8361001621353057429L;
	@Expose
	@ElementCollection
	@CollectionTable(name = "T_TRAINING_CATEGORIES")
	@LazyCollection(LazyCollectionOption.FALSE)
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
