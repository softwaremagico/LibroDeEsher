package com.softwaremagico.librodeesher.gui.elements;

import java.util.ArrayList;
import java.util.List;

import com.softwaremagico.librodeesher.pj.categories.Category;
import com.softwaremagico.librodeesher.pj.categories.CategoryFactory;

public class CategoryComboBox extends BaseComboBox<Category> {
	private static final long serialVersionUID = 7160132975462619054L;
	private List<CategoryChangedListener> categoryListeners;

	public CategoryComboBox() {
		categoryListeners = new ArrayList<>();
		for (Category category : CategoryFactory.getCategories()) {
			addItem(category);
		}
	}

	@Override
	public void doAction() {
		for (CategoryChangedListener listener : categoryListeners) {
			listener.categoryChanged((Category) this.getSelectedItem());
		}
	}

	public void addCategoryChangedListener(CategoryChangedListener listener) {
		categoryListeners.add(listener);
	}

	public void removeCategoryChangedListener(CategoryChangedListener listener) {
		categoryListeners.remove(listener);
	}
}
