package com.softwaremagico.librodeesher.pj;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.softwaremagico.librodeesher.pj.categories.CategoryFactory;
import com.softwaremagico.librodeesher.pj.characteristic.CharacteristicsAbbreviature;
import com.softwaremagico.librodeesher.pj.skills.Skill;
import com.softwaremagico.log.EsherLog;

/**
 * Increases the performance of the character creator.
 */
public class CharacterPlayerHelper {
	private Map<String, Integer> categoryRanks;
	private Map<String, Integer> categoryCharacteristicsBonus;
	private Map<String, Integer> categoryGeneralBonus;
	private Map<String, Integer> categoryObjectBonus;
	private Map<String, Integer> categoryTotalBonus;
	private Map<String, Integer> categoryTotal;

	private Map<String, Integer> skillRanks;
	private Map<String, Integer> skillRealRanks;
	private Map<String, Integer> skillPreviousRanks;
	private Map<String, Integer> skillGeneralBonus;
	private Map<String, Integer> skillObjectsBonus;
	private Map<String, Integer> skillTotalBonus;
	private Map<String, Integer> skillTotal;
	private Map<String, Integer> skillTotalRanksPerCategory;

	private Map<CharacteristicsAbbreviature, Integer> temporalValues;

	private Map<String, Integer> trainingCosts;

	private Map<String, Integer> maxHistoryLanguages;

	private Map<String, Boolean> interestingCategories;

	private Map<String, Boolean> interestingSkills;

	private Set<String> favouriteSkills;

	private boolean enabled = true;

	private Integer developmentPoints;

	// Player has been changed and must be updated.
	private boolean isDirty;

	public CharacterPlayerHelper() {
		resetAll();
	}

	public void resetAll() {
		categoryRanks = new HashMap<>();
		categoryCharacteristicsBonus = new HashMap<>();
		categoryGeneralBonus = new HashMap<>();
		categoryObjectBonus = new HashMap<>();
		categoryTotalBonus = new HashMap<>();
		categoryTotal = new HashMap<>();
		skillRanks = new HashMap<>();
		skillGeneralBonus = new HashMap<>();
		skillObjectsBonus = new HashMap<>();
		skillTotalBonus = new HashMap<>();
		skillTotal = new HashMap<>();
		developmentPoints = null;
		temporalValues = new HashMap<>();
		trainingCosts = new HashMap<>();
		maxHistoryLanguages = new HashMap<>();
		skillTotalRanksPerCategory = new HashMap<>();
		skillPreviousRanks = new HashMap<>();
		skillRealRanks = new HashMap<>();
		interestingCategories = new HashMap<>();
		interestingSkills = new HashMap<>();
		favouriteSkills = new HashSet<>();
		setDirty(true);
	}

	public void resetAllSkillGeneralBonus() {
		skillGeneralBonus = new HashMap<>();
		resetAllSkillTotal();
		setDirty(true);
	}

	public void resetAllSkillTotal() {
		skillTotal = new HashMap<>();
		interestingSkills = new HashMap<>();
		setDirty(true);
	}

	public void resetAllSkillRanks() {
		skillRanks = new HashMap<>();
		skillPreviousRanks = new HashMap<>();
		skillRealRanks = new HashMap<>();
		interestingSkills = new HashMap<>();
		resetAllSkillTotal();
		resetFavouriteSkills();
		setDirty(true);
	}

	public void resetAllCategoryGeneralBonus() {
		categoryGeneralBonus = new HashMap<>();
		resetAllCategoryTotalBonus();
		interestingCategories = new HashMap<>();
		setDirty(true);
	}

	public void resetAllCategoryCharacteristicsBonus() {
		categoryCharacteristicsBonus = new HashMap<>();
		resetAllCategoryTotal();
		interestingCategories = new HashMap<>();
		setDirty(true);
	}

	public void resetAllCategoryTotalBonus() {
		categoryTotalBonus = new HashMap<>();
		resetAllCategoryTotal();
		interestingCategories = new HashMap<>();
		setDirty(true);
	}

	public void resetAllCategoryTotal() {
		categoryTotal = new HashMap<>();
		interestingCategories = new HashMap<>();
		setDirty(true);
	}

	public void resetCategoryRanks(String categoryName) {
		categoryRanks.remove(categoryName);
		resetCategoryTotal(categoryName);
		developmentPoints = null;
		interestingCategories.remove(categoryName);
		setDirty(true);
	}

	public void resetCategoryCharacteristicsBonus(String categoryName) {
		categoryCharacteristicsBonus.remove(categoryName);
		resetCategoryTotalBonus(categoryName);
	}

