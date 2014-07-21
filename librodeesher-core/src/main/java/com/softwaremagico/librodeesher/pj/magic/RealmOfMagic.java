package com.softwaremagico.librodeesher.pj.magic;

import com.softwaremagico.librodeesher.pj.characteristic.CharacteristicsAbbreviature;

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

public enum RealmOfMagic {
	CANALIZATION("Canalización", CharacteristicsAbbreviature.INTUITION),

	ESSENCE("Esencia", CharacteristicsAbbreviature.EMPATHY),

	MENTALISM("Mentalismo", CharacteristicsAbbreviature.PRESENCE),

	PSIONIC("Psiónico", CharacteristicsAbbreviature.SELFDISCIPLINE),

	ARCHANUM("Arcano", CharacteristicsAbbreviature.NULL),

	RACE("Racial", CharacteristicsAbbreviature.NULL);

	private String name;
	private CharacteristicsAbbreviature characteristic;

	private RealmOfMagic(String tag, CharacteristicsAbbreviature characteristic) {
		this.name = tag;
		this.characteristic = characteristic;
	}

	public String getName() {
		return name;
	}

	public CharacteristicsAbbreviature getCharacteristic() {
		return characteristic;
	}

	public static RealmOfMagic getMagicRealm(String name) {
		for (RealmOfMagic type : RealmOfMagic.values()) {
			if (type.name.equals(name)) {
				return type;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return getName();
	}

}
