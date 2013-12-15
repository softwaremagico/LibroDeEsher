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

public class CategoryProbability {
	private CharacterPlayer characterPlayer;
	private Category category;
	private int tries = 0;
	private Map<String, Integer> suggestedSkillsRanks;
	private Integer specializationLevel;

	CategoryProbability(CharacterPlayer characterPlayer, Category category, int tries,
			Map<String, Integer> suggestedSkillsRanks, Integer specializationLevel) {
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
		if (characterPlayer.getNewRankCost(category, characterPlayer.getCurrentLevelRanks(category),
				characterPlayer.getCurrentLevelRanks(category) + 1) <= Config.getCategoryMaxCost()) {
			if (characterPlayer.getRemainingDevelopmentPoints() >= characterPlayer.getNewRankCost(category,
					characterPlayer.getCurrentLevelRanks(category), 1)
					&& category.getCategoryType().equals(CategoryType.STANDARD)) {
				probability += characterPlayer.getCharacteristicsBonus(category);
				probability += preferredCategory();
				probability -= categoryCostProbability();
				probability += bonusCommonSkills();
				probability += tries * 3;
				probability += smartBonus();
				if (probability > 90) {
					probability = 90;
				}
				if (probability < 1) {
					probability = 1;
				}
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
		if (!characterPlayer.isFirearmsAllowed() && category.getName().contains(Spanish.FIREARMS_SUFIX)) {
			bonus = -10000;
		}
		if (category.getName().equals(Spanish.LIGHT_ARMOUR_TAG)
				&& characterPlayer.getTotalValue(category) > 10) {
			return -1000;
		}
		if (category.getName().equals(Spanish.MEDIUM_ARMOUR_TAG)
				&& characterPlayer.getTotalValue(category) > 20) {
			return -1000;
		}
		if (category.getName().equals(Spanish.HEAVY_ARMOUR_TAG)
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
			if (suggestedSkillsRanks.get(skill.getName()) > 0) {
				skills.add(skill);
			}
		}
		return skills;
	}

	/**
	 * How much it cost.
	 */
	private int categoryCostProbability() {
		return (characterPlayer.getNewRankCost(category, characterPlayer.getCurrentLevelRanks(category), 1) - 5) * 10;
	}

	/**
	 * Prefeerred category are whitch one has assigned more ranks.
	 */
	private int preferredCategory() {
		int prob;
		prob = (characterPlayer.getTotalRanks(category)) * (specializationLevel + 4);
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
