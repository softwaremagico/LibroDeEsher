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
import com.softwaremagico.librodeesher.pj.skills.SkillFactory;
import com.softwaremagico.librodeesher.pj.training.InvalidTrainingException;
import com.softwaremagico.librodeesher.pj.training.Training;
import com.softwaremagico.librodeesher.pj.training.TrainingCategory;
import com.softwaremagico.librodeesher.pj.training.TrainingFactory;
import com.softwaremagico.librodeesher.pj.training.TrainingSkill;
import com.softwaremagico.librodeesher.pj.training.TrainingType;
import com.softwaremagico.log.EsherLog;

public class TrainingProbability {

	protected static List<String> shuffleTrainings(CharacterPlayer characterPlayer, List<String> suggestedTrainings) {
		List<String> allTrainings = TrainingFactory.getAvailableTrainings();
		Collections.shuffle(allTrainings);

		// Suggested trainings at the beginning
		if (suggestedTrainings != null) {
			allTrainings.removeAll(suggestedTrainings);
		}

		// For elementalist, elementalist training first.
		List<String> elementalistTrainings = new ArrayList<>();
		if (characterPlayer.getProfession().isElementalist()) {
			for (int i = 0; i < allTrainings.size(); i++) {
				String training = allTrainings.get(i);
				if (isElementalistTraining(training)) {
					elementalistTrainings.add(training);
					allTrainings.remove(i);
					i--;
				}
			}
		}

		// Add preferences to the beggining.
		for (int j = 0; j < elementalistTrainings.size(); j++) {
			allTrainings.add(0, elementalistTrainings.get(j));
		}
		if (suggestedTrainings != null) {
			for (String training : suggestedTrainings) {
				allTrainings.add(0, training);
			}
		}
		// Remove already acquired trainings.
		allTrainings.removeAll(characterPlayer.getTrainings());

		return allTrainings;
	}

	private static boolean isElementalistTraining(String training) {
		return training.contains(Spanish.ELEMENTALIST_INITIAL_TAG) || training.contains(Spanish.ELEMENTALIST_PROFESSION);
	}

	protected static int trainingRandomness(CharacterPlayer characterPlayer, String trainingName, int specialization, List<String> suggestedTrainings,
			int finalLevel) throws InvalidTrainingException {
		int cost = characterPlayer.getTrainingCost(trainingName);
		// No too expensive trainings.
		if (cost > characterPlayer.getRemainingDevelopmentPoints()) {
			return 0;
		}

		// Has not the characteristics requirements.
		Training training = TrainingFactory.getTraining(trainingName);
		for (CharacteristicsAbbreviature abbreviature : training.getCharacteristicRequirements().keySet()) {
			if (training.getCharacteristicRequirements().get(abbreviature) > characterPlayer.getCharacteristicTemporalValue(abbreviature)) {
				return 0;
			}
		}

		// No training from different realm of magic.

		// Has not the skills requirements.
		for (String skill : training.getSkillRequirements().keySet()) {
			if (training.getSkillRequirements().get(skill) > characterPlayer.getRealRanks(SkillFactory.getSkill(skill))) {
				return 0;
			}
		}

		// Suggested training
		if (suggestedTrainings != null) {
			if (characterPlayer.getSelectedTrainings() != null) {
				suggestedTrainings.removeAll(characterPlayer.getSelectedTrainings());
			}
			if (suggestedTrainings.contains(trainingName) && characterPlayer.getTrainingCost(trainingName) <= characterPlayer.getRemainingDevelopmentPoints()) {
				// At least one training per level
				if (characterPlayer.getCurrentLevel().getTrainings().isEmpty()) {
					return 100;
				} else if (suggestedTrainings.size() > finalLevel - characterPlayer.getLevelUps().size()) {
					return 100;
				}
			}
		}

		int probability = (int) ((28 - cost) * 1.5 + characterPlayer.getLevelUps().size() - ((characterPlayer.getSelectedTrainings().size() + specialization) * 25));

		if (characterPlayer.getProfession().getTrainingTypes().get(trainingName) != null
				&& characterPlayer.getProfession().getTrainingTypes().get(trainingName).equals(TrainingType.FAVOURITE)) {
			probability += 15;
		} else if (training.getProfessionPreferences().get(characterPlayer.getProfession().getName()) != null
				&& training.getProfessionPreferences().get(characterPlayer.getProfession().getName()).equals(TrainingType.FAVOURITE)) {
			probability += 15;
		}

		// Culture has favorite training.
		if (characterPlayer.getCulture().getTrainingPricePercentage(training.getName()) > 0) {
			probability += 10;
		}

		if (probability < 1 && (characterPlayer.getSelectedTrainings().size() < characterPlayer.getLevelUps().size() / 10)) {
			probability = 1;
		}

		if (characterPlayer.getProfession().getTrainingTypes().get(trainingName) != null
				&& characterPlayer.getProfession().getTrainingTypes().get(trainingName).equals(TrainingType.FORBIDDEN)) {
			probability -= 1500;
		} else if (training.getProfessionPreferences().get(characterPlayer.getProfession().getName()) != null
				&& training.getProfessionPreferences().get(characterPlayer.getProfession().getName()).equals(TrainingType.FORBIDDEN)) {
			probability -= 1500;
		}

		// Elementalist must select a training.
		if (characterPlayer.getProfession().isElementalist() && isElementalistTraining(trainingName) && characterPlayer.getSelectedTrainings().isEmpty()) {
			probability = 1000;
		}
		return probability / (characterPlayer.getCurrentLevel().getTrainings().size() + 1);
	}

