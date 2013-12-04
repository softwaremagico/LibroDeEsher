package com.softwaremagico.librodeesher.pj.perk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.softwaremagico.librodeesher.basics.ChooseType;
import com.softwaremagico.librodeesher.pj.categories.Category;
import com.softwaremagico.librodeesher.pj.categories.ChooseCategoryGroup;
import com.softwaremagico.librodeesher.pj.skills.ChooseSkillGroup;
import com.softwaremagico.librodeesher.pj.skills.Skill;
import com.softwaremagico.librodeesher.pj.skills.SkillFactory;

@Entity
@Table(name = "T_PERKS")
public class Perk {
	@Id
	@GeneratedValue
	private Long id; // database id.

	private String name;
	private Integer cost;
	private String description;
	@ElementCollection
	@CollectionTable(name = "T_PERK_AVAILABLE_TO_RACES")
	private List<String> avalibleToRaces;
	@ElementCollection
	@CollectionTable(name = "T_PERK_AVAILABLE_TO_PROFESSIONS")
	private List<String> avalibleToProfessions;
	private PerkCategory category;
	@ElementCollection
	@CollectionTable(name = "T_PERK_CATEGORY_BONUS")
	private Map<String, Integer> categoryBonus;
	@ElementCollection
	@CollectionTable(name = "T_PERK_CONDITIONAL_CATEGORY_BONUS")
	private Map<String, Integer> conditionalCategoryBonus;
	@ElementCollection
	@CollectionTable(name = "T_PERK_CATEGORY_RANKS")
	private Map<String, Integer> categoryRanks;
	@ElementCollection
	@CollectionTable(name = "T_PERK_SKILL_BONUS")
	private Map<String, Integer> skillBonus;
	@ElementCollection
	@CollectionTable(name = "T_PERK_SKILL_RANKS")
	private Map<String, Integer> skillRanks;
	@ElementCollection
	@CollectionTable(name = "T_PERK_CONDITIONAL_SKILL_BONUS")
	private Map<String, Integer> conditionalSkillBonus;
	@ElementCollection
	@CollectionTable(name = "T_PERK_RESISTANCE_BONUS")
	private Map<String, Integer> resistanceBonus;
	@ElementCollection
	@CollectionTable(name = "T_PERK_CHARACTERISTIC_BONUS")
	private Map<String, Integer> characteristicBonus;
	@ElementCollection
	@CollectionTable(name = "T_PERK_CATEGORY_WITH_RESTRICTED")
	private Map<String, Integer> categoryWithRestricted;
	@ElementCollection
	@CollectionTable(name = "T_PERK_COMMON_SKILLS")
	private List<String> commonSkills;
	@ElementCollection
	@CollectionTable(name = "T_PERK_COMMON_CATEGORIES")
	private List<String> commonCategories;
	@ElementCollection
	@CollectionTable(name = "T_PERK_RESTRICTED_SKILLS")
	private List<String> restrictedSkills;
	@ElementCollection
	@CollectionTable(name = "T_PERK_RESTRICTED_CATEGORIES")
	private List<String> restrictedCategories;
	@ElementCollection
	@CollectionTable(name = "T_PERK_CATEGORIES_TO_CHOOSE")
	private List<ChooseCategoryGroup> categoriesToChoose;
	@ElementCollection
	@CollectionTable(name = "T_PERK_SKILLS_TO_CHOOSE")
	private List<ChooseSkillGroup> skillsToChoose;
	@ElementCollection
	@CollectionTable(name = "T_PERK_COMMON_SKILLS_TO_CHOOSE")
	private List<ChooseSkillGroup> commonSkillsToChoose;
	private Integer appareanceBonus;
	private Integer armourClass;
	private Integer chosenBonus;

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

	public void setCategoryToSelectCommonSkills(Category category, Integer commonSkills) {
		commonSkillsToChoose
				.add(new ChooseSkillGroup(commonSkills, SkillFactory.getSkills(category), ChooseType.COMMON));
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

	// public List<String> getOptionsToChoose() {
	// if (categoriesToChoose.size() > 0) {
	// return categoriesToChoose;
	// } else if (skillsToChoose.size() > 0) {
	// return skillsToChoose;
	// }
	// return null;
	// }

	public boolean isCategorySelected(String optionName) {
		return categoriesToChoose.contains(optionName);
	}

	public boolean isSkillSelected(String optionName) {
		return skillsToChoose.contains(optionName);
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

	public List<ChooseSkillGroup> getCommonSkillsToChoose() {
		return commonSkillsToChoose;
	}

	protected Long getId() {
		return id;
	}

	protected void setId(Long id) {
		this.id = id;
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

	protected Map<String, Integer> getConditionalCategoryBonus() {
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

	protected Map<String, Integer> getConditionalSkillBonus() {
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

	protected Map<String, Integer> getCharacteristicBonus() {
		return characteristicBonus;
	}

	protected void setCharacteristicBonus(HashMap<String, Integer> characteristicBonus) {
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

	protected Integer getArmourClass() {
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

	protected void setCharacteristicBonus(Map<String, Integer> characteristicBonus) {
		this.characteristicBonus = characteristicBonus;
	}

	protected void setCategoryWithRestricted(Map<String, Integer> categoryWithRestricted) {
		this.categoryWithRestricted = categoryWithRestricted;
	}
}
