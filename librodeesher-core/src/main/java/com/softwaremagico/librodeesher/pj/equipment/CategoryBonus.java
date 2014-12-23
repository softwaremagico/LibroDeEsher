package com.softwaremagico.librodeesher.pj.equipment;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "T_TRAINING_OBJECT_CATEGORY_BONUS")
public class CategoryBonus extends ObjectBonus {

	@Expose
	public String category;

	public CategoryBonus() {

	}

	@Override
	public String getBonusName() {
		return category;
	}

	@Override
	public void setBonusName(String value) {
		this.category = value;
	}
}
