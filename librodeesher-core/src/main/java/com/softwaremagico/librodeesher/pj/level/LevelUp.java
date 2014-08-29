package com.softwaremagico.librodeesher.pj.level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.google.gson.annotations.Expose;
import com.softwaremagico.librodeesher.pj.categories.CategoryGroup;
import com.softwaremagico.librodeesher.pj.skills.Skill;
import com.softwaremagico.persistence.StorableObject;

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

@Entity
@Table(name = "T_LEVELUP")
public class LevelUp extends StorableObject {

	@Expose
	@ElementCollection
	@CollectionTable(name = "T_LEVELUP_CATEGORIES_RANKS")
	@LazyCollection(LazyCollectionOption.FALSE)
	private Map<String, Integer> categoriesRanks;

	@Expose
	@ElementCollection
	@CollectionTable(name = "T_LEVELUP_SKILLS_RANKS")
	@LazyCollection(LazyCollectionOption.FALSE)
	private Map<String, Integer> skillsRanks;

	@Expose
	@ElementCollection
	@CollectionTable(name = "T_LEVELUP_GENERALIZED_SKILLS")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<String> generalizedSkills;

	@Expose
	@ElementCollection
	@CollectionTable(name = "T_LEVEL_UP_SPELLS_UPDATED")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<String> spellsUpdated; // More than 5 list is more expensive in
										// Development Points.

	@Expose
	@ElementCollection
	@CollectionTable(name = "T_LEVEL_UP_TRAININGS_ADQUIRED")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<String> trainings;

	@Expose
	@ElementCollection
	@CollectionTable(name = "T_LEVEL_UP_SKILL_SPECIALIZATIONS")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<String> skillSpecializations;

	public LevelUp() {
		categoriesRanks = new HashMap<>();
		skillsRanks = new HashMap<>();
		spellsUpdated = new ArrayList<>();
		trainings = new ArrayList<>();
		generalizedSkills = new ArrayList<>();
	}

	@Override
	public void resetIds() {
		resetIds(this);
	}

	public Integer getCategoryRanks(String categoryName) {
		Integer ranks = categoriesRanks.get(categoryName);
		if (ranks == null) {
			return 0;
		}
		return ranks;
	}

	public Integer getSkillsRanks(String skillName) {
		Integer ranks = skillsRanks.get(skillName);
		if (ranks == null) {
			return 0;
		}
		return ranks;
	}

	public void setSkillsRanks(Skill skill, Integer ranks) {
		if (ranks <= 0) {
			skillsRanks.remove(skill.getName());
			if (skill.getCategory().getCategoryGroup().equals(CategoryGroup.SPELL)) {
				spellsUpdated.remove(skill.getName());
			}
		} else {
			skillsRanks.put(skill.getName(), ranks);
			if (skill.getCategory().getCategoryGroup().equals(CategoryGroup.SPELL)) {
				if (!spellsUpdated.contains(skill.getName())) {
					spellsUpdated.add(skill.getName());
				}
			}
		}
	}

	public List<String> getCategoriesWithRanks() {
		List<String> list = new ArrayList<>(categoriesRanks.keySet());
		return list;
	}

	public List<String> getSkillsWithRanks() {
		List<String> list = new ArrayList<>(skillsRanks.keySet());
		return list;
	}

	public void setCategoryRanks(String categoryName, Integer ranks) {
		if (ranks <= 0) {
			categoriesRanks.remove(categoryName);
		} else {
			categoriesRanks.put(categoryName, ranks);
		}
	}

	/**
	 * If a player learn more than 5 spell list in one level, the cost is doubled. If he learns more than 10 spells, the
	 * cost is the quadruple.
	 * 
	 * @param skill
	 * @return
	 */
	public Integer getSpellRankMultiplier(Skill skill) {
		int spellLists = 0;
		for (int i = 0; i < spellsUpdated.size(); i++) {
			if (spellsUpdated.get(i).equals(skill.getName())) {
				spellLists = i + 1;
			}
		}
		if (spellLists <= 5) {
			return 1;
		} else if (spellLists <= 10) {
			return 2;
		} else {
			return 4;
		}
	}

	public List<String> getTrainings() {
		return trainings;
	}

	public void addTraining(String trainingName) {
		trainings.add(trainingName);
	}

	public void removeTraining(String trainingName) {
		trainings.remove(trainingName);
	}

	public Map<String, Integer> getCategoriesRanks() {
		return categoriesRanks;
	}

	public void setCategoriesRanks(HashMap<String, Integer> categoriesRanks) {
		this.categoriesRanks = categoriesRanks;
	}

	public Map<String, Integer> getSkillsRanks() {
		return skillsRanks;
	}

	public void setSkillsRanks(HashMap<String, Integer> skillsRanks) {
		this.skillsRanks = skillsRanks;
	}

	public List<String> getSpellsUpdated() {
		return spellsUpdated;
	}

	public void setSpellsUpdated(List<String> spellsUpdated) {
		this.spellsUpdated = spellsUpdated;
	}

	public void setTrainings(List<String> trainings) {
		this.trainings = trainings;
	}

	public List<String> getGeneralizedSkills() {
		return generalizedSkills;
	}

	public void setGeneralizedSkills(List<String> generalizedSkills) {
		this.generalizedSkills = generalizedSkills;
	}

	public void setCategoriesRanks(Map<String, Integer> categoriesRanks) {
		this.categoriesRanks = categoriesRanks;
	}

	public void setSkillsRanks(Map<String, Integer> skillsRanks) {
		this.skillsRanks = skillsRanks;
	}

	public List<String> getSkillSpecializations(Skill skill) {
		List<String> specialities = new ArrayList<>();
		for (String speciality : skill.getSpecialities()) {
			if (getSkillSpecializations().contains(speciality)) {
				specialities.add(speciality);
			}
		}
		return specialities;
	}

	public List<String> getSkillSpecializations() {
		return skillSpecializations;
	}

	public void addSkillSpecialization(String specialization) {
		skillSpecializations.add(specialization);
	}

	public void setSkillSpecializations(List<String> skillSpecializations) {
		this.skillSpecializations = skillSpecializations;
	}

	public Integer getRanksSpentInSpecializations(Skill skill) {
		int total = 0;
		for (String speciality : skill.getSpecialities()) {
			if (skillSpecializations.contains(speciality)) {
				total++;
			}
		}
		return total;
	}
}
