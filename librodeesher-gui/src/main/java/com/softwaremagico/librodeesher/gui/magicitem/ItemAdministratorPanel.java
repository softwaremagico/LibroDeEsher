package com.softwaremagico.librodeesher.gui.magicitem;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import com.softwaremagico.librodeesher.gui.style.BaseButton;
import com.softwaremagico.librodeesher.gui.style.BasePanel;

public class ItemAdministratorPanel extends BasePanel {
	private static final long serialVersionUID = 6676492366247973621L;
	private BaseButton newItem, editItem, deleteItem;
	private ItemComboBox itemComboBox;

	protected ItemAdministratorPanel(){
		setElements();
	}
	
	private void setElements(){
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
		
		editItem = new BaseButton("Editar");
		constraints.fill = GridBagConstraints.NONE;
		constraints.gridx = 3;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.weightx = 0;
		add(editItem, constraints);

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

}
