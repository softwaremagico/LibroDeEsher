package com.softwaremagico.librodeesher.pj;

/*
 * #%L
 * Libro de Esher
 * %%
 * Copyright (C) 2007 - 2012 Softwaremagico
 * %%
 * This software is designed by Jorge Hortelano Otero. Jorge Hortelano Otero
 * <softwaremagico@gmail.com> C/Quart 89, 3. Valencia CP:46008 (Spain).
 *  
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *  
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *  
 * You should have received a copy of the GNU General Public License along with
 * this program; If not, see <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.softwaremagico.librodeesher.basics.Roll;
import com.softwaremagico.librodeesher.basics.RollGroup;
import com.softwaremagico.librodeesher.basics.Spanish;
import com.softwaremagico.librodeesher.config.Config;
import com.softwaremagico.librodeesher.pj.categories.Category;
import com.softwaremagico.librodeesher.pj.categories.CategoryCost;
import com.softwaremagico.librodeesher.pj.categories.CategoryFactory;
import com.softwaremagico.librodeesher.pj.categories.CategoryGroup;
import com.softwaremagico.librodeesher.pj.characteristic.Appearance;
import com.softwaremagico.librodeesher.pj.characteristic.Characteristic;
import com.softwaremagico.librodeesher.pj.characteristic.CharacteristicRoll;
import com.softwaremagico.librodeesher.pj.characteristic.Characteristics;
import com.softwaremagico.librodeesher.pj.culture.Culture;
import com.softwaremagico.librodeesher.pj.culture.CultureDecisions;
import com.softwaremagico.librodeesher.pj.culture.CultureFactory;
import com.softwaremagico.librodeesher.pj.historial.Historial;
import com.softwaremagico.librodeesher.pj.level.LevelUp;
import com.softwaremagico.librodeesher.pj.magic.MagicListType;
import com.softwaremagico.librodeesher.pj.magic.MagicSpellLists;
import com.softwaremagico.librodeesher.pj.magic.RealmOfMagic;
import com.softwaremagico.librodeesher.pj.perk.Perk;
import com.softwaremagico.librodeesher.pj.perk.PerkDecision;
import com.softwaremagico.librodeesher.pj.profession.Profession;
import com.softwaremagico.librodeesher.pj.profession.ProfessionDecisions;
import com.softwaremagico.librodeesher.pj.profession.ProfessionFactory;
import com.softwaremagico.librodeesher.pj.profession.ProfessionalRealmsOfMagicOptions;
import com.softwaremagico.librodeesher.pj.race.Race;
import com.softwaremagico.librodeesher.pj.race.RaceFactory;
import com.softwaremagico.librodeesher.pj.resistance.ResistanceType;
import com.softwaremagico.librodeesher.pj.skills.Skill;
import com.softwaremagico.librodeesher.pj.skills.SkillFactory;
import com.softwaremagico.librodeesher.pj.skills.SkillGroup;
import com.softwaremagico.librodeesher.pj.skills.SkillType;
import com.softwaremagico.librodeesher.pj.training.Training;
import com.softwaremagico.librodeesher.pj.training.TrainingCategory;
import com.softwaremagico.librodeesher.pj.training.TrainingDecision;
import com.softwaremagico.librodeesher.pj.training.TrainingFactory;

@Entity
@Table(name = "T_CHARACTERPLAYER")
public class CharacterPlayer {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name = "ID", unique = true, nullable = false)
	private Long characterPlayerId; // database id.

	private String name;
	private SexType sex;
	private String historyText;

	@ElementCollection
	@CollectionTable(name = "T_CHARACTERPLAYER_INITIAL_TEMPORAL_VALUES")
	private Map<String, Integer> characteristicsInitialTemporalValues;

	@ElementCollection
	@CollectionTable(name = "T_CHARACTERPLAYER_POTENTIAL_VALUES")
	private Map<String, Integer> characteristicsPotentialValues;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@CollectionTable(name = "T_CHARACTERPLAYER_TEMPORAL_CHARACTERISTICS_ROLLS")
	@JoinColumn(name = "rollGroupId")
	@MapKey(name = "characteristicAbbreviature")
	private Map<String, RollGroup> characteristicsTemporalUpdatesRolls;
	private boolean characteristicsConfirmed = false;

	private String raceName;
	@Transient
	private transient Race race;

	private String cultureName;
	@Transient
	private transient Culture culture;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "cultureDecisionsId")
	private CultureDecisions cultureDecisions;

	private String professionName;
	@Transient
	private transient Profession profession;
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "professionDecisionsId")
	private ProfessionDecisions professionDecisions;

	@ElementCollection
	@CollectionTable(name = "T_CHARACTERPLAYER_TRAININGS")
	private List<String> trainingsNames;
	@Transient
	private transient List<Training> trainings;
	@ElementCollection
	@CollectionTable(name = "T_CHARACTERPLAYER_TRAINING_DECISIONS")
	private Map<String, TrainingDecision> trainingDecisions;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "professionalRealmId")
	private ProfessionalRealmsOfMagicOptions realmOfMagic;

	@Transient
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "magicSpellListId")
	private MagicSpellLists magicSpellLists;

	@Transient
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "historialId")
	private Historial historial;

	@ElementCollection
	@CollectionTable(name = "T_CHARACTERPLAYER_PERKS")
	private List<Perk> perks;

	@ElementCollection
	@CollectionTable(name = "T_CHARACTERPLAYER_PERKS_DECISIONS")
	private Map<String, PerkDecision> perkDecisions;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Appearance appearance;

	private boolean darkSpellsAsBasicListsAllowed = false;
	private boolean firearmsAllowed = false;
	private boolean chiPowersAllowed = false;
	private boolean otherRealmtrainingSpellsAllowed = false;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@CollectionTable(name = "T_CHARACTERPLAYER_LEVEL_UP")
	@JoinColumn(name = "id")
	private List<LevelUp> levelUps;

	public CharacterPlayer() {
		appearance = new Appearance();
		levelUps = new ArrayList<>();
		historial = new Historial();
		characteristicsInitialTemporalValues = new HashMap<>();
		characteristicsTemporalUpdatesRolls = new HashMap<>();
		characteristicsPotentialValues = new HashMap<>();
		trainingDecisions = new HashMap<>();
		perkDecisions = new HashMap<>();
		setTemporalValuesOfCharacteristics();
		sex = SexType.MALE;
		cultureDecisions = new CultureDecisions();
		professionDecisions = new ProfessionDecisions();
		trainingsNames = new ArrayList<>();
		trainings = new ArrayList<>();
		perks = new ArrayList<>();
		setDefaultConfig();
		// Starts in level 1.
		increaseLevel();
	}

	public Integer getAppearance() {
		return appearance.getTotal(getCharacteristicPotentialValue("Pr")) + getRace().getApperanceBonus()
				+ getPerkApperanceBonus();
	}

	/**
	 * Default config is stored in a file and changed with the options windows. When a new character is created, it uses
	 * this default config options.
	 */
	private void setDefaultConfig() {
		darkSpellsAsBasicListsAllowed = Config.getDarkSpellsAsBasic();
		firearmsAllowed = Config.getFireArmsActivated();
		chiPowersAllowed = Config.getChiPowersAllowed();
		otherRealmtrainingSpellsAllowed = Config.getOtherRealmtrainingSpells();
	}

	/**
	 * Is a non magic profession.
	 */
	public boolean isFighter() {
		if (getRealmOfMagic().getRealmsOfMagic().size() < 3) {
			return false;
		}
		return true;
	}

	/**
	 * Can cast spells.
	 * 
	 * @return
	 */
	public boolean isWizard() {
		return isPureWizard() || isHybridWizard() || isSemiWizard();
	}

	/**
	 * Is a pure wizard.
	 * 
	 * @return
	 */
	public boolean isPureWizard() {
		return (getNewRankCost(CategoryFactory.getCategory(Spanish.BASIC_LIST_TAG), 0, 0) == 3);
	}

	/**
	 * Wizards of two realms.
	 * 
	 * @return
	 */
	public boolean isHybridWizard() {
		return (getNewRankCost(CategoryFactory.getCategory(Spanish.BASIC_LIST_TAG), 0, 0) == 3 && getRealmOfMagic()
				.getRealmsOfMagic().size() == 2);
	}

	/**
	 * Is semi wizard.
	 * 
	 * @return
	 */
	public boolean isSemiWizard() {
		return (getNewRankCost(CategoryFactory.getCategory(Spanish.BASIC_LIST_TAG), 0, 0) == 6);
	}

	public CultureDecisions getCultureDecisions() {
		return cultureDecisions;
	}

	public ProfessionDecisions getProfessionDecisions() {
		return professionDecisions;
	}

	public boolean areCharacteristicsConfirmed() {
		return characteristicsConfirmed;
	}

	public Integer getCharacteristicPotentialValue(String abbreviature) {
		Integer potential = characteristicsPotentialValues.get(abbreviature);
		if (potential != null) {
			return potential;
		}
		return 0;
	}

	public Integer getCharacteristicRaceBonus(String abbreviature) {
		return getRace().getCharacteristicBonus(abbreviature);
	}

	public List<Characteristic> getCharacteristics() {
		return Characteristics.getCharacteristics();
	}

	public Integer getCharacteristicSpecialBonus(String abbreviature) {
		return getPerkCharacteristicBonus(abbreviature);
	}

	public Integer getCharacteristicTemporalBonus(String abbreviature) {
		return Characteristics.getTemporalBonus(getCharacteristicTemporalValue(abbreviature));
	}

	private Integer getTemporalModifications(String abbreviature) {
		Integer temporalValue = characteristicsInitialTemporalValues.get(abbreviature);
		for (TrainingDecision training : getTrainingDecisions().values()) {
			for (CharacteristicRoll roll : training.getCharacteristicsUpdates(abbreviature)) {
				temporalValue += Characteristic.getCharacteristicUpgrade(roll.getCharacteristicTemporalValue(),
						roll.getCharacteristicPotentialValue(), roll.getRoll());
			}
		}
		for (CharacteristicRoll roll : historial.getCharacteristicsUpdates(abbreviature)) {
			temporalValue += Characteristic.getCharacteristicUpgrade(roll.getCharacteristicTemporalValue(),
					roll.getCharacteristicPotentialValue(), roll.getRoll());
		}
		return temporalValue;
	}

	public Integer getCharacteristicTemporalValue(String abbreviature) {
		return getTemporalModifications(abbreviature);
	}

	/**
	 * Temporal value of the characteristic when starting the creation of the character.
	 * 
	 * @param abbreviature
	 * @return
	 */
	public Integer getCharacteristicInitialTemporalValue(String abbreviature) {
		Integer value = getTemporalModifications(abbreviature);
		if (value != null) {
			if (isMainProfessionalCharacteristic(abbreviature)) {
				if (value > 90) {
					return value;
				} else {
					setCharacteristicTemporalValues(abbreviature, 90);
					return 90;
				}
			}
			return value;
		}
		return 0;
	}

	public Integer getBonusCharacteristicOfRealmOfMagic() {
		Integer total = 0;
		for (RealmOfMagic realm : realmOfMagic.getRealmsOfMagic()) {
			total += getCharacteristicTotalBonus(realm.getCharacteristic());
		}
		return total / realmOfMagic.getRealmsOfMagic().size();
	}

	public Integer getCharacteristicTotalBonus(String abbreviature) {
		if (abbreviature.toLowerCase().contains(Spanish.NOTHING_TAG)) {
			return 0;
		}
		if (abbreviature.toLowerCase().contains("*")) {
			return getBonusCharacteristicOfRealmOfMagic();
		}
		return getCharacteristicTemporalBonus(abbreviature) + getCharacteristicRaceBonus(abbreviature)
				+ getCharacteristicSpecialBonus(abbreviature);
	}

	public Integer getCurrentLevelNumber() {
		return levelUps.size();
	}

	public Culture getCulture() {
		if (cultureName == null)
			return null;
		if (culture == null || !cultureName.equals(culture.getName())) {
			culture = CultureFactory.getCulture(cultureName);
		}
		return culture;
	}

	public Integer getTotalDevelopmentPoints() {
		Integer total = 0;
		total += getCharacteristicTemporalValue("Ag");
		total += getCharacteristicTemporalValue("Co");
		total += getCharacteristicTemporalValue("Me");
		total += getCharacteristicTemporalValue("Ra");
		total += getCharacteristicTemporalValue("Ad");
		return total / 5;
	}

	public String getName() {
		if (name != null && name.length() > 0) {
			return name;
		}
		return Spanish.DEFAULT_NAME;
	}

	public Profession getProfession() {
		if (professionName == null)
			return null;
		if (profession == null || !professionName.equals(profession.getName())) {
			profession = ProfessionFactory.getProfession(professionName);
		}
		return profession;
	}

	public Race getRace() {
		if (raceName == null)
			return null;
		if (race == null || !raceName.equals(race.getName())) {
			race = RaceFactory.getRace(raceName);
		}
		return race;
	}

	public Integer getResistanceBonus(ResistanceType type) {
		switch (type) {
		case CANALIZATION:
			return getCharacteristicTotalBonus("In") * 3;
		case ESSENCE:
			return getCharacteristicTotalBonus("Em") * 3;
		case MENTALISM:
			return getCharacteristicTotalBonus("In") * 3;
		case PSIONIC:
			return 0;
		case POISON:
			return getCharacteristicTotalBonus("Co") * 3;
		case DISEASE:
			return getCharacteristicTotalBonus("Co") * 3;
		case COLD:
			return 0;
		case HOT:
			return 0;
		case FEAR:
			return getCharacteristicTotalBonus("Ad") * 3;
		default:
			return 0;
		}
	}

	public SexType getSex() {
		return sex;
	}

	private Integer getSpentDevelopmentPointsInCategoryRanks(Integer level) {
		Integer total = 0;
		List<String> categoriesWithRanks = levelUps.get(level).getCategoriesWithRanks();
		for (String categoryName : categoriesWithRanks) {
			Category category = CategoryFactory.getCategory(categoryName);
			Integer ranksUpdatedInLevel = levelUps.get(level).getCategoryRanks(categoryName);
			for (int i = 0; i < ranksUpdatedInLevel; i++) {
				total += getNewRankCost(category, getPreviousRanks(category) + i, i);
			}
		}
		return total;
	}

	/**
	 * Returns the skills with one or more ranks.
	 * 
	 * @param category
	 * @return
	 */
	public List<Skill> getSkillsWithRanks(Category category) {
		List<Skill> result = new ArrayList<>();
		for (Skill skill : category.getSkills()) {
			if (getTotalRanks(skill) > 0) {
				result.add(skill);
			}
		}
		return result;
	}

	public List<Skill> getSkillsWithNewRanks(Category category) {
		List<Skill> result = new ArrayList<>();
		for (Skill skill : category.getSkills()) {
			if (getCurrentLevelRanks(skill) > 0) {
				result.add(skill);
			}
		}
		return result;
	}

	private Integer getSpentDevelopmentPointsInSkillsRanks(Integer level) {
		Integer total = 0;
		List<String> skillsWithRanks = levelUps.get(level).getSkillsWithRanks();
		for (String skillName : skillsWithRanks) {
			Skill skill = SkillFactory.getAvailableSkill(skillName);
			Integer ranksUpdatedInLevel = levelUps.get(level).getSkillsRanks(skillName);
			for (int i = 0; i < ranksUpdatedInLevel; i++) {
				total += getNewRankCost(skill, getPreviousRanks(skill) + i, i);
			}
		}
		return total;
	}

	private Integer getSpentDevelopmentPointsInTrainings(Integer level) {
		Integer total = 0;
		LevelUp levelUp = levelUps.get(level);
		for (String trainingName : levelUp.getTrainings()) {
			total += getProfession().getTrainingCost(trainingName);
		}
		return total;
	}

	private Integer getSpentDevelopmentPoints() {
		if (levelUps.size() > 0) {
			return getSpentDevelopmentPointsInCategoryRanks(levelUps.size() - 1)
					+ getSpentDevelopmentPointsInSkillsRanks(levelUps.size() - 1)
					+ getSpentDevelopmentPointsInTrainings(levelUps.size() - 1);
		}
		return 0;
	}

	public Integer getRemainingDevelopmentPoints() {
		return getTotalDevelopmentPoints() - getSpentDevelopmentPoints();
	}

	public Integer getCharacteristicsTemporalPointsSpent(String abbreviature) {
		return Characteristic.getTemporalCost(getCharacteristicInitialTemporalValue(abbreviature));
	}

	public Integer getCharacteristicsTemporalPointsSpent() {
		Integer total = 0;
		for (Characteristic characteristic : Characteristics.getCharacteristics()) {
			total += getCharacteristicsTemporalPointsSpent(characteristic.getAbbreviature());
		}
		return total;
	}

	public boolean isMainProfessionalCharacteristic(Characteristic characteristic) {
		return getProfession().isCharacteristicProfessional(characteristic);
	}

	public boolean isMainProfessionalCharacteristic(String abbreviature) {
		return getProfession().isCharacteristicProfessional(
				Characteristics.getCharacteristicFromAbbreviature(abbreviature));
	}

	public void setCharacteristicsAsConfirmed() {
		setPotentialValues();
		characteristicsConfirmed = true;
		magicSpellLists.orderSpellListsByCategory(this);
	}

	private void setCharacteristicsTemporalUpdatesRolls() {
		for (Characteristic characteristic : Characteristics.getCharacteristics()) {
			if (characteristicsTemporalUpdatesRolls.get(characteristic.getAbbreviature()) == null) {
				characteristicsTemporalUpdatesRolls.put(characteristic.getAbbreviature(),
						new RollGroup(characteristic.getAbbreviature()));
			}
		}
	}

	private Roll getStoredCharacteristicRoll(String abbreviature) {
		Roll roll = characteristicsTemporalUpdatesRolls.get(abbreviature).getFirst();
		setCharacteristicsTemporalUpdatesRolls();
		return roll;
	}

	public void setCharacteristicTemporalValues(String abbreviature, Integer value) {
		characteristicsInitialTemporalValues.put(abbreviature, value);
	}

	public void setCulture(String cultureName) {
		this.cultureName = cultureName;
	}

	public void setName(String name) {
		if (!name.equals(Spanish.DEFAULT_NAME)) {
			this.name = name;
		}
	}

	private void setPotentialValues() {
		for (Characteristic characteristic : Characteristics.getCharacteristics()) {
			Integer potential = Characteristics.getPotencial(characteristicsInitialTemporalValues.get(characteristic
					.getAbbreviature()));
			characteristicsPotentialValues.put(characteristic.getAbbreviature(), potential);
		}
	}

	public void setProfession(String professionName) {
		if (this.professionName == null || !this.professionName.equals(professionName)) {
			this.professionName = professionName;
			setTemporalValuesOfCharacteristics();
			magicSpellLists = new MagicSpellLists();
		}
	}

	public void setRace(String raceName) {
		this.raceName = raceName;
	}

	public void setRealmOfMagic(ProfessionalRealmsOfMagicOptions realmOfMagic) {
		this.realmOfMagic = realmOfMagic;
	}

	public void setSex(SexType sex) {
		this.sex = sex;
	}

	private void setTemporalValuesOfCharacteristics() {
		for (Characteristic characteristic : Characteristics.getCharacteristics()) {
			characteristicsInitialTemporalValues.put(characteristic.getAbbreviature(),
					Characteristics.INITIAL_CHARACTERISTIC_VALUE);
		}
		setCharacteristicsTemporalUpdatesRolls();
	}

	public Integer getLanguageInitialRanks(String language) {
		return Math.max(getCulture().getLanguageMaxRanks(language), getRace().getLanguageInitialRanks(language));
	}

	public Integer getLanguageMaxInitialRanks(String language) {
		return Math.max(getCulture().getLanguageMaxRanks(language), getRace().getLanguageMaxRanks(language));
	}

	public Integer getLanguageRanks(String language) {
		return Math.max(getLanguageInitialRanks(language), cultureDecisions.getLanguageRanks(language));
	}

	public Integer getCurrentLevelRanks(Category category) {
		if (levelUps.size() > 0) {
			return getCurrentLevel().getCategoryRanks(category.getName());
		} else {
			return 0;
		}
	}

	public Integer getCurrentLevelRanks(Skill skill) {
		if (levelUps.size() > 0) {
			return getCurrentLevel().getSkillsRanks(skill.getName());
		} else {
			return 0;
		}
	}

	public LevelUp getCurrentLevel() {
		return levelUps.get(levelUps.size() - 1);
	}

	public void setCurrentLevelRanks(Skill skill, Integer ranks) {
		if (levelUps.size() > 0) {
			getCurrentLevel().setSkillsRanks(skill, ranks);
		}
	}

	public void setCurrentLevelRanks(Category category, Integer ranks) {
		if (levelUps.size() > 0) {
			getCurrentLevel().setCategoryRanks(category.getName(), ranks);
		}
	}

	private Integer getPreviousLevelsRanks(Category category) {
		Integer total = 0;
		for (int i = 0; i < levelUps.size() - 1; i++) {
			total += levelUps.get(i).getCategoryRanks(category.getName());
		}
		return total;
	}

	private Integer getPreviousLevelsRanks(Skill skill) {
		Integer total = 0;
		for (int i = 0; i < levelUps.size() - 1; i++) {
			total += levelUps.get(i).getSkillsRanks(skill.getName());
		}
		return total;
	}

	public Integer getPreviousRanks(Category category) {
		Integer total = 0;
		total += getCulture().getCultureRanks(category);
		total += getPreviousLevelsRanks(category);
		total += getPerksRanks(category);
		return total;
	}

	private Integer getPerksRanks(Category category) {
		Integer total = 0;
		for (Perk perk : perks) {
			total += perk.getRanks(category);
		}
		return total;
	}

	public Integer getPreviousRanks(Skill skill) {
		Integer total = 0;
		total += getCulture().getCultureRanks(skill);
		total += getCultureDecisions().getWeaponRanks(skill.getName());
		total += getCultureDecisions().getHobbyRanks(skill.getName());
		total += getPerksRanks(skill);
		total += getPreviousLevelsRanks(skill);
		if (skill.getCategory().getName().toLowerCase().equals(Spanish.COMUNICATION_CATEGORY)) {
			total += getLanguageRanks(skill.getName());
		}
		return total;
	}

	private Integer getPerksRanks(Skill skill) {
		Integer total = 0;
		for (Perk perk : perks) {
			total += perk.getRanks(skill);
		}
		return total;
	}

	public Integer getTotalRanks(Category category) {
		return getPreviousRanks(category) + getCurrentLevelRanks(category);
	}

	public Integer getTotalRanks(Skill skill) {
		return getPreviousRanks(skill) + getCurrentLevelRanks(skill);

	}

	public Integer getRealRanks(Skill skill) {
		Float modifier = (float) 1;
		if (isRestricted(skill)) {
			modifier = (float) 0.5;
		} else if (isSkillGeneralized(skill)) {
			if (isCommon(skill) || isProfessional(skill)) {
				modifier = (float) 1;
			} else {
				modifier = (float) 0.5;
			}
		} else if (isProfessional(skill)) {
			modifier = (float) 3;
		} else if (isCommon(skill)) {
			modifier = (float) 2;
		}
		return (int) (getTotalRanks(skill) * modifier);
	}

	public Integer getRanksValue(Category category) {
		return category.getRankValue(getTotalRanks(category));
	}

	public Integer getRanksValue(Skill skill) {
		return skill.getRankValue(this, getRealRanks(skill));
	}

	public Integer getCharacteristicsBonus(Category category) {
		Integer total = 0;
		List<String> characteristicsAbbreviature = category.getCharacteristics();
		for (String characteristic : characteristicsAbbreviature) {
			total += getCharacteristicTotalBonus(characteristic);
		}
		return total;
	}

	public Integer getBonus(Category category) {
		return category.getBonus() + getProfession().getCategoryBonus(category.getName())
				+ historial.getBonus(category) + getPerkBonus(category);
	}

	public Integer getBonus(Skill skill) {
		return getProfession().getSkillBonus(skill.getName()) + historial.getBonus(skill) + getPerkBonus(skill);
	}

	public Integer getPerkApperanceBonus() {
		Integer total = 0;
		for (Perk perk : perks) {
			total += perk.getAppareanceBonus();
		}
		return total;
	}

	public Integer getPerkCharacteristicBonus(String characteristic) {
		Integer total = 0;
		for (Perk perk : perks) {
			total += perk.getCharacteristicBonus(characteristic);
		}
		return total;
	}

	public Integer getPerkBonus(Skill skill) {
		Integer total = 0;
		for (Perk perk : perks) {
			total += perk.getBonus(skill);
			PerkDecision decision = perkDecisions.get(perk);
			if (decision != null) {
				if (decision.isBonusChosen(skill)) {
					total += perk.getChosenBonus();
				}
			}
		}
		return total;
	}

	public Integer getPerkBonus(Category category) {
		Integer total = 0;
		for (Perk perk : perks) {
			total += perk.getBonus(category);
			PerkDecision decision = perkDecisions.get(perk);
			if (decision != null) {
				if (decision.isBonusChosen(category)) {
					total += perk.getChosenBonus();
				}
			}
		}
		return total;
	}

	public Integer getTotalValue(Category category) {
		return getRanksValue(category) + getBonus(category) + getCharacteristicsBonus(category);
	}

	public Integer getTotalValue(Skill skill) {
		return getRanksValue(skill) + getBonus(skill) + getTotalValue(skill.getCategory());
	}

	public Integer getMaxRanksPerCulture(Category category) {
		try {
			// Weapons cost are not still defined.
			if (category.getCategoryGroup().equals(CategoryGroup.WEAPON)) {
				return getProfession().getWeaponCategoryCost().get(0).getMaxRanksPerLevel();
			}
			return getCategoryCost(category, 0).getMaxRanksPerLevel();
		} catch (NullPointerException npe) {
			return 0;
		}
	}

	/**
	 * In culture, weapon costs are not defined. Therefore, we will use the best value.
	 * 
	 * @param category
	 * @return
	 */
	public Integer getCultureStimatedCategoryCost(Category category) {
		if (category.getCategoryGroup().equals(CategoryGroup.WEAPON)) {
			return getProfession().getWeaponCategoryCost().get(0).getRankCost(0);
		} else {
			return getCategoryCost(category, 0).getRankCost(0);
		}
	}

	/**
	 * Get the definition of the rank cost.
	 * 
	 * @param category
	 * @param currentListRanks
	 *            current ranks spent in this skill. In magic, higher levels are more expensive.
	 * @return
	 */
	public CategoryCost getCategoryCost(Category category, Integer currentListRanks) {
		if (category.getCategoryGroup().equals(CategoryGroup.WEAPON)) {
			return getProfessionDecisions().getWeaponCost(category);
		} else if (category.getCategoryGroup().equals(CategoryGroup.SPELL)) {
			return getProfession().getMagicCost(MagicListType.getMagicTypeOfCategory(category.getName()),
					currentListRanks);
		} else {
			return getProfession().getCategoryCost(category.getName());
		}
	}

	public Integer getMaxRanksPerLevel(Category category, Integer currentListRanks) {
		try {
			return getCategoryCost(category, currentListRanks).getMaxRanksPerLevel();
		} catch (NullPointerException npe) {
			return 0;
		}
	}

	/**
	 * Calculate the cost of a new rank.
	 * 
	 * @param category
	 *            category to be updated.
	 * @param currentRanks
	 *            Current ranks in category or skill. Must include old levels ranks and previous ranks included in this
	 *            new level. Only used for spell cost (higher level spells are more expensive).
	 * @param rankAdded
	 *            If it is the first, second or third rank added at this level [0, 1, 2].
	 * @return
	 */
	public Integer getNewRankCost(Category category, Integer currentRanks, Integer rankAdded) {
		// If you have X currentRanks, the cost of the new one will be
		// currentRanks + 1;
		CategoryCost cost = getCategoryCost(category, currentRanks);
		if (cost == null || cost.getRankCost(rankAdded) == null) {
			return Integer.MAX_VALUE;
		}

		return cost.getRankCost(rankAdded);
	}

	/**
	 * Obtains the cost for purchasing a new rank.
	 * 
	 * @param category
	 * @return
	 */
	public Integer getNewRankCost(Category category) {
		return getNewRankCost(category, getTotalRanks(category), getCurrentLevelRanks(category));
	}

	/**
	 * 
	 * @param skill
	 *            skill to be updated.
	 * @param currentRanks
	 *            Current ranks in category or skill. Must include old levels ranks and previous ranks included in this
	 *            new level. Only used for spell cost.
	 * @param rankAdded
	 *            If it is the first, second or third rank added at this level [0, 1, 2].
	 * @return
	 */
	public Integer getNewRankCost(Skill skill, Integer currentRanks, Integer rankAdded) {
		// Spell cost is increased if lots of spells are acquired in one level
		// and also if spells of high level are purchased.
		if (skill.getCategory().getCategoryGroup().equals(CategoryGroup.SPELL)) {
			return getNewRankCost(skill.getCategory(), currentRanks, rankAdded)
					* getCurrentLevel().getSpellRankMultiplier(skill);
		} else {
			return getNewRankCost(skill.getCategory(), currentRanks, rankAdded);
		}

	}

	/**
	 * Obtains the cost for purchasing a new rank.
	 * 
	 * @param skill
	 * @return
	 */
	public Integer getNewRankCost(Skill skill) {
		return getNewRankCost(skill, getRealRanks(skill), getCurrentLevelRanks(skill));
	}

	/**
	 * A category is not used if it has not skills or the cost is more than the selected in the configuration.
	 * 
	 * @param category
	 * @return
	 */
	public boolean isCategoryUseful(Category category) {
		// Categories without skills are useless.
		if (getCategory(category).getSkills().size() == 0) {
			return false;
		}
		// Weapons always are useful. We need to define the rank cost.
		if (category.getCategoryGroup().equals(CategoryGroup.WEAPON)) {
			// Firearms only if activated
			if (!firearmsAllowed && category.getName().contains(Spanish.FIREARMS_SUFIX)) {
				return false;
			}
			return true;
		}
		// Expensive categories are useless.
		if (getNewRankCost(category, 0, getTotalRanks(category)) > Config.getCategoryMaxCost()) {
			return false;
		}
		if (category.getName().equals(Spanish.OTHER_REALM_TRAINING_LISTS) && !isOtherRealmtrainingSpellsAllowed()) {
			return false;
		}
		return true;
	}

	/**
	 * Skill is useful to this character and can be used to add new ranks.
	 * 
	 * @param skill
	 * @return
	 */
	public boolean isSkillUseful(Skill skill) {
		if (skill.getSkillGroup().equals(SkillGroup.CHI) && !isChiPowersAllowed()) {
			return false;
		}
		if (skill.getSkillGroup().equals(SkillGroup.FIREARM) && !isFirearmsAllowed()) {
			return false;
		}
		return true;
	}

	/**
	 * Skill has some information than need to be shown.
	 * 
	 * @param skill
	 * @return
	 */
	public boolean isSkillInteresting(Skill skill) {
		// No ranks and no bonus, not interesting
		if ((getTotalRanks(skill) > 0) || getBonus(skill) > 0) {
			return true;
		}
		return false;
	}

	public Category getCategory(Category category) {
		if (category.getCategoryGroup().equals(CategoryGroup.SPELL)) {
			return magicSpellLists.getMagicCategory(category.getName());
		}
		return category;
	}

	public List<String> getTrainingsNames() {
		return trainingsNames;
	}

	public boolean isDarkSpellsAsBasicListsAllowed() {
		return darkSpellsAsBasicListsAllowed;
	}

	public void setDarkSpellsAsBasicListsAllowed(boolean darkSpellsAsBasicLists) {
		this.darkSpellsAsBasicListsAllowed = darkSpellsAsBasicLists;
	}

	public boolean isFirearmsAllowed() {
		return firearmsAllowed;
	}

	public void setFirearmsAllowed(boolean firearmsActivated) {
		this.firearmsAllowed = firearmsActivated;
		// Reset weaponCosts because new Weapons are added.
		getProfessionDecisions().resetWeaponCosts();
	}

	public boolean isChiPowersAllowed() {
		return chiPowersAllowed;
	}

	public void setChiPowersAllowed(boolean chiPowersAllowed) {
		this.chiPowersAllowed = chiPowersAllowed;
	}

	public boolean isOtherRealmtrainingSpellsAllowed() {
		return otherRealmtrainingSpellsAllowed;
	}

	public void setOtherRealmtrainingSpellsAllowed(boolean otherRealmtrainingSpells) {
		this.otherRealmtrainingSpellsAllowed = otherRealmtrainingSpells;
	}

	public CategoryCost getFirstWeaponCostNotSelected() {
		List<CategoryCost> costs = getProfession().getWeaponCategoryCost();
		for (CategoryCost cost : costs) {
			if (!getProfessionDecisions().isWeaponCostUsed(cost)) {
				return cost;
			}
		}
		// Last cost for any extra categories.
		return getProfession().getWeaponCategoryCost().get(getProfession().getWeaponCategoryCost().size() - 1);
	}

	public void setHistoryPoints(Skill skill, boolean value) {
		historial.setPoint(skill, value);
	}

	public void setHistoryPoints(Category category, boolean value) {
		historial.setPoint(category, value);
	}

	public boolean isHistoryPointSelected(Category category) {
		return historial.isHistorialPointSelected(category);
	}

	public boolean isHistoryPointSelected(Skill skill) {
		return historial.isHistorialPointSelected(skill);
	}

	public Integer getRemainingHistorialPoints() {
		return getRace().getHistorialPoints() - historial.getSpentHistoryPoints();
	}

	public CharacteristicRoll setCharacteristicHistorialUpdate(String abbreviature) {
		Roll roll = getStoredCharacteristicRoll(abbreviature);
		CharacteristicRoll characteristicRoll = historial.addCharactersiticUpdate(abbreviature,
				getCharacteristicTemporalValue(abbreviature), getCharacteristicPotentialValue(abbreviature), roll);
		return characteristicRoll;
	}

	public CharacteristicRoll getNewCharacteristicTrainingUpdate(String abbreviature, String trainingName) {
		Roll roll = getStoredCharacteristicRoll(abbreviature);
		CharacteristicRoll characteristicRoll = getTrainingDecision(trainingName).addCharactersiticUpdate(abbreviature,
				getCharacteristicTemporalValue(abbreviature), getCharacteristicPotentialValue(abbreviature), roll);
		return characteristicRoll;
	}

	public void addPerk(Perk perk) {
		if (!isPerkChoosed(perk)) {
			perks.add(perk);
		}
	}

	public void removePerk(Perk perk) {
		perks.remove(perk);
	}

	public boolean isPerkChoosed(Perk perk) {
		return perks.contains(perk);
	}

	private Integer getSpentPerksPoints() {
		Integer total = 0;
		for (Perk perk : perks) {
			total += perk.getCost();
		}
		return total;
	}

	public Integer getRemainingPerksPoints() {
		return getRace().getPerksPoints() - getSpentPerksPoints();
	}

	public void setPerkBonusDecision(Perk perk, List<String> chosenOptions) {
		if (chosenOptions != null && chosenOptions.size() > 0) {
			PerkDecision perkDecision = perkDecisions.get(perk);
			if (perkDecision == null) {
				perkDecision = new PerkDecision();
			}
			// Is the list a category list?
			if (perk.isCategorySelected(chosenOptions.get(0))) {
				perkDecision.setCategoriesBonusChoosen(chosenOptions);
			}
			// Is the list a skill list?
			if (perk.isSkillSelected(chosenOptions.get(0))) {
				perkDecision.setSkillsBonusChoosen(chosenOptions);
			}
			perkDecisions.put(perk.getName(), perkDecision);
		}
	}

	public void setPerkCommonDecision(Perk perk, List<String> commonSkillsChosen) {
		if (commonSkillsChosen != null && commonSkillsChosen.size() > 0) {
			PerkDecision perkDecision = perkDecisions.get(perk);
			if (perkDecision == null) {
				perkDecision = new PerkDecision();
			}
			perkDecision.setCommonSkillsChosen(commonSkillsChosen);
		}
	}

	public boolean isProfessional(Skill skill) {
		return skill.getSkillType().equals(SkillType.PROFESSIONAL) || getProfession().isProfessional(skill)
				|| professionDecisions.isProfessional(skill);
	}

	public boolean isRestricted(Skill skill) {
		return skill.getSkillType().equals(SkillType.RESTRICTED) || isRestrictedByPerk(skill)
				|| getProfession().isRestricted(skill) || professionDecisions.isRestricted(skill)
				|| getRace().isRestricted(skill);
	}

	public boolean isCommon(Skill skill) {
		return skill.getSkillType().equals(SkillType.COMMON) || isCommonByPerk(skill)
				|| getProfession().isCommon(skill) || professionDecisions.isCommon(skill) || getRace().isCommon(skill);
	}

	private boolean isCommonByPerk(Skill skill) {
		for (Perk perk : perks) {
			if (perk.isCommon(skill)) {
				return true;
			}
			PerkDecision decision = perkDecisions.get(perk);
			if (decision != null) {
				if (decision.isCommon(skill)) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean isRestrictedByPerk(Skill skill) {
		for (Perk perk : perks) {
			if (perk.isRestricted(skill)) {
				return true;
			}
		}
		return false;
	}

	public void setCommonSkillsChoseFromProfession(List<String> commonSkillsChose) {
		professionDecisions.setCommonSkillsChosen(commonSkillsChose);
	}

	public void setRestrictedSkillsChoseFromProfession(List<String> restrictedSkillsChose) {
		professionDecisions.setRestrictedSkillsChosen(restrictedSkillsChose);
	}

	public void setProfessionalSkillsChoseFromProfession(List<String> professionalSkillsChose) {
		professionDecisions.setProfessionalSkillsChosen(professionalSkillsChose);
	}

	public List<String> getAvailableTrainings() {
		List<String> availableTrainings = new ArrayList<>();
		List<String> selectedTrainings = getSelectedTrainings();
		for (String trainingName : TrainingFactory.getAvailableTrainings()) {
			// Correct race of training.
			if (TrainingFactory.getTraining(trainingName).getLimitedRaces().size() > 0) {
				if (!TrainingFactory.getTraining(trainingName).getLimitedRaces().contains(getRace().getName())) {
					continue;
				}
			}
			// Enough developmentPoints
			if (getTrainingCost(trainingName) > getRemainingDevelopmentPoints()) {
				continue;
			}
			// Not already selected Training
			if (selectedTrainings.contains(trainingName)) {
				continue;
			}
			availableTrainings.add(trainingName);
		}
		Collections.sort(availableTrainings);
		return availableTrainings;
	}

	public List<String> getSelectedTrainings() {
		List<String> selectedTrainings = new ArrayList<>();
		for (LevelUp level : levelUps) {
			selectedTrainings.addAll(level.getTrainings());
		}
		return selectedTrainings;
	}

	private Integer getTrainingSkillCostReduction(List<Skill> skills, Training training) {
		Integer costModification = 0;
		for (Skill skill : skills) {
			if (getRealRanks(skill) >= training.getSkillRequirements().get(skill.getName())) {
				costModification += training.getSkillRequirementsCostModification().get(skill.getName());
			}
		}
		return costModification;
	}

	private Integer getTrainingCharacteristicCostReduction(List<Characteristic> characteristics, Training training) {
		Integer costModification = 0;
		for (Characteristic characteristic : characteristics) {
			try {
				if (getCharacteristicTemporalValue(characteristic.getAbbreviature()) >= training
						.getCharacteristicRequirements().get(characteristic.getAbbreviature())) {
					costModification += training.getCharacteristicRequirementsCostModification().get(
							characteristic.getAbbreviature());
				}
			} catch (NullPointerException npe) {
				// Is not a requirement. Do nothing.
			}
		}
		return costModification;
	}

	public Integer getTrainingCost(String trainingName) {
		Integer baseCost = getProfession().getTrainingCost(trainingName);
		Training training = TrainingFactory.getTraining(trainingName);
		baseCost += getTrainingSkillCostReduction(SkillFactory.getSkills(training.getSkillRequirementsList()), training);
		baseCost += getTrainingCharacteristicCostReduction(Characteristics.getCharacteristics(), training);
		return baseCost;
	}

	public void addTraining(String trainingName) {
		if (levelUps.size() > 0) {
			getCurrentLevel().addTraining(trainingName);
		}
	}

	/**
	 * The user must to choose one category from a training if the training has this option and it has not been chosen
	 * before.
	 * 
	 * @param trainingName
	 * @return
	 */
	public boolean needToChooseOneCategory(Training training, TrainingCategory trainingCategory) {
		if (trainingCategory.needToChooseOneCategory()) {
			TrainingDecision trainingDecision = trainingDecisions.get(training.getName());
			if (trainingDecision == null || trainingDecision.getSelectedCategory(trainingCategory).isEmpty()) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	public String getHistoryText() {
		return historyText;
	}

	public void setHistoryText(String historyText) {
		this.historyText = historyText;
	}

	protected String getRaceName() {
		return raceName;
	}

	protected void setRaceName(String raceName) {
		this.raceName = raceName;
	}

	protected String getCultureName() {
		return cultureName;
	}

	protected void setCultureName(String cultureName) {
		this.cultureName = cultureName;
	}

	protected String getProfessionName() {
		return professionName;
	}

	protected void setProfessionName(String professionName) {
		this.professionName = professionName;
	}

	public Map<String, Integer> getCharacteristicsInitialTemporalValues() {
		return characteristicsInitialTemporalValues;
	}

	public void setCharacteristicsInitialTemporalValues(Map<String, Integer> characteristicsInitialTemporalValues) {
		this.characteristicsInitialTemporalValues = characteristicsInitialTemporalValues;
	}

	public Map<String, Integer> getCharacteristicsPotentialValues() {
		return characteristicsPotentialValues;
	}

	public void setCharacteristicsPotentialValues(Map<String, Integer> characteristicsPotentialValues) {
		this.characteristicsPotentialValues = characteristicsPotentialValues;
	}

	public Map<String, RollGroup> getCharacteristicsTemporalUpdatesRolls() {
		return characteristicsTemporalUpdatesRolls;
	}

	public void setCharacteristicsTemporalUpdatesRolls(Map<String, RollGroup> characteristicsTemporalUpdatesRolls) {
		this.characteristicsTemporalUpdatesRolls = characteristicsTemporalUpdatesRolls;
	}

	public boolean isCharacteristicsConfirmed() {
		return characteristicsConfirmed;
	}

	public void setCharacteristicsConfirmed(boolean characteristicsConfirmed) {
		this.characteristicsConfirmed = characteristicsConfirmed;
	}

	protected MagicSpellLists getMagicSpellLists() {
		return magicSpellLists;
	}

	protected void setMagicSpellLists(MagicSpellLists magicSpellLists) {
		this.magicSpellLists = magicSpellLists;
	}

	protected List<Training> getTrainings() {
		return trainings;
	}

	protected void setTrainings(List<Training> trainings) {
		this.trainings = trainings;
	}

	public TrainingDecision getTrainingDecision(String trainingName) {
		if (trainingDecisions.get(trainingName) == null) {
			trainingDecisions.put(trainingName, new TrainingDecision());
		}
		return trainingDecisions.get(trainingName);
	}

	protected Map<String, TrainingDecision> getTrainingDecisions() {
		return trainingDecisions;
	}

	protected void setTrainingDecisions(Map<String, TrainingDecision> trainingDecisions) {
		this.trainingDecisions = trainingDecisions;
	}

	protected Historial getHistorial() {
		return historial;
	}

	protected void setHistorial(Historial historial) {
		this.historial = historial;
	}

	protected List<Perk> getPerks() {
		return perks;
	}

	protected void setPerks(List<Perk> perks) {
		this.perks = perks;
	}

	protected Map<String, PerkDecision> getPerkDecisions() {
		return perkDecisions;
	}

	protected void setPerkDecisions(Map<String, PerkDecision> perkDecisions) {
		this.perkDecisions = perkDecisions;
	}

	public List<LevelUp> getLevelUps() {
		return levelUps;
	}

	protected void setLevelUps(List<LevelUp> levelUps) {
		this.levelUps = levelUps;
	}

	public ProfessionalRealmsOfMagicOptions getRealmOfMagic() {
		return realmOfMagic;
	}

	protected void setCultureDecisions(CultureDecisions cultureDecisions) {
		this.cultureDecisions = cultureDecisions;
	}

	protected void setProfessionDecisions(ProfessionDecisions professionDecisions) {
		this.professionDecisions = professionDecisions;
	}

	protected void setTrainingsNames(List<String> trainingsNames) {
		this.trainingsNames = trainingsNames;
	}

	protected void setAppearance(Appearance appearance) {
		this.appearance = appearance;
	}

	public boolean hasCommonOrProfessionalSkill(Category category) {
		for (Skill skill : category.getSkills()) {
			if ((isCommon(skill) || isProfessional(skill)) && !isRestricted(skill)) {
				return true;
			}
		}
		return false;
	}

	public boolean isSkillSpecialized(Skill skill) {
		for (String speciality : skill.getSpecialities()) {
			for (LevelUp level : getLevelUps()) {
				if (level.getSkillSpecializations().contains(speciality)) {
					return true;
				}
			}
		}
		return false;
	}

	public List<String> getSkillSpecializations(Skill skill) {
		List<String> specialities = new ArrayList<>();
		for (LevelUp level : getLevelUps()) {
			specialities.addAll(level.getSkillSpecializations(skill));
		}
		return specialities;
	}

	public void addSkillSpecialization(Skill skill, String specialization) {

		if (!getSkillSpecializations(skill).contains(specialization) && !isSkillGeneralized(skill)) {
			getCurrentLevel().addSkillSpecialization(specialization);
		}
	}

	public void addSkillGeneralized(Skill skill) {
		if (!isSkillGeneralized(skill)) {
			getCurrentLevel().getGeneralizedSkills().add(skill.getName());
		}
	}

	public boolean isSkillGeneralized(Skill skill) {
		for (LevelUp level : getLevelUps()) {
			if (level.getGeneralizedSkills().contains(skill.getName())) {
				return true;
			}
		}
		return false;
	}

	public Integer getRanksSpentInSkillSpecializations(Skill skill) {
		int total = 0;
		for (LevelUp level : levelUps) {
			total += level.getRanksSpentInSpecializations(skill);
		}
		return total;
	}

	public int getWeaponsLearnedInCurrentLevel() {
		int skillsIncreased = 0;
		for (Category category : CategoryFactory.getCategories()) {
			if (category.getCategoryGroup().equals(CategoryGroup.WEAPON)) {
				skillsIncreased += getSkillsWithNewRanks(category).size();
			}
		}
		return skillsIncreased;
	}

	public boolean isLifeStyle(Skill skill) {
		return false;
	}

	public void increaseLevel() {
		levelUps.add(new LevelUp());
	}

	public Long getCharacterPlayerId() {
		return characterPlayerId;
	}

	protected void setCharacterPlayerId(Long characterPlayerId) {
		this.characterPlayerId = characterPlayerId;
	}

	public void removeTraining(Training training) {
		if (training != null) {
			trainingDecisions.remove(training.getName());
			getCurrentLevel().removeTraining(training.getName());
		}
	}

}
