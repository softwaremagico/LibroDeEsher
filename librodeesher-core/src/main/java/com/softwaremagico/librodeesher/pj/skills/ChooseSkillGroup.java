package com.softwaremagico.librodeesher.pj.skills;
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
import java.util.Arrays;
import java.util.List;

import com.softwaremagico.librodeesher.core.ShowMessage;

public class ChooseSkillGroup {
	int chooseNumber;
	List<Skill> skillGroup;

	public ChooseSkillGroup(int chooseNumber, List<Skill> skillGroup) {
		this.chooseNumber = chooseNumber;
		this.skillGroup = skillGroup;
	}

	public ChooseSkillGroup(int chooseNumber, Skill skill) {
		this.chooseNumber = chooseNumber;
		List<Skill> skillGroup = new ArrayList<>();
		skillGroup.add(skill);
		this.skillGroup = skillGroup;
	}

	public ChooseSkillGroup(int chooseNumber, Skill[] skillGroup) {
		this.chooseNumber = chooseNumber;
		this.skillGroup = Arrays.asList(skillGroup);
	}

	public ChooseSkillGroup(int chooseNumber, String[] skillGroup) {
		this.chooseNumber = chooseNumber;
		this.skillGroup = new ArrayList<>();
		for (String skillName : skillGroup) {
			Skill skill = SkillFactory.getAvailableSkill(skillName);
			if (skill != null) {
				this.skillGroup.add(skill);
			} else {
				ShowMessage.showErrorMessage("Error leyendo un conjunto de categorias. Fallo en: "
						+ skillName, "Leer Profesión");
			}
		}
	}
}
