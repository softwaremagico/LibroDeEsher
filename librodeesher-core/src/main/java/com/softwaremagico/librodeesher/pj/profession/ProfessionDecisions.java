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
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.softwaremagico.librodeesher.pj.categories.Category;
import com.softwaremagico.librodeesher.pj.categories.CategoryCost;
import com.softwaremagico.librodeesher.pj.skills.Skill;

@Entity
@Table(name = "T_PROFESSION_DECISIONS")
public class ProfessionDecisions {
	@Id
	@GeneratedValue
	private Long professionDecisionsId; // database id.
	//Category -> cost
	@ElementCollection
	@CollectionTable(name = "T_PROFESSION_WEAPON_COST_CHOSEN")
	private Map<String, CategoryCost> weaponsCost;
	@ElementCollection
	@CollectionTable(name = "T_PROFESSION_COMMON_SKILLS_CHOSEN")
	private List<String> commonSkillsChosen;
	@ElementCollection
	@CollectionTable(name = "T_PROFESSION_RESTRICTED_SKILLS_CHOSEN")
	private List<String> restrictedSkillsChosen;
	@ElementCollection
	@CollectionTable(name = "T_PROFESSION_PROFESSIONAL_SKILLS_CHOSEN")
	private List<String> professionalSkillsChosen;

	public ProfessionDecisions() {
		weaponsCost = new HashMap<>();
		commonSkillsChosen = new ArrayList<>();
		professionalSkillsChosen = new ArrayList<>();
		restrictedSkillsChosen = new ArrayList<>();
	}

	public void setWeaponCost(Category category, CategoryCost cost) {
		weaponsCost.put(category.getName(), cost);
	}

	public CategoryCost getWeaponCost(Category category) {
		return weaponsCost.get(category.getName());
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
		weaponsCost.remove(category.getName());
	}

	public boolean isCommon(Skill skill) {
		return commonSkillsChosen.contains(skill.getName());
	}

	public boolean isRestricted(Skill skill) {
		return restrictedSkillsChosen.contains(skill.getName());
	}

	public boolean isProfessional(Skill skill) {
		return professionalSkillsChosen.contains(skill.getName());
	}

	protected Long getProfessionDecisionsId() {
		return professionDecisionsId;
	}

	protected void setProfessionDecisionsId(Long professionDecisionsId) {
		this.professionDecisionsId = professionDecisionsId;
	}

	public Map<String, CategoryCost> getWeaponsCost() {
		return weaponsCost;
	}

	public void setWeaponsCost(Map<String, CategoryCost> weaponsCost) {
		this.weaponsCost = weaponsCost;
	}

	public List<String> getCommonSkillsChosen() {
		return commonSkillsChosen;
	}

	public void setCommonSkillsChosen(List<String> commonSkillsChosen) {
		this.commonSkillsChosen = commonSkillsChosen;
	}

	public List<String> getRestrictedSkillsChosen() {
		return restrictedSkillsChosen;
	}

	public void setRestrictedSkillsChosen(List<String> restrictedSkillsChosen) {
		this.restrictedSkillsChosen = restrictedSkillsChosen;
	}

	public List<String> getProfessionalSkillsChosen() {
		return professionalSkillsChosen;
	}

	public void setProfessionalSkillsChosen(List<String> professionalSkillsChosen) {
		this.professionalSkillsChosen = professionalSkillsChosen;
	}
}
