package com.softwaremagico.librodeesher.pj.random;

import java.util.Map;

import com.softwaremagico.librodeesher.basics.Spanish;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.categories.CategoryGroup;
import com.softwaremagico.librodeesher.pj.categories.CategoryType;
import com.softwaremagico.librodeesher.pj.magic.RealmOfMagic;
import com.softwaremagico.librodeesher.pj.skills.Skill;
import com.softwaremagico.log.EsherLog;

public class SkillProbability {
	private final static int MAX_VALUE = 200;
	private final static int LAST_DEVELOPMENT_POINTS_RANGE = 10;
	private CharacterPlayer characterPlayer;
	private Skill skill;
	private Map<String, Integer> suggestedSkillsRanks;
	private Integer specializationLevel;
	private int finalLevel;

	public SkillProbability(CharacterPlayer characterPlayer, Skill skill,
			Map<String, Integer> suggestedSkillsRanks, Integer specializationLevel, int finalLevel) {
		this.characterPlayer = characterPlayer;
		this.skill = skill;
		this.suggestedSkillsRanks = suggestedSkillsRanks;
		this.specializationLevel = specializationLevel;
		this.finalLevel = finalLevel;
	}

	/**
	 * Gets the probability to add a new rank.
	 */
	public int getRankProbability() {
		int probability = 0;

		// Avoid disabled strings.
		if (characterPlayer.isSkillDisabled(skill)) {
			return -MAX_VALUE;
		}

		// Avoid strange skills.
		if (skill.isRare() && !characterPlayer.isCommon(skill)) {
			return -MAX_VALUE;
		}

		Integer cost = characterPlayer.getNewRankCost(skill);

		if (cost > characterPlayer.getRemainingDevelopmentPoints()) {
			return 0;
		}

		// Suggested ranks
		if (suggestedSkillsRanks != null && suggestedSkillsRanks.get(skill.getName()) != null) {
			if (characterPlayer.getTotalRanks(skill) < suggestedSkillsRanks.get(skill.getName())) {
				if (characterPlayer.getCurrentLevelRanks(skill) == 0) {
					return 100;
				} else if (characterPlayer.getTotalRanks(skill) < suggestedSkillsRanks.get(skill.getName())
						- finalLevel
						&& characterPlayer.getNewRankCost(skill) < CharacterPlayer.MAX_REASONABLE_COST) {
					return 100;
				}
			}
		}

		// Favorite skills are preferred.
		if (characterPlayer.getFavouriteSkills().contains(skill.getName())) {
			probability += 25;
		}

		if (characterPlayer.getCurrentLevelRanks(skill) <= 3) {
			EsherLog.debug(SkillProbability.class.getName(), "Probability of skill '" + skill.getName() + "'");
			int preferredCategory = increasedCategory() / 3;
			EsherLog.debug(SkillProbability.class.getName(), "\t Increased Category: " + preferredCategory);
			probability += preferredCategory;
			int preferredSkill = preferredSkill();
			EsherLog.debug(SkillProbability.class.getName(), "\t Preferred Skill: " + preferredSkill);
			probability += preferredSkill;
			int skillsPerCategory = skillsPerCategory();
			EsherLog.debug(SkillProbability.class.getName(), "\t Number of Skills per Category: "
					+ skillsPerCategory);
			probability += skillsPerCategory;
			int raceBonus = raceSkills();
			EsherLog.debug(SkillProbability.class.getName(), "\t Race bonus: " + raceBonus);
			probability += raceBonus;
			int bestSkills = bestSkills();
			EsherLog.debug(SkillProbability.class.getName(), "\t Best Skills: " + bestSkills);
			probability += bestSkills;
			int skillExpensiveness = skillExpensiveness();
			EsherLog.debug(SkillProbability.class.getName(), "\t Skill expensiveness: " + skillExpensiveness);
			probability += skillExpensiveness;
			int applyCharacterSpecialization = applyCharacterSpecialization();
			EsherLog.debug(SkillProbability.class.getName(), "\t Specialization: "
					+ applyCharacterSpecialization);
			probability += applyCharacterSpecialization;
			int stillNotUsedSkill = stillNotUsedSkill();
			EsherLog.debug(SkillProbability.class.getName(), "\t Still not used: " + stillNotUsedSkill);
			probability += stillNotUsedSkill;
			int wizardPreferredSkills = wizardPreferredSkills();
			EsherLog.debug(SkillProbability.class.getName(), "\t Wizard skill: " + wizardPreferredSkills);
			probability += wizardPreferredSkills;
			int warriorsPreferredSkills = warriorsPreferredSkills();
			EsherLog.debug(SkillProbability.class.getName(), "\t Warrior skill: " + warriorsPreferredSkills);
			probability += warriorsPreferredSkills;
			int smartRandomness = smartRandomness();
			EsherLog.debug(SkillProbability.class.getName(), "\t Smart randomness: " + smartRandomness);
			probability += smartRandomness;
			int ridicolousSkill = ridicolousSkill();
			EsherLog.debug(SkillProbability.class.getName(), "\t Ridicolous randomness: " + ridicolousSkill);
			probability += ridicolousSkill;
			int culturalSkill = culturalSkill();
			EsherLog.debug(SkillProbability.class.getName(), "\t Cultural Skill: " + culturalSkill);
			probability += culturalSkill;
			int maxRanks = maxRanks();
			EsherLog.debug(SkillProbability.class.getName(), "\t Max ranks: " + maxRanks);
			probability += maxRanks;
			// Limitation of value.
			if (probability > 150) {
				EsherLog.warning(SkillProbability.class.getName(), "Increased Category: " + preferredCategory
						+ "\t Preferred Skill: " + preferredSkill + "\t Number of Skills per category: "
						+ skillsPerCategory + "\t Best Skills: " + bestSkills + "\t Skill expensiveness: "
						+ skillExpensiveness + "\t Specialization: " + applyCharacterSpecialization
						+ "\t Still not used: " + stillNotUsedSkill + "\t Wizard skill: "
						+ wizardPreferredSkills + "\t Warrior skill: " + warriorsPreferredSkills
						+ "\t Smart randomness: " + smartRandomness + "\t Ridicolous randomness: "
						+ ridicolousSkill + "\t Cultural Skill: " + culturalSkill + "\t Max ranks: "
						+ maxRanks + "\t Total: " + probability);
			}
			if (probability > 90 && probability < 150) {
				probability = 90;
			}
			EsherLog.debug(SkillProbability.class.getName(), "\t Total: " + probability);
			return probability;
		}
		return 0;
	}