	public static void setRandomCategoryRanks(CharacterPlayer characterPlayer, String trainingName, int specialization) throws InvalidTrainingException {
		Training training = TrainingFactory.getTraining(trainingName);

		// For each category
		for (TrainingCategory trainingCategory : training.getCategoriesWithRanks()) {
			// Choose one category option.
			List<String> availableCategories = trainingCategory.getCategoryOptions();
			if (specialization >= 0) {
				Collections.sort(availableCategories, new CategoryComparatorBySkillWithRanks(characterPlayer));
			} else {
				Collections.sort(availableCategories, new CategoryComparatorBySkillWithLessRanks(characterPlayer));
			}

			// Select category from list.
			int index = 0;
			String categoryName;
			while (true) {
				categoryName = availableCategories.get(index % availableCategories.size());
				int probability = Math.abs(specialization * 30) + 15;
				if (Math.random() * 100 < probability) {
					characterPlayer.addTrainingSelectedCategory(training, trainingCategory, categoryName);
					break;
				}
				index++;
			}

			// Random skill.
			List<TrainingSkill> skillsOfCategory = characterPlayer.getTrainingOptionsSkills(trainingCategory, categoryName);
			Collections.shuffle(skillsOfCategory);
			List<TrainingSkill> skillsToUpdate;
			if (specialization >= 0) {
				// Min skills to add max ranks in one skill.
				skillsToUpdate = skillsOfCategory.subList(0, Math.min(skillsOfCategory.size(), trainingCategory.getMinSkills()));
			} else {
				// Max skills to share ranks.
				skillsToUpdate = skillsOfCategory.subList(0, Math.min(skillsOfCategory.size(), trainingCategory.getMaxSkills()));
			}
			// Add rank to each skill.
			int ranksAdded = 0;
			while (true) {
				if (skillsToUpdate.isEmpty()) {
					break;
				}
				TrainingSkill trainingSkill = skillsToUpdate.get(ranksAdded % skillsToUpdate.size());
				List<String> skillOptions = trainingSkill.getSkillOptions();
				Collections.shuffle(skillOptions);
				String skillSelected = skillOptions.get(0);
				characterPlayer.addTrainingSkillRanks(training, trainingCategory, skillSelected,
						characterPlayer.getTrainingSkillRanks(training, trainingCategory, skillSelected) + 1);
				ranksAdded++;
				// All ranks added, stop.
				if (ranksAdded >= trainingCategory.getSkillRanks()) {
					break;
				}
			}
		}
	}

