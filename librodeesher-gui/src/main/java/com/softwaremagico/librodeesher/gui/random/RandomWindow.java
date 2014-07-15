package com.softwaremagico.librodeesher.gui.random;

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

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

import com.softwaremagico.librodeesher.gui.elements.BaseSpinner;
import com.softwaremagico.librodeesher.gui.elements.CategoryChangedListener;
import com.softwaremagico.librodeesher.gui.elements.CategoryComboBox;
import com.softwaremagico.librodeesher.gui.elements.SkillChangedListener;
import com.softwaremagico.librodeesher.gui.elements.SkillComboBox;
import com.softwaremagico.librodeesher.gui.elements.SpinnerValueChangedListener;
import com.softwaremagico.librodeesher.gui.style.BaseFrame;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.categories.Category;
import com.softwaremagico.librodeesher.pj.random.RandomCharacterPlayer;
import com.softwaremagico.librodeesher.pj.skills.Skill;

public class RandomWindow extends BaseFrame {
	private static final long serialVersionUID = -2265764014622959400L;
	private static final Integer MARGIN = 10;
	private CharacterPlayer characterPlayer;
	private JSlider generalizationSpecializationSlider;
	private CategoryComboBox categoryComboBox;
	private SkillComboBox skillComboBox;
	private BaseSpinner categorySpinner, skillSpinner;
	private RandomCharacterPlayer randomPlayer;
	private Map<String, Integer> suggestedSkillsRanks;
	private Map<String, Integer> suggestedCategoriesRanks;

	public RandomWindow(CharacterPlayer characterPlayer) {
		this.characterPlayer = characterPlayer;
		suggestedCategoriesRanks = new HashMap<>();
		suggestedSkillsRanks = new HashMap<>();
		defineWindow(500, 400);
		setResizable(false);
		setElements();
	}

	@Override
	public void updateFrame() {
		// setResizable(false);
		setElements();
	}

