package com.softwaremagico.librodeesher.pj;

import com.softwaremagico.librodeesher.basics.Spanish;
import com.softwaremagico.librodeesher.pj.categories.Category;
import com.softwaremagico.librodeesher.pj.categories.CategoryFactory;
import com.softwaremagico.librodeesher.pj.characteristic.CharacteristicsAbbreviature;
import com.softwaremagico.librodeesher.pj.exceptions.CategoryNotEqualsException;
import com.softwaremagico.librodeesher.pj.exceptions.CharacteristicNotEqualsException;
import com.softwaremagico.librodeesher.pj.exceptions.SkillNotEqualsException;
import com.softwaremagico.librodeesher.pj.skills.Skill;

public class CharacterComparator {

	public static void compare(CharacterPlayer character1, CharacterPlayer character2)
			throws CharacteristicNotEqualsException, CategoryNotEqualsException, SkillNotEqualsException {
		compareCharacteristics(character1, character2);
		compareCategories(character1, character2);
		compareSkills(character1, character2);
	}

	private static void compareCharacteristics(CharacterPlayer character1, CharacterPlayer character2)
			throws CharacteristicNotEqualsException {
		for (CharacteristicsAbbreviature abbreviature : CharacteristicsAbbreviature.values()) {
			if (character1.getCharacteristicTemporalValue(abbreviature) != null) {
				if (!character1.getCharacteristicTemporalValue(abbreviature).equals(
						character2.getCharacteristicTemporalValue(abbreviature))) {
					throw new CharacteristicNotEqualsException("Characteristic '" + abbreviature
							+ "' has different temporal value '"
							+ character1.getCharacteristicTemporalValue(abbreviature) + "' compared to '"
							+ character2.getCharacteristicTemporalValue(abbreviature) + "'.");
				}

				if (!character1.getCharacteristicPotentialValue(abbreviature).equals(
						character2.getCharacteristicPotentialValue(abbreviature))) {
					throw new CharacteristicNotEqualsException("Characteristic '" + abbreviature
							+ "' has different potential value '"
							+ character1.getCharacteristicPotentialValue(abbreviature) + "' compared to '"
							+ character2.getCharacteristicPotentialValue(abbreviature) + "'.");
				}

				if (!character1.getCharacteristicTotalBonus(abbreviature).equals(
						character2.getCharacteristicTotalBonus(abbreviature))) {
					throw new CharacteristicNotEqualsException("Characteristic '" + abbreviature
							+ "' has different bonus value '"
							+ character1.getCharacteristicTotalBonus(abbreviature) + "' compared to '"
							+ character2.getCharacteristicTotalBonus(abbreviature) + "'.");
				}
			}
		}
	}

	private static void compareCategories(CharacterPlayer character1, CharacterPlayer character2)
			throws CategoryNotEqualsException {
		for (Category category : CategoryFactory.getCategories()) {
			if (!character1.getTotalValue(category).equals(character2.getTotalValue(category))) {
				throw new CategoryNotEqualsException("Category '" + category.getName()
						+ "' comparation error.\n\tranks '" + character1.getPreviousRanks(category)
						+ "', culture ranks '" + character1.getCulture().getCultureRanks(category)
						+ "', culture adolescence ranks '"
						+ character1.getCultureAdolescenceRanks(category.getName()) + "', culture hobby ranks '"
						+ character1.getCultureHobbyRanks(category.getName()) + "', previous level ranks '"
						+ character1.getPreviousLevelsRanks(category) + "', training ranks '"
						+ character1.getTrainingRanks(category) + "', inserted ranks '"
						+ character1.getInsertedRanks(category) + "', new ranks '"
						+ character1.getCurrentLevelRanks(category) + "', characteristics '"
						+ character1.getCharacteristicsBonus(category) + "', bonus '"
						+ character1.getBonus(category) + "', total '" + character1.getTotalValue(category)
						+ "'.\n\tranks '" + character2.getPreviousRanks(category) + "', culture ranks '"
						+ character2.getCulture().getCultureRanks(category) + "', culture adolescence ranks '"
						+ character2.getCultureAdolescenceRanks(category.getName()) + "', culture hobby ranks '"
						+ character2.getCultureHobbyRanks(category.getName()) + "', previous level ranks '"
						+ character2.getPreviousLevelsRanks(category) + "', training ranks '"
						+ character2.getTrainingRanks(category) + "', inserted ranks '"
						+ character2.getInsertedRanks(category) + "', new ranks '"
						+ character2.getCurrentLevelRanks(category) + "', characteristics '"
						+ character2.getCharacteristicsBonus(category) + "', bonus '"
						+ character2.getBonus(category) + "', total '" + character2.getTotalValue(category)
						+ "'.");
			}
		}
	}

