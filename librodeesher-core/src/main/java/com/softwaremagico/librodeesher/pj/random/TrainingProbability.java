package com.softwaremagico.librodeesher.pj.random;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.softwaremagico.librodeesher.basics.Spanish;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.training.Training;
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
		Training training = TrainingFactory.getTraining(trainingName);
		int cost = characterPlayer.getProfession().getTrainingCost(trainingName);
		// No training from different realm of magic.
//		if (!ad.reino) {
//			return 0;
//		}
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
}
