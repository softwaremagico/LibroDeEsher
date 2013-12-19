package com.softwaremagico.librodeesher.pj.random;

import java.util.Map;

import com.softwaremagico.librodeesher.basics.Spanish;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.categories.Category;
import com.softwaremagico.librodeesher.pj.categories.CategoryGroup;
import com.softwaremagico.librodeesher.pj.categories.CategoryType;
import com.softwaremagico.librodeesher.pj.magic.MagicListType;
import com.softwaremagico.librodeesher.pj.skills.Skill;

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
	public int rankProbability() {
		int probability = 0;
		if (!skill.isUsedInRandom() && characterPlayer.getRealRanks(skill) < 1) {
			return -100;
		}

		if (!skill.isUsedInRandom() && suggestedSkillsRanks.get(skill.getName()) == 0) {
			return -1000;
		}

		if (characterPlayer.getCurrentLevelRanks(skill) <= 3) {
			if (characterPlayer.getRemainingDevelopmentPoints() >= Personaje.getInstance().CosteCategoriaYHabilidad(
					categoriaPadre, characterPlayer.getCurrentLevelRanks(skill), this)) {
				probability += categoriaPadre.CategoriaPreferida() / 3;
				probability += HabilidadPreferida();
				probability += FacilidadHabilidad();
				probability -= CaroHabilidad();
				probability += tries * 3;
				probability += AplicarEspecializacionPersonaje();
				probability += HabilidadAtrasada();
				probability += HabilidadesPreferidasHechiceros();
				probability += HabilidadesPreferidasLuchadores();
				probability += AplicarInteligenciaALaAleatorizacion();
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
	 * Habilidades que no han tenido ningÃºn rango en muchos niveles y no son
	 * demasiado caras.
	 * 
	 * @return
	 */
	private int HabilidadAtrasada() {
		int bonus = 0;
		bonus += Math.max(
				characterPlayer.getCurrentLevelNumber()
						- (Personaje.getInstance().CosteCategoriaYHabilidad(categoriaPadre, 0, this))
						- characterPlayer.getRealRanks(skill) * 2, 0);
		return bonus;
	}

	/**
	 * Cuanto cuesta en puntos de desarrollo.
	 */
	private int CaroHabilidad() {
		// Las que valen uno de coste son casi obligatorias cogerlas.
		if (Personaje.getInstance().CosteCategoriaYHabilidad(categoriaPadre,
				characterPlayer.getCurrentLevelRanks(skill), this) == 1) {
			return -100;
		}
		if (categoriaPadre.skill.getName().toLowerCase().equals("Listas BÃ¡sicas de Hechizos")) {
			return (Personaje.getInstance().CosteCategoriaYHabilidad(categoriaPadre,
					characterPlayer.getCurrentLevelRanks(skill), this) - 8) * 10;
		}
		return (Personaje.getInstance().CosteCategoriaYHabilidad(categoriaPadre,
				characterPlayer.getCurrentLevelRanks(skill), this) - 5) * 10;
	}

	/**
	 * Devuelve un modificador de acuerdo con algunos criterios.
	 */
	private int AplicarInteligenciaALaAleatorizacion() {
		return AleatorizacionPorRaza() + AleatorizacionPorProfesion() + AleatorizacionPorRangos()
				+ AleatorizacionPorHabilidad() + AleatorizacionPorOpciones();
	}

	private int AleatorizacionPorRaza() {
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
		if (skill.getName().toLowerCase().startsWith(Spanish.RACIAL_ATTACK_TAG) && !characterPlayer.isCommon(skill)) {
			return -1000;
		}
		return 0;
	}

	private int AleatorizacionPorProfesion() {
		Habilidad hab;
		int bonus = 0;

		// Para los hechiceros.
		if (Personaje.getInstance().EsHechicero()) {
			// Potenciamos que existan las listas bÃ¡sicas (al menos 3)
			if (skill.getCategory().getName().toLowerCase().equals(MagicListType.BASIC.getCategoryName().toLowerCase())) {
				if (characterPlayer.getSkillsWithNewRanks(skill.getCategory()).size() < specializationLevel + 2) {
					if (characterPlayer.getRealRanks(skill) < characterPlayer.getCurrentLevelNumber()
							+ specializationLevel + 2) {
						if (characterPlayer.getSkillsWithNewRanks(skill.getCategory()).size() < specializationLevel + 1) {
							if (Personaje.getInstance().EsSemiHechicero()) {
								bonus += 65;
							} else {
								bonus += 50;
							}
						} else {
							if (Personaje.getInstance().EsSemiHechicero()) {
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

			if (skill.getCategory().getName().toLowerCase().equals(MagicListType.OPEN.getCategoryName().toLowerCase())) {
				if (characterPlayer.getSkillsWithNewRanks(skill.getCategory()).size() < specializationLevel + 2) {
					if (characterPlayer.getRealRanks(skill) < characterPlayer.getCurrentLevelNumber()
							+ specializationLevel + 2) {
						if (characterPlayer.getSkillsWithNewRanks(skill.getCategory()).size() == 0) {
							if (Personaje.getInstance().EsSemiHechicero()) {
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
				if (characterPlayer.getRealRanks(skill) < characterPlayer.getCurrentLevelNumber() + specializationLevel
						+ 2
						&& characterPlayer.getSkillsWithNewRanks(skill.getCategory()).size() < 1) {
					bonus += 5;
				} else {
					bonus -= 150;
				}
			}
			if (skill.getCategory().getName().toLowerCase()
					.equals(MagicListType.OTHER_REALM_OPEN.getCategoryName().toLowerCase())) {
				if (characterPlayer.getRealRanks(skill) > characterPlayer.getCurrentLevelNumber() + specializationLevel
						+ 2
						|| characterPlayer.getSkillsWithNewRanks(skill.getCategory()).size() > 1) {
					bonus -= 150;
				}
			}
			if (skill.getCategory().getName().toLowerCase()
					.equals(MagicListType.OTHER_REALM_CLOSED.getCategoryName().toLowerCase())) {
				if (characterPlayer.getRealRanks(skill) > characterPlayer.getCurrentLevelNumber() + specializationLevel
						+ 2
						|| characterPlayer.getSkillsWithNewRanks(skill.getCategory()).size() > 1) {
					bonus -= 150;
				}
			}
			if (skill.getCategory().getName().toLowerCase()
					.equals(MagicListType.OTHER_REALM_OTHER_PROFESSION.getCategoryName().toLowerCase())) {
				if (characterPlayer.getRealRanks(skill) > characterPlayer.getCurrentLevelNumber() + specializationLevel
						+ 2
						|| characterPlayer.getSkillsWithNewRanks(skill.getCategory()).size() > 1) {
					bonus -= 150;
				}
			}
			if (skill.getCategory().getName().toLowerCase()
					.equals(MagicListType.ARCHANUM.getCategoryName().toLowerCase())) {
				if (characterPlayer.getRealRanks(skill) > characterPlayer.getCurrentLevelNumber() + specializationLevel
						+ 2
						|| characterPlayer.getSkillsWithNewRanks(skill.getCategory()).size() > 2) {
					bonus -= 150;
				}
			}
			if (skill.getName().startsWith(Spanish.AIMED_SPELLS)) {
				String elemento = skill.getName().replaceAll(Spanish.AIMED_SPELLS, "");
				if (((hab = Personaje.getInstance().DevolverHabilidadDeNombre("Ley del ", elemento)) != null || (hab = Personaje
						.getInstance().DevolverHabilidadDeNombre("Ley de ", elemento)) != null)
						&& hab.characterPlayer.getRealRanks(skill) > 0) {
					bonus += 10 * (hab.characterPlayer.getRealRanks(skill) - characterPlayer.getRealRanks(skill));
				} else {
					bonus -= 150;
				}
			}
			if (skill.getName().toLowerCase().equals("Desarrollo de Puntos de Poder")) {
				if (characterPlayer.getCurrentLevelRanks(skill) == 0) {
					bonus += 50;
				} else {
					if (rangos + characterPlayer.getCurrentLevelRanks(skill) < characterPlayer.getCurrentLevelNumber()) {
						bonus += 10 * (characterPlayer.getCurrentLevelNumber() - rangos + characterPlayer
								.getCurrentLevelRanks(skill));
					}
					// Al menos tener PPs suficientes para lanzar el mejor
					// hechizo.
					if (Personaje.getInstance().DevolverMaximoNivelHechizos() > characterPlayer.getTotalValue(skill)) {
						bonus += 100;
					}
				}
			}
		}

		// Dejamos los movimientos adrenales para monjes.
		if (!Personaje.getInstance().profesion.contains("Monje")) {
			if (nombre.contains("Adrenal")) {
				bonus -= 50;
			}
		}

		// Tampoco interesa demasiados artes marciales si no es monje.
		if (!Personaje.getInstance().profesion.contains("Monje")
				&& (categoriaPadre.DevolverNombre().startsWith("Artes MarcialesÂ·"))
				&& characterPlayer.getSkillsWithNewRanks(skill.getCategory()).size() > 0
				&& (Personaje.getInstance().CosteCategoriaYHabilidad(categoriaPadre, 0, this) > 2)) {
			bonus -= 100;
		}

		// Para los guerreros.
		if (Personaje.getInstance().EsCombatiente()) {
			if ((skill.getCategory().getCategoryGroup().equals(CategoryGroup.WEAPON))
					&& characterPlayer.getCurrentLevelRanks(skill) == 0 && categoriaPadre.costeRango[0] < 2) {
				if (characterPlayer.getSkillsWithNewRanks(skill.getCategory()).size() < 2) {
					bonus += 20;
				}
				if (characterPlayer.getSkillsWithNewRanks(skill.getCategory()).size() < 3) {
					bonus += 10;
				}
			}

			if (skill.getName().toLowerCase().equals("Desarrollo FÃ­sico") && characterPlayer.getRealRanks(skill) < 10
					&& characterPlayer.getCurrentLevelRanks(skill) == 0) {
				bonus += 20;
			}
			// A veces lo no hechiceros tienen hechizos de opciones de
			// adiestramiento o cultura.
			if (skill.getName().toLowerCase().equals("Desarrollo de Puntos de Poder")) {
				Category cat1 = Personaje.getInstance().DevolverCategoriaDeNombre("Listas Abiertas de Hechizos");
				Category cat2 = Personaje.getInstance().DevolverCategoriaDeNombre("Listas Cerradas de Hechizos");

				if (characterPlayer.getTotalValue(skill) < Math.max(cat1.DevolvermaxRanksEnHabilidad(),
						cat2.DevolvermaxRanksEnHabilidad())) {
					bonus += Math.min(10 * characterPlayer.getCurrentLevelNumber(), 50);
				}
			}
			if (categoriaPadre.DevolverNombre().startsWith("ArmaduraÂ·") && characterPlayer.getTotalValue(skill) < 30) {
				bonus += 20;
			}
		}

		return bonus;
	}

	private int AleatorizacionPorRangos() {
		int bonus = 0;

		// No more than 50 ranks
		if (characterPlayer.getRealRanks(skill) >= 50) {
			return -1000;
		}

		// Siempre va bien tener algunos puntos de vida.
		if (skill.getCategory().getCategoryType().equals(CategoryType.FD) && characterPlayer.getRealRanks(skill) == 0) {
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

		if (skill.getName().toLowerCase().equals(Spanish.SOFT_LEATHER_TAG) && characterPlayer.getTotalValue(skill) > 30) {
			return -1000;
		}
		if (skill.getName().toLowerCase().equals(Spanish.HARD_LEATHER_TAG) && characterPlayer.getTotalValue(skill) > 90) {
			return -1000;
		}
		if (skill.getName().toLowerCase().equals(Spanish.CHAINMAIL_TAG) && characterPlayer.getTotalValue(skill) > 100) {
			return -1000;
		}
		if (skill.getName().toLowerCase().equals(Spanish.CUIRASS_TAG) && characterPlayer.getTotalValue(skill) > 120) {
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

	private int AleatorizacionPorHabilidad() {
		int bonus = 0;
		// Main Guache cannot be the first weapon.
		if (skill.getName().toLowerCase().equals(Spanish.MAIN_GAUCHE_TAG)
				&& characterPlayer.getSkillsWithRanks(skill.getCategory()).size() < 1) {
			return -10000;
		}

		// No rocks in random characters.
		if (skill.getName().toLowerCase().contains(Spanish.ROCKS_TAG) && characterPlayer.getRealRanks(skill) < 1) {
			return -10000;
		}

		// A veces se aprenden demasiados tipos de armas.
		if ((skill.getCategory().getCategoryGroup().equals(CategoryGroup.WEAPON))
				&& characterPlayer.getSkillsWithRanks(skill.getCategory()).size() > (3 - characterPlayer
						.getNewRankCost(skill.getCategory(), 0, 1))
				|| Personaje.getInstance().DevolverArmasAprendidasEnEsteNivel() > 5) {
			bonus -= 50;
		}
		return bonus;
	}

	private int AleatorizacionPorOpciones() {
		if (!characterPlayer.isFirearmsAllowed() && skill.getCategory().getCategoryGroup().equals(CategoryGroup.WEAPON)
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
		if (Personaje.getInstance().EsHechicero()) {
			if (Personaje.getInstance().reino.contains("Esencia")) {
				if (skill.getName().toLowerCase().equals(Spanish.SHIELD_SPELLS_TAG)) {
					return Math.max(50 - specializationLevel * 8, 10);
				}
				if (skill.getName().toLowerCase().equals(Spanish.QUICKNESS_SPELLS_TAG)) {
					return Math.max(20 - specializationLevel * 5, 0);
				}
			}
			if (Personaje.getInstance().reino.contains("Mentalismo")) {
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
		if (Personaje.getInstance().EsCombatiente()) {
			if (Personaje.getInstance().adiestramientosAntiguos.contains("Caballero")) {
				if (skill.getName().toLowerCase().equals("Montar: Caballos")) {
					return 50;
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
			if (characterPlayer.getRealRanks(skill) - rangosCultura < 2) {
				bonus -= Personaje.getInstance().DevolverArmasAprendidas() * (specializationLevel + 1) * 5;
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
		prob = (characterPlayer.getRealRanks(skill)) * 5 + (characterPlayer.getRanksSpentInSpecializations(skill) * 15);
		if (prob > 50 + characterPlayer.getRanksSpentInSpecializations(skill) * 15) {
			prob = 50 + characterPlayer.getRanksSpentInSpecializations(skill) * 15;
		}
		return prob;
	}

	/**
	 * Select common and professional skills.
	 */
	private int FacilidadHabilidad() {
		if (characterPlayer.isGeneralized(skill)) {
			return 50 - CaroHabilidad() * 5;
		}
		if (characterPlayer.isRestricted(skill)) {
			return -1000;
		}
		if (characterPlayer.isProfessional(skill)) {
			return 90 - CaroHabilidad() * 10;
		}
		if (characterPlayer.isCommon(skill)) {
			return 75 - CaroHabilidad() * 10;
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
				&& (characterPlayer.getRemainingDevelopmentPoints() > 10 && !estiloDeVida)
				|| (characterPlayer.getRemainingDevelopmentPoints() > 15 && estiloDeVida)) {
			return -1000;
		}
		return 0;
	}
}
