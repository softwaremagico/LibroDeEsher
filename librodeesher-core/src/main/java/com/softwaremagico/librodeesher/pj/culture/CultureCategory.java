package com.softwaremagico.librodeesher.pj.culture;

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
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.softwaremagico.librodeesher.basics.Spanish;
import com.softwaremagico.librodeesher.pj.categories.CategoryFactory;
import com.softwaremagico.librodeesher.pj.skills.Skill;
import com.softwaremagico.librodeesher.pj.skills.SkillFactory;

public class CultureCategory implements Comparable<CultureCategory> {
	private Integer ranks;
	private Integer skillRanksToChoose;
	private HashMap<String, CultureSkill> skills;
	private List<String> categoryOptions; // List to choose one category from.

	public CultureCategory(String name, Integer ranks) {
		skills = new HashMap<>();
		categoryOptions = new ArrayList<String>();
		categoryOptions.add(name);
		this.ranks = ranks;
		skillRanksToChoose = 0;
	}

	public CultureCategory(String name, String ranks) throws InvalidCultureException {
		categoryOptions = new ArrayList<String>();
		categoryOptions.add(name);
		skills = new HashMap<>();
		try {
			this.ranks = Integer.parseInt(ranks);
		} catch (NumberFormatException nfe) {
			throw new InvalidCultureException("Error al obtener los rangos de la categoria cultural: "
					+ categoryOptions + ". " + nfe.getMessage());
		}
	}

	public CultureCategory(String[] options, String ranks) throws InvalidCultureException {
		categoryOptions = new ArrayList<String>(Arrays.asList(options));
		Collections.sort(categoryOptions);
		skills = new HashMap<>();
		try {
			this.ranks = Integer.parseInt(ranks);
		} catch (NumberFormatException nfe) {
			throw new InvalidCultureException("Error al obtener los rangos de la categoria cultural: "
					+ categoryOptions + ". Razón: " + nfe.getMessage());
		}
	}

	public CultureSkill addSkill(String skillName) {
		CultureSkill skill = skills.get(skillName);
		if (skill == null) {
			skill = new CultureSkill(skillName);
			skills.put(skillName, skill);
		}
		return skill;
	}

	public List<CultureSkill> getSkills() {
		return new ArrayList<CultureSkill>(skills.values());
	}

	public CultureSkill addSkillFromLine(String skillLine) throws InvalidCultureException {
		skillLine = skillLine.replace("*", "").trim();
		String[] skillColumns = skillLine.split("\t");
		if (skillColumns[0].toLowerCase().equals(Spanish.WEAPON)
				|| skillColumns[0].toLowerCase().equals(Spanish.CULTURE_SPELLS)
				|| skillColumns[0].toLowerCase().equals(Spanish.CULTURE_LANGUAGE_TAG)
				|| skillColumns[0].toLowerCase().equals(Spanish.ANY_SKILL)) {
			try {
				skillRanksToChoose = Integer.parseInt(skillColumns[1]);
			} catch (NumberFormatException nfe) {
				throw new InvalidCultureException("Error al obtener los rangos de la habilidad cultural: "
						+ skillLine + ". Razón: " + nfe.getMessage());
			}
			return null;
		} else {
			CultureSkill skill = addSkill(skillColumns[0]);
			skill.setRanks(skillColumns[1]);
			return skill;
		}
	}

	/**
	 * This ranks are for magic and weapons only.
	 * 
	 * @return
	 */
	public Integer getSkillRanksToChoose() {
		if (skillRanksToChoose == null) {
			return 0;
		}
		return skillRanksToChoose;
	}

	public Integer getRanks() {
		if (ranks == 0) {
			return 0;
		}
		return ranks;
	}

	public List<String> getCategoryOptions() {
		return categoryOptions;
	}

	/**
	 * When selecting the culture, in some cultures you have a specific category
	 * with a list of skills with ranks. In other case, you can choose some
	 * categories and some skills to set ranks.
	 * 
	 * @param cultureCategory
	 * @param selectedCategory
	 * @return
	 */
	public List<Skill> getCultureSkills(String selectedCategory) {
		// return option skills if no category to choose.
		if (getCategoryOptions().isEmpty() || getCategoryOptions().size() == 1) {
			List<Skill> skills = new ArrayList<Skill>();
			for (CultureSkill skill : getSkills()) {
				skills.add(SkillFactory.getSkill(skill.getName()));
			}
			return skills;
		} else {
			return CategoryFactory.getCategory(selectedCategory).getNonRareSkills();
		}
	}

	public boolean isUseful() {
		return (ranks + (skillRanksToChoose != null ? skillRanksToChoose : 0)) > 0;
	}

	@Override
	public int compareTo(CultureCategory cultureCategory) {
		return getCategoryOptions().get(0).compareTo(cultureCategory.getCategoryOptions().get(0));
	}

	@Override
	public String toString() {
		return categoryOptions.toString();
	}
}
