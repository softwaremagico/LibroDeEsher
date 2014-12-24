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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;

import com.softwaremagico.librodeesher.gui.style.BaseButton;
import com.softwaremagico.librodeesher.gui.style.BasePanel;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.equipment.MagicObject;

public class ItemAdministratorPanel extends BasePanel {
	private static final long serialVersionUID = 6676492366247973621L;
	private BaseButton newItem, deleteItem;
	private ItemComboBox itemComboBox;
	private CharacterPlayer character;

	protected ItemAdministratorPanel(CharacterPlayer character) {
		setElements();
		this.character = character;
		itemComboBox.update(character);
	}

	private void setElements() {
		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.ipadx = xPadding;
		constraints.weighty = 0;

		newItem = new BaseButton("Nuevo");
		constraints.fill = GridBagConstraints.NONE;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.weightx = 0;
		add(newItem, constraints);

		itemComboBox = new ItemComboBox();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.gridwidth = 2;
		constraints.weightx = 1;
		add(itemComboBox, constraints);

		deleteItem = new BaseButton("Borrar");
		constraints.fill = GridBagConstraints.NONE;
		constraints.gridx = 4;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.weightx = 0;
		add(deleteItem, constraints);
	}

	@Override
	public void update() {

	}

	public void addNewButtonActionListener(ActionListener al) {
		newItem.addActionListener(al);
	}

	public void addDeleteButtonActionListener(ActionListener al) {
		deleteItem.addActionListener(al);
	}

	public void addItemSelectedActionListener(ActionListener al) {
		itemComboBox.addActionListener(al);
	}

	public void addMagicObject(MagicObject magicItem) {
		itemComboBox.addItem(magicItem);
		itemComboBox.setSelectedItem(magicItem);
	}

	public MagicObject getSelectedMagicObject() {
		return (MagicObject) itemComboBox.getSelectedItem();
	}

	public void removeMagicObject(MagicObject magicItem) {
		if (magicItem != null) {
			itemComboBox.removeItem(magicItem);
		}
	}

	public void updateName(MagicObject magicObject) {
		itemComboBox.update(character);
		itemComboBox.setSelectedItem(magicObject);
	}

}
