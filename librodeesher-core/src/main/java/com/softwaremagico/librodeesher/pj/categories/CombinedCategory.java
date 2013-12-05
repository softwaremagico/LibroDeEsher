package com.softwaremagico.librodeesher.pj.categories;

import java.util.Arrays;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Table;

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

@Entity
@Table(name = "T_COMBINED_CATEGORY")
public class CombinedCategory extends Category {
	@ElementCollection
	private static List<Float> skillRankValues = Arrays.asList((float) -30, (float) 5, (float) 3, (float) 1.5,
			(float) 0.5);

	public CombinedCategory(String name, String abbreviature, String characteristicsTag) {
		super(name, abbreviature, CategoryType.COMBINED, characteristicsTag, skillRankValues);
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
			return -30;
		}
		if (ranksNumber <= 10) {
			return ranksNumber * 5;
		}
		if (ranksNumber > 10 && ranksNumber <= 20) {
			return (50 + (ranksNumber - 10) * 3);
		}
		if (ranksNumber > 20 && ranksNumber <= 25) {
			return 80 + (ranksNumber - 20) * 2;
		}
		if (ranksNumber > 25 && ranksNumber <= 30) {
			return 80 + (int) ((ranksNumber - 20) * 1.5);
		}
		if (ranksNumber > 30) {
			return 95 + (ranksNumber - 30) / 2;
		}
		return -30;
	}

	@Override
	public boolean hasRanks() {
		return false;
	}

}
