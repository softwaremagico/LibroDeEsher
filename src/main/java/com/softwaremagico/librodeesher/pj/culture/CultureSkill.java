package com.softwaremagico.librodeesher.pj.culture;

import com.softwaremagico.librodeesher.gui.MostrarMensaje;
import com.softwaremagico.librodeesher.pj.skills.Skill;

public class CultureSkill extends Skill {
	protected Integer ranks;

	public CultureSkill(String name) {
		super(name);
	}

	public void setRanks(Integer ranks) {
		this.ranks = ranks;
	}

	public void setRanks(String ranks) {
		try {
			this.ranks = Integer.parseInt(ranks);
		} catch (NumberFormatException nfe) {
			MostrarMensaje.showErrorMessage("Error al obtener los rangos de la habilidad cultural: "
					+ getName(), "Añadir habilidades de cultura.");
		}
	}

}
