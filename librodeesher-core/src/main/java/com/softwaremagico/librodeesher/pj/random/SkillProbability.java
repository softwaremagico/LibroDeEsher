package com.softwaremagico.librodeesher.pj.random;

import java.util.Map;

import com.softwaremagico.librodeesher.basics.Spanish;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.categories.Category;
import com.softwaremagico.librodeesher.pj.categories.CategoryFactory;
import com.softwaremagico.librodeesher.pj.categories.CategoryGroup;
import com.softwaremagico.librodeesher.pj.categories.CategoryType;
import com.softwaremagico.librodeesher.pj.magic.MagicListType;
import com.softwaremagico.librodeesher.pj.magic.RealmOfMagic;
import com.softwaremagico.librodeesher.pj.skills.Skill;
import com.softwaremagico.librodeesher.pj.skills.SkillFactory;

public class SkillProbability {
	private CharacterPlayer characterPlayer;
	private Skill skill;
	private Map<String, Integer> suggestedSkillsRanks;
	private int tries;
	private Integer specializationLevel;

	public SkillProbability(CharacterPlayer characterPlayer, Skill skill, int tries,
			Map<String, Integer> suggestedSkillsRanks, Integer specializationLevel) {
		this.characterPlayer = characterPlayer;
		this.skill = skill;
		this.suggestedSkillsRanks = suggestedSkillsRanks;
		this.tries = tries;
		this.specializationLevel = specializationLevel;
	}

