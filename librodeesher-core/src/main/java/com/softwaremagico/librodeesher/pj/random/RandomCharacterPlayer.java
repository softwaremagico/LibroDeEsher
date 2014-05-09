package com.softwaremagico.librodeesher.pj.random;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.softwaremagico.librodeesher.basics.Spanish;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.SexType;
import com.softwaremagico.librodeesher.pj.categories.Category;
import com.softwaremagico.librodeesher.pj.categories.CategoryFactory;
import com.softwaremagico.librodeesher.pj.characteristic.Characteristic;
import com.softwaremagico.librodeesher.pj.characteristic.Characteristics;
import com.softwaremagico.librodeesher.pj.magic.MagicFactory;
import com.softwaremagico.librodeesher.pj.magic.RealmOfMagic;
import com.softwaremagico.librodeesher.pj.profession.ProfessionalRealmsOfMagicOptions;
import com.softwaremagico.librodeesher.pj.race.RaceFactory;
import com.softwaremagico.librodeesher.pj.skills.Skill;
import com.softwaremagico.librodeesher.pj.skills.SkillFactory;
import com.softwaremagico.librodeesher.pj.weapons.Weapon;
import com.softwaremagico.librodeesher.pj.weapons.WeaponType;
import com.softwaremagico.log.Log;

public class RandomCharacterPlayer {
	public final static int MAX_TRIES = 5;
	private Integer tries = 0;
	private CharacterPlayer characterPlayer;
	private SexType sex;
	private int finalLevel;
	private String race, culture, profession;
	private static Integer characteristicsPoints;
	// Specialization level [-2...2]
	private Integer specializationLevel;
	private Map<String, Integer> suggestedSkillsRanks;

	public RandomCharacterPlayer(SexType sex, String race, String culture, String profession, int finalLevel) {
		this.sex = sex;
		this.finalLevel = finalLevel;
		characterPlayer = new CharacterPlayer();
		this.race = race;
		this.culture = culture;
		this.profession = profession;

		createRandomValues();
	}

	public CharacterPlayer getCharacterPlayer() {
		return characterPlayer;
	}

	private void createRandomValues() {
		setRace();
		setProfession();
		setCharacterInfo();
		setMagicRealm();
		setCharacteristics(characterPlayer, getSpecializationLevel());
		setCulture();
		setDevelopmentPoints();
		setHistoryPoints(characterPlayer, getSpecializationLevel());
		// setPerksPoints();
		setLevels();
	}

	private SexType getSex() {
		if (sex == null) {
			if (Math.random() < 0.5) {
				sex = SexType.MALE;
			} else {
				sex = SexType.FEMALE;
			}
		}
		return sex;
	}

	private void setCharacterInfo() {
		characterPlayer.setSex(getSex());
		characterPlayer.setName(characterPlayer.getRace().getRandonName(characterPlayer.getSex()));
	}

	private void setRace() {
		if (race == null) {
			List<String> races = RaceFactory.getAvailableRaces();
			race = races.get((int) (Math.random() * races.size()));
		}
		characterPlayer.setRace(race);
	}

	private void setProfession() {
		if (profession == null) {
			List<String> professions = characterPlayer.getRace().getAvailableProfessions();
			profession = professions.get((int) (Math.random() * professions.size()));
		}
		characterPlayer.setProfession(profession);
		if (!characterPlayer.getProfessionDecisions().isWeaponCostDecided()) {
			setWeaponCosts(characterPlayer);
		}
	}

	private void setCulture() {
		if (culture == null) {
			List<String> cultures = characterPlayer.getRace().getAvailableCultures();
			culture = cultures.get((int) (Math.random() * cultures.size()));
		}
		characterPlayer.setCulture(culture);
		setRandomCultureHobbyRanks();
		setRandomCultureWeaponRanks();
		setRandomCultureAndRaceLanguages();
		setRandomCultureSpells();
	}

	private static Integer getTotalCharacteristicsPoints() {
		if (characteristicsPoints == null) {
			characteristicsPoints = Characteristics.TOTAL_CHARACTERISTICS_POINTS;
		}
		return characteristicsPoints;
	}

	private Integer getSpecializationLevel() {
		if (specializationLevel == null) {
			specializationLevel = 0;
		}
		return specializationLevel;
	}

