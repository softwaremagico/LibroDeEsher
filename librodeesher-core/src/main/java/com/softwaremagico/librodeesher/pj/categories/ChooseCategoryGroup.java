package com.softwaremagico.librodeesher.pj.categories;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.softwaremagico.librodeesher.basics.ChooseGroup;
import com.softwaremagico.librodeesher.basics.ChooseType;

public class ChooseCategoryGroup extends ChooseGroup<Category> {

	public ChooseCategoryGroup(int chooseNumber, List<Category> categoryGroup, ChooseType chooseType) {
		super(chooseNumber, categoryGroup, chooseType);
	}

	public ChooseCategoryGroup(int chooseNumber, String[] categoryGroup, ChooseType chooseType)
			throws InvalidCategoryException {
		super(chooseType);
		setNumberOfOptionsToChoose(chooseNumber);
		for (String categoryName : categoryGroup) {
			Category category = CategoryFactory.getCategory(categoryName);
			if (category != null) {
				getOptionsGroup().add(category);
			} else {
				throw new InvalidCategoryException("Error leyendo un conjunto de categorias. Fallo en: " + categoryName);
			}
		}
	}

	@Override
	public List<String> getOptionsAsString() {
		List<String> nameList = new ArrayList<>();
		for (Category category : getOptionsGroup()) {
			nameList.add(category.getName());
		}
		return Collections.unmodifiableList(nameList);
	}

}
