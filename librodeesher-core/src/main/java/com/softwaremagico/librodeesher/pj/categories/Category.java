package com.softwaremagico.librodeesher.pj.categories;

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
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import com.softwaremagico.librodeesher.basics.Spanish;
import com.softwaremagico.librodeesher.pj.characteristic.CharacteristicsAbbreviature;
import com.softwaremagico.librodeesher.pj.skills.Skill;
import com.softwaremagico.librodeesher.pj.skills.SkillComparatorByName;
import com.softwaremagico.librodeesher.pj.skills.SkillFactory;

public abstract class Category {
	private String name;
	protected String abbreviature;
	protected CategoryType categoryType;
	protected CategoryGroup categoryGroup;
	private String characterisitcsTags;
	private List<CharacteristicsAbbreviature> characteristics;
	protected List<Skill> skills;
	// Subset of skills that are without an '*' in the name.
	private List<Skill> normalSkills = null;
	private List<Float> skillRankValues; // Rank values. i.e: -15/3/2/1/0.5
	private boolean notUsedInRandom = false;

	public Category(String name, String abbreviature, CategoryType type, String characteristicsTag, List<Float> skillRankValues) {
		this.name = name;
		this.abbreviature = abbreviature;
		this.categoryType = type;
		this.skillRankValues = skillRankValues;
		skills = new ArrayList<>();
		this.characterisitcsTags = characteristicsTag;
		createCharacteristicList(characteristicsTag);
		setCategoryGroup();
	}

	private void createCharacteristicList(String characteristicsTag) {
		characteristics = new ArrayList<>();
		String[] characteristicsTags = characteristicsTag.split(Pattern.quote("/"));
		for (String characteristic : characteristicsTags) {
			characteristics.add(CharacteristicsAbbreviature.getCharacteristicsAbbreviature(characteristic));
		}
	}

	public String getName() {
		return name;
	}

	public abstract void addSkills(String skills);

	public void addSkillsFromName(List<String> skillsNames) {
		if (skillsNames != null) {
			for (String skill : skillsNames) {
				addSkill(skill);
			}
		}
	}

	public void setSkillsFromName(List<String> skillsNames) {
		skills = new ArrayList<>();
		addSkillsFromName(skillsNames);
	}

	public Skill getSkill(String skillName) {
		for (Skill skill : skills) {
			if (skill.getName().equals(skillName)) {
				return skill;
			}
		}
		return null;
	}

	public String getAbbreviature() {
		return abbreviature;
	}

	public List<Skill> getSkills() {
		return Collections.unmodifiableList(new ArrayList<>(skills));
	}

	public List<Skill> getNonRareSkills() {
		if (normalSkills != null) {
			return normalSkills;
		}
		List<Skill> filteredSkills = new ArrayList<>();
		for (Skill skill : getSkills()) {
			if (!skill.isRare()) {
				filteredSkills.add(skill);
			}
		}
		Collections.sort(filteredSkills, new SkillComparatorByName());
		normalSkills = filteredSkills;
		return filteredSkills;
	}

	public CategoryType getCategoryType() {
		return categoryType;
	}

	public void setSkills(List<Skill> skills) {
		this.skills = skills;
		for (Skill skill : skills) {
			skill.setCategory(this);
		}
		Collections.sort(skills, new SkillComparatorByName());
	}

	public void addSkills(List<Skill> skills) {
		this.skills.addAll(skills);
		for (Skill skill : skills) {
			skill.setCategory(this);
		}
		Collections.sort(skills, new SkillComparatorByName());
	}

	public Skill addSkill(String skillName) {
		if (skillName.length() > 0) {
			Skill skill = SkillFactory.getSkill(skillName);
			if (skill != null) {
				skill.setCategory(this);
				if (!skills.contains(skill)) {
					skills.add(skill);
					Collections.sort(skills, new SkillComparatorByName());
				}
				return skill;
			}
		}
		return null;
	}

	public Integer getRankValue(Integer ranksNumber) {
		return 0;
	}