	private static void compareSkills(CharacterPlayer character1, CharacterPlayer character2)
			throws SkillNotEqualsException {
		for (Category category : CategoryFactory.getCategories()) {
			if (character1.getCategory(category).getSkills().size() != character2.getCategory(category)
					.getSkills().size()) {
				throw new SkillNotEqualsException("Skills number is different for category '"
						+ category.getName() + "':\n\t" + character1.getCategory(category).getSkills()
						+ "\n\t" + character2.getCategory(category).getSkills());
			}
			for (Skill skill : character1.getCategory(category).getSkills()) {
				if (!character1.getTotalValue(skill).equals(character2.getTotalValue(skill))) {
					throw new SkillNotEqualsException(
							"Skill '"
									+ skill.getName()
									+ "', comparation error.\n\tranks '"
									+ character1.getPreviousRanks(skill)
									+ "', culture ranks '"
									+ character1.getCulture().getCultureRanks(skill)
									+ "', culture adolescence ranks '"
									+ character1.getCultureAdolescenceRanks(skill.getName())
									+ "', culture hobby ranks '"
									+ character1.getCultureHobbyRanks(skill.getName())
									+ "', previous level ranks '"
									+ character1.getPreviousLevelsRanks(skill)
									+ "', training ranks '"
									+ character1.getTrainingRanks(skill)
									+ "', inserted ranks '"
									+ character1.getInsertedRanks(skill)
									+ "', new ranks '"
									+ character1.getCurrentLevelRanks(skill)
									+ (category.getName().toLowerCase().equals(Spanish.COMUNICATION_CATEGORY.toLowerCase()) ? "language ranks '"
											+ character1.getCultureLanguageRanks(skill.getName()) + "'"
											: "")
									+ ", specialization ranks '"
									+ character1.getSkillSpecializations(skill)
									+ ", category value '"
									+ character1.getTotalValue(category)
									+ "', profession bonus '"
									+ character1.getProfession().getSkillBonus(skill.getName())
									+ "', background bonus'"
									+ character1.getBackground().getBonus(skill)
									+ "', perk bonus '"
									+ character1.getPerkBonus(skill)
									+ "', conditional perk bonus '"
									+ character1.getConditionalPerkBonus(skill)
									+ "', item bonus '"
									+ character1.getItemBonus(skill)
									+ "', bonus '"
									+ character1.getBonus(skill)
									+ "', total '"
									+ character1.getTotalValue(skill)
									+ "'.\n\tranks '"
									+ character2.getPreviousRanks(skill)
									+ "', culture ranks '"
									+ character2.getCulture().getCultureRanks(skill)
									+ "', culture weapons ranks '"
									+ character2.getCultureAdolescenceRanks(skill.getName())
									+ "', culture hobby ranks '"
									+ character2.getCultureHobbyRanks(skill.getName())
									+ "', previous level ranks '"
									+ character2.getPreviousLevelsRanks(skill)
									+ "', training ranks '"
									+ character2.getTrainingRanks(skill)
									+ "', inserted ranks '"
									+ character2.getInsertedRanks(skill)
									+ "', new ranks '"
									+ character2.getCurrentLevelRanks(skill)
									+ (category.getName().toLowerCase().equals(Spanish.COMUNICATION_CATEGORY) ? "language ranks '"
											+ character2.getCultureLanguageRanks(skill.getName()) + "'"
											: "") + ", specialization ranks '"
									+ character2.getSkillSpecializations(skill) + ", category value '"
									+ character2.getTotalValue(category) + "', profession bonus '"
									+ character2.getProfession().getSkillBonus(skill.getName())
									+ "', background bonus'" + character2.getBackground().getBonus(skill)
									+ "', perk bonus '" + character2.getPerkBonus(skill)
									+ "', conditional perk bonus '"
									+ character2.getConditionalPerkBonus(skill) + "', item bonus '"
									+ character2.getItemBonus(skill) + "', bonus '"
									+ character2.getBonus(skill) + "', total '"
									+ character2.getTotalValue(skill) + "'.");
				}

			}
		}

	}
}
