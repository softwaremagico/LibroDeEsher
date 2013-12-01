package com.softwaremagico.librodeesher.pj.profession;

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

import com.softwaremagico.librodeesher.pj.categories.Category;
import com.softwaremagico.librodeesher.pj.categories.CategoryCost;
import com.softwaremagico.librodeesher.pj.skills.Skill;

public class ProfessionDecisions {
	private Long id; // database id.
	private HashMap<Category, CategoryCost> weaponsCost;
	private List<String> commonSkillsChose;
	private List<String> restrictedSkillsChose;
	private List<String> professionalSkillsChose;

	public ProfessionDecisions() {
		weaponsCost = new HashMap<>();
		commonSkillsChose = new ArrayList<>();
		professionalSkillsChose = new ArrayList<>();
		restrictedSkillsChose = new ArrayList<>();
	}

	public void setWeaponCost(Category category, CategoryCost cost) {
		weaponsCost.put(category, cost);
	}

	public CategoryCost getWeaponCost(Category category) {
		return weaponsCost.get(category);
	}

	public boolean isWeaponCostUsed(CategoryCost cost) {
		for (CategoryCost usedCosts : weaponsCost.values()) {
			if (usedCosts.equals(cost)) {
				return true;
			}
		}
		return false;
	}

	public void resetWeaponCosts() {
		weaponsCost = new HashMap<>();
	}

	public void resetWeaponCost(Category category) {
		weaponsCost.remove(category);
	}

	public boolean isCommon(Skill skill) {
		return commonSkillsChose.contains(skill.getName());
	}

	public boolean isRestricted(Skill skill) {
		return restrictedSkillsChose.contains(skill.getName());
	}

	public boolean isProfessional(Skill skill) {
		return professionalSkillsChose.contains(skill.getName());
	}

	public List<String> getCommonSkillsChose() {
		return commonSkillsChose;
	}

	public void setCommonSkillsChose(List<String> commonSkillsChose) {
		this.commonSkillsChose = commonSkillsChose;
	}

	public List<String> getRestrictedSkillsChose() {
		return restrictedSkillsChose;
	}

	public void setRestrictedSkillsChose(List<String> restrictedSkillsChose) {
		this.restrictedSkillsChose = restrictedSkillsChose;
	}

	public List<String> getProfessionalSkillsChose() {
		return professionalSkillsChose;
	}

	public void setProfessionalSkillsChose(List<String> professionalSkillsChose) {
		this.professionalSkillsChose = professionalSkillsChose;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
