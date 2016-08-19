package com.softwaremagico.librodeesher.pj.random;

import com.softwaremagico.librodeesher.basics.Spanish;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.categories.CategoryFactory;
import com.softwaremagico.librodeesher.pj.perk.Perk;
import com.softwaremagico.librodeesher.pj.race.exceptions.InvalidRaceDefinition;
import com.softwaremagico.librodeesher.pj.skills.SkillFactory;

public class PerkProbability {
	private CharacterPlayer characterPlayer;
	private Perk perk;

	public PerkProbability(CharacterPlayer characterPlayer, Perk perk) {
		this.characterPlayer = characterPlayer;
		this.perk = perk;
	}

	public int getProbability() {
		int probability = 0;
		int probabilityBySkills = 0;
		if (perk.isPerkAllowed(characterPlayer.getRace().getName(), characterPlayer.getProfession().getName())
				&& !hasAlreadySimilarPerk()) {
			probability += getProbabilityByCost();
			if (probability >= 0) {
				probability += getProbabilityByGrade();
				probability += getProbabilityByCharacteristics();
				probability += getProbabilityByResistances();
				probabilityBySkills += getProbabilityBySkillsBonus();
				if (probabilityBySkills > 15) {
					probabilityBySkills = 15;
				}
				probability += probabilityBySkills;
				probability += getProbabilityByCommonsOrRestricted();
				probability += getProbabilityByArmourClass();
				probability += getProbabilityByMovement();
			}
			// No bonus, no malus. A standard perk. Add it if has no perk chosen.
			if (probabilityBySkills == 0 && probability < 0) {
				probability = 3 - characterPlayer.getPerks().size();
			}

			probability += smartRandomness();
		}
		return probability;
	}

	private boolean hasAlreadySimilarPerk() {
		for (Perk characterPerk : characterPlayer.getPerks()) {
			if (characterPerk.getNameBasic().equals(perk.getNameBasic())) {
				return true;
			}
		}
		return false;
	}

	private int smartRandomness() {
		int bonus = 0;
		if (characterPlayer.isWizard()) {
			// No sceptic perk for wizards.
			if (perk.getName().equals(Spanish.PERK_SCEPTIC)) {
				return Integer.MIN_VALUE;
			}
		}
		return bonus;
	}

	private int getProbabilityByMovement() {
		return (int) (perk.getMovementBonus() != null ? perk.getMovementBonus() / 2 : 0);
	}

	private int getProbabilityByArmourClass() {
		return (int) (perk.getArmourClass() != null ? perk.getArmourClass() - 1 : 0);
	}

	private int getProbabilityByCost() {
		try {
			if (characterPlayer.getSpentPerksPoints() + perk.getCost() <= characterPlayer.getRace().getPerksPoints()) {
				return 0;
			}
		} catch (InvalidRaceDefinition e) {
		}
		return -Integer.MIN_VALUE;
	}

	private int getProbabilityByGrade() {
		return -perk.getGrade().asNumber() * 3;
	}

	private int getProbabilityByResistances() {
		int bonus = 0;
		for (Integer bonusOfResistence : perk.getResistanceBonus().values()) {
			bonus += bonusOfResistence / 5;
		}
		return bonus;
	}

	private int getProbabilityBySkillsBonus() {
		int bonus = 0;
		for (String categoryName : perk.getCategoryBonus().keySet()) {
			if (characterPlayer.isCategoryInteresting(CategoryFactory.getCategory(categoryName))) {
				bonus += perk.getCategoryBonus().get(categoryName);
			}
		}
		for (String categoryName : perk.getCategoryExtraRanks().keySet()) {
			if (characterPlayer.isCategoryInteresting(CategoryFactory.getCategory(categoryName))) {
				bonus += perk.getCategoryExtraRanks().get(categoryName);
			}
		}
		for (String categoryName : perk.getConditionalCategoryBonus().keySet()) {
			if (characterPlayer.isCategoryInteresting(CategoryFactory.getCategory(categoryName))) {
				bonus += perk.getConditionalCategoryBonus().get(categoryName) / 2;
			}
		}
		for (String skillName : perk.getSkillBonus().keySet()) {
			if (characterPlayer.isSkillInteresting(SkillFactory.getSkill(skillName))) {
				bonus += perk.getSkillBonus().get(skillName) / 2;
			}
		}
		for (String skillName : perk.getSkillRanksBonus().keySet()) {
			if (characterPlayer.isSkillInteresting(SkillFactory.getSkill(skillName))) {
				bonus += perk.getSkillRanksBonus().get(skillName);
			}
		}
		for (String skillName : perk.getConditionalSkillBonus().keySet()) {
			if (characterPlayer.isSkillInteresting(SkillFactory.getSkill(skillName))) {
				bonus += perk.getConditionalSkillBonus().get(skillName) / 3;
			}
		}
		return bonus;
	}

	private int getProbabilityByCharacteristics() {
		int bonus = 0;
		for (Integer charBonus : perk.getCharacteristicBonus().values()) {
			bonus += charBonus;
		}
		return bonus;
	}

	private int getProbabilityByCommonsOrRestricted() {
		int bonus = 0;
		for (String categoryName : perk.getCommonCategories()) {
			if (characterPlayer.isCategoryInteresting(CategoryFactory.getCategory(categoryName))) {
				bonus += 3;
			}
		}
		for (String categoryName : perk.getRestrictedCategories()) {
			if (characterPlayer.isCategoryInteresting(CategoryFactory.getCategory(categoryName))) {
				bonus -= 3;
			}
		}
		for (String skillName : perk.getCommonSkills()) {
			if (characterPlayer.isSkillInteresting(SkillFactory.getSkill(skillName))) {
				bonus += 1;
			}
		}
		for (String skillName : perk.getRestrictedSkills()) {
			if (characterPlayer.isSkillInteresting(SkillFactory.getSkill(skillName))) {
				bonus -= 1;
			}
		}
		return bonus;
	}
}
