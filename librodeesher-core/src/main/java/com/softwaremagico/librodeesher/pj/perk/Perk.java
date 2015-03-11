package com.softwaremagico.librodeesher.pj.perk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.softwaremagico.librodeesher.basics.ChooseType;
import com.softwaremagico.librodeesher.pj.categories.Category;
import com.softwaremagico.librodeesher.pj.categories.ChooseCategoryGroup;
import com.softwaremagico.librodeesher.pj.characteristic.CharacteristicsAbbreviature;
import com.softwaremagico.librodeesher.pj.perk.exceptions.InvalidPerkDefinition;
import com.softwaremagico.librodeesher.pj.skills.ChooseSkillGroup;
import com.softwaremagico.librodeesher.pj.skills.Skill;
import com.softwaremagico.librodeesher.pj.skills.SkillFactory;
import com.softwaremagico.log.EsherLog;

public class Perk {

	private String name;
	private Integer cost;
	private String description;
	private List<String> avalibleToRaces;
	private List<String> avalibleToProfessions;
	private PerkCategory category;
	private Map<String, Integer> categoryBonus;
	private Map<String, Integer> conditionalCategoryBonus;
	private Map<String, Integer> categoryRanks;
	private Map<String, Integer> skillBonus;
	private Map<String, Integer> skillRanks;
	private Map<String, Integer> conditionalSkillBonus;
	private Map<String, Integer> resistanceBonus;
	private Map<CharacteristicsAbbreviature, Integer> characteristicBonus;
	private Map<String, Integer> categoryWithRestricted;
	private List<String> commonSkills;
	private List<String> commonCategories;
	private List<String> restrictedSkills;
	private List<String> restrictedCategories;
	private List<ChooseCategoryGroup> categoriesToChoose;
	private List<ChooseSkillGroup> skillsToChoose;
	private List<ChooseSkillGroup> commonSkillsToChoose;
	private Integer appareanceBonus;
	private Integer armourClass;
	private Integer chosenBonus;
	private Integer movementBonus;

	public Perk(String name, Integer cost, PerkCategory classification, String description,
			List<String> avalibleToRaces, List<String> avalibleToProfessions) {
		this.name = name;
		this.cost = cost;
		this.description = description;
		this.avalibleToRaces = avalibleToRaces;
		this.avalibleToProfessions = avalibleToProfessions;
		this.category = classification;
		categoryBonus = new HashMap<>();
		skillBonus = new HashMap<>();
		resistanceBonus = new HashMap<>();
		categoryWithRestricted = new HashMap<>();
		characteristicBonus = new HashMap<>();
		conditionalCategoryBonus = new HashMap<>();
		conditionalSkillBonus = new HashMap<>();
		skillRanks = new HashMap<>();
		categoryRanks = new HashMap<>();
		commonSkills = new ArrayList<>();
		restrictedSkills = new ArrayList<>();
		commonCategories = new ArrayList<>();
		restrictedCategories = new ArrayList<>();
		categoriesToChoose = new ArrayList<>();
		skillsToChoose = new ArrayList<>();
		commonSkillsToChoose = new ArrayList<>();
		appareanceBonus = 0;
		armourClass = 1;
		chosenBonus = 0;
		movementBonus = 0;
	}

	public void setCategoryBonus(String categoryName, Integer bonus) {
		if (bonus == null || bonus <= 0) {
			categoryBonus.remove(categoryName);
		} else {
			categoryBonus.put(categoryName, bonus);
		}
	}

	public void setCategoriesConditionalBonus(String categoryName, Integer bonus) {
		if (bonus == null || bonus <= 0) {
			conditionalCategoryBonus.remove(categoryName);
		} else {
			conditionalCategoryBonus.put(categoryName, bonus);
		}
	}

	public void setSkillBonus(String skillName, Integer bonus) {
		if (bonus == null || bonus <= 0) {
			skillBonus.remove(skillName);
		} else {
			skillBonus.put(skillName, bonus);
		}
	}

