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
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.softwaremagico.files.MessageManager;
import com.softwaremagico.librodeesher.config.Config;
import com.softwaremagico.librodeesher.gui.elements.BaseCheckBox;
import com.softwaremagico.librodeesher.gui.elements.ListBackgroundPanel;
import com.softwaremagico.librodeesher.gui.elements.ListLabel;
import com.softwaremagico.librodeesher.gui.style.BaseLine;
import com.softwaremagico.librodeesher.gui.style.BasePanel;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.categories.Category;
import com.softwaremagico.librodeesher.pj.categories.ChooseCategoryGroup;
import com.softwaremagico.librodeesher.pj.perk.Perk;
import com.softwaremagico.librodeesher.pj.perk.PerkFactory;
import com.softwaremagico.librodeesher.pj.perk.PerkGrade;
import com.softwaremagico.librodeesher.pj.race.exceptions.InvalidRaceDefinition;
import com.softwaremagico.librodeesher.pj.skills.ChooseSkillGroup;
import com.softwaremagico.librodeesher.pj.skills.Skill;

public class PerkLine extends BaseLine {
	private static final long serialVersionUID = 4767533985935793545L;
	private static final int MAX_TRYES = 100;
	private final static Integer DEFAULT_COLUMN_WIDTH = 50;
	private final static String NOTHING = "ninguno";
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
		perkCheckBox.setSelected(character.isPerkChoosed(perk) || character.getRace().getPerks().contains(perk));
		perkCheckBox.setEnabled(!character.getRace().getPerks().contains(perk) && !character.isSelectedAsRandomPerk(perk) && !character.isWeakness(perk)
				&& !character.hasWeakness(perk));
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
		perkCategory = new ListLabel(perk.getGrade().toString(), SwingConstants.CENTER, DEFAULT_COLUMN_WIDTH * 2, columnHeight);
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
			try {
				if (!updating) {
					Perk weakness = null;
					if (!character.getRace().getPerks().contains(perk)) {
						if (perkCheckBox.isSelected()) {
							if (perk.getCost() <= character.getRemainingPerksPoints()) {
								updating = true;
								createSelectOptionsWindow();
								// It is not a weakness selected from a previous perk.
								if (!character.isWeakness(perk)) {
									character.addPerk(perk);
									// Must select a weakness.
									weakness = selectWeakness();
									if (weakness != null) {
										MessageManager.infoMessage(this.getClass().getName(), "El defecto adquirido es '" + weakness.getName() + "' ["
												+ weakness.getGrade().getTag() + "]", "¡Nuevo defecto adquirido!");
										character.addWeakness(perk, weakness);
										((PerksListPanel) parent).selectPerk(weakness, true);
										((PerksListPanel) parent).enablePerk(weakness, false);
										// character.setAsRandomPerk(perk);
										perkCheckBox.setEnabled(false);
									}
								}
								// SelectOptionWindows unselect the checkbox by
								// unknown reason. We force again to select it.
								perkCheckBox.setSelected(true);
								updating = false;
							} else {
								perkCheckBox.setSelected(false);
							}
						} else {
							// Cannot remove a weakness if cost does not allow it.
							if (character.getRemainingPerksPoints() + perk.getCost() < 0) {
								perkCheckBox.setSelected(true);
							} else {
								// It is a weakness?
								if (character.removeWeakness(perk)) {
									((PerksListPanel) parent).selectPerk(perk, false);
									((PerksListPanel) parent).enablePerk(perk, true);
								}
								// It is a perk?
								character.removePerk(perk);
							}
						}
					}
					update();
					// Cannot afford a random perk. Remove it.
					if ((character.getRemainingPerksPoints() < 0 || character.getRemainingBackgroundPoints() < 0) && !perkCheckBox.isEnabled()) {
						MessageManager.warningMessage(this.getClass().getName(), "El coste del talento es demasiado alto. Se anula la operación",
								"No de puede incluir el nuevo talento.");
						perkCheckBox.setSelected(false);
						perkCheckBox.setEnabled(true);
						// Remove also the weakness if exists.
						if (weakness != null) {
							((PerksListPanel) parent).selectPerk(weakness, false);
							((PerksListPanel) parent).enablePerk(weakness, true);
						}
					}
				}
			} catch (InvalidRaceDefinition ex) {
				MessageManager.basicErrorMessage(this.getClass().getName(), ex.getMessage(), "Raza incorrectamente definida.");
				MessageManager.errorMessage(this.getClass().getName(), ex);
			}
		}
	}

	private String getBonusTag() {
		if (perk.getChosenBonus() > 0) {
			return "+" + perk.getChosenBonus();
		}
		return perk.getChosenBonus().toString();
	}

	private Perk selectWeakness() {
		if (Config.getPerksCostHistoryPoints() && perk.getCost() > 0) {
			List<PerkGrade> lesserGrades = perk.getGrade().getLesserGrades(2);
			List<String> tags = new ArrayList<String>();
			if (lesserGrades.size() > 0) {
				for (PerkGrade weaknessGrade : lesserGrades) {
					// Cost is affordable.
					tags.add(weaknessGrade.getTag());
				}
				// add none option.
				tags.add(NOTHING);
				if (tags.isEmpty()) {
					return null;
				}
				int selected = MessageManager.questionMessage("¿Quieres escoger un defecto aleatorio para este adiestramiento?", "Añadir un defecto.",
						tags.toArray());
				if (selected < 0 || tags.get(selected).equals(NOTHING)) {
					return null;
				}

				PerkGrade weaknessGrade = PerkGrade.getPerkCategory(tags.get(selected));
				Perk weakness = null;
				if (weaknessGrade != null) {
					weakness = PerkFactory.getRandomWeakness(weaknessGrade, perk.getPerkType());
					// Select a not selected already weakness.
					int tryes = 0;
					while ((character.isWeakness(weakness) || character.getPerks().contains(weakness)) && tryes < MAX_TRYES) {
						weakness = PerkFactory.getRandomWeakness(weaknessGrade);
						tryes++;
					}
					if (tryes == MAX_TRYES) {
						// Not possible to obtain a non selected weakness.
						MessageManager.warningMessage(this.getClass().getName(), "No es posible obtener una debilidad de tipo '" + weaknessGrade.getTag()
								+ "'. Probablemente no quede ninguna disponible.", "Fallo en la obtención de una debilidad.");
						return null;
					}
				}
				return weakness;
			}

		}
		return null;
	}

	private void createSelectOptionsWindow() {
		// More than one category, select one of them.
		if (perk.getCategoriesToChoose().size() == 1 && perk.getCategoriesToChoose().get(0).getOptionsGroup().size() > 1) {
			for (ChooseCategoryGroup options : perk.getCategoriesToChoose()) {
				PerkOptionsWindow<Category> optionsWindow = new PerkOptionsWindow<Category>(character, perk, options, this);
				optionsWindow.setPointCounterLabel("Categorias con (" + getBonusTag() + "): ");
				optionsWindow.setVisible(true);
			}
		} else
		// One category, select skills.
		if (perk.getCategoriesToChoose().size() == 1) {
			ChooseCategoryGroup options = perk.getCategoriesToChoose().get(0);
			ChooseSkillGroup skillOptions = new ChooseSkillGroup(perk.getCategorySkillsRanksBonus(character.getCategory(options.getOptionsGroup().get(0))
					.getName()), character.getCategory(options.getOptionsGroup().get(0)).getSkills(), options.getChooseType());
			skillOptions.setNumberOfOptionsToChoose(options.getNumberOfOptionsToChoose());
			PerkOptionsWindow<Skill> optionsWindow = new PerkOptionsWindow<Skill>(character, perk, skillOptions, this);
			optionsWindow.setPointCounterLabel("Habilidades: ");
			optionsWindow.setVisible(true);
		} else
		// Select one skill from list.
		if (!perk.getSkillsToChoose().isEmpty()) {
			for (ChooseSkillGroup options : perk.getSkillsToChoose()) {
				PerkOptionsWindow<Skill> optionsWindow = new PerkOptionsWindow<Skill>(character, perk, options, this);
				optionsWindow.setPointCounterLabel("Habilidades con (" + getBonusTag() + "): ");
				optionsWindow.setVisible(true);
			}
		} else if (!perk.getCommonSkillsToChoose().isEmpty()) {
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
		setSelected(false);
	}

	protected void setSelected(boolean selected) {
		perkCheckBox.setSelected(selected);
	}

	protected void enabledCheckBox(boolean enabled) {
		perkCheckBox.setEnabled(enabled);
	}
}