	private void setElements() {
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(MARGIN, MARGIN, MARGIN, MARGIN));
		panel.setLayout(new GridLayout(0, 1));
		panel.add(createSpecializationPanel());
		panel.add(createCategoryPanel());
	}

	private JPanel createSpecializationPanel() {
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory
				.createEtchedBorder(), new EmptyBorder(MARGIN, MARGIN,
				MARGIN / 2, MARGIN)));

		GridBagLayout layout = new GridBagLayout();
		panel.setLayout(layout);
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.ipadx = xPadding;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weightx = 1;
		constraints.weighty = 1;

		generalizationSpecializationSlider = createSpecializedSlider();
		panel.add(generalizationSpecializationSlider, constraints);

		return panel;
	}

	private JPanel createCategoryPanel() {
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory
				.createEtchedBorder(), new EmptyBorder(MARGIN, MARGIN,
				MARGIN / 2, MARGIN)));

		GridBagLayout layout = new GridBagLayout();
		panel.setLayout(layout);
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.ipadx = xPadding;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weightx = 0;
		constraints.weighty = 0;

		JLabel titleLabel = new JLabel("Habilidades Deseadas:");
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 3;
		panel.add(titleLabel, constraints);

		JLabel categoryLabel = new JLabel("Categoría:");
		constraints.fill = GridBagConstraints.NONE;
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		panel.add(categoryLabel, constraints);

		categoryComboBox = createCategoryComboBox();
		categorySpinner = createCategorySpinner();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.weightx = 1;
		constraints.gridwidth = 3;
		panel.add(categoryComboBox, constraints);

		constraints.gridx = 4;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.weightx = 0;
		panel.add(categorySpinner, constraints);

		JLabel skillLabel = new JLabel("Habilidad:");
		constraints.fill = GridBagConstraints.NONE;
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		panel.add(skillLabel, constraints);

		skillComboBox = createSkillComboBox();
		skillSpinner = createSkillSpinner();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 1;
		constraints.gridy = 2;
		constraints.gridwidth = 3;
		constraints.weightx = 1;
		panel.add(skillComboBox, constraints);
		if (categorySpinner.getValue() != null) {
			skillComboBox.setSkills((Category) categoryComboBox
					.getSelectedItem());
		}

		constraints.gridx = 4;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		constraints.weightx = 0;
		panel.add(skillSpinner, constraints);

		return panel;
	}

	private JSlider createSpecializedSlider() {
		final int GENERALIZED = -2;
		final int SPECIALIZED = 2;
		final int INIT = 0;

		JSlider generalizationSpecializationSlider = new JSlider(
				JSlider.HORIZONTAL, GENERALIZED, SPECIALIZED, INIT);
		// generalizationSpecializationSlider.addChangeListener(this);

		// Turn on labels at major tick marks.
		generalizationSpecializationSlider.setMajorTickSpacing(1);
		generalizationSpecializationSlider.setMinorTickSpacing(1);
		generalizationSpecializationSlider.setPaintTicks(true);
		generalizationSpecializationSlider.setPaintLabels(true);

		Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
		labelTable.put(GENERALIZED, new JLabel("Generalizado"));
		labelTable.put(INIT, new JLabel("Equilibrado"));
		labelTable.put(SPECIALIZED, new JLabel("Especializado"));
		generalizationSpecializationSlider.setLabelTable(labelTable);

		return generalizationSpecializationSlider;
	}

	private CategoryComboBox createCategoryComboBox() {
		CategoryComboBox categoryComboBox = new CategoryComboBox();
		categoryComboBox
				.addCategoryChangedListener(new CategoryChangedListener() {
					@Override
					public void categoryChanged(Category category) {
						categorySpinner
								.setValue(getSuggestedCategoriesRanks(category
										.getName()));
						skillComboBox.setSkills(category);
					}
				});
		return categoryComboBox;
	}

	private SkillComboBox createSkillComboBox() {
		SkillComboBox skillComboBox = new SkillComboBox();
		skillComboBox.addSkillChangedListener(new SkillChangedListener() {
			@Override
			public void skillChanged(Skill skill) {
				if (skill != null) {
					skillSpinner.setValue(getSuggestedSkillRanks(skill
							.getName()));
					skillSpinner.setEnabled(true);
				} else {
					skillSpinner.setValue(0);
					skillSpinner.setEnabled(false);
				}
			}
		});
		return skillComboBox;
	}

	public void setSuggestedCategoryRanks(String categoryName, int ranks) {
		if (ranks <= 0) {
			suggestedCategoriesRanks.remove(categoryName);
		} else {
			suggestedCategoriesRanks.put(categoryName, ranks);
		}
	}

	public void setSuggestedSkillRanks(String skillName, int ranks) {
		if (ranks <= 0) {
			suggestedSkillsRanks.remove(skillName);
		} else {
			suggestedSkillsRanks.put(skillName, ranks);
		}
	}

	private Integer getSuggestedCategoriesRanks(String categoryName) {
		if (suggestedCategoriesRanks.get(categoryName) == null
				|| suggestedCategoriesRanks.get(categoryName) < 0) {
			return 0;
		}
		return suggestedCategoriesRanks.get(categoryName);
	}

	private Integer getSuggestedSkillRanks(String skillName) {
		if (suggestedSkillsRanks.get(skillName) == null
				|| suggestedSkillsRanks.get(skillName) < 0) {
			return 0;
		}
		return suggestedSkillsRanks.get(skillName);
	}

	private BaseSpinner createCategorySpinner() {
		SpinnerModel spinnerModel = new SpinnerNumberModel(0, 0, 50, 1);
		BaseSpinner categorySpinner = new BaseSpinner(spinnerModel);
		categorySpinner
				.addSpinnerValueChangedListener(new SpinnerValueChangedListener() {
					@Override
					public void valueChanged(int value) {
						setSuggestedCategoryRanks(((Category) categoryComboBox
								.getSelectedItem()).getName(), value);
					}
				});
		return categorySpinner;
	}

	private BaseSpinner createSkillSpinner() {
		SpinnerModel spinnerModel = new SpinnerNumberModel(0, 0, 50, 1);
		BaseSpinner skillSpinner = new BaseSpinner(spinnerModel);
		skillSpinner
				.addSpinnerValueChangedListener(new SpinnerValueChangedListener() {
					@Override
					public void valueChanged(int value) {
						setSuggestedSkillRanks(((Skill) skillComboBox
								.getSelectedItem()).getName(), value);
					}
				});
		return skillSpinner;
	}
}
