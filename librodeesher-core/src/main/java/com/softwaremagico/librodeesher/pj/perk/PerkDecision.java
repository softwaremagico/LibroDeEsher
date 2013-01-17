package com.softwaremagico.librodeesher.pj.perk;

import java.util.List;

public class PerkDecision {
	private List<String> categoriesToChoose;
	private List<String> skillsToChoose;

	public PerkDecision() {

	}

	public void addCategoriesChoosen(List<String> choosed) {
		categoriesToChoose = choosed;
	}

	public void addSkillsChoosen(List<String> choosed) {
		skillsToChoose = choosed;
	}
}
