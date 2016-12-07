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

import com.softwaremagico.files.MessageManager;
import com.softwaremagico.librodeesher.gui.components.SelectSkillPanel;
import com.softwaremagico.librodeesher.gui.elements.BaseCheckBox;
import com.softwaremagico.librodeesher.gui.elements.CloseButton;
import com.softwaremagico.librodeesher.gui.elements.SkillChangedListener;
import com.softwaremagico.librodeesher.gui.style.BaseFrame;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.export.pdf.PdfStandardSheet;
import com.softwaremagico.librodeesher.pj.skills.Skill;

public class SelectFavouriteSkillsWindow extends BaseFrame {
	private static final long serialVersionUID = -5951000255101455159L;
	private CharacterPlayer character;
	private SetSkillAsFavouritePanel setSkillAsFavouritePanel;
	private SelectedSkillsPanel selectedFavouriteSkillsPanel;
	private BaseCheckBox includeRecommendedFavouriteSkills;

	public SelectFavouriteSkillsWindow(final CharacterPlayer character) {
		this.character = character;
		defineWindow(350, 400);
		setResizable(false);
		setElements();
		updateFavouriteList();
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
		final SelectSkillPanel selectSkillPanel = new SelectSkillPanel(character);
		selectSkillPanel.addSkillChangedListener(new SkillChangedListener() {

			@Override
			public void skillChanged(Skill skill) {
				if (skill != null) {
					setSkillAsFavouritePanel.setFavouriteCheckBoxSelected(character.getFavouriteSkills().contains(skill.getName()));
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
				boolean attackMessageShown = false;
				boolean skillMessageShown = false;
				if (!setSkillAsFavouritePanel.isFavouriteCheckBoxSelected()) {
					character.getFavouriteSkills().remove(selectSkillPanel.getSelectedSkill().getName());
					attackMessageShown = false;
					skillMessageShown = false;
				} else {
					character.getFavouriteSkills().add(selectSkillPanel.getSelectedSkill().getName());
					// Check max favourite items!
					if (!skillMessageShown && character.getFavouriteNoOffensiveSkills().size() > PdfStandardSheet.MOST_USED_SKILLS_LINES * 2) {
						MessageManager.warningMessage(this.getClass().getName(), "Has seleccionado un número muy alto de habilidades favoritas, solo los "
								+ (PdfStandardSheet.MOST_USED_SKILLS_LINES * 2) + " primero se podrá visualizar en la ficha de personaje.",
								"¡Demasiadas habilidades favoritas!");
						skillMessageShown = true;
						// character.addFavouriteSkill(selectSkillPanel.getSelectedSkill().getName());
					}
					// Check max favorite items!
					if (!skillMessageShown && character.getFavouriteNoOffensiveSkills().size() > PdfStandardSheet.MOST_USED_SKILLS_LINES * 2) {
						MessageManager.warningMessage(this.getClass().getName(), "Has seleccionado un número muy alto de habilidades favoritas, solo los "
								+ (PdfStandardSheet.MOST_USED_SKILLS_LINES * 2) + " primero se podrá visualizar en la ficha de personaje.",
								"¡Demasiadas habilidades favoritas!");
						skillMessageShown = true;
					}
					if (!attackMessageShown && character.getFavouriteOffensiveSkills().size() > PdfStandardSheet.MOST_USED_ATTACKS_LINES) {
						MessageManager.warningMessage(this.getClass().getName(), "Has seleccionado un número muy alto de ataques favoritos, solo los "
								+ PdfStandardSheet.MOST_USED_ATTACKS_LINES + " primeros se podrán visualizar en la ficha de personaje.",
								"¡Demasiadas habilidades favoritas!");
						attackMessageShown = true;
					}
				}
				updateFavouriteList();
			}
		});
		getContentPane().add(setSkillAsFavouritePanel, gridBagConstraints);

		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.anchor = GridBagConstraints.LINE_END;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.weighty = 1;
		selectedFavouriteSkillsPanel = new SelectedSkillsPanel();
		getContentPane().add(selectedFavouriteSkillsPanel, gridBagConstraints);

		includeRecommendedFavouriteSkills = new BaseCheckBox("Include recommended skills");
		includeRecommendedFavouriteSkills.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				character.setRecommendedFavouriteSkillsIncluded(includeRecommendedFavouriteSkills.isSelected());
				updateFavouriteList();
			}
		});
		includeRecommendedFavouriteSkills.setSelected(character.isRecommendedFavouriteSkillsIncluded());
		gridBagConstraints.gridy = 3;
		gridBagConstraints.fill = GridBagConstraints.NONE;
		gridBagConstraints.anchor = GridBagConstraints.EAST;
		gridBagConstraints.fill = GridBagConstraints.NONE;
		gridBagConstraints.weighty = 0;
		getContentPane().add(includeRecommendedFavouriteSkills, gridBagConstraints);

		JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
		CloseButton closeButton = new CloseButton(this);
		buttonPanel.add(closeButton);
		gridBagConstraints.anchor = GridBagConstraints.LINE_END;
		gridBagConstraints.fill = GridBagConstraints.NONE;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 0;
		getContentPane().add(buttonPanel, gridBagConstraints);

		// Update first selected skill.
		Skill skill = selectSkillPanel.getSelectedSkill();
		if (skill != null) {
			setSkillAsFavouritePanel.setFavouriteCheckBoxSelected(character.getFavouriteSkills().contains(skill.getName()));
		}
	}

	private void updateFavouriteList() {
		List<String> sortedSkills = new ArrayList<>();
		sortedSkills.addAll(character.getFavouriteSkills());
		Collections.sort(sortedSkills);
		selectedFavouriteSkillsPanel.setOptions(sortedSkills);
	}

	@Override
	public void updateFrame() {

	}

}
