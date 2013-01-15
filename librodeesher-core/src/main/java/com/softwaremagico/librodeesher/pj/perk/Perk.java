package com.softwaremagico.librodeesher.pj.perk;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class Perk {
	private String name;
	private Integer cost;
	private String description;
	private List<String> avalibleToRaces;
	private List<String> avalibleToProfessions;
	private PerkCategory classification;
	private Hashtable<String, Integer> categoryBonus;
	private Hashtable<String, Integer> conditionalCategoryBonus;
	private Hashtable<String, Integer> skillBonus;
	private Hashtable<String, Integer> skillRanks;
	private Hashtable<String, Integer> categoryRanks;
	private Hashtable<String, Integer> conditionalSkillBonus;
	private Hashtable<String, Integer> resistanceBonus;
	private Hashtable<String, Integer> characteristicBonus;
	private Hashtable<String, Integer> categoryWithCommon;
	private List<String> commonSkills;
	private Integer appareanceBonus;
	private Integer armourClass;

	public Perk(String name, Integer cost, PerkCategory classification, String description,
			List<String> avalibleToRaces, List<String> avalibleToProfessions) {
		this.name = name;
		this.cost = cost;
		this.description = description;
		this.avalibleToRaces = avalibleToRaces;
		this.avalibleToProfessions = avalibleToProfessions;
		this.classification = classification;
		categoryBonus = new Hashtable<>();
		skillBonus = new Hashtable<>();
		resistanceBonus = new Hashtable<>();
		categoryWithCommon = new Hashtable<>();
		characteristicBonus = new Hashtable<>();
		conditionalCategoryBonus = new Hashtable<>();
		conditionalSkillBonus = new Hashtable<>();
		skillRanks = new Hashtable<>();
		categoryRanks = new Hashtable<>();
		commonSkills = new ArrayList<>();
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

	public void setSkillAsCommon(String skillName, boolean common) {
		if (common == false) {
			commonSkills.remove(skillName);
		} else {
			if (!commonSkills.contains(skillName)) {
				commonSkills.add(skillName);
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
}