	public Integer getSkillRankValues(Integer ranksNumber) {
		return getSkillRankValues(ranksNumber, skillRankValues);
	}

	public Integer getSkillRankValues(Integer ranksNumber, List<Float> definedSkillRankValues) {
		if (ranksNumber <= 0) {
			return definedSkillRankValues.get(0).intValue();
		} else if (ranksNumber > 0 && ranksNumber <= 10) {
			return definedSkillRankValues.get(1).intValue() * ranksNumber;
		} else if (ranksNumber > 10 && ranksNumber <= 20) {
			return definedSkillRankValues.get(1).intValue() * 10 + definedSkillRankValues.get(2).intValue() * (ranksNumber - 10);
		} else if (ranksNumber > 20 && ranksNumber <= 30) {
			return definedSkillRankValues.get(1).intValue() * 10 + definedSkillRankValues.get(2).intValue() * 10 + definedSkillRankValues.get(3).intValue()
					* (ranksNumber - 20);
		} else {
			return definedSkillRankValues.get(1).intValue() * 10 + definedSkillRankValues.get(2).intValue() * 10 + definedSkillRankValues.get(3).intValue()
					* 10 + definedSkillRankValues.get(4).intValue() * (ranksNumber - 30);
		}
	}

	public static List<Float> getConvertedProgressionString(String progression) {
		Scanner s = new Scanner(progression);
		s.useDelimiter(Pattern.quote("/"));
		List<Float> progressionCost = new ArrayList<>();
		int i = 0;
		while (s.hasNext() && i < 5) {
			progressionCost.add(s.nextFloat());
			i++;
		}
		s.close();
		return progressionCost;
	}

	public abstract boolean hasRanks();

	public Integer getBonus() {
		return 0;
	}

	public List<CharacteristicsAbbreviature> getCharacteristics() {
		return characteristics;
	}

	public CategoryGroup getCategoryGroup() {
		return categoryGroup;
	}

	private void setCategoryGroup() {
		this.categoryGroup = getCategoryGroup(this.getName());
	}

	private static CategoryGroup getCategoryGroup(String categoryName) {
		if (categoryName.toLowerCase().startsWith(Spanish.WEAPON_CATEGORY_PREFIX)) {
			return CategoryGroup.WEAPON;
		} else if (categoryName.toLowerCase().startsWith(Spanish.SPELL_CATEGORY_PREFIX)) {
			return CategoryGroup.SPELL;
		} else if (categoryName.toLowerCase().startsWith(Spanish.AIMED_SPELLS_CATEGORY) || categoryName.toLowerCase().startsWith(Spanish.POWER_MANIPULATION)
				|| categoryName.toLowerCase().equals(Spanish.POWER_POINTS_CATEGORY)) {
			return CategoryGroup.SPELLS_RELATED;
		}
		return CategoryGroup.STANDARD;
	}

	public String getCharacterisitcsTags() {
		return characterisitcsTags;
	}

	public String toString() {
		return getName();
	}

	protected void setCharacteristicsListTags(List<CharacteristicsAbbreviature> characteristicsListTags) {
		this.characteristics = characteristicsListTags;
	}

	protected List<Float> getSkillRankValues() {
		return skillRankValues;
	}

	protected void setSkillRankValues(List<Float> skillRankValues) {
		this.skillRankValues = skillRankValues;
	}

	protected void setName(String name) {
		this.name = name;
	}

	protected void setAbbreviature(String abbreviature) {
		this.abbreviature = abbreviature;
	}

	protected void setCategoryType(CategoryType type) {
		this.categoryType = type;
	}

	protected void setGroup(CategoryGroup group) {
		this.categoryGroup = group;
	}

	protected void setCharacterisitcsTags(String characterisitcsTags) {
		this.characterisitcsTags = characterisitcsTags;
	}

	public boolean isNotUsedInRandom() {
		return notUsedInRandom;
	}

	public void setNotUsedInRandom(boolean notUsedInRandom) {
		this.notUsedInRandom = notUsedInRandom;
	}

	public String getName(int length) {
		return getName().substring(0, Math.min(length - 1, getName().length()));
	}
}