	public void resetCategoryGeneralBonus(String categoryName) {
		categoryGeneralBonus.remove(categoryName);
		resetCategoryTotalBonus(categoryName);
	}

	public void resetCategoryObjectBonus(String categoryName) {
		categoryObjectBonus.remove(categoryName);
		resetCategoryTotalBonus(categoryName);
	}

	public void resetCategoryTotalBonus(String categoryName) {
		categoryTotalBonus.remove(categoryName);
		resetCategoryTotal(categoryName);
	}

	public void resetCategoryTotal(String categoryName) {
		categoryTotal.remove(categoryName);
		interestingCategories.remove(categoryName);
		try {
			for (Skill skill : CategoryFactory.getCategory(categoryName).getSkills()) {
				resetSkillTotal(skill.getName());
			}
		} catch (NullPointerException npe) {
			EsherLog.severe(this.getClass().getName(), "Category '" + categoryName + "' not found!");
		}
		setDirty(true);
	}

	public void resetSkillRanks(String skillName) {
		skillRanks.remove(skillName);
		skillPreviousRanks.remove(skillName);
		skillTotalRanksPerCategory = new HashMap<>();
		skillRealRanks = new HashMap<>();
		resetSkillTotalBonus(skillName);
		resetFavouriteSkills();
		resetDevelopmentPoints();
	}

	public void resetSkillGeneralBonus(String skillName) {
		skillGeneralBonus.remove(skillName);
		resetSkillTotalBonus(skillName);
	}

	public void resetSkillObjectBonus(String skillName) {
		skillObjectsBonus.remove(skillName);
		resetSkillTotalBonus(skillName);
	}

	public void resetSkillTotalBonus(String skillName) {
		skillTotalBonus.remove(skillName);
		resetSkillTotal(skillName);
	}

	public void resetSkillTotal(String skillName) {
		skillTotal.remove(skillName);
		interestingSkills.remove(skillName);
		setDirty(true);
	}

	public Integer getCategoryRanks(String categoryName) {
		if (!enabled) {
			return null;
		}
		return categoryRanks.get(categoryName);
	}

	public void setCategoryRanks(String categoryName, Integer ranks) {
		categoryRanks.put(categoryName, ranks);
		resetCategoryTotal(categoryName);
		resetDevelopmentPoints();
		setDirty(true);
	}

	public Integer getCategoryCharacteristicsBonus(String categoryName) {
		if (!enabled) {
			return null;
		}
		return categoryCharacteristicsBonus.get(categoryName);
	}

	public void setCategoryCharacteristicsBonus(String categoryName, Integer value) {
		categoryCharacteristicsBonus.put(categoryName, value);
		resetCategoryTotal(categoryName);
		setDirty(true);
	}

	public Integer getCategoryGeneralBonus(String categoryName) {
		if (!enabled) {
			return null;
		}
		return categoryGeneralBonus.get(categoryName);
	}

	public void setCategoryGeneralBonus(String categoryName, Integer value) {
		categoryGeneralBonus.put(categoryName, value);
		resetCategoryTotalBonus(categoryName);
		setDirty(true);
	}

	public Integer getCategoryObjectBonus(String categoryName) {
		if (!enabled) {
			return null;
		}
		return categoryObjectBonus.get(categoryName);
	}

	public void setCategoryObjectBonus(String categoryName, Integer value) {
		categoryObjectBonus.put(categoryName, value);
		resetCategoryTotalBonus(categoryName);
		setDirty(true);
	}

	public void setCategoryTotalBonus(String categoryName, Integer value) {
		categoryTotalBonus.put(categoryName, value);
		resetCategoryTotal(categoryName);
		setDirty(true);
	}

	public Integer getCategoryTotalBonus(String categoryName) {
		if (!enabled) {
			return null;
		}
		return categoryTotalBonus.get(categoryName);
	}

	public Integer getCategoryTotal(String categoryName) {
		if (!enabled) {
			return null;
		}
		return categoryTotal.get(categoryName);
	}

	public void setCategoryTotal(String categoryName, Integer value) {
		categoryTotal.put(categoryName, value);
		setDirty(true);
	}

	public Integer getSkillRanks(String skillName) {
		if (!enabled) {
			return null;
		}
		return skillRanks.get(skillName);
	}

	public void setSkillRanks(String skillName, Integer ranks) {
		skillRanks.put(skillName, ranks);
		resetSkillTotalBonus(skillName);
		resetDevelopmentPoints();
		resetFavouriteSkills();
		setDirty(true);
	}

