package com.softwaremagico.librodeesher.gui.skills.favourite;
/*
 * #%L
 * Libro de Esher (GUI)
 * %%
 * Copyright (C) 2007 - 2015 Softwaremagico
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
import java.util.Collections;
import java.util.List;

import javax.swing.JPanel;

import com.softwaremagico.librodeesher.gui.components.SelectSkillPanel;
import com.softwaremagico.librodeesher.gui.elements.CloseButton;
import com.softwaremagico.librodeesher.gui.elements.SkillChangedListener;
import com.softwaremagico.librodeesher.gui.style.BaseFrame;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.skills.Skill;

public class SelectFavouriteSkillsWindow extends BaseFrame {
	private static final long serialVersionUID = -5951000255101455159L;
	private CharacterPlayer character;
	private SetSkillAsFavouritePanel setSkillAsFavouritePanel;
	private SelectedSkillsPanel selectSpecializationPanel;

	public SelectFavouriteSkillsWindow(final CharacterPlayer character) {
		this.character = character;
		defineWindow(350, 350);
		setResizable(false);
		setElements();
	}

	private void setElements() {
		setLayout(new GridBagLayout());
		GridBagConstraints gridBagConstraints = new GridBagConstraints();

		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.ipady = yPadding;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.weightx = 0;
		gridBagConstraints.weighty = 0;
		gridBagConstraints.insets = new Insets(2, 2, 2, 2);
		final SelectSkillPanel selectSkillPanel = new SelectSkillPanel();
		selectSkillPanel.addSkillChangedListener(new SkillChangedListener() {

			@Override
			public void skillChanged(Skill skill) {
				if (skill != null) {
					if (character.getFavouriteSkills().contains(skill.getName())) {
						setSkillAsFavouritePanel.setFavouriteCheckBoxSelected(true);
					} else {
						setSkillAsFavouritePanel.setFavouriteCheckBoxSelected(false);
					}
				}
			}
		});
		getContentPane().add(selectSkillPanel, gridBagConstraints);

		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridy = 1;
		setSkillAsFavouritePanel = new SetSkillAsFavouritePanel();
		setSkillAsFavouritePanel.addFavouriteCheckBoxActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!setSkillAsFavouritePanel.isFavouriteCheckBoxSelected()) {
					character.getFavouriteSkills().remove(selectSkillPanel.getSelectedSkill().getName());
				} else {
					character.getFavouriteSkills().add(selectSkillPanel.getSelectedSkill().getName());
				}
				updateFavouriteList();
			}
		});
		getContentPane().add(setSkillAsFavouritePanel, gridBagConstraints);

		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.weighty = 1;
		selectSpecializationPanel = new SelectedSkillsPanel();
		getContentPane().add(selectSpecializationPanel, gridBagConstraints);

		JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
		CloseButton closeButton = new CloseButton(this);
		buttonPanel.add(closeButton);
		gridBagConstraints.anchor = GridBagConstraints.LINE_END;
		gridBagConstraints.fill = GridBagConstraints.NONE;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 0;
		getContentPane().add(buttonPanel, gridBagConstraints);

		selectSkillPanel.addSkillChangedListener(new SkillChangedListener() {
			@Override
			public void skillChanged(Skill skill) {

			}
		});
	}

	private void updateFavouriteList() {
		List<String> sortedSkills = new ArrayList<>();
		sortedSkills.addAll(character.getFavouriteSkills());
		Collections.sort(sortedSkills);
		selectSpecializationPanel.setOptions(sortedSkills);
	}

	@Override
	public void updateFrame() {

	}

}
