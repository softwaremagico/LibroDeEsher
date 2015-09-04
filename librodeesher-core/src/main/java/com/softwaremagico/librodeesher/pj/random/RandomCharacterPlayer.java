package com.softwaremagico.librodeesher.pj.random;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.softwaremagico.librodeesher.basics.Spanish;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.SexType;
import com.softwaremagico.librodeesher.pj.categories.Category;
import com.softwaremagico.librodeesher.pj.categories.CategoryComparatorByCost;
import com.softwaremagico.librodeesher.pj.categories.CategoryFactory;
import com.softwaremagico.librodeesher.pj.categories.CategoryGroup;
import com.softwaremagico.librodeesher.pj.characteristic.Characteristic;
import com.softwaremagico.librodeesher.pj.characteristic.Characteristics;
import com.softwaremagico.librodeesher.pj.characteristic.CharacteristicsAbbreviature;
import com.softwaremagico.librodeesher.pj.equipment.MagicObject;
import com.softwaremagico.librodeesher.pj.magic.MagicDefinitionException;
import com.softwaremagico.librodeesher.pj.magic.MagicFactory;
import com.softwaremagico.librodeesher.pj.magic.RealmOfMagic;
import com.softwaremagico.librodeesher.pj.profession.InvalidProfessionException;
import com.softwaremagico.librodeesher.pj.profession.ProfessionalRealmsOfMagicOptions;
import com.softwaremagico.librodeesher.pj.race.RaceFactory;
import com.softwaremagico.librodeesher.pj.skills.Skill;
import com.softwaremagico.librodeesher.pj.skills.SkillComparatorByRanks;
import com.softwaremagico.librodeesher.pj.skills.SkillFactory;
import com.softwaremagico.librodeesher.pj.skills.SkillForEnablingMustBeSelected;
import com.softwaremagico.librodeesher.pj.training.TrainingDecision;
import com.softwaremagico.librodeesher.pj.weapons.Weapon;
import com.softwaremagico.librodeesher.pj.weapons.WeaponType;
import com.softwaremagico.log.EsherLog;

/**
 * Specialization level: if 0, categories and skills are obtained randomly. If
 * specialized are obtained by ranks number if not at inverse. If value is 3
 * then character can specialize skills, if -3 can generalize skills.
 */
public class RandomCharacterPlayer {
	public final static int MAX_TRIES = 5;
	private static final int MAX_HOBBY_COST = 40;
	private static final int MAX_RANDOM_COST = 20;
	private Integer tries = 0;
	private CharacterPlayer characterPlayer;
	private SexType sex;
	private int finalLevel;
	private String race, culture, profession;
	private static Integer characteristicsPoints;
	// Specialization level [-3...3]
	private Integer specializationLevel;
	private Map<String, Integer> suggestedSkillsRanks;
	private Map<String, Integer> suggestedCategoriesRanks;
	private List<String> suggestedTrainings;
	private List<RandomFeedbackListener> feedbackListeners;

	/**
	 * Creates an entirely new character.
	 * 
	 * @param sex
	 * @param race
	 * @param culture
	 * @param profession
	 * @param finalLevel
	 * @throws MagicDefinitionException
	 * @throws InvalidProfessionException
	 */
	public RandomCharacterPlayer(SexType sex, String race, String culture, String profession, int finalLevel)
			throws MagicDefinitionException, InvalidProfessionException {
		EsherLog.info(this.getClass().getName(), "--------------------");
		EsherLog.info(this.getClass().getName(), "New Random Character");
		EsherLog.info(this.getClass().getName(), "--------------------");
		this.sex = sex;
		suggestedSkillsRanks = new HashMap<>();
		suggestedCategoriesRanks = new HashMap<>();
		this.finalLevel = finalLevel;
		characterPlayer = new CharacterPlayer();
		this.race = race;
		this.culture = culture;
		this.profession = profession;
		feedbackListeners = new ArrayList<>();
		suggestedTrainings = new ArrayList<>();
	}

