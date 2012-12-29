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

public class StandardCategory extends Category {
	private static Float[] skillRankValues = {(float) -15, (float)3, (float)2, (float)1, (float)0.5};

	public StandardCategory(String name, String abbreviature,
			String characteristicsTag) {
		super(name, abbreviature, CategoryType.STANDARD, skillRankValues);
	}

	@Override
	public void addSkills(String skills) {
		String[] skillsArray = skills.split(",");
		for (String skill : skillsArray) {
			addSkill(skill);
		}
	}

	@Override
	public Integer getRankValue(Integer ranksNumber) {
		if (ranksNumber == 0) {
			return -15;
		}
		if (ranksNumber <= 10) {
			return ranksNumber * 2;
		}
		if (ranksNumber > 10 && ranksNumber <= 20) {
			return (20 + (ranksNumber - 10));
		}
		if (ranksNumber > 20 && ranksNumber <= 30) {
			return 30 + (ranksNumber - 20) / 2;
		}
		if (ranksNumber > 30) {
			return 35;
		}
		return -15;
	}

	@Override
	public Integer getSkillRankValues(Integer ranksNumber) {
		if (ranksNumber == 0) {
			return -15;
		}
		if (ranksNumber <= 10) {
			return ranksNumber * 3;
		}
		if (ranksNumber > 10 && ranksNumber <= 20) {
			return (30 + (ranksNumber - 10) * 2);
		}
		if (ranksNumber > 20 && ranksNumber <= 30) {
			return 50 + (ranksNumber - 20);
		}
		if (ranksNumber > 30) {
			return 60 + (ranksNumber - 30) / 2;
		}
		return -15;
	}

	@Override
	public boolean hasRanks() {
		return true;
	}
}
