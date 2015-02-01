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
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import com.softwaremagico.librodeesher.basics.ChooseGroup;
import com.softwaremagico.librodeesher.basics.ChooseType;
import com.softwaremagico.librodeesher.gui.elements.CloseButton;
import com.softwaremagico.librodeesher.gui.options.SelectOption;
import com.softwaremagico.librodeesher.gui.style.BaseFrame;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.skills.ChooseSkillName;
import com.softwaremagico.librodeesher.pj.skills.Skill;

public class SelectSkillWindow extends BaseFrame {
	private static final long serialVersionUID = -1395591756061099850L;
	private CharacterPlayer character;
	private Skill skill;
	private SelectOption<String> optionsToChoose;
	private List<SkillEnabledListener> listeners;

	interface SkillEnabledListener {
		void skillEnabledEvent(Skill skill, String skillSelected, boolean selected);
	}

	public SelectSkillWindow(CharacterPlayer character, Skill skill) {
		this.character = character;
		this.skill = skill;
		listeners = new ArrayList<>();
		defineSize();
		// setResizable(false);
		setElements();
	}

	private void defineSize() {
		defineWindow(350, 250);
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
		// Add skill options.
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		ChooseGroup<String> skillsToChoose = new ChooseSkillName(1, skill.getEnableSkills(),
				ChooseType.ENABLED);
		SelectOption<String> options = new SelectOption<String>(this, skillsToChoose);
		optionsToChoose = options;
		gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
		options.setPointCounterLabel("   Activa: ");
		getContentPane().add(options, gridBagConstraints);

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
		character.disableSkillOption(skill);
		for (String skillSelected : optionsToChoose.getSelectedOptions()) {
			character.enableSkillOption(skill.getName(), skillSelected);
		}
		for (String skillsOptions : optionsToChoose.getOptions().getOptionsGroup()) {
			for (SkillEnabledListener listener : listeners) {
				listener.skillEnabledEvent(skill, skillsOptions, optionsToChoose.getSelectedOptions()
						.contains(skillsOptions));
			}
		}
	}

	public void addSkillEnabledListener(SkillEnabledListener listener) {
		listeners.add(listener);
	}

}