	/**
	 * Set random characteristics. Characteristics preferred that generates
	 * development points have a little of advantage.
	 */
	public static void setCharacteristics(CharacterPlayer characterPlayer, int specializationLevel) {
		while (characterPlayer.getCharacteristicsTemporalPointsSpent() < getTotalCharacteristicsPoints()) {
			for (int i = 0; i < characterPlayer.getProfession().getCharacteristicPreferences().size(); i++) {
				Characteristic characteristic = Characteristics
						.getCharacteristicFromAbbreviature(characterPlayer.getProfession()
								.getCharacteristicPreferences().get(i));
				// Max probability 90%. Preferred characteristics with points.
				int availablePoints = getTotalCharacteristicsPoints()
						- characterPlayer.getCharacteristicsTemporalPointsSpent();
				if ((Math.random() * 100 + 1) < Math.min((Math.max(
						(characterPlayer.getCharacteristicInitialTemporalValue(characteristic
								.getAbbreviature()) - Characteristics.INITIAL_CHARACTERISTIC_VALUE)
								* Math.max(1, specializationLevel * 5), 5)), 90)
						// Temporal values has a max limit.
						&& characterPlayer.getCharacteristicInitialTemporalValue(characteristic
								.getAbbreviature()) < (Math.min(90 + specializationLevel * 5, 101))
						// Cost affordable.
						&& Characteristic.getTemporalCost(characterPlayer
								.getCharacteristicInitialTemporalValue(characteristic.getAbbreviature()) + 1)
								- Characteristic.getTemporalCost(characterPlayer
										.getCharacteristicInitialTemporalValue(characteristic
												.getAbbreviature())) <= availablePoints) {
					// Increase more than one point depending on the value of
					// the characteristic.
					int valueToAdd = 1;
					int charCurrentTempValue = characterPlayer.getCharacteristicsInitialTemporalValues().get(
							characteristic.getAbbreviature());
					if (charCurrentTempValue < 70) {
						valueToAdd = Math.min(10 + specializationLevel * 4, availablePoints);
					} else if (charCurrentTempValue < 85) {
						valueToAdd = Math.min(5 + specializationLevel, availablePoints);
					} else {
						valueToAdd = 1;
					}
					characterPlayer.setCharacteristicTemporalValues(
							characteristic.getAbbreviature(),
							characterPlayer.getCharacteristicsInitialTemporalValues().get(
									characteristic.getAbbreviature())
									+ valueToAdd);
					// Add new point to same characteristic.
					if (specializationLevel > 0) {
						i--;
					}
				}
			}
		}

		characterPlayer.setCharacteristicsAsConfirmed();
	}

	/**
	 * Select the magic realm with the higher bonus.
	 */
	private void setMagicRealm() {
		ProfessionalRealmsOfMagicOptions chosenRealm = null;
		int maxCharValue = -100;
		for (int i = 0; i < characterPlayer.getProfession().getMagicRealmsAvailable().size(); i++) {
			ProfessionalRealmsOfMagicOptions professionRealm = characterPlayer.getProfession()
					.getMagicRealmsAvailable().get(i);
			int charValue = 0;
			for (RealmOfMagic realm : professionRealm.getRealmsOfMagic()) {
				charValue += characterPlayer.getCharacteristicTotalBonus(realm.getCharacteristic());
			}
			if (charValue >= maxCharValue) {
				maxCharValue = charValue;
				chosenRealm = characterPlayer.getProfession().getMagicRealmsAvailable().get(i);
			}
		}
		characterPlayer.setRealmOfMagic(chosenRealm);
	}

	public static void setCharacteristicsPoints(Integer totalPoints) {
		characteristicsPoints = totalPoints;
	}

	public void setSpecializationLevel(Integer specializationLevel) {
		this.specializationLevel = specializationLevel;
	}

	public static void setWeaponCosts(CharacterPlayer characterPlayer) {
		List<Category> weaponCategories = shuffleWeapons(characterPlayer);
		for (Category weaponCategory : weaponCategories) {
			characterPlayer.getProfessionDecisions().setWeaponCost(weaponCategory,
					characterPlayer.getFirstWeaponCostNotSelected());
		}
	}

