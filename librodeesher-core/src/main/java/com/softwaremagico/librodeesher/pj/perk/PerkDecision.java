package com.softwaremagico.librodeesher.pj.perk;

import java.util.List;

import com.softwaremagico.librodeesher.pj.categories.Category;
import com.softwaremagico.librodeesher.pj.skills.Skill;

public class PerkDecision {
	private List<String> categoriesToChoose;
	private List<String> skillsToChoose;

	public PerkDecision() {

	}

	public void addCategoriesChose(List<String> choosed) {
		categoriesToChoose = choosed;
	}

	public void addSkillsChose(List<String> choosed) {
		skillsToChoose = choosed;
	}

	public boolean isChose(Skill skill) {
		return skillsToChoose.contains(skill.getName());
	}

	public boolean isChose(Category category) {
		return categoriesToChoose.contains(category.getName());
	}
}