	/**
	 * Skills without ranks and are not so much expensive.
	 * 
	 * @return
	 */
	private int stillNotUsedSkill() {
		int bonus = 0;
		bonus += Math.max(characterPlayer.getLevelUps().size() - characterPlayer.getNewRankCost(skill)
				- characterPlayer.getRealRanks(skill) * 2, 0);
		return bonus;
	}

	/**
	 * Cost in development points.
	 */
	private int skillExpensiveness() {
		if (characterPlayer.getNewRankCost(skill) > CharacterPlayer.MAX_REASONABLE_COST) {
			return -1000;
		}
		// Spells are a little more expensive that common categories. We make a
		// softer probability.
		if (skill.getCategory().getCategoryGroup().equals(CategoryGroup.SPELL)) {
			if (characterPlayer.isHybridWizard()) {
				return (30 - (int) (Math.pow(characterPlayer.getNewRankCost(skill) - 2, 2)));
			}
			if (characterPlayer.isWizard()) {
				return (30 - (int) (Math.pow(characterPlayer.getNewRankCost(skill), 2)));
			}
		}
		if (characterPlayer.getRemainingDevelopmentPoints() > 1) {
			return Math.max(1, 30 - (int) ((Math.pow(characterPlayer.getNewRankCost(skill), 2)) * 3));
		} else {
			// Last development point is hard to use. Set it if only one point
			// remaining.
			if (characterPlayer.getNewRankCost(skill) == 1) {
				return 30;
			} else {
				return 0;
			}
		}
	}

	/**
	 * Some extra criteria to avoid weird characters.
	 */
	private int smartRandomness() {
		return randomnessByRace()
				+ randomnessByCulture()
				+ ProfessionRandomness
						.preferredSkillByProfession(characterPlayer, skill, specializationLevel)
				+ randomnessByRanks() + randomnessBySkill() + randomnessByOptions();
	}

	private int randomnessByRace() {
		// Horses for human and wolfs for orcs
		if (skill.getName().toLowerCase().contains(Spanish.HORSES_TAG)
				&& characterPlayer.getRace().getName().toLowerCase().contains(Spanish.ORCS_TAG)) {
			return -MAX_VALUE;
		}
		if (skill.getName().toLowerCase().contains(Spanish.WOLF_TAG)
				&& !characterPlayer.getRace().getName().toLowerCase().contains(Spanish.ORCS_TAG)) {
			return -MAX_VALUE;
		}
		if (skill.getName().toLowerCase().contains(Spanish.BEARS_TAG)
				&& !characterPlayer.getRace().getName().toLowerCase().contains(Spanish.DWARF_TAG)) {
			return -MAX_VALUE;
		}
		if (skill.getName().toLowerCase().contains(Spanish.CAMEL_TAG)
				&& !characterPlayer.getCulture().getName().toLowerCase().contains(Spanish.DESERT)) {
			return -MAX_VALUE;
		}
		if (skill.getName().toLowerCase().contains(Spanish.ELEMENTAL_MOUNT)
				&& !characterPlayer.getProfession().getName().toLowerCase()
						.contains(Spanish.ELEMENTALIST_PROFESSION)) {
			return -MAX_VALUE;
		}
		return 0;
	}

