package com.softwaremagico.librodeesher.pj.categories;
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

public class SpecialCategory extends Category {
	private static Float[] skillRankValues = { (float) 0, (float) 6, (float) 5, (float) 4, (float) 3 };

	public SpecialCategory(String name, String abbreviature, String characteristicsTag) {
		super(name, abbreviature, CategoryType.SPECIAL,characteristicsTag, skillRankValues);
	}

	@Override
	public void addSkills(String skills) {
		String[] skillsArray = skills.split(",");
		for (String skill : skillsArray) {
			addSkill(skill);
		}
	}

	@Override
	public Integer getSkillRankValues(Integer ranksNumber) {
		if (ranksNumber == 0) {
			return 0;
		}
		if (ranksNumber <= 10) {
			return ranksNumber * 6;
		}
		if (ranksNumber > 10 && ranksNumber <= 20) {
			return (60 + (ranksNumber - 10) * 5);
		}
		if (ranksNumber > 20 && ranksNumber <= 30) {
			return 110 + (ranksNumber - 20) * 4;
		}
		if (ranksNumber > 30) {
			return 150 + (ranksNumber - 30) * 3;
		}
		return 0;
	}

	@Override
	public boolean hasRanks() {
		return false;
	}

}
