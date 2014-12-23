package com.softwaremagico.librodeesher.gui.magicitem;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JPanel;

import com.softwaremagico.librodeesher.gui.elements.CloseButton;
import com.softwaremagico.librodeesher.gui.style.BaseFrame;
import com.softwaremagico.librodeesher.gui.style.BasePanel;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;

public class InsertMagicItemWindow  extends BaseFrame {
	private static final long serialVersionUID = -4213290581671073366L;
	private CharacterPlayer character;
	private BasePanel itemAdministrator, itemEditor;
	
	public InsertMagicItemWindow(CharacterPlayer character){
		this.character = character;
		defineWindow(400, 300);
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
		itemAdministrator = new ItemAdministratorPanel();
		getContentPane().add(itemAdministrator, gridBagConstraints);
		
		gridBagConstraints.gridy = 1;
		gridBagConstraints.weighty = 1;
		itemEditor = new EditMagicItemPanel(null);
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

	@Override
	public void updateFrame() {
		// TODO Auto-generated method stub
		
	}

}
