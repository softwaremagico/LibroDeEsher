package com.softwaremagico.librodeesher.pj.training;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.google.gson.annotations.Expose;
import com.softwaremagico.librodeesher.basics.Roll;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.categories.Category;
import com.softwaremagico.librodeesher.pj.categories.CategoryFactory;
import com.softwaremagico.librodeesher.pj.characteristic.CharacteristicRoll;
import com.softwaremagico.librodeesher.pj.characteristic.CharacteristicsAbbreviature;
import com.softwaremagico.librodeesher.pj.equipment.BonusType;
import com.softwaremagico.librodeesher.pj.equipment.MagicObject;
import com.softwaremagico.librodeesher.pj.random.TrainingProbability;
import com.softwaremagico.librodeesher.pj.skills.Skill;
import com.softwaremagico.librodeesher.pj.skills.SkillFactory;
import com.softwaremagico.log.EsherLog;
import com.softwaremagico.persistence.StorableObject;

/**
 * Categories that are already defined are not stored in this class, can be obtained from training class. Skills that
 * are already defined are stored in this class as a list with one element.
 */
@Entity
@Table(name = "T_TRAINING_DECISION")
public class TrainingDecision extends StorableObject {
	private static final long serialVersionUID = 3914227163981778627L;

	@Expose
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@CollectionTable(name = "T_TRAINING_DECISION_CATEGORY_SELECTED")
	// For avoiding error org.hibernate.loader.MultipleBagFetchException: cannot
	// simultaneously fetch multiple bags
	// (http://stackoverflow.com/questions/4334970/hibernate-cannot-simultaneously-fetch-multiple-bags)
	@LazyCollection(LazyCollectionOption.FALSE)
	// TrainingCategoryIndex -> TrainingCategoriesSelected
	private Map<Integer, TrainingCategoriesSelected> categoriesSelected;

	@Expose
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@LazyCollection(LazyCollectionOption.FALSE)
	@CollectionTable(name = "T_TRAINING_DECISION_SKILLS_SELECTED")
	// TrainingCategoryIndex -> TrainingSkillsSelected
	// Defines all ranks in a skill for a training. If no skills selected
	// options is a list with one skill.
	private Map<Integer, TrainingSkillsSelected> skillsSelected;

	@Expose
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@LazyCollection(LazyCollectionOption.FALSE)
	@CollectionTable(name = "T_TRAINING_CHARACTERISTICS_UPDATES")
	private List<CharacteristicRoll> characteristicsUpdates;

	@Expose
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@LazyCollection(LazyCollectionOption.FALSE)
	@CollectionTable(name = "T_TRAINING_OBJECTS")
	private List<TrainingItem> equipment;

	@Expose
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@CollectionTable(name = "T_TRAINING_MAGIC_ITEMS")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<MagicObject> magicItems; // Equipment converted to magic
											// objects.

	public TrainingDecision() {
		categoriesSelected = new HashMap<>();
		skillsSelected = new HashMap<>();
		characteristicsUpdates = new ArrayList<>();
		equipment = new ArrayList<>();
		magicItems = new ArrayList<>();
	}

	public void addSelectedCategory(Integer trainingCategory, String categoryName) {
		TrainingCategoriesSelected categories = categoriesSelected.get(trainingCategory);
		if (categories == null) {
			categories = new TrainingCategoriesSelected();
		}
		categories.add(categoryName);
		categoriesSelected.put(trainingCategory, categories);
	}

	public List<String> getSelectedCategory(Integer trainingCategory) {
		TrainingCategoriesSelected categories = categoriesSelected.get(trainingCategory);
		if (categories == null) {
			categories = new TrainingCategoriesSelected();
		}
		return categories.getAll();
	}

	public void setSkillRank(Integer trainingCategory, String skill, int ranks) {
		if (skillsSelected.get(trainingCategory) == null) {
			skillsSelected.put(trainingCategory, new TrainingSkillsSelected());
		}
		skillsSelected.get(trainingCategory).put(skill, ranks);
	}

