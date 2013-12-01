package com.softwaremagico.librodeesher.pj;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.softwaremagico.librodeesher.pj.categories.Category;
import com.softwaremagico.librodeesher.pj.categories.CategoryFactory;
import com.softwaremagico.librodeesher.pj.characteristic.Characteristic;
import com.softwaremagico.librodeesher.pj.characteristic.Characteristics;
import com.softwaremagico.librodeesher.pj.magic.RealmOfMagic;
import com.softwaremagico.librodeesher.pj.profession.ProfessionalRealmsOfMagicOptions;
import com.softwaremagico.librodeesher.pj.race.RaceFactory;
import com.softwaremagico.librodeesher.pj.skills.Skill;
import com.softwaremagico.librodeesher.pj.skills.SkillFactory;
import com.softwaremagico.librodeesher.pj.weapons.WeaponType;

public class RandomCharacterPlayer {
	private CharacterPlayer characterPlayer;
	private SexType sex;
	private int finalLevel;
	private String race, culture, profession;
	private Integer characteristicsPoints;
	private Integer specializationLevel;

	public RandomCharacterPlayer(SexType sex, String race, String culture, String profession, int finalLevel) {
		this.sex = sex;
		this.finalLevel = finalLevel;
		characterPlayer = new CharacterPlayer();
		this.race = race;
		this.culture = culture;
		this.profession = profession;

		createRandomValues();
	}

	private void createRandomValues() {
		setRace();
		setCharacterInfo();
		setProfession();
		setCharacteristics();
		setMagicRealm();
		setCulture();
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
		setCharacteristics();
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
		setRandomHobbyRanks();
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
		ProfessionalRealmsOfMagicOptions choseRealm = null;
		int maxCharValue = 0;
		for (int i = 0; i < characterPlayer.getProfession().getMagicRealmsAvailable().size(); i++) {
			ProfessionalRealmsOfMagicOptions professionRealm = characterPlayer.getProfession()
					.getMagicRealmsAvailable().get(i);
			int charValue = 0;
			for (RealmOfMagic realm : professionRealm.getRealmsOfMagic()) {
				charValue += characterPlayer.getCharacteristicTotalBonus(realm.getCharacteristic());
			}
			if (charValue >= maxCharValue) {
				maxCharValue = charValue;
				choseRealm = characterPlayer.getProfession().getMagicRealmsAvailable().get(i);
			}
		}
		characterPlayer.setRealmOfMagic(choseRealm);
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

	private void setRandomHobbyRanks() {
		int loop = 0;
		while (characterPlayer.getCulture().getHobbyRanks() > 0) {
			loop++;
			List<String> hobbies = characterPlayer.getCulture().getHobbySkills();
			Collections.shuffle(hobbies);

			if (SkillFactory.getAvailableSkill(hobbies.get(0)).isUsedInRandom()
					&& Math.random() * 100 + 1 < probablilityOfSetHobby(
							SkillFactory.getAvailableSkill(hobbies.get(0)), loop)) {
				characterPlayer.getCultureDecisions().setHobbyRanks(hobbies.get(0),
						characterPlayer.getCultureDecisions().getHobbyRanks(hobbies.get(0)) + 1);
			}
		}
	}

	public CharacterPlayer getCharacterPlayer() {
		return characterPlayer;
	}
}
