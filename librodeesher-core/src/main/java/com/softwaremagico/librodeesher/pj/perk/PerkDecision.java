package com.softwaremagico.librodeesher.pj.perk;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.softwaremagico.librodeesher.pj.categories.Category;
import com.softwaremagico.librodeesher.pj.skills.Skill;

@Entity
@Table(name = "T_PERKS_DECISION")
public class PerkDecision {
	@Id
	@GeneratedValue
	private Long id; // database id.

	@ElementCollection
	@CollectionTable(name = "T_PERKS_DECISION_CATEGORY_BONUS_CHOSEN")
	private List<String> categoriesBonusChosen;
	@ElementCollection
	@CollectionTable(name = "T_PERKS_DECISION_SKILLS_BONUS_CHOSEN")
	private List<String> skillsBonusChosen;
	@ElementCollection
	@CollectionTable(name = "T_PERKS_DECISION_COMMON_SKILLS_CHOSEN")
	private List<String> commonSkillsChosen;

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

	public boolean isCommon(Skill skill) {
		return commonSkillsChosen.contains(skill.getName());
	}

	public List<String> getCommonSkillsChosen() {
		return commonSkillsChosen;
	}

	public void setCommonSkillsChosen(List<String> commonSkillsChosen) {
		this.commonSkillsChosen = commonSkillsChosen;
	}

	protected Long getId() {
		return id;
	}

	protected void setId(Long id) {
		this.id = id;
	}

	protected List<String> getCategoriesBonusChosen() {
		return categoriesBonusChosen;
	}

	protected void setCategoriesBonusChosen(List<String> categoriesBonusChosen) {
		this.categoriesBonusChosen = categoriesBonusChosen;
	}

	protected List<String> getSkillsBonusChosen() {
		return skillsBonusChosen;
	}

	protected void setSkillsBonusChosen(List<String> skillsBonusChosen) {
		this.skillsBonusChosen = skillsBonusChosen;
	}
}
