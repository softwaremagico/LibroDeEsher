package com.softwaremagico.librodeesher.pj;

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

public enum ProgressionCostType {
	PHYSICAL_DEVELOPMENT("Desarrollo Físico"),
	ARCANUM_POWER_DEVELOPMENT("PP Arcano"),
	CANALIZATION_POWER_DEVELOPMENT("PP Canalización"),
	ESSENCE_POWER_DEVELOPMENT("PP Esencia"),
	MENTALISM_POWER_DEVELOPMENT("PP Mentalismo"),
	PSIONIC_POWER_DEVELOPMENT("PP Psiónico");

	private String tag;

	ProgressionCostType(String tag) {
		this.tag = tag;
	}

	public static ProgressionCostType getProgressionCostType(String tag) {
		for (ProgressionCostType type : ProgressionCostType.values()) {
			if (type.tag.equals(tag)) {
				return type;
			}
		}
		return null;
	}

	public static ProgressionCostType getProgressionCostType(RealmOfMagic realm) {
		switch (realm) {
		case ESSENCE:
			return ESSENCE_POWER_DEVELOPMENT;
		case CANALIZATION:
			return CANALIZATION_POWER_DEVELOPMENT;
		case MENTALISM:
			return MENTALISM_POWER_DEVELOPMENT;
		case PSIONIC:
			return PSIONIC_POWER_DEVELOPMENT;
		default:
			return null;
		}
	}
}
