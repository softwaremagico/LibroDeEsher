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
	private SkillType type;
	private Category category;

	public Skill(String name, SkillType type) {
		this.type = type;
		specialities = new ArrayList<>();
		String specialityPattern = Pattern.quote("[");
		String[] nameColumns = name.split(specialityPattern);
		this.name = nameColumns[0].trim();
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
		switch (getCategory().getType()) {
		case STANDARD:
		case COMBINED:
		case LIMITED:
		case SPECIAL:
			return getCategory().getSkillRankValues(ranksNumber);
		case PPD:
			if (character.getRealmOfMagic().equals(RealmOfMagic.ESSENCE)) {
				return getCategory().getSkillRankValues(
						ranksNumber,
						character.getRace().getProgressionRankValues(
								ProgressionCostType.ESSENCE_POWER_DEVELOPMENT));
			} else if (character.getRealmOfMagic().equals(RealmOfMagic.CANALIZATION)) {
				return getCategory().getSkillRankValues(
						ranksNumber,
						character.getRace().getProgressionRankValues(
								ProgressionCostType.CANALIZATION_POWER_DEVELOPMENT));
			} else if (character.getRealmOfMagic().equals(RealmOfMagic.MENTALISM)) {
				return getCategory().getSkillRankValues(
						ranksNumber,
						character.getRace().getProgressionRankValues(
								ProgressionCostType.MENTALISM_POWER_DEVELOPMENT));
			} else if (character.getRealmOfMagic().equals(RealmOfMagic.PSIONIC)) {
				return getCategory().getSkillRankValues(
						ranksNumber,
						character.getRace().getProgressionRankValues(
								ProgressionCostType.PSIONIC_POWER_DEVELOPMENT));
			}
		case FD:
			return getCategory().getSkillRankValues(ranksNumber,
					character.getRace().getProgressionRankValues(ProgressionCostType.PHYSICAL_DEVELOPMENT));
		default:
			return 0;
		}
	}
}
