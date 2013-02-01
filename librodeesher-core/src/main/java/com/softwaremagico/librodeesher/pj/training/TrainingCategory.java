package com.softwaremagico.librodeesher.pj.training;

import java.util.ArrayList;
import java.util.List;

public class TrainingCategory {
	private String name;
	private Integer categoryRanks;
	private Integer minSkills;
	private Integer maxSkills;
	private Integer skillRanks;
	private List<String> categoryOptions; // List to choose one category from.
	private List<TrainingSkill> skills;

	public TrainingCategory(String name, Integer categoryRanks, Integer minSkills, Integer maxSkills,
			Integer skillRanks) {
		this.name = name;
		this.categoryRanks = categoryRanks;
		this.minSkills = minSkills;
		this.maxSkills = maxSkills;
		skills = new ArrayList<>();
		categoryOptions = new ArrayList<>();
		this.skillRanks = skillRanks;
	}

	public TrainingCategory(List<String> categoryOptions, Integer ranks, Integer minSkills,
			Integer maxSkills, Integer skillRanks) {
		this.name = null;
		this.categoryOptions = categoryOptions;
		this.categoryRanks = ranks;
		this.minSkills = minSkills;
		this.maxSkills = maxSkills;
		this.skillRanks = skillRanks;
	}

	protected void addSkill(TrainingSkill skill) {
		skills.add(skill);
	}

	public String getName() {
		return name;
	}

	public Integer getMinSkills() {
		return minSkills;
	}

	public Integer getMaxSkills() {
		return maxSkills;
	}

	public Integer getSkillRanks() {
		return skillRanks;
	}

	public List<TrainingSkill> getSkills() {
		return skills;
	}

	public boolean needToChooseOneCategory() {
		return categoryOptions.size() > 1;
	}

	public boolean needToChooseOneSkill() {
		return skills.size() > 1;
	}
}
