package com.softwaremagico.librodeesher.pj.level;

import java.util.Hashtable;

import com.softwaremagico.librodeesher.pj.profession.Profession;
import com.softwaremagico.librodeesher.pj.skills.SkillFactory;

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

public class LevelUp {
	private Hashtable<String, Integer> categoriesRanks;
	private Hashtable<String, Integer> skillsRanks;

	public LevelUp() {
		categoriesRanks = new Hashtable<>();
		skillsRanks = new Hashtable<>();
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

	private Integer getSpentDevelopmentPointsInCategoryRanks(Profession profession) {
		Integer total = 0;
		for (String categoryName : categoriesRanks.keySet()) {
			total += profession.getCategoryRanksCost(categoryName, categoriesRanks.get(categoryName));
		}
		return total;
	}

	private Integer getSpentDevelopmentPointsInSkillsRanks(Profession profession) {
		Integer total = 0;
		for (String skillName : skillsRanks.keySet()) {
			total += profession.getCategoryRanksCost(SkillFactory.getAvailableSkill(skillName).getCategory()
					.getName(), skillsRanks.get(skillName));
		}
		return total;
	}

	public Integer getSpentDevelpmentPoints(Profession profession) {
		return getSpentDevelopmentPointsInCategoryRanks(profession)
				+ getSpentDevelopmentPointsInSkillsRanks(profession);
	}
}
