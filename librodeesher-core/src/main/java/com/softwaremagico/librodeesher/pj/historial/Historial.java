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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.softwaremagico.librodeesher.basics.Roll;
import com.softwaremagico.librodeesher.basics.RollGroup;
import com.softwaremagico.librodeesher.pj.categories.Category;
import com.softwaremagico.librodeesher.pj.skills.Skill;

@Entity
@Table(name = "T_HISTORIAL")
public class Historial {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name = "ID", unique = true, nullable = false)
	private Long historialId; // database id.

	private static final Integer SKILL_BONUS = 10;
	private static final Integer CATEGORY_BONUS = 5;
	@ElementCollection
	@CollectionTable(name = "T_HISTORIAL_CATEGORIES")
	private List<String> categories;
	@ElementCollection
	@CollectionTable(name = "T_HISTORIAL_SKILLS")
	private List<String> skills;
	@ElementCollection
	@CollectionTable(name = "T_HISTORIAL_CHARACTERISTICS_UPDATES")
	private Map<String, RollGroup> characteristicsUpdates;

	public Historial() {
		categories = new ArrayList<>();
		skills = new ArrayList<>();
		characteristicsUpdates = new HashMap<>();
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

	public void setCharactersiticUpdate(String abbreviature, Roll roll) {
		if (characteristicsUpdates.get(abbreviature) == null) {
			characteristicsUpdates.put(abbreviature, new RollGroup(abbreviature));
		}
		characteristicsUpdates.get(abbreviature).add(roll);
	}

	private Integer getCharacteristicsUpdatesPoints() {
		Integer total = 0;
		for (RollGroup rollGroup : characteristicsUpdates.values()) {
			total += rollGroup.getRolls().size();
		}
		return total;
	}

	public List<Roll> getCharacteristicsUpdates(String abbreviature) {
		List<Roll> rolls = new ArrayList<>();
		if (characteristicsUpdates.get(abbreviature) != null) {
			rolls.addAll(characteristicsUpdates.get(abbreviature).getRolls());
		}
		return rolls;
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

	public Map<String, RollGroup> getCharacteristicsUpdates() {
		return characteristicsUpdates;
	}

	protected Long getHistorialId() {
		return historialId;
	}

	protected void setHistorialId(Long historialId) {
		this.historialId = historialId;
	}

	protected void setCharacteristicsUpdates(Map<String, RollGroup> characteristicsUpdates) {
		this.characteristicsUpdates = characteristicsUpdates;
	}
}
