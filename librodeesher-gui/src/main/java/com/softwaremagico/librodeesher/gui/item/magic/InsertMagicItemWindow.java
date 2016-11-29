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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import com.softwaremagico.librodeesher.gui.elements.CloseButton;
import com.softwaremagico.librodeesher.gui.item.magic.EditMagicItemPanel.MagicObjectNameUpdated;
import com.softwaremagico.librodeesher.gui.style.BaseFrame;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.equipment.MagicObject;

public class InsertMagicItemWindow extends BaseFrame {
	private static final long serialVersionUID = -4213290581671073366L;
	private CharacterPlayer character;
	private EditMagicItemPanel itemEditor;
	private ItemAdministratorPanel itemAdministrator;

	public InsertMagicItemWindow(CharacterPlayer character) {
		this.character = character;
		defineWindow(400, 420);
		setResizable(false);
		setElements();
	}

	private void setElements() {
		setLayout(new GridBagLayout());
		GridBagConstraints gridBagConstraints = new GridBagConstraints();

		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.ipady = yPadding;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 0;
		gridBagConstraints.insets = new Insets(2, 2, 2, 2);
		itemAdministrator = new ItemAdministratorPanel(character);
		getContentPane().add(itemAdministrator, gridBagConstraints);

		itemAdministrator.addNewButtonActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				addNewMagicItem();
			}
		});

		itemAdministrator.addItemSelectedActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				editMagicItem();
			}
		});
		itemAdministrator.addDeleteButtonActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				deleteMagicItem();
			}

		});

		gridBagConstraints.gridy = 1;
		gridBagConstraints.weighty = 1;
		itemEditor = new EditMagicItemPanel(character, null);
		itemEditor.setMagicObject(itemAdministrator.getSelectedMagicObject());
		itemEditor.addNameUpdateListener(new MagicObjectNameUpdated() {
			@Override
			public void updatedName(MagicObject magicObject) {
				
			}
		});
		getContentPane().add(itemEditor, gridBagConstraints);

		JPanel buttonPanel = new JPanel(new GridLayout(1, 2));

		CloseButton closeButton = new CloseButton(this);
		buttonPanel.add(closeButton);

		gridBagConstraints.anchor = GridBagConstraints.LINE_END;
		gridBagConstraints.fill = GridBagConstraints.NONE;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 0;
		getContentPane().add(buttonPanel, gridBagConstraints);
	}

	private void addNewMagicItem() {
		MagicObject magicObject = new MagicObject();
		magicObject.setName("Nuevo Objeto");
		character.addMagicItem(magicObject);
		itemAdministrator.addMagicObject(magicObject);
		itemEditor.setMagicObject(magicObject);
	}

	private void editMagicItem() {
		MagicObject magicObject = itemAdministrator.getSelectedMagicObject();
		itemEditor.setMagicObject(magicObject);
	}

	private void deleteMagicItem() {
		MagicObject objectToDelete = itemAdministrator.getSelectedMagicObject();
		itemAdministrator.removeMagicObject(objectToDelete);
		character.removeMagicItem(objectToDelete);		
		itemEditor.setMagicObject(itemAdministrator.getSelectedMagicObject());
	}

	@Override
	public void updateFrame() {
	
	}

}
