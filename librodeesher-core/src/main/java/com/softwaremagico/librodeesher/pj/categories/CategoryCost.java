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

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.softwaremagico.librodeesher.basics.ShowMessage;
import com.softwaremagico.persistence.StorableObject;

@Entity
@Table(name = "T_CATEGORY_COST")
public class CategoryCost extends StorableObject {

	@ElementCollection
	@CollectionTable(name = "T_RANK_COSTS")
	private List<Integer> rankCost;

	public CategoryCost(List<Integer> rankCost) {
		this.rankCost = rankCost;
	}

	public CategoryCost(String rankCost) {
		this.rankCost = covertStringToCost(rankCost);
	}

	public static List<Integer> covertStringToCost(String costString) {
		List<Integer> cost = new ArrayList<>();
		String[] costColumn = costString.split("/");
		try {
			cost.add(Integer.parseInt(costColumn[0]));
			if (costColumn.length > 1) {
				cost.add(Integer.parseInt(costColumn[1]));
				if (costColumn.length > 2) {
					cost.add(Integer.parseInt(costColumn[2]));
				}
			}
		} catch (Exception e) {
			ShowMessage.showErrorMessage("Coste mal formado.", "Personaje");
		}

		return cost;
	}

	public Integer getTotalRanksCost(Integer getRanksAdquired) {
		Integer total = 0;
		for (int i = 0; i < getRanksAdquired; i++) {
			total += getRankCost(i);
		}
		return total;
	}

	public Integer getRankCost(Integer rankAdquiredInThisLevel) {
		if (rankCost != null && rankAdquiredInThisLevel < rankCost.size()) {
			return rankCost.get(rankAdquiredInThisLevel);
		}
		return null;
	}

	public Integer getMaxRanksPerLevel() {
		return rankCost.size();
	}

	public String getCostTag() {
		String tag = "";
		for (int i = 0; i < rankCost.size(); i++) {
			tag += rankCost.get(i);
			if (i < rankCost.size() - 1) {
				tag += "/";
			}
		}
		return tag;
	}

	@Override
	public String toString() {
		return getCostTag();
	}

	public List<Integer> getRankCost() {
		return rankCost;
	}

	protected void setRankCost(List<Integer> rankCost) {
		this.rankCost = rankCost;
	}
}