	private static List<Category> shuffleWeapons(CharacterPlayer characterPlayer) {
		List<Category> shuffledWeapons = new ArrayList<>();
		List<Category> handToHand = new ArrayList<>();
		List<Category> distance = new ArrayList<>();
		List<Category> others = new ArrayList<>();
		List<Category> fireArms = new ArrayList<>();

		// Main weapon is the one used by culture or any special bonus.
		for (WeaponType weapon : WeaponType.values()) {
			switch (weapon) {
			case EDGE:
			case BLUNT:
			case HANDLE:
			case TWO_HANDS:
				handToHand.add(CategoryFactory.getCategory(weapon.getWeaponCategoryName()));
				break;
			case PROJECTILE:
			case THROWING:
				distance.add(CategoryFactory.getCategory(weapon.getWeaponCategoryName()));
			case FIREARM:
			case TWO_HANDS_FIREARM:
				fireArms.add(CategoryFactory.getCategory(weapon.getWeaponCategoryName()));
				break;
			default:
				others.add(CategoryFactory.getCategory(weapon.getWeaponCategoryName()));
				break;
			}
		}

		Collections.shuffle(handToHand);
		Collections.shuffle(distance);
		Collections.shuffle(fireArms);

		// One hand to hand weapon, and one distance weapon.
		shuffledWeapons.add(handToHand.get(0));
		handToHand.remove(0);
		if (!characterPlayer.isFirearmsAllowed()) {
			shuffledWeapons.add(distance.get(0));
			distance.remove(0);
		} else {
			shuffledWeapons.add(fireArms.get(0));
			fireArms.remove(0);
		}

		// Adding remaining weapons randomly
		for (Category category : handToHand) {
			others.add(category);
		}
		for (Category category : distance) {
			others.add(category);
		}
		if (characterPlayer.isFirearmsAllowed()) {
			for (Category category : fireArms) {
				others.add(category);
			}
		}
		Collections.shuffle(others);
		shuffledWeapons.addAll(others);

		return shuffledWeapons;
	}

	private int getProbablilityOfSetHobby(Skill skill, int loop) throws NullPointerException {
		if (skill != null) {
			// Max skills achieved or unused category.
			if (characterPlayer.getTotalRanks(skill.getCategory()) == 0
					|| characterPlayer.getSkillsWithRanks(skill.getCategory()).size() > -specializationLevel + 1) {
				return 5 * loop;
			}
			return characterPlayer.getRealRanks(skill)
					* (2 + specializationLevel + loop)
					+ characterPlayer.getTotalRanks(skill.getCategory())
					* 5
					+ Math.min(characterPlayer.getCategoryCost(skill.getCategory(), 0).getRankCost().get(0),
							15) - characterPlayer.getSkillsWithRanks(skill.getCategory()).size() * 5 + 5;
		}
		return 0;
	}

	private void setRandomCultureHobbyRanks() {
		int loop = 0;
		while (characterPlayer.getCulture().getHobbyRanks()
				- characterPlayer.getCultureDecisions().getTotalHobbyRanks() > 0) {
			loop++;
			List<String> hobbies = characterPlayer.getCulture().getHobbySkills();
			Collections.shuffle(hobbies);

			if (hobbies.size() > 0
					&& SkillFactory.getAvailableSkill(hobbies.get(0)) != null
					&& SkillFactory.getAvailableSkill(hobbies.get(0)).isUsedInRandom()
					&& Math.random() * 100 + 1 < getProbablilityOfSetHobby(
							SkillFactory.getAvailableSkill(hobbies.get(0)), loop)) {
				characterPlayer.getCultureDecisions().setHobbyRanks(hobbies.get(0),
						characterPlayer.getCultureDecisions().getHobbyRanks(hobbies.get(0)) + 1);
			}
		}
	}

	private int getLanguageProbability(String language) {
		if ((characterPlayer.getCultureDecisions().getLanguageRanks(language) + 1 > characterPlayer
				.getLanguageMaxInitialRanks(language))
				|| (characterPlayer.getCultureDecisions().getLanguageRanks(language) > 10)) {
			return 0;
		}
		return characterPlayer.getCultureDecisions().getLanguageRanks(language) * specializationLevel + 15;
	}

	private void setRandomCultureAndRaceLanguages() {
		while (characterPlayer.getRace().getLanguagePoints()
				+ characterPlayer.getCulture().getLanguageRanksToChoose()
				- characterPlayer.getCultureDecisions().getTotalLanguageRanks() > 0) {
			String randomLanguage;
			// Use culture or race language
			if (Math.random() < 0.5) {
				randomLanguage = characterPlayer
						.getCulture()
						.getLanguagesMaxRanks()
						.get((int) (Math.random() * characterPlayer.getCulture().getLanguagesMaxRanks()
								.size()));
			} else {
				randomLanguage = characterPlayer
						.getRace()
						.getAvailableLanguages()
						.get((int) (Math.random() * characterPlayer.getRace().getAvailableLanguages().size()));
			}
			if (Math.random() * 100 + 1 < getLanguageProbability(randomLanguage)) {
				characterPlayer.getCultureDecisions().setLanguageRank(randomLanguage,
						characterPlayer.getCultureDecisions().getLanguageRanks(randomLanguage) + 1);
			}
		}
	}

