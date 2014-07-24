package com.softwaremagico.librodeesher.pj.random;

import java.util.Map;

import com.softwaremagico.librodeesher.basics.Spanish;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.categories.CategoryGroup;
import com.softwaremagico.librodeesher.pj.categories.CategoryType;
import com.softwaremagico.librodeesher.pj.magic.RealmOfMagic;
import com.softwaremagico.librodeesher.pj.skills.Skill;
import com.softwaremagico.log.Log;

public class SkillProbability {
	private final static int MAX_VALUE = 1000;
	private CharacterPlayer characterPlayer;
	private Skill skill;
	private Map<String, Integer> suggestedSkillsRanks;
	private int tries;
	private Integer specializationLevel;
	private int finalLevel;

	public SkillProbability(CharacterPlayer characterPlayer, Skill skill,
			int tries, Map<String, Integer> suggestedSkillsRanks,
			Integer specializationLevel, int finalLevel) {
		this.characterPlayer = characterPlayer;
		this.skill = skill;
		this.suggestedSkillsRanks = suggestedSkillsRanks;
		this.tries = tries;
		this.specializationLevel = specializationLevel;
		this.finalLevel = finalLevel;
	}

	/**
	 * Gets the probability to add a new rank.
	 */
	public int getRankProbability() {
		int probability = 0;

		// Avoid strange skills.
		if (skill.isRare() && !characterPlayer.isCommon(skill)
				&& suggestedSkillsRanks != null
				&& suggestedSkillsRanks.get(skill.getName()) != null
				&& suggestedSkillsRanks.get(skill.getName()) == 0) {
			return MAX_VALUE;
		}

		Integer cost = characterPlayer.getNewRankCost(skill);

		// Suggested ranks
		if (suggestedSkillsRanks != null
				&& suggestedSkillsRanks.get(skill.getName()) != null) {
			if (characterPlayer.getTotalRanks(skill) < suggestedSkillsRanks
					.get(skill.getName())
					&& cost <= characterPlayer.getRemainingDevelopmentPoints()) {
				if (characterPlayer.getCurrentLevelRanks(skill) == 0) {
					return 100;
				} else if (characterPlayer.getTotalRanks(skill) < suggestedSkillsRanks
						.get(skill.getName()) - finalLevel
						&& characterPlayer.getNewRankCost(skill) < 40) {
					return 100;
				}
			}
		}

		if (characterPlayer.getCurrentLevelRanks(skill) <= 3) {
			if (characterPlayer.getRemainingDevelopmentPoints() >= cost) {

				Log.debug(SkillProbability.class.getName(),
						"Probability of skill '" + skill.getName() + "'");
				int preferredCategory = increasedCategory() / 3;
				Log.debug(SkillProbability.class.getName(),
						"\t Increased Category: " + preferredCategory);
				probability += preferredCategory;
				int preferredSkill = preferredSkill();
				Log.debug(SkillProbability.class.getName(),
						"\t Preferred Skill: " + preferredSkill);
				probability += preferredSkill;
				int bestSkills = bestSkills();
				Log.debug(SkillProbability.class.getName(), "\t Best Skills: "
						+ bestSkills);
				probability += bestSkills;
				int skillExpensiveness = -skillExpensiveness();
				Log.debug(SkillProbability.class.getName(),
						"\t Skill expensiveness: " + skillExpensiveness);
				probability += skillExpensiveness;
				int applyCharacterSpecialization = applyCharacterSpecialization();
				Log.debug(SkillProbability.class.getName(),
						"\t Specialization: " + applyCharacterSpecialization);
				probability += applyCharacterSpecialization;
				int stillNotUsedSkill = stillNotUsedSkill();
				Log.debug(SkillProbability.class.getName(),
						"\t Still not used: " + stillNotUsedSkill);
				probability += stillNotUsedSkill;
				int wizardPreferredSkills = wizardPreferredSkills();
				Log.debug(SkillProbability.class.getName(), "\t Wizard skill: "
						+ wizardPreferredSkills);
				probability += wizardPreferredSkills;
				int warriorsPreferredSkills = warriorsPreferredSkills();
				Log.debug(SkillProbability.class.getName(),
						"\t Warrior skill: " + warriorsPreferredSkills);
				probability += warriorsPreferredSkills;
				int smartRandomness = smartRandomness();
				Log.debug(SkillProbability.class.getName(),
						"\t Smart randomness: " + smartRandomness);
				probability += smartRandomness;
				int ridicolousSkill = ridicolousSkill();
				Log.debug(SkillProbability.class.getName(),
						"\t Ridicolous randomness: " + smartRandomness);
				probability += ridicolousSkill;
				int culturaslSkill = culturalSkill();
				Log.debug(SkillProbability.class.getName(),
						"\t Cultural Skill: " + ridicolousSkill);
				probability += culturaslSkill;
				int maxRanks = maxRanks();
				Log.debug(SkillProbability.class.getName(), "\t Max ranks: "
						+ maxRanks);
				probability += maxRanks;
				// Max and min value
				if (probability > 90 && probability < 150) {
					probability = 90;
				} else if (probability > 0) {
					probability += tries * 3;
				}
				Log.debug(SkillProbability.class.getName(), "\t Total: "
						+ probability);
				return probability;
			}
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
		bonus += Math.max(
				characterPlayer.getCurrentLevelNumber()
						- characterPlayer.getNewRankCost(skill)
						- characterPlayer.getRealRanks(skill) * 2, 0);
		return bonus;
	}

	/**
	 * Cuanto cuesta en puntos de desarrollo.
	 */
	private int skillExpensiveness() {
		// Cheapest category must be used!
		if (characterPlayer.getNewRankCost(skill) == 1) {
			return -50;
		}
		// Spells are a little more expensive that common categories. We make a
		// softer probability.
		if (skill.getCategory().getName().toLowerCase()
				.equals(Spanish.BASIC_LIST_TAG)) {
			if (characterPlayer.isHybridWizard()) {
				return (int) (Math.pow(
						(characterPlayer.getNewRankCost(skill) - 4), 2) * 2);
			} else {
				return (int) (Math.pow(
						(characterPlayer.getNewRankCost(skill) - 2), 2) * 2);
			}
		}
		return (int) (Math.pow((characterPlayer.getNewRankCost(skill) - 1), 2) * 5);
	}

	/**
	 * Devuelve un modificador de acuerdo con algunos criterios.
	 */
	private int smartRandomness() {
		return randomnessByRace()
				+ randomnessByCulture()
				+ ProfessionRandomness.preferredSkillByProfession(
						characterPlayer, skill, specializationLevel)
				+ randomnessByRanks() + randomnessBySkill()
				+ randomnessByOptions();
	}

	private int randomnessByRace() {
		// Horses for human and wolfs for orcs
		if (skill.getName().toLowerCase().contains(Spanish.HORSES_TAG)
				&& characterPlayer.getRace().getName().toLowerCase()
						.contains(Spanish.ORCS_TAG)) {
			return MAX_VALUE;
		}
		if (skill.getName().toLowerCase().contains(Spanish.WOLF_TAG)
				&& !characterPlayer.getRace().getName().toLowerCase()
						.contains(Spanish.ORCS_TAG)) {
			return MAX_VALUE;
		}
		if (skill.getName().toLowerCase().contains(Spanish.BEARS_TAG)
				&& !characterPlayer.getRace().getName().toLowerCase()
						.contains(Spanish.DWARF_TAG)) {
			return MAX_VALUE;
		}
		if (skill.getName().toLowerCase().contains(Spanish.CAMEL_TAG)
				&& !characterPlayer.getCulture().getName().toLowerCase()
						.contains(Spanish.DESERT)) {
			return MAX_VALUE;
		}
		if (skill.getName().toLowerCase().contains(Spanish.ELEMENTAL_MOUNT)
				&& !characterPlayer.getProfession().getName().toLowerCase()
						.contains(Spanish.ELEMENTALIST_PROFESSION)) {
			return MAX_VALUE;
		}
		return 0;
	}

	private int randomnessByCulture() {
		int bonus = 0;
		// Only communication skills that already has ranks (to avoid extrange
		// languages!).
		if (skill.getCategory().getName().toLowerCase()
				.contains(Spanish.COMUNICATION_CATEGORY)
				&& characterPlayer.getTotalRanks(skill) == 0) {
			bonus -= 40;
		}
		return bonus;
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
		if (skill.getCategory().getName().toLowerCase()
				.contains(Spanish.COMUNICATION_CATEGORY)
				&& characterPlayer.getRealRanks(skill) > 9) {
			return -MAX_VALUE;
		}

		// No so much communication skills.
		if (skill.getCategory().getName().toLowerCase()
				.contains(Spanish.COMUNICATION_CATEGORY)
				&& characterPlayer.getTotalValue(skill) > 60) {
			bonus -= 40;
		}

		if (skill.getName().toLowerCase().equals(Spanish.SOFT_LEATHER_TAG)
				&& characterPlayer.getTotalValue(skill) > 30) {
			return -MAX_VALUE;
		}
		if (skill.getName().toLowerCase().equals(Spanish.HARD_LEATHER_TAG)
				&& characterPlayer.getTotalValue(skill) > 90) {
			return -MAX_VALUE;
		}
		if (skill.getName().toLowerCase().equals(Spanish.CHAINMAIL_TAG)
				&& characterPlayer.getTotalValue(skill) > 100) {
			return -MAX_VALUE;
		}
		if (skill.getName().toLowerCase().equals(Spanish.CUIRASS_TAG)
				&& characterPlayer.getTotalValue(skill) > 120) {
			return -MAX_VALUE;
		}

		/*
		 * Cuando se tiene muchos rangos, es tonteria subir mÃ¡s de un rango en
		 * una habilidad por nivel
		 */
		if (characterPlayer.getRealRanks(skill) > 10
				&& characterPlayer.getCurrentLevelRanks(skill) > 0) {
			bonus -= 50;
		}

		return bonus;
	}

	private int randomnessBySkill() {
		int bonus = 0;
		// Main Guache cannot be the first weapon.
		if (skill.getName().toLowerCase().equals(Spanish.MAIN_GAUCHE_TAG)
				&& characterPlayer.getSkillsWithRanks(skill.getCategory())
						.size() < 1) {
			return -MAX_VALUE;
		}

		// No rocks in random characters.
		if (skill.getName().toLowerCase().contains(Spanish.ROCKS_TAG)
				&& characterPlayer.getRealRanks(skill) < 1) {
			return -MAX_VALUE;
		}

		// Sometimes too much weapons are learned.
		if ((skill.getCategory().getCategoryGroup()
				.equals(CategoryGroup.WEAPON))
				&& characterPlayer.getSkillsWithRanks(skill.getCategory())
						.size() > (3 - characterPlayer.getNewRankCost(
						skill.getCategory(), 0, 1))
				|| characterPlayer.getWeaponsLearnedInCurrentLevel() > 5 - specializationLevel) {
			bonus -= 50;
		}

		// Not so many skills of the same category.
		if (!characterPlayer.getSkillsWithNewRanks(skill.getCategory())
				.contains(skill)) {
			bonus -= characterPlayer.getSkillsWithNewRanks(skill.getCategory())
					.size() * (specializationLevel + 2);
		}
		return bonus;
	}

	private int randomnessByOptions() {
		if (!characterPlayer.isFirearmsAllowed()
				&& skill.getCategory().getCategoryGroup()
						.equals(CategoryGroup.WEAPON)
				&& skill.getCategory().getName().toLowerCase()
						.contains(Spanish.FIREARMS_SUFIX)) {
			return -MAX_VALUE;
		}

		if (!characterPlayer.isFirearmsAllowed()
				&& skill.getCategory().getName().toLowerCase()
						.contains(Spanish.COMBAT_SKILLS_TAG)
				&& skill.getName().toLowerCase()
						.contains(Spanish.FIREARMS_SUFIX)) {
			return -MAX_VALUE;
		}

		if (!characterPlayer.isChiPowersAllowed()
				&& skill.getName().toLowerCase().startsWith(Spanish.CHI_SUFIX)) {
			return -MAX_VALUE;
		}
		return 0;
	}

	/**
	 * Some skills for spellcasters.
	 * 
	 * @return
	 */
	private int wizardPreferredSkills() {
		// Algunos hechizos son mejores.
		if (characterPlayer.isWizard()) {
			if (characterPlayer.getRealmOfMagic().getRealmsOfMagic()
					.equals(RealmOfMagic.ESSENCE)) {
				if (skill.getName().toLowerCase()
						.equals(Spanish.SHIELD_SPELLS_TAG)) {
					return Math
							.max(50
									- characterPlayer
											.getCurrentLevelRanks(skill)
									* characterPlayer
											.getNewRankCost(
													skill,
													characterPlayer
															.getCurrentLevelRanks(skill),
													1)
									* (8 - specializationLevel), 0);
				}
				if (skill.getName().toLowerCase()
						.equals(Spanish.QUICKNESS_SPELLS_TAG)) {
					return Math
							.max(20
									- characterPlayer
											.getCurrentLevelRanks(skill)
									* characterPlayer
											.getNewRankCost(
													skill,
													characterPlayer
															.getCurrentLevelRanks(skill),
													1)
									* (5 - specializationLevel), 0);
				}
			}
			if (characterPlayer.getRealmOfMagic().getRealmsOfMagic()
					.equals(RealmOfMagic.MENTALISM)) {
				if (skill.getName().toLowerCase()
						.equals(Spanish.DODGE_SPELLS_TAG)) {
					return Math
							.max(50
									- characterPlayer
											.getCurrentLevelRanks(skill)
									* characterPlayer
											.getNewRankCost(
													skill,
													characterPlayer
															.getCurrentLevelRanks(skill),
													1)
									* (8 - specializationLevel), 0);
				}
				if (skill.getName().toLowerCase()
						.equals(Spanish.AUTOHEALTH_SPELLS_TAG)) {
					return Math
							.max(30
									- characterPlayer
											.getCurrentLevelRanks(skill)
									* characterPlayer
											.getNewRankCost(
													skill,
													characterPlayer
															.getCurrentLevelRanks(skill),
													1)
									* (5 - specializationLevel), 0);
				}
				if (skill.getName().toLowerCase()
						.equals(Spanish.SPEED_SPELLS_TAG)) {
					return Math
							.max(20
									- characterPlayer
											.getCurrentLevelRanks(skill)
									* characterPlayer
											.getNewRankCost(
													skill,
													characterPlayer
															.getCurrentLevelRanks(skill),
													1)
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
			bonus -= characterPlayer.getWeaponsLearnedInCurrentLevel()
					* (specializationLevel + 1) * 5;
		} else {
			// Not so many skills in one category.
			bonus -= characterPlayer.getSkillsWithNewRanks(skill.getCategory())
					.size() * (specializationLevel + 1) * 5;
		}
		return bonus;
	}

	/**
	 * More important skills with ranks.
	 */
	private int preferredSkill() {
		int prob;
		prob = Math.min(
				50,
				(characterPlayer.getRealRanks(skill))
						* (specializationLevel)
						* 3
						+ (characterPlayer
								.getRanksSpentInSkillSpecializations(skill)
								* (specializationLevel) * 5));
		return prob;
	}

	/**
	 * Select common and professional skills.
	 */
	private int bestSkills() {
		if (characterPlayer.isSkillGeneralized(skill)) {
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
				&& (skill.getName().toLowerCase()
						.contains(Spanish.LICANTROPY_TAG)
						|| skill.getName().toLowerCase()
								.contains(Spanish.BEARS_TAG) || skill.getName()
						.toLowerCase().contains(Spanish.CAMEL_TAG))) {
			return -MAX_VALUE;
		}
		return 0;
	}

	private int culturalSkill() {
		// No cultural knowledge from other cultures.
		if (skill.getName().toLowerCase()
				.startsWith(Spanish.REGIONAL_KNOWNLEDGE_TAG)
				&& !skill.getName().toLowerCase()
						.contains(characterPlayer.getCulture().getName())) {
			return -MAX_VALUE;
		}
		if (skill.getName().toLowerCase()
				.startsWith(Spanish.CULTURAL_KNOWNLEDGE_TAG)
				&& !skill.getName().toLowerCase()
						.contains(characterPlayer.getCulture().getName())) {
			return -MAX_VALUE;
		}
		if (skill.getName().toLowerCase()
				.startsWith(Spanish.FAUNA_KNOWNLEDGE_TAG)
				&& !skill.getName().toLowerCase()
						.contains(characterPlayer.getCulture().getName())) {
			return -MAX_VALUE;
		}
		if (skill.getName().toLowerCase()
				.startsWith(Spanish.FLORA_KNOWNLEDGE_TAG)
				&& !skill.getName().toLowerCase()
						.contains(characterPlayer.getCulture().getName())) {
			return -MAX_VALUE;
		}
		return 0;
	}

	private int maxRanks() {
		if (((characterPlayer.getRealRanks(skill) > 10 && !characterPlayer
				.isLifeStyle(skill)) || (characterPlayer.getRealRanks(skill) > 15))) {
			return -MAX_VALUE;
		}
		return 0;
	}

	/**
	 * Preferred category are which one has assigned more ranks.
	 */
	private int increasedCategory() {
		int prob;
		prob = (characterPlayer.getCurrentLevelRanks(skill.getCategory()))
				* (specializationLevel + 4);
		// if we add ranks to a category, we must add ranks at least to one
		// skill.
		if (characterPlayer.getSkillsWithRanks(skill.getCategory()).isEmpty()
				&& characterPlayer.getCurrentLevelRanks(skill.getCategory()) > 0) {
			prob += 20;
		}
		return prob;
	}
}
