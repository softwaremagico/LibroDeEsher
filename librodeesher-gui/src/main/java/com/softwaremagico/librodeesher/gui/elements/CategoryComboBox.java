package com.softwaremagico.librodeesher.gui.elements;

/*
 * #%L
 * Libro de Esher (GUI)
 * %%
 * Copyright (C) 2007 - 2014 Softwaremagico
 * %%
 * This software is designed by Jorge Hortelano Otero. Jorge Hortelano Otero
 * <softwaremagico@gmail.com> Valencia (Spain).
 *  
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *  
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *  
 * You should have received a copy of the GNU General Public License along with
 * this program; If not, see <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import java.util.ArrayList;
import java.util.List;

import com.softwaremagico.librodeesher.pj.categories.Category;
import com.softwaremagico.librodeesher.pj.categories.CategoryFactory;

public class CategoryComboBox extends BaseComboBox<Category> {
	private static final long serialVersionUID = 7160132975462619054L;
	private List<CategoryChangedListener> categoryListeners;

	public CategoryComboBox() {
		this(CategoryFactory.getCategories());
	}

	public CategoryComboBox(List<Category> categories) {
		categoryListeners = new ArrayList<>();
		for (Category category : categories) {
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
