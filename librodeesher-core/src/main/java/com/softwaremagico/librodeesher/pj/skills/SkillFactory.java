package com.softwaremagico.librodeesher.pj.skills;

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
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.softwaremagico.librodeesher.basics.Spanish;
import com.softwaremagico.librodeesher.pj.categories.Category;
import com.softwaremagico.librodeesher.pj.categories.CategoryFactory;
import com.softwaremagico.log.EsherLog;

public class SkillFactory {
	private static HashMap<String, Skill> availableSkills = new HashMap<>();
	private static List<String> availableSkillsByName = new ArrayList<>();
	private static Set<String> disabledSkills = new HashSet<>();
	private static List<Skill> weaponSkills;
	private static List<String> languages = null;

	public static Skill getSkill(String skillNameAndType) {
		Skill skill = availableSkills.get(skillNameAndType);
		if (skill == null && !skillNameAndType.toLowerCase().equals(Spanish.WEAPON.toLowerCase())
				&& !skillNameAndType.toLowerCase().equals(Spanish.NOT_IMPORTANT.toLowerCase())) {
			skill = createSkill(skillNameAndType);
			addSkill(skill);
		}
		return skill;
	}

	public static List<Skill> getSkills(List<String> skillsNames) {
		List<Skill> skills = new ArrayList<>();
		for (String skillName : skillsNames) {
			Skill skill = getSkill(skillName);
			skills.add(skill);
		}
		return skills;
	}

	public static Skill getSkill(String skillNameAndType, List<String> specialities) {
		Skill skill = getSkill(skillNameAndType);
		if (skill != null) {
			skill.addSpecialities(specialities);
		}
		return skill;
	}

	public static void setAvailableSkill(String[] skills) {
		for (String skillParameter : skills) {
			String skillName = skillParameter.trim();
			if (!skillName.equals(Spanish.NOT_IMPORTANT)) {
				Skill skill = createSkill(skillName);
				addSkill(skill);
			}
		}
	}

	public static List<String> getAvailableLanguages() {
		if (languages == null) {
			languages = new ArrayList<>();
			Category communication = CategoryFactory.getCategory(Spanish.COMUNICATION_CATEGORY);
			for (Skill skill : communication.getSkills()) {
				// Use speaking for selecting languages values.
				if (skill.getName().startsWith(Spanish.SPOKEN_TAG)) {
					languages.add(skill.getName().replace(Spanish.SPOKEN_TAG, "").trim());
				}
			}
		}
		return languages;
	}

	public static List<String> getAvailableSkillsNames() {
		if (availableSkillsByName.isEmpty()) {
			CategoryFactory.readCategories();
		}
		return availableSkillsByName;
	}
	
	private static HashMap<String, Skill> getAvailableSkills(){
		if (availableSkills.isEmpty()) {
			CategoryFactory.readCategories();
		}
		return availableSkills;
	}

	public static void addSkill(Skill skill) {
		availableSkills.put(skill.getName(), skill);
		availableSkillsByName.add(skill.getName());
		Collections.sort(availableSkillsByName);
	}

	public static Skill getAvailableSkill(String skillName) {
		return getAvailableSkills().get(skillName);
	}

	public static boolean existsSkill(String skillName) {
		return (getAvailableSkill(skillName.trim()) != null);
	}

	public static Skill getSkill(String skillName, SkillType skillType) {
		Skill skill = getAvailableSkills().get(skillName);
		if (skill == null) {
			skill = createSkill(skillName, skillType);
			addSkill(skill);
		}
		return skill;
	}

	public static Skill getSkill(String skillPrefix, String containText) {
		for (Skill skill : getAvailableSkills().values()) {
			if (skill.getName().toLowerCase().startsWith(skillPrefix) && skill.getName().toLowerCase().contains((containText))) {
				return skill;
			}
		}
		return null;
	}

