package com.softwaremagico.librodeesher.pj.perk;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.google.gson.annotations.Expose;
import com.softwaremagico.librodeesher.pj.categories.Category;
import com.softwaremagico.librodeesher.pj.skills.Skill;
import com.softwaremagico.persistence.StorableObject;

@Entity
@Table(name = "T_PERKS_DECISION")
public class PerkDecision extends StorableObject {
	private static final long serialVersionUID = 8122359267738308670L;

	@Expose
	@ElementCollection
	@CollectionTable(name = "T_PERKS_DECISION_CATEGORY_BONUS_CHOSEN")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<String> categoriesBonusChosen;
	
	@Expose
	@ElementCollection
	@CollectionTable(name = "T_PERKS_DECISION_SKILLS_BONUS_CHOSEN")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<String> skillsBonusChosen;
	
	@Expose
	@ElementCollection
	@CollectionTable(name = "T_PERKS_DECISION_COMMON_SKILLS_CHOSEN")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<String> commonSkillsChosen;

	public PerkDecision() {

	}

	@Override
	public void resetIds() {
		resetIds(this);
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
