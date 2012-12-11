package com.softwaremagico.librodeesher.pj.categories;

import java.util.ArrayList;
import java.util.List;

import com.softwaremagico.librodeesher.gui.MostrarMensaje;

public class CategoryCost {
	List<Integer> rankCost;

	public CategoryCost(List<Integer> rankCost) {
		this.rankCost = rankCost;
	}

	public CategoryCost(String rankCost) {
		this.rankCost = covertStringToCost(rankCost);
	}

	public Integer getCost(int rankAdquiredInThisLevel) {
		if (rankCost != null && rankAdquiredInThisLevel < rankCost.size()) {
			return rankCost.get(rankAdquiredInThisLevel);
		}
		return null;
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
			MostrarMensaje.showErrorMessage("Coste mal formado.", "Personaje");
		}

		return cost;
	}
}