	private int randomnessByCulture() {
		int bonus = 0;
		// Only communication skills that already has ranks (to avoid weird
		// languages!).
		if (skill.getCategory().getName().toLowerCase().contains(Spanish.COMUNICATION_CATEGORY.toLowerCase())
				&& characterPlayer.getTotalRanks(skill) == 0) {
			bonus -= 40;
		}
		return bonus;
	}

	private int skillsPerCategory() {
		// Greater specialization allows more skills per category.
		return (characterPlayer.getCurrentLevelSkillsRanks(skill.getCategory()) * specializationLevel * 2);
	}

	private int randomnessByRanks() {
		int bonus = 0;

		// No more than 50 ranks
		if (characterPlayer.getRealRanks(skill) >= 50) {
			return -MAX_VALUE;
		}

		// Point life are always good!
		if (skill.getCategory().getCategoryType().equals(CategoryType.PD)
				&& characterPlayer.getRealRanks(skill) == 0) {
			bonus += 40;
		}

		// No more than 10 ranks per language.
		if (skill.getCategory().getName().toLowerCase().contains(Spanish.COMUNICATION_CATEGORY.toLowerCase())
				&& characterPlayer.getRealRanks(skill) > 9) {
			return -MAX_VALUE;
		}

		// No so much communication skills.
		if (skill.getCategory().getName().toLowerCase().contains(Spanish.COMUNICATION_CATEGORY)
				&& characterPlayer.getTotalValue(skill) > 60) {
			bonus -= 40;
		}

		// No so much regional knowledge from different cultures.
		if (skill.getCategory().getName().toLowerCase().contains(Spanish.REGIONAL_KNOWNLEDGE_TAG)
				&& !skill.getName().toLowerCase()
						.contains(characterPlayer.getCulture().getName().toLowerCase())) {
			bonus -= 40;
		}

		// No so much general knowledge if still has points for other
		// categories.
		if (skill.getCategory().getName().toLowerCase().equals(Spanish.GENERAL_KNOWLEDGE_TAG.toLowerCase())
				&& characterPlayer.getRemainingDevelopmentPoints() > LAST_DEVELOPMENT_POINTS_RANGE) {
			bonus -= characterPlayer.getCurrentLevelSkillsRanks(skill.getCategory()) * 10;
		}

		// Check armour values. Also armours are useless if race has natural
		// armour.
		if (skill.getName().toLowerCase().equals(Spanish.SOFT_LEATHER_TAG)
				&& (characterPlayer.getRace().getNaturalArmourType() > 2 || characterPlayer
						.getTotalValue(skill) > 30)) {
			return -MAX_VALUE;
		}
		if (skill.getName().toLowerCase().equals(Spanish.HARD_LEATHER_TAG)
				&& (characterPlayer.getRace().getNaturalArmourType() > 4 || characterPlayer
						.getTotalValue(skill) > 90)) {
			return -MAX_VALUE;
		}
		if (skill.getName().toLowerCase().equals(Spanish.CHAINMAIL_TAG)
				&& (characterPlayer.getRace().getNaturalArmourType() > 12 || characterPlayer
						.getTotalValue(skill) > 100)) {
			return -MAX_VALUE;
		}
		if (skill.getName().toLowerCase().equals(Spanish.CUIRASS_TAG)
				&& (characterPlayer.getRace().getNaturalArmourType() > 16 || characterPlayer
						.getTotalValue(skill) > 120)) {
			return -MAX_VALUE;
		}

		/*
		 * Only one rank per level for very high skills.
		 */
		if (characterPlayer.getRealRanks(skill) > 10 && characterPlayer.getCurrentLevelRanks(skill) > 0) {
			bonus -= 50;
		}

		return bonus;
	}

