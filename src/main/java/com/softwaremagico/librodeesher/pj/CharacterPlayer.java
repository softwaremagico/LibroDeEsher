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

import com.softwaremagico.librodeesher.core.TwoDices;
import com.softwaremagico.librodeesher.pj.categories.Category;
import com.softwaremagico.librodeesher.pj.characteristic.Characteristic;
import com.softwaremagico.librodeesher.pj.characteristic.Characteristics;
import com.softwaremagico.librodeesher.pj.culture.Culture;
import com.softwaremagico.librodeesher.pj.culture.CultureFactory;
import com.softwaremagico.librodeesher.pj.level.LevelUp;
import com.softwaremagico.librodeesher.pj.magic.RealmOfMagic;
import com.softwaremagico.librodeesher.pj.profession.Profession;
import com.softwaremagico.librodeesher.pj.profession.ProfessionFactory;
import com.softwaremagico.librodeesher.pj.race.Race;
import com.softwaremagico.librodeesher.pj.race.RaceFactory;
import com.softwaremagico.librodeesher.pj.resistance.ResistanceType;
import com.softwaremagico.librodeesher.pj.resistance.Resistances;
import com.softwaremagico.librodeesher.pj.training.Training;

public class CharacterPlayer {

	private final String DEFAULT_NAME = " ** Nuevo Personaje ** ";
	private final Integer STORED_ROLLS = 10;
	private String name;
	private String surname;
	private SexType sex;
	private String history;

	private Characteristics characteristics;
	private Hashtable<String, Integer> characteristicsInitialTemporalValues;
	private Hashtable<String, Integer> characteristicsPotentialValues;
	private Hashtable<String, List<TwoDices>> characteristicsTemporalUpdatesRolls;
	private boolean characteristicsConfirmed = false;
	private List<Category> categories;
	private String raceName;
	private transient Race race;
	private String cultureName;
	private transient Culture culture;
	private String professionName;
	private transient Profession profession;
	private List<Training> trainings;
	private Resistances resistances;
	private RealmOfMagic realmOfMagic;

	private List<LevelUp> levelUps;

	public CharacterPlayer() {
		characteristics = new Characteristics();
		levelUps = new ArrayList<>();
		levelUps.add(new LevelUp());
		characteristicsInitialTemporalValues = new Hashtable<>();
		characteristicsTemporalUpdatesRolls = new Hashtable<>();
		characteristicsPotentialValues = new Hashtable<>();
		setTemporalValuesOfCharacteristics();
		sex = SexType.MALE;
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
		return Characteristics.getTemporalBonus(getCharacteristicTemporalValues(abbreviature));
	}

	public Integer getCharacteristicTemporalValues(String abbreviature) {
		Integer value = characteristicsInitialTemporalValues.get(abbreviature);
		if (value != null) {
			if (isMainProfessionalCharacteristic(abbreviature)) {
				if (value > 90) {
					return value;
				} else {
					return 90;
				}
			}
			return value;
		}
		return 0;
	}

	public Integer getCharacteristicTotalBonus(String abbreviature) {
		return getCharacteristicTemporalBonus(abbreviature) + getCharacteristicRaceBonus(abbreviature)
				+ getCharacteristicSpecialBonus(abbreviature);
	}

	public Integer getCharacterLevel() {
		return levelUps.size();
	}

	public Culture getCulture() {
		if (culture == null || !cultureName.equals(culture.getName())) {
			culture = CultureFactory.getCulture(cultureName);
		}
		return culture;
	}

	public String getCultureName() {
		return cultureName;
	}

	public Integer getDevelopmentPoints() {
		Integer total = 0;
		total += getCharacteristicTemporalValues("Ag");
		total += getCharacteristicTemporalValues("Co");
		total += getCharacteristicTemporalValues("Me");
		total += getCharacteristicTemporalValues("Ra");
		total += getCharacteristicTemporalValues("Ad");
		return total / 5;
	}

	public String getName() {
		if (name != null && name.length() > 0) {
			return name;
		}
		return DEFAULT_NAME;
	}

	public Profession getProfession() {
		if (profession == null || !professionName.equals(profession.getName())) {
			profession = ProfessionFactory.getProfession(professionName);
		}
		return profession;
	}

	public String getProfessionName() {
		return professionName;
	}

	public Race getRace() {
		if (race == null || !raceName.equals(race.getName())) {
			race = RaceFactory.getRace(raceName);
		}
		return race;
	}

	public RealmOfMagic getRealmOfMagic() {
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

	public Integer getSpentDevelopmentPoints() {
		// TODO
		return 0;
	}

	public Integer getRemainingDevelopmentPoints() {
		return getDevelopmentPoints() - getSpentDevelopmentPoints();
	}

	public Integer getTemporalPointsSpent() {
		Integer total = 0;
		for (Characteristic characteristic : characteristics.getCharacteristicsList()) {
			total += getCharacteristicTemporalValues(characteristic.getAbbreviature());
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
	}

	private void setCharacteristicsTemporalUpdatesRolls() {
		for (Characteristic characteristic : characteristics.getCharacteristicsList()) {
			if (characteristicsTemporalUpdatesRolls.get(characteristic.getAbbreviature()) == null) {
				characteristicsTemporalUpdatesRolls.put(characteristic.getAbbreviature(),
						new ArrayList<TwoDices>());
			}
			while (characteristicsTemporalUpdatesRolls.get(characteristic.getAbbreviature()).size() < STORED_ROLLS) {
				characteristicsTemporalUpdatesRolls.get(characteristic.getAbbreviature()).add(new TwoDices());
			}
		}
	}

	public void setCharacteristicTemporalValues(String abbreviature, Integer value) {
		characteristicsInitialTemporalValues.put(abbreviature, value);
	}

	public void setCulture(String cultureName) {
		this.cultureName = cultureName;
	}

	public void setName(String name) {
		if (!name.equals(DEFAULT_NAME)) {
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
		}
	}

	public void setRace(String raceName) {
		this.raceName = raceName;
	}

	public void setRealmOfMagic(RealmOfMagic realmOfMagic) {
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

}