	public Integer getSkillGeneralBonus(String skillName) {
		if (!enabled) {
			return null;
		}
		return skillGeneralBonus.get(skillName);
	}

	public void setSkillGeneralBonus(String skillName, Integer value) {
		skillGeneralBonus.put(skillName, value);
		resetSkillTotalBonus(skillName);
		setDirty(true);
	}

	public Integer getSkillObjectBonus(String skillName) {
		if (!enabled) {
			return null;
		}
		return skillObjectsBonus.get(skillName);
	}

	public void setSkillObjectBonus(String skillName, Integer value) {
		skillObjectsBonus.put(skillName, value);
		resetSkillTotalBonus(skillName);
		setDirty(true);
	}

	public void setSkillTotalBonus(String skillName, Integer value) {
		skillTotalBonus.put(skillName, value);
		resetSkillTotal(skillName);
		setDirty(true);
	}

	public Integer getSkillTotalBonus(String skillName) {
		if (!enabled) {
			return null;
		}
		return skillTotalBonus.get(skillName);
	}

	public Integer getSkillTotal(String skillName) {
		if (!enabled) {
			return null;
		}
		return skillTotal.get(skillName);
	}

	public void setSkillTotal(String skillName, Integer value) {
		skillTotal.put(skillName, value);
		setDirty(true);
	}

	public Integer getDevelopmentPoints() {
		if (!enabled) {
			return null;
		}
		return developmentPoints;
	}

	public void setDevelopmentPoints(Integer developmentPoints) {
		this.developmentPoints = developmentPoints;
		setDirty(true);
	}

	public void resetDevelopmentPoints() {
		developmentPoints = null;
	}

	public boolean isDirty() {
		return isDirty;
	}

	public void setDirty(boolean isDirty) {
		this.isDirty = isDirty;
	}

	public Integer getCharacteristicTemporalValue(CharacteristicsAbbreviature abbreviature) {
		if (!enabled) {
			return null;
		}
		return temporalValues.get(abbreviature);
	}

	public void setCharacteristicTemporalValue(CharacteristicsAbbreviature abbreviature, Integer value) {
		temporalValues.put(abbreviature, value);
		setDirty(true);
	}

	public void resetCharacteristicTemporalValues() {
		temporalValues = new HashMap<>();
	}

	public void setTrainingCost(String trainingName, int cost) {
		trainingCosts.put(trainingName, cost);
	}

	public Integer getTrainingCost(String trainingName) {
		if (!enabled) {
			return null;
		}
		return trainingCosts.get(trainingName);
	}

	public Integer getSkillTotalRanksPerCategory(String categoryName) {
		if (!enabled) {
			return null;
		}
		return skillTotalRanksPerCategory.get(categoryName);
	}

	public void setSkillTotalRanksPerCategory(String categoryName, int totalRanks) {
		skillTotalRanksPerCategory.put(categoryName, totalRanks);
		setDirty(true);
	}

	public Map<String, Integer> getMaxHistoryLanguages() {
		if (!enabled) {
			return null;
		}
		return maxHistoryLanguages;
	}

	public void setMaxHistoryLanguages(Map<String, Integer> maxLanguages) {
		maxHistoryLanguages = maxLanguages;
		setDirty(true);
	}

	public Integer getSkillPreviousRanks(String skillName) {
		if (!enabled) {
			return null;
		}
		return skillPreviousRanks.get(skillName);
	}

	public void setSkillPreviousRanks(String skillName, int ranks) {
		skillPreviousRanks.put(skillName, ranks);
		setDirty(true);
	}

	public Integer getSkillRealRanks(String skillName) {
		if (!enabled) {
			return null;
		}
		return skillRealRanks.get(skillName);
	}

	public void setSkillRealRanks(String skillName, int ranks) {
		skillRealRanks.put(skillName, ranks);
		setDirty(true);
	}

	public Boolean isCategoryInteresting(String categoryName) {
		return interestingCategories.get(categoryName);
	}

	public void setCategoryInteresting(String categoryName, boolean interesting) {
		interestingCategories.put(categoryName, interesting);
	}

	public Boolean isSkillInteresting(String skillName) {
		return interestingSkills.get(skillName);
	}

	public void setSkillInteresting(String skillName, boolean interesting) {
		interestingSkills.put(skillName, interesting);
	}

	public Set<String> getFavouriteSkills() {
		return favouriteSkills;
	}

	public void setFavouriteSkills(Set<String> favouriteSkills) {
		resetFavouriteSkills();
		this.favouriteSkills.addAll(favouriteSkills);
	}

	public void resetFavouriteSkills() {
		favouriteSkills = new HashSet<>();
	}
}
