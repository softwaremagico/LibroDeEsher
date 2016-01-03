package com.softwaremagico.librodeesher.gui.components;

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
import java.util.HashSet;
import java.util.Set;

import javax.swing.JLabel;

import com.softwaremagico.librodeesher.gui.elements.CategoryChangedListener;
import com.softwaremagico.librodeesher.gui.elements.CategoryComboBox;
import com.softwaremagico.librodeesher.gui.elements.SkillChangedListener;
import com.softwaremagico.librodeesher.gui.elements.SkillComboBox;
import com.softwaremagico.librodeesher.gui.style.BasePanel;
import com.softwaremagico.librodeesher.gui.style.Fonts;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.categories.Category;
import com.softwaremagico.librodeesher.pj.skills.Skill;

public class SelectSkillPanel extends BasePanel {
	private static final long serialVersionUID = -1400449718847716450L;
	private CategoryComboBox categoryComboBox;
	private SkillComboBox skillComboBox;
	private Set<SkillChangedListener> skillListeners;
	private CharacterPlayer player;

	public SelectSkillPanel(CharacterPlayer player) {
		skillListeners = new HashSet<>();
		this.player = player;
		setElements();
	}

	@Override
	public void update() {

	}

	private void setElements() {
		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.ipadx = xPadding;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weightx = 0;
		constraints.weighty = 0;

		JLabel titleLabel = new JLabel("Selecciona una habilidad:");
		titleLabel.setFont(Fonts.getInstance().getBoldFont());
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 3;
		add(titleLabel, constraints);

		JLabel categoryLabel = new JLabel("Categoría:");
		constraints.fill = GridBagConstraints.NONE;
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.weightx = 0;
		constraints.gridwidth = 1;
		add(categoryLabel, constraints);

		categoryComboBox = createCategoryComboBox();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.weightx = 1;
		constraints.gridwidth = 3;
		add(categoryComboBox, constraints);

		JLabel skillLabel = new JLabel("Habilidad:");
		constraints.fill = GridBagConstraints.NONE;
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.weightx = 0;
		constraints.gridwidth = 1;
		add(skillLabel, constraints);

		skillComboBox = createSkillComboBox();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 1;
		constraints.gridy = 2;
		constraints.gridwidth = 3;
		constraints.weightx = 1;
		add(skillComboBox, constraints);
		skillComboBox.setSkills((Category) categoryComboBox.getSelectedItem());
	}

	private CategoryComboBox createCategoryComboBox() {
		CategoryComboBox categoryComboBox = new CategoryComboBox();
		categoryComboBox.setNormalStyle();
		categoryComboBox.addCategoryChangedListener(new CategoryChangedListener() {
			@Override
			public void categoryChanged(Category category) {
				skillComboBox.setSkills(category);
			}
		});
		return categoryComboBox;
	}

	private SkillComboBox createSkillComboBox() {
		SkillComboBox skillComboBox = new SkillComboBox(player);
		skillComboBox.setNormalStyle();
		skillComboBox.addSkillChangedListener(new SkillChangedListener() {
			@Override
			public void skillChanged(Skill skill) {
				skillChangedEvent();
			}
		});
		return skillComboBox;
	}

	public void skillChangedEvent() {
		for (SkillChangedListener listener : skillListeners) {
			listener.skillChanged((Skill) skillComboBox.getSelectedItem());
		}
	}

	public void addSkillChangedListener(SkillChangedListener listener) {
		skillListeners.add(listener);
	}

	public void removeSkillChangedListener(SkillChangedListener listener) {
		skillListeners.remove(listener);
	}

	public Skill getSelectedSkill() {
		return (Skill) skillComboBox.getSelectedItem();
	}
}
