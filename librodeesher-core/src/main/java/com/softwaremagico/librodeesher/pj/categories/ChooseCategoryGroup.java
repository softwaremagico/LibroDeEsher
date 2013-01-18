package com.softwaremagico.librodeesher.pj.categories;

import java.util.ArrayList;
import java.util.List;

import com.softwaremagico.librodeesher.basics.ChooseGroup;
import com.softwaremagico.librodeesher.basics.ChooseType;
import com.softwaremagico.librodeesher.basics.ShowMessage;

public class ChooseCategoryGroup extends ChooseGroup<Category> {

	public ChooseCategoryGroup(int chooseNumber, List<Category> categoryGroup, ChooseType chooseType) {
		super(chooseNumber, categoryGroup, chooseType);
	}

	public ChooseCategoryGroup(int chooseNumber, String[] categoryGroup, ChooseType chooseType) {
		super(chooseType);
		this.numberOfOptionsToChoose = chooseNumber;
		for (String categoryName : categoryGroup) {
			Category category = CategoryFactory.getCategory(categoryName);
			if (category != null) {
				this.optionsGroup.add(category);
			} else {
				ShowMessage.showErrorMessage("Error leyendo un conjunto de categorias. Fallo en: "
						+ categoryName, "Leer Profesión");
			}
		}
	}

	@Override
	public List<String> getOptionsAsString() {
		List<String> nameList = new ArrayList<>();
		for (Category category : optionsGroup) {
			nameList.add(category.getName());
		}
		return nameList;
	}

}
