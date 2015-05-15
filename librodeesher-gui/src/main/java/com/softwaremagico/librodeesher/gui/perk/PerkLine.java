package com.softwaremagico.librodeesher.gui.perk;

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

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.softwaremagico.librodeesher.gui.elements.BaseCheckBox;
import com.softwaremagico.librodeesher.gui.elements.ListBackgroundPanel;
import com.softwaremagico.librodeesher.gui.elements.ListLabel;
import com.softwaremagico.librodeesher.gui.style.BaseLine;
import com.softwaremagico.librodeesher.gui.style.BasePanel;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.categories.Category;
import com.softwaremagico.librodeesher.pj.categories.ChooseCategoryGroup;
import com.softwaremagico.librodeesher.pj.perk.Perk;
import com.softwaremagico.librodeesher.pj.skills.ChooseSkillGroup;
import com.softwaremagico.librodeesher.pj.skills.Skill;

public class PerkLine extends BaseLine {
	private static final long serialVersionUID = 4767533985935793545L;
	private final static Integer DEFAULT_COLUMN_WIDTH = 50;
	private BasePanel parent;
	private ListLabel perkLabel, perkCost, perkCategory, perkDescription;
	private Perk perk;
	private Color background;
	private BaseCheckBox perkCheckBox;
	private CharacterPlayer character;
	private boolean updating = false;

	public PerkLine(CharacterPlayer character, Perk perk, Color background, BasePanel parentWindow) {
		this.parent = parentWindow;
		this.perk = perk;
		this.background = background;
		this.character = character;
		setBackground(background);
		setElements();
	}

	protected void setElements() {
		this.removeAll();
		setLayout(new GridBagLayout());
		GridBagConstraints gridBagConstraints = new GridBagConstraints();

		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.weighty = 0;

		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 0;
		JPanel panel = new JPanel();
		updating = true;
		perkCheckBox = new BaseCheckBox("");
		perkCheckBox.setSelected(character.isPerkChoosed(perk));
		panel.add(perkCheckBox);
		panel.setBackground(background);
		perkCheckBox.setBackground(background);
		perkCheckBox.addItemListener(new CheckBoxListener());
		add(panel, gridBagConstraints);
		updating = false;

		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 0;
		perkLabel = new ListLabel(perk.getName(), SwingConstants.LEFT, DEFAULT_COLUMN_WIDTH * 6, columnHeight);
		add(new ListBackgroundPanel(perkLabel, background), gridBagConstraints);

		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 0;
		perkCost = new ListLabel(perk.getCost().toString(), SwingConstants.CENTER, DEFAULT_COLUMN_WIDTH, columnHeight);
		add(new ListBackgroundPanel(perkCost, background), gridBagConstraints);

		gridBagConstraints.gridx = 3;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 0;
		perkCategory = new ListLabel(perk.getCategory().toString(), SwingConstants.CENTER, DEFAULT_COLUMN_WIDTH * 2,
				columnHeight);
		add(new ListBackgroundPanel(perkCategory, background), gridBagConstraints);

		gridBagConstraints.gridx = 4;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 1;
		perkDescription = new ListLabel(perk.getLongDescription().toString(), SwingConstants.LEFT);
		add(new ListBackgroundPanel(perkDescription, background), gridBagConstraints);

	}

	class CheckBoxListener implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent e) {
			if (!updating) {
				if (perkCheckBox.isSelected()) {
					if (perk.getCost() <= character.getRemainingPerksPoints()) {
						character.addPerk(perk);
						createSelectOptionsWindow();
					} else {
						perkCheckBox.setSelected(false);
					}
				} else {
					if (character.getRemainingPerksPoints() + perk.getCost() < 0) {
						perkCheckBox.setSelected(true);
					} else {
						character.removePerk(perk);
					}
				}
				update();
			}
		}
	}

	private String getBonusTag() {
		if (perk.getChosenBonus() > 0) {
			return "+" + perk.getChosenBonus();
		}
		return perk.getChosenBonus().toString();
	}

	private void createSelectOptionsWindow() {
		if (!perk.getCategoriesToChoose().isEmpty()) {
			for (ChooseCategoryGroup options : perk.getCategoriesToChoose()) {
				PerkOptionsWindow<Category> optionsWindow = new PerkOptionsWindow<Category>(character, perk, options, this);
				optionsWindow.setPointCounterLabel("Categorias con (" + getBonusTag() + "): ");
				optionsWindow.setVisible(true);
			}
		}

		if (!perk.getSkillsToChoose().isEmpty()) {
			for (ChooseSkillGroup options : perk.getSkillsToChoose()) {
				PerkOptionsWindow<Skill> optionsWindow = new PerkOptionsWindow<Skill>(character, perk, options, this);
				optionsWindow.setPointCounterLabel("Habilidades con (" + getBonusTag() + "): ");
				optionsWindow.setVisible(true);
			}
		}

		if (!perk.getCommonSkillsToChoose().isEmpty()) {
			for (ChooseSkillGroup options : perk.getCommonSkillsToChoose()) {
				PerkOptionsWindow<Skill> optionsWindow = new PerkOptionsWindow<Skill>(character, perk, options, this);
				optionsWindow.setPointCounterLabel("Habilidades comunes: ");
				optionsWindow.setVisible(true);
			}
		}
	}

	@Override
	public void update() {
		parent.update();
	}

	protected void removePerk() {
		perkCheckBox.setSelected(false);
	}

}
