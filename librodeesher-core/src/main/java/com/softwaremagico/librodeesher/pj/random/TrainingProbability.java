package com.softwaremagico.librodeesher.pj.random;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.softwaremagico.librodeesher.basics.Spanish;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.characteristic.CharacteristicRoll;
import com.softwaremagico.librodeesher.pj.training.Training;
import com.softwaremagico.librodeesher.pj.training.TrainingCategory;
import com.softwaremagico.librodeesher.pj.training.TrainingFactory;
import com.softwaremagico.librodeesher.pj.training.TrainingType;

public class TrainingProbability {

	protected static List<String> shuffleTrainings(CharacterPlayer characterPlayer) {
		List<String> trainings = TrainingFactory.getAvailableTrainings();
		Collections.shuffle(trainings);
		List<String> trainingList = new ArrayList<>();
		// For elementalist, elementalist training first.
		if (characterPlayer.getProfession().isElementalist()) {
			for (int i = 0; i < trainings.size(); i++) {
				String ad = trainings.get(i);
				if (isElementalistTraining(ad)) {
					trainingList.add(ad);
					trainings.remove(i);
					i--;
				}
			}
			Collections.shuffle(trainingList);
		}
		for (int j = 0; j < trainings.size(); j++) {
			trainingList.add(trainings.get(j));
		}
		return trainingList;
	}

	private static boolean isElementalistTraining(String training) {
		return training.contains(Spanish.ELEMENTALIST_INITIAL_TAG)
				|| training.contains(Spanish.ELEMENTALIST_PROFESSION);
	}

	protected static int trainingRandomness(CharacterPlayer characterPlayer, String trainingName, int specialization) {
		int cost = characterPlayer.getProfession().getTrainingCost(trainingName);
		// No training from different realm of magic.
		// if (!ad.reino) {
		// return 0;
		// }
		if (cost > characterPlayer.getRemainingDevelopmentPoints()) {
			return 0;
		}

		int probability = ((28 - cost) * 2 + characterPlayer.getLevelUps().size() - ((characterPlayer
				.getTrainingsNames().size() + specialization) * 20));

		if (characterPlayer.getProfession().getTrainingTypes().get(trainingName).equals(TrainingType.FAVOURITE)) {
			probability += 25;
		}

		if (probability < 1 && (characterPlayer.getTrainingsNames().size() < characterPlayer.getLevelUps().size() / 10)) {
			probability = 1;
		}

		if (characterPlayer.getProfession().getTrainingTypes().get(trainingName).equals(TrainingType.FORBIDDEN)) {
			probability -= 1500;
		}

		// Elementalist must select a training.
		if (characterPlayer.getProfession().isElementalist() && isElementalistTraining(trainingName)
				&& characterPlayer.getTrainingsNames().isEmpty()) {
			probability += 1000;
		}
		return probability / (characterPlayer.getCurrentLevel().getTrainings().size() + 1);
	}

	public static void setRandomCategoryRanks(CharacterPlayer characterPlayer, String trainingName) {
		int ret = 0;
		Training training = TrainingFactory.getTraining(trainingName);
		for (int i = 0; i < training.getCategoriesWithRanks().size(); i++) {
			TrainingCategory trainingCategory = training.getCategoriesWithRanks().get(i);
			while (characterPlayer.getTrainingDecision(trainingName)
					.DevolverTotalRangosHabilidadesGastadosGrupoAdiestramiento(trainingCategory.nombre) < trainingCategory.rangosHabilidades
					&& ret == 0) {
				if (trainingCategory.DevolverNumeroHabilidadesConRangos() < trainingCategory.getMinSkills()) {
					if (!trainingCategory.AñadirRangoNuevaHabilidad()) {
						ret = trainingCategory.AñadirUnRangoAleatorio();
					}
				} else {
					if (DevolverNumeroHabilidadesConRangosDeGrupo(trainingCategory.nombre) < trainingCategory
							.getMaxSkills()) {
						ret = trainingCategory.AñadirUnRangoAleatorio();
					} else {
						if (!trainingCategory.AñadirUnRangoHabilidadExistente()) {
							ret = trainingCategory.AñadirUnRangoAleatorio();
						}
					}
				}
			}
		}
	}

	public static void setRandomCharacteristicsUpgrades(CharacterPlayer characterPlayer, String trainingName) {
		for (int i = 0; i < TrainingFactory.getTraining(trainingName).getUpdateCharacteristics().size(); i++) {
			--Comprobar que no existen ya los aumentos de caracteristicas. 
			List<String> availableUpdates = TrainingFactory.getTraining(trainingName).getUpdateCharacteristics().get(i);
			// Order by profession preferences.
			boolean updated = false;
			String lastCharacteristicChecked = "";
			for (String abbreviature : characterPlayer.getProfession().getCharacteristicPreferences()) {
				// Available for update.
				if (availableUpdates.contains(abbreviature)) {
					lastCharacteristicChecked = abbreviature;
					// Good to be updated if: long distance, medium distance per values > 70 or short distance per
					// values > 85
					if (characterPlayer.getCharacteristicTemporalValue(abbreviature)
							- characterPlayer.getCharacteristicPotentialValue(abbreviature) > 20
							|| (characterPlayer.getCharacteristicTemporalValue(abbreviature)
									- characterPlayer.getCharacteristicPotentialValue(abbreviature) > 10 && characterPlayer
									.getCharacteristicTemporalValue(abbreviature) > 70)
							|| (characterPlayer.getCharacteristicTemporalValue(abbreviature)
									- characterPlayer.getCharacteristicPotentialValue(abbreviature) > 5 && characterPlayer
									.getCharacteristicTemporalValue(abbreviature) > 85)) {
						AñadirAumentoCaracteristicaSeleccionadaAdiestramiento(characterPlayer, trainingName,
								abbreviature);
						updated = true;
						break;
					}
				}
			}
			// Updates are mandatory. Update the last one to avoid decreasing an important one.
			if (!updated) {
				AñadirAumentoCaracteristicaSeleccionadaAdiestramiento(characterPlayer, trainingName,
						lastCharacteristicChecked);
			}
		}
	}

	private static void AñadirAumentoCaracteristicaSeleccionadaAdiestramiento(CharacterPlayer characterPlayer,
			String trainingName, String abbreviature) {
		CharacteristicRoll characteristicRoll = characterPlayer.getNewCharacteristicTrainingUpdate(abbreviature,
				trainingName);
		characterPlayer.getTrainingDecision(trainingName).addCharacteristicUpdate(characteristicRoll);
	}

}
