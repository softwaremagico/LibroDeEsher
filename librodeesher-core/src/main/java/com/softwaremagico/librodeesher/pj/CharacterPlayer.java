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
import java.util.Hashtable;
import java.util.List;

import com.softwaremagico.librodeesher.config.Config;
import com.softwaremagico.librodeesher.core.ShowMessage;
import com.softwaremagico.librodeesher.core.Spanish;
import com.softwaremagico.librodeesher.core.TwoDices;
import com.softwaremagico.librodeesher.pj.categories.Category;
import com.softwaremagico.librodeesher.pj.categories.CategoryCost;
import com.softwaremagico.librodeesher.pj.categories.CategoryFactory;
import com.softwaremagico.librodeesher.pj.categories.CategoryGroup;
import com.softwaremagico.librodeesher.pj.characteristic.Characteristic;
import com.softwaremagico.librodeesher.pj.characteristic.Characteristics;
import com.softwaremagico.librodeesher.pj.culture.Culture;
import com.softwaremagico.librodeesher.pj.culture.CultureDecisions;
import com.softwaremagico.librodeesher.pj.culture.CultureFactory;
import com.softwaremagico.librodeesher.pj.level.LevelUp;
import com.softwaremagico.librodeesher.pj.magic.MagicListType;
import com.softwaremagico.librodeesher.pj.magic.MagicSpellLists;
import com.softwaremagico.librodeesher.pj.magic.RealmOfMagic;
import com.softwaremagico.librodeesher.pj.profession.Profession;
import com.softwaremagico.librodeesher.pj.profession.ProfessionDecisions;
import com.softwaremagico.librodeesher.pj.profession.ProfessionFactory;
import com.softwaremagico.librodeesher.pj.profession.ProfessionalRealmsOfMagicOptions;
import com.softwaremagico.librodeesher.pj.race.Race;
import com.softwaremagico.librodeesher.pj.race.RaceDecisions;
import com.softwaremagico.librodeesher.pj.race.RaceFactory;
import com.softwaremagico.librodeesher.pj.resistance.ResistanceType;
import com.softwaremagico.librodeesher.pj.resistance.Resistances;
import com.softwaremagico.librodeesher.pj.skills.Skill;
import com.softwaremagico.librodeesher.pj.skills.SkillFactory;
import com.softwaremagico.librodeesher.pj.skills.SkillGroup;
import com.softwaremagico.librodeesher.pj.training.Training;

public class CharacterPlayer {

	private static final Integer STORED_ROLLS_NUMBER = 10;
	private String name;
	private SexType sex;
	private String historyText;

	private Characteristics characteristics;
	private Hashtable<String, Integer> characteristicsInitialTemporalValues;
	private Hashtable<String, Integer> characteristicsPotentialValues;
	private Hashtable<String, List<TwoDices>> characteristicsTemporalUpdatesRolls;
	private boolean characteristicsConfirmed = false;
	private String raceName;
	private transient Race race;
	private RaceDecisions raceDecisions;
	private String cultureName;
	private transient Culture culture;
	private CultureDecisions cultureDecisions;
	private String professionName;
	private transient Profession profession;
	private ProfessionDecisions professionDecisions;
	private List<String> trainingsNames;
	private transient List<Training> trainings;
	private Resistances resistances;
	private ProfessionalRealmsOfMagicOptions realmOfMagic;
	private MagicSpellLists magicSpellLists;
	private Historial historial;

	private boolean darkSpellsAsBasicListsAllowed = false;
	private boolean firearmsAllowed = false;
	private boolean chiPowersAllowed = false;
	private boolean otherRealmtrainingSpellsAllowed = false;

	private List<LevelUp> levelUps;

