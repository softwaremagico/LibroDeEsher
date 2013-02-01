package com.softwaremagico.librodeesher.gui.culture;
/*
 * #%L
 * Libro de Esher
 * %%
 * Copyright (C) 2007 - 2013 Softwaremagico
 * %%
 * This software is designed by Jorge Hortelano Otero. Jorge Hortelano Otero
 * <softwaremagico@gmail.com> C/Quart 89, 3. Valencia CP:46008 (Spain).
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

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.softwaremagico.librodeesher.gui.style.BasePanel;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.categories.Category;
import com.softwaremagico.librodeesher.pj.categories.CategoryFactory;
import com.softwaremagico.librodeesher.pj.skills.SkillFactory;
import com.softwaremagico.librodeesher.pj.weapons.Weapon;

public class ChooseWeaponPanel extends BasePanel {

	private static final long serialVersionUID = 544393371168606333L;
	private Hashtable<Category, List<WeaponSkillLine>> weaponLines = new Hashtable<>();

	public ChooseWeaponPanel(CharacterPlayer character) {
		setElements(character);
	}

	private void setElements(CharacterPlayer character) {
		this.removeAll();
		setLayout(new GridLayout(0, 1));
		int i = 0;

		for (Category category : CategoryFactory.getWeaponsCategories()) {
			add(new WeaponCategoryLine(category, character.getCulture().getCultureRanks(category),
					getLineBackgroundColor(i)));
			i++;
			ArrayList<WeaponSkillLine> weaponLineList = new ArrayList<>();
			weaponLines.put(category, weaponLineList);
			for (Weapon weapon : character.getCulture().getCultureWeapons()) {
				try {
					if (weapon.getType().getWeaponCategoryName().equals(category.getName())) {
						WeaponSkillLine weaponLine = new WeaponSkillLine(character, category, this,
								SkillFactory.getAvailableSkill(weapon.getName()), getLineBackgroundColor(i));
						add(weaponLine);
						weaponLineList.add(weaponLine);
						i++;
					}
				} catch (NullPointerException npe) {
					// Arma de cultura no existe en el programa. Ignorar.
				}
			}
		}
	}

	protected Integer getSpinnerValues(Category category) {
		Integer total = 0;
		for (WeaponSkillLine lines : weaponLines.get(category)) {
			total += lines.getSelectedRanks();
		}
		return total;
	}

	@Override
	public void update() {		
	}
}
