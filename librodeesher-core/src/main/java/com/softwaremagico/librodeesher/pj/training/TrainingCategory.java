package com.softwaremagico.librodeesher.pj.training;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.softwaremagico.librodeesher.pj.categories.Category;
import com.softwaremagico.librodeesher.pj.categories.CategoryFactory;
import com.softwaremagico.librodeesher.pj.skills.Skill;

public class TrainingCategory {
	private Integer categoryRanks;
	private Integer minSkills;
	private Integer maxSkills;
	private Integer skillRanks;
	private List<String> categoryOptions; // List to choose one category from.
	private Hashtable<String, List<TrainingSkill>> skillsPerCategory;

	public TrainingCategory(List<String> categoryOptions, Integer ranks, Integer minSkills,
			Integer maxSkills, Integer skillRanks) {
		this.categoryOptions = categoryOptions;
		this.categoryRanks = ranks;
		this.minSkills = minSkills;
		this.maxSkills = maxSkills;
		this.skillRanks = skillRanks;
		skillsPerCategory = new Hashtable<>();
	}

	protected void addSkill(String categoryName, String skillName) {
		List<String> skillList = new ArrayList<>();
		skillList.add(skillName); // List with only one element.
		TrainingSkill trainingSkill = new TrainingSkill(skillList, 0);
		addSkill(categoryName, trainingSkill);
	}

	protected void addSkill(String categoryName, TrainingSkill skill) {
		List<TrainingSkill> skills = skillsPerCategory.get(categoryName);
		if (skills == null) {
			skills = new ArrayList<>();
		}
		skills.add(skill);
		skillsPerCategory.put(categoryName, skills);
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
		List<TrainingSkill> skills = skillsPerCategory.get(categoryName);
		if (skills == null) {
			skills = new ArrayList<>();
		}
		skills.add(skill);
		skillsPerCategory.put(categoryName, skills);
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
		return skillsPerCategory.get(categoryName);
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

	/**
	 * Add any skill if category has not preselected skills.
	 */
	public void addGeneralSkills() {
		for (String categoryName : categoryOptions) {
			if (skillsPerCategory.get(categoryName) == null || skillsPerCategory.get(categoryName).isEmpty()) {
				Category category = CategoryFactory.getCategory(categoryName);
				for (Skill skill : category.getSkills()) {
					addSkill(categoryName, skill.getName());
				}
			}
		}
	}
}
