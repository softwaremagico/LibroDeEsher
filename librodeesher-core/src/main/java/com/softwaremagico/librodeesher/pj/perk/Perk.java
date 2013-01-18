package com.softwaremagico.librodeesher.pj.perk;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.softwaremagico.librodeesher.pj.categories.Category;
import com.softwaremagico.librodeesher.pj.characteristic.Characteristic;
import com.softwaremagico.librodeesher.pj.skills.Skill;

public class Perk {
	private String name;
	private Integer cost;
	private String description;
	private List<String> avalibleToRaces;
	private List<String> avalibleToProfessions;
	private PerkCategory category;
	private Hashtable<String, Integer> categoryBonus;
	private Hashtable<String, Integer> conditionalCategoryBonus;
	private Hashtable<String, Integer> categoryRanks;
	private Hashtable<String, Integer> skillBonus;
	private Hashtable<String, Integer> skillRanks;
	private Hashtable<String, Integer> conditionalSkillBonus;
	private Hashtable<String, Integer> resistanceBonus;
	private Hashtable<String, Integer> characteristicBonus;
	private Hashtable<String, Integer> categoryWithCommon;
	private Hashtable<String, Integer> categoryWithRestricted;
	private List<String> commonSkills;
	private List<String> commonCategories;
	private List<String> restrictedSkills;
	private List<String> restrictedCategories;
	private List<String> categoriesToChoose;
	private List<String> skillsToChoose;
	private Integer appareanceBonus;
	private Integer armourClass;
	private Integer chosenBonus;
	private Integer numberOfChooseOptions;

	public Perk(String name, Integer cost, PerkCategory classification, String description,
			List<String> avalibleToRaces, List<String> avalibleToProfessions) {
		this.name = name;
		this.cost = cost;
		this.description = description;
		this.avalibleToRaces = avalibleToRaces;
		this.avalibleToProfessions = avalibleToProfessions;
		this.category = classification;
		categoryBonus = new Hashtable<>();
		skillBonus = new Hashtable<>();
		resistanceBonus = new Hashtable<>();
		categoryWithCommon = new Hashtable<>();
		categoryWithRestricted = new Hashtable<>();
		characteristicBonus = new Hashtable<>();
		conditionalCategoryBonus = new Hashtable<>();
		conditionalSkillBonus = new Hashtable<>();
		skillRanks = new Hashtable<>();
		categoryRanks = new Hashtable<>();
		commonSkills = new ArrayList<>();
		restrictedSkills = new ArrayList<>();
		commonCategories = new ArrayList<>();
		restrictedCategories = new ArrayList<>();
		categoriesToChoose = new ArrayList<>();
		skillsToChoose = new ArrayList<>();
		appareanceBonus = 0;
		armourClass = 1;
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

	public Integer getBonus(Category category) {
		Integer bonus = categoryBonus.get(category.getName());
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

	public void setCharacteristicBonus(String characteristic, Integer bonus) {
		if (bonus == null || bonus <= 0) {
			characteristicBonus.remove(characteristic);
		} else {
			characteristicBonus.put(characteristic, bonus);
		}
	}

	public void setCategoryToSelectCommonSkills(String categoryName, Integer commonSkills) {
		if (commonSkills == null || commonSkills <= 0) {
			categoryWithCommon.remove(categoryName);
		} else {
			categoryWithCommon.put(categoryName, commonSkills);
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
	 * If a perk is restricted to a race or a profession the character must have
	 * selected this race or profession. If not, the perk is no allowed to be
	 * used.
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

	public List<String> getCategoriesToChoose() {
		return categoriesToChoose;
	}

	public void setCategoriesToChoose(List<String> categoriesToChoose) {
		this.categoriesToChoose = categoriesToChoose;
	}

	public List<String> getSkillsToChoose() {
		return skillsToChoose;
	}

	public void setSkillsToChoose(List<String> skillsToChoose) {
		this.skillsToChoose = skillsToChoose;
	}

	public Integer getChosenBonus() {
		return chosenBonus;
	}

	public void setChosenBonus(Integer choseBonus) {
		this.chosenBonus = choseBonus;
	}

	/**
	 * 
	 * @return
	 */
	public Integer getNumberOfChooseOptions() {
		if (numberOfChooseOptions >= 0) {
			return numberOfChooseOptions;
		} else {
			return getOptionsToChoose().size() + numberOfChooseOptions;
		}
	}

	public void setChooseOptions(Integer chooseOptions) {
		this.numberOfChooseOptions = chooseOptions;
	}

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

	public List<String> getOptionsToChoose() {
		if (categoriesToChoose.size() > 0) {
			return categoriesToChoose;
		} else if (skillsToChoose.size() > 0) {
			return skillsToChoose;
		}
		return null;
	}

	public boolean isCategorySelected(String optionName) {
		return categoriesToChoose.contains(optionName);
	}

	public boolean isSkillSelected(String optionName) {
		return skillsToChoose.contains(optionName);
	}

	public String getSelectionableDescription() {
		String options = "";
		for (String category : categoriesToChoose) {
			if (options.length() > 1) {
				options += ", ";
			}
			options += category;
		}

		for (String skill : skillsToChoose) {
			if (options.length() > 1) {
				options += ", ";
			}
			options += skill;
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

	public Integer getCharacteristicBonus(String characteristic) {
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
}
