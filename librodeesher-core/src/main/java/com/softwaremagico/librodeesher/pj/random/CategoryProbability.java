package com.softwaremagico.librodeesher.pj.random;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.softwaremagico.librodeesher.basics.Spanish;
import com.softwaremagico.librodeesher.config.Config;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.categories.Category;
import com.softwaremagico.librodeesher.pj.categories.CategoryType;
import com.softwaremagico.librodeesher.pj.skills.Skill;
import com.softwaremagico.log.Log;

public class CategoryProbability {
	private CharacterPlayer characterPlayer;
	private Category category;
	private int tries = 0;
	private Map<String, Integer> suggestedSkillsRanks;
	private Integer specializationLevel;

	CategoryProbability(CharacterPlayer characterPlayer, Category category,
			int tries, Map<String, Integer> suggestedSkillsRanks,
			Integer specializationLevel) {
		this.characterPlayer = characterPlayer;
		this.category = category;
		this.tries = tries;
		this.suggestedSkillsRanks = suggestedSkillsRanks;
		this.specializationLevel = specializationLevel;
	}

	/**
	 * Probability of adding a new rank
	 */
	public int rankProbability() {
		int probability = 0;
		if (category.isNotUsedInRandom()) {
			return -100;
		}

		Integer cost = characterPlayer.getNewRankCost(category);
		if (cost != null && cost <= Config.getCategoryMaxCost()) {
			if (characterPlayer.getRemainingDevelopmentPoints() >= characterPlayer
					.getNewRankCost(category)
					&& category.getCategoryType().equals(CategoryType.STANDARD)) {
				Log.debug(CategoryProbability.class.getName(),
						"Probability of category '" + category.getName() + "'");
				int getCharacteristicsBonus = characterPlayer
						.getCharacteristicsBonus(category);
				Log.debug(CategoryProbability.class.getName(),
						"\t Characteristic bonus: " + getCharacteristicsBonus);
				probability += getCharacteristicsBonus;
				int preferredCategory = preferredCategory();
				Log.debug(CategoryProbability.class.getName(),
						"\t Preferred category: " + preferredCategory);
				probability += preferredCategory;
				int categoryCostProbability = -categoryCostProbability();
				Log.debug(CategoryProbability.class.getName(),
						"\t Category cost: " + categoryCostProbability);
				probability += categoryCostProbability;
				int bonusCommonSkills = bonusCommonSkills();
				Log.debug(CategoryProbability.class.getName(),
						"\t Common skills: " + bonusCommonSkills);
				probability += bonusCommonSkills;
				int smartBonus = smartBonus();
				Log.debug(CategoryProbability.class.getName(),
						"\t Smart bonus: " + bonusCommonSkills);
				probability += smartBonus;
				probability += tries * 3;
				if (probability > 90) {
					probability = 90;
				}
				Log.debug(CategoryProbability.class.getName(), "\t Total: "
						+ probability);
				return probability;
			}
		}
		return 0;
	}

	/**
	 * Devuelve un modificador de acuerdo con algunos criterios.
	 */
	private int smartBonus() {
		int bonus = 0;
		// No ponemos armas de fuego si no tienen nada.
		if (!characterPlayer.isFirearmsAllowed()
				&& category.getName().contains(Spanish.FIREARMS_SUFIX)) {
			bonus = -10000;
		}
		if (category.getName().toLowerCase().equals(Spanish.LIGHT_ARMOUR_TAG)
				&& characterPlayer.getTotalValue(category) > 10) {
			return -1000;
		}
		if (category.getName().toLowerCase().equals(Spanish.MEDIUM_ARMOUR_TAG)
				&& characterPlayer.getTotalValue(category) > 20) {
			return -1000;
		}
		if (category.getName().toLowerCase().equals(Spanish.HEAVY_ARMOUR_TAG)
				&& characterPlayer.getTotalValue(category) > 30) {
			return -1000;
		}
		// Category with skills with ranks, more probability
		if (characterPlayer.getTotalRanks(category) == 0) {
			bonus += (30 + characterPlayer.getSkillsWithRanks(category).size() * 20);
			bonus += (30 + getSkillsWithSuggestedRanks().size() * 20);

			// Bonus to common or professional skills.
			if (characterPlayer.hasCommonOrProfessionalSkill(category)) {
				bonus += 50;
			}
		}
		if (characterPlayer.getTotalRanks(category) > 10) {
			bonus -= (8 - characterPlayer.getTotalRanks(category)) * 10;
		}
		return bonus;
	}

	private List<Skill> getSkillsWithSuggestedRanks() {
		List<Skill> skills = new ArrayList<>();
		for (Skill skill : category.getSkills()) {
			if (getSuggestedSkillRanks(skill.getName()) > 0) {
				skills.add(skill);
			}
		}
		return skills;
	}

	private Integer getSuggestedSkillRanks(String skillName) {
		if (suggestedSkillsRanks.get(skillName) == null
				|| suggestedSkillsRanks.get(skillName) < 0) {
			return 0;
		}
		return suggestedSkillsRanks.get(skillName);
	}

	/**
	 * How much it cost.
	 */
	private int categoryCostProbability() {
		return (int) ((Math.pow(characterPlayer.getNewRankCost(category), 2))) * 2;
	}

	/**
	 * Prefeerred category are whitch one has assigned more ranks.
	 */
	private int preferredCategory() {
		int prob;
		prob = (characterPlayer.getTotalRanks(category))
				* (specializationLevel + 4);
		if (prob > 30) {
			prob = 30;
		}
		return prob;
	}

	private int bonusCommonSkills() {
		int bonus = 0;
		for (Skill skill : category.getSkills()) {
			if (characterPlayer.isCommon(skill)) {
				bonus += 20;
			}
		}
		if (characterPlayer.getTotalRanks(category) >= 0) {
			return bonus;
		} else {
			return bonus / 10;
		}
	}
}
