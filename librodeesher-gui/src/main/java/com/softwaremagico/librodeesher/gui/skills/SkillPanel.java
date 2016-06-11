package com.softwaremagico.librodeesher.gui.skills;

/*
 * #%L
 * Libro de Esher
 * %%
 * Copyright (C) 2007 - 2013 Softwaremagico
 * %%
 * This software is designed by Jorge Hortelano Otero. Jorge Hortelano Otero
 * <softwaremagico@gmail.com> Valencia (Spain).
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

import java.awt.Component;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.softwaremagico.librodeesher.gui.elements.BaseSkillPanel;
import com.softwaremagico.librodeesher.gui.elements.GenericSkillLine;
import com.softwaremagico.librodeesher.gui.elements.SkillChangedListener;
import com.softwaremagico.librodeesher.gui.skills.SelectSkillWindow.SkillEnabledListener;
import com.softwaremagico.librodeesher.gui.style.BaseLine;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.categories.Category;
import com.softwaremagico.librodeesher.pj.categories.CategoryCost;
import com.softwaremagico.librodeesher.pj.categories.CategoryFactory;
import com.softwaremagico.librodeesher.pj.categories.CategoryGroup;
import com.softwaremagico.librodeesher.pj.skills.Skill;
import com.softwaremagico.librodeesher.pj.skills.SkillFactory;
import com.softwaremagico.librodeesher.pj.skills.SkillForEnablingMustBeSelected;
import com.softwaremagico.log.EsherLog;

public class SkillPanel extends BaseSkillPanel {
	private static final long serialVersionUID = 544393371168606333L;
	private CompleteSkillPanel parentWindow;
	private HashMap<Category, List<GenericSkillLine>> skillLinesPerCategory;
	private HashMap<Skill, List<GenericSkillLine>> skillSpecializationLines;
	private List<WeaponCategoryLine> weaponsLines;
	private List<CategoryLine> categoryLines;
	private CharacterPlayer characterPlayer;

	public SkillPanel(CharacterPlayer character, CompleteSkillPanel parentWindow) {
		characterPlayer = character;
		this.parentWindow = parentWindow;
		skillLinesPerCategory = new HashMap<>();
		skillSpecializationLines = new HashMap<>();
		setElements(character);
	}

	private void setElements(CharacterPlayer character) {
		this.removeAll();
		setLayout(new GridLayout(0, 1));
		int i = 0;
		int weapon = 0;

		weaponsLines = new ArrayList<>();
		categoryLines = new ArrayList<>();
		// Add extra weapons cost if new weapons categories exists.
		character.getProfession().extendCategoryCost(character.isFirearmsAllowed());
		for (Category category : CategoryFactory.getCategories()) {
			// Translate general category to player specific category.
			category = character.getCategory(category);
			if (character.isCategoryUseful(category)) {
				if (category.getCategoryGroup().equals(CategoryGroup.WEAPON)) {
					CategoryCost cost = character.getProfessionDecisions().getWeaponCost(category);
					if (cost == null) {
						cost = character.getFirstWeaponCostNotSelected();
					}
					WeaponCategoryLine wl = new WeaponCategoryLine(character, category, getLineBackgroundColor(i), this, cost, weapon);
					add(wl);
					weaponsLines.add(wl);
					weapon++;
				} else {
					CategoryLine categoryLine = new CategoryLine(character, category, getLineBackgroundColor(i), this);
					categoryLines.add(categoryLine);
					add(categoryLine);
				}
				i++;

				List<GenericSkillLine> skillLines = new ArrayList<>();
				for (Skill skill : category.getSkills()) {
					if (!character.isSkillDisabled(skill)) {
						GenericSkillLine skillLine = createSkillLine(i, skill);
						skillSpecializationLines.put(skill, new ArrayList<GenericSkillLine>());
						if (skillLine != null) {
							add(skillLine);
							skillLines.add(skillLine);
							i++;
							// Add specializations.
							for (String specialization : characterPlayer.getSkillSpecializations(skill)) {
								SpecializedSkillLine specializatedSkillLine = new SpecializedSkillLine(characterPlayer, skill,
										specialization, getLineBackgroundColor(i), this);
								getSkillLinesPerCategory(category).add(specializatedSkillLine);
								add(specializatedSkillLine);
								skillSpecializationLines.get(skill).add(specializatedSkillLine);
								i++;
							}
						}
					}
				}
				skillLinesPerCategory.put(category, skillLines);
			}
		}
	}

	private List<GenericSkillLine> getSkillLinesPerCategory(Category category) {
		if (skillLinesPerCategory.get(category) == null) {
			skillLinesPerCategory.put(category, new ArrayList<GenericSkillLine>());
		}
		return skillLinesPerCategory.get(category);
	}

	private SkillLine createSkillLine(CategoryLine categoryLine, Skill skill, int backgroundIndex) {
		return createSkillLine(backgroundIndex, skill);
	}

	private SkillLine createSkillLine(int backgroundIndex, Skill skill) {
		if (!characterPlayer.isSkillDisabled(skill)) {
			final SkillLine skillLine = new SkillLine(characterPlayer, skill, getLineBackgroundColor(backgroundIndex), this);
			add(skillLine);
			skillLine.addSkillEnabledListener(new SkillEnabledListener() {

				@Override
				public void skillEnabledEvent(Skill skill, String skillSelectedName, boolean selected) {
					Skill skillSelected = SkillFactory.getSkill(skillSelectedName);
					if (!selected) {
						// Remove any rank if selected
						try {
							if (characterPlayer.getEnabledSkill().get(skill.getName()) != null
									&& characterPlayer.getEnabledSkill().get(skill.getName()).equals(skillSelectedName)) {
								characterPlayer.getEnabledSkill().remove(skill.getName());
							}
							characterPlayer.setCurrentLevelRanks(skillSelected, 0);
						} catch (SkillForEnablingMustBeSelected e) {
							// Nothing
							EsherLog.errorMessage(this.getClass().getName(), e);
						}
					} else {
						characterPlayer.getEnabledSkill().put(skill.getName(), skillSelectedName);
					}
					updateCategorySkills(skillSelected.getCategory());
					update();
				}
			});
			skillLine.addSkillChangedListener(new SkillChangedListener() {

				@Override
				public void skillChanged(Skill skill) {
					// Skill updated. Update also specializations.
					for (GenericSkillLine line : skillSpecializationLines.get(skill)) {
						line.updateRanksValue();
					}
				}
			});
			return skillLine;
		}
		return null;
	}

	private void updateCategorySkills(Category category) {
		// Remove all skills of this category
		List<GenericSkillLine> skills = skillLinesPerCategory.get(category);
		for (GenericSkillLine skillLine : skills) {
			remove(skillLine);
		}
		skillLinesPerCategory.put(category, new ArrayList<GenericSkillLine>());

		// Add skills after category
		for (CategoryLine categoryLine : categoryLines) {
			if (categoryLine.getCategory().equals(category)) {
				int categoryLineIndex = getIndex(categoryLine);
				categoryLineIndex++;
				int backgroundIndex = getBackgroundColorIndex(categoryLine.getBackground());
				backgroundIndex++;
				for (Skill skill : category.getSkills()) {
					if (!characterPlayer.isSkillDisabled(skill)) {
						SkillLine skillLine = createSkillLine(categoryLine, skill, backgroundIndex);
						if (skillLine != null) {
							skillSpecializationLines.put(skill, new ArrayList<GenericSkillLine>());
							getSkillLinesPerCategory(category).add(skillLine);
							add(skillLine, categoryLineIndex);
							categoryLineIndex++;
							backgroundIndex++;
							// Add specializations.
							for (String specialization : characterPlayer.getSkillSpecializations(skill)) {
								SpecializedSkillLine specializatedSkillLine = new SpecializedSkillLine(characterPlayer, skill,
										specialization, getLineBackgroundColor(backgroundIndex), this);
								getSkillLinesPerCategory(category).add(specializatedSkillLine);
								add(specializatedSkillLine, categoryLineIndex);
								skillSpecializationLines.get(skill).add(specializatedSkillLine);
								backgroundIndex++;
								categoryLineIndex++;
							}
						}
					}
				}
			}
		}
		updateLinesBackground();
	}

	private void updateLinesBackground() {
		int i = 0;
		for (Component component : this.getComponents()) {
			if (component instanceof BaseLine) {
				((BaseLine) component).updateBackground(getLineBackgroundColor(i));
			}
			i++;
		}
	}

	private int getIndex(Component component) {
		for (int i = 0; i < getComponentCount(); i++) {
			if (getComponent(i) == component)
				return i;
		}
		return -1;
	}

	@Override
	public void update() {
		parentWindow.update();
	}

	@Override
	public void updateSkillsOfCategory(Category category) {
		List<GenericSkillLine> skillLines = skillLinesPerCategory.get(category);
		if (skillLines != null) {
			for (GenericSkillLine skillLine : skillLines) {
				skillLine.updateCategory();
			}
		}
	}

	protected void updateWeaponsCost(Integer newUsedItemIndex, Integer oldUsedItemIndex, Integer skipedWeaponLine) {

		for (int i = 0; i < weaponsLines.size(); i++) {
			if (i != skipedWeaponLine) {
				if (weaponsLines.get(i).getSelectedIndex().equals(newUsedItemIndex)) {
					weaponsLines.get(i).setSelectedIndex(oldUsedItemIndex);
				}
			}
		}
	}

	public void updateRanks() {
		for (CategoryLine categoryLine : categoryLines) {
			categoryLine.updateCurrentRanks();
			categoryLine.updateRankValues();
		}
		for (WeaponCategoryLine weaponLine : weaponsLines) {
			weaponLine.updateCurrentRanks();
			weaponLine.updateRankValues();
		}
		for (Category category : skillLinesPerCategory.keySet()) {
			for (GenericSkillLine skillLine : skillLinesPerCategory.get(category)) {
				skillLine.updateRanks();
				skillLine.updateRankValues();
			}
		}
	}

	public void updateWeaponCost() {
		for (WeaponCategoryLine weaponLine : weaponsLines) {
			weaponLine.setSelected(characterPlayer.getProfessionDecisions().getWeaponCost(weaponLine.getCategory()));
		}
	}
}