	public static void setRandomCharacteristicsUpgrades(CharacterPlayer characterPlayer, String trainingName) throws InvalidTrainingException {
		// Only do it for remaining characteristic updates (if any).
		for (int i = characterPlayer.getTrainingCharacteristicsUpdates(trainingName).size(); i < TrainingFactory.getTraining(trainingName)
				.getUpdateCharacteristics().size(); i++) {
			List<CharacteristicsAbbreviature> availableUpdates = TrainingFactory.getTraining(trainingName).getUpdateCharacteristics().get(i);
			// Order by profession preferences.
			boolean updated = false;
			CharacteristicsAbbreviature lastCharacteristicChecked = null;
			for (CharacteristicsAbbreviature characteristic : characterPlayer.getProfession().getCharacteristicPreferences()) {
				// Available for update.
				if (characteristic != null && availableUpdates.contains(characteristic)) {
					// Good to be increased if: long distance, medium distance per
					// values > 70 or short distance per
					// values > 85
					lastCharacteristicChecked = characteristic;
					if (characterPlayer.getCharacteristicTemporalValue(characteristic) - characterPlayer.getCharacteristicPotentialValue(characteristic) > 20
							|| (characterPlayer.getCharacteristicTemporalValue(characteristic)
									- characterPlayer.getCharacteristicPotentialValue(characteristic) > 10 && characterPlayer
									.getCharacteristicTemporalValue(characteristic) > 70)
							|| (characterPlayer.getCharacteristicTemporalValue(characteristic)
									- characterPlayer.getCharacteristicPotentialValue(characteristic) > 5 && characterPlayer
									.getCharacteristicTemporalValue(characteristic) > 85)) {
						CharacteristicRoll roll = characterPlayer.addNewCharacteristicTrainingUpdate(characteristic, trainingName);
						EsherLog.debug(TrainingProbability.class.getName(), "Characteristic update for training '" + trainingName + "': " + roll);
						updated = true;
						break;
					}
				}
			}
			// Updates are mandatory. Update the last one to avoid decreasing an
			// important one.
			if (!updated && lastCharacteristicChecked != null) {
				CharacteristicRoll roll = characterPlayer.addNewCharacteristicTrainingUpdate(lastCharacteristicChecked, trainingName);
				EsherLog.debug(TrainingProbability.class.getName(), "Characteristic update for training '" + trainingName + "': " + roll);
			}
		}
	}

	public static void setRandomObjects(CharacterPlayer characterPlayer, String trainingName) throws InvalidTrainingException {
		int accepted = 1;
		Training training = TrainingFactory.getTraining(trainingName);
		for (int i = 0; i < training.getObjects().size(); i++) {
			int roll = (int) (Math.random() * 100);
			EsherLog.debug(TrainingProbability.class.getName(), "Objeto '" + training.getObjects().get(i).getName()
					+ (training.getObjects().get(i).getBonus() != 0 ? " (" + training.getObjects().get(i).getBonus() + ")" : "") + " ["
					+ training.getObjects().get(i).getType() + "]' para el adiestramiento '" + trainingName + "'. Probabilidad "
					+ training.getObjects().get(i).getProbability() / (accepted) + "%. Tirada " + roll + ".");
			if (roll < training.getObjects().get(i).getProbability() / (accepted)) {
				characterPlayer.addTrainingEquipment(training, i);
				accepted++;
				EsherLog.info(TrainingProbability.class.getName(), "Nuevo objeto '" + training.getObjects().get(i).getName()
						+ (training.getObjects().get(i).getBonus() != 0 ? " (" + training.getObjects().get(i).getBonus() + ")" : "") + " ["
						+ training.getObjects().get(i).getType() + "]' para el adiestramiento '" + trainingName + "' añadido.");
			}
		}
	}
}