	private void setRandomCultureSpells() {
		while (characterPlayer.getCultureDecisions().getTotalSpellRanks() < characterPlayer.getCulture()
				.getSpellRanks()) {
			List<String> spellLists = MagicFactory.getListOfProfession(characterPlayer.getRealmOfMagic()
					.getRealmsOfMagic(), Spanish.OPEN_LIST_TAG);
			String choseSpell = spellLists.get((int) (Math.random() * spellLists.size()));
			characterPlayer.getCultureDecisions().setSpellRanks(choseSpell,
					characterPlayer.getCultureDecisions().getSpellRanks(choseSpell) + 1);
		}
	}

	private void setRandomCultureWeaponRanks() {
		for (Category category : CategoryFactory.getWeaponsCategories()) {
			// Get weapons of category
			List<Skill> weaponsOfCategory = new ArrayList<>();
			for (Weapon weapon : characterPlayer.getCulture().getCultureWeapons()) {
				try {
					if (weapon.getType().getWeaponCategoryName().equals(category.getName())) {
						Skill weaponSkill = category.getSkill(weapon.getName());
						if (weaponSkill != null && weaponSkill.isUsedInRandom()) {
							weaponsOfCategory.add(weaponSkill);
						}
					}
				} catch (NullPointerException npe) {
					// Unknown weapon: ignore.
				}
			}
			if (weaponsOfCategory.size() > 0) {
				while (characterPlayer.getCultureDecisions().getTotalWeaponRanks(category) < characterPlayer
						.getCulture().getCultureRanks(category)) {
					Skill weaponSkill = weaponsOfCategory
							.get((int) (Math.random() * weaponsOfCategory.size()));
					if (Math.random() * 100 + 1 < Math.max(characterPlayer.getRealRanks(weaponSkill)
							* specializationLevel * 25, 10 - specializationLevel)) {
						characterPlayer.getCultureDecisions()
								.setWeaponRanks(
										weaponSkill.getName(),
										characterPlayer.getCultureDecisions().getWeaponRanks(
												weaponSkill.getName()) + 1);
					}
				}
			}
		}
	}

	/**
	 * Obtains the levesl greater than one.
	 */
	private void setLevels() {
		while (characterPlayer.getLevelUps().size() < finalLevel) {
			Log.info(this.getClass().getName(), " -----------  Subida de Nivel  --------------");
			characterPlayer.increaseLevel();
			setDevelopmentPoints();
		}
	}

	private void setDevelopmentPoints() {
		while (characterPlayer.getRemainingDevelopmentPoints() > 0 && tries <= MAX_TRIES) {
			// if (obtenerAdiestramientos) {
			// ObtenerAdiestramientosSugeridos();
			// ObtenerAdiestramientoAleatorio();
			// }
			setRandomRanks(characterPlayer, specializationLevel, suggestedSkillsRanks, tries);
			tries++;
		}
	}

