package com.softwaremagico.librodeesher.pj.random;

import com.softwaremagico.librodeesher.basics.Spanish;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.categories.Category;
import com.softwaremagico.librodeesher.pj.categories.CategoryFactory;
import com.softwaremagico.librodeesher.pj.categories.CategoryGroup;
import com.softwaremagico.librodeesher.pj.categories.CategoryType;
import com.softwaremagico.librodeesher.pj.magic.MagicListType;
import com.softwaremagico.librodeesher.pj.skills.Skill;
import com.softwaremagico.librodeesher.pj.skills.SkillFactory;

public class ProfessionRandomness {

	/**
	 * Obtains a ponderation of the preference of a skill by profession.
	 * 
	 * @param characterPlayer
	 * @param skill
	 * @param specializationLevel
	 * @return [-100..100]
	 */
	public static int preferredSkillByProfession(
			CharacterPlayer characterPlayer, Skill skill,
			int specializationLevel) {
		// For spellcasters.
		if (characterPlayer.isWizard()) {
			// At least a basic list per level.
			if (skill
					.getCategory()
					.getName()
					.toLowerCase()
					.equals(MagicListType.BASIC.getCategoryName().toLowerCase())) {
				if (characterPlayer.getRealRanks(skill) < characterPlayer
						.getCurrentLevelNumber() + specializationLevel + 1) {
					if (characterPlayer.getSkillsWithNewRanks(
							skill.getCategory()).size() < specializationLevel) {
						if (characterPlayer.isSemiWizard()) {
							return 45;
						} else {
							return 30;
						}
					} else {
						if (characterPlayer.isSemiWizard()) {
							return 25;
						} else {
							return 15;
						}
					}
				} else {
					return -100;
				}
			}
			if (skill.getCategory().getName().toLowerCase()
					.equals(MagicListType.OPEN.getCategoryName().toLowerCase())) {
				if (characterPlayer.getSkillsWithNewRanks(skill.getCategory())
						.size() < specializationLevel) {
					if (characterPlayer.getRealRanks(skill) < characterPlayer
							.getCurrentLevelNumber() + specializationLevel + 1) {
						if (characterPlayer.getSkillsWithNewRanks(
								skill.getCategory()).isEmpty()) {
							if (characterPlayer.isSemiWizard()) {
								return 15;
							} else {
								return 5;
							}
						} else {
							return 10;
						}
					} else {
						return -100;
					}
				}
			}
			if (skill
					.getCategory()
					.getName()
					.toLowerCase()
					.equals(MagicListType.CLOSED.getCategoryName()
							.toLowerCase())) {
				if (characterPlayer.getSkillsWithRanks(skill.getCategory())
						.size() < specializationLevel) {
					if (characterPlayer.getRealRanks(skill) < characterPlayer
							.getCurrentLevelNumber() + specializationLevel + 1) {
						if (characterPlayer.getSkillsWithNewRanks(
								skill.getCategory()).isEmpty()) {
							return 20;
						} else {
							return 10;
						}
					} else {
						return -100;
					}
				}
			}
			if (skill.getName().startsWith(Spanish.AIMED_SPELLS)) {
				String elemento = skill.getName().replaceAll(
						Spanish.AIMED_SPELLS, "");

				Skill basicList = SkillFactory.getSkill(
						Spanish.SPELL_LAW_PREFIX, elemento);

				// Already has a rank in an elementalist list. Increase aimed
				// spells skill.
				if (basicList != null
						&& characterPlayer.getRealRanks(basicList) > 0) {
					return 10 * (characterPlayer.getRealRanks(basicList) - characterPlayer
							.getRealRanks(skill));
				} else {
					return -100;
				}
			}
			if (skill.getCategory().getCategoryType().equals(CategoryType.PPD)) {
				if (characterPlayer.getCurrentLevelRanks(skill) == 0) {
					return 50;
				} else {
					// Needs enough PPs.
					for (Category spellList : characterPlayer
							.getSpellsCategories()) {
						for (Skill spell : spellList.getSkills()) {
							//Spells need more PP that available.
							if (characterPlayer.getTotalValue(spell) < characterPlayer
									.getTotalValue(skill)) {
								return 100;
							}
						}
					}
					// Add some extra PPD
					if (characterPlayer.getRealRanks(skill)
							+ characterPlayer.getCurrentLevelRanks(skill) < characterPlayer
								.getCurrentLevelNumber()) {
						return 10 * (characterPlayer.getCurrentLevelNumber()
								- characterPlayer.getRealRanks(skill) + characterPlayer
									.getCurrentLevelRanks(skill));
					}
				}
			}
		}

		// Adrenal movements for monk.
		if (!characterPlayer.getProfession().getName().toLowerCase()
				.contains(Spanish.MONK_PROFESSION)) {
			if (skill.getName().toLowerCase().contains(Spanish.ADRENAL_SKILL)) {
				return -50;
			}

			// Only one martial arts for non monks professions by level.
			if ((skill.getCategory().getName().toLowerCase()
					.startsWith(Spanish.MARTIAL_ARTS_PREFIX))
					&& characterPlayer.getSkillsWithNewRanks(
							skill.getCategory()).size() > 0
					&& (characterPlayer.getNewRankCost(skill.getCategory(), 0,
							1) > 3)) {
				return -100;
			}
		}

		// For warriors.
		if (characterPlayer.isFighter()) {
			if ((skill.getCategory().getCategoryGroup()
					.equals(CategoryGroup.WEAPON))
					&& characterPlayer.getCurrentLevelRanks(skill) == 0
					&& characterPlayer.getCategoryCost(skill.getCategory(), 0)
							.getRankCost().get(0) < 2) {
				if (characterPlayer.getSkillsWithNewRanks(skill.getCategory())
						.size() < 2) {
					return 20;
				}
				if (characterPlayer.getSkillsWithNewRanks(skill.getCategory())
						.size() < 3) {
					return 10;
				}
			}

			if (skill.getCategory().getCategoryType().equals(CategoryType.PD)
					&& characterPlayer.getRealRanks(skill) < 10
					&& characterPlayer.getCurrentLevelRanks(skill) == 0) {
				return 20;
			}
			// A veces lo no hechiceros tienen hechizos de opciones de
			// adiestramiento o cultura.
			if (skill.getCategory().getCategoryType().equals(CategoryType.PPD)) {
				Category cat1 = characterPlayer.getCategory(CategoryFactory
						.getCategory(Spanish.OPEN_LISTS));
				Category cat2 = characterPlayer.getCategory(CategoryFactory
						.getCategory(Spanish.CLOSED_LISTS));

				if (characterPlayer.getTotalValue(skill) < Math.max(
						characterPlayer.getMaxRanksPerLevel(cat1, 0),
						characterPlayer.getMaxRanksPerLevel(cat2, 0))) {
					return Math.min(
							10 * characterPlayer.getCurrentLevelNumber(), 50);
				}
			}
			// Armors with ranks for fighters must be increased if value is low.
			if (skill.getCategory().getName()
					.startsWith(Spanish.CULTURE_ARMOUR)) {
				if (characterPlayer.getRealRanks(skill) > 0) {
					if (skill.getCategory().getName().toLowerCase()
							.equals(Spanish.LIGHT_ARMOUR_TAG)
							&& characterPlayer.getTotalValue(skill
									.getCategory()) < 10
							&& characterPlayer.getNewRankCost(
									skill.getCategory(), 0, 1) < 3) {
						return 20;
					}
					if (skill.getCategory().getName().toLowerCase()
							.equals(Spanish.MEDIUM_ARMOUR_TAG)
							&& characterPlayer.getTotalValue(skill
									.getCategory()) < 20
							&& characterPlayer.getNewRankCost(
									skill.getCategory(), 0, 1) < 4) {
						return 20;
					}
					if (skill.getCategory().getName().toLowerCase()
							.equals(Spanish.HEAVY_ARMOUR_TAG)
							&& characterPlayer.getTotalValue(skill
									.getCategory()) < 30
							&& characterPlayer.getNewRankCost(
									skill.getCategory(), 0, 1) < 4) {
						return 20;
					}
				}
			}
		}
		return 0;
	}

	/**
	 * Obtains a ponderation of the preference of a category by profession.
	 * 
	 * @param characterPlayer
	 * @param skill
	 * @param specializationLevel
	 * @return [-100..100]
	 */
	public static int preferredCategoryByProfession(
			CharacterPlayer characterPlayer, Category category,
			int specializationLevel) {
		// Categories with preferred skills also have a bonus.
		for (Skill skill : category.getSkills()) {
			if (preferredSkillByProfession(characterPlayer, skill,
					specializationLevel) > 0) {
				return 20;
			}
		}
		return 0;
	}
}
