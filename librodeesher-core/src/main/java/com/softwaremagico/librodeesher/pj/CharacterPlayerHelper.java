package com.softwaremagico.librodeesher.pj;

import java.util.HashMap;
import java.util.Map;

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
	private Map<String, Integer> skillGeneralBonus;
	private Map<String, Integer> skillObjectsBonus;
	private Map<String, Integer> skillTotalBonus;
	private Map<String, Integer> skillTotal;

	private Map<CharacteristicsAbbreviature, Integer> temporalValues;

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
		setDirty(true);
	}

	public void resetAllSkillGeneralBonus() {
		skillGeneralBonus = new HashMap<>();
		resetAllSkillTotal();
		setDirty(true);
	}

	public void resetAllSkillTotal() {
		skillTotal = new HashMap<>();
		setDirty(true);
	}

	public void resetAllSkillRanks() {
		skillRanks = new HashMap<>();
		resetAllSkillTotal();
		setDirty(true);
	}

	public void resetAllCategoryGeneralBonus() {
		categoryGeneralBonus = new HashMap<>();
		resetAllCategoryTotalBonus();
		setDirty(true);
	}

	public void resetAllCategoryCharacteristicsBonus() {
		categoryCharacteristicsBonus = new HashMap<>();
		resetAllCategoryTotal();
		setDirty(true);
	}

	public void resetAllCategoryTotalBonus() {
		categoryTotalBonus = new HashMap<>();
		resetAllCategoryTotal();
		setDirty(true);
	}

	public void resetAllCategoryTotal() {
		categoryTotal = new HashMap<>();
		setDirty(true);
	}

	public void resetCategoryRanks(String categoryName) {
		categoryRanks.remove(categoryName);
		resetCategoryTotal(categoryName);
		developmentPoints = null;
		setDirty(true);
	}

	public void resetCategoryCharacteristicsBonus(String categoryName) {
		categoryCharacteristicsBonus.remove(categoryName);
		resetCategoryTotal(categoryName);
		setDirty(true);
	}

	public void resetCategoryGeneralBonus(String categoryName) {
		categoryGeneralBonus.remove(categoryName);
		resetCategoryTotalBonus(categoryName);
		setDirty(true);
	}

	public void resetCategoryObjectBonus(String categoryName) {
		categoryObjectBonus.remove(categoryName);
		resetCategoryTotalBonus(categoryName);
		setDirty(true);
	}

	public void resetCategoryTotalBonus(String categoryName) {
		categoryTotalBonus.remove(categoryName);
		resetCategoryTotal(categoryName);
		setDirty(true);
	}

	public void resetCategoryTotal(String categoryName) {
		categoryTotal.remove(categoryName);
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
		resetSkillTotalBonus(skillName);
		resetDelvelopmentPoints();
		setDirty(true);
	}

	public void resetSkillGeneralBonus(String skillName) {
		skillGeneralBonus.remove(skillName);
		resetSkillTotalBonus(skillName);
		setDirty(true);
	}

	public void resetSkillObjectBonus(String skillName) {
		skillObjectsBonus.remove(skillName);
		resetSkillTotalBonus(skillName);
		setDirty(true);
	}

	public void resetSkillTotalBonus(String skillName) {
		skillTotalBonus.remove(skillName);
		resetSkillTotal(skillName);
		setDirty(true);
	}

	public void resetSkillTotal(String skillName) {
		skillTotal.remove(skillName);
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
		resetDelvelopmentPoints();
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
		resetDelvelopmentPoints();
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

	public void resetDelvelopmentPoints() {
		developmentPoints = null;
	}

	public boolean isDirty() {
		return isDirty;
	}

	public void setDirty(boolean isDirty) {
		this.isDirty = isDirty;
	}

	public Integer getCharacteristicTemporalValue(CharacteristicsAbbreviature abbreviature) {
		return temporalValues.get(abbreviature);
	}

	public void setCharacteristicTemporalValue(CharacteristicsAbbreviature abbreviature, Integer value) {
		temporalValues.put(abbreviature, value);
	}

	public void resetCharacteristicTemporalValues() {
		temporalValues = new HashMap<>();
	}

}
