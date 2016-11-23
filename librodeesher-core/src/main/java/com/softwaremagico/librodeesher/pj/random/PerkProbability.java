package com.softwaremagico.librodeesher.pj.random;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.softwaremagico.librodeesher.basics.Spanish;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.categories.CategoryFactory;
import com.softwaremagico.librodeesher.pj.categories.ChooseCategoryGroup;
import com.softwaremagico.librodeesher.pj.perk.Perk;
import com.softwaremagico.librodeesher.pj.perk.PerkFactory;
import com.softwaremagico.librodeesher.pj.perk.PerkType;
import com.softwaremagico.librodeesher.pj.race.exceptions.InvalidRaceDefinition;
import com.softwaremagico.librodeesher.pj.skills.ChooseSkillGroup;
import com.softwaremagico.librodeesher.pj.skills.SkillFactory;

public class PerkProbability {
	private CharacterPlayer characterPlayer;
	private Perk perk;
	private int specicializationLevel;
	private List<String> suggestedPerks;

	public PerkProbability(CharacterPlayer characterPlayer, Perk perk, int specicializationLevel, List<String> suggestedPerks) {
		this.characterPlayer = characterPlayer;
		this.perk = perk;
		this.suggestedPerks = suggestedPerks;
		this.specicializationLevel = specicializationLevel;
	}

	public int getProbability() {
		int probability = 0;
		int probabilityBySkills = 0;
		if (perk.getPerkType().equals(PerkType.MAGICAL) && !characterPlayer.isMagicAllowed()) {
			return 0;
		}
		if (perk.isPerkAllowed(characterPlayer.getRace().getName(), characterPlayer.getProfession().getName()) && !hasAlreadySimilarPerk()) {
			probability += getProbabilityByCost();
			if (probability >= 0) {
				probability += getProbabilityByNumberOfPerks();
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
				probability += getProbabilityBySuggestedPerks();
			}
			// No bonus, no malus. A standard perk. Add it if has no perk
			// chosen.
			if (probabilityBySkills == 0 && probability < 0) {
				probability = 2 - characterPlayer.getPerks().size() + specicializationLevel;
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

	private int getProbabilityByNumberOfPerks() {
		return -1 * characterPlayer.getPerks().size() * (2 - specicializationLevel) * 5;
	}

	private int getProbabilityBySuggestedPerks() {
		if (suggestedPerks != null && suggestedPerks.contains(perk.getName())) {
			return 100;
		}
		return 0;
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

	private Set<String> selectCategories(List<String> categories, int selections) {
		Collections.shuffle(categories);
		Set<String> selected = new HashSet<>();
		for (int i = 0; i < categories.size(); i++) {
			if ((categories.size() - i <= selections)
					|| (characterPlayer.isCategoryUseful(CategoryFactory.getCategory(categories.get(i))) && characterPlayer.getSkillsWithRanks(
							CategoryFactory.getCategory(categories.get(i))).size() > 0)) {
				selected.add(categories.get(i));
			}
			if (selected.size() >= selections) {
				break;
			}
		}
		return selected;
	}

	private Set<String> selectSkills(List<String> skills, int selections) {
		Collections.shuffle(skills);
		Set<String> selected = new HashSet<>();
		for (int i = 0; i < skills.size(); i++) {
			if ((skills.size() - i <= selections) || characterPlayer.isSkillInteresting(SkillFactory.getSkill(skills.get(i)))) {
				selected.add(skills.get(i));
			}
			if (selected.size() >= selections) {
				break;
			}
		}
		return selected;
	}

	public void selectOptions() {
		// Select options.
		// More than one category, select one of them.
		if (perk.getCategoriesToChoose().size() == 1 && perk.getCategoriesToChoose().get(0).getOptionsGroup().size() > 1) {
			for (ChooseCategoryGroup options : perk.getCategoriesToChoose()) {
				characterPlayer.setPerkDecision(perk,
						selectCategories(new ArrayList<String>(options.getOptionsAsString()), options.getNumberOfOptionsToChoose()), options.getChooseType());
			}
		} else
		// One category, select skills.
		if (perk.getCategoriesToChoose().size() == 1) {
			ChooseCategoryGroup options = perk.getCategoriesToChoose().get(0);
			ChooseSkillGroup skillOptions = new ChooseSkillGroup(perk.getCategorySkillsRanksBonus(characterPlayer.getCategory(options.getOptionsGroup().get(0))
					.getName()), characterPlayer.getCategory(options.getOptionsGroup().get(0)).getSkills(), options.getChooseType());
			skillOptions.setNumberOfOptionsToChoose(options.getNumberOfOptionsToChoose());
			characterPlayer.setPerkDecision(perk, selectSkills(skillOptions.getOptionsAsString(), options.getNumberOfOptionsToChoose()),
					skillOptions.getChooseType());
		} else
		// Select one skill from list.
		if (!perk.getSkillsToChoose().isEmpty()) {
			for (ChooseSkillGroup skillOptions : perk.getSkillsToChoose()) {
				characterPlayer.setPerkDecision(perk, selectSkills(skillOptions.getOptionsAsString(), skillOptions.getNumberOfOptionsToChoose()),
						skillOptions.getChooseType());
			}
		} else
		// Select common skills.
		if (!perk.getCommonSkillsToChoose().isEmpty()) {
			for (ChooseSkillGroup skillOptions : perk.getCommonSkillsToChoose()) {
				characterPlayer.setPerkDecision(perk, selectSkills(skillOptions.getOptionsAsString(), skillOptions.getNumberOfOptionsToChoose()),
						skillOptions.getChooseType());
			}
		}
	}

	protected static List<Perk> shufflePerks(CharacterPlayer characterPlayer, List<String> suggestedPerksNames) {
		List<Perk> suggestedPerks = new ArrayList<>();
		if (suggestedPerksNames != null) {
			for (String perkName : suggestedPerksNames) {
				suggestedPerks.add(PerkFactory.getPerk(perkName));
			}
		}

		List<Perk> allPerks = PerkFactory.gerPerks();
		Collections.shuffle(allPerks);

		// Suggested trainings at the beginning
		if (suggestedPerks != null) {
			allPerks.removeAll(suggestedPerks);
		}

		if (suggestedPerks != null) {
			for (Perk perk : suggestedPerks) {
				allPerks.add(0, perk);
			}
		}
		return allPerks;
	}
}
