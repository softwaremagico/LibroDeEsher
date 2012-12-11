package com.softwaremagico.librodeesher.pj.profession;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.softwaremagico.librodeesher.gui.MostrarMensaje;
import com.softwaremagico.librodeesher.pj.skills.Skill;
import com.softwaremagico.librodeesher.pj.skills.SkillFactory;

public class ChooseSkillGroup {
	int chooseNumber;
	List<Skill> skillGroup;

	public ChooseSkillGroup(int chooseNumber, List<Skill> skillGroup) {
		this.chooseNumber = chooseNumber;
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
				MostrarMensaje.showErrorMessage("Error leyendo un conjunto de categorias. Fallo en: "
						+ skillName, "Leer Profesión");
			}
		}
	}
}
