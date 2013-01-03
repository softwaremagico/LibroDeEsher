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

import com.softwaremagico.librodeesher.pj.skills.Skill;
import com.softwaremagico.librodeesher.pj.skills.SkillFactory;

public abstract class Category {
	private String name;
	protected String abbreviature;
	protected CategoryType type;
	protected CategoryGroup group;
	private String characterisitcsTags;
	private List<String> characteristicsListTags;
	protected List<Skill> skills;
	private Float[] skillRankValues; // Rank values. i.e: -15/3/2/1/0.5

	public Category(String name, String abbreviature, CategoryType type, String characteristicsTag,
			Float[] skillRankValues) {
		this.name = name;
		this.abbreviature = abbreviature;
		this.type = type;
		this.skillRankValues = skillRankValues;
		skills = new ArrayList<>();
		this.characterisitcsTags = characteristicsTag;
		createCharacteristicList(characteristicsTag);
		setGroup();
	}

	private void createCharacteristicList(String characteristicsTag) {
		characteristicsListTags = new ArrayList<>();
		String[] characteristics = characteristicsTag.split(Pattern.quote("/"));
		for (String characteristic : characteristics) {
			characteristicsListTags.add(characteristic);
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

	public String getAbbreviature() {
		return abbreviature;
	}

	public List<Skill> getSkills() {
		return skills;
	}

	public CategoryType getType() {
		return type;
	}

	public void setSkills(List<Skill> skills) {
		this.skills = skills;
		for (Skill skill : skills) {
			skill.setCategory(this);
		}
		Collections.sort(skills, new SkillComparator());
	}

	public void addSkills(List<Skill> skills) {
		this.skills.addAll(skills);
		for (Skill skill : skills) {
			skill.setCategory(this);
		}
		Collections.sort(skills, new SkillComparator());
	}

	public Skill addSkill(String skillName) {
		if (skillName.length() > 0) {
			Skill skill = SkillFactory.getSkill(skillName);
			skill.setCategory(this);
			if (!skills.contains(skill)) {
				skills.add(skill);
				Collections.sort(skills, new SkillComparator());
			}
			return skill;
		}
		return null;
	}

	public Integer getRankValue(Integer ranksNumber) {
		return 0;
	}

	public Integer getSkillRankValues(Integer ranksNumber) {
		return getSkillRankValues(ranksNumber, skillRankValues);
	}

	public Integer getSkillRankValues(Integer ranksNumber, Float[] definedSkillRankValues) {
		if (ranksNumber == 0) {
			return definedSkillRankValues[0].intValue();
		} else if (ranksNumber > 0 && ranksNumber <= 10) {
			return definedSkillRankValues[1].intValue() * ranksNumber;
		} else if (ranksNumber > 10 && ranksNumber <= 20) {
			return definedSkillRankValues[1].intValue() * 10 + definedSkillRankValues[2].intValue()
					* (ranksNumber - 10);
		} else if (ranksNumber > 20 && ranksNumber <= 30) {
			return definedSkillRankValues[1].intValue() * 10 + definedSkillRankValues[2].intValue() * 10
					+ definedSkillRankValues[3].intValue() * (ranksNumber - 20);
		} else {
			return definedSkillRankValues[1].intValue() * 10 + definedSkillRankValues[2].intValue() * 10
					+ definedSkillRankValues[3].intValue() * 10 + definedSkillRankValues[4].intValue()
					* (ranksNumber - 30);
		}
	}

	public static Float[] getConvertedProgressionString(String progression) {
		Scanner s = new Scanner(progression);
		s.useDelimiter(Pattern.quote("/"));
		Float[] progressionCost = new Float[5];
		int i = 0;
		while (s.hasNext() && i < progressionCost.length) {
			progressionCost[i] = s.nextFloat();
			i++;
		}
		return progressionCost;
	}

	public abstract boolean hasRanks();

	public Integer getBonus() {
		return 0;
	}

	public List<String> getCharacteristics() {
		return characteristicsListTags;
	}

	public CategoryGroup getGroup() {
		return group;
	}

	public void setGroup() {
		this.group = getCategoryGroup(this.getName());
	}

	private CategoryGroup getCategoryGroup(String categoryName) {
		if (categoryName.startsWith("Armas·")) {
			return CategoryGroup.WEAPON;
		} else if (categoryName.startsWith("Listas ")) {
			return CategoryGroup.SPELL;
		}
		return CategoryGroup.STANDARD;
	}

	public String getCharacterisitcsTags() {
		return characterisitcsTags;
	}
}
