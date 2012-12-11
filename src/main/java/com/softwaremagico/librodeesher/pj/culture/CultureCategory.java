package com.softwaremagico.librodeesher.pj.culture;

import com.softwaremagico.librodeesher.gui.MostrarMensaje;
import com.softwaremagico.librodeesher.pj.categories.Category;
import com.softwaremagico.librodeesher.pj.categories.SimpleCategory;

public class CultureCategory extends SimpleCategory {
	private Integer ranks;
	private Integer ranksToChoose;

	public CultureCategory(String name, Integer ranks) {
		super(name);
		this.ranks = ranks;
		ranksToChoose = 0;
	}

	public CultureCategory(String name, String ranks) {
		super(name);
		try {
			this.ranks = Integer.parseInt(ranks);
		} catch (NumberFormatException nfe) {
			MostrarMensaje.showErrorMessage("Error al obtener los rangos de la categoria cultural: "
					+ getName(), "Añadir categorias de cultura.");
		}
	}

	public CultureSkill addSkillFromLine(String skillLine) {
		skillLine = skillLine.replace("*", "").trim();
		String[] skillColumns = skillLine.split("\t");
		if (skillColumns[0].equals("Arma") || skillColumns[0].equals("Lista de Hechizos")) {
			try {
				ranksToChoose = Integer.parseInt(skillColumns[1]);
			} catch (NumberFormatException nfe) {
				MostrarMensaje.showErrorMessage("Error al obtener los rangos de la habilidad cultural: "
						+ skillLine, "Añadir habilidades de cultura.");
			}
			return null;
		} else {
			CultureSkill skill = (CultureSkill) addSkill(skillColumns[0]);
			skill.setRanks(skillColumns[1]);
			if (skill != null) {
				MostrarMensaje.showErrorMessage("Error al obtener los rangos de la habilidad cultural: "
						+ skillLine, "Añadir habilidades de cultura.");
			}
			return skill;
		}
	}

}
