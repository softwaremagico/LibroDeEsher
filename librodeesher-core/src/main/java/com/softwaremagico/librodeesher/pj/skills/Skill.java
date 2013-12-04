package com.softwaremagico.librodeesher.pj.skills;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.ProgressionCostType;
import com.softwaremagico.librodeesher.pj.categories.Category;
import com.softwaremagico.librodeesher.pj.magic.RealmOfMagic;

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
@Table(name = "T_SKILL")
public class Skill {
	@Id
	@GeneratedValue
	private Long id; // database id.

	private String name;
	@ElementCollection
	@CollectionTable(name = "T_SKILL_SPECIALITIES")
	private List<String> specialities; // A skill can have some specializations.
	private SkillType type;
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Category category;
	private SkillGroup group;
	private boolean usedInRandom = true;

	public Skill(String name, SkillType type) {
		this.type = type;
		specialities = new ArrayList<>();
		String specialityPattern = Pattern.quote("[");
		String[] nameColumns = name.split(specialityPattern);
		this.name = nameColumns[0].trim();
		group = SkillGroup.STANDARD;
	}

	public Skill(String name, SkillType type, SkillGroup group) {
		this.type = type;
		specialities = new ArrayList<>();
		String specialityPattern = Pattern.quote("[");
		String[] nameColumns = name.split(specialityPattern);
		this.name = nameColumns[0].trim();
		this.group = group;
	}

	public String getName() {
		return name;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Category getCategory() {
		return category;
	}

	public void addSpecialities(List<String> specialities) {
		this.specialities.addAll(specialities);
	}

	public Integer getRankValue(CharacterPlayer character, Integer ranksNumber) {
		switch (getCategory().getType()) {
		case STANDARD:
		case COMBINED:
		case LIMITED:
		case SPECIAL:
			return getCategory().getSkillRankValues(ranksNumber);
		case PPD:
			Integer total = 0;
			for (RealmOfMagic realm : character.getProfessionalRealmsOfMagicChoosen().getRealmsOfMagic()) {
				ProgressionCostType progressionValue = ProgressionCostType.getProgressionCostType(realm);
				total += getCategory().getSkillRankValues(ranksNumber,
						character.getRace().getProgressionRankValues(progressionValue));
			}
			return total / character.getProfessionalRealmsOfMagicChoosen().getRealmsOfMagic().size();
		case FD:
			return getCategory().getSkillRankValues(ranksNumber,
					character.getRace().getProgressionRankValues(ProgressionCostType.PHYSICAL_DEVELOPMENT));
		default:
			return 0;
		}
	}

	public SkillGroup getGroup() {
		return group;
	}

	public String toString() {
		return getName();
	}

	public SkillType getType() {
		return type;
	}

	public boolean isUsedInRandom() {
		return usedInRandom;
	}

	public void setUsedInRandom(boolean usedInRandom) {
		this.usedInRandom = usedInRandom;
	}

	protected Long getId() {
		return id;
	}

	protected void setId(Long id) {
		this.id = id;
	}

	protected List<String> getSpecialities() {
		return specialities;
	}

	protected void setSpecialities(List<String> specialities) {
		this.specialities = specialities;
	}

	protected void setName(String name) {
		this.name = name;
	}

	protected void setType(SkillType type) {
		this.type = type;
	}

	protected void setGroup(SkillGroup group) {
		this.group = group;
	}

}
