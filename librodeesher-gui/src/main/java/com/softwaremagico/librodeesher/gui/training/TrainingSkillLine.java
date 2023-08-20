package com.softwaremagico.librodeesher.gui.training;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultFormatter;

import com.softwaremagico.librodeesher.gui.elements.BaseComboBox;
import com.softwaremagico.librodeesher.gui.elements.BaseSpinner;
import com.softwaremagico.librodeesher.gui.elements.ListBackgroundPanel;
import com.softwaremagico.librodeesher.gui.elements.ListLabel;
import com.softwaremagico.librodeesher.gui.style.BaseLine;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.training.TrainingCategory;
import com.softwaremagico.librodeesher.pj.training.TrainingSkill;

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

public class TrainingSkillLine extends BaseLine {
	private static final long serialVersionUID = 8590311992050051013L;
	private CharacterPlayer character;
	private TrainingCategory trainingCategory;
	private TrainingSkill trainingSkill;
	private TrainingCategoryPanel parentPanel;
	private SkillComboBox<String> chooseSkillsComboBox = null;
	protected BaseSpinner rankSpinner;

	protected TrainingSkillLine(CharacterPlayer character, TrainingCategory category, TrainingSkill skill, TrainingCategoryPanel parent, Color background) {
		this.character = character;
		this.trainingSkill = skill;
		this.parentPanel = parent;
		this.trainingCategory = category;
		setDefaultBackground(background);
		setElements();
	}

	private void addItemsToComboBox() {
		chooseSkillsComboBox.removeAllItems();
		for (String skillName : trainingSkill.getSkillOptions()) {
			chooseSkillsComboBox.addItem(skillName);
		}
	}

	private ListBackgroundPanel getSkillOrGroup() {
		if (!trainingSkill.needToChooseOneSkill()) {
			ListLabel skillLabel = new ListLabel(trainingSkill.getSkillOptions().get(0), SwingConstants.LEFT, 150, columnHeight);
			return new ListBackgroundPanel(skillLabel, getDefaultBackground());
		} else {
			chooseSkillsComboBox = new SkillComboBox<>();
			chooseSkillsComboBox.setBackground(getDefaultBackground());
			addItemsToComboBox();
			return new ListBackgroundPanel(chooseSkillsComboBox, getDefaultBackground());
		}
	}

	protected void setElements() {
		this.removeAll();
		setLayout(new GridLayout(1, 0));
		setBackground(getDefaultBackground());

		ListBackgroundPanel categoryPanel = getSkillOrGroup();
		add(categoryPanel);

		ListLabel minHab = new ListLabel("", SwingConstants.CENTER);
		add(new ListBackgroundPanel(minHab, getDefaultBackground()));

		ListLabel maxHab = new ListLabel("", SwingConstants.CENTER);
		add(new ListBackgroundPanel(maxHab, getDefaultBackground()));

		JPanel spinnerPanel = new JPanel();
		SpinnerModel sm = new SpinnerNumberModel((int) trainingSkill.getRanks(), (int) trainingSkill.getRanks(), (int) trainingCategory.getSkillRanks()
				- trainingCategory.getMinSkills() + 1, 1);
		rankSpinner = new BaseSpinner(sm);
		rankSpinner.setBackground(getDefaultBackground());
		spinnerPanel.add(rankSpinner);
		spinnerPanel.setBackground(getDefaultBackground());
		add(spinnerPanel, getDefaultBackground());
		addRankSpinnerEvent();
	}

	protected void addRankSpinnerEvent() {
		JComponent comp = rankSpinner.getEditor();
		JFormattedTextField field = (JFormattedTextField) comp.getComponent(0);
		DefaultFormatter formatter = (DefaultFormatter) field.getFormatter();
		formatter.setCommitsOnValidEdit(true);
		rankSpinner.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				// Correct the spinner if too many ranks
				if (parentPanel.getSpinnerValues(trainingCategory) > trainingCategory.getSkillRanks()) {
					rankSpinner.setValue((Integer) rankSpinner.getValue() - 1);
					// Correct the spinner if too many skills.
				} else if (parentPanel.getSkillsWithRanks(trainingCategory) > trainingCategory.getMaxSkills()) {
					rankSpinner.setValue((Integer) rankSpinner.getValue() - 1);
					// At least some skills.
				} else if (parentPanel.getSkillsWithRanks(trainingCategory) < trainingCategory.getMinSkills()
						&& (trainingCategory.getSkillRanks() - parentPanel.getSpinnerValues(trainingCategory)) < trainingCategory.getMinSkills()
								- parentPanel.getSkillsWithRanks(trainingCategory)) {
					rankSpinner.setValue((Integer) rankSpinner.getValue() - 1);
				} else {
					// Update character
					setSkillRanks();
				}
			}
		});
	}

	public void setSkillRanks() {
		character.addTrainingSkillRanks(parentPanel.getTraining(), trainingCategory, getSelectedSkill(), (Integer) rankSpinner.getValue());
	}

	public int getSelectedRanks() {
		return (Integer) rankSpinner.getValue();
	}

	public String getSelectedSkill() {
		if (!trainingSkill.needToChooseOneSkill()) {
			return trainingSkill.getSkillOptions().get(0);
		} else {
			return (String) chooseSkillsComboBox.getSelectedItem();
		}
	}

	protected class SkillComboBox<E> extends BaseComboBox<E> {
		private static final long serialVersionUID = 7410336849188101092L;

		@Override
		public void doAction() {

		}
	}

	@Override
	public void update() {
		// setRanksNumber();
	}

}
