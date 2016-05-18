package com.softwaremagico.librodeesher.gui.perk;

/*
 * #%L
 * Libro de Esher GUI
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

import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;

import com.softwaremagico.librodeesher.gui.style.BasePanel;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.perk.Perk;
import com.softwaremagico.librodeesher.pj.perk.PerkFactory;

public class PerksListPanel extends BasePanel {

	private static final long serialVersionUID = -1612700951233838060L;
	private PerksListCompletePanel parent;
	private Map<Perk, Perk> perksWithWeakness = new HashMap<>();

	public PerksListPanel(CharacterPlayer character, PerksListCompletePanel parent) {
		this.parent = parent;
		setElements(character);
	}

	public void setElements(CharacterPlayer character) {
		this.removeAll();
		setLayout(new GridLayout(0, 1));
		int i = 0;
		for (Perk perk : PerkFactory.gerPerks()) {
			// Only perks that can be used.
			if (perk.isPerkAllowed(character.getRace().getName(), character.getProfession().getName())) {
				PerkLine perkLine = new PerkLine(character, perk, getLineBackgroundColor(i), this);
				add(perkLine);
				i++;
			}
		}
	}

	public void addWeakness(Perk perk, Perk weakness) {
		perksWithWeakness.put(perk, weakness);
	}

	@Override
	public void update() {
		parent.update();
	}

	public Map<Perk, Perk> getPerksWithWeakness() {
		return perksWithWeakness;
	}

}
