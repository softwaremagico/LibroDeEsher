package com.softwaremagico.librodeesher.gui.equipment;

/*
 * #%L
 * Libro de Esher (GUI)
 * %%
 * Copyright (C) 2007 - 2016 Softwaremagico
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

import com.softwaremagico.librodeesher.gui.elements.BaseScrollPanel;
import com.softwaremagico.librodeesher.gui.equipment.EquipmentLine.EquipmentRemovedListener;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;

public class ResumeEquipmentListPanel extends BaseScrollPanel {
	private static final long serialVersionUID = 4077686958927513421L;
	private EquipmentTitle title;
	private EquipmentListPanel equipmentListPanel;

	public ResumeEquipmentListPanel(CharacterPlayer characterPlayer) {
		title = new EquipmentTitle();
		addTitle(title);
		equipmentListPanel = new EquipmentListPanel(characterPlayer);
		setBody(equipmentListPanel);
	}

	@Override
	public void update() {
		equipmentListPanel.update();
		revalidate();
	}

	public void addEquipmentRemovedListener(EquipmentRemovedListener equipmentRemovedListener) {
		equipmentListPanel.addEquipmentRemovedListener(equipmentRemovedListener);
	}

}
