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

import com.softwaremagico.librodeesher.pj.categories.Category;
import com.softwaremagico.librodeesher.pj.characteristic.Characteristic;
import com.softwaremagico.librodeesher.pj.characteristic.Characteristics;
import com.softwaremagico.librodeesher.pj.culture.Culture;
import com.softwaremagico.librodeesher.pj.culture.CultureFactory;
import com.softwaremagico.librodeesher.pj.level.LevelUp;
import com.softwaremagico.librodeesher.pj.profession.Profession;
import com.softwaremagico.librodeesher.pj.profession.ProfessionFactory;
import com.softwaremagico.librodeesher.pj.race.Race;
import com.softwaremagico.librodeesher.pj.race.RaceFactory;
import com.softwaremagico.librodeesher.pj.resistance.ResistanceType;
import com.softwaremagico.librodeesher.pj.resistance.Resistances;
import com.softwaremagico.librodeesher.pj.training.Training;

public class CharacterPlayer {

	private final String DEFAULT_NAME = " ** Nuevo Personaje ** ";
	private String name;
	private String surname;
	private SexType sex;
	private String history;

	private Characteristics characteristics;
	private Hashtable<String, Integer> characteristicsTemporalValues;
	private List<Category> categories;
	private String raceName;
	private transient Race race;
	private String cultureName;
	private transient Culture culture;
	private String professionName;
	private transient Profession profession;
	private List<Training> trainings;
	private Resistances resistances;

	private List<LevelUp> levelUps;

	public CharacterPlayer() {
		characteristics = new Characteristics();
		levelUps = new ArrayList<>();
		characteristicsTemporalValues = new Hashtable<>();
		initializeTemporalValuesOfCharacteristics();
		sex = SexType.MALE;
	}

	public void setCharacteristicsTemporalValues(String abbreviature, Integer value) {
		characteristicsTemporalValues.put(abbreviature, value);
	}

	private void initializeTemporalValuesOfCharacteristics() {
		for (Characteristic characteristic : characteristics.getCharacteristicsList()) {
			characteristicsTemporalValues.put(characteristic.getAbbreviation(),
					Characteristics.INITIAL_CHARACTERISTIC_VALUE);
		}
	}

	public Integer getCharacteristicsTemporalValues(String abbreviature) {
		Integer value = characteristicsTemporalValues.get(abbreviature);
		if (value != null) {
			return value;
		}
		return 0;
	}

	public boolean isMainProfessionalCharacteristic(Characteristic characteristic) {
		// TODO
		return false;
	}

	public String getName() {
		if (name != null && name.length() > 0) {
			return name;
		}
		return DEFAULT_NAME;
	}

	public void setName(String name) {
		if (!name.equals(DEFAULT_NAME)) {
			this.name = name;
		}
	}

	public List<Characteristic> getCharacteristics() {
		return characteristics.getCharacteristicsList();
	}

	public Integer getSpentDevelopmentPoints() {
		// TODO
		return 0;
	}

	public Integer getDevelopmentPoints() {
		Integer total = 0;
		total += getCharacteristicsTemporalValues("Ag");
		total += getCharacteristicsTemporalValues("Co");
		total += getCharacteristicsTemporalValues("Me");
		total += getCharacteristicsTemporalValues("Ra");
		total += getCharacteristicsTemporalValues("Ad");
		return total / 5;
	}

	public Integer getTemporalPointsSpent() {
		Integer total = 0;
		for (Integer value : characteristicsTemporalValues.values()) {
			total += Characteristic.getTemporalCost(value);
		}
		return total;
	}

	public Integer getCharacterLevel() {
		return levelUps.size();
	}

	public Integer getResistanceBonus(ResistanceType type) {
		return Resistances.getResistanceCharacteristicsBonus(type, characteristics);
	}

	public void setSex(SexType sex) {
		this.sex = sex;
	}

	public SexType getSex() {
		return sex;
	}

	public void setRace(String raceName) {
		this.raceName = raceName;
	}
	
	public void setCulture(String cultureName) {
		this.cultureName = cultureName;
	}
	
	public void setProfession(String professionName) {
		this.professionName = professionName;
	}

	public Race getRace() {
		if (race == null || !raceName.equals(race.getName())) {
			race = RaceFactory.getRace(raceName);
		}
		return race;
	}
	
	public Culture getCulture() {
		if (culture == null || !cultureName.equals(culture.getName())) {
			culture = CultureFactory.getCulture(cultureName);
		}
		return culture;
	}
	
	public Profession getProfession() {
		if (profession == null || !professionName.equals(profession.getName())) {
			profession = ProfessionFactory.getProfession(professionName);
		}
		return profession;
	}

}
