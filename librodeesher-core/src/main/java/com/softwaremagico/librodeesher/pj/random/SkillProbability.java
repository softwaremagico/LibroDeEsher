package com.softwaremagico.librodeesher.pj.random;

import java.util.Map;

import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.skills.Skill;

public class SkillProbability {
	private CharacterPlayer characterPlayer;
	private Skill skill;
	private Map<String, Integer> suggestedSkillsRanks;
	private int tries;

	public SkillProbability(CharacterPlayer characterPlayer, Skill skill, int tries,
			Map<String, Integer> suggestedSkillsRanks) {
		this.characterPlayer = characterPlayer;
		this.skill = skill;
		this.suggestedSkillsRanks = suggestedSkillsRanks;
		this.tries=tries;
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

		if (nuevosRangos <= 3) {
			if (Personaje.getInstance().PuntosDesarrolloNoGastados() >= Personaje.getInstance()
					.CosteCategoriaYHabilidad(categoriaPadre, nuevosRangos, this)) {
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
					probability += HabilidadAbsurda();
				// Ponemos una cota superior y una cota inferior.
				if (probability > 90) {
					probability = 90;
				}
				if (probability < 1 && categoriaPadre.DevolverRangos() > 0) {
					probability = 1;
				}
				probability += MaximoRangos();
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
				Personaje.getInstance().nivel
						- (Personaje.getInstance().CosteCategoriaYHabilidad(categoriaPadre, 0, this))
						- DevolverRangos() * 2, 0);
		return bonus;
	}

	/**
	 * Cuanto cuesta en puntos de desarrollo.
	 */
	private int CaroHabilidad() {
		// Las que valen uno de coste son casi obligatorias cogerlas.
		if (Personaje.getInstance().CosteCategoriaYHabilidad(categoriaPadre, nuevosRangos, this) == 1) {
			return -100;
		}
		if (categoriaPadre.DevolverNombre().equals("Listas BÃ¡sicas de Hechizos")) {
			return (Personaje.getInstance().CosteCategoriaYHabilidad(categoriaPadre, nuevosRangos, this) - 8) * 10;
		}
		return (Personaje.getInstance().CosteCategoriaYHabilidad(categoriaPadre, nuevosRangos, this) - 5) * 10;
	}

	/**
	 * Devuelve un modificador de acuerdo con algunos criterios.
	 */
	private int AplicarInteligenciaALaAleatorizacion() {
		return AleatorizacionPorRaza() + AleatorizacionPorProfesion() + AleatorizacionPorRangos()
				+ AleatorizacionPorHabilidad() + AleatorizacionPorOpciones();
	}

	private int AleatorizacionPorRaza() {
		// Los caballos para uno y los lobos para otros.
		if (DevolverNombre().equals("Montar: Caballos") && Personaje.getInstance().raza.contains("Orco")) {
			return -1000;
		}
		if (DevolverNombre().equals("Montar: Lobos") && !Personaje.getInstance().raza.contains("Orco")) {
			return -1000;
		}
		if (DevolverNombre().equals("Montar: Osos") && !Personaje.getInstance().raza.contains("Enanos")) {
			return -1000;
		}

		// Los ataques raciales com mordisco y demÃ¡s, solo para aquellos a los
		// que sea comunes.
		if (nombre.startsWith("Ataque Racial: ") && !EsComun()) {
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
			if (categoriaPadre.DevolverNombre().equals("Listas BÃ¡sicas de Hechizos")) {
				if (categoriaPadre.DevolverHabilidadesConNuevosRangos() < Esher.especializacion + 2) {
					if (DevolverRangos() < Personaje.getInstance().nivel + Esher.especializacion + 2) {
						if (categoriaPadre.DevolverHabilidadesConNuevosRangos() < Esher.especializacion + 1) {
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

			if (categoriaPadre.DevolverNombre().equals("Listas Abiertas de Hechizos")) {
				if (categoriaPadre.DevolverHabilidadesConNuevosRangos() < Esher.especializacion + 2) {
					if (DevolverRangos() < Personaje.getInstance().nivel + Esher.especializacion + 2) {
						if (categoriaPadre.DevolverHabilidadesConNuevosRangos() == 0) {
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
			if (categoriaPadre.DevolverNombre().equals("Listas Cerradas de Hechizos")) {
				if (categoriaPadre.DevolverHabilidadesConRangos() < 3) {
					if (DevolverRangos() < Personaje.getInstance().nivel + Esher.especializacion + 2) {
						if (categoriaPadre.DevolverHabilidadesConNuevosRangos() == 0) {
							bonus += 20;
						} else {
							bonus += 10;
						}
					} else {
						return -150;
					}
				}
			}
			if (categoriaPadre.DevolverNombre().equals("Listas BÃ¡sicas de otras Profesiones")) {
				if (DevolverRangos() < Personaje.getInstance().nivel + Esher.especializacion + 2
						&& categoriaPadre.DevolverHabilidadesConNuevosRangos() < 1) {
					bonus += 5;
				} else {
					bonus -= 150;
				}
			}
			if (categoriaPadre.DevolverNombre().equals("Listas Abiertas de otros Reinos")) {
				if (DevolverRangos() > Personaje.getInstance().nivel + Esher.especializacion + 2
						|| categoriaPadre.DevolverHabilidadesConNuevosRangos() > 1) {
					bonus -= 150;
				}
			}
			if (categoriaPadre.DevolverNombre().equals("Listas Cerradas de otros Reinos")) {
				if (DevolverRangos() > Personaje.getInstance().nivel + Esher.especializacion + 2
						|| categoriaPadre.DevolverHabilidadesConNuevosRangos() > 1) {
					bonus -= 150;
				}
			}
			if (categoriaPadre.DevolverNombre().equals("Listas BÃ¡sicas de otros Reinos")) {
				if (DevolverRangos() > Personaje.getInstance().nivel + Esher.especializacion + 2
						|| categoriaPadre.DevolverHabilidadesConNuevosRangos() > 1) {
					bonus -= 150;
				}
			}
			if (categoriaPadre.DevolverNombre().equals("Listas Abiertas Arcanas")) {
				if (DevolverRangos() > Personaje.getInstance().nivel + Esher.especializacion + 2
						|| categoriaPadre.DevolverHabilidadesConNuevosRangos() > 2) {
					bonus -= 150;
				}
			}
			if (nombre.startsWith("Hechizos Dirigidos de")) {
				String elemento = nombre.replaceAll("Hechizos Dirigidos de ", "");
				if (((hab = Personaje.getInstance().DevolverHabilidadDeNombre("Ley del ", elemento)) != null || (hab = Personaje
						.getInstance().DevolverHabilidadDeNombre("Ley de ", elemento)) != null)
						&& hab.DevolverRangos() > 0) {
					bonus += 10 * (hab.DevolverRangos() - DevolverRangos());
				} else {
					bonus -= 150;
				}
			}
			if (nombre.equals("Desarrollo de Puntos de Poder")) {
				if (nuevosRangos == 0) {
					bonus += 50;
				} else {
					if (rangos + nuevosRangos < Personaje.getInstance().nivel) {
						bonus += 10 * (Personaje.getInstance().nivel - rangos + nuevosRangos);
					}
					// Al menos tener PPs suficientes para lanzar el mejor
					// hechizo.
					if (Personaje.getInstance().DevolverMaximoNivelHechizos() > Total()) {
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
				&& categoriaPadre.DevolverHabilidadesConNuevosRangos() > 0
				&& (Personaje.getInstance().CosteCategoriaYHabilidad(categoriaPadre, 0, this) > 2)) {
			bonus -= 100;
		}

		// Para los guerreros.
		if (Personaje.getInstance().EsCombatiente()) {
			if ((categoriaPadre.DevolverNombre().startsWith("ArmasÂ·")) && nuevosRangos == 0
					&& categoriaPadre.costeRango[0] < 2) {
				if (categoriaPadre.DevolverHabilidadesConNuevosRangos() < 2) {
					bonus += 20;
				}
				if (categoriaPadre.DevolverHabilidadesConNuevosRangos() < 3) {
					bonus += 10;
				}
			}

			if (nombre.equals("Desarrollo FÃ­sico") && DevolverRangos() < 10 && nuevosRangos == 0) {
				bonus += 20;
			}
			// A veces lo no hechiceros tienen hechizos de opciones de
			// adiestramiento o cultura.
			if (nombre.equals("Desarrollo de Puntos de Poder")) {
				Categoria cat1 = Personaje.getInstance().DevolverCategoriaDeNombre(
						"Listas Abiertas de Hechizos");
				Categoria cat2 = Personaje.getInstance().DevolverCategoriaDeNombre(
						"Listas Cerradas de Hechizos");

				if (Total() < Math.max(cat1.DevolverMaximoRangosEnHabilidad(),
						cat2.DevolverMaximoRangosEnHabilidad())) {
					bonus += Math.min(10 * Personaje.getInstance().nivel, 50);
				}
			}
			if (categoriaPadre.DevolverNombre().startsWith("ArmaduraÂ·") && Total() < 30) {
				bonus += 20;
			}
		}

		return bonus;
	}

	private int AleatorizacionPorRangos() {
		int bonus = 0;

		/* Nunca mÃ¡s de 50 rangos en una habilidad */
		if (DevolverRangos() >= 50) {
			return -1000;
		}

		// Siempre va bien tener algunos puntos de vida.
		if (nombre.equals("Desarrollo FÃ­sico") && DevolverRangos() == 0) {
			bonus += 40;
		}

		// No tiene sentido subir un idioma mÃ¡s de 10 rangos;
		if (categoriaPadre.DevolverNombre().equals("ComunicaciÃ³n") && DevolverRangos() > 9) {
			return -1000;
		}

		// Las habilidades de comunicaciÃ³n tienen muchos rangos, por lo que
		// tienen demasiadas probabilidades de subir.
		if (categoriaPadre.DevolverNombre().equals("ComunicaciÃ³n") && DevolverRangos() > 60) {
			bonus -= 40;
		}

		if (DevolverNombre().equals("Cuero Blando") && Total() > 30) {
			return -1000;
		}
		if (DevolverNombre().equals("Cuero Endurecido") && Total() > 90) {
			return -1000;
		}
		if (DevolverNombre().equals("Cota de Mallas") && Total() > 100) {
			return -1000;
		}
		if (DevolverNombre().equals("Coraza") && Total() > 120) {
			return -1000;
		}

		/*
		 * Cuando se tiene muchos rangos, es tonteria subir mÃ¡s de un rango en
		 * una habilidad por nivel
		 */
		if (DevolverRangos() > 10 && nuevosRangos > 0) {
			bonus -= 50;
		}

		return bonus;
	}

	private int AleatorizacionPorHabilidad() {
		int bonus = 0;
		/* Es tonterÃ­a incluir el Main Gauche si no tiene ningun arma mas. */
		if (DevolverNombre().equals("Main Gauche") && categoriaPadre.DevolverHabilidadesConRangos() < 1) {
			return -10000;
		}

		// Evitemos que aleatoriamente se lancen rocas.
		if (nombre.contains("Rocas") && DevolverRangos() < 1) {
			return -10000;
		}

		// A veces se aprenden demasiados tipos de armas.
		if ((categoriaPadre.DevolverNombre().startsWith("ArmasÂ·"))
				&& categoriaPadre.DevolverHabilidadesConNuevosRangos() > (3 - Personaje.getInstance()
						.CosteCategoriaYHabilidad(categoriaPadre, 0, this))
				|| Personaje.getInstance().DevolverArmasAprendidasEnEsteNivel() > 5) {
			bonus -= 50;
		}
		return bonus;
	}

	private int AleatorizacionPorOpciones() {
		if (!Esher.armasFuegoPermitidas && categoriaPadre.DevolverNombre().contains("Armas")
				&& categoriaPadre.DevolverNombre().contains("Fuego")) {
			return -10000;
		}

		if (!Esher.armasFuegoPermitidas && categoriaPadre.DevolverNombre().contains("Maniobras de Combate")
				&& DevolverNombre().contains("Fuego")) {
			return -10000;
		}
		return 0;
	}

	/**
	 * Habilidades relacionadas con la profesion del personaje.
	 * 
	 * @return
	 */
	private int HabilidadesPreferidasHechiceros() {
		// Algunos hechizos son mejores.
		if (Esher.inteligencia) {
			if (Personaje.getInstance().EsHechicero()) {
				if (Personaje.getInstance().reino.contains("Esencia")) {
					if (nombre.equals("MaestrÃ­a de los Escudos")) {
						return Math.max(50 - Esher.especializacion * 8, 10);
					}
					if (nombre.equals("Sendas de la Rapidez")) {
						return Math.max(20 - Esher.especializacion * 5, 0);
					}
				}
				if (Personaje.getInstance().reino.contains("Mentalismo")) {
					if (nombre.equals("EvasiÃ³n de los Ataques")) {
						return Math.max(50 - Esher.especializacion * 8, 10);
					}
					if (nombre.equals("AutocuraciÃ³n")) {
						return Math.max(30 - Esher.especializacion * 5, 5);
					}
					if (nombre.equals("Velocidad")) {
						return Math.max(20 - Esher.especializacion * 5, 0);
					}
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
		if (Esher.inteligencia) {
			if (Personaje.getInstance().EsCombatiente()) {
				if (Personaje.getInstance().adiestramientosAntiguos.contains("Caballero")) {
					if (nombre.equals("Montar: Caballos")) {
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
		// Una pequeÃ±a penalizaciÃ³n al aÃ±adir habilidades nuevas que no son
		// tÃ­picas.
		if (DevolverRangos() == 0 && !(EsProfesional() || EsComun())) {
			bonus -= Math.pow(categoriaPadre.DevolverHabilidadesConRangos(), 2) * (Esher.especializacion + 1)
					* 10;
		}
		// Evitar demasiada variedad de arma.
		if (categoriaPadre.DevolverNombre().startsWith("ArmasÂ·")) {
			if (DevolverRangos() - rangosCultura < 2) {
				bonus -= Personaje.getInstance().DevolverArmasAprendidas() * (Esher.especializacion + 1) * 5;
			}
		}
		// No aÃ±adir demasiados rangos en habilidades de una misma categoria.
		bonus -= Math.max(Math.pow(categoriaPadre.DevolverHabilidadesConNuevosRangos(), 3)
				* (Esher.especializacion + 1) * 3, 0);
		return bonus;
	}

	/**
	 * Da preferencia a aquellas habilidades del personaje que ya han sido
	 * subidas.
	 */
	private int HabilidadPreferida() {
		int prob;
		prob = (DevolverRangos()) * 5 + (RangosGastadosEnEspecializacion() * 15);
		if (prob > 50 + RangosGastadosEnEspecializacion() * 15) {
			prob = 50 + RangosGastadosEnEspecializacion() * 15;
		}
		return prob;
	}

	/**
	 * Las habilidades comunes y profesionales son mÃ¡s importantes para el
	 * Personaje.getInstance().
	 */
	private int FacilidadHabilidad() {
		if (generalizada) {
			return 50 - CaroHabilidad() * 5;
		}
		if (EsRestringida()) {
			return -1000;
		}
		if (EsProfesional()) {
			return 90 - CaroHabilidad() * 10;
		}
		if (EsComun()) {
			return 75 - CaroHabilidad() * 10;
		}
		return 0;
	}

	/**
	 * Existen ciertas habilidades que son absurdas tenerlas salvo ocasiones
	 * especiales.
	 * 
	 * @return
	 */
	private int HabilidadAbsurda() {
		if (nombre.contains("LicantropÃ­a") || nombre.contains("Osos") || nombre.contains("Camellos")) {
			return -1000;
		}
		return 0;
	}

	private int MaximoRangos() {
		if (Personaje.getInstance().nivel == 1 && (DevolverRangos() > 10 && !estiloDeVida)
				|| (DevolverRangos() > 15 && estiloDeVida)) {
			return -1000;
		}
		return 0;
	}

	public void DeshabilitarAleatorio(boolean value) {
		noElegirAleatorio = value;
	}

}