	public Integer getBonus(Skill skill) {
		Integer bonus = skillBonus.get(skill.getName());
		if (bonus == null) {
			return 0;
		}
		return bonus;
	}

	public Integer getConditionalBonus(Skill skill) {
		Integer bonus = getConditionalSkillBonus().get(skill.getName());
		if (bonus == null) {
			return 0;
		}
		return bonus;
	}

	public Integer getBonus(Category category) {
		Integer bonus = categoryBonus.get(category.getName());
		if (bonus == null) {
			return 0;
		}
		return bonus;
	}

	public Integer getConditionalBonus(Category category) {
		Integer bonus = getConditionalCategoryBonus().get(category.getName());
		if (bonus == null) {
			return 0;
		}
		return bonus;
	}

	public Integer getRanks(Category category) {
		Integer ranks = categoryRanks.get(category.getName());
		if (ranks == null) {
			return 0;
		}
		return ranks;
	}

	public Integer getRanks(Skill skill) {
		Integer ranks = skillRanks.get(skill.getName());
		if (ranks == null) {
			return 0;
		}
		return ranks;
	}

	public void setSkillConditionalBonus(String skillName, Integer bonus) {
		if (bonus == null || bonus <= 0) {
			conditionalSkillBonus.remove(skillName);
		} else {
			conditionalSkillBonus.put(skillName, bonus);
		}
	}

	public void setSkillRanks(String skillName, Integer ranks) {
		if (ranks == null || ranks <= 0) {
			skillRanks.remove(skillName);
		} else {
			skillRanks.put(skillName, ranks);
		}
	}

	public void setCategoryRanks(String categoryName, Integer ranks) {
		if (ranks == null || ranks <= 0) {
			categoryRanks.remove(categoryName);
		} else {
			categoryRanks.put(categoryName, ranks);
		}
	}

	public void setResistanceBonus(String resistance, Integer bonus) {
		if (bonus == null || bonus <= 0) {
			resistanceBonus.remove(resistance);
		} else {
			resistanceBonus.put(resistance, bonus);
		}
	}

	public void setCharacteristicBonus(CharacteristicsAbbreviature characteristic, Integer bonus) {
		if (bonus == null || bonus <= 0) {
			characteristicBonus.remove(characteristic);
		} else {
			characteristicBonus.put(characteristic, bonus);
		}
	}

	public void setCategoryToSelectCommonSkills(Category category, Integer commonSkills) throws InvalidPerkDefinition {
		try {
			commonSkillsToChoose.add(new ChooseSkillGroup(commonSkills, SkillFactory.getSkills(category),
					ChooseType.COMMON));
		} catch (Exception e) {
			EsherLog.errorMessage(this.getClass().getName(), e);
			throw new InvalidPerkDefinition("Invalid common skill definition in Category '" + category + "' for Perk '"
					+ name + "',");
		}
	}

	public void setCategoryToSelectRestrictedSkills(String categoryName, Integer restrictedSkills) {
		if (restrictedSkills == null || restrictedSkills <= 0) {
			categoryWithRestricted.remove(categoryName);
		} else {
			categoryWithRestricted.put(categoryName, restrictedSkills);
		}
	}

	public void setSkillAsCommon(String skillName, boolean common) {
		if (common == false) {
			commonSkills.remove(skillName);
		} else {
			if (!commonSkills.contains(skillName)) {
				commonSkills.add(skillName);
			}
		}
	}

	/**
	 * If a category is common, means that all its skills are common.
	 * 
	 * @param categoryName
	 * @param common
	 */
	public void setCategoryAsCommon(String categoryName, boolean common) {
		if (common == false) {
			commonCategories.remove(categoryName);
		} else {
			if (!commonCategories.contains(categoryName)) {
				commonCategories.add(categoryName);
			}
		}
	}

	public void setSkillAsRestricted(String skillName, boolean restricted) {
		if (restricted == false) {
			restrictedSkills.remove(skillName);
		} else {
			if (!restrictedSkills.contains(skillName)) {
				restrictedSkills.add(skillName);
			}
		}
	}

