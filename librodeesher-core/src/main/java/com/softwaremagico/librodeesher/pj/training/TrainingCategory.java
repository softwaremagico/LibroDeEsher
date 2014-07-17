package com.softwaremagico.librodeesher.pj.training;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.softwaremagico.librodeesher.pj.categories.Category;
import com.softwaremagico.librodeesher.pj.categories.CategoryFactory;
import com.softwaremagico.librodeesher.pj.skills.Skill;

public class TrainingCategory {
	private Integer categoryRanks;
	private Integer minSkills;
	private Integer maxSkills;
	private Integer skillRanks;
	private List<String> categoryOptions; // List to choose one category from.
	private Map<String, TrainingSkillList> skillsPerCategory;

	public TrainingCategory(List<String> categoryOptions, Integer ranks, Integer minSkills,
			Integer maxSkills, Integer skillRanks) {
		this.categoryOptions = categoryOptions;
		this.categoryRanks = ranks;
		this.minSkills = minSkills;
		this.maxSkills = maxSkills;
		this.skillRanks = skillRanks;
		skillsPerCategory = new HashMap<>();
	}

	protected void addSkill(String categoryName, String skillName) {
		List<String> skillList = new ArrayList<>();
		skillList.add(skillName); // List with only one element.
		TrainingSkill trainingSkill = new TrainingSkill(skillList, 0);
		addSkill(categoryName, trainingSkill);
	}

	protected void addSkill(String categoryName, TrainingSkill skill) {
		TrainingSkillList skillList = skillsPerCategory.get(categoryName);
		if (skillList == null) {
			skillList = new TrainingSkillList();
		}
		if (!skillList.getAll().contains(skill)) {
			skillList.add(skill);
			Collections.sort(skillList.getAll(), new TrainingSkillComparator());
			skillsPerCategory.put(categoryName, skillList);
		}
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
		return skillsPerCategory.get(categoryName).getAll();
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
		List<TrainingSkill> skills = skillsPerCategory.get(categoryName)
				.getAll();
		for (TrainingSkill skill : skills) {
			total += skill.getRanks();
		}
		return total;
	}

	private boolean mustAddGeneralSkills(String categoryName) {
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

	/**
	 * Add any skill if category has not preselected skills.
	 */
	public void addGeneralSkills() {
		for (String categoryName : categoryOptions) {
			if (mustAddGeneralSkills(categoryName)) {
				Category category = CategoryFactory.getCategory(categoryName);
				for (Skill skill : category.getSkills()) {
					addSkill(categoryName, skill.getName());
				}
			}
		}
	}
}
