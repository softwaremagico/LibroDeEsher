package com.softwaremagico.librodeesher.gui.culture;

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

			add(new WeaponCategoryLine(category, character.getCulture().getCategoryCultureRanks(
					category.getName()), getLineBackgroundColor(i)));
			i++;

			ArrayList<WeaponSkillLine> weaponLineList = new ArrayList<>();
			weaponLines.put(category, weaponLineList);
			for (Weapon weapon : character.getCulture().getCultureWeapons()) {
				try{
				if (weapon.getType().getWeaponCategoryName().equals(category.getName())) {
					WeaponSkillLine weaponLine = new WeaponSkillLine(character, category, this,
							SkillFactory.getAvailableSkill(weapon.getName()), getLineBackgroundColor(i));
					add(weaponLine);
					weaponLineList.add(weaponLine);
					i++;
				}
				}catch(NullPointerException npe){
					//Arma de cultura no existe en el programa. Ignorar. 
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
}