	public CharacterPlayer() {
		characteristics = new Characteristics();
		levelUps = new ArrayList<>();
		levelUps.add(new LevelUp());
		historial = new Historial();
		characteristicsInitialTemporalValues = new Hashtable<>();
		characteristicsTemporalUpdatesRolls = new Hashtable<>();
		characteristicsPotentialValues = new Hashtable<>();
		setTemporalValuesOfCharacteristics();
		sex = SexType.MALE;
		cultureDecisions = new CultureDecisions();
		raceDecisions = new RaceDecisions();
		professionDecisions = new ProfessionDecisions();
		trainingsNames = new ArrayList<>();
		trainings = new ArrayList<>();
		setDefaultConfig();
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
	}

	public RaceDecisions getRaceDecisions() {
		return raceDecisions;
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

	public Integer getCharacteristicPotentialValues(String abbreviature) {
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
		return characteristics.getCharacteristicsList();
	}

	public Integer getCharacteristicSpecialBonus(String abbreviature) {
		return 0;
	}

	public Integer getCharacteristicTemporalBonus(String abbreviature) {
		return Characteristics.getTemporalBonus(getCharacteristicTemporalValue(abbreviature));
	}

	private Integer getHistoryTemporalModification(String abbreviature) {
		Integer temporalValue = characteristicsInitialTemporalValues.get(abbreviature);
		Integer potentialValue = characteristicsPotentialValues.get(abbreviature);
		for (TwoDices roll : historial.getCharacteristicsUpdates(abbreviature)) {
			temporalValue += Characteristic.getCharacteristicUpgrade(temporalValue, potentialValue, roll);
		}
		return temporalValue;
	}

	public Integer getCharacteristicTemporalValue(String abbreviature) {
		return getHistoryTemporalModification(abbreviature);
	}

	public Integer getCharacteristicInitialTemporalValue(String abbreviature) {
		Integer value = getHistoryTemporalModification(abbreviature);
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
		if (abbreviature.toLowerCase().contains("ningu")) {
			return 0;
		}
		if (abbreviature.toLowerCase().contains("*")) {
			return getBonusCharacteristicOfRealmOfMagic();
		}
		return getCharacteristicTemporalBonus(abbreviature) + getCharacteristicRaceBonus(abbreviature)
				+ getCharacteristicSpecialBonus(abbreviature);
	}

	public Integer getCharacterLevel() {
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

	public ProfessionalRealmsOfMagicOptions getProfessionalRealmsOfMagicChoosen() {
		return realmOfMagic;
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
			Category category = CategoryFactory.getAvailableCategory(categoryName);
			Integer ranksUpdatedInLevel = levelUps.get(level).getCategoryRanks(categoryName);
			for (int i = 0; i < ranksUpdatedInLevel; i++) {
				total += getNewRankCost(category, getPreviousRanks(category) + i, i);
			}
		}
		return total;
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

	private Integer getSpentDevelopmentPoints() {
		if (levelUps.size() > 0) {
			return getSpentDevelopmentPointsInCategoryRanks(levelUps.size() - 1)
					+ getSpentDevelopmentPointsInSkillsRanks(levelUps.size() - 1);
		}
		return 0;
	}

	public Integer getRemainingDevelopmentPoints() {
		return getTotalDevelopmentPoints() - getSpentDevelopmentPoints();
	}

	public Integer getCharacteristicsTemporalPointsSpent() {
		Integer total = 0;
		for (Characteristic characteristic : characteristics.getCharacteristicsList()) {
			total += getCharacteristicInitialTemporalValue(characteristic.getAbbreviature());
		}
		return total;
	}

	public boolean isMainProfessionalCharacteristic(Characteristic characteristic) {
		return getProfession().isCharacteristicProfessional(characteristic);
	}

	public boolean isMainProfessionalCharacteristic(String abbreviature) {
		return getProfession().isCharacteristicProfessional(
				characteristics.getCharacteristicFromAbbreviature(abbreviature));
	}

	public void setCharacteristicsAsConfirmed() {
		setPotentialValues();
		characteristicsConfirmed = true;
		magicSpellLists.orderSpellListsByCategory(this);
	}

	private void setCharacteristicsTemporalUpdatesRolls() {
		for (Characteristic characteristic : characteristics.getCharacteristicsList()) {
			if (characteristicsTemporalUpdatesRolls.get(characteristic.getAbbreviature()) == null) {
				characteristicsTemporalUpdatesRolls.put(characteristic.getAbbreviature(),
						new ArrayList<TwoDices>());
			}
			while (characteristicsTemporalUpdatesRolls.get(characteristic.getAbbreviature()).size() < STORED_ROLLS_NUMBER) {
				characteristicsTemporalUpdatesRolls.get(characteristic.getAbbreviature()).add(new TwoDices());
			}
		}
	}

	private TwoDices getStoredCharacteristicRoll(String abbreviature) {
		TwoDices roll = characteristicsTemporalUpdatesRolls.get(abbreviature).remove(0);
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
		for (Characteristic characteristic : characteristics.getCharacteristicsList()) {
			Integer potential = Characteristics.getPotencial(characteristicsInitialTemporalValues
					.get(characteristic.getAbbreviature()));
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
		for (Characteristic characteristic : characteristics.getCharacteristicsList()) {
			characteristicsInitialTemporalValues.put(characteristic.getAbbreviature(),
					Characteristics.INITIAL_CHARACTERISTIC_VALUE);
		}
		setCharacteristicsTemporalUpdatesRolls();
	}

	public Integer getLanguageInitialRanks(Language language) {
		return Math.max(getCulture().getLanguageRank(language), getRace().getLanguageInitialRanks(language));
	}

	public Integer getLanguagesInitialRanks() {
		Integer total = 0;

		return total;
	}

	public Integer getLanguageMaxInitialRanks(Language language) {
		return Math.max(getCulture().getLanguageRank(language), getRace().getLanguageMaxRanks(language));
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

	private LevelUp getCurrentLevel() {
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
		return total;
	}

	public Integer getPreviousRanks(Skill skill) {
		Integer total = 0;
		total += getCulture().getCultureRanks(skill);
		total += getCultureDecisions().getWeaponRanks(skill.getName());
		total += getCultureDecisions().getHobbyRanks(skill.getName());
		total += getPreviousLevelsRanks(skill);
		return total;
	}

	public Integer getTotalRanks(Category category) {
		return getPreviousRanks(category) + getCurrentLevelRanks(category);
	}

	public Integer getTotalRanks(Skill skill) {
		return getPreviousRanks(skill) + getCurrentLevelRanks(skill);
	}

	public Integer getRanksValue(Category category) {
		return category.getRankValue(getTotalRanks(category));
	}

	public Integer getRanksValue(Skill skill) {
		return skill.getRankValue(this, getTotalRanks(skill));
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
				+ historial.getBonus(category);
	}

	public Integer getBonus(Skill skill) {
		return getProfession().getSkillBonus(skill.getName()) + historial.getBonus(skill);
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
			if (category.getGroup().equals(CategoryGroup.WEAPON)) {
				return getProfession().getWeaponCategoryCost().get(0).getMaxRanksPerLevel();
			}
			return getCategoryCost(category, 0).getMaxRanksPerLevel();
		} catch (NullPointerException npe) {
			return 0;
		}
	}

	/**
	 * In culture, weapon cost are not defined. Therefore, we will use the best
	 * value.
	 * 
	 * @param category
	 * @return
	 */
	public Integer getCultureStimatedCategoryCost(Category category) {
		if (category.getGroup().equals(CategoryGroup.WEAPON)) {
			return getProfession().getWeaponCategoryCost().get(0).getRankCost(0);
		} else {
			return getCategoryCost(category, 0).getRankCost(0);
		}
	}

	public CategoryCost getCategoryCost(Category category, Integer currentRanks) {
		if (category.getGroup().equals(CategoryGroup.WEAPON)) {
			return getProfessionDecisions().getWeaponCost(category);
		} else if (category.getGroup().equals(CategoryGroup.SPELL)) {
			return getProfession().getMagicCost(MagicListType.getMagicTypeOfCategory(category.getName()),
					currentRanks);
		} else {
			return getProfession().getCategoryCost(category.getName());
		}
	}

	public Integer getMaxRanksPerLevel(Category category, Integer currentRanks) {
		try {
			// CurrentRanks + 1 to ensure that we do not have more ranks when
			// update spell range cost.
			return getCategoryCost(category, currentRanks + 1).getMaxRanksPerLevel();
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
	 *            ranks and previous ranks included in this new level.
	 * @param rankAdded
	 *            If it is the first, second or third rank added at this level
	 *            [0, 1, 2].
	 * @return
	 */
	public Integer getNewRankCost(Category category, Integer currentRanks, Integer rankAdded) {
		// If you have X currentRanks, the cost of the new one will be
		// currentRanks + 1;
		CategoryCost cost = getCategoryCost(category, currentRanks + 1);
		if (cost == null) {
			return Integer.MAX_VALUE;
		}

		return cost.getRankCost(rankAdded);
	}

	public Integer getNewRankCost(Skill skill, Integer currentRanks, Integer rankAdded) {
		// Spell cost is increased if lots of spells are acquired in one level.
		if (skill.getCategory().getGroup().equals(CategoryGroup.SPELL)) {
			return getNewRankCost(skill.getCategory(), currentRanks, rankAdded)
					* getCurrentLevel().getSpellRankMultiplier(skill);
		} else {
			return getNewRankCost(skill.getCategory(), currentRanks, rankAdded);
		}

	}

	/**
	 * A category is not used if it has not skills or the cost is more than the
	 * selected in the configuration.
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
		if (category.getGroup().equals(CategoryGroup.WEAPON)) {
			// Firearms only if activated
			if (!firearmsAllowed && category.getName().contains(Spanish.FIREARMS_SUFIX)) {
				return false;
			}
			return true;
		}
		// Expensive categories are useless.
		if (getNewRankCost(category, 0, 0) > Config.getCategoryMaxCost()) {
			return false;
		}
		if (category.getName().equals(Spanish.OTHER_REALM_TRAINING_LISTS)
				&& !isOtherRealmtrainingSpellsAllowed()) {
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
		if (skill.getGroup().equals(SkillGroup.CHI) && !isChiPowersAllowed()) {
			return false;
		}
		if (skill.getGroup().equals(SkillGroup.FIREARM) && !isFirearmsAllowed()) {
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
		if ((getTotalRanks(skill) == 0) || getBonus(skill) > 0) {
			return false;
		}
		return true;
	}

	public Category getCategory(Category category) {
		if (category.getGroup().equals(CategoryGroup.SPELL)) {
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
		return null;
	}

	public void setHistoryPoints(Skill skill, boolean value) {
		historial.setPoint(skill, value);
	}

	public void setHistoryPoints(Category category, boolean value) {
		historial.setPoint(category, value);
	}

	public Integer getRemainingHistorialPoints() {
		return getRace().getHistorialPoints() - historial.getSpentHistoryPoints();
	}

	public void setCharacteristicHistorialUpdate(String abbreviature) {
		TwoDices roll = getStoredCharacteristicRoll(abbreviature);
		Integer temporalValue = getCharacteristicTemporalValue(abbreviature);
		Integer potentialValue = characteristicsPotentialValues.get(abbreviature);
		ShowMessage.showInfoMessage(
				"El resultado de los dados es: [" + roll.getFirstDice() + "," + roll.getSecondDice() + "]\n"
						+ "Por tanto, la característica ha cambiado en: "
						+ Characteristic.getCharacteristicUpgrade(temporalValue, potentialValue, roll),
				"Característica aumentada!");

		historial.setCharactersiticUpdate(abbreviature, roll);
	}
	
	public Integer getRemainingPerksPoints(){
		return 0;
	}
}