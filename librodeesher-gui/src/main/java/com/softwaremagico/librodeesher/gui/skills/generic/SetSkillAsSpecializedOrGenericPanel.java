package com.softwaremagico.librodeesher.gui.skills.generic;

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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.ButtonGroup;

import com.softwaremagico.librodeesher.gui.elements.BaseRadioButton;
import com.softwaremagico.librodeesher.gui.style.BasePanel;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.skills.Skill;

public class SetSkillAsSpecializedOrGenericPanel extends BasePanel {
	private static final long serialVersionUID = -2805487044197535337L;
	private CharacterPlayer character;
	private BaseRadioButton specializedRadioButton;
	private BaseRadioButton genericRadioButton;
	private BaseRadioButton normalRadioButton;
	private Set<SkillModeChanged> skillListeners;
	private Skill skill;

	protected enum SkillMode {
		NORMAL, SPECIALIZED, GENERALIZED;
	}

	protected interface SkillModeChanged {
		void setMode(Skill skill, SkillMode skillMode);
	}

	protected SetSkillAsSpecializedOrGenericPanel(CharacterPlayer character) {
		this.character = character;
		setElements();
		skillListeners = new HashSet<>();
	}

	private void setElements() {
		specializedRadioButton = new BaseRadioButton("Especializada");
		genericRadioButton = new BaseRadioButton("Genérica");
		normalRadioButton = new BaseRadioButton("Normal");
		specializedRadioButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				skillChangedEvent();
			}
		});
		genericRadioButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				skillChangedEvent();
			}
		});
		normalRadioButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				skillChangedEvent();
			}
		});

		ButtonGroup skillGroup = new ButtonGroup();
		skillGroup.add(specializedRadioButton);
		skillGroup.add(genericRadioButton);
		skillGroup.add(normalRadioButton);
		normalRadioButton.setSelected(true);

		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.LINE_START;
		c.ipadx = 0;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.5;
		add(normalRadioButton, c);
		c.gridx = 1;
		add(specializedRadioButton, c);
		c.gridx = 2;
		add(genericRadioButton, c);
	}

	@Override
	public void update() {

	}

	public void updateSkill(Skill skill) {
		this.skill = skill;
		if (skill != null) {
			if (character.isGeneralized(skill)) {
				genericRadioButton.setSelected(true);
			} else if (character.isSkillSpecialized(skill)) {
				specializedRadioButton.setSelected(true);
			} else {
				normalRadioButton.setSelected(true);
			}
			// Disable not valid options;
			specializedRadioButton.setEnabled(!skill.getSpecialities().isEmpty());
			genericRadioButton.setEnabled(!character.isRestricted(skill));
			normalRadioButton.setEnabled(true);
		} else {
			genericRadioButton.setEnabled(false);
			specializedRadioButton.setEnabled(false);
			normalRadioButton.setEnabled(false);
		}
	}

	private void skillChangedEvent() {
		for (SkillModeChanged listener : skillListeners) {
			if (genericRadioButton.isSelected()) {
				listener.setMode(skill, SkillMode.GENERALIZED);
			} else if (specializedRadioButton.isSelected()) {
				listener.setMode(skill, SkillMode.SPECIALIZED);
			} else {
				listener.setMode(skill, SkillMode.NORMAL);
			}
		}
	}

	protected SkillMode getSkillMode() {
		if (genericRadioButton.isSelected()) {
			return SkillMode.GENERALIZED;
		} else if (specializedRadioButton.isSelected()) {
			return SkillMode.SPECIALIZED;
		} else {
			return SkillMode.NORMAL;
		}
	}

	public void addSkillModeChangedListener(SkillModeChanged listener) {
		skillListeners.add(listener);
	}

	public void removeSkillModeChangedListener(SkillModeChanged listener) {
		skillListeners.remove(listener);
	}
}
