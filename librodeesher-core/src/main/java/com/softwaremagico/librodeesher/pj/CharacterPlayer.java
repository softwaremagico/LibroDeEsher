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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.gson.annotations.Expose;
import com.softwaremagico.files.Version;
import com.softwaremagico.librodeesher.basics.Roll;
import com.softwaremagico.librodeesher.basics.RollGroup;
import com.softwaremagico.librodeesher.basics.Spanish;
import com.softwaremagico.librodeesher.config.Config;
import com.softwaremagico.librodeesher.pj.categories.Category;
import com.softwaremagico.librodeesher.pj.categories.CategoryCost;
import com.softwaremagico.librodeesher.pj.categories.CategoryFactory;
import com.softwaremagico.librodeesher.pj.categories.CategoryGroup;
import com.softwaremagico.librodeesher.pj.categories.CategoryType;
import com.softwaremagico.librodeesher.pj.characteristic.Appearance;
import com.softwaremagico.librodeesher.pj.characteristic.Characteristic;
import com.softwaremagico.librodeesher.pj.characteristic.CharacteristicRoll;
import com.softwaremagico.librodeesher.pj.characteristic.Characteristics;
import com.softwaremagico.librodeesher.pj.characteristic.CharacteristicsAbbreviature;
import com.softwaremagico.librodeesher.pj.culture.Culture;
import com.softwaremagico.librodeesher.pj.culture.CultureDecisions;
import com.softwaremagico.librodeesher.pj.culture.CultureFactory;
import com.softwaremagico.librodeesher.pj.culture.InvalidCultureException;
import com.softwaremagico.librodeesher.pj.equipment.BonusType;
import com.softwaremagico.librodeesher.pj.equipment.MagicObject;
import com.softwaremagico.librodeesher.pj.equipment.ObjectBonus;
import com.softwaremagico.librodeesher.pj.export.pdf.PdfStandardSheet;
import com.softwaremagico.librodeesher.pj.historial.Historial;
import com.softwaremagico.librodeesher.pj.level.LevelUp;
import com.softwaremagico.librodeesher.pj.magic.MagicDefinitionException;
import com.softwaremagico.librodeesher.pj.magic.MagicFactory;
import com.softwaremagico.librodeesher.pj.magic.MagicListType;
import com.softwaremagico.librodeesher.pj.magic.MagicSpellLists;
import com.softwaremagico.librodeesher.pj.magic.RealmOfMagic;
import com.softwaremagico.librodeesher.pj.perk.Perk;
import com.softwaremagico.librodeesher.pj.perk.PerkDecision;
import com.softwaremagico.librodeesher.pj.perk.PerkFactory;
import com.softwaremagico.librodeesher.pj.perk.SelectedPerk;
import com.softwaremagico.librodeesher.pj.profession.InvalidProfessionException;
import com.softwaremagico.librodeesher.pj.profession.Profession;
import com.softwaremagico.librodeesher.pj.profession.ProfessionDecisions;
import com.softwaremagico.librodeesher.pj.profession.ProfessionFactory;
import com.softwaremagico.librodeesher.pj.profession.ProfessionalRealmsOfMagicOptions;
import com.softwaremagico.librodeesher.pj.race.Race;
import com.softwaremagico.librodeesher.pj.race.RaceFactory;
import com.softwaremagico.librodeesher.pj.race.exceptions.InvalidRaceDefinition;
import com.softwaremagico.librodeesher.pj.race.exceptions.InvalidRaceException;
import com.softwaremagico.librodeesher.pj.resistance.ResistanceType;
import com.softwaremagico.librodeesher.pj.skills.ChooseSkillGroup;
import com.softwaremagico.librodeesher.pj.skills.Skill;
import com.softwaremagico.librodeesher.pj.skills.SkillComparatorByName;
import com.softwaremagico.librodeesher.pj.skills.SkillComparatorByValue;
import com.softwaremagico.librodeesher.pj.skills.SkillFactory;
import com.softwaremagico.librodeesher.pj.skills.SkillForEnablingMustBeSelected;
import com.softwaremagico.librodeesher.pj.skills.SkillGroup;
import com.softwaremagico.librodeesher.pj.skills.SkillType;
import com.softwaremagico.librodeesher.pj.training.Training;
import com.softwaremagico.librodeesher.pj.training.TrainingCategory;
import com.softwaremagico.librodeesher.pj.training.TrainingDecision;
import com.softwaremagico.librodeesher.pj.training.TrainingFactory;
import com.softwaremagico.librodeesher.pj.training.TrainingItem;
import com.softwaremagico.librodeesher.pj.weapons.Weapon;
import com.softwaremagico.log.EsherLog;
import com.softwaremagico.persistence.StorableObject;

@Entity
@Table(name = "T_CHARACTERPLAYER")
public class CharacterPlayer extends StorableObject {
	private static final long serialVersionUID = -3029332867945656263L;
	// Store into the database the software version of creation.
	@Expose
	private String version = Version.getVersion();
	@Expose
	private String name;
	@Expose
	@Enumerated(EnumType.STRING)
	private SexType sex;
	@Expose
	private String historyText;

	@Expose
	private Integer characteristicsTemporalTotalPoints;

	@Expose
	@ElementCollection
	@CollectionTable(name = "T_CHARACTERPLAYER_INITIAL_TEMPORAL_VALUES")
	private Map<CharacteristicsAbbreviature, Integer> characteristicsInitialTemporalValues;

	@Expose
	@ElementCollection
	@CollectionTable(name = "T_CHARACTERPLAYER_POTENTIAL_VALUES")
	private Map<CharacteristicsAbbreviature, Integer> characteristicsPotentialValues;

	@Expose
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@CollectionTable(name = "T_CHARACTERPLAYER_TEMPORAL_CHARACTERISTICS_ROLLS")
	private Map<CharacteristicsAbbreviature, RollGroup> characteristicsTemporalUpdatesRolls;
	@Expose
	private boolean characteristicsConfirmed = false;

	@Expose
	private String raceName;
	@Transient
	private transient Race race;

	@Expose
	private String cultureName;
	@Transient
	private transient Culture culture;

