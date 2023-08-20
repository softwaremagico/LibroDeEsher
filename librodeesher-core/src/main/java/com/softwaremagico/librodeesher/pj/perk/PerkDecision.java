package com.softwaremagico.librodeesher.pj.perk;

import java.util.HashSet;
import java.util.Set;

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
	private Set<String> categoriesBonusChosen;

	@Expose
	@ElementCollection
	@CollectionTable(name = "T_PERKS_DECISION_CATEGORY_RANKS_CHOSEN")
	@LazyCollection(LazyCollectionOption.FALSE)
	private Set<String> categoriesRanksChosen;

	@Expose
	@ElementCollection
	@CollectionTable(name = "T_PERKS_DECISION_SKILLS_BONUS_CHOSEN")
	@LazyCollection(LazyCollectionOption.FALSE)
	private Set<String> skillsBonusChosen;

	@Expose
	@ElementCollection
	@CollectionTable(name = "T_PERKS_DECISION_SKILLS_RANKS_CHOSEN")
	@LazyCollection(LazyCollectionOption.FALSE)
	private Set<String> skillsRanksChosen;

	@Expose
	@ElementCollection
	@CollectionTable(name = "T_PERKS_DECISION_COMMON_SKILLS_CHOSEN")
	@LazyCollection(LazyCollectionOption.FALSE)
	private Set<String> commonSkillsChosen;

	public PerkDecision() {
		categoriesBonusChosen = new HashSet<>();
		skillsBonusChosen = new HashSet<>();
		commonSkillsChosen = new HashSet<>();
		skillsRanksChosen = new HashSet<>();
		categoriesRanksChosen = new HashSet<>();
	}

	@Override
	public void resetIds() {
		resetIds(this);
	}

	@Override
	public void resetComparationIds() {
		resetComparationIds(this);
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

	public Set<String> getCommonSkillsChosen() {
		return commonSkillsChosen;
	}

	public void setCommonSkillsChosen(Set<String> commonSkillsChosen) {
		this.commonSkillsChosen = commonSkillsChosen;
	}

	public Set<String> getCategoriesBonusChosen() {
		return categoriesBonusChosen;
	}

	public void setCategoriesBonusChosen(Set<String> categoriesBonusChosen) {
		this.categoriesBonusChosen = categoriesBonusChosen;
	}

	public Set<String> getSkillsBonusChosen() {
		return skillsBonusChosen;
	}

	public void setSkillsBonusChosen(Set<String> skillsBonusChosen) {
		this.skillsBonusChosen = skillsBonusChosen;
	}

	public Set<String> getCategoriesRanksChosen() {
		return categoriesRanksChosen;
	}

	public void setCategoriesRanksChosen(Set<String> categoriesRanksChosen) {
		this.categoriesRanksChosen = categoriesRanksChosen;
	}

	public Set<String> getSkillsRanksChosen() {
		return skillsRanksChosen;
	}

	public void setSkillsRanksChosen(Set<String> skillsRanksChosen) {
		this.skillsRanksChosen = skillsRanksChosen;
	}
	
}