	/**
	 * If a category is restricted, means that all its skills are restricted.
	 * 
	 * @param categoryName
	 * @param common
	 */
	public void setCategoryAsRestricted(String categoryName, boolean restricted) {
		if (restricted == false) {
			restrictedCategories.remove(categoryName);
		} else {
			if (!restrictedCategories.contains(categoryName)) {
				restrictedCategories.add(categoryName);
			}
		}
	}

	public void setAppareanceBonus(Integer bonus) {
		if (bonus != null) {
			appareanceBonus = bonus;
		} else {
			appareanceBonus = new Integer(0);
		}
	}

	public void setArmour(Integer armourClass) {
		if (armourClass != null) {
			this.armourClass = armourClass;
		} else {
			this.armourClass = new Integer(1);
		}
	}

	public String getName() {
		return name;
	}

	/**
	 * If a perk is restricted to a race or a profession the character must have selected this race or profession. If
	 * not, the perk is no allowed to be used.
	 * 
	 * @param raceName
	 * @param professionName
	 * @return
	 */
	public boolean isPerkAllowed(String raceName, String professionName) {
		if (avalibleToRaces.isEmpty() && avalibleToProfessions.isEmpty()) {
			return true;
		}
		if (avalibleToRaces.contains(raceName)) {
			return true;
		}
		if (avalibleToProfessions.contains(professionName)) {
			return true;
		}
		return false;
	}

	public List<ChooseCategoryGroup> getCategoriesToChoose() {
		return categoriesToChoose;
	}

	public void addCategoriesToChoose(ChooseCategoryGroup categoryToChoose) {
		this.categoriesToChoose.add(categoryToChoose);
	}

	public List<ChooseSkillGroup> getSkillsToChoose() {
		return skillsToChoose;
	}

	public void addSkillsToChoose(ChooseSkillGroup skillToChoose) {
		if (skillToChoose.getChooseType().equals(ChooseType.COMMON)) {
			this.commonSkillsToChoose.add(skillToChoose);
		} else {
			this.skillsToChoose.add(skillToChoose);
		}
	}

	public Integer getChosenBonus() {
		return chosenBonus;
	}

	public void setChosenBonus(Integer choseBonus) {
		this.chosenBonus = choseBonus;
	}

	// /**
	// *
	// * @return
	// */
	// public Integer getNumberOfChooseOptions() {
	// if (numberOfChooseOptions >= 0) {
	// return numberOfChooseOptions;
	// } else {
	// return getOptionsToChoose().size() + numberOfChooseOptions;
	// }
	// }
	//
	// public void setChooseOptions(Integer chooseOptions) {
	// this.numberOfChooseOptions = chooseOptions;
	// }

	public Integer getCost() {
		return cost;
	}

	public PerkCategory getCategory() {
		return category;
	}

	private String getBonusesDescription() {
		String bonuses = "";
		for (String category : categoryBonus.keySet()) {
			if (bonuses.length() > 1) {
				bonuses += ", ";
			}
			bonuses += category + " (" + categoryBonus.get(category) + ")";
		}

		for (String skill : skillBonus.keySet()) {
			if (bonuses.length() > 1) {
				bonuses += ", ";
			}
			bonuses += skill + " (" + skillBonus.get(skill) + ")";
		}

		for (String category : conditionalCategoryBonus.keySet()) {
			if (bonuses.length() > 1) {
				bonuses += ", ";
			}
			bonuses += category + " (" + conditionalCategoryBonus.get(category) + "*)";
		}

		for (String skill : conditionalSkillBonus.keySet()) {
			if (bonuses.length() > 1) {
				bonuses += ", ";
			}
			bonuses += skill + " (" + conditionalSkillBonus.get(skill) + "*)";
		}
		return bonuses;
	}

	public boolean isSelectionableOptions() {
		return (categoriesToChoose.size() > 0 || skillsToChoose.size() > 0);
	}