	public static void setRandomRanks(CharacterPlayer characterPlayer, int specializationLevel,
			Map<String, Integer> suggestedSkillsRanks, Integer tries) {
		List<Category> shuffledCategoryList = CategoryFactory.getCategories();
		Collections.shuffle(shuffledCategoryList);
		// shuffle it!
		for (int i = 0; i < shuffledCategoryList.size(); i++) {
			Category cat = shuffledCategoryList.get(i);
			if (Math.random() * 100 + 1 < new CategoryProbability(characterPlayer, cat, tries,
					suggestedSkillsRanks, specializationLevel).rankProbability()) {
				characterPlayer.getCurrentLevel().setCategoryRanks(cat.getName(),
						characterPlayer.getCurrentLevel().getCategoryRanks(cat.getName()) + 1);
			}
			List<Skill> shuffledSkillList = cat.getSkills();
			Collections.shuffle(shuffledSkillList);
			for (int j = 0; j < shuffledSkillList.size(); j++) {
				Skill skill = shuffledSkillList.get(j);
				int roll = (int) (Math.random() * 100 + 1);
				int probability = new SkillProbability(characterPlayer, skill, tries, suggestedSkillsRanks,
						specializationLevel).getRankProbability();
				Log.info(RandomCharacterPlayer.class.getName(), "Skill '" + skill.getName() + "' ("
						+ probability + "%), roll: " + roll);
				if (roll < probability) {
					characterPlayer.getCurrentLevel().setSkillsRanks(skill,
							characterPlayer.getCurrentLevel().getSkillsRanks(skill.getName()) + 1);
					Log.info(RandomCharacterPlayer.class.getName(), "\tRank added!");
					// Si da opciones de nuevas habilidades, se incluyen ahora.
					// if (skill.habilidadesNuevasPosibles.size() > 0
					// &&
					// cat.NumeroHabilidadesExistes(skill.habilidadesNuevasPosibles)
					// == 0) {
					// cat.AddHabilidad(Habilidad.getSkill(cat,
					// skill.habilidadesNuevasPosibles
					// .get(generator.nextInt(skill.habilidadesNuevasPosibles.size()))));
					// }

					// A skill can be updated more than one rank.
					if (specializationLevel > 0) {
						j--;
					}

					// Add specializations.
					for (int k = 0; k < skill.getSpecialities().size(); k++) {
						// Se le da una posibilidad de añadirse.
						if (Math.random() * 100 < specializationLevel) {
							characterPlayer.addSkillSpecialization(skill, skill.getSpecialities().get(k));
						}
					}
					// Or generalization
					if (!characterPlayer.isRestricted(skill) && !characterPlayer.isSkillSpecialized(skill)) {
						if (Math.random() * 100 < -specializationLevel) {
							characterPlayer.addSkillGeneralized(skill);
						}
					}
				}
			}
		}
	}

	private static List<Category> getProfessionalShuffledCategories() {
		List<Category> categoryList = CategoryFactory.getCategories();
		Collections.shuffle(categoryList);
		return categoryList;
	}

	/**
	 * Sets randomly the history points.
	 */
	public static void setHistoryPoints(CharacterPlayer characterPlayer, int specializationLevel) {
		int loops = 0;
		while (characterPlayer.getRemainingHistorialPoints() > 0) {
			List<Category> shuffledCategoryList = getProfessionalShuffledCategories();
			for (int i = 0; i < shuffledCategoryList.size(); i++) {
				Category category = shuffledCategoryList.get(i);
				//Avoid some categories.
				if(category.getName().toLowerCase().contains(Spanish.COMUNICATION_CATEGORY)){
					continue;
				}
				if (!characterPlayer.isHistoryPointSelected(category)
						&& characterPlayer.getRemainingHistorialPoints() > 0
						&& characterPlayer.getTotalRanks(category) > 0
						&& characterPlayer.isCategoryUseful(category)
						// If only one skill, is better to use the point into
						// the skill.
						&& category.getSkills().size() > 1
						&& Math.random() * 80 + 20 < characterPlayer.getTotalValue(category)
								/ 3
								+ (8 - characterPlayer.getNewRankCost(category, 0, 1) * specializationLevel + 3)
								+ loops
								// More skills, better
								+ characterPlayer.getSkillsWithRanks(category).size()
								* 3
								+ ProfessionRandomness.preferredCategoryByProfession(characterPlayer,
										category, specializationLevel) / 2) {
					characterPlayer.setHistoryPoints(category, true);
				} else {
					List<Skill> shuffledSkillList = category.getSkills();
					Collections.shuffle(shuffledSkillList);
					for (int j = 0; j < shuffledSkillList.size(); j++) {
						Skill skill = shuffledSkillList.get(j);
						if (!characterPlayer.isHistoryPointSelected(skill)
								&& characterPlayer.getRemainingHistorialPoints() > 0
								&& characterPlayer.isSkillInteresting(skill)
								&& Math.random() * 80 + 20 < characterPlayer.getTotalValue(skill)
										/ 3
										- (8 - characterPlayer.getNewRankCost(category, 0, 1)
												* specializationLevel + 3)
										+ loops
										+ ProfessionRandomness.preferredSkillByProfession(characterPlayer,
												skill, specializationLevel) / 2) {
							characterPlayer.setHistoryPoints(skill, true);
						}
					}
				}
			}
			loops += 10;
		}
	}
}
