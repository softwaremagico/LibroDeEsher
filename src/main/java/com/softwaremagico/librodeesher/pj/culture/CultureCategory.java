package com.softwaremagico.librodeesher.pj.culture;

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
import java.util.Hashtable;
import java.util.List;

import com.softwaremagico.librodeesher.gui.ShowMessage;

public class CultureCategory {
	private Integer ranks;
	private Integer ranksToChoose;
	private Hashtable<String, CultureSkill> skills;
	private String name;

	public CultureCategory(String name, Integer ranks) {
		skills = new Hashtable<>();
		this.name = name;
		this.ranks = ranks;
		ranksToChoose = 0;
	}

	public String getName() {
		return name;
	}

	public CultureSkill addSkill(String skillName) {
		CultureSkill skill = skills.get(skillName);
		if (skill == null) {
			skill = new CultureSkill(skillName);
			skills.put(skillName, skill);
		}
		return skill;
	}
	
	public List<CultureSkill> getSkills(){
		return new ArrayList<CultureSkill>(skills.values());
	}

	public CultureCategory(String name, String ranks) {
		this.name = name;
		skills = new Hashtable<>();
		try {
			this.ranks = Integer.parseInt(ranks);
		} catch (NumberFormatException nfe) {
			ShowMessage.showErrorMessage(
					"Error al obtener los rangos de la categoria cultural: " + getName(),
					"Añadir categorias de cultura.");
		}
	}

	public CultureSkill addSkillFromLine(String skillLine) {
		skillLine = skillLine.replace("*", "").trim();
		String[] skillColumns = skillLine.split("\t");
		if (skillColumns[0].equals("Arma") || skillColumns[0].equals("Lista de Hechizos")) {
			try {
				ranksToChoose = Integer.parseInt(skillColumns[1]);
			} catch (NumberFormatException nfe) {
				ShowMessage.showErrorMessage("Error al obtener los rangos de la habilidad cultural: "
						+ skillLine, "Añadir habilidades de cultura");
			}
			return null;
		} else {
			CultureSkill skill = addSkill(skillColumns[0]);
			skill.setRanks(skillColumns[1]);
			return skill;
		}
	}

	/**
	 * This ranks are for magic and weapons only.
	 * @return
	 */
	public Integer getChooseRanks(){
		return ranksToChoose;
	}
	
	public Integer getRanks() {
		return ranks;
	}

}