	public boolean isCategorySelectable(String optionName) {
		for (ChooseCategoryGroup categoryToChose : categoriesToChoose) {
			if (categoryToChose.getOptionsAsString().contains(optionName)) {
				return true;
			}
		}
		return false;
	}

	public boolean isSkillSelectable(String optionName) {
		for (ChooseSkillGroup skillToChose : skillsToChoose) {
			if (skillToChose.getOptionsAsString().contains(optionName)) {
				return true;
			}
		}
		return false;
	}

	public String getSelectionableDescription() {
		String options = "";
		for (ChooseCategoryGroup optionGroup : categoriesToChoose) {
			for (String category : optionGroup.getOptionsAsString()) {
				if (options.length() > 1) {
					options += ", ";
				}
				options += category;
			}
		}

		for (ChooseSkillGroup optionGroup : skillsToChoose) {
			for (String skill : optionGroup.getOptionsAsString()) {
				if (options.length() > 1) {
					options += ", ";
				}
				options += skill;
			}
		}
		return options;
	}

	public String getLongDescription() {
		String longDescription = getBonusesDescription();
		if (isSelectionableOptions()) {
			longDescription += "{Selección}";
		}
		if (longDescription.length() > 0) {
			longDescription += ". ";
		}
		longDescription += description;
		return longDescription;
	}

	public Integer getAppareanceBonus() {
		return appareanceBonus;
	}

	public Integer getCharacteristicBonus(CharacteristicsAbbreviature characteristic) {
		Integer bonus = characteristicBonus.get(characteristic);
		if (bonus != null) {
			return bonus;
		}
		return 0;
	}

	public boolean isCommon(Skill skill) {
		return commonSkills.contains(skill.getName());
	}

	public boolean isRestricted(Skill skill) {
		return restrictedSkills.contains(skill.getName());
	}

	public List<ChooseSkillGroup> getCommonSkillsToChoose() {
		return commonSkillsToChoose;
	}

	protected String getDescription() {
		return description;
	}

	protected void setDescription(String description) {
		this.description = description;
	}

	protected List<String> getAvalibleToRaces() {
		return avalibleToRaces;
	}

	protected void setAvalibleToRaces(List<String> avalibleToRaces) {
		this.avalibleToRaces = avalibleToRaces;
	}

	protected List<String> getAvalibleToProfessions() {
		return avalibleToProfessions;
	}

	protected void setAvalibleToProfessions(List<String> avalibleToProfessions) {
		this.avalibleToProfessions = avalibleToProfessions;
	}

	protected Map<String, Integer> getCategoryBonus() {
		return categoryBonus;
	}

	protected void setCategoryBonus(HashMap<String, Integer> categoryBonus) {
		this.categoryBonus = categoryBonus;
	}

	public Map<String, Integer> getConditionalCategoryBonus() {
		return conditionalCategoryBonus;
	}

	protected void setConditionalCategoryBonus(HashMap<String, Integer> conditionalCategoryBonus) {
		this.conditionalCategoryBonus = conditionalCategoryBonus;
	}

	protected Map<String, Integer> getCategoryRanks() {
		return categoryRanks;
	}

	protected void setCategoryRanks(HashMap<String, Integer> categoryRanks) {
		this.categoryRanks = categoryRanks;
	}

	protected Map<String, Integer> getSkillBonus() {
		return skillBonus;
	}

	protected void setSkillBonus(HashMap<String, Integer> skillBonus) {
		this.skillBonus = skillBonus;
	}

	protected Map<String, Integer> getSkillRanks() {
		return skillRanks;
	}

	protected void setSkillRanks(HashMap<String, Integer> skillRanks) {
		this.skillRanks = skillRanks;
	}

	public Map<String, Integer> getConditionalSkillBonus() {
		return conditionalSkillBonus;
	}

	protected void setConditionalSkillBonus(HashMap<String, Integer> conditionalSkillBonus) {
		this.conditionalSkillBonus = conditionalSkillBonus;
	}

