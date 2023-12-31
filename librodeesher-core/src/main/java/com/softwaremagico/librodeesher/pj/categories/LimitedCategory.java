package com.softwaremagico.librodeesher.pj.categories;

import java.util.Arrays;
import java.util.List;

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

public class LimitedCategory extends Category {
	private static List<Float> skillRankValues = Arrays.asList((float) 0, (float) 1, (float) 1, (float) 0.5, (float) 0);

	public LimitedCategory(String name, String abbreviature, String characteristicsTag) {
		super(name, abbreviature, CategoryType.LIMITED, characteristicsTag, skillRankValues);
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
		if (ranksNumber <= 20) {
			return ranksNumber;
		}
		if (ranksNumber > 20 && ranksNumber < 30) {
			return 20 + (ranksNumber - 20) / 2;
		}
		if (ranksNumber >= 30) {
			return 25;
		}
		return 0;
	}

	@Override
	public boolean hasRanks() {
		return false;
	}

}