	/**
	 * Uses input character and increases it to the defined level.
	 * 
	 * @param characterPlayer
	 * @param finalLevel
	 * @throws MagicDefinitionException
	 * @throws InvalidProfessionException
	 */
	public RandomCharacterPlayer(CharacterPlayer characterPlayer, int finalLevel)
			throws MagicDefinitionException, InvalidProfessionException {
		this.sex = characterPlayer.getSex();
		suggestedSkillsRanks = new HashMap<>();
		suggestedCategoriesRanks = new HashMap<>();
		this.finalLevel = finalLevel;
		this.characterPlayer = characterPlayer;
		this.race = characterPlayer.getRace().getName();
		this.culture = characterPlayer.getCulture().getName();
		this.profession = characterPlayer.getProfession().getName();
		feedbackListeners = new ArrayList<>();
	}

	/**
	 * Only spends current available development points.
	 * 
	 * @param characterPlayer
	 * @throws MagicDefinitionException
	 * @throws InvalidProfessionException
	 */
	public RandomCharacterPlayer(CharacterPlayer characterPlayer) throws MagicDefinitionException,
			InvalidProfessionException {
		this.sex = characterPlayer.getSex();
		suggestedSkillsRanks = new HashMap<>();
		suggestedCategoriesRanks = new HashMap<>();
		this.finalLevel = characterPlayer.getLevelUps().size();
		this.characterPlayer = characterPlayer;
		this.race = characterPlayer.getRace().getName();
		this.culture = characterPlayer.getCulture().getName();
		this.profession = characterPlayer.getProfession().getName();
		feedbackListeners = new ArrayList<>();
	}

	public void addFeedbackListener(RandomFeedbackListener listener) {
		feedbackListeners.add(listener);
	}

	public void removeFeedbackListener(RandomFeedbackListener listener) {
		feedbackListeners.remove(listener);
	}

	private void sendFeedBack(String message) {
		for (RandomFeedbackListener listener : feedbackListeners) {
			listener.feedBackMessage(message);
		}
	}

	public CharacterPlayer getCharacterPlayer() {
		return characterPlayer;
	}

	public void setSuggestedCategoryRanks(String categoryName, int ranks) {
		if (ranks <= 0) {
			suggestedCategoriesRanks.remove(categoryName);
		} else {
			suggestedCategoriesRanks.put(categoryName, ranks);
		}
	}

	public void setSuggestedSkillRanks(String skillName, int ranks) {
		if (ranks <= 0) {
			suggestedSkillsRanks.remove(skillName);
		} else {
			suggestedSkillsRanks.put(skillName, ranks);
		}
	}

	public void createRandomValues() throws MagicDefinitionException, InvalidProfessionException {
		sendFeedBack("Creando la raza.");
		setRace();
		sendFeedBack("Seleccionando la profesión.");
		setProfession();
		sendFeedBack("Añadiendo la información del personaje.");
		setCharacterInfo();
		sendFeedBack("Eligiendo el reino de magia.");
		setMagicRealm();
		sendFeedBack("Seleccionando las características.");
		setCharacteristics(characterPlayer, getSpecializationLevel());
		sendFeedBack("Seleccionando la cultura.");
		setCulture(characterPlayer, culture, getSpecializationLevel());
		setDevelopmentPoints();
		sendFeedBack("Gastando los puntos de historial.");
		setHistoryPoints(characterPlayer, getSpecializationLevel());
		// setPerksPoints();
		setLevels();
		sendFeedBack("Personaje aleatorio completado!");
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
		EsherLog.info(this.getClass().getName(), "Name: " + characterPlayer.getName());
	}

	private void setRace() {
		if (race == null) {
			List<String> races = RaceFactory.getAvailableRaces();
			race = races.get((int) (Math.random() * races.size()));
		}
		EsherLog.info(this.getClass().getName(), "Race: " + race);
		characterPlayer.setRace(race);
	}

	private void setProfession() {
		if (profession == null) {
			List<String> professions = characterPlayer.getRace().getAvailableProfessions();
			profession = professions.get((int) (Math.random() * professions.size()));
		}
		EsherLog.info(this.getClass().getName(), "Profession: " + profession);
		characterPlayer.setProfession(profession);
		if (!characterPlayer.getProfessionDecisions().isWeaponCostDecided()) {
			setWeaponCosts(characterPlayer);
		}
	}

