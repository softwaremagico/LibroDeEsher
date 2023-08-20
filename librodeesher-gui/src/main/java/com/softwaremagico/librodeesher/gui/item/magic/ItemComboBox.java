package com.softwaremagico.librodeesher.gui.item.magic;

/*
 * #%L
 * Libro de Esher (GUI)
 * %%
 * Copyright (C) 2007 - 2014 Softwaremagico
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

import java.util.ArrayList;
import java.util.List;

import com.softwaremagico.librodeesher.gui.elements.BaseComboBox;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.equipment.MagicObject;

public class ItemComboBox extends BaseComboBox<MagicObject> {
	private static final long serialVersionUID = 4290328543865348273L;
	private List<ItemChangedListener> itemListeners;

	public ItemComboBox() {
		itemListeners = new ArrayList<>();
	}

	public void update(CharacterPlayer character) {
		this.removeAllItems();
		for (MagicObject object : character.getAllMagicItems()) {
			addItem(object);
		}
	}

	@Override
	public void doAction() {
		for (ItemChangedListener listener : itemListeners) {
			listener.ItemChanged((MagicObject) this.getSelectedItem());
		}
	}

	public void addItemChangedListener(ItemChangedListener listener) {
		itemListeners.add(listener);
	}

	public void removeItemChangedListener(ItemChangedListener listener) {
		itemListeners.remove(listener);
	}

}
