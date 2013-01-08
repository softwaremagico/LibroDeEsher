package com.softwaremagico.librodeesher.pj;

import java.util.ArrayList;
import java.util.List;

import com.softwaremagico.librodeesher.pj.categories.Category;
import com.softwaremagico.librodeesher.pj.skills.Skill;

public class Historial {
	private static final Integer SKILL_BONUS = 10;
	private static final Integer CATEGORY_BONUS = 5;
	private List<String> categories;
	private List<String> skills;

	public Historial() {
		categories = new ArrayList<>();
		skills = new ArrayList<>();
	}

	public void setPoint(Skill skill, boolean value) {
		if (value) {
			if (!skills.contains(skill.getName())) {
				skills.add(skill.getName());
			}
		} else {
			skills.remove(skill.getName());
		}
	}

	public void setPoint(Category category, boolean value) {
		if (value) {
			if (!categories.contains(category.getName())) {
				categories.add(category.getName());
			}
		} else {
			categories.remove(category.getName());
		}
	}

	public Integer getBonus(Skill skill) {
		if (skills.contains(skill.getName())) {
			return SKILL_BONUS;
		}
		return 0;
	}

	public Integer getBonus(Category category) {
		if (categories.contains(category.getName())) {
			return CATEGORY_BONUS;
		}
		return 0;
	}

	public Integer getSpentHistoryPoints() {
		return skills.size() + categories.size();
	}
}