	private int randomnessBySkill() {
		int bonus = 0;
		// Main Guache cannot be the first weapon.
		if (skill.getName().toLowerCase().equals(Spanish.MAIN_GAUCHE_TAG)
				&& characterPlayer.getSkillsWithRanks(skill.getCategory()).size() < 1) {
			return -MAX_VALUE;
		}

		// No rocks in random characters.
		if (skill.getName().toLowerCase().contains(Spanish.ROCKS_TAG)
				&& characterPlayer.getRealRanks(skill) < 1) {
			return -MAX_VALUE;
		}

		// Not so many skills of the same category.
		if (!characterPlayer.getSkillsWithNewRanks(skill.getCategory()).contains(skill)) {
			bonus -= characterPlayer.getSkillsWithNewRanks(skill.getCategory()).size()
					* (specializationLevel + 2);
		}
		return bonus;
	}

	private int randomnessByOptions() {
		if (!characterPlayer.isFirearmsAllowed()
				&& skill.getCategory().getCategoryGroup().equals(CategoryGroup.WEAPON)
				&& skill.getCategory().getName().toLowerCase().contains(Spanish.FIREARMS_SUFIX)) {
			return -MAX_VALUE;
		}

		if (!characterPlayer.isFirearmsAllowed()
				&& skill.getCategory().getName().toLowerCase().contains(Spanish.COMBAT_SKILLS_TAG)
				&& skill.getName().toLowerCase().contains(Spanish.FIREARMS_SUFIX)) {
			return -MAX_VALUE;
		}

		if (!characterPlayer.isChiPowersAllowed()
				&& skill.getName().toLowerCase().startsWith(Spanish.CHI_SUFIX.toLowerCase())) {
			return -MAX_VALUE;
		}
		return 0;
	}

	private int raceSkills() {
		int bonus = 0;
		// Some natural attacks
		if (skill.getName().toLowerCase().startsWith(Spanish.RACIAL_ATTACK_TAG.toLowerCase())
				&& characterPlayer.getRace().isCommon(skill)) {
			bonus += 20;
		}
		return bonus;
	}

	/**
	 * Some skills for spellcasters.
	 * 
	 * @return
	 */
	private int wizardPreferredSkills() {
		// Some spells are preferred.
		if (characterPlayer.isWizard()) {
			if (characterPlayer.getRealmOfMagic().getRealmsOfMagic().contains(RealmOfMagic.ESSENCE)) {
				if (skill.getName().toLowerCase().equals(Spanish.SHIELD_SPELLS_TAG)) {
					return Math.max(
							50
									- characterPlayer.getCurrentLevelRanks(skill)
									* characterPlayer.getRankCost(skill,
											characterPlayer.getCurrentLevelRanks(skill), 1)
									* (8 - specializationLevel), 0);
				}
				if (skill.getName().toLowerCase().equals(Spanish.QUICKNESS_SPELLS_TAG)) {
					return Math.max(
							20
									- characterPlayer.getCurrentLevelRanks(skill)
									* characterPlayer.getRankCost(skill,
											characterPlayer.getCurrentLevelRanks(skill), 1)
									* (5 - specializationLevel), 0);
				}
			}
			if (characterPlayer.getRealmOfMagic().getRealmsOfMagic().contains(RealmOfMagic.MENTALISM)) {
				if (skill.getName().toLowerCase().equals(Spanish.DODGE_SPELLS_TAG)) {
					return Math.max(
							50
									- characterPlayer.getCurrentLevelRanks(skill)
									* characterPlayer.getRankCost(skill,
											characterPlayer.getCurrentLevelRanks(skill), 1)
									* (8 - specializationLevel), 0);
				}
				if (skill.getName().toLowerCase().equals(Spanish.AUTOHEALTH_SPELLS_TAG)) {
					return Math.max(
							30
									- characterPlayer.getCurrentLevelRanks(skill)
									* characterPlayer.getRankCost(skill,
											characterPlayer.getCurrentLevelRanks(skill), 1)
									* (5 - specializationLevel), 0);
				}
				if (skill.getName().toLowerCase().equals(Spanish.SPEED_SPELLS_TAG)) {
					return Math.max(
							20
									- characterPlayer.getCurrentLevelRanks(skill)
									* characterPlayer.getRankCost(skill,
											characterPlayer.getCurrentLevelRanks(skill), 1)
									* (5 - specializationLevel), 0);
				}
			}
		}
		return 0;
	}

