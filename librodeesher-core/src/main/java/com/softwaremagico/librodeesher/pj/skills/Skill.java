package com.softwaremagico.librodeesher.pj.skills;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

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

public class Skill {
	private String name;
	private List<String> specialities; // A skill can have some specializations.
	private List<String> enableSkills; // A skill can enable others skills.
	private SkillType skilltype;
	private Category category;
	private SkillGroup skillGroup;
	private boolean rare = true;
	private boolean enabled = true;

	public Skill(String name, SkillType type) {
		this.skilltype = type;
		specialities = new ArrayList<>();
		enableSkills = new ArrayList<>();
		// Enables some other skills
		name = getSkillsEnabled(name);
		String specialityPattern = Pattern.quote("[");
		String[] nameColumns = name.split(specialityPattern);
		this.name = nameColumns[0].trim();
		skillGroup = SkillGroup.STANDARD;
	}

	public Skill(String name, SkillType type, SkillGroup group) {
		this.skilltype = type;
		specialities = new ArrayList<>();
		enableSkills = new ArrayList<>();
		// Enables some other skills
		name = getSkillsEnabled(name);
		String specialityPattern = Pattern.quote("[");
		String[] nameColumns = name.split(specialityPattern);
		this.name = nameColumns[0].trim();
		this.skillGroup = group;
	}

	/**
	 * Converts any { skill1 | skill2 } string in skills to be enabled when
	 * selecting one rank.
	 * 
	 * @param name
	 * @return
	 */
	private String getSkillsEnabled(String name) {
		String enabledSkills = null;
		if (name.contains("{") && name.contains("}")) {
			enabledSkills = name.substring(name.indexOf('{') + 1, name.indexOf('}'));
			// Remove enabled skills from name.
			name = name.replaceAll(Pattern.quote("{" + enabledSkills + "}"), "").trim();

			String[] enabledArray = enabledSkills.split("\\|");
			for (String skillEnabled : enabledArray) {
				enableSkills.add(skillEnabled.trim());
			}
		}
		return name;
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
		if (getCategory() != null) {
			switch (getCategory().getCategoryType()) {
			case STANDARD:
			case COMBINED:
			case LIMITED:
			case SPECIAL:
				return getCategory().getSkillRankValues(ranksNumber);
			case PPD:
				Integer total = 0;
				for (RealmOfMagic realm : character.getRealmOfMagic().getRealmsOfMagic()) {
					ProgressionCostType progressionValue = ProgressionCostType.getProgressionCostType(realm);
					total += getCategory().getSkillRankValues(ranksNumber,
							character.getRace().getProgressionRankValues(progressionValue));
				}
				return total / character.getRealmOfMagic().getRealmsOfMagic().size();
			case PD:
				return getCategory().getSkillRankValues(
						ranksNumber,
						character.getRace()
								.getProgressionRankValues(ProgressionCostType.PHYSICAL_DEVELOPMENT));
			default:
				return 0;
			}
		}
		return 0;
	}

	public SkillGroup getSkillGroup() {
		return skillGroup;
	}

	public String toString() {
		return getName();
	}

	public SkillType getSkillType() {
		return skilltype;
	}

	public boolean isRare() {
		return rare;
	}

	public void setRare(boolean rare) {
		this.rare = rare;
	}

	public List<String> getSpecialities() {
		return specialities;
	}

	protected void setSpecialities(List<String> specialities) {
		this.specialities = specialities;
	}

	protected void setName(String name) {
		this.name = name;
	}

	protected void setSkillType(SkillType type) {
		this.skilltype = type;
	}

	protected void setSkillGroup(SkillGroup group) {
		this.skillGroup = group;
	}

	public String getName(int length) {
		return getName().substring(0, Math.min(length - 1, getName().length()));
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public List<String> getEnableSkills() {
		return enableSkills;
	}

}