	public int getSkillRank(Integer trainingCategory, String skill) {
		if (skillsSelected.get(trainingCategory) == null
				|| skillsSelected.get(trainingCategory).getSkillsRanks().get(skill) == null) {
			return 0;
		}
		return skillsSelected.get(trainingCategory).getSkillsRanks().get(skill);
	}

	public int getSkillRank(Integer trainingCategory, Skill skill) {
		if (skill == null || skillsSelected.get(trainingCategory) == null) {
			return 0;
		}
		for (String trainingSkill : skillsSelected.get(trainingCategory).getSkillsRanks().keySet()) {
			if (trainingSkill != null && trainingSkill.equals(skill.getName())) {
				return getSkillRank(trainingCategory, trainingSkill);
			}
		}
		return 0;
	}

	public void clearSkillRanks() {
		skillsSelected.clear();
	}

	protected Map<Integer, TrainingCategoriesSelected> getCategoriesSelected() {
		return categoriesSelected;
	}

	protected void setCategoriesSelected(Map<Integer, TrainingCategoriesSelected> categoriesSelected) {
		this.categoriesSelected = categoriesSelected;
	}

	protected Map<Integer, TrainingSkillsSelected> getSkillsSelected() {
		return skillsSelected;
	}

	protected void setSkillsSelected(Map<Integer, TrainingSkillsSelected> skillsSelected) {
		this.skillsSelected = skillsSelected;
	}

	public void removeSkillsSelected(Integer trainingCategory) {
		skillsSelected.remove(trainingCategory);
	}

	public CharacteristicRoll addCharactersiticUpdate(CharacteristicsAbbreviature abbreviature,
			Integer currentTemporalValue, Integer currentPotentialValue, Roll roll) {
		CharacteristicRoll characteristicRoll = new CharacteristicRoll(abbreviature, currentTemporalValue,
				currentPotentialValue, roll);
		characteristicsUpdates.add(characteristicRoll);
		return characteristicRoll;
	}

	public List<CharacteristicRoll> getCharacteristicsUpdates(CharacteristicsAbbreviature abbreviature) {
		List<CharacteristicRoll> returnList = new ArrayList<>();
		for (CharacteristicRoll characteristicRollGroup : characteristicsUpdates) {
			if (characteristicRollGroup.getCharacteristicAbbreviature().equals(abbreviature)) {
				returnList.add(characteristicRollGroup);
			}
		}

		return returnList;
	}

	public List<CharacteristicRoll> getCharacteristicsUpdates() {
		return characteristicsUpdates;
	}

	protected void setCharacteristicsUpdates(List<CharacteristicRoll> characteristicsUpdates) {
		this.characteristicsUpdates = characteristicsUpdates;
	}

	public void addCharacteristicUpdate(CharacteristicRoll characteristicRoll) {
		characteristicsUpdates.add(characteristicRoll);
	}

	public List<TrainingItem> getEquipment() {
		return equipment;
	}

	public List<MagicObject> getMagicItems() {
		return magicItems;
	}

	public static List<MagicObject> convertTrainingEquipmentToMagicObject(CharacterPlayer characterPlayer,
			String trainingName) {
		List<MagicObject> magicObjects = new ArrayList<>();
		List<TrainingItem> equipment = characterPlayer.getTrainingEquipment(trainingName);
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
				magicArmour.setObjectBonus(item.getName(), BonusType.DEFENSIVE_BONUS, item.getBonus());
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
				magicObjects.add(createMagicObjectFor(skills.get(new Random().nextInt(skills.size() / 10)), item));
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
		magicObject.setName(trainingItem.getName());
		magicObject.setDescription(trainingItem.getDescription());
		magicObject.setSkillBonus(skill.getName(), trainingItem.getBonus());
		return magicObject;
	}
}
