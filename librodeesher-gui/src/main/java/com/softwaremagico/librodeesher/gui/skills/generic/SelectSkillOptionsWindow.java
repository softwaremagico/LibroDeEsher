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
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JPanel;

import com.softwaremagico.librodeesher.gui.components.SelectSkillPanel;
import com.softwaremagico.librodeesher.gui.elements.CloseButton;
import com.softwaremagico.librodeesher.gui.elements.SkillChangedListener;
import com.softwaremagico.librodeesher.gui.elements.UpdatePanelListener;
import com.softwaremagico.librodeesher.gui.skills.generic.SetSkillAsSpecializedOrGenericPanel.SkillMode;
import com.softwaremagico.librodeesher.gui.skills.generic.SetSkillAsSpecializedOrGenericPanel.SkillModeChanged;
import com.softwaremagico.librodeesher.gui.style.BaseFrame;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.categories.CategoryFactory;
import com.softwaremagico.librodeesher.pj.skills.Skill;

public class SelectSkillOptionsWindow extends BaseFrame {
	private static final long serialVersionUID = -8329696535425900576L;
	private CharacterPlayer character;
	private Skill selectedSkill;
	private Set<SkillChangedListener> skillChangedListeners;
	private SetSkillAsSpecializedOrGenericPanel setSkillAsSpecializedOrGenericPanel;
	private SelectSpecializationPanel selectSpecializationPanel;

	public SelectSkillOptionsWindow(final CharacterPlayer character) {
		this.character = character;
		skillChangedListeners = new HashSet<>();
		defineWindow(350, 350);
		setResizable(false);
		setElements();
		addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent arg0) {
			}

			@Override
			public void windowIconified(WindowEvent arg0) {
			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {
			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {
			}

			@Override
			public void windowClosing(WindowEvent arg0) {
			}

			@Override
			public void windowClosed(WindowEvent arg0) {
				// Update parent windows on closing.
				for (SkillChangedListener listener : skillChangedListeners) {
					listener.skillChanged(selectedSkill);
				}
			}

			@Override
			public void windowActivated(WindowEvent arg0) {
			}
		});
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
		SelectSkillPanel selectSkillPanel = new SelectSkillPanel(character, CategoryFactory.getCategories());
		getContentPane().add(selectSkillPanel, gridBagConstraints);

		gridBagConstraints.gridy = 1;
		setSkillAsSpecializedOrGenericPanel = new SetSkillAsSpecializedOrGenericPanel(character);
		getContentPane().add(setSkillAsSpecializedOrGenericPanel, gridBagConstraints);

		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.weighty = 1;
		selectSpecializationPanel = new SelectSpecializationPanel();
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
				selectedSkill = skill;
				setSkillAsSpecializedOrGenericPanel.updateSkill(skill);
				selectSpecializationPanel.removeOptions();
				updateSpecializationOptions(skill);
			}
		});
		setSkillAsSpecializedOrGenericPanel.addSkillModeChangedListener(new SkillModeChanged() {

			@Override
			public void setMode(Skill skill, SkillMode skillMode) {
				if (skill != null) {
					switch (skillMode) {
					case GENERALIZED:
						character.addGeneralized(skill);
						selectSpecializationPanel.setEnabled(false);
						break;
					case SPECIALIZED:
						selectSpecializationPanel.setEnabled(true);
						break;
					default:
						character.removeGeneralized(skill);
						character.removeSpecialized(skill);
						selectSpecializationPanel.setEnabled(false);
						break;
					}
				}
			}
		});
		selectSpecializationPanel.addUpdatePanelListener(new UpdatePanelListener() {

			@Override
			public void updatePanel() {
				if (selectedSkill != null) {
					character.setSkillSpecialization(selectedSkill, selectSpecializationPanel.getSelectedOptions());
				}
			}

		});

		// Initial values
		if (selectSkillPanel.getSelectedSkill() != null) {
			setSkillAsSpecializedOrGenericPanel.updateSkill(selectSkillPanel.getSelectedSkill());
			updateSpecializationOptions(selectSkillPanel.getSelectedSkill());
			selectedSkill = selectSkillPanel.getSelectedSkill();
		}
	}

	private void enableSkillSpecializationSelection(SkillMode skillMode) {
		switch (skillMode) {
		case GENERALIZED:
			selectSpecializationPanel.setEnabled(false);
			break;
		case SPECIALIZED:
			selectSpecializationPanel.setEnabled(true);
			break;
		default:
			selectSpecializationPanel.setEnabled(false);
		}
	}

	private void updateSpecializationOptions(Skill skill) {
		selectSpecializationPanel.setOptions(skill.getSpecialities(), skill.getSpecialities().size(), character.getSkillSpecializations(skill));
		if (setSkillAsSpecializedOrGenericPanel != null) {
			enableSkillSpecializationSelection(setSkillAsSpecializedOrGenericPanel.getSkillMode());
		}
	}

	@Override
	public void updateFrame() {

	}

	public void addSkillChangedListener(SkillChangedListener listener) {
		skillChangedListeners.add(listener);
	}

}
