package com.softwaremagico.librodeesher.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import com.softwaremagico.librodeesher.gui.culture.CompleteWeaponPanel;
import com.softwaremagico.librodeesher.gui.elements.CloseButton;
import com.softwaremagico.librodeesher.gui.style.BaseFrame;
import com.softwaremagico.librodeesher.gui.style.BasicButton;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;

public class SkillWindow extends BaseFrame {
	private static final long serialVersionUID = 3505731416535837471L;
	private CharacterPlayer character;
	private BasicButton acceptButton;

	public SkillWindow(CharacterPlayer character) {
		this.character = character;
		defineWindow(500, 400);
		// setResizable(false);
		setElements();
	}

	private void setElements() {
		setLayout(new GridBagLayout());
		GridBagConstraints gridBagConstraints = new GridBagConstraints();

		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		gridBagConstraints.insets = new Insets(2, 2, 2, 2);
		getContentPane().add(new CompleteWeaponPanel(character), gridBagConstraints);

		JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
		acceptButton = new BasicButton("Aceptar");
		acceptButton.addActionListener(new AcceptListener());
		buttonPanel.add(acceptButton);

		CloseButton closeButton = new CloseButton(this);
		buttonPanel.add(closeButton);

		gridBagConstraints.anchor = GridBagConstraints.LINE_END;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 0.2;
		gridBagConstraints.weighty = 0;
		getContentPane().add(buttonPanel, gridBagConstraints);

	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
	}

	class AcceptListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

		}
	}

}