	@Expose
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "cultureDecisionsId")
	private CultureDecisions cultureDecisions;

	@Expose
	private String professionName;
	@Transient
	private transient Profession profession;

	@Expose
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "professionDecisionsId")
	private ProfessionDecisions professionDecisions;

	@Expose
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "professionalRealmId")
	private ProfessionalRealmsOfMagicOptions realmOfMagic;

	@Transient
	// List are obtained when loading the character.
	private transient MagicSpellLists magicSpellLists;
	@Transient
	// Checks to only recreate the lists one time.
	private transient boolean magicSpellListsObtained;

	@Expose
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "historialId")
	private Historial historial;

	@Expose
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@CollectionTable(name = "T_CHARACTERPLAYER_SELECTED_PERKS")
	private List<SelectedPerk> selectedPerks;

	@Expose
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@CollectionTable(name = "T_CHARACTERPLAYER_PERKS_DECISIONS")
	private Map<String, PerkDecision> perkDecisions;

	@Expose
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Appearance appearance;

	@Expose
	private boolean darkSpellsAsBasicListsAllowed = false;
	@Expose
	private boolean firearmsAllowed = false;
	@Expose
	private boolean chiPowersAllowed = false;
	@Expose
	private boolean otherRealmtrainingSpellsAllowed = false;
	@Expose
	private boolean perksCostHistoryPoints = false;

	@Expose
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	@CollectionTable(name = "T_CHARACTERPLAYER_LEVEL_UP")
	@OrderColumn(name = "level_index")
	private List<LevelUp> levelUps;

	@Expose
	@ElementCollection
	@CollectionTable(name = "T_CHARACTERPLAYER_SKILLS_ENABLED")
	// Skill -- enables --> Skill.
	private Map<String, String> enabledSkill;

	@Expose
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@CollectionTable(name = "T_CHARACTERPLAYER_MAGIC_ITEMS")
	private List<MagicObject> magicItems;

	private transient CharacterPlayerHelper characterPlayerHelper;

	@Expose
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "insertedDataId")
	private InsertedData insertedData;

	public CharacterPlayer() {
		characterPlayerHelper = new CharacterPlayerHelper();
		appearance = new Appearance();
		levelUps = new ArrayList<>();
		historial = new Historial();
		characteristicsInitialTemporalValues = new HashMap<>();
		characteristicsTemporalUpdatesRolls = new HashMap<>();
		characteristicsPotentialValues = new HashMap<>();
		perkDecisions = new HashMap<>();
		enabledSkill = new HashMap<>();
		setTemporalValuesOfCharacteristics();
		sex = SexType.MALE;
		cultureDecisions = new CultureDecisions();
		professionDecisions = new ProfessionDecisions();
		selectedPerks = new ArrayList<>();
		magicSpellLists = null;
		magicSpellListsObtained = false;
		magicItems = new ArrayList<>();
		insertedData = new InsertedData();
		setDefaultConfig();
		// Starts in level 1.
		increaseLevel();
	}

	@Override
	public void resetIds() {
		resetIds(this);
		resetIds(levelUps);
		resetIds(appearance);
		resetIds(perkDecisions);
		resetIds(selectedPerks);
		resetIds(characteristicsInitialTemporalValues);
		resetIds(characteristicsPotentialValues);
		resetIds(characteristicsTemporalUpdatesRolls);
		resetIds(cultureDecisions);
		resetIds(professionDecisions);
		resetIds(realmOfMagic);
		resetIds(historial);
		resetIds(magicItems);
		resetIds(insertedData);
		resetIds(enabledSkill);
	}

	@Override
	public void resetComparationIds() {
		resetComparationIds(this);
		resetComparationIds(levelUps);
		resetComparationIds(appearance);
		resetComparationIds(perkDecisions);
		resetComparationIds(selectedPerks);
		resetComparationIds(characteristicsInitialTemporalValues);
		resetComparationIds(characteristicsPotentialValues);
		resetComparationIds(characteristicsTemporalUpdatesRolls);
		resetComparationIds(cultureDecisions);
		resetComparationIds(professionDecisions);
		resetComparationIds(realmOfMagic);
		resetComparationIds(historial);
		resetComparationIds(magicItems);
		resetComparationIds(insertedData);
		resetComparationIds(enabledSkill);
	}

	public void clearCache() {
		characterPlayerHelper.resetAll();
	}

	public Integer getAppearance() {
		return appearance.getTotal(getCharacteristicPotentialValue(CharacteristicsAbbreviature.getCharacteristicsAbbreviature("Pr")))
				+ getRace().getApperanceBonus() + getPerkApperanceBonus();
	}

	/**
	 * Default config is stored in a file and changed with the options windows.
	 * When a new character is created, it uses this default config options.
	 */
	private void setDefaultConfig() {
		darkSpellsAsBasicListsAllowed = Config.getDarkSpellsAsBasic();
		firearmsAllowed = Config.getFireArmsActivated();
		chiPowersAllowed = Config.getChiPowersAllowed();
		otherRealmtrainingSpellsAllowed = Config.getOtherRealmtrainingSpells();
		perksCostHistoryPoints = Config.getPerksCostHistoryPoints();
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
		return (getNewRankCost(getCategory(Spanish.BASIC_LIST_TAG), 0, 0) == 3);
	}

	/**
	 * Wizards of two realms.
	 * 
	 * @return
	 */
	public boolean isHybridWizard() {
		return (getNewRankCost(getCategory(Spanish.BASIC_LIST_TAG), 0, 0) == 3 && getRealmOfMagic().getRealmsOfMagic().size() == 2);
	}

	/**
	 * Is semi wizard.
	 * 
	 * @return
	 */
	public boolean isSemiWizard() {
		return (getNewRankCost(getCategory(Spanish.BASIC_LIST_TAG), 0, 0) == 6);
	}

	public void setCultureHobbyRanks(String skillName, Integer ranks) {
		cultureDecisions.setHobbyRanks(skillName, ranks);
		characterPlayerHelper.resetSkillRanks(skillName);
	}

	public int getCultureHobbyRanks(String skillName) {
		return cultureDecisions.getHobbyRanks(skillName);
	}

	public int getCultureTotalHobbyRanks() {
		return cultureDecisions.getTotalHobbyRanks();
	}

	public void setCultureLanguageRanks(String skillName, Integer ranks) {
		cultureDecisions.setLanguageRank(skillName, ranks);
		characterPlayerHelper.resetSkillRanks(skillName);
	}

	public int getCultureLanguageRanks(String skillName) {
		return cultureDecisions.getLanguageRanks(skillName);
	}

	public int getCultureTotalLanguageRanks() {
		return cultureDecisions.getTotalLanguageRanks();
	}

	public void setCultureSpellRanks(String skillName, Integer ranks) {
		cultureDecisions.setSpellRanks(skillName, ranks);
		characterPlayerHelper.resetSkillRanks(skillName);
	}

	public int getCultureSpellRanks(String skillName) {
		return cultureDecisions.getSpellRanks(skillName);
	}

	public int getCultureTotalSpellRanks() {
		return cultureDecisions.getTotalSpellRanks();
	}

	public void setCultureWeaponsRanks(String skillName, Integer ranks) {
		cultureDecisions.setWeaponRanks(skillName, ranks);
		characterPlayerHelper.resetSkillRanks(skillName);
	}

	public int getCultureWeaponsRanks(String skillName) {
		return cultureDecisions.getWeaponRanks(skillName);
	}

	public int getCultureTotalWeaponsRanks(Category category) {
		return cultureDecisions.getTotalWeaponRanks(category);
	}

	public ProfessionDecisions getProfessionDecisions() {
		return professionDecisions;
	}

	public boolean areCharacteristicsConfirmed() {
		return characteristicsConfirmed;
	}

	public Integer getCharacteristicPotentialValue(CharacteristicsAbbreviature abbreviature) {
		if (insertedData.getCharacteristicsPotentialValuesModification(abbreviature) != null) {
			return insertedData.getCharacteristicsPotentialValuesModification(abbreviature);
		}
		Integer potential = characteristicsPotentialValues.get(abbreviature);
		if (potential != null) {
			return potential;
		}
		return 0;
	}

	public Integer getCharacteristicRaceBonus(CharacteristicsAbbreviature abbreviature) {
		return getRace().getCharacteristicBonus(abbreviature);
	}

	public List<Characteristic> getCharacteristics() {
		return Characteristics.getCharacteristics();
	}

	public Integer getCharacteristicSpecialBonus(CharacteristicsAbbreviature abbreviature) {
		return getPerkCharacteristicBonus(abbreviature);
	}

	public Integer getCharacteristicTemporalBonus(CharacteristicsAbbreviature abbreviature) {
		return Characteristics.getTemporalBonus(getCharacteristicTemporalValue(abbreviature));
	}

	private Integer getTemporalModifications(CharacteristicsAbbreviature abbreviature) {
		Integer temporalValue = 0;
		if (insertedData.getCharacteristicsTemporalValuesModification(abbreviature) != null
				&& insertedData.getCharacteristicsTemporalValuesModification(abbreviature) != 0) {
			temporalValue = insertedData.getCharacteristicsTemporalValuesModification(abbreviature);
		} else {
			temporalValue = characteristicsInitialTemporalValues.get(abbreviature);
		}
		// Only rolls after insertion of data.
		if (areCharacteristicsConfirmed()) {
			for (LevelUp levelUp : getLevelUps()) {
				CharacteristicRoll roll = levelUp.getCharacteristicsUpdates(abbreviature);
				if (roll != null) {
					temporalValue += Characteristic.getCharacteristicUpgrade(roll.getCharacteristicTemporalValue(),
							roll.getCharacteristicPotentialValue(), roll.getRoll());
				}
			}
			// Only use training modifications after insertedData.
			for (TrainingDecision training : getTrainingDecisionsFromLevel(insertedData.getCreatedAtLevel()).values()) {
				for (CharacteristicRoll roll : training.getCharacteristicsUpdates(abbreviature)) {
					temporalValue += Characteristic.getCharacteristicUpgrade(roll.getCharacteristicTemporalValue(),
							roll.getCharacteristicPotentialValue(), roll.getRoll());
				}
			}
			// History rolls only if not inserted data.
			if (!insertedData.isEnabled()) {
				for (CharacteristicRoll roll : historial.getCharacteristicsUpdates(abbreviature)) {
					temporalValue += Characteristic.getCharacteristicUpgrade(roll.getCharacteristicTemporalValue(),
							roll.getCharacteristicPotentialValue(), roll.getRoll());
				}
			}
		}
		return temporalValue;
	}

	public Integer getCharacteristicTemporalValue(CharacteristicsAbbreviature abbreviature) {
		Integer value = characterPlayerHelper.getCharacteristicTemporalValue(abbreviature);
		if (value != null) {
			return value;
		}
		value = getTemporalModifications(abbreviature);
		characterPlayerHelper.setCharacteristicTemporalValue(abbreviature, value);
		return value;
	}

	/**
	 * Temporal value of the characteristic when starting the creation of the
	 * character.
	 * 
	 * @param abbreviature
	 * @return
	 */
	public Integer getCharacteristicInitialTemporalValue(CharacteristicsAbbreviature abbreviature) {
		characterPlayerHelper.resetCharacteristicTemporalValues();
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

	public Integer getCharacteristicTotalBonus(CharacteristicsAbbreviature abbreviature) {
		if (abbreviature.equals(CharacteristicsAbbreviature.NULL)) {
			return 0;
		}
		if (abbreviature.equals(CharacteristicsAbbreviature.MAGIC_REALM)) {
			return getBonusCharacteristicOfRealmOfMagic();
		}
		return getCharacteristicTemporalBonus(abbreviature) + getCharacteristicRaceBonus(abbreviature)
				+ getCharacteristicSpecialBonus(abbreviature);
	}

	public Integer getCurrentLevelNumber() {
		return levelUps.size() + insertedData.getInsertedLevels();
	}

	public Culture getCulture() {
		if (cultureName == null)
			return null;
		if (culture == null || !cultureName.equals(culture.getName())) {
			try {
				culture = CultureFactory.getCulture(cultureName);
			} catch (InvalidCultureException e) {
				EsherLog.errorMessage(this.getClass().getName(), e);
				return null;
			}
		}
		return culture;
	}

	public Integer getTotalDevelopmentPoints() {
		Integer total = 0;
		total += getCharacteristicTemporalValue(CharacteristicsAbbreviature.AGILITY);
		total += getCharacteristicTemporalValue(CharacteristicsAbbreviature.CONSTITUTION);
		total += getCharacteristicTemporalValue(CharacteristicsAbbreviature.MEMORY);
		total += getCharacteristicTemporalValue(CharacteristicsAbbreviature.REASON);
		total += getCharacteristicTemporalValue(CharacteristicsAbbreviature.SELFDISCIPLINE);
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
			try {
				profession = ProfessionFactory.getProfession(professionName);
			} catch (InvalidProfessionException e) {
				return null;
			}
		}
		return profession;
	}

	public Race getRace() {
		if (raceName == null)
			return null;
		if (race == null || !raceName.equals(race.getName())) {
			try {
				race = RaceFactory.getRace(raceName);
			} catch (InvalidRaceException e) {
				return null;
			}
		}
		return race;
	}

	public Integer getDefensiveBonus() {
		return (getCharacteristicTotalBonus(CharacteristicsAbbreviature.SPEED) * 3) + getItemBonus(BonusType.DEFENSIVE_BONUS);
	}

	public Integer getResistanceBonus(ResistanceType type) {
		switch (type) {
		case CANALIZATION:
			return getCharacteristicTotalBonus(CharacteristicsAbbreviature.INTUITION) * 3 + getRace().getResistancesBonus(type)
					+ getPerkResistanceBonus(type);
		case ESSENCE:
			return getCharacteristicTotalBonus(CharacteristicsAbbreviature.EMPATHY) * 3 + getRace().getResistancesBonus(type)
					+ getPerkResistanceBonus(type);
		case MENTALISM:
			return getCharacteristicTotalBonus(CharacteristicsAbbreviature.PRESENCE) * 3 + getRace().getResistancesBonus(type)
					+ getPerkResistanceBonus(type);
		case PSIONIC:
			return 0 + getRace().getResistancesBonus(type) + getPerkResistanceBonus(type);
		case POISON:
			return getCharacteristicTotalBonus(CharacteristicsAbbreviature.CONSTITUTION) * 3 + getRace().getResistancesBonus(type)
					+ getPerkResistanceBonus(type);
		case DISEASE:
			return getCharacteristicTotalBonus(CharacteristicsAbbreviature.CONSTITUTION) * 3 + getRace().getResistancesBonus(type)
					+ getPerkResistanceBonus(type);
		case COLD:
			return 0 + getRace().getResistancesBonus(type) + getPerkResistanceBonus(type);
		case HOT:
			return 0 + getRace().getResistancesBonus(type) + getPerkResistanceBonus(type);
		case FEAR:
			return getCharacteristicTotalBonus(CharacteristicsAbbreviature.SELFDISCIPLINE) * 3 + getRace().getResistancesBonus(type)
					+ getPerkResistanceBonus(type);
		default:
			return 0;
		}
	}

	private Integer getPerkResistanceBonus(ResistanceType type) {
		Integer total = 0;
		for (SelectedPerk perk : getRealSelectedPerks()) {
			for (String perkResistances : PerkFactory.getPerk(perk).getResistanceBonus().keySet()) {
				if (type.getTag().equals(PerkFactory.getPerk(perk).getResistanceBonus())) {
					total += PerkFactory.getPerk(perk).getResistanceBonus().get(perkResistances);
				} else if (perkResistances.equals(Spanish.REALM_TAG)) {
					for (RealmOfMagic realm : getRealmOfMagic().getRealmsOfMagic()) {
						if (realm.getName().equals(type.getTag())) {
							total += PerkFactory.getPerk(perk).getResistanceBonus().get(perkResistances);
						}
					}
				}
			}
		}
		return total;
	}

	public SexType getSex() {
		return sex;
	}

	private Integer getSpentDevelopmentPointsInCategoryRanks(Integer level) {
		Integer total = 0;
		List<String> categoriesWithRanks = levelUps.get(level).getCategoriesWithRanks();
		for (String categoryName : categoriesWithRanks) {
			Category category = getCategory(categoryName);
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
			if (skill != null) {
				Integer ranksUpdatedInLevel = levelUps.get(level).getSkillsRanks(skillName);
				for (int i = 0; i < ranksUpdatedInLevel; i++) {
					total += getRankCost(skill, getPreviousRanks(skill) + i, i);
				}
			} else {
				EsherLog.severe(this.getClass().getName(), "Habilidad no encontrada: " + skillName);
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
		return getSpentDevelopmentPoints(levelUps.size() - 1);
	}

	private Integer getSpentDevelopmentPoints(int level) {
		// If the user has inserted data, set development points to zero.
		if (level == insertedData.getCreatedAtLevel() - 1) {
			return getTotalDevelopmentPoints();
		}
		if (level < levelUps.size()) {
			return getSpentDevelopmentPointsInCategoryRanks(level) + getSpentDevelopmentPointsInSkillsRanks(level)
					+ getSpentDevelopmentPointsInTrainings(level);
		}
		return 0;
	}

	public Integer getRemainingDevelopmentPoints() {
		if (characterPlayerHelper.getDevelopmentPoints() != null) {
			return characterPlayerHelper.getDevelopmentPoints();
		}
		Integer developmentPoints = getTotalDevelopmentPoints() - getSpentDevelopmentPoints();
		characterPlayerHelper.setDevelopmentPoints(developmentPoints);
		return developmentPoints;
	}

	public Integer getCharacteristicsTemporalPointsSpent(CharacteristicsAbbreviature abbreviature) {
		return Characteristic.getTemporalCost(getCharacteristicInitialTemporalValue(abbreviature));
	}

	public Integer getCharacteristicsTemporalPointsSpent() {
		Integer total = 0;
		for (Characteristic characteristic : Characteristics.getCharacteristics()) {
			total += getCharacteristicsTemporalPointsSpent(characteristic.getAbbreviature());
		}
		return total;
	}

	public boolean isMainProfessionalCharacteristic(CharacteristicsAbbreviature characteristic) {
		return getProfession().isCharacteristicProfessional(characteristic);
	}

	public boolean isMainProfessionalCharacteristic(String abbreviature) {
		return getProfession().isCharacteristicProfessional(CharacteristicsAbbreviature.getCharacteristicsAbbreviature(abbreviature));
	}

	public void setCharacteristicsAsConfirmed() {
		setPotentialValues();
		characteristicsConfirmed = true;
		characterPlayerHelper.resetCharacteristicTemporalValues();
	}

	private void setCharacteristicsTemporalUpdatesRolls() {
		for (Characteristic characteristic : Characteristics.getCharacteristics()) {
			if (characteristicsTemporalUpdatesRolls.get(characteristic.getAbbreviature()) == null) {
				characteristicsTemporalUpdatesRolls.put(characteristic.getAbbreviature(), new RollGroup(characteristic.getAbbreviature()));
			}
		}
	}

	private Roll getStoredCharacteristicRoll(CharacteristicsAbbreviature abbreviature) {
		Roll roll = characteristicsTemporalUpdatesRolls.get(abbreviature).getFirst();
		return roll;
	}

	public void setCharacteristicTemporalValues(CharacteristicsAbbreviature abbreviature, Integer value) {
		characteristicsInitialTemporalValues.put(abbreviature, value);
		characterPlayerHelper.resetAllCategoryCharacteristicsBonus();
	}

	public void setCulture(String cultureName) {
		this.cultureName = cultureName;
		characterPlayerHelper.resetAll();
	}

	public void setName(String name) {
		if (!name.equals(Spanish.DEFAULT_NAME)) {
			this.name = name;
		}
	}

	private void setPotentialValues() {
		for (Characteristic characteristic : Characteristics.getCharacteristics()) {
			Integer potential = Characteristics.getPotencial(characteristicsInitialTemporalValues.get(characteristic.getAbbreviature()));
			characteristicsPotentialValues.put(characteristic.getAbbreviature(), potential);
			// Update potentical characteristics of level 1, that still has
			// value of zero.
			for (LevelUp levelUp : getLevelUps()) {
				levelUp.updateCharacteristicRoll(characteristic.getAbbreviature(),
						characteristicsInitialTemporalValues.get(characteristic.getAbbreviature()), potential);
			}
		}
	}

	public void setProfession(String professionName) {
		if (this.professionName == null || !this.professionName.equals(professionName)) {
			this.professionName = professionName;
			setTemporalValuesOfCharacteristics();
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
			characteristicsInitialTemporalValues.put(characteristic.getAbbreviature(), Characteristics.INITIAL_CHARACTERISTIC_VALUE);
		}
		setCharacteristicsTemporalUpdatesRolls();
	}

	public Integer getLanguageInitialRanks(String language) {
		// return Math.max(getCulture().getLanguageMaxRanks(language),
		// getRace().getLanguageInitialRanks(language));
		return getRace().getLanguageInitialRanks(language);
	}

	public Integer getLanguageMaxInitialRanks(String language) {
		return Math.max(getCulture().getLanguageMaxRanks(language), getRace().getLanguageMaxRanks(language));
	}

	public Integer getLanguageRanks(String language) {
		return Math.max(getLanguageInitialRanks(language), cultureDecisions.getLanguageRanks(language));
	}

	public Integer getCurrentLevelRanks(Category category) {
		if (levelUps.size() > 0) {
			Integer ranks = getCurrentLevel().getCategoryRanks(category.getName());
			return ranks;
		} else {
			return 0;
		}
	}

	public Integer getCurrentLevelRanks(Skill skill) {
		if (levelUps.size() > 0) {
			Integer skillRanks = getCurrentLevel().getSkillsRanks(skill.getName());
			return skillRanks;
		} else {
			return 0;
		}
	}

	public LevelUp getCurrentLevel() {
		if (levelUps == null || levelUps.isEmpty()) {
			return null;
		}
		return levelUps.get(levelUps.size() - 1);
	}

	public void setCurrentLevelRanks(Skill skill, Integer ranks) throws SkillForEnablingMustBeSelected {
		if (levelUps.size() > 0) {
			getCurrentLevel().setSkillsRanks(skill, ranks);
			characterPlayerHelper.resetSkillRanks(skill.getName());
		}
		// Enable a disabled skill
		if (ranks > 0 && !skill.getEnableSkills().isEmpty() && enabledSkill.get(skill.getName()) == null) {
			throw new SkillForEnablingMustBeSelected();
		} else if (ranks == 0) {
			// Remove enabled skill.
			if (enabledSkill.get(skill.getName()) != null) {
				getCurrentLevel().setSkillsRanks(SkillFactory.getSkill(enabledSkill.get(skill.getName())), 0);
				enabledSkill.remove(skill.getName());
			}
		}
	}

	public void setCurrentLevelRanks(Category category, Integer ranks) {
		if (levelUps.size() > 0) {
			getCurrentLevel().setCategoryRanks(category.getName(), ranks);
			characterPlayerHelper.resetCategoryRanks(category.getName());
		}
	}

	protected Integer getPreviousLevelsRanks(Category category) {
		Integer total = 0;
		for (int i = 0; i < levelUps.size() - 1; i++) {
			total += levelUps.get(i).getCategoryRanks(category.getName());
		}
		return total;
	}

	protected Integer getPreviousLevelsRanks(Skill skill) {
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
		total += getTrainingRanks(category);
		total += getInsertedRanks(category);
		for (PerkDecision perkDecision : getPerkDecisions().values()) {
			total += perkDecision.getCategoriesRanksChosen().contains(category.getName()) ? 1 : 0;
		}
		return total;
	}

	protected Integer getInsertedRanks(Category category) {
		return insertedData.getCategoryRanksModification(category.getName());
	}

	protected Integer getInsertedRanks(Skill skill) {
		return insertedData.getSkillsRanksModification(skill.getName());
	}

	public Integer getPreviousRanks(Skill skill) {
		Integer total = 0;
		total += getCulture().getCultureRanks(skill);
		total += getCultureWeaponsRanks(skill.getName());
		total += getCultureHobbyRanks(skill.getName());
		total += getPreviousLevelsRanks(skill);
		total += getInsertedRanks(skill);
		if (skill.getCategory() != null) {
			if (skill.getCategory().getName().toLowerCase().equals(Spanish.COMUNICATION_CATEGORY)) {
				total += getLanguageRanks(skill.getName());
			}
		}
		total += getTrainingRanks(skill);

		for (PerkDecision perkDecision : getPerkDecisions().values()) {
			total += perkDecision.getSkillsRanksChosen().contains(skill.getName()) ? 1 : 0;
		}

		return total;
	}

	protected Integer getTrainingRanks(Skill skill) {
		int total = 0;
		for (String trainingName : getTrainings()) {
			Training training = TrainingFactory.getTraining(trainingName);
			for (TrainingCategory trainingCategory : training.getCategoriesWithRanks()) {
				TrainingDecision trainingDecision = getTrainingDecision(training.getName());
				total += trainingDecision.getSkillRank(training.getTrainingCategoryIndex(trainingCategory), skill);
			}
		}
		return total;
	}

	protected Integer getTrainingRanks(Category category) {
		int total = 0;
		for (String trainingName : getTrainings()) {
			Training training = TrainingFactory.getTraining(trainingName);
			for (TrainingCategory trainingCategory : training.getCategoriesWithRanks()) {
				// Default category
				if (trainingCategory.getCategoryOptions().size() == 1
						&& trainingCategory.getCategoryOptions().get(0).equals(category.getName())) {
					total += trainingCategory.getCategoryRanks();
				} else {
					// Selected category from list
					TrainingDecision trainingDecision = getTrainingDecisions().get(trainingName);
					if (trainingDecision != null
							&& trainingDecision.getSelectedCategory(training.getTrainingCategoryIndex(trainingCategory)).contains(
									category.getName())) {
						total += trainingCategory.getCategoryRanks();
					}
				}
			}
		}
		return total;
	}

	public Integer getTotalRanks(Category category) {
		if (characterPlayerHelper.getCategoryRanks(category.getName()) != null) {
			return characterPlayerHelper.getCategoryRanks(category.getName());
		}
		Integer totalRanks = getPreviousRanks(category) + getCurrentLevelRanks(category);
		characterPlayerHelper.setCategoryRanks(category.getName(), totalRanks);
		return totalRanks;
	}

	public Integer getTotalRanks(Skill skill) {
		if (characterPlayerHelper.getSkillRanks(skill.getName()) != null) {
			return characterPlayerHelper.getSkillRanks(skill.getName());
		}
		Integer totalRanks = Math.max(0, getPreviousRanks(skill) + getCurrentLevelRanks(skill) - getSkillSpecializations(skill).size());
		characterPlayerHelper.setSkillRanks(skill.getName(), totalRanks);
		return totalRanks;
	}

	/**
	 * Get total ranks for a specialized skill.
	 * 
	 * @param skill
	 * @return
	 */
	public Integer getSpecializedRanks(Skill skill) {
		if (getTotalRanks(skill.getCategory()) > 0) {
			return getTotalRanks(skill) * 2;
		} else {
			return (int) (getTotalRanks(skill) * 1.5);
		}
	}

	public Integer getRealRanks(Skill skill) {
		Float modifier = (float) 1;
		if (isRestricted(skill)) {
			modifier = (float) 0.5;
		} else if (isGeneralized(skill)) {
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

	public Integer getSpecializedRanksValue(Skill skill) {
		return skill.getRankValue(this, getSpecializedRanks(skill));
	}

	public Integer getCharacteristicsBonus(Category category) {
		if (characterPlayerHelper.getCategoryCharacteristicsBonus(category.getName()) != null) {
			return characterPlayerHelper.getCategoryCharacteristicsBonus(category.getName());
		}
		Integer total = 0;
		List<CharacteristicsAbbreviature> characteristicsAbbreviature = category.getCharacteristics();
		for (CharacteristicsAbbreviature characteristic : characteristicsAbbreviature) {
			total += getCharacteristicTotalBonus(characteristic);
		}
		characterPlayerHelper.setCategoryCharacteristicsBonus(category.getName(), total);
		return total;
	}

	/**
	 * Gets all bonus except the magic items.
	 * 
	 * @param category
	 * @return
	 */
	public Integer getSimpleBonus(Category category) {
		if (characterPlayerHelper.getCategoryGeneralBonus(category.getName()) != null) {
			return characterPlayerHelper.getCategoryGeneralBonus(category.getName());
		}
		Integer genericBonus = category.getBonus() + getProfession().getCategoryBonus(category.getName()) + getRace().getBonus(category)
				+ getHistorial().getBonus(category) + getPerkBonus(category) + getConditionalPerkBonus(category);
		characterPlayerHelper.setCategoryGeneralBonus(category.getName(), genericBonus);
		return genericBonus;
	}

	public Integer getBonus(Category category) {
		if (characterPlayerHelper.getCategoryTotalBonus(category.getName()) != null) {
			return characterPlayerHelper.getCategoryTotalBonus(category.getName());
		}
		Integer genericBonus = getSimpleBonus(category);
		Integer itemBonus = getItemBonus(category);
		characterPlayerHelper.setCategoryObjectBonus(category.getName(), itemBonus);
		characterPlayerHelper.setCategoryTotalBonus(category.getName(), genericBonus + itemBonus);
		return genericBonus + itemBonus;
	}

	/**
	 * Gets all bonus except the magic items.
	 * 
	 * @param category
	 * @return
	 */
	public Integer getSimpleBonus(Skill skill) {
		if (characterPlayerHelper.getSkillGeneralBonus(skill.getName()) != null) {
			return characterPlayerHelper.getSkillGeneralBonus(skill.getName());
		}
		Integer genericBonus = getProfession().getSkillBonus(skill.getName()) + getHistorial().getBonus(skill) + getPerkBonus(skill)
				+ getConditionalPerkBonus(skill) + getRace().getBonus(skill);
		characterPlayerHelper.setSkillGeneralBonus(skill.getName(), genericBonus);
		return genericBonus;
	}

	public Integer getBonus(Skill skill) {
		if (characterPlayerHelper.getSkillTotalBonus(skill.getName()) != null) {
			return characterPlayerHelper.getSkillTotalBonus(skill.getName());
		}
		Integer genericBonus = getSimpleBonus(skill);
		Integer itemBonus = getItemBonus(skill);
		characterPlayerHelper.setSkillObjectBonus(skill.getName(), itemBonus);
		characterPlayerHelper.setSkillTotalBonus(skill.getName(), genericBonus + itemBonus);
		return genericBonus + itemBonus;
	}

	public Integer getPerkApperanceBonus() {
		Integer total = 0;
		for (SelectedPerk perk : getRealSelectedPerks()) {
			total += PerkFactory.getPerk(perk).getAppareanceBonus();
		}
		return total;
	}

	public Integer getPerkCharacteristicBonus(CharacteristicsAbbreviature characteristic) {
		Integer total = 0;
		for (SelectedPerk perk : getRealSelectedPerks()) {
			total += PerkFactory.getPerk(perk).getCharacteristicBonus(characteristic);
		}
		return total;
	}

	/**
	 * Get all bonus related to a skill: conditional and concrete.
	 * 
	 * @param skill
	 * @return
	 */
	public Integer getPerkBonus(Skill skill) {
		Integer total = 0;
		for (SelectedPerk perk : getRealSelectedPerks()) {
			total += PerkFactory.getPerk(perk).getBonus(skill);
			PerkDecision decision = perkDecisions.get(perk.getName());
			if (decision != null) {
				if (decision.isBonusChosen(skill)) {
					total += PerkFactory.getPerk(perk).getChosenBonus();
				}
			}
			// Get rank bonus. Some perks add an extra bonus for each rank
			total += PerkFactory.getPerk(perk).getSkillRanksBonus(skill.getName()) * getRealRanks(skill);
		}
		return total;
	}

	public Integer getConditionalPerkBonus(Skill skill) {
		Integer total = 0;
		for (SelectedPerk perk : getRealSelectedPerks()) {
			Integer bonus = PerkFactory.getPerk(perk).getConditionalSkillBonus().get(skill.getName());
			if (bonus != null) {
				total += bonus;
			}
		}
		return total;
	}

	public Integer getPerkBonus(Category category) {
		Integer total = 0;
		for (SelectedPerk perk : getRealSelectedPerks()) {
			total += PerkFactory.getPerk(perk).getBonus(category);
			PerkDecision decision = perkDecisions.get(perk.getName());
			if (decision != null) {
				if (decision.isBonusChosen(category)) {
					total += PerkFactory.getPerk(perk).getChosenBonus();
				}
			}
			// Get rank bonus.
			total += PerkFactory.getPerk(perk).getCategoryRanksBonus(category.getName()) * getTotalRanks(category);
		}
		return total;
	}

	public Integer getConditionalPerkBonus(Category category) {
		Integer total = 0;
		for (SelectedPerk perk : getRealSelectedPerks()) {
			Integer bonus = PerkFactory.getPerk(perk).getConditionalBonus(category);
			if (bonus != null) {
				total += bonus;
			}
		}
		return total;
	}

	public Integer getTotalValue(Category category) {
		if (characterPlayerHelper.getCategoryTotal(category.getName()) != null) {
			return characterPlayerHelper.getCategoryTotal(category.getName());
		}
		int totalValue = getRanksValue(category) + getBonus(category) + getCharacteristicsBonus(category);
		characterPlayerHelper.setCategoryTotal(category.getName(), totalValue);
		return totalValue;
	}

	public Integer getTotalValue(Skill skill) {
		if (characterPlayerHelper.getSkillTotal(skill.getName()) != null) {
			return characterPlayerHelper.getSkillTotal(skill.getName());
		}
		int totalValue = getRanksValue(skill) + getBonus(skill) + getTotalValue(skill.getCategory());
		characterPlayerHelper.setSkillTotal(skill.getName(), totalValue);
		return totalValue;
	}

	public Integer getSpecializedTotalValue(Skill skill) {
		return getSpecializedRanksValue(skill) + getBonus(skill) + getTotalValue(skill.getCategory());
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
	 * In culture, weapon costs are not defined. Therefore, we will use the best
	 * value.
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
	 *            current ranks spent in this skill. In magic, higher levels are
	 *            more expensive.
	 * @return
	 */
	public CategoryCost getCategoryCost(Category category, Integer currentListRanks) {
		if (category.getCategoryGroup().equals(CategoryGroup.WEAPON)) {
			return getProfessionDecisions().getWeaponCost(category);
		} else if (category.getCategoryGroup().equals(CategoryGroup.SPELL)) {
			return getProfession().getMagicCost(MagicListType.getMagicTypeOfCategory(category.getName()), currentListRanks);
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
	 *            Current ranks in category or skill. Must include old levels
	 *            ranks and previous ranks included in this new level. Only used
	 *            for spell cost (higher level spells are more expensive).
	 * @param rankAdded
	 *            If it is the first, second or third rank added at this level
	 *            [0, 1, 2].
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
	 *            Current ranks in category or skill. Must include old levels
	 *            ranks and previous ranks included in this new level. Only used
	 *            for spell cost.
	 * @param rankAdded
	 *            If it is the first, second or third rank added at this level
	 *            [0, 1, 2].
	 * @return
	 */
	public Integer getRankCost(Skill skill, Integer currentRanks, Integer rankAdded) {
		// Spell cost is increased if lots of spells are acquired in one level
		// and also if spells of high level are purchased.
		if (skill.getCategory().getCategoryGroup().equals(CategoryGroup.SPELL)) {
			return getNewRankCost(skill.getCategory(), currentRanks, rankAdded)
			// Calculate the multiplier if we add a new rank to a spell
			// list.
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
		return getRankCost(skill, getRealRanks(skill), getCurrentLevelRanks(skill));
	}

	/**
	 * A category is not used if it has not skills or the cost is more than the
	 * selected in the configuration.
	 * 
	 * @param category
	 * @return
	 */
	public boolean isCategoryUseful(Category category) {
		// Weapons are allowed despite cost is not set.
		if (category.getCategoryGroup().equals(CategoryGroup.WEAPON)) {
			if (!isFirearmsAllowed() && category.getName().contains(Spanish.FIREARMS_SUFIX)) {
				return false;
			}
			return true;
		}
		// Hide forbidden categories.
		if (!isOtherRealmTrainingSpellsAllowed() && category.getName().equals(Spanish.OTHER_REALM_TRAINING_LISTS)) {
			return false;
		}
		// Expensive categories are useless.
		if (getNewRankCost(category, 0, 0) > Config.getCategoryMaxCost()) {
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
		// Skill tags are not interesting
		if (skill.getName().toLowerCase().equals(Spanish.WEAPON) || skill.getName().toLowerCase().equals(Spanish.ARMOUR)
				|| skill.getName().toLowerCase().equals(Spanish.CULTURE_SPELLS)) {
			return false;
		}
		// No ranks and no high bonus, not interesting
		if ((getTotalRanks(skill) > 0) || (getBonus(skill) > 0)) {
			return true;
		}
		return false;
	}

	public List<Category> getSpellsCategories() {
		List<Category> spellCategories = new ArrayList<>();
		for (String categoryName : CategoryFactory.getAvailableCategories()) {
			Category category = getCategory(categoryName);
			if (category.getCategoryGroup().equals(CategoryGroup.SPELL)) {
				spellCategories.add(category);
			}
		}
		return spellCategories;
	}

	/**
	 * Some categories depend on the profession of the character (as spell
	 * lists).
	 * 
	 * @param category
	 * @return
	 */
	public Category getCategory(Category category) {
		if (isCharacteristicsConfirmed() && category.getCategoryGroup().equals(CategoryGroup.SPELL)) {
			return getMagicSpellLists().getMagicCategory(category.getName());
		}
		return category;
	}

	public Category getCategory(String categoryName) {
		Category category = CategoryFactory.getCategory(categoryName);
		if (category != null) {
			return getCategory(category);
		}
		return null;
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
		if (getProfessionDecisions().isWeaponCostDecided()) {
			// Add costs for firearms
			if (firearmsAllowed) {
				for (Category category : CategoryFactory.getWeaponsCategories()) {
					if (getProfessionDecisions().getWeaponCost(category) == null) {
						getProfessionDecisions().setWeaponCost(category, getFirstWeaponCostNotSelected());
					}
				}
			} else {
				for (Category category : CategoryFactory.getWeaponsCategories()) {
					if (category.getName().contains(Spanish.FIREARMS_SUFIX)) {
						// Remove costs for firearms
						getProfessionDecisions().resetWeaponCost(category);
						// Remove ranks
						getCurrentLevel().setCategoryRanks(category, 0);
						for (Skill skill : category.getSkills()) {
							getCurrentLevel().setSkillsRanks(skill, 0);
						}
					}
				}
			}
		}
	}

	public boolean isChiPowersAllowed() {
		return chiPowersAllowed;
	}

	public void setChiPowersAllowed(boolean chiPowersAllowed) {
		this.chiPowersAllowed = chiPowersAllowed;
	}

	public boolean isOtherRealmTrainingSpellsAllowed() {
		return otherRealmtrainingSpellsAllowed;
	}

	public void setOtherRealmTrainingSpellsAllowed(boolean otherRealmtrainingSpells) {
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
		characterPlayerHelper.resetSkillGeneralBonus(skill.getName());
	}

	public void setHistoryPoints(Category category, boolean value) {
		historial.setPoint(category, value);
		characterPlayerHelper.resetCategoryGeneralBonus(category.getName());
	}

	public boolean isHistoryPointSelected(Category category) {
		return historial.isHistorialPointSelected(category);
	}

	public boolean isHistoryPointSelected(Skill skill) {
		return historial.isHistorialPointSelected(skill);
	}

	public Integer getRemainingHistorialPoints() {
		return getRace().getHistorialPoints() - historial.getSpentHistoryPoints() - getPerksHistoryPointsCost();
	}

	private int getPerksHistoryPointsCost() {
		if (!perksCostHistoryPoints) {
			return 0;
		}
		return PerkFactory.getPerksHistoryCost(selectedPerks);
	}

	public CharacteristicRoll setCharacteristicHistorialUpdate(CharacteristicsAbbreviature abbreviature) {
		Roll roll = getStoredCharacteristicRoll(abbreviature);
		CharacteristicRoll characteristicRoll = historial.addCharactersiticUpdate(abbreviature,
				getCharacteristicTemporalValue(abbreviature), getCharacteristicPotentialValue(abbreviature), roll);
		characterPlayerHelper.resetAllCategoryCharacteristicsBonus();
		characterPlayerHelper.resetDelvelopmentPoints();
		characterPlayerHelper.resetCharacteristicTemporalValues();
		return characteristicRoll;
	}

	public void importLevel(LevelUp level) {
		if (level != null) {
			levelUps.add(level);
			characterPlayerHelper.resetAll();
		}
	}

	public CharacteristicRoll addNewCharacteristicTrainingUpdate(CharacteristicsAbbreviature abbreviature, String trainingName) {
		Roll roll = getStoredCharacteristicRoll(abbreviature);
		CharacteristicRoll characteristicRoll = getTrainingDecision(trainingName).addCharactersiticUpdate(abbreviature,
				getCharacteristicTemporalValue(abbreviature), getCharacteristicPotentialValue(abbreviature), roll);
		characterPlayerHelper.resetAllCategoryCharacteristicsBonus();
		characterPlayerHelper.resetDelvelopmentPoints();
		characterPlayerHelper.resetCharacteristicTemporalValues();
		return characteristicRoll;
	}

	public void addPerk(Perk perk, Perk weakness) {
		if (!isPerkChoosed(perk)) {
			SelectedPerk selectedPerk = new SelectedPerk(perk);
			if (weakness != null) {
				selectedPerk.setWeakness(new SelectedPerk(weakness));
			}
			selectedPerks.add(selectedPerk);
			characterPlayerHelper.resetAll();
		}
	}

	public void removePerk(Perk perk) {
		SelectedPerk perkToRemove = null;
		for (SelectedPerk selectedPerk : selectedPerks) {
			if (selectedPerk.getName().equals(perk.getName()) && selectedPerk.getCost().equals(perk.getCost())) {
				perkToRemove = selectedPerk;
				break;
			}
		}
		if (perkToRemove != null) {
			selectedPerks.remove(perkToRemove);
			characterPlayerHelper.resetAllCategoryGeneralBonus();
			characterPlayerHelper.resetAllSkillGeneralBonus();
		}
	}

	public boolean isPerkChoosed(Perk perk) {
		for (SelectedPerk selectedPerk : getRealSelectedPerks()) {
			if (selectedPerk.getName().equals(perk.getName()) && selectedPerk.getCost().equals(perk.getCost())) {
				return true;
			}
			// Check not repeat weakness with selected perks.
			if (selectedPerk.getWeakness() != null && selectedPerk.getWeakness().getName().equals(perk.getName())
					&& selectedPerk.getWeakness().getCost().equals(perk.getCost())) {
				return true;
			}
		}
		return false;
	}

	public Set<SelectedPerk> getRandomWeakness() {
		Set<SelectedPerk> randomWeakness = new HashSet<>();
		for (SelectedPerk selectedPerk : getRealSelectedPerks()) {
			if (selectedPerk.getWeakness() != null) {
				randomWeakness.add(selectedPerk.getWeakness());
			}
		}
		return randomWeakness;
	}

	private Integer getSpentPerksPoints() {
		Integer total = 0;
		for (SelectedPerk perk : getRealSelectedPerks()) {
			total += perk.getCost();
		}
		return total;
	}

	public Integer getRemainingPerksPoints() throws InvalidRaceDefinition {
		return getRace().getPerksPoints() - getSpentPerksPoints();
	}

	public void setPerkBonusDecision(Perk perk, Set<String> chosenOptions) {
		if (chosenOptions != null && chosenOptions.size() > 0) {
			PerkDecision perkDecision = perkDecisions.get(perk.getName());
			if (perkDecision == null) {
				perkDecision = new PerkDecision();
			}
			// Is the list a category list?
			if (perk.isCategorySelectable(chosenOptions.iterator().next())) {
				perkDecision.setCategoriesBonusChosen(chosenOptions);
			}
			// Is the list a skill list?
			if (perk.isSkillSelectable(chosenOptions.iterator().next())) {
				perkDecision.setSkillsBonusChosen(chosenOptions);
			}
			perkDecisions.put(perk.getName(), perkDecision);
		}
	}

	public void setPerkRankDecision(Perk perk, Set<String> chosenOptions) {
		if (chosenOptions != null && chosenOptions.size() > 0) {
			PerkDecision perkDecision = perkDecisions.get(perk.getName());
			if (perkDecision == null) {
				perkDecision = new PerkDecision();
			}
			// Is the list a category list?
			Category category = CategoryFactory.getCategory((chosenOptions.iterator().next()));
			if (category != null && category.getCategoryType().equals(CategoryType.STANDARD)) {
				perkDecision.setCategoriesRanksChosen(chosenOptions);
			} else {
				perkDecision.setSkillsRanksChosen(chosenOptions);
			}
			perkDecisions.put(perk.getName(), perkDecision);
		}
	}

	public void setPerkCommonDecision(Perk perk, Set<String> commonSkillsChosen) {
		if (commonSkillsChosen != null && commonSkillsChosen.size() > 0) {
			PerkDecision perkDecision = perkDecisions.get(perk.getName());
			if (perkDecision == null) {
				perkDecision = new PerkDecision();
				perkDecisions.put(perk.getName(), perkDecision);
			}
			perkDecision.setCommonSkillsChosen(commonSkillsChosen);
		}
	}

	public void setTrainingCommonDecision(Training training, Set<String> commonSkillsChosen) {
		if (commonSkillsChosen != null && commonSkillsChosen.size() > 0) {
			TrainingDecision trainingDecision = getTrainingDecision(training.getName());
			if (trainingDecision != null) {
				trainingDecision.setCommonSkillsChosen(commonSkillsChosen);
			}
		}
	}

	public void setTrainingProfessionalDecision(Training training, Set<String> professionalSkillsChosen) {
		if (professionalSkillsChosen != null && professionalSkillsChosen.size() > 0) {
			TrainingDecision trainingDecision = getTrainingDecision(training.getName());
			if (trainingDecision != null) {
				trainingDecision.setProfessionalSkillsChosen(professionalSkillsChosen);
			}
		}
	}

	public void setTrainingRestrictedDecision(Training training, Set<String> restrictedSkillsChosen) {
		if (restrictedSkillsChosen != null && restrictedSkillsChosen.size() > 0) {
			TrainingDecision trainingDecision = getTrainingDecision(training.getName());
			if (trainingDecision != null) {
				trainingDecision.setProfessionalSkillsChosen(restrictedSkillsChosen);
			}
		}
	}

	public boolean isProfessional(Skill skill) {
		return skill.getSkillType().equals(SkillType.PROFESSIONAL) || getProfession().isProfessional(skill)
				|| professionDecisions.isProfessional(skill) || isProfessionalByTraining(skill);
	}

	public boolean isProfessionalByTraining(Skill skill) {
		for (String trainingName : getTrainings()) {
			Training training = TrainingFactory.getTraining(trainingName);
			for (ChooseSkillGroup skills : training.getProfessionalSkills()) {
				if (skills.getOptionsAsString().size() == 1 && skills.getOptionsAsString().get(0).equals(skill.getName())) {
					return true;
				}
			}
			TrainingDecision trainingDecision = getTrainingDecision(trainingName);
			if (trainingDecision.getProfessionalSkillsChosen().contains(skill.getName())) {
				return true;
			}
		}
		return false;
	}

	public boolean isRestricted(Skill skill) {
		return skill.getSkillType().equals(SkillType.RESTRICTED) || isRestrictedByPerk(skill) || getProfession().isRestricted(skill)
				|| professionDecisions.isRestricted(skill) || getRace().isRestricted(skill) || isRestrictedByTraining(skill);
	}

	public boolean isRestrictedByTraining(Skill skill) {
		for (String trainingName : getTrainings()) {
			Training training = TrainingFactory.getTraining(trainingName);
			for (ChooseSkillGroup skills : training.getRestrictedSkills()) {
				if (skills.getOptionsAsString().size() == 1 && skills.getOptionsAsString().get(0).equals(skill.getName())) {
					return true;
				}
			}
			TrainingDecision trainingDecision = getTrainingDecision(trainingName);
			if (trainingDecision.getRestrictedSkillsChosen().contains(skill.getName())) {
				return true;
			}
		}
		return false;
	}

	public boolean isCommon(Skill skill) {
		return skill.getSkillType().equals(SkillType.COMMON) || isCommonByPerk(skill) || getProfession().isCommon(skill)
				|| professionDecisions.isCommon(skill) || getRace().isCommon(skill) || isCommonByTraining(skill);
	}

	public boolean isCommonByTraining(Skill skill) {
		for (String trainingName : getTrainings()) {
			Training training = TrainingFactory.getTraining(trainingName);
			for (ChooseSkillGroup skills : training.getCommonSkills()) {
				if (skills.getOptionsAsString().size() == 1 && skills.getOptionsAsString().get(0).equals(skill.getName())) {
					return true;
				}
			}
			TrainingDecision trainingDecision = getTrainingDecision(trainingName);
			if (trainingDecision.getCommonSkillsChosen().contains(skill.getName())) {
				return true;
			}
		}
		return false;
	}

	private boolean isCommonByPerk(Skill skill) {
		for (SelectedPerk perk : getRealSelectedPerks()) {
			if (PerkFactory.getPerk(perk).isCommon(skill)) {
				return true;
			}
			PerkDecision decision = perkDecisions.get(perk.getName());
			if (decision != null) {
				if (decision.isCommon(skill)) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean isRestrictedByPerk(Skill skill) {
		for (SelectedPerk perk : getRealSelectedPerks()) {
			if (PerkFactory.getPerk(perk).isRestricted(skill)) {
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

	public List<String> getCommonSkillsChoseFromProfession() {
		return professionDecisions.getCommonSkillsChosen();
	}

	public List<String> getRestrictedSkillsChoseFromProfession() {
		return professionDecisions.getRestrictedSkillsChosen();
	}

	public List<String> getProfessionalSkillsChoseFromProfession() {
		return professionDecisions.getProfessionalSkillsChosen();
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
			if (areCharacteristicsConfirmed() && getTrainingCost(trainingName) > getRemainingDevelopmentPoints()) {
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
		selectedTrainings.addAll(insertedData.getTrainings());
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
				if (getCharacteristicTemporalValue(characteristic.getAbbreviature()) >= training.getCharacteristicRequirements().get(
						characteristic.getAbbreviature())) {
					costModification += training.getCharacteristicRequirementsCostModification().get(characteristic.getAbbreviature());
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

	public boolean addTraining(String trainingName) {
		if (levelUps.size() > 0) {
			if (!getTrainings().contains(trainingName)) {
				getCurrentLevel().addTraining(trainingName);
				characterPlayerHelper.resetAll();
				// Force to recalculate spell lists.
				magicSpellListsObtained = false;
				return true;
			}
		}
		return false;
	}

	/**
	 * The user must to choose one category from a training if the training has
	 * this option and it has not been chosen before.
	 * 
	 * @param trainingName
	 * @return
	 */
	public boolean needToChooseOneCategory(Training training, TrainingCategory trainingCategory) {
		if (trainingCategory.needToChooseOneCategory()) {
			TrainingDecision trainingDecision = getTrainingDecision(training.getName());
			if (trainingDecision == null
					|| trainingDecision.getSelectedCategory(training.getTrainingCategoryIndex(trainingCategory)).isEmpty()) {
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

	public Map<CharacteristicsAbbreviature, Integer> getCharacteristicsInitialTemporalValues() {
		return characteristicsInitialTemporalValues;
	}

	public void setCharacteristicsInitialTemporalValues(Map<CharacteristicsAbbreviature, Integer> characteristicsInitialTemporalValues) {
		this.characteristicsInitialTemporalValues = characteristicsInitialTemporalValues;
	}

	public Map<CharacteristicsAbbreviature, Integer> getCharacteristicsPotentialValues() {
		return characteristicsPotentialValues;
	}

	public void setCharacteristicsPotentialValues(Map<CharacteristicsAbbreviature, Integer> characteristicsPotentialValues) {
		this.characteristicsPotentialValues = characteristicsPotentialValues;
	}

	public Map<CharacteristicsAbbreviature, RollGroup> getCharacteristicsTemporalUpdatesRolls() {
		return characteristicsTemporalUpdatesRolls;
	}

	public boolean isCharacteristicsConfirmed() {
		return characteristicsConfirmed;
	}

	public void setCharacteristicsConfirmed(boolean characteristicsConfirmed) {
		this.characteristicsConfirmed = characteristicsConfirmed;
		characterPlayerHelper.resetAll();
	}

	private MagicSpellLists getMagicSpellLists() {
		if (magicSpellLists == null) {
			magicSpellLists = new MagicSpellLists();
		}
		if (isCharacteristicsConfirmed() && !magicSpellListsObtained && getProfession() != null && getRealmOfMagic() != null) {
			try {
				magicSpellLists.orderSpellListsByCategory(this);
				magicSpellListsObtained = true;
			} catch (MagicDefinitionException | InvalidProfessionException e) {
				EsherLog.errorMessage(this.getClass().getName(), e);
				magicSpellLists = new MagicSpellLists();
			}
		}
		return magicSpellLists;
	}

	// protected void setMagicSpellLists(MagicSpellLists magicSpellLists) {
	// this.magicSpellLists = magicSpellLists;
	// }

	private TrainingDecision getTrainingDecision(String trainingName) {
		for (int i = 0; i < getLevelUps().size(); i++) {
			TrainingDecision trainingDecision = getTrainingDecisions(i).get(trainingName);
			if (trainingDecision != null) {
				return trainingDecision;
			}
		}
		TrainingDecision trainingDecision = insertedData.getTrainingDecisions().get(trainingName);
		if (trainingDecision != null) {
			return trainingDecision;
		}
		trainingDecision = new TrainingDecision();
		// Create trainingDecision for this level if it is not inserted.
		if (!insertedData.getTrainings().contains(trainingName)) {
			getCurrentLevel().getTrainingDecisions().put(trainingName, trainingDecision);
		} else {
			insertedData.getTrainingDecisions().put(trainingName, trainingDecision);
		}
		return trainingDecision;
	}

	public void addTrainingSelectedCategory(Training training, TrainingCategory trainingCategory, String categoryName) {
		getTrainingDecision(training.getName()).addSelectedCategory(training.getTrainingCategoryIndex(trainingCategory), categoryName);
		characterPlayerHelper.resetCategoryRanks(categoryName);
	}

	public void addTrainingSkillRanks(Training training, TrainingCategory trainingCategory, String selectedSkill, int ranks) {
		getTrainingDecision(training.getName()).setSkillRank(training.getTrainingCategoryIndex(trainingCategory), selectedSkill, ranks);
		characterPlayerHelper.resetSkillRanks(selectedSkill);
	}

	public void removeTrainingSkill(Training training, TrainingCategory trainingCategory) {
		getTrainingDecision(training.getName()).removeSkillsSelected(training.getTrainingCategoryIndex(trainingCategory));
		characterPlayerHelper.resetAllSkillRanks();
	}

	public boolean clearTrainingSkillRanks(String trainingName) {
		if (getTrainingDecision(trainingName) == null) {
			return false;
		}
		getTrainingDecision(trainingName).clearSkillRanks();
		characterPlayerHelper.resetAllSkillRanks();
		return true;
	}

	public int getTrainingSkillRanks(Training training, TrainingCategory trainingCategory, String trainingSkill) {
		return getTrainingDecision(training.getName()).getSkillRank(training.getTrainingCategoryIndex(trainingCategory), trainingSkill);
	}

	public void addTrainingEquipment(Training training, int trainingObjectIndex) {
		getTrainingDecision(training.getName()).getEquipment().add(training.getObjects().get(trainingObjectIndex));
	}

	public List<TrainingItem> getTrainingEquipment(String trainingName) {
		return getTrainingDecision(trainingName).getEquipment();
	}

	public List<CharacteristicRoll> getTrainingCharacteristicsUpdates(String trainingName) {
		return Collections.unmodifiableList(getTrainingDecision(trainingName).getCharacteristicsUpdates());
	}

	public Map<String, TrainingDecision> getTrainingDecisions(int level) {
		if (level >= 0 && level < getLevelUps().size()) {
			return getLevelUps().get(level).getTrainingDecisions();
		}
		return new HashMap<String, TrainingDecision>();
	}

	public Map<String, TrainingDecision> getTrainingDecisions() {
		Map<String, TrainingDecision> trainingDecisions = new HashMap<>();
		for (int i = 0; i < getLevelUps().size(); i++) {
			trainingDecisions.putAll(getTrainingDecisions(i));
		}
		return trainingDecisions;
	}

	private Map<String, TrainingDecision> getTrainingDecisionsFromLevel(int level) {
		Map<String, TrainingDecision> trainingDecisions = new HashMap<>();
		for (int i = level; i < getLevelUps().size(); i++) {
			trainingDecisions.putAll(getTrainingDecisions(i));
		}
		return trainingDecisions;
	}

	public Historial getHistorial() {
		return historial;
	}

	protected void setHistorial(Historial historial) {
		this.historial = historial;
	}

	public List<Perk> getPerks() {
		List<Perk> perks = new ArrayList<>();
		for (SelectedPerk selectedPerk : selectedPerks) {
			perks.add(PerkFactory.getPerk(selectedPerk));
		}
		return perks;
	}

	public List<Perk> getWeakness() {
		List<Perk> weakness = new ArrayList<>();
		for (SelectedPerk selectedPerk : selectedPerks) {
			if (selectedPerk.getWeakness() != null)
				weakness.add(PerkFactory.getPerk(selectedPerk.getWeakness()));
		}
		return weakness;
	}

	protected void setPerks(List<Perk> perks) {
		selectedPerks.clear();
		for (Perk perk : perks) {
			selectedPerks.add(new SelectedPerk(perk));
		}
	}

	public Map<String, PerkDecision> getPerkDecisions() {
		return perkDecisions;
	}

	protected void setPerkDecisions(Map<String, PerkDecision> perkDecisions) {
		this.perkDecisions = perkDecisions;
	}

	public List<LevelUp> getLevelUps() {
		return Collections.unmodifiableList(levelUps);
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

	protected void setAppearance(Appearance appearance) {
		this.appearance = appearance;
	}

	public boolean hasCommonOrProfessionalSkills(Category category) {
		for (Skill skill : category.getSkills()) {
			if ((isCommon(skill) || isProfessional(skill)) && !isRestricted(skill)) {
				return true;
			}
		}
		return false;
	}

	public boolean isSkillSpecialized(Skill skill) {
		// Add inserted specialities.
		if (!insertedData.getSkillSpecializations(skill).isEmpty()) {
			return true;
		}
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
		// Add inserted specialities.
		specialities.addAll(insertedData.getSkillSpecializations(skill));
		return specialities;
	}

	public void addSkillSpecialization(Skill skill, String specialization) {
		if (!getSkillSpecializations(skill).contains(specialization)) {
			removeGeneralized(skill);
			getCurrentLevel().addSkillSpecialization(specialization);
			characterPlayerHelper.resetSkillRanks(skill.getName());
		}
	}

	public void setSkillSpecialization(Skill skill, List<String> specializations) {
		removeSpecialized(skill);
		for (String specialization : specializations) {
			addSkillSpecialization(skill, specialization);
		}
	}

	public void addGeneralized(Skill skill) {
		removeSpecialized(skill);

		if (!isGeneralized(skill)) {
			getCurrentLevel().getGeneralizedSkills().add(skill.getName());
			characterPlayerHelper.resetSkillRanks(skill.getName());
		}
	}

	public boolean isGeneralized(Skill skill) {
		for (LevelUp level : getLevelUps()) {
			if (level.getGeneralizedSkills().contains(skill.getName())) {
				return true;
			}
		}
		return false;
	}

	public void removeGeneralized(Skill skill) {
		for (LevelUp level : getLevelUps()) {
			level.getGeneralizedSkills().remove(skill.getName());
		}
		characterPlayerHelper.resetSkillRanks(skill.getName());
	}

	public void removeSpecialized(Skill skill) {
		for (LevelUp level : getLevelUps()) {
			for (String specialization : skill.getSpecialities()) {
				level.getSkillSpecializations().remove(specialization);
			}
		}
		characterPlayerHelper.resetSkillRanks(skill.getName());
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
		LevelUp levelUp = new LevelUp();
		// Calculate characteristics modifications.
		for (Characteristic characteristic : Characteristics.getCharacteristics()) {
			Roll roll = getStoredCharacteristicRoll(characteristic.getAbbreviature());
			roll.resetComparationIds();
			levelUp.addCharactersiticUpdate(characteristic.getAbbreviature(),
					getCharacteristicTemporalValue(characteristic.getAbbreviature()),
					getCharacteristicPotentialValue(characteristic.getAbbreviature()), roll);
		}

		// Copy favorite skills from previous level to new one.
		if (getCurrentLevel() != null) {
			levelUp.setFavouriteSkills(new HashSet<>(getCurrentLevel().getFavouriteSkills()));
		}

		levelUps.add(levelUp);
		// Reset id to force to be saved the character as a new record.
		// resetIds();
		characterPlayerHelper.resetAll();
	}

	public void removeTraining(Training training) {
		if (training != null) {
			getCurrentLevel().removeTraining(training.getName());
		}
	}

	public Map<CharacteristicsAbbreviature, Integer> getCharacteristicInitialTemporalValue() {
		return characteristicsInitialTemporalValues;
	}

	public Map<CharacteristicsAbbreviature, Integer> getCharacteristicPotencialValue() {
		return characteristicsPotentialValues;
	}

	/**
	 * A list of all non-magic equipment obtained from trainings, history,...
	 * 
	 * @return
	 */
	public List<String> getEquipment() {
		// TODO
		return new ArrayList<>();
	}

	/**
	 * A list of all magic equipment obtained from training, history,...
	 * 
	 * @return
	 */
	public List<MagicObject> getMagicItems() {
		List<MagicObject> magicItems = new ArrayList<>();
		for (TrainingDecision trainingDecision : getTrainingDecisions().values()) {
			magicItems.addAll(trainingDecision.getMagicItems());
		}
		magicItems.addAll(this.magicItems);
		return Collections.unmodifiableList(magicItems);
	}

	public void addMagicItem(MagicObject magicObject) {
		if (magicObject != null) {
			updateMagicItemHelper(magicObject);
			EsherLog.info(MagicObject.class.getName(), "Added magic item '" + magicObject.getName() + "'.");
			magicItems.add(magicObject);
		}
	}

	public void updateMagicItemHelper(MagicObject magicObject) {
		if (magicObject != null) {
			for (ObjectBonus objectBonus : magicObject.getBonus()) {
				if (objectBonus.getType().equals(BonusType.CATEGORY)) {
					characterPlayerHelper.resetCategoryObjectBonus(objectBonus.getBonusName());
				} else if (objectBonus.getType().equals(BonusType.SKILL)) {
					characterPlayerHelper.resetSkillObjectBonus(objectBonus.getBonusName());
				}
			}
		}
	}

	public void removeMagicItem(MagicObject magicObject) {
		if (magicObject != null) {
			EsherLog.info(MagicObject.class.getName(), "Removing magic item '" + magicObject.getName() + "'.");
			magicItems.remove(magicObject);
		}
	}

	public void addMagicItem(MagicObject magicObject, String trainingName) {
		for (ObjectBonus objectBonus : magicObject.getBonus()) {
			if (objectBonus.getType().equals(BonusType.CATEGORY)) {
				characterPlayerHelper.resetCategoryObjectBonus(objectBonus.getBonusName());
			} else if (objectBonus.getType().equals(BonusType.SKILL)) {
				characterPlayerHelper.resetSkillObjectBonus(objectBonus.getBonusName());
			}
		}
		TrainingDecision trainingDecision = getTrainingDecision(trainingName);
		EsherLog.info(MagicObject.class.getName(), "Added magic item '" + magicObject.getName() + "' of '" + trainingName + "'.");
		trainingDecision.getMagicItems().add(magicObject);
	}

	public int getItemBonus(Category category) {
		int max = 0;
		for (MagicObject magicObject : getMagicItems()) {
			int value = magicObject.getCategoryBonus(category.getName());
			if (value > max) {
				max = value;
			}
		}
		return max;
	}

	public int getItemBonus(Skill skill) {
		int max = 0;
		for (MagicObject magicObject : getMagicItems()) {
			int value = magicObject.getSkillBonus(skill.getName());
			if (value > max) {
				max = value;
			}
		}
		return max;
	}

	public int getItemBonus(BonusType type) {
		int max = 0;
		for (MagicObject magicObject : getMagicItems()) {
			int value = magicObject.getObjectBonus(type);
			if (value > max) {
				max = value;
			}
		}
		return max;
	}

	/**
	 * Gets the PPD progression for all types of wizards.
	 * 
	 * @return
	 */
	public List<Float> getPowerPointsDevelopmentCost() {
		List<Float> calculatedPpds = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			float value = 0;
			for (RealmOfMagic realm : getRealmOfMagic().getRealmsOfMagic()) {
				ProgressionCostType progressionValue = ProgressionCostType.getProgressionCostType(realm);
				List<Float> ppdOfRealm = getRace().getProgressionRankValues(progressionValue);
				value += ppdOfRealm.get(i);
			}
			calculatedPpds.add(value / getRealmOfMagic().getRealmsOfMagic().size());
		}
		return calculatedPpds;
	}

	public int getMovementCapacity() {
		return (15 + getCharacteristicTotalBonus(CharacteristicsAbbreviature.SPEED) + getPerkMovementBonus());
	}

	public int getPerkMovementBonus() {
		int total = 0;
		for (Perk perk : getPerks()) {
			total += perk.getMovementBonus();
		}
		return total;
	}

	/**
	 * Obtains the number of Power Points.
	 * 
	 * @return
	 */
	public int getPowerPoints() {
		return getTotalValue(SkillFactory.getSkill(Spanish.POWER_POINTS_DEVELOPMENT_SKILL));
	}

	public String getVersion() {
		return version;
	}

	/**
	 * Convert special tags as 'weapon' or 'armor' to real skills names
	 * depending on the culture possibilities.
	 * 
	 * @return
	 */
	public List<String> getRealHobbySkills() {
		List<String> realSkills = new ArrayList<>();
		for (String skill : getCulture().getHobbySkills()) {
			if (skill.toLowerCase().equals(Spanish.WEAPON)) {
				for (Weapon weapon : getCulture().getCultureWeapons()) {
					realSkills.add(weapon.getName());
				}
			} else if (skill.toLowerCase().equals(Spanish.ARMOUR)) {
				for (String armour : getCulture().getCultureArmours()) {
					realSkills.add(armour);
				}
			} else if (skill.toLowerCase().equals(Spanish.CULTURE_SPELLS)) {
				List<String> spells = new ArrayList<>();
				// Add open lists.
				for (Skill spell : getCategory(CategoryFactory.getCategory(Spanish.OPEN_LISTS)).getSkills()) {
					// addHobbyLine(spell.getName());
					spells.add(spell.getName());
				}
				// Add race lists. Note than spell casters has race lists as
				// basic and not as open. Therefore are not included in the
				// previous step.
				for (String spell : MagicFactory.getRaceLists(getRace().getName())) {
					// avoid to add a race list two times.
					if (!spells.contains(spell)) {
						spells.add(spell);
					}
				}
				// Create line.
				for (String spell : spells) {
					realSkills.add(spell);
				}
			} else {
				realSkills.add(skill);
			}
		}
		return realSkills;
	}

	public List<String> getTrainings() {
		List<String> trainings = new ArrayList<>();
		for (LevelUp level : levelUps) {
			trainings.addAll(level.getTrainings());
		}
		return trainings;
	}

	public int getArmourClass() {
		int armourClass = 1;
		for (Perk perk : getPerks()) {
			if (perk.getArmourClass() > armourClass) {
				armourClass = perk.getArmourClass();
			}
		}
		return armourClass;
	}

	/**
	 * Some skills are disabled until other skill is learned. I.e. Chi Powers.
	 * 
	 * @param skill
	 * @return
	 */
	public boolean isSkillDisabled(Skill skill) {
		// Chi powers can ben forbidden as an option.
		if (skill.getName().toLowerCase().startsWith(Spanish.CHI_SUFIX)) {
			if (!chiPowersAllowed) {
				return false;
			}
		}
		// Some skills enable other skills that are disabled by default.
		return !skill.isEnabled() && !getEnabledSkills().contains(skill.getName());
	}

	private Set<String> getEnabledSkills() {
		return new HashSet<String>(enabledSkill.values());
	}

	public void enableSkillOption(Skill skill, Skill disabledSkill) {
		if (skill != null && disabledSkill != null) {
			enableSkillOption(skill.getName(), disabledSkill.getName());
		}
	}

	public void enableSkillOption(String skill, String disabledSkill) {
		if (skill != null && disabledSkill != null) {
			enabledSkill.put(skill, disabledSkill);
		}
	}

	public void disableSkillOption(Skill skill) {
		if (skill != null) {
			disableSkillOption(skill.getName());
		}
	}

	public void disableSkillOption(String skill) {
		if (skill != null) {
			enabledSkill.remove(skill);
		}
	}

	public List<Skill> getSkillsOrderByValue(List<Skill> skills) {
		Collections.sort(skills, new SkillComparatorByValue(this));
		return skills;
	}

	public List<Skill> getSkillsFromCategoriesOrderByValue(List<Category> categories) {
		List<Skill> skills = new ArrayList<>();
		for (Category category : categories) {
			skills.addAll(category.getSkills());
		}
		return getSkillsOrderByValue(skills);
	}

	public String getSkillNameWithSufix(Skill skill) {
		String skillName = skill.getName();

		if (isGeneralized(skill)) {
			skillName += " " + SkillType.GENERALIZED.getTag();
		}
		if (isProfessional(skill)) {
			skillName += " " + SkillType.PROFESSIONAL.getTag();
		} else if (isCommon(skill)) {
			skillName += " " + SkillType.COMMON.getTag();
		}

		if (isRestricted(skill)) {
			skillName += " " + SkillType.RESTRICTED.getTag();
		}

		return skillName;
	}

	/**
	 * Returns a list of perks mixed with this weakness.
	 * 
	 * @return
	 */
	private List<SelectedPerk> getRealSelectedPerks() {
		List<SelectedPerk> reallySelected = new ArrayList<>();
		for (SelectedPerk selectedPerk : selectedPerks) {
			reallySelected.add(selectedPerk);
			if (selectedPerk.getWeakness() != null) {
				reallySelected.add(selectedPerk.getWeakness());
			}
		}
		return reallySelected;
	}

	/**
	 * Only used for DAO.
	 * 
	 * @return
	 */
	public List<SelectedPerk> getSelectedPerks() {
		return selectedPerks;
	}

	public Map<String, String> getEnabledSkill() {
		return enabledSkill;
	}

	public CultureDecisions getCultureDecisions() {
		return cultureDecisions;
	}

	public int getInsertedLevel() {
		if (insertedData == null) {
			return 0;
		}
		return insertedData.getInsertedLevels();
	}

	public boolean isDirty() {
		if (characterPlayerHelper == null) {
			return true;
		}
		return characterPlayerHelper.isDirty();
	}

	public void setDirty(boolean dirty) {
		if (characterPlayerHelper != null) {
			characterPlayerHelper.setDirty(dirty);
		}
	}

	public Set<String> getFavouriteSkills() {
		return getCurrentLevel().getFavouriteSkills();
	}

	public List<Skill> getFavouriteNoOffensiveSkills() {
		List<Skill> favouriteSkills = new ArrayList<>();
		for (String skillName : getFavouriteSkills()) {
			Skill skill = SkillFactory.getAvailableSkill(skillName);
			if (skill != null
					&& !(skill.getCategory().getCategoryGroup().equals(CategoryGroup.WEAPON)
							|| CategoryFactory.getOthersAttack().contains(skill.getCategory())
							|| skill.getCategory().getName().equals(Spanish.AIMED_SPELLS_CATEGORY) || skill.getName().startsWith(
							Spanish.WEAPONS_RACE))) {
				favouriteSkills.add(skill);
			}
		}
		Collections.sort(favouriteSkills, new SkillComparatorByValue(this));
		// Remove too much skills.
		favouriteSkills = favouriteSkills.subList(0,
				(favouriteSkills.size() < PdfStandardSheet.MOST_USED_SKILLS_LINES * 2 ? favouriteSkills.size()
						: PdfStandardSheet.MOST_USED_SKILLS_LINES * 2));
		// Order by name.
		Collections.sort(favouriteSkills, new SkillComparatorByName());
		return favouriteSkills;
	}

	public List<Skill> getFavouriteOffensiveSkills() {
		List<Skill> favouriteSkills = new ArrayList<>();
		for (String skillName : getFavouriteSkills()) {
			Skill skill = SkillFactory.getAvailableSkill(skillName);
			if (skill != null
					&& (skill.getCategory().getCategoryGroup().equals(CategoryGroup.WEAPON)
							|| CategoryFactory.getOthersAttack().contains(skill.getCategory())
							|| skill.getCategory().getName().equals(Spanish.AIMED_SPELLS_CATEGORY) || skill.getName().startsWith(
							Spanish.WEAPONS_RACE))) {
				favouriteSkills.add(skill);
			}
		}
		Collections.sort(favouriteSkills, new SkillComparatorByValue(this));
		Collections.reverse(favouriteSkills);
		// Weaposn order by value always.
		return favouriteSkills.subList(0, favouriteSkills.size() < PdfStandardSheet.MOST_USED_ATTACKS_LINES ? favouriteSkills.size()
				: PdfStandardSheet.MOST_USED_ATTACKS_LINES);
	}

	public boolean isPerksCostHistoryPoints() {
		return perksCostHistoryPoints;
	}

	public void setPerksCostHistoryPoints(boolean perksCostHistoryPoints) {
		this.perksCostHistoryPoints = perksCostHistoryPoints;
	}

	public int getCharacteristicsTemporalTotalPoints() {
		if (characteristicsTemporalTotalPoints != null) {
			return characteristicsTemporalTotalPoints;
		}
		return Characteristics.TOTAL_CHARACTERISTICS_POINTS;
	}

	public void setCharacteristicsTemporalTotalPoints(int characteristicsTemporalPoints) {
		this.characteristicsTemporalTotalPoints = characteristicsTemporalPoints;
	}

}
