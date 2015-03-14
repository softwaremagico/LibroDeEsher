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

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.google.gson.annotations.Expose;
import com.softwaremagico.persistence.StorableObject;

@Entity
@Table(name = "T_CATEGORY_COST")
public class CategoryCost extends StorableObject {
	private static final long serialVersionUID = -146580302207852563L;
	@Expose
	@ElementCollection
	@CollectionTable(name = "T_RANK_COSTS")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Integer> rankCost;

	// When importing from JSON, comparatorId is not the same as the profession
	// file.
	@Expose
	private String categoryCostId = null;

	protected CategoryCost() {
		rankCost = new ArrayList<>();
	}

	public CategoryCost(List<Integer> rankCost) {
		this.rankCost = rankCost;
	}

	public CategoryCost(String rankCost) throws InvalidCategoryException {
		this.rankCost = covertStringToCost(rankCost);
	}

	@Override
	public void resetIds() {
		resetIds(this);
	}

	public static List<Integer> covertStringToCost(String costString) throws InvalidCategoryException {
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
			throw new InvalidCategoryException("Coste mal formado: " + costString);
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

	public String getCategoryCostId() {
		return categoryCostId;
	}

	public void setCategoryCostId(String categoryCostId) {
		this.categoryCostId = categoryCostId;
	}

	/**
	 * ComparatorId is not valid as equals when importing weapons from Json.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		if (categoryCostId == null) {
			return super.hashCode();
		}
		int result = prime * ((categoryCostId == null) ? 0 : categoryCostId.hashCode());
		return result;
	}

	/**
	 * ComparatorId is not valid as equals when importing weapons from Json.
	 */
	@Override
	public boolean equals(Object obj) {
		if (categoryCostId == null) {
			return super.equals(obj);
		}
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		CategoryCost other = (CategoryCost) obj;
		if (categoryCostId == null) {
			if (other.categoryCostId != null)
				return false;
		} else if (!categoryCostId.equals(other.categoryCostId))
			return false;
		return true;
	}
}
