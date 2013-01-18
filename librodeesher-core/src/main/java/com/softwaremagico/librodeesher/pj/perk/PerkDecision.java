package com.softwaremagico.librodeesher.pj.perk;

import java.util.List;

import com.softwaremagico.librodeesher.pj.categories.Category;
import com.softwaremagico.librodeesher.pj.skills.Skill;

public class PerkDecision {
	private List<String> categoriesBonusChosen;
	private List<String> skillsBonusChosen;

	public PerkDecision() {

	}

	public void setCategoriesBonusChoosen(List<String> chose) {
		categoriesBonusChosen = chose;
	}

	public void setSkillsBonusChoosen(List<String> chose) {
		skillsBonusChosen = chose;
	}

	public boolean isBonusChosen(Skill skill) {
		return skillsBonusChosen.contains(skill.getName());
	}

	public boolean isBonusChosen(Category category) {
		return categoriesBonusChosen.contains(category.getName());
	}
	
	public boolean isCommon(Skill skill){
		//TODO
		return false;
	}
	
	public boolean isCommon(Category category){
		//TODO
		return false;
	}
}
