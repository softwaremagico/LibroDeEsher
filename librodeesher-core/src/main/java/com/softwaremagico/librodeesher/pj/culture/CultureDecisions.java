package com.softwaremagico.librodeesher.pj.culture;

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
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.softwaremagico.librodeesher.pj.categories.Category;
import com.softwaremagico.librodeesher.pj.skills.Skill;
import com.softwaremagico.persistence.StorableObject;

@Entity
@Table(name = "T_CULTUREDECISIONS")
public class CultureDecisions extends StorableObject {
	private static final long serialVersionUID = 2367407444500030039L;

	@Expose
	@SerializedName(value = "skillRanks", alternate = { "weaponRanks" })
	@ElementCollection
	@CollectionTable(name = "T_CULTURE_SKILL_RANKS")
	@LazyCollection(LazyCollectionOption.FALSE)
	private Map<String, Integer> skillRanks;

	@Expose
	@ElementCollection
	@CollectionTable(name = "T_CULTURE_HOBBY_RANKS")
	@LazyCollection(LazyCollectionOption.FALSE)
	private Map<String, Integer> hobbyRanks;

	@Expose
	@ElementCollection
	@CollectionTable(name = "T_CULTURE_SPELL_RANKS")
	@LazyCollection(LazyCollectionOption.FALSE)
	private Map<String, Integer> spellRanks;

	@Expose
	@ElementCollection
	@CollectionTable(name = "T_CULTURE_LANGUAGE_RANKS")
	@LazyCollection(LazyCollectionOption.FALSE)
	private Map<String, Integer> languageRanks;

	@Expose
	@ElementCollection
	@CollectionTable(name = "T_CULTURE_OPTIONAL_CULTURE_LANGUAGES")
	@LazyCollection(LazyCollectionOption.FALSE)
	@OrderColumn(name = "raceLanguageIndex")
	private List<String> optionalCulturalLanguageSelection;

	@Expose
	@ElementCollection
	@CollectionTable(name = "T_CULTURE_OPTIONAL_RACE_LANGUAGES")
	@LazyCollection(LazyCollectionOption.FALSE)
	@OrderColumn(name = "raceLanguageIndex")
	private List<String> optionalRaceLanguageSelection;

	@ElementCollection
	@CollectionTable(name = "T_CULTURE_OPTIONAL_CATEGORIES")
	@LazyCollection(LazyCollectionOption.FALSE)
	@OrderColumn(name = "raceLanguageIndex")
	private List<String> adolescenceCategoriesSelected;

	public CultureDecisions() {
		languageRanks = new HashMap<>();
		skillRanks = new HashMap<>();
		hobbyRanks = new HashMap<>();
		spellRanks = new HashMap<>();
		optionalCulturalLanguageSelection = new ArrayList<>();
		optionalRaceLanguageSelection = new ArrayList<>();
		adolescenceCategoriesSelected = new ArrayList<>();
		resetLanguageOptions();
	}

	@Override
	public void resetIds() {
		resetIds(this);
	}

	@Override
	public void resetComparationIds() {
		resetComparationIds(this);
	}

	public void resetLanguageOptions() {
		optionalCulturalLanguageSelection.clear();
		optionalRaceLanguageSelection.clear();
	}

	/**
	 * Includes the ranks in the communication category of the culture plus the
	 * communications ranks of the race.
	 * 
	 * @param language
	 * @param ranks
	 */
	public void setLanguageRank(String language, Integer ranks) {
		if (ranks <= 0) {
			languageRanks.remove(language);
		} else {
			languageRanks.put(language, ranks);
		}
	}

	public Integer getTotalLanguageRanks() {
		Integer total = 0;
		for (Integer value : languageRanks.values()) {
			total += value;
		}
		return total;
	}

	public Integer getLanguageRanks(String language) {
		Integer ranks = languageRanks.get(language);
		if (ranks == null) {
			return 0;
		}
		return ranks;
	}

	public void setSkillRanks(String skill, Integer ranks) {
		if (ranks <= 0) {
			skillRanks.remove(skill);
		} else {
			skillRanks.put(skill, ranks);
		}
	}

	public Integer getSkillRanks(String skill) {
		Integer value = skillRanks.get(skill);
		if (value == null) {
			return 0;
		}
		return value;
	}

	public Integer getTotalAdolescenceSkillRanks(Category category) {
		Integer total = 0;
		for (Skill skill : category.getSkills()) {
			total += getSkillRanks(skill.getName());
		}
		return total;
	}

	public void setHobbyRanks(String hobby, Integer ranks) {
		if (ranks <= 0) {
			hobbyRanks.remove(hobby);
		} else {
			hobbyRanks.put(hobby, ranks);
		}
	}

	public Integer getHobbyRanks(String hobby) {
		Integer value = hobbyRanks.get(hobby);
		if (value == null) {
			return 0;
		}
		return value;
	}

	public void setSpellRanks(String spellList, Integer ranks) {
		if (ranks <= 0) {
			spellRanks.remove(spellList);
		} else {
			spellRanks.put(spellList, ranks);
		}
	}

	public Integer getSpellRanks(String spellList) {
		Integer value = spellRanks.get(spellList);
		if (value == null) {
			return 0;
		}
		return value;
	}

	public Integer getTotalHobbyRanks() {
		Integer total = 0;
		for (Integer value : hobbyRanks.values()) {
			total += value;
		}
		return total;
	}

	public Integer getTotalSpellRanks() {
		Integer total = 0;
		for (Integer value : spellRanks.values()) {
			total += value;
		}
		return total;
	}

	public Map<String, Integer> getLanguageRanks() {
		return languageRanks;
	}

	public List<String> getOptionalCulturalLanguages() {
		return optionalCulturalLanguageSelection;
	}

	public List<String> getOptionalRaceLanguages() {
		return optionalRaceLanguageSelection;
	}

	public void removeSkills(CultureCategory cultureCategory) {
		for (String category : cultureCategory.getCategoryOptions()) {
			for (Skill skill : cultureCategory.getCultureSkills(category)) {
				skillRanks.remove(skill.getName());
			}
		}
	}

	public void addAdolescenceCategorySelection(String categoryName) {
		if (!adolescenceCategoriesSelected.contains(categoryName)) {
			adolescenceCategoriesSelected.add(categoryName);
		}
	}

	public void removeAdolescenceCategorySelection(String categoryName) {
		adolescenceCategoriesSelected.remove(categoryName);
	}
}