	protected Map<String, Integer> getResistanceBonus() {
		return resistanceBonus;
	}

	protected void setResistanceBonus(HashMap<String, Integer> resistanceBonus) {
		this.resistanceBonus = resistanceBonus;
	}

	protected Map<CharacteristicsAbbreviature, Integer> getCharacteristicBonus() {
		return characteristicBonus;
	}

	protected void setCharacteristicBonus(HashMap<CharacteristicsAbbreviature, Integer> characteristicBonus) {
		this.characteristicBonus = characteristicBonus;
	}

	protected Map<String, Integer> getCategoryWithRestricted() {
		return categoryWithRestricted;
	}

	protected void setCategoryWithRestricted(HashMap<String, Integer> categoryWithRestricted) {
		this.categoryWithRestricted = categoryWithRestricted;
	}

	protected List<String> getCommonSkills() {
		return commonSkills;
	}

	protected void setCommonSkills(List<String> commonSkills) {
		this.commonSkills = commonSkills;
	}

	protected List<String> getCommonCategories() {
		return commonCategories;
	}

	protected void setCommonCategories(List<String> commonCategories) {
		this.commonCategories = commonCategories;
	}

	protected List<String> getRestrictedSkills() {
		return restrictedSkills;
	}

	protected void setRestrictedSkills(List<String> restrictedSkills) {
		this.restrictedSkills = restrictedSkills;
	}

	protected List<String> getRestrictedCategories() {
		return restrictedCategories;
	}

	protected void setRestrictedCategories(List<String> restrictedCategories) {
		this.restrictedCategories = restrictedCategories;
	}

	public Integer getArmourClass() {
		return armourClass;
	}

	protected void setArmourClass(Integer armourClass) {
		this.armourClass = armourClass;
	}

	protected void setName(String name) {
		this.name = name;
	}

	protected void setCost(Integer cost) {
		this.cost = cost;
	}

	protected void setCategory(PerkCategory category) {
		this.category = category;
	}

	protected void setCategoriesToChoose(List<ChooseCategoryGroup> categoriesToChoose) {
		this.categoriesToChoose = categoriesToChoose;
	}

	protected void setSkillsToChoose(List<ChooseSkillGroup> skillsToChoose) {
		this.skillsToChoose = skillsToChoose;
	}

	protected void setCommonSkillsToChoose(List<ChooseSkillGroup> commonSkillsToChoose) {
		this.commonSkillsToChoose = commonSkillsToChoose;
	}

	protected void setCategoryBonus(Map<String, Integer> categoryBonus) {
		this.categoryBonus = categoryBonus;
	}

	protected void setConditionalCategoryBonus(Map<String, Integer> conditionalCategoryBonus) {
		this.conditionalCategoryBonus = conditionalCategoryBonus;
	}

	protected void setCategoryRanks(Map<String, Integer> categoryRanks) {
		this.categoryRanks = categoryRanks;
	}

	protected void setSkillBonus(Map<String, Integer> skillBonus) {
		this.skillBonus = skillBonus;
	}

	protected void setSkillRanks(Map<String, Integer> skillRanks) {
		this.skillRanks = skillRanks;
	}

	protected void setConditionalSkillBonus(Map<String, Integer> conditionalSkillBonus) {
		this.conditionalSkillBonus = conditionalSkillBonus;
	}

	protected void setResistanceBonus(Map<String, Integer> resistanceBonus) {
		this.resistanceBonus = resistanceBonus;
	}

	protected void setCharacteristicBonus(Map<CharacteristicsAbbreviature, Integer> characteristicBonus) {
		this.characteristicBonus = characteristicBonus;
	}

	protected void setCategoryWithRestricted(Map<String, Integer> categoryWithRestricted) {
		this.categoryWithRestricted = categoryWithRestricted;
	}

	public Integer getMovementBonus() {
		return movementBonus;
	}

	public void setMovementBonus(Integer movementBonus) {
		this.movementBonus = movementBonus;
	}
}
