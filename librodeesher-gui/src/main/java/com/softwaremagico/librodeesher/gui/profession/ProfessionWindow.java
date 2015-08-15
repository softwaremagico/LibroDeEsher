package com.softwaremagico.librodeesher.gui.profession;

/*
 * #%L
 * Libro de Esher GUI
 * %%
 * Copyright (C) 2007 - 2013 Softwaremagico
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

import com.softwaremagico.librodeesher.gui.elements.CloseButton;
import com.softwaremagico.librodeesher.gui.options.SelectOption;
import com.softwaremagico.librodeesher.gui.style.BaseFrame;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.skills.Skill;

public class ProfessionWindow extends BaseFrame {
	private static final long serialVersionUID = -1395591756061099850L;
	private CharacterPlayer character;
	private List<SelectOption<Skill>> commonOptions;
	private List<SelectOption<Skill>> professionalOptions;
	private List<SelectOption<Skill>> restrictedOptions;
	Integer widthCells, heighCells;

	public ProfessionWindow(CharacterPlayer character) {
		commonOptions = new ArrayList<>();
		professionalOptions = new ArrayList<>();
		restrictedOptions = new ArrayList<>();
		this.character = character;
		defineSize();
		// setResizable(false);
		setElements();
	}

	private void defineSize() {
		widthCells = Math.max(
				Math.max(character.getProfession().getProfessionalSkillsToChoose().size(), character
						.getProfession().getCommonSkillsToChoose().size()), character.getProfession()
						.getRestrictedSkillsToChoose().size());
		heighCells = 0;
		if (!character.getProfession().getProfessionalSkillsToChoose().isEmpty()) {
			heighCells++;
		}
		if (!character.getProfession().getCommonSkillsToChoose().isEmpty()) {
			heighCells++;
		}
		if (!character.getProfession().getRestrictedSkillsToChoose().isEmpty()) {
			heighCells++;
		}
		defineWindow(250 * widthCells, 50 + 200 * heighCells);
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
		int i = 0;
		for (int c = 0; c < character.getProfession().getCommonSkillsToChoose().size(); c++) {
			gridBagConstraints.gridx = i;
			gridBagConstraints.gridy = 0;
			i++;
			SelectOption<Skill> optionsLayout = new SelectOption<Skill>(this, character.getProfession()
					.getCommonSkillsToChoose().get(c), character.getCommonSkillsChoseFromProfession());
			commonOptions.add(optionsLayout);
			if (c == character.getProfession().getCommonSkillsToChoose().size() - 1) {
				gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
			} else {
				gridBagConstraints.gridwidth = 1;
			}
			optionsLayout.setPointCounterLabel("   Comunes: ");
			getContentPane().add(optionsLayout, gridBagConstraints);
		}
		// Add professional options.
		i = 0;
		for (int c = 0; c < character.getProfession().getProfessionalSkillsToChoose().size(); c++) {
			gridBagConstraints.gridx = i;
			gridBagConstraints.gridy = 1;
			i++;
			SelectOption<Skill> optionsLayout = new SelectOption<Skill>(this, character.getProfession()
					.getProfessionalSkillsToChoose().get(c), character.getProfessionalSkillsChoseFromProfession());
			professionalOptions.add(optionsLayout);
			if (c == character.getProfession().getProfessionalSkillsToChoose().size() - 1) {
				gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
			} else {
				gridBagConstraints.gridwidth = 1;
			}
			if (commonOptions.size() > 0) {
				gridBagConstraints.insets = new Insets(20, 2, 2, 2);
			}
			optionsLayout.setPointCounterLabel("   Profesionales: ");
			getContentPane().add(optionsLayout, gridBagConstraints);
		}
		// Add restricted options.
		i = 0;
		for (int c = 0; c < character.getProfession().getRestrictedSkillsToChoose().size(); c++) {
			gridBagConstraints.gridx = i;
			gridBagConstraints.gridy = 2;
			i++;
			SelectOption<Skill> optionsLayout = new SelectOption<Skill>(this, character.getProfession()
					.getRestrictedSkillsToChoose().get(c), character.getRestrictedSkillsChoseFromProfession());
			restrictedOptions.add(optionsLayout);
			if (c == character.getProfession().getRestrictedSkillsToChoose().size() - 1) {
				gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
			} else {
				gridBagConstraints.gridwidth = 1;
			}
			if (commonOptions.size() > 0 || professionalOptions.size() > 0) {
				gridBagConstraints.insets = new Insets(20, 2, 2, 2);
			}
			optionsLayout.setPointCounterLabel("   Restringidas: ");
			getContentPane().add(optionsLayout, gridBagConstraints);
		}

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
		List<String> selectedCommon = new ArrayList<>();
		for (SelectOption<Skill> commonOption : commonOptions) {
			selectedCommon.addAll(commonOption.getSelectedOptions());
		}
		character.setCommonSkillsChoseFromProfession(selectedCommon);

		List<String> selectedProfessional = new ArrayList<>();
		for (SelectOption<Skill> professionalOption : professionalOptions) {
			selectedProfessional.addAll(professionalOption.getSelectedOptions());
		}
		character.setProfessionalSkillsChoseFromProfession(selectedProfessional);

		List<String> selectedRestricted = new ArrayList<>();
		for (SelectOption<Skill> restrictedOption : restrictedOptions) {
			selectedRestricted.addAll(restrictedOption.getSelectedOptions());
		}
		character.setRestrictedSkillsChoseFromProfession(selectedRestricted);
	}

}
