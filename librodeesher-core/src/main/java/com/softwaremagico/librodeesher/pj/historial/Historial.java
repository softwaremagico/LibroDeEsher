package com.softwaremagico.librodeesher.pj.historial;

/*
 * #%L
 * Libro de Esher
 * %%
 * Copyright (C) 2007 - 2013 Softwaremagico
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
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.google.gson.annotations.Expose;
import com.softwaremagico.librodeesher.basics.Roll;
import com.softwaremagico.librodeesher.pj.categories.Category;
import com.softwaremagico.librodeesher.pj.characteristic.CharacteristicRoll;
import com.softwaremagico.librodeesher.pj.characteristic.CharacteristicsAbbreviature;
import com.softwaremagico.librodeesher.pj.skills.Skill;
import com.softwaremagico.persistence.StorableObject;

@Entity
@Table(name = "T_HISTORIAL")
public class Historial extends StorableObject {
	private static final long serialVersionUID = 4564464938854797944L;
	private static final Integer SKILL_BONUS = 10;
	private static final Integer CATEGORY_BONUS = 5;
	@Expose
	@ElementCollection
	@LazyCollection(LazyCollectionOption.FALSE)
	@CollectionTable(name = "T_HISTORIAL_CATEGORIES")
	private List<String> categories;
	
	@Expose
	@ElementCollection
	@LazyCollection(LazyCollectionOption.FALSE)
	@CollectionTable(name = "T_HISTORIAL_SKILLS")
	private List<String> skills;
	
	@Expose
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@LazyCollection(LazyCollectionOption.FALSE)
	@CollectionTable(name = "T_HISTORIAL_CHARACTERISTICS_UPDATES")
	@OrderColumn(name = "roll_index")
	private List<CharacteristicRoll> characteristicsUpdates;

	public Historial() {
		categories = new ArrayList<>();
		skills = new ArrayList<>();
		characteristicsUpdates = new ArrayList<>();
	}
	
	@Override
	public void resetIds(){
		resetIds(this);
		resetIds(characteristicsUpdates);
	}
	
	@Override
	public void resetComparationIds(){
		resetComparationIds(this);
		resetComparationIds(characteristicsUpdates);
	}

	public void setPoint(Skill skill, boolean value) {
		if (value) {
			if (!isHistorialPointSelected(skill)) {
				skills.add(skill.getName());
			}
		} else {
			skills.remove(skill.getName());
		}
	}

	public void setPoint(Category category, boolean value) {
		if (value) {
			if (!isHistorialPointSelected(category)) {
				categories.add(category.getName());
			}
		} else {
			categories.remove(category.getName());
		}
	}

	public boolean isHistorialPointSelected(Category category) {
		return categories.contains(category.getName());
	}

	public boolean isHistorialPointSelected(Skill skill) {
		return skills.contains(skill.getName());
	}

	public Integer getBonus(Skill skill) {
		if (skills.contains(skill.getName())) {
			return SKILL_BONUS;
		}
		return 0;
	}

	public Integer getBonus(Category category) {
		if (categories.contains(category.getName())) {
			return CATEGORY_BONUS;
		}
		return 0;
	}

	public Integer getSpentHistoryPoints() {
		return skills.size() + categories.size() + getCharacteristicsUpdatesPoints();
	}

	public CharacteristicRoll addCharactersiticUpdate(CharacteristicsAbbreviature abbreviature, Integer currentTemporalValue,
			Integer currentPotentialValue, Roll roll) {
		CharacteristicRoll characteristicRoll = new CharacteristicRoll(abbreviature, currentTemporalValue,
				currentPotentialValue, roll);
		characteristicsUpdates.add(characteristicRoll);
		return characteristicRoll;
	}

	private Integer getCharacteristicsUpdatesPoints() {
		return characteristicsUpdates.size();
	}

	public List<CharacteristicRoll> getCharacteristicsUpdates(CharacteristicsAbbreviature abbreviature) {
		List<CharacteristicRoll> returnList = new ArrayList<>();
		for (CharacteristicRoll characteristicRollGroup : characteristicsUpdates) {
			if (characteristicRollGroup.getCharacteristicAbbreviature().equals(abbreviature)) {
				returnList.add(characteristicRollGroup);
			}
		}

		return returnList;
	}

	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

	public List<String> getSkills() {
		return skills;
	}

	public void setSkills(List<String> skills) {
		this.skills = skills;
	}

	public List<CharacteristicRoll> getCharacteristicsUpdates() {
		return characteristicsUpdates;
	}

	protected void setCharacteristicsUpdates(List<CharacteristicRoll> characteristicsUpdates) {
		this.characteristicsUpdates = characteristicsUpdates;
	}
}
