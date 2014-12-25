package com.softwaremagico.librodeesher.gui.magicitem;

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
import com.softwaremagico.librodeesher.pj.equipment.BonusType;

public class OtherBonusComboBox extends BaseComboBox<BonusType> {
	private static final long serialVersionUID = 169019436489940061L;
	private List<OthersChangedListener> othersListeners;

	public interface OthersChangedListener {
		public void otherChanged(BonusType type);
	}

	public OtherBonusComboBox() {
		othersListeners = new ArrayList<>();
		fillUp();
	}

	private void fillUp() {
		removeAllItems();
		for (BonusType type : BonusType.values()) {
			// All except skills and categories.
			if (!type.equals(BonusType.SKILL) && !type.equals(BonusType.CATEGORY))
				addItem(type);
		}
	}

	@Override
	public void doAction() {
		for (OthersChangedListener listener : othersListeners) {
			listener.otherChanged((BonusType) this.getSelectedItem());
		}
	}

	public void addOthersChangedListener(OthersChangedListener listener) {
		othersListeners.add(listener);
	}

	public void removeOthersChangedListener(OthersChangedListener listener) {
		othersListeners.remove(listener);
	}

}