	private static Skill createSkill(String skillNameAndType) {
		SkillType skillType = SkillType.getSkillType(skillNameAndType);
		Skill skill = createSkill(removeTypeFromName(skillNameAndType), skillType);
		// "*" is used to avoid skills to be used in random characters.
		skill.setRare(skillNameAndType.contains("*"));
		// Add all disabled skills to list.
		disabledSkills.addAll(skill.getEnableSkills());
		return skill;
	}

	private static Skill createSkill(String skillName, SkillType skillType) {
		SkillGroup group = SkillGroup.STANDARD;
		if (skillName.toLowerCase().startsWith(Spanish.CHI_SUFIX.toLowerCase())) {
			group = SkillGroup.CHI;
		}
		if (skillName.startsWith(Spanish.FIREARMS_SKILL1) || skillName.startsWith(Spanish.FIREARMS_SKILL2) || skillName.startsWith(Spanish.FIREARMS_SKILL3)) {
			group = SkillGroup.FIREARM;
		}
		return new Skill(skillName, skillType, group);
	}

	private static String removeTypeFromName(String skillName) {
		// String pattern = Pattern.quote("*");
		return skillName.replace("*", "").replace("(R)", "").replace("(r)", "").replace("(C)", "").replace("(c)", "").replace("(P)", "").replace("(p)", "")
				.trim();
	}

	public static List<Skill> getSkills() {
		List<Skill> skills = new ArrayList<>(availableSkills.values());
		Collections.sort(skills, new SkillComparatorByName());
		return skills;
	}

	public static List<Skill> getWeaponSkills() {
		if (weaponSkills == null) {
			weaponSkills = new ArrayList<>();
			List<Category> weaponCategories = CategoryFactory.getWeaponsCategories();
			for (Category weapon : weaponCategories) {
				weaponSkills.addAll(getSkills(weapon));
			}
			Collections.sort(weaponSkills, new SkillComparatorByName());
		}
		return weaponSkills;
	}

	public static List<Skill> getSkills(Category category) {
		List<Skill> skills = new ArrayList<>();
		for (Skill skill : getAvailableSkills().values()) {
			try {
				if (skill.getCategory() == null) {
					EsherLog.severe(SkillFactory.class.getName(), "Skill '" + skill.getName() + "' has no category defined in '" + category + "'.");
				} else if (skill != null && skill.getCategory().equals(category)) {
					skills.add(skill);
				}
			} catch (Exception e) {
				EsherLog.errorMessage(SkillFactory.class.getName(), e);
			}
		}
		Collections.sort(skills, new SkillComparatorByName());
		return skills;
	}

	public static List<Skill> getSkills(String skillPrefix) {
		List<Skill> skillsForPrefix = new ArrayList<>();
		for (Skill skill : getAvailableSkills().values()) {
			if (skill.getName().toLowerCase().startsWith(skillPrefix.toLowerCase())) {
				skillsForPrefix.add(skill);
			}
		}
		return skillsForPrefix;
	}

	public static void updateDisabledSkills() throws InvalidSkillException {
		for (String skillDisabled : disabledSkills) {
			if (getAvailableSkills().get(skillDisabled) == null) {
				throw new InvalidSkillException("Skill '" + skillDisabled + "' does not exist.");
			}
		}
		for (Skill skill : getAvailableSkills().values()) {
			if (disabledSkills.contains(skill.getName())) {
				skill.setEnabled(false);
			}
		}
	}

	public static List<String> getSkillNames(Collection<Skill> skills) {
		List<String> names = new ArrayList<>();
		for (Skill skill : skills) {
			names.add(skill.getName());
		}
		return names;
	}

	public void reset() {
		availableSkills = new HashMap<>();
		availableSkillsByName = new ArrayList<>();
		disabledSkills = new HashSet<>();
	}

	public static List<String> toString(List<Skill> skills) {
		List<String> skillNames = new ArrayList<>();
		for (Skill skill : skills) {
			skillNames.add(skill.getName());
		}
		return skillNames;
	}
}
