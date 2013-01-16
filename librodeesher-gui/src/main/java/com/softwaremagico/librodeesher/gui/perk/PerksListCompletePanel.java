package com.softwaremagico.librodeesher.gui.perk;

/*
 * #%L
 * Libro de Esher GUI
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

import com.softwaremagico.librodeesher.gui.elements.BaseScrollPanel;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;

public class PerksListCompletePanel extends BaseScrollPanel {
	private static final long serialVersionUID = -5606065330811602828L;
	private PerkWindow parent;
	CharacterPlayer character;
	private PerksListTitle title;
	private PerksListPanel perksPanel;

	PerksListCompletePanel(CharacterPlayer character, PerkWindow parent) {
		this.parent = parent;
		this.character = character;
		title = new PerksListTitle();
		addTitle(title);
		perksPanel = new PerksListPanel(character, this);
		addBody(perksPanel);
	}

	protected void update() {
		parent.update();
	}

}
