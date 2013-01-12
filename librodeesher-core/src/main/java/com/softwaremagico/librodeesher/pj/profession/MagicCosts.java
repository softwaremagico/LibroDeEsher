package com.softwaremagico.librodeesher.pj.profession;

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

import java.util.Hashtable;

import com.softwaremagico.librodeesher.core.ShowMessage;
import com.softwaremagico.librodeesher.pj.categories.CategoryCost;
import com.softwaremagico.librodeesher.pj.magic.MagicLevelRange;
import com.softwaremagico.librodeesher.pj.magic.MagicListType;

public class MagicCosts {
	private Hashtable<MagicListType, CategoryCost[]> listCosts;

	public MagicCosts() {
		listCosts = new Hashtable<>();
		for (MagicListType magicType : MagicListType.values()) {
			listCosts.put(magicType, new CategoryCost[MagicLevelRange.values().length]);
		}
	}

	public boolean setMagicCost(MagicListType listType, MagicLevelRange level, String cost) {
		if (listType == null || level == null || cost == null) {
			ShowMessage.showErrorMessage("Coste de lista de hechizos mal formado.", "Asignar costes magia");
			return false;
		}
		CategoryCost[] listCosts = getListCost(listType);
		Integer levelRange = getListLevelSeparation(level);
		CategoryCost listCost = new CategoryCost(cost);
		if (listCosts != null && listCost != null && levelRange != null && levelRange >= 0
				&& levelRange < listCosts.length) {
			listCosts[levelRange] = listCost;
			return true;
		} else {
			ShowMessage.showErrorMessage("Coste de lista de hechizos mal formado.", "Asignar costes magia");
		}
		return false;
	}

	private CategoryCost[] getListCost(MagicListType listType) {
		return listCosts.get(listType);
	}

	public CategoryCost getListCost(MagicListType listType, MagicLevelRange levelRange) {
		return getListCost(listType)[getListLevelSeparation(levelRange)];
	}

	private Integer getListLevelSeparation(MagicLevelRange level) {
		switch (level) {
		case FIRST_FIVE_LEVELS:
			return 0;
		case SECOND_FIVE_LEVELS:
			return 1;
		case THIRD_FIVE_LEVELS:
			return 2;
		case FOURTH_FIVE_LEVELS:
			return 3;
		default:
			return 4;
		}
	}
}
