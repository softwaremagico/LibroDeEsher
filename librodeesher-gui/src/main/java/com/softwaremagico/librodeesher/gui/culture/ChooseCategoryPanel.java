package com.softwaremagico.librodeesher.gui.culture;

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
import java.util.Map;

import com.softwaremagico.librodeesher.gui.style.BasePanel;
import com.softwaremagico.librodeesher.gui.training.TrainingCategoryLine;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.culture.CultureCategory;
import com.softwaremagico.librodeesher.pj.skills.Skill;
import com.softwaremagico.librodeesher.pj.skills.SkillFactory;

public class ChooseCategoryPanel extends BasePanel {
	private static final long serialVersionUID = 544393371168606333L;

	private HashMap<CultureCategory, List<CultureSkillLine>> weaponLines = new HashMap<>();
	private Map<CultureCategory, List<CultureSkillLine>> skillsLinesPerCategory = new HashMap<>();
	private List<CultureCategoryLine> categoryLines;
	private CharacterPlayer character;

	public ChooseCategoryPanel(CharacterPlayer character) {
		this.character = character;
		setElements();
	}

	private void setElements() {
		this.removeAll();
		setLayout(new GridLayout(0, 1));
		int i = 0;

		if (character.getCulture() != null) {
			for (CultureCategory cultureCategory : character.getCulture().getAdolescenceCategories()) {
				CultureCategoryLine categoryLine = new CultureCategoryLine(character, cultureCategory, getLineBackgroundColor(i), this);
				add(categoryLine);
				categoryLines.add(categoryLine);
				skillsLinesPerCategory.put(cultureCategory, new ArrayList<CultureSkillLine>());

				i++;
				String selectedCategory = cultureCategory.getCategoryOptions().get(0);
				for (Skill skill : cultureCategory.getCultureSkills(selectedCategory)) {
					CultureSkillLine skillLine = new CultureSkillLine(character, cultureCategory, this, SkillFactory.getAvailableSkill(skill.getName()),
							getLineBackgroundColor(i));
					add(skillLine);
					i++;
					skillsLinesPerCategory.get(cultureCategory).add(skillLine);
				}
			}
		}

		// for (Category category : CategoryFactory.getWeaponsCategories()) {
		// add(new CultureCategoryLine(category,
		// character.getCulture().getCultureRanks(category),
		// getLineBackgroundColor(i)));
		// i++;
		// ArrayList<WeaponSkillLine> weaponLineList = new ArrayList<>();
		// weaponLines.put(category, weaponLineList);
		// if (character.getCulture().hasSkillsToChooseRanks(category)) {
		// for (Weapon weapon : character.getCulture().getCultureWeapons()) {
		// try {
		// if
		// (weapon.getType().getWeaponCategoryName().equals(category.getName()))
		// {
		// WeaponSkillLine weaponLine = new WeaponSkillLine(character, category,
		// this, SkillFactory.getAvailableSkill(weapon.getName()),
		// getLineBackgroundColor(i));
		// add(weaponLine);
		// weaponLineList.add(weaponLine);
		// i++;
		// }
		// } catch (NullPointerException npe) {
		// // Arma de cultura no existe en el programa. Ignorar.
		// }
		// }
		// }
		// }
	}

	protected void removeSkillLinesOfCategory(CultureCategory cultureCategory) {
		if (skillsLinesPerCategory.get(cultureCategory) != null) {
			for (CultureSkillLine line : skillsLinesPerCategory.get(cultureCategory)) {
				this.remove(line);
			}
			skillsLinesPerCategory.put(cultureCategory, new ArrayList<CultureSkillLine>());
		}
	}

	protected void addSkillLinesOfCategory(CultureCategory cultureCategory, String selectedCategory) {
		// Get the index of the category.
		int index = -1;

		for (int i = 0; i < this.getComponents().length; i++) {
			Component component = this.getComponents()[i];
			if (component instanceof TrainingCategoryLine) {
				if (((CultureCategoryLine) component).getCultureCategory().equals(cultureCategory)) {
					index = i + 1;
					break;
				}
			}
		}

		for (Skill skill : cultureCategory.getCultureSkills(selectedCategory)) {
			CultureSkillLine skillLine = new CultureSkillLine(character, cultureCategory, this, SkillFactory.getAvailableSkill(skill.getName()),
					getLineBackgroundColor(index));
			add(skillLine, index);
			index++;
			skillsLinesPerCategory.get(cultureCategory).add(skillLine);
		}

		this.revalidate();
		this.repaint();
	}

	protected Integer getSpinnerValues(CultureCategory category) {
		Integer total = 0;
		for (CultureSkillLine lines : weaponLines.get(category)) {
			total += lines.getSelectedRanks();
		}
		return total;
	}

	@Override
	public void update() {
	}
}
