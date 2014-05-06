package com.softwaremagico.librodeesher.pj.training;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_TRAINING_CATEGORIES_SELECTED")
class TrainingCategoriesSelected {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name = "ID", unique = true, nullable = false)
	private Long id; // database id.
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

	protected Long getId() {
		return id;
	}

	protected void setId(Long id) {
		this.id = id;
	}

	protected List<String> getCategories() {
		return categories;
	}

	protected void setCategories(List<String> categories) {
		this.categories = categories;
	}
}
