package com.softwaremagico.librodeesher.pj.random;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.softwaremagico.librodeesher.basics.Spanish;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.categories.CategoryComparatorBySkillWithLessRanks;
import com.softwaremagico.librodeesher.pj.categories.CategoryComparatorBySkillWithRanks;
import com.softwaremagico.librodeesher.pj.characteristic.CharacteristicRoll;
import com.softwaremagico.librodeesher.pj.characteristic.CharacteristicsAbbreviature;
import com.softwaremagico.librodeesher.pj.training.Training;
import com.softwaremagico.librodeesher.pj.training.TrainingCategory;
import com.softwaremagico.librodeesher.pj.training.TrainingFactory;
import com.softwaremagico.librodeesher.pj.training.TrainingType;

public class TrainingProbability {

	protected static List<String> shuffleTrainings(
			CharacterPlayer characterPlayer) {
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

	protected static int trainingRandomness(CharacterPlayer characterPlayer,
			String trainingName, int specialization) {
		int cost = characterPlayer.getProfession()
				.getTrainingCost(trainingName);
		// No training from different realm of magic.
		// if (!training.reino) {
		// return 0;
		// }
		if (cost > characterPlayer.getRemainingDevelopmentPoints()) {
			return 0;
		}

		// Has not the characteristics requirements.
		Training training = TrainingFactory.getTraining(trainingName);
		for (CharacteristicsAbbreviature abbreviature : training.getCharacteristicRequirements()
				.keySet()) {
			if (training.getCharacteristicRequirements().get(abbreviature) > characterPlayer
					.getCharacteristicTemporalValue(abbreviature)) {
				return 0;
			}
		}

		// Has not the skills requirements.
		// for (String skill : training.getSkillRequirements().keySet()) {
		// if (training.getSkillRequirements().get(skill) >
		// characterPlayer.getRealRanks(SkillFactory.getSkill(skill)))
		// {
		// return 0;
		// }
		// }

		int probability = ((28 - cost) * 2
				+ characterPlayer.getLevelUps().size() - ((characterPlayer
				.getTrainingsNames().size() + specialization) * 20));

		if (characterPlayer.getProfession().getTrainingTypes()
				.get(trainingName).equals(TrainingType.FAVOURITE)) {
			probability += 25;
		}

		if (probability < 1
				&& (characterPlayer.getTrainingsNames().size() < characterPlayer
						.getLevelUps().size() / 10)) {
			probability = 1;
		}

		if (characterPlayer.getProfession().getTrainingTypes()
				.get(trainingName).equals(TrainingType.FORBIDDEN)) {
			probability -= 1500;
		}

		// Elementalist must select a training.
		if (characterPlayer.getProfession().isElementalist()
				&& isElementalistTraining(trainingName)
				&& characterPlayer.getTrainingsNames().isEmpty()) {
			probability += 1000;
		}
		return probability
				/ (characterPlayer.getCurrentLevel().getTrainings().size() + 1);
	}

	public static void setRandomCategoryRanks(CharacterPlayer characterPlayer,
			String trainingName, int specialization) {
		Training training = TrainingFactory.getTraining(trainingName);

		// For each category
		for (TrainingCategory trainingCategory : training
				.getCategoriesWithRanks()) {
			// Choose one category option.
			List<String> availableCategories = trainingCategory
					.getCategoryOptions();
			if (specialization >= 0) {
				Collections
						.sort(availableCategories,
								new CategoryComparatorBySkillWithRanks(
										characterPlayer));
			} else {
				Collections.sort(availableCategories,
						new CategoryComparatorBySkillWithLessRanks(
								characterPlayer));
			}

			// Select category from list.
			int index = 0;
			while (true) {
				String categoryName = availableCategories.get(index
						% availableCategories.size());
				int probability = Math.abs(specialization * 30) + 15;
				if (Math.random() * 100 < probability) {
					characterPlayer
							.getTrainingDecision(trainingName)
							.addSelectedCategory(training.getTrainingCategoryIndex(trainingCategory), categoryName);
					break;
				}
				index++;
			}

			// Order skills by probability.

			// Set ranks to skills.
		}

		// for (int i = 0; i < training.getCategoriesWithRanks().size(); i++) {
		// TrainingCategory trainingCategory =
		// training.getCategoriesWithRanks().get(i);
		// while (characterPlayer.getTrainingDecision(trainingName)
		// .DevolverTotalRangosHabilidadesGastadosGrupoAdiestramiento(trainingCategory.nombre)
		// < characterPlayer
		// .getSkillsRanks(trainingName, trainingCategory)
		// && ret == 0) {
		// if (characterPlayer.getSkillsWithRanks(trainingName,
		// trainingCategory) < trainingCategory
		// .getMinSkills()) {
		// if (!trainingCategory.AñadirRangoNuevaHabilidad()) {
		// ret = trainingCategory.AñadirUnRangoAleatorio();
		// }
		// } else {
		// if
		// (DevolverNumeroHabilidadesConRangosDeGrupo(trainingCategory.nombre) <
		// trainingCategory
		// .getMaxSkills()) {
		// ret = trainingCategory.AñadirUnRangoAleatorio();
		// } else {
		// if (!trainingCategory.AñadirUnRangoHabilidadExistente()) {
		// ret = trainingCategory.AñadirUnRangoAleatorio();
		// }
		// }
		// }
		// }
		// }
	}

	public static void setRandomCharacteristicsUpgrades(
			CharacterPlayer characterPlayer, String trainingName) {
		// Only do it for remaining characteristic updates (if any).
		for (int i = characterPlayer.getTrainingDecision(trainingName)
				.getCharacteristicsUpdates().size(); i < TrainingFactory
				.getTraining(trainingName).getUpdateCharacteristics().size(); i++) {
			List<CharacteristicsAbbreviature> availableUpdates = TrainingFactory
					.getTraining(trainingName).getUpdateCharacteristics()
					.get(i);
			// Order by profession preferences.
			boolean updated = false;
			CharacteristicsAbbreviature lastCharacteristicChecked = null;
			for (CharacteristicsAbbreviature characteristic : characterPlayer.getProfession()
					.getCharacteristicPreferences()) {
				// Available for update.
				if (availableUpdates.contains(characteristic)) {
					// Good to be updated if: long distance, medium distance per
					// values > 70 or short distance per
					// values > 85
					lastCharacteristicChecked = characteristic;
					if (characterPlayer
							.getCharacteristicTemporalValue(characteristic)
							- characterPlayer
									.getCharacteristicPotentialValue(characteristic) > 20
							|| (characterPlayer
									.getCharacteristicTemporalValue(characteristic)
									- characterPlayer
											.getCharacteristicPotentialValue(characteristic) > 10 && characterPlayer
									.getCharacteristicTemporalValue(characteristic) > 70)
							|| (characterPlayer
									.getCharacteristicTemporalValue(characteristic)
									- characterPlayer
											.getCharacteristicPotentialValue(characteristic) > 5 && characterPlayer
									.getCharacteristicTemporalValue(characteristic) > 85)) {
						addCharacteristicUpdate(characterPlayer, trainingName,
								characteristic);
						updated = true;
						break;
					}
				}
			}
			// Updates are mandatory. Update the last one to avoid decreasing an
			// important one.
			if (!updated) {
				addCharacteristicUpdate(characterPlayer, trainingName,
						lastCharacteristicChecked);
			}
		}
	}

	private static void addCharacteristicUpdate(
			CharacterPlayer characterPlayer, String trainingName,
			CharacteristicsAbbreviature abbreviature) {
		CharacteristicRoll characteristicRoll = characterPlayer
				.getNewCharacteristicTrainingUpdate(abbreviature, trainingName);
		characterPlayer.getTrainingDecision(trainingName)
				.addCharacteristicUpdate(characteristicRoll);
	}

	public static void setRandomObjects(CharacterPlayer characterPlayer,
			String trainingName) {
		int accepted = 1;
		Training training = TrainingFactory.getTraining(trainingName);
		for (int i = 0; i < training.getObjects().size(); i++) {
			if ((Math.random() * 100) < training.getObjects().get(i)
					.getProbability()
					/ (accepted)) {
				characterPlayer.getTrainingDecision(trainingName)
						.getEquipment().add(training.getObjects().get(i));
				accepted++;
			}
		}
	}

}
