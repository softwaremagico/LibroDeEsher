package com.softwaremagico.librodeesher.pj.equipment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.categories.Category;
import com.softwaremagico.librodeesher.pj.categories.CategoryFactory;
import com.softwaremagico.librodeesher.pj.random.TrainingProbability;
import com.softwaremagico.librodeesher.pj.skills.Skill;
import com.softwaremagico.librodeesher.pj.skills.SkillFactory;
import com.softwaremagico.librodeesher.pj.training.TrainingItem;
import com.softwaremagico.log.EsherLog;
import com.softwaremagico.persistence.StorableObject;

@Entity
@Table(name = "T_MAGIC_OBJECT")
public class MagicObject extends StorableObject {
	@Expose
	private String name = "";

	@Expose
	private String description = "";

	@Expose
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getSkillBonus(String skillName) {
		for (ObjectBonus objectBonus : new ArrayList<>(bonus)) {
			if (objectBonus instanceof SkillBonus) {
				if (objectBonus.getBonusName().equals(skillName)) {
					return objectBonus.getBonus();
				}
			}
		}
		return 0;
	}

	public void setSkillBonus(String skillName, int bonusValue) {
		for (ObjectBonus objectBonus : new ArrayList<>(bonus)) {
			if (objectBonus instanceof SkillBonus) {
				if (objectBonus.getBonusName().equals(skillName)) {
					if (bonusValue != 0) {
						objectBonus.setBonus(bonusValue);
					} else {
						bonus.remove(objectBonus);
					}
					return;
				}
			}
		}
		if (bonusValue != 0) {
			// Not existing bonus, create a new one.
			SkillBonus skillBonus = new SkillBonus();
			skillBonus.setBonusName(skillName);
			skillBonus.setBonus(bonusValue);
			bonus.add(skillBonus);
		}
	}

	public int getCategoryBonus(String categoryName) {
		for (ObjectBonus objectBonus : new ArrayList<>(bonus)) {
			if (objectBonus instanceof CategoryBonus) {
				if (objectBonus.getBonusName().equals(categoryName)) {
					return objectBonus.getBonus();
				}
			}
		}
		return 0;
	}

	public void setCategoryBonus(String categoryName, int bonusValue) {
		for (ObjectBonus objectBonus : new ArrayList<>(bonus)) {
			if (objectBonus instanceof CategoryBonus) {
				if (objectBonus.getBonusName().equals(categoryName)) {
					if (bonusValue != 0) {
						objectBonus.setBonus(bonusValue);
					} else {
						bonus.remove(objectBonus);
					}
					return;
				}
			}
		}
		if (bonusValue != 0) {
			// Not existing bonus, create a new one.
			CategoryBonus categoryBonus = new CategoryBonus();
			categoryBonus.setBonusName(categoryName);
			categoryBonus.setBonus(bonusValue);
			bonus.add(categoryBonus);
		}
	}

	public int getOthersBonus(OtherBonusType type) {
		for (ObjectBonus objectBonus : new ArrayList<>(bonus)) {
			if (objectBonus instanceof OtherBonus) {
				if (((OtherBonus) objectBonus).getType().equals(type)) {
					return objectBonus.getBonus();
				}
			}
		}
		return 0;
	}

	public void setOthersBonus(OtherBonusType type, int bonusValue) {
		for (ObjectBonus objectBonus : new ArrayList<>(bonus)) {
			if (objectBonus instanceof OtherBonus) {
				if (((OtherBonus) objectBonus).getType().equals(type)) {
					if (bonusValue != 0) {
						objectBonus.setBonus(bonusValue);
					} else {
						bonus.remove(objectBonus);
					}
					return;
				}
			}
		}
		if (bonusValue != 0) {
			// Not existing bonus, create a new one.
			OtherBonus otherBonus = new OtherBonus();
			otherBonus.setType(type);
			otherBonus.setBonus(bonusValue);
			bonus.add(otherBonus);
		}
	}

	@Override
	public String toString() {
		return getName();
	}
	
	public static List<MagicObject> convertTrainingEquipmentToMagicObject(CharacterPlayer characterPlayer,
			String trainingName) {
		List<MagicObject> magicObjects = new ArrayList<>();
		List<TrainingItem> equipment = characterPlayer.getTrainingDecision(trainingName).getEquipment();
		for (TrainingItem item : equipment) {
			List<Category> categories;
			List<Skill> skills;

			switch (item.getType()) {
			case WEAPON:
				categories = CategoryFactory.getWeaponsCategories();
				skills = characterPlayer.getSkillsFromCategoriesOrderByValue(categories);
				magicObjects.add(createMagicObjectFor(skills.get(0), item));
				break;
			case WEAPON_CLOSE_COMBAT:
				categories = CategoryFactory.getCloseCombatWeapons();
				skills = characterPlayer.getSkillsFromCategoriesOrderByValue(categories);
				magicObjects.add(createMagicObjectFor(skills.get(0), item));
				break;
			case WEAPON_RANGED:
				categories = CategoryFactory.getLongRangeWeapons();
				skills = characterPlayer.getSkillsFromCategoriesOrderByValue(categories);
				magicObjects.add(createMagicObjectFor(skills.get(0), item));
				break;
			case ARMOUR:
				MagicObject magicArmour = new MagicObject();
				magicArmour.setOthersBonus(OtherBonusType.DEFENSIVE_BONUS, item.getBonus());
				magicObjects.add(magicArmour);
				break;
			case SKILL:
				MagicObject magicObjectOfSkill = new MagicObject();
				Skill skill = SkillFactory.getAvailableSkill(item.getSkill());
				if (skill != null) {
					magicObjectOfSkill.setSkillBonus(skill.getName(), item.getBonus());
					magicObjects.add(magicObjectOfSkill);
				} else {
					EsherLog.warning(TrainingProbability.class.getName(), "Skill '" + item.getSkill()
							+ "' not found when creating a magic object of training '" + trainingName + "'.");
				}
				break;
			case ANY:
				skills = characterPlayer.getSkillsOrderByValue(SkillFactory.getSkills());
				magicObjects.add(createMagicObjectFor(skills.get(new Random().nextInt(skills.size() / 10)),
						item));
				break;
			case CATEGORY:
				MagicObject magicObjectOfCategory = new MagicObject();
				Category category = CategoryFactory.getCategory(item.getSkill());
				if (category != null) {
					magicObjectOfCategory.setCategoryBonus(category.getName(), item.getBonus());
					magicObjects.add(magicObjectOfCategory);
				} else {
					EsherLog.warning(TrainingProbability.class.getName(), "Category '" + item.getSkill()
							+ "' not found when creating a magic object of training '" + trainingName + "'.");
				}
				break;
			default:
				// Nothing.
				break;
			}
		}
		return magicObjects;
	}
	
	private static MagicObject createMagicObjectFor(Skill skill, TrainingItem trainingItem) {
		MagicObject magicObject = new MagicObject();
		magicObject.setSkillBonus(skill.getName(), trainingItem.getBonus());
		return magicObject;
	}
}
