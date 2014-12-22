package com.softwaremagico.librodeesher.gui.skills;
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
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

import com.softwaremagico.librodeesher.gui.elements.CloseButton;
import com.softwaremagico.librodeesher.gui.style.BaseFrame;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;

public class SelectSkillWindow extends BaseFrame {
	private static final long serialVersionUID = 4792683000547964467L;
	private CharacterPlayer character;
	private List<String> skillsToChoose;
	private Integer heightCells;
	private List<JCheckBox> checkBoxes = new ArrayList<>();

	public SelectSkillWindow(CharacterPlayer character, List<String> skillsToChoose) {
		this.skillsToChoose = skillsToChoose;
		this.character = character;
		defineSize();
		setElements();
	}

	private void defineSize() {
		heightCells = skillsToChoose.size();
		defineWindow(250, 50 * heightCells + 200);
	}

	private void setElements() {
		setLayout(new GridBagLayout());
		GridBagConstraints gridBagConstraints = new GridBagConstraints();

		// gridBagConstraints.anchor = GridBagConstraints.LINE_END;
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		gridBagConstraints.insets = new Insets(2, 2, 2, 2);
		// Add common options.
		JPanel skillsOptions = new JPanel(new GridLayout(0, 1));
		for (int c = 0; c < skillsToChoose.size(); c++) {
			JCheckBox skillOption = new JCheckBox(skillsToChoose.get(0));
			checkBoxes.add(skillOption);
			skillOption.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

				}
			});
			skillsOptions.add(skillOption);
		}

		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		getContentPane().add(skillsOptions, gridBagConstraints);

		JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
		CloseButton closeButton = new CloseButton(this);
		buttonPanel.add(closeButton);
		gridBagConstraints.anchor = GridBagConstraints.LINE_END;
		gridBagConstraints.fill = GridBagConstraints.NONE;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 0;
		gridBagConstraints.insets = new Insets(2, 2, 2, 2);
		getContentPane().add(buttonPanel, gridBagConstraints);
	}

	@Override
	public void updateFrame() {
		
	}
}
