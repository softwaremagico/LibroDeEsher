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
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.softwaremagico.librodeesher.basics.ChooseGroup;
import com.softwaremagico.librodeesher.basics.ChooseType;
import com.softwaremagico.librodeesher.basics.ShowMessage;

@Entity
@Table(name = "T_CHOOSE_SKILL_GROUP")
public class ChooseSkillGroup extends ChooseGroup<Skill> {

	public ChooseSkillGroup(int chooseNumber, Skill skill, ChooseType chooseType) {
		super(chooseNumber, skill, chooseType);
	}

	public ChooseSkillGroup(int chooseNumber, List<Skill> skillGroup, ChooseType chooseType) {
		super(chooseNumber, skillGroup, chooseType);
	}

	public ChooseSkillGroup(int chooseNumber, String[] skillGroup, ChooseType chooseType) {
		super(chooseType);
		this.numberOfOptionsToChoose = chooseNumber;
		for (String skillName : skillGroup) {
			Skill skill = SkillFactory.getAvailableSkill(skillName);
			if (skill != null) {
				this.optionsGroup.add(skill);
			} else {
				ShowMessage.showErrorMessage("Error leyendo un conjunto de categorias. Fallo en: " + skillName,
						"Leer Profesión");
			}
		}
	}

	@Override
	public List<String> getOptionsAsString() {
		List<String> nameList = new ArrayList<>();
		for (Skill skill : optionsGroup) {
			nameList.add(skill.getName());
		}
		return nameList;
	}
}
