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

public class RandomCharacterPlayer {
	private final static int MAX_TRIES = 5;
	private int tries = 0;
	private CharacterPlayer characterPlayer;
	private SexType sex;
	private int finalLevel;
	private String race, culture, profession;
	private Integer characteristicsPoints;
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
		setCharacteristics();
		setCulture();
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
		setWeaponCosts();
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

	private Integer getTotalCharacteristicsPoints() {
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
	 * Set characteristics
	 */
	private void setCharacteristics() {
		List<Characteristic> characteristics = characterPlayer.getCharacteristics();

		while (characterPlayer.getCharacteristicsTemporalPointsSpent() < getTotalCharacteristicsPoints()) {
			for (int i = 0; i < characteristics.size(); i++) {
				Characteristic characteristic = characteristics.get(i);
				if ((Math.random() * 100 + 1) < (characterPlayer
						.getCharacteristicInitialTemporalValue(characteristic.getAbbreviature()) - getSpecializationLevel() * 10)
						&& characterPlayer.getCharacteristicInitialTemporalValue(characteristic
								.getAbbreviature()) < (Math.min(90 + getSpecializationLevel() * 4, 101))
						&& characterPlayer.getCharacteristicsTemporalPointsSpent(characteristic
								.getAbbreviature()) <= getTotalCharacteristicsPoints()) {
					characterPlayer.setCharacteristicTemporalValues(
							characteristic.getAbbreviature(),
							characterPlayer.getCharacteristicsInitialTemporalValues().get(
									characteristic.getAbbreviature()) + 1);
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

	public void setCharacteristicsPoints(Integer characteristicsPoints) {
		this.characteristicsPoints = characteristicsPoints;
	}

	public void setSpecializationLevel(Integer specializationLevel) {
		this.specializationLevel = specializationLevel;
	}

	private void setWeaponCosts() {
		List<Category> weaponCategories = shuffleWeapons();
		for (Category weaponCategory : weaponCategories) {
			characterPlayer.getProfessionDecisions().setWeaponCost(weaponCategory,
					characterPlayer.getFirstWeaponCostNotSelected());
		}
	}

	private List<Category> shuffleWeapons() {
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

	private int probablilityOfSetHobby(Skill skill, int loop) throws NullPointerException {
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
					&& Math.random() * 100 + 1 < probablilityOfSetHobby(
							SkillFactory.getAvailableSkill(hobbies.get(0)), loop)) {
				characterPlayer.getCultureDecisions().setHobbyRanks(hobbies.get(0),
						characterPlayer.getCultureDecisions().getHobbyRanks(hobbies.get(0)) + 1);
			}
		}
	}

	private int LanguageProbability(String language) {
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
			if (Math.random() * 100 + 1 < LanguageProbability(randomLanguage)) {
				characterPlayer.getCultureDecisions().setLanguageRank(randomLanguage,
						characterPlayer.getCultureDecisions().getLanguageRanks(randomLanguage) + 1);
			}
		}
	}

	private void setRandomCultureSpells() {
		while (characterPlayer.getCultureDecisions().getTotalSpellRanks() < characterPlayer.getCulture()
				.getSpellRanks()) {
			List<String> spellLists = MagicFactory.getListOfProfession(characterPlayer
					.getProfessionalRealmsOfMagicChoosen().getRealmsOfMagic(), Spanish.OPEN_LIST_TAG);
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

	private void setLevels() {
		while (characterPlayer.getLevelUps().size() < finalLevel) {
			setDevelopmentPoints();
			// ObtenerRangosSugeridos();
			// GastarPuntosDesarrolloDeFormaAleatoria(true);
			// Log.info(" -----------  Subida de Nivel  --------------");
			// Personaje.getInstance().SubirUnNivel();
			// adiestramientos_escogidos_en_nivel = 0;
		}
	}

	private void setDevelopmentPoints() {
		List<Category> shuffledCategoryList = CategoryFactory.getCategories();
		Collections.shuffle(shuffledCategoryList);
		while (characterPlayer.getRemainingDevelopmentPoints() > 0 && tries <= MAX_TRIES) {
			// if (obtenerAdiestramientos) {
			// ObtenerAdiestramientosSugeridos();
			// ObtenerAdiestramientoAleatorio();
			// }
			ObtenerRangosAleatorios(shuffledCategoryList);
		}
	}

	private void ObtenerRangosAleatorios(List<Category> shuffledCategoryList) {
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
				if (Math.random() * 100 + 1 < new SkillProbability(characterPlayer, skill, tries,
						suggestedSkillsRanks, specializationLevel).getRankProbability()) {
					characterPlayer.getCurrentLevel().setSkillsRanks(skill,
							characterPlayer.getCurrentLevel().getSkillsRanks(skill.getName()) + 1);
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
		tries++;
	}
}
