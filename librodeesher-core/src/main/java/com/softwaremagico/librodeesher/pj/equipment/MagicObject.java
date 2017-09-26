package com.softwaremagico.librodeesher.pj.equipment;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.google.gson.annotations.Expose;
import com.softwaremagico.librodeesher.pj.skills.Skill;
import com.softwaremagico.librodeesher.pj.training.TrainingItem;
import com.softwaremagico.persistence.StorableObject;

@Entity
@Table(name = "T_MAGIC_OBJECT")
public class MagicObject extends StorableObject {
	private static final long serialVersionUID = -311068996858853985L;

	@Expose
	private String name = "";

	@Expose
	private String description = "";

	@Expose
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<ObjectBonus> bonus = new ArrayList<>();

	public String getName() {
		return name;
	}

	public List<ObjectBonus> getBonus() {
		return bonus;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setBonus(List<ObjectBonus> bonus) {
		this.bonus = bonus;
	}

	@Override
	public void resetIds() {
		setId(null);
		for (ObjectBonus bonus : getBonus()) {
			bonus.resetIds();
		}
	}

	@Override
	public void resetComparationIds() {
		setId(null);
		for (ObjectBonus bonus : getBonus()) {
			bonus.resetComparationIds();
		}
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getSkillBonus(String skillName) {
		for (ObjectBonus objectBonus : bonus) {
			if (objectBonus.getType().equals(BonusType.SKILL)) {
				if (objectBonus.getBonusName().equals(skillName)) {
					return objectBonus.getBonus();
				}
			}
		}
		return 0;
	}

	public void setSkillBonus(String skillName, int bonusValue) {
		setObjectBonus(skillName, BonusType.SKILL, bonusValue);
	}

	public int getCategoryBonus(String categoryName) {
		for (ObjectBonus objectBonus : bonus) {
			if (objectBonus.getType().equals(BonusType.CATEGORY)) {
				if (objectBonus.getBonusName().equals(categoryName)) {
					return objectBonus.getBonus();
				}
			}
		}
		return 0;
	}

	public void setCategoryBonus(String categoryName, int bonusValue) {
		setObjectBonus(categoryName, BonusType.CATEGORY, bonusValue);
	}

	public int getObjectBonus(BonusType type) {
		for (ObjectBonus objectBonus : new ArrayList<>(bonus)) {
			if (objectBonus.getType().equals(type)) {
				return objectBonus.getBonus();
			}
		}
		return 0;
	}

	public void setObjectBonus(String bonusName, BonusType type, int bonusValue) {
		for (ObjectBonus objectBonus : new ArrayList<>(bonus)) {
			if (objectBonus.getType().equals(type) && objectBonus.getBonusName().equals(bonusName)) {
				if (bonusValue != 0) {
					objectBonus.setBonus(bonusValue);
				} else {
					bonus.remove(objectBonus);
				}
				return;
			}
		}
		if (bonusValue != 0) {
			// Not existing bonus, create a new one.
			ObjectBonus objectBonus = new ObjectBonus();
			objectBonus.setType(type);
			objectBonus.setBonus(bonusValue);
			objectBonus.setBonusName(bonusName);
			bonus.add(objectBonus);
		}
	}

	public static MagicObject createMagicObjectFor(Skill skill, TrainingItem trainingItem) {
		MagicObject magicObject = new MagicObject();
		magicObject.setName(trainingItem.getName());
		magicObject.setDescription(trainingItem.getDescription());
		magicObject.setSkillBonus(skill.getName(), trainingItem.getBonus());
		return magicObject;
	}

	@Override
	public String toString() {
		return getName();
	}
}
