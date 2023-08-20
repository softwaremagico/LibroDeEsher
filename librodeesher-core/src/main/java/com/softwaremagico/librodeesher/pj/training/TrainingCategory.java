package com.softwaremagico.librodeesher.pj.training;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrainingCategory {
	private Integer categoryRanks;
	private Integer minSkills;
	private Integer maxSkills;
	private Integer skillRanks;
	private List<String> categoryOptions; // List to choose one category from.
	private Map<String, TrainingSkillList> skillsPerCategory;

	public TrainingCategory(List<String> categoryOptions, Integer ranks, Integer minSkills, Integer maxSkills, Integer skillRanks) {
		this.categoryOptions = categoryOptions;
		this.categoryRanks = ranks;
		this.minSkills = minSkills;
		this.maxSkills = maxSkills;
		this.skillRanks = skillRanks;
		skillsPerCategory = new HashMap<>();
	}

	/**
	 * Add a skill to the only category option that exists.
	 * 
	 * @param skill
	 */
	protected void addSkill(TrainingSkill skill) {
		// A training only has defined skills if a category is not an option.
		// Therefore, categoryOptions has size 1;
		String categoryName = categoryOptions.get(0);
		TrainingSkillList skillList = skillsPerCategory.get(categoryName);
		if (skillList == null) {
			skillList = new TrainingSkillList();
		}
		skillList.add(skill);
		skillsPerCategory.put(categoryName, skillList);
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

	public List<TrainingSkill> getSkills(String categoryName) {
		if (skillsPerCategory.get(categoryName) != null) {
			return skillsPerCategory.get(categoryName).getAll();
		}
		return null;
	}

	public boolean needToChooseOneCategory() {
		return categoryOptions.size() > 1;
	}

	public boolean needToChooseOneSkill(String categoryName) {
		return skillsPerCategory.get(categoryName).size() > 1;
	}

	public List<String> getCategoryOptions() {
		return categoryOptions;
	}

	public Integer getRanksInSkills(String categoryName) {
		Integer total = 0;
		List<TrainingSkill> skills = skillsPerCategory.get(categoryName).getAll();
		for (TrainingSkill skill : skills) {
			total += skill.getRanks();
		}
		return total;
	}

	public boolean mustAddAllSkills(String categoryName) {
		if (skillsPerCategory.get(categoryName) == null) {
			return true;
		}
		if (skillsPerCategory.get(categoryName).isEmpty()) {
			return true;
		}
		if (getRanksInSkills(categoryName) < skillRanks) {
			return true;
		}
		return false;
	}

	public Integer getCategoryRanks() {
		return categoryRanks;
	}

	public Map<String, TrainingSkillList> getSkillsPerCategory() {
		return skillsPerCategory;
	}
}
