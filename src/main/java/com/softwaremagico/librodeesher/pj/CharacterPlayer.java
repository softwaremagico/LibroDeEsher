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

import com.softwaremagico.librodeesher.pj.categories.Category;
import com.softwaremagico.librodeesher.pj.characteristic.Characteristic;
import com.softwaremagico.librodeesher.pj.characteristic.Characteristics;
import com.softwaremagico.librodeesher.pj.culture.Culture;
import com.softwaremagico.librodeesher.pj.level.LevelUp;
import com.softwaremagico.librodeesher.pj.profession.Profession;
import com.softwaremagico.librodeesher.pj.resistance.ResistanceType;
import com.softwaremagico.librodeesher.pj.resistance.Resistances;
import com.softwaremagico.librodeesher.pj.training.Training;

import java.util.ArrayList;
import java.util.List;

public class CharacterPlayer {
	private final String DEFAULT_NAME = " ** Nuevo Personaje ** ";
	private String name;
	private String surname;
	private Sex sex;
	private String history;

	private Characteristics characteristics;
	private List<Category> categories;
	private Culture culture;
	private List<Training> trainings;
	private Profession profession;
	private Resistances resistances;

	private List<LevelUp> levelUps;

	public CharacterPlayer() {
		characteristics = new Characteristics();
		levelUps = new ArrayList<>();
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
		return characteristics.getDevelopmentPoints() - getSpentDevelopmentPoints();
	}
	
	public Integer getCharacterLevel(){
		return levelUps.size();
	}
	
	public Integer getResistanceBonus(ResistanceType type){
		return Resistances.getResistanceCharacteristicsBonus(type, characteristics);
	}

}