	/**
	 * Devuelve la probabilidad de que una habilidad suba un rango.
	 */
	public int getRankProbability() {
		int probability = 0;
		if (!skill.isUsedInRandom() && characterPlayer.getRealRanks(skill) < 1) {
			return -100;
		}

		if (!skill.isUsedInRandom() && suggestedSkillsRanks.get(skill.getName()) == 0) {
			return -1000;
		}

		if (characterPlayer.getCurrentLevelRanks(skill) <= 3) {
			if (characterPlayer.getRemainingDevelopmentPoints() >= characterPlayer.getNewRankCost(
					skill.getCategory(), characterPlayer.getCurrentLevelRanks(skill),
					characterPlayer.getCurrentLevelRanks(skill) + 1)) {
				probability += preferredCategory() / 3;
				probability += HabilidadPreferida();
				probability += FacilidadHabilidad();
				probability -= skillExpensiveness();
				probability += tries * 3;
				probability += AplicarEspecializacionPersonaje();
				probability += stillNotUsedSkill();
				probability += HabilidadesPreferidasHechiceros();
				probability += HabilidadesPreferidasLuchadores();
				probability += smartRandomness();
				probability += ridicolousSkill();
				// Ponemos una cota superior y una cota inferior.
				if (probability > 90) {
					probability = 90;
				}
				if (probability < 1 && characterPlayer.getRealRanks(skill) > 0) {
					probability = 1;
				}
				probability += maxRanks();
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
						- (characterPlayer.getNewRankCost(skill.getCategory(), 0, 1))
						- characterPlayer.getRealRanks(skill) * 2, 0);
		return bonus;
	}

	/**
	 * Cuanto cuesta en puntos de desarrollo.
	 */
	private int skillExpensiveness() {
		// Cheapest category must be used!
		if (characterPlayer.getNewRankCost(skill.getCategory(), characterPlayer.getCurrentLevelRanks(skill),
				characterPlayer.getCurrentLevelRanks(skill) + 1) == 1) {
			return -100;
		}
		// Spells are a little more expensive that common categories. We make a
		// softer probability.
		if (skill.getCategory().getName().toLowerCase().equals(Spanish.BASIC_LIST_TAG)) {
			return (characterPlayer.getNewRankCost(skill.getCategory(),
					characterPlayer.getCurrentLevelRanks(skill),
					characterPlayer.getCurrentLevelRanks(skill) + 1) - 8) * 10;
		}
		return (characterPlayer.getNewRankCost(skill.getCategory(),
				characterPlayer.getCurrentLevelRanks(skill), characterPlayer.getCurrentLevelRanks(skill) + 1) - 5) * 10;
	}

	/**
	 * Devuelve un modificador de acuerdo con algunos criterios.
	 */
	private int smartRandomness() {
		return randomnessByRace() + randomnessByProfession() + randomnessByRanks() + randomnessBySkill()
				+ randomnessByOptions();
	}

	private int randomnessByRace() {
		// Horses for human and wolfs for orcs
		if (skill.getName().toLowerCase().contains(Spanish.HORSES_TAG)
				&& characterPlayer.getRace().getName().toLowerCase().contains(Spanish.ORCS_TAG)) {
			return -1000;
		}
		if (skill.getName().toLowerCase().contains(Spanish.WOLF_TAG)
				&& !characterPlayer.getRace().getName().toLowerCase().contains(Spanish.ORCS_TAG)) {
			return -1000;
		}
		if (skill.getName().toLowerCase().contains(Spanish.BEARS_TAG)
				&& !characterPlayer.getRace().getName().toLowerCase().contains(Spanish.DWARF_TAG)) {
			return -1000;
		}

		// Only racial attacks if it is a common skill.
		if (skill.getName().toLowerCase().startsWith(Spanish.RACIAL_ATTACK_TAG)
				&& !characterPlayer.isCommon(skill)) {
			return -1000;
		}
		return 0;
	}

	private int randomnessByProfession() {
		int bonus = 0;

		// Para los hechiceros.
		if (characterPlayer.isWizard()) {
			// At least 3 basic lists.
			if (skill.getCategory().getName().toLowerCase()
					.equals(MagicListType.BASIC.getCategoryName().toLowerCase())) {
				if (characterPlayer.getSkillsWithNewRanks(skill.getCategory()).size() < specializationLevel + 2) {
					if (characterPlayer.getRealRanks(skill) < characterPlayer.getCurrentLevelNumber()
							+ specializationLevel + 2) {
						if (characterPlayer.getSkillsWithNewRanks(skill.getCategory()).size() < specializationLevel + 1) {
							if (characterPlayer.isSemiWizard()) {
								bonus += 65;
							} else {
								bonus += 50;
							}
						} else {
							if (characterPlayer.isSemiWizard()) {
								bonus += 35;
							} else {
								bonus += 25;
							}
						}
					} else {
						return -150;
					}
				}
			}

			if (skill.getCategory().getName().toLowerCase()
					.equals(MagicListType.OPEN.getCategoryName().toLowerCase())) {
				if (characterPlayer.getSkillsWithNewRanks(skill.getCategory()).size() < specializationLevel + 2) {
					if (characterPlayer.getRealRanks(skill) < characterPlayer.getCurrentLevelNumber()
							+ specializationLevel + 2) {
						if (characterPlayer.getSkillsWithNewRanks(skill.getCategory()).size() == 0) {
							if (characterPlayer.isSemiWizard()) {
								bonus += 35;
							} else {
								bonus += 20;
							}
						} else {
							bonus += 15;
						}
					} else {
						return -100;
					}
				}
			}
			if (skill.getCategory().getName().toLowerCase()
					.equals(MagicListType.CLOSED.getCategoryName().toLowerCase())) {
				if (characterPlayer.getSkillsWithRanks(skill.getCategory()).size() < 3) {
					if (characterPlayer.getRealRanks(skill) < characterPlayer.getCurrentLevelNumber()
							+ specializationLevel + 2) {
						if (characterPlayer.getSkillsWithNewRanks(skill.getCategory()).size() == 0) {
							bonus += 20;
						} else {
							bonus += 10;
						}
					} else {
						return -150;
					}
				}
			}
			if (skill.getCategory().getName().toLowerCase()
					.equals(MagicListType.OTHER_PROFESSION.getCategoryName().toLowerCase())) {
				if (characterPlayer.getRealRanks(skill) < characterPlayer.getCurrentLevelNumber()
						+ specializationLevel + 2
						&& characterPlayer.getSkillsWithNewRanks(skill.getCategory()).size() < 1) {
					bonus += 5;
				} else {
					bonus -= 150;
				}
			}
			if (skill.getCategory().getName().toLowerCase()
					.equals(MagicListType.OTHER_REALM_OPEN.getCategoryName().toLowerCase())) {
				if (characterPlayer.getRealRanks(skill) > characterPlayer.getCurrentLevelNumber()
						+ specializationLevel + 2
						|| characterPlayer.getSkillsWithNewRanks(skill.getCategory()).size() > 1) {
					bonus -= 150;
				}
			}
			if (skill.getCategory().getName().toLowerCase()
					.equals(MagicListType.OTHER_REALM_CLOSED.getCategoryName().toLowerCase())) {
				if (characterPlayer.getRealRanks(skill) > characterPlayer.getCurrentLevelNumber()
						+ specializationLevel + 2
						|| characterPlayer.getSkillsWithNewRanks(skill.getCategory()).size() > 1) {
					bonus -= 150;
				}
			}
			if (skill.getCategory().getName().toLowerCase()
					.equals(MagicListType.OTHER_REALM_OTHER_PROFESSION.getCategoryName().toLowerCase())) {
				if (characterPlayer.getRealRanks(skill) > characterPlayer.getCurrentLevelNumber()
						+ specializationLevel + 2
						|| characterPlayer.getSkillsWithNewRanks(skill.getCategory()).size() > 1) {
					bonus -= 150;
				}
			}
			if (skill.getCategory().getName().toLowerCase()
					.equals(MagicListType.ARCHANUM.getCategoryName().toLowerCase())) {
				if (characterPlayer.getRealRanks(skill) > characterPlayer.getCurrentLevelNumber()
						+ specializationLevel + 2
						|| characterPlayer.getSkillsWithNewRanks(skill.getCategory()).size() > 2) {
					bonus -= 150;
				}
			}
			if (skill.getName().startsWith(Spanish.AIMED_SPELLS)) {
				String elemento = skill.getName().replaceAll(Spanish.AIMED_SPELLS, "");

				Skill basicList = SkillFactory.getSkill(Spanish.SPELL_LAW_PREFIX, elemento);

				if (characterPlayer.getRealRanks(basicList) > 0) {
					bonus += 10 * (characterPlayer.getRealRanks(basicList) - characterPlayer
							.getRealRanks(skill));
				} else {
					bonus -= 150;
				}
			}
			if (skill.getName().toLowerCase().equals("Desarrollo de Puntos de Poder")) {
				if (characterPlayer.getCurrentLevelRanks(skill) == 0) {
					bonus += 50;
				} else {
					if (characterPlayer.getRealRanks(skill) + characterPlayer.getCurrentLevelRanks(skill) < characterPlayer
							.getCurrentLevelNumber()) {
						bonus += 10 * (characterPlayer.getCurrentLevelNumber()
								- characterPlayer.getRealRanks(skill) + characterPlayer
								.getCurrentLevelRanks(skill));
					}
					// Needs enough PPs.
					if (characterPlayer.getTotalValue((CategoryFactory
							.getCategory("Desarrollo de Puntos de Poder")).getSkills().get(0)) < characterPlayer
							.getRanksValue(skill)) {
						bonus += 100;
					}
				}
			}
		}

		// Adrenal movements for monk.
		if (!characterPlayer.getProfession().getName().contains(Spanish.MONK_PROFESSION)) {
			if (skill.getName().toLowerCase().contains(Spanish.ADRENAL_SKILL)) {
				bonus -= 50;
			}
		}

		// Not so many martial arts for non monks professions.
		if ((skill.getCategory().getName().toLowerCase().startsWith(Spanish.MARTIAL_ARTS_PREFIX))
				&& characterPlayer.getSkillsWithNewRanks(skill.getCategory()).size() > 0
				&& (characterPlayer.getNewRankCost(skill.getCategory(), 0, 1) > 3)) {
			bonus -= 100;
		}

		// For warriors.
		if (characterPlayer.isFighter()) {
			if ((skill.getCategory().getCategoryGroup().equals(CategoryGroup.WEAPON))
					&& characterPlayer.getCurrentLevelRanks(skill) == 0
					&& characterPlayer.getCategoryCost(skill.getCategory(), 0).getRankCost().get(0) < 2) {
				if (characterPlayer.getSkillsWithNewRanks(skill.getCategory()).size() < 2) {
					bonus += 20;
				}
				if (characterPlayer.getSkillsWithNewRanks(skill.getCategory()).size() < 3) {
					bonus += 10;
				}
			}

			if (skill.getName().toLowerCase().equals("Desarrollo FÃ­sico")
					&& characterPlayer.getRealRanks(skill) < 10
					&& characterPlayer.getCurrentLevelRanks(skill) == 0) {
				bonus += 20;
			}
			// A veces lo no hechiceros tienen hechizos de opciones de
			// adiestramiento o cultura.
			if (skill.getName().toLowerCase().equals("Desarrollo de Puntos de Poder")) {
				Category cat1 = characterPlayer.getCategory(CategoryFactory.getCategory(Spanish.OPEN_LISTS));
				Category cat2 = characterPlayer
						.getCategory(CategoryFactory.getCategory(Spanish.CLOSED_LISTS));

				if (characterPlayer.getTotalValue(skill) < Math.max(
						characterPlayer.getMaxRanksPerLevel(cat1, 0),
						characterPlayer.getMaxRanksPerLevel(cat2, 0))) {
					bonus += Math.min(10 * characterPlayer.getCurrentLevelNumber(), 50);
				}
			}
			if (skill.getCategory().getName().startsWith(Spanish.CULTURE_ARMOUR)
					&& characterPlayer.getTotalValue(skill) < 30) {
				bonus += 20;
			}
		}

		return bonus;
	}

	private int randomnessByRanks() {
		int bonus = 0;

		// No more than 50 ranks
		if (characterPlayer.getRealRanks(skill) >= 50) {
			return -1000;
		}

		// Siempre va bien tener algunos puntos de vida.
		if (skill.getCategory().getCategoryType().equals(CategoryType.FD)
				&& characterPlayer.getRealRanks(skill) == 0) {
			bonus += 40;
		}

		// No more than 10 ranks per language.
		if (skill.getCategory().getName().toLowerCase().contains(Spanish.COMUNICATION_CATEGORY)
				&& characterPlayer.getRealRanks(skill) > 9) {
			return -1000;
		}

		// No so much communication skills.
		if (skill.getCategory().getName().toLowerCase().contains(Spanish.COMUNICATION_CATEGORY)
				&& characterPlayer.getRealRanks(skill) > 60) {
			bonus -= 40;
		}

		if (skill.getName().toLowerCase().equals(Spanish.SOFT_LEATHER_TAG)
				&& characterPlayer.getTotalValue(skill) > 30) {
			return -1000;
		}
		if (skill.getName().toLowerCase().equals(Spanish.HARD_LEATHER_TAG)
				&& characterPlayer.getTotalValue(skill) > 90) {
			return -1000;
		}
		if (skill.getName().toLowerCase().equals(Spanish.CHAINMAIL_TAG)
				&& characterPlayer.getTotalValue(skill) > 100) {
			return -1000;
		}
		if (skill.getName().toLowerCase().equals(Spanish.CUIRASS_TAG)
				&& characterPlayer.getTotalValue(skill) > 120) {
			return -1000;
		}

		/*
		 * Cuando se tiene muchos rangos, es tonteria subir mÃ¡s de un rango en
		 * una habilidad por nivel
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
			return -10000;
		}

		// No rocks in random characters.
		if (skill.getName().toLowerCase().contains(Spanish.ROCKS_TAG)
				&& characterPlayer.getRealRanks(skill) < 1) {
			return -10000;
		}

		// Sometimes too much weapons are learned.
		if ((skill.getCategory().getCategoryGroup().equals(CategoryGroup.WEAPON))
				&& characterPlayer.getSkillsWithRanks(skill.getCategory()).size() > (3 - characterPlayer
						.getNewRankCost(skill.getCategory(), 0, 1))
				|| characterPlayer.getWeaponsLearnedInCurrentLevel() > 5 - specializationLevel) {
			bonus -= 50;
		}
		return bonus;
	}

	private int randomnessByOptions() {
		if (!characterPlayer.isFirearmsAllowed()
				&& skill.getCategory().getCategoryGroup().equals(CategoryGroup.WEAPON)
				&& skill.getCategory().getName().toLowerCase().contains(Spanish.FIREARMS_SUFIX)) {
			return -10000;
		}

		if (!characterPlayer.isFirearmsAllowed()
				&& skill.getCategory().getName().toLowerCase().contains(Spanish.COMBAT_SKILLS_TAG)
				&& skill.getName().toLowerCase().contains(Spanish.FIREARMS_SUFIX)) {
			return -10000;
		}
		return 0;
	}

	/**
	 * Some skills for spellcsters.
	 * 
	 * @return
	 */
	private int HabilidadesPreferidasHechiceros() {
		// Algunos hechizos son mejores.
		if (characterPlayer.isWizard()) {
			if (characterPlayer.getProfessionalRealmsOfMagicChoosen().getRealmsOfMagic()
					.equals(RealmOfMagic.ESSENCE)) {
				if (skill.getName().toLowerCase().equals(Spanish.SHIELD_SPELLS_TAG)) {
					return Math.max(50 - specializationLevel * 8, 10);
				}
				if (skill.getName().toLowerCase().equals(Spanish.QUICKNESS_SPELLS_TAG)) {
					return Math.max(20 - specializationLevel * 5, 0);
				}
			}
			if (characterPlayer.getProfessionalRealmsOfMagicChoosen().getRealmsOfMagic()
					.equals(RealmOfMagic.MENTALISM)) {
				if (skill.getName().toLowerCase().equals(Spanish.DODGE_SPELLS_TAG)) {
					return Math.max(50 - specializationLevel * 8, 10);
				}
				if (skill.getName().toLowerCase().equals(Spanish.AUTOHEALTH_SPELLS_TAG)) {
					return Math.max(30 - specializationLevel * 5, 5);
				}
				if (skill.getName().toLowerCase().equals(Spanish.SPEED_SPELLS_TAG)) {
					return Math.max(20 - specializationLevel * 5, 0);
				}
			}
		}
		return 0;
	}

	/**
	 * Habilidades relacionadas con la profesion del personaje.
	 * 
	 * @return
	 */
	private int HabilidadesPreferidasLuchadores() {
		if (characterPlayer.isFighter()) {
			if (skill.getName().toLowerCase().equals(Spanish.HORSES_TAG)) {
				for (String training : characterPlayer.getTrainingsNames()) {
					if (training.toLowerCase().equals(Spanish.KNIGHT_TRAINING)) {
						return 50;
					}
				}
			}
		}
		return 0;
	}

	/**
	 * Permite seleccionar si un personaje quiere muchas habilidades de pocos
	 * rangos o pocas habilidades de muchos rangos.
	 */
	private int AplicarEspecializacionPersonaje() {
		int bonus = 0;
		// A malus for not tipics skills
		if (characterPlayer.getRealRanks(skill) == 0
				&& !(characterPlayer.isProfessional(skill) || characterPlayer.isCommon(skill))) {
			bonus -= Math.pow(characterPlayer.getSkillsWithRanks(skill.getCategory()).size(), 2)
					* (specializationLevel + 1) * 10;
		}
		// Evitar demasiada variedad de arma.
		if (skill.getCategory().getCategoryGroup().equals(CategoryGroup.WEAPON)) {
			if (characterPlayer.getRealRanks(skill) - characterPlayer.getCulture().getCultureRanks(skill) < 2) {
				bonus -= characterPlayer.getWeaponsLearnedInCurrentLevel() * (specializationLevel + 1) * 5;
			}
		}
		// No aÃ±adir demasiados rangos en habilidades de una misma categoria.
		bonus -= Math.max(Math.pow(characterPlayer.getSkillsWithNewRanks(skill.getCategory()).size(), 3)
				* (specializationLevel + 1) * 3, 0);
		return bonus;
	}

	/**
	 * More important skills with ranks.
	 */
	private int HabilidadPreferida() {
		int prob;
		prob = (characterPlayer.getRealRanks(skill)) * 5
				+ (characterPlayer.getRanksSpentInSkillSpecializations(skill) * 15);
		if (prob > 50 + characterPlayer.getRanksSpentInSkillSpecializations(skill) * 15) {
			prob = 50 + characterPlayer.getRanksSpentInSkillSpecializations(skill) * 15;
		}
		return prob;
	}

	/**
	 * Select common and professional skills.
	 */
	private int FacilidadHabilidad() {
		if (characterPlayer.isSkillGeneralized(skill)) {
			return 50 - skillExpensiveness() * 5;
		}
		if (characterPlayer.isRestricted(skill)) {
			return -1000;
		}
		if (characterPlayer.isProfessional(skill)) {
			return 90 - skillExpensiveness() * 10;
		}
		if (characterPlayer.isCommon(skill)) {
			return 75 - skillExpensiveness() * 10;
		}
		return 0;
	}

	/**
	 * Avoid weird skills.
	 * 
	 * @return
	 */
	private int ridicolousSkill() {
		if (skill.getName().toLowerCase().contains(Spanish.LICANTROPY_TAG)
				|| skill.getName().toLowerCase().contains(Spanish.BEARS_TAG)
				|| skill.getName().toLowerCase().contains(Spanish.CAMEL_TAG)) {
			return -1000;
		}
		return 0;
	}

	private int maxRanks() {
		if (characterPlayer.getCurrentLevelNumber() == 1
				&& ((characterPlayer.getRemainingDevelopmentPoints() > 10 && !characterPlayer
						.isLifeStyle(skill)) || (characterPlayer.getRemainingDevelopmentPoints() > 15))) {
			return -1000;
		}
		return 0;
	}

	/**
	 * Prefeerred category are whitch one has assigned more ranks.
	 */
	private int preferredCategory() {
		int prob;
		prob = (characterPlayer.getTotalRanks(skill.getCategory())) * (specializationLevel + 4);
		if (prob > 30) {
			prob = 30;
		}
		return prob;
	}
}