	/**
	 * Skills for warriors.
	 * 
	 * @return
	 */
	private int warriorsPreferredSkills() {
		if (characterPlayer.isFighter()) {
			// Knights ride horses.
			if (skill.getName().toLowerCase().equals(Spanish.HORSES_TAG)) {
				for (String training : characterPlayer.getSelectedTrainings()) {
					if (training.toLowerCase().equals(Spanish.KNIGHT_TRAINING)) {
						return 50;
					}
				}
			} else if (skill.getCategory().getCategoryGroup().equals(CategoryGroup.WEAPON)) {
				return 10;
			} else if (skill.getName().toLowerCase().startsWith(Spanish.RACIAL_ATTACK_TAG.toLowerCase())) {
				return 30;
			}
		}
		return 0;
	}

	/**
	 * Selects if one character one few skills with lots of ranks or lots of
	 * skills with few ranks.
	 */
	private int applyCharacterSpecialization() {
		int bonus = 0;
		// Not too much weapons.
		if (skill.getCategory().getCategoryGroup().equals(CategoryGroup.WEAPON)) {
			bonus -= characterPlayer.getWeaponsLearnedInCurrentLevel() * (specializationLevel + 1) * 5;
		} else {
			// Not so many skills in one category.
			bonus -= characterPlayer.getSkillsWithNewRanks(skill.getCategory()).size()
					* (specializationLevel + 1) * 5;
		}
		return bonus;
	}

	/**
	 * More important skills with ranks.
	 */
	private int preferredSkill() {
		int prob;
		prob = Math.min(50, (characterPlayer.getRealRanks(skill)) * (specializationLevel) * 3
				+ (characterPlayer.getRanksSpentInSkillSpecializations(skill) * (specializationLevel) * 5));
		return prob;
	}

	/**
	 * Select common and professional skills.
	 */
	private int bestSkills() {
		if (characterPlayer.isGeneralized(skill)) {
			return Math.max(0, 50 - characterPlayer.getNewRankCost(skill) * 20);
		}
		if (characterPlayer.isRestricted(skill)) {
			return -MAX_VALUE;
		}
		if (characterPlayer.isProfessional(skill)) {
			return Math.max(0, 90 - characterPlayer.getNewRankCost(skill) * 20);
		}
		if (characterPlayer.isCommon(skill)) {
			return Math.max(0, 75 - characterPlayer.getNewRankCost(skill) * 20);
		}
		return 0;
	}

	/**
	 * Avoid weird skills.
	 * 
	 * @return
	 */
	private int ridicolousSkill() {
		if (characterPlayer.getRealRanks(skill) == 0
				&& (skill.getName().toLowerCase().contains(Spanish.LICANTROPY_TAG)
						|| skill.getName().toLowerCase().contains(Spanish.BEARS_TAG) || skill.getName()
						.toLowerCase().contains(Spanish.CAMEL_TAG))) {
			return -MAX_VALUE;
		}
		return 0;
	}

	private int culturalSkill() {
		// No cultural knowledge from other cultures.
		if (skill.getName().toLowerCase().startsWith(Spanish.REGIONAL_KNOWNLEDGE_TAG)
				&& !skill.getName().toLowerCase().contains(characterPlayer.getCulture().getName())) {
			return -MAX_VALUE;
		}
		if (skill.getName().toLowerCase().startsWith(Spanish.CULTURAL_KNOWNLEDGE_TAG)
				&& !skill.getName().toLowerCase().contains(characterPlayer.getCulture().getName())) {
			return -MAX_VALUE;
		}
		if (skill.getName().toLowerCase().startsWith(Spanish.FAUNA_KNOWNLEDGE_TAG)
				&& !skill.getName().toLowerCase().contains(characterPlayer.getCulture().getName())) {
			return -MAX_VALUE;
		}
		if (skill.getName().toLowerCase().startsWith(Spanish.FLORA_KNOWNLEDGE_TAG)
				&& !skill.getName().toLowerCase().contains(characterPlayer.getCulture().getName())) {
			return -MAX_VALUE;
		}
		return 0;
	}

	private int maxRanks() {
		if (((characterPlayer.getRealRanks(skill) > 10 && !characterPlayer.isLifeStyle(skill)) || (characterPlayer
				.getRealRanks(skill) > 15))) {
			return -MAX_VALUE;
		}
		return 0;
	}

	/**
	 * Preferred category are which one has assigned more ranks.
	 */
	private int increasedCategory() {
		int prob;
		prob = (characterPlayer.getCurrentLevelRanks(skill.getCategory())) * (specializationLevel + 4);
		// if we add ranks to a category, we must add ranks at least to one
		// skill.
		if (characterPlayer.getSkillsWithRanks(skill.getCategory()).isEmpty()
				&& characterPlayer.getCurrentLevelRanks(skill.getCategory()) > 0) {
			prob += 20;
		}
		return prob;
	}
}