	public static void setCulture(CharacterPlayer characterPlayer, String culture, int specializationLevel)
			throws MagicDefinitionException, InvalidProfessionException {
		if (culture == null) {
			List<String> cultures = characterPlayer.getRace().getAvailableCultures();
			culture = cultures.get((int) (Math.random() * cultures.size()));
		}
		EsherLog.info(RandomCharacterPlayer.class.getName(), "Culture: " + culture);
		characterPlayer.setCulture(culture);
		setRandomCultureHobbyRanks(characterPlayer, specializationLevel);
		setRandomCultureWeaponRanks(characterPlayer, specializationLevel);
		setRandomCultureAndRaceLanguages(characterPlayer, specializationLevel);
		setRandomCultureSpells(characterPlayer);
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
		int loop = 0;
		// Use preferred characteristics for probability
		List<CharacteristicsAbbreviature> preferences = characterPlayer.getProfession()
				.getCharacteristicPreferences();
		Collections.reverse(preferences);
		while (characterPlayer.getCharacteristicsTemporalPointsSpent() < getTotalCharacteristicsPoints()
				&& loop < 20) {
			for (int i = 0; i < characterPlayer.getProfession().getCharacteristicPreferences().size(); i++) {
				Characteristic characteristic = Characteristics
						.getCharacteristicFromAbbreviature(characterPlayer.getProfession()
								.getCharacteristicPreferences().get(i));
				// Max probability 90%. Preferred characteristics with points.
				int availablePoints = getTotalCharacteristicsPoints()
						- characterPlayer.getCharacteristicsTemporalPointsSpent();
				if (((Math.random() * 100 + 1) < (preferences.indexOf(characteristic.getAbbreviature()) * 4 + loop))
						// Temporal values has a max limit.
						&& characterPlayer.getCharacteristicInitialTemporalValue(characteristic
								.getAbbreviature()) < (Math.min(
								Math.max(90, 90 + (specializationLevel - 1) * 6), 101))
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
					if (charCurrentTempValue < 50) {
						valueToAdd = Math.min(20 + (specializationLevel + 3) * 5, availablePoints);
					} else if (charCurrentTempValue < 70) {
						valueToAdd = Math.min(7 + (specializationLevel + 3) * 4, availablePoints);
					} else if (charCurrentTempValue < 83) {
						valueToAdd = Math.min(2 + (specializationLevel + 3), availablePoints);
					} else if (charCurrentTempValue < 90 - specializationLevel) {
						valueToAdd = Math.min(1, specializationLevel);
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
			loop++;
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
		EsherLog.info(this.getClass().getName(), "Realm: " + chosenRealm);
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

	private static int getProbablilityOfSetHobby(CharacterPlayer characterPlayer, Skill skill, int loop,
			int specializationLevel) throws NullPointerException {
		if (skill != null) {
			if (skill.isRare()) {
				return 0 + loop;
			}
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
							15) - characterPlayer.getSkillsWithRanks(skill.getCategory()).size() * 5 + 5
					+ loop;
		}
		return 0;
	}

	private static void setRandomCultureHobbyRanks(CharacterPlayer characterPlayer, int specializationLevel) {
		int loop = 0;
		while (characterPlayer.getCulture().getHobbyRanks() - characterPlayer.getCultureTotalHobbyRanks() > 0) {
			loop++;
			List<String> hobbyNames = characterPlayer.getRealHobbySkills();
			List<Skill> hobbies = new ArrayList<>();
			for (String hobbyName : hobbyNames) {
				hobbies.add(SkillFactory.getAvailableSkill(hobbyName));
			}

			// Order by specialization.
			sortSkillsBySpecialization(characterPlayer, hobbies, specializationLevel);

			Skill skill = hobbies.get(0);
			if (skill != null && hobbies.size() > 0) {
				int weaponsRanks = 0;
				// Cost greater than 40 can not be a hobby
				if (characterPlayer.getCultureStimatedCategoryCost(skill.getCategory()) <= MAX_HOBBY_COST) {
					// Only if a new rank can be added to the hobby.
					if (characterPlayer.getCultureHobbyRanks(skill.getName()) < characterPlayer
							.getMaxRanksPerCulture(skill.getCategory())) {
						if (skill != null
								&& Math.random()
										* 100
										// Penalization for too many weapons
										+ (skill.getCategory().getCategoryGroup()
												.equals(CategoryGroup.WEAPON) ? weaponsRanks * 5 : 0) < getProbablilityOfSetHobby(
											characterPlayer, skill, loop, specializationLevel)) {
							characterPlayer.setCultureHobbyRanks(skill.getName(),
									characterPlayer.getCultureHobbyRanks(skill.getName()) + 1);
							// New weapon rank added. Increase counter.
							weaponsRanks += skill.getCategory().getCategoryGroup()
									.equals(CategoryGroup.WEAPON) ? 1 : 0;
						}
					}
				}
			} else {
				break;
			}
		}
	}

	private static int getLanguageProbability(CharacterPlayer characterPlayer, String language,
			int specializationLevel) {
		if ((characterPlayer.getCultureLanguageRanks(language) + 1 > characterPlayer
				.getLanguageMaxInitialRanks(language))
				|| (characterPlayer.getCultureLanguageRanks(language) > 10)) {
			return 0;
		}
		return characterPlayer.getCultureLanguageRanks(language) * specializationLevel + 15;
	}

	private static void setRandomCultureAndRaceLanguages(CharacterPlayer characterPlayer,
			int specializationLevel) {
		while (characterPlayer.getRace().getLanguagePoints()
				+ characterPlayer.getCulture().getLanguageRanksToChoose()
				- characterPlayer.getCultureTotalLanguageRanks() > 0) {
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
			// Enough points
			if (characterPlayer.getCultureTotalLanguageRanks() < characterPlayer.getRace()
					.getLanguagePoints() + characterPlayer.getCulture().getLanguageRanksToChoose()) {
				// Max limit of language.
				if (characterPlayer.getLanguageInitialRanks(randomLanguage)
						+ characterPlayer.getCultureLanguageRanks(randomLanguage) < characterPlayer
							.getLanguageMaxInitialRanks(randomLanguage)) {
					if (Math.random() * 100 + 1 < getLanguageProbability(characterPlayer, randomLanguage,
							specializationLevel)) {
						characterPlayer.setCultureLanguageRanks(randomLanguage,
								characterPlayer.getCultureLanguageRanks(randomLanguage) + 1);
					}
				}
			}
		}
	}

	private static void setRandomCultureSpells(CharacterPlayer characterPlayer)
			throws MagicDefinitionException, InvalidProfessionException {
		while (characterPlayer.getCultureTotalSpellRanks() < characterPlayer.getCulture().getSpellRanks()) {
			List<String> spellLists = new ArrayList<>(MagicFactory.getListOfProfession(characterPlayer
					.getRealmOfMagic().getRealmsOfMagic(), Spanish.OPEN_LIST_TAG));
			String choseSpell = spellLists.get((int) (Math.random() * spellLists.size()));
			characterPlayer.setCultureSpellRanks(choseSpell,
					characterPlayer.getCultureSpellRanks(choseSpell) + 1);
		}
	}

	private static void setRandomCultureWeaponRanks(CharacterPlayer characterPlayer, int specializationLevel) {
		for (Category category : CategoryFactory.getWeaponsCategories()) {
			// Get weapons of category
			List<Skill> weaponsOfCategory = new ArrayList<>();
			for (Weapon weapon : characterPlayer.getCulture().getCultureWeapons()) {
				try {
					if (weapon.getType().getWeaponCategoryName().equals(category.getName())) {
						Skill weaponSkill = category.getSkill(weapon.getName());
						if (weaponSkill != null && !weaponSkill.isRare()) {
							weaponsOfCategory.add(weaponSkill);
						}
					}
				} catch (NullPointerException npe) {
					// Unknown weapon: ignore.
				}
			}
			if (weaponsOfCategory.size() > 0) {
				while (characterPlayer.getCultureTotalWeaponsRanks(category) < characterPlayer.getCulture()
						.getCultureRanks(category)) {
					Skill weaponSkill = weaponsOfCategory
							.get((int) (Math.random() * weaponsOfCategory.size()));
					if (Math.random() * 100 + 1 < Math.max(characterPlayer.getRealRanks(weaponSkill)
							* specializationLevel * 25, 10 - specializationLevel)) {
						characterPlayer.setCultureWeaponsRanks(weaponSkill.getName(),
								characterPlayer.getCultureWeaponsRanks(weaponSkill.getName()) + 1);
					}
				}
			}
		}
	}

	/**
	 * Obtains the levels greater than one.
	 */
	private void setLevels() {
		while (characterPlayer.getLevelUps().size() < finalLevel) {
			characterPlayer.increaseLevel();
			setDevelopmentPoints();
		}
	}

	public void setDevelopmentPoints() {
		// Store probability to increase speed.
		Map<Skill, Integer> skillProbabilityStored = new HashMap<>();
		Map<Category, Integer> categoryProbabilityStored = new HashMap<>();
		tries = 0;
		while (characterPlayer.getRemainingDevelopmentPoints() > 0 && tries <= MAX_TRIES) {
			sendFeedBack("(Nº" + characterPlayer.getLevelUps().size() + ") Gastando puntos de desarrollo.");
			getRandomTrainings(characterPlayer, getSpecializationLevel(), suggestedTrainings, finalLevel);
			setRandomRanks(characterPlayer, specializationLevel, suggestedSkillsRanks, tries, finalLevel,
					categoryProbabilityStored, skillProbabilityStored);
			tries++;
		}
		EsherLog.debug(RandomCharacterPlayer.class.getName(),
				"Final remaining DP: " + characterPlayer.getRemainingDevelopmentPoints());
	}

	public static void setRandomRanks(CharacterPlayer characterPlayer, int specializationLevel,
			Map<String, Integer> suggestedSkillsRanks, Integer tries, int finalLevel,
			Map<Category, Integer> categoryProbabilityStored, Map<Skill, Integer> skillProbabilityStored) {
		List<Category> sortedCategoriesByCost = CategoryFactory.getCategories();
		int developmentPoints = characterPlayer.getRemainingDevelopmentPoints();
		// shuffle it!
		Collections.sort(sortedCategoriesByCost, new CategoryComparatorByCost(characterPlayer));
		for (int i = 0; i < sortedCategoriesByCost.size(); i++) {
			if (developmentPoints < 1) {
				break;
			}
			Category category = characterPlayer.getCategory(sortedCategoriesByCost.get(i));

			if (characterPlayer.getNewRankCost(category) > developmentPoints
					|| characterPlayer.getNewRankCost(category) > MAX_RANDOM_COST) {
				continue;
			}

			// Check if already stored probability. If not, calculate it.
			if (categoryProbabilityStored.get(category) == null) {
				categoryProbabilityStored.put(category, new CategoryProbability(characterPlayer, category,
						suggestedSkillsRanks, specializationLevel, finalLevel).rankProbability());
			}
			int roll = (int) (Math.random() * 100 + 1);
			if (categoryProbabilityStored.get(category) > 0
					&& roll < categoryProbabilityStored.get(category) + tries * 3) {
				EsherLog.info(RandomCharacterPlayer.class.getName(), "Category '" + category.getName()
						+ "' (" + (categoryProbabilityStored.get(category) + tries * 3) + "%), roll: " + roll
						+ " Added!");
				characterPlayer.setCurrentLevelRanks(category, new Integer(characterPlayer.getCurrentLevel()
						.getCategoryRanks(category.getName()) + 1));
				developmentPoints = characterPlayer.getRemainingDevelopmentPoints();
				// Probability change. Remove stored one.
				categoryProbabilityStored.remove(category);
				for (Skill skill : category.getSkills()) {
					skillProbabilityStored.remove(skill);
				}
			}
			List<Skill> shuffledCategorySkills = new ArrayList<>(category.getSkills());

			// Order by specialization.
			sortSkillsBySpecialization(characterPlayer, shuffledCategorySkills, specializationLevel);

			for (int j = 0; j < shuffledCategorySkills.size(); j++) {
				if (developmentPoints < 1) {
					break;
				}
				Skill skill = shuffledCategorySkills.get(j);
				EsherLog.debug(RandomCharacterPlayer.class.getName(), characterPlayer.getNewRankCost(skill)
						+ ">" + developmentPoints);
				// Cost of next rank is possible.
				if (characterPlayer.getRankCost(skill, characterPlayer.getRealRanks(skill),
						characterPlayer.getCurrentLevelRanks(skill)) > developmentPoints) {
					break;
				}

				roll = (int) (Math.random() * 100 + 1);
				if (skillProbabilityStored.get(skill) == null) {
					skillProbabilityStored.put(skill, new SkillProbability(characterPlayer, skill,
							suggestedSkillsRanks, specializationLevel, finalLevel).getRankProbability());
				}
				EsherLog.debug(RandomCharacterPlayer.class.getName(), "Skill '" + skill.getName() + "' ("
						+ (skillProbabilityStored.get(skill) + tries * 3) + "%), roll: " + roll);

				if (skillProbabilityStored.get(skill) > 0
						&& roll < skillProbabilityStored.get(skill) + tries * 3 || tries == MAX_TRIES) {
					try {
						characterPlayer.setCurrentLevelRanks(skill, characterPlayer.getCurrentLevel()
								.getSkillsRanks(skill.getName()) + 1);
					} catch (SkillForEnablingMustBeSelected e) {
						// Skills than depends in other skills.
						characterPlayer.enableSkillOption(skill, selectOneSkillForEnabling(skill));
					}
					EsherLog.info(RandomCharacterPlayer.class.getName(), "Skill '" + skill.getName() + "' ("
							+ (skillProbabilityStored.get(skill) + tries * 3) + "%), roll: " + roll
							+ " Added!");
					// All skill probability of this category changes, remove
					// it.
					for (Skill skillToRemove : category.getSkills()) {
						skillProbabilityStored.remove(skillToRemove);
					}
					developmentPoints = characterPlayer.getRemainingDevelopmentPoints();
					EsherLog.debug(RandomCharacterPlayer.class.getName(), "Remaining DP: "
							+ developmentPoints);

					// A skill can be updated more than one rank.
					if (specializationLevel > 0) {
						j--;
					}

					// Add specializations if rank has been set and second rank
					// is available
					// Only specialization value of 3 can generate this options.
					if (specializationLevel > 2) {
						if (!characterPlayer.isRestricted(skill) && !characterPlayer.isGeneralized(skill)) {
							for (int k = 0; k < skill.getSpecialities().size(); k++) {
								// Only specialization value of 3 can
								// generate
								// specialized skills with 3% of
								// probability.
								if (Math.random() * 100 < (specializationLevel - 2) * 3) {
									if (characterPlayer.getNewRankCost(skill) < developmentPoints) {
										characterPlayer.addSkillSpecialization(skill, skill.getSpecialities()
												.get(k));
										EsherLog.info(RandomCharacterPlayer.class.getName(), "Skill '"
												+ skill.getName() + "' specialized to '"
												+ skill.getSpecialities().get(k) + "'");
										developmentPoints = characterPlayer.getRemainingDevelopmentPoints();

										break;
									}
								}
							}
						}
					} else if (specializationLevel < -2) {
						// Or generalization
						if (!characterPlayer.isRestricted(skill)
								&& !characterPlayer.isSkillSpecialized(skill)) {
							// Only generalized value of -3 can generate
							// generalized skills with 5% of
							// probability.
							if (Math.random() * 100 < (-specializationLevel - 2) * 5) {
								EsherLog.info(RandomCharacterPlayer.class.getName(),
										"Skill '" + skill.getName() + "' generalized!");
								characterPlayer.addGeneralized(skill);
							}
						}
					}
				}
			}
		}
	}

	private static Skill selectOneSkillForEnabling(Skill skill) {
		EsherLog.info(RandomCharacterPlayer.class.getName(), "Selecting skill for enabling due to use of '"
				+ skill.getName() + "'");
		List<String> options = skill.getEnableSkills();
		Collections.shuffle(options);
		EsherLog.info(RandomCharacterPlayer.class.getName(), "Enabling '" + options.get(0) + "'");
		return SkillFactory.getSkill(options.get(0));
	}

	private static List<Category> getProfessionalShuffledCategories(CharacterPlayer characterPlayer) {
		List<Category> categoryList = CategoryFactory.getCategories();
		Collections.sort(categoryList, new CategoryComparatorByCost(characterPlayer));
		return categoryList;
	}

	/**
	 * Sets randomly the history points.
	 */
	public static void setHistoryPoints(CharacterPlayer characterPlayer, int specializationLevel) {
		int loops = 0;
		while (characterPlayer.getRemainingHistorialPoints() > 0) {
			List<Category> shuffledCategoryList = getProfessionalShuffledCategories(characterPlayer);
			for (int i = 0; i < shuffledCategoryList.size(); i++) {
				Category category = characterPlayer.getCategory(shuffledCategoryList.get(i));
				// Avoid some categories.
				if (category.getName().toLowerCase().contains(Spanish.COMUNICATION_CATEGORY)) {
					continue;
				}
				if (!characterPlayer.isHistoryPointSelected(category)
						&& characterPlayer.getRemainingHistorialPoints() > 0
						&& characterPlayer.getTotalRanks(category) > 0
						&& characterPlayer.isCategoryUseful(category)
						// If only one skill, is better to use the point into
						// the skill.
						&& category.getSkills().size() > 1
						&& Math.random() * 100 < (characterPlayer.getTotalValue(category) - 15 + loops) * 3) {
					characterPlayer.setHistoryPoints(category, true);
				}
				if (!category.getName().equals(Spanish.COMUNICATION_CATEGORY)
						&& !category.getName().startsWith(Spanish.REGIONAL_KNOWNLEDGE_TAG)) {
					List<Skill> shuffledSkillList = new ArrayList<>(category.getSkills());
					sortSkillsBySpecialization(characterPlayer, shuffledSkillList, specializationLevel);
					for (int j = 0; j < shuffledSkillList.size(); j++) {
						Skill skill = shuffledSkillList.get(j);
						if (!characterPlayer.isHistoryPointSelected(skill)
								&& characterPlayer.getRemainingHistorialPoints() > 0
								&& characterPlayer.isSkillInteresting(skill)
								&& Math.random() * 100 < (characterPlayer.getTotalValue(skill) - 25 + loops) * 3) {
							characterPlayer.setHistoryPoints(skill, true);
						}
					}
				}
			}
			loops++;
		}
	}

	private static void getRandomTrainings(CharacterPlayer characterPlayer, int specializationLevel,
			List<String> suggestedTrainings, int finalLevel) {
		List<String> trainings = TrainingProbability.shuffleTrainings(characterPlayer, suggestedTrainings);
		for (int i = 0; i < trainings.size(); i++) {
			String training = trainings.get(i);
			int probability = TrainingProbability.trainingRandomness(characterPlayer, training,
					specializationLevel, suggestedTrainings, finalLevel);
			if (Math.random() * 100 < probability) {
				setRandomTraining(characterPlayer, training, specializationLevel);
			}
		}
	}

	public static void setRandomTraining(CharacterPlayer characterPlayer, String trainingName,
			int specializationLevel) {
		if (characterPlayer.addTraining(trainingName)) {
			// Set random skill ranks
			TrainingProbability.setRandomCategoryRanks(characterPlayer, trainingName, specializationLevel);

			// Set characteristics upgrades.
			TrainingProbability.setRandomCharacteristicsUpgrades(characterPlayer, trainingName);

			// Set random Objects.
			TrainingProbability.setRandomObjects(characterPlayer, trainingName);
			List<MagicObject> trainingObjects = TrainingDecision.convertTrainingEquipmentToMagicObject(
					characterPlayer, trainingName);
			for (MagicObject trainingObject : trainingObjects) {
				characterPlayer.addMagicItem(trainingObject, trainingName);
			}
		}
	}

	/**
	 * Sorts a list of skills.
	 * 
	 * @param skills
	 * @param specializationLevel
	 */
	private static void sortSkillsBySpecialization(CharacterPlayer characterPlayer, List<Skill> skills,
			int specializationLevel) {
		if (specializationLevel == -1) {
			Collections.shuffle(skills);
		} else {
			Collections.sort(skills, new SkillComparatorByRanks(characterPlayer));
			if (specializationLevel < 0) {
				Collections.reverse(skills);
			}
		}
	}

	public List<String> getSuggestedTrainings() {
		return suggestedTrainings;
	}

	public void setSuggestedTrainings(List<String> suggestedTrainings) {
		this.suggestedTrainings = suggestedTrainings;
	}

}
