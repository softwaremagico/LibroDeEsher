package com.softwaremagico.librodeesher.gui.magicitem;

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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import com.softwaremagico.librodeesher.gui.elements.BaseSpinner;
import com.softwaremagico.librodeesher.gui.elements.BaseTextField;
import com.softwaremagico.librodeesher.gui.elements.CategoryChangedListener;
import com.softwaremagico.librodeesher.gui.elements.CategoryComboBox;
import com.softwaremagico.librodeesher.gui.elements.SkillChangedListener;
import com.softwaremagico.librodeesher.gui.elements.SkillComboBox;
import com.softwaremagico.librodeesher.gui.elements.SpinnerValueChangedListener;
import com.softwaremagico.librodeesher.gui.magicitem.OtherBonusComboBox.OthersChangedListener;
import com.softwaremagico.librodeesher.gui.style.BasePanel;
import com.softwaremagico.librodeesher.gui.style.Fonts;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.categories.Category;
import com.softwaremagico.librodeesher.pj.equipment.MagicObject;
import com.softwaremagico.librodeesher.pj.equipment.BonusType;
import com.softwaremagico.librodeesher.pj.skills.Skill;

public class EditMagicItemPanel extends BasePanel {
	private static final long serialVersionUID = 689205483854072507L;
	private CharacterPlayer character;
	private CategoryComboBox categoryComboBox;
	private SkillComboBox skillComboBox;
	private BaseSpinner categorySpinner, skillSpinner, othersSpinner;
	private BaseTextField descriptionField, nameField;
	private MagicObject magicObject;
	private List<MagicObjectNameUpdated> nameListeners;
	private OtherBonusComboBox othersComboBox;
	private ResumeMagicObjectPanel magicObjectResume;

	public interface MagicObjectNameUpdated {
		void updatedName(MagicObject magicObject);
	}

	protected EditMagicItemPanel(CharacterPlayer character, MagicObject magicObject) {
		this.character = character;
		this.magicObject = magicObject;
		nameListeners = new ArrayList<>();
		setElements();
		update();
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

		JLabel nameLabel = new JLabel("Nombre:");
		nameLabel.setFont(Fonts.getInstance().getBoldFont());
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 3;
		add(nameLabel, constraints);

		nameField = new BaseTextField();
		nameField.setHorizontalAlignment(JTextField.LEFT);
		nameField.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent arg0) {
				if (magicObject != null) {
					magicObject.setName(nameField.getText());
					updateNameListeners();
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				if (magicObject != null) {
					magicObject.setName(nameField.getText());
					updateNameListeners();
				}
			}

			@Override
			public void keyPressed(KeyEvent arg0) {
				if (magicObject != null) {
					magicObject.setName(nameField.getText());
					updateNameListeners();
				}
			}
		});
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		add(nameField, constraints);

		JLabel descriptonLabel = new JLabel("Descripción:");
		descriptonLabel.setFont(Fonts.getInstance().getBoldFont());
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridwidth = 3;
		add(descriptonLabel, constraints);

		descriptionField = new BaseTextField();
		descriptionField.setHorizontalAlignment(JTextField.LEFT);
		descriptionField.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent arg0) {
				if (magicObject != null) {
					magicObject.setDescription(descriptionField.getText());
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				if (magicObject != null) {
					magicObject.setDescription(descriptionField.getText());
				}
			}

			@Override
			public void keyPressed(KeyEvent arg0) {
				if (magicObject != null) {
					magicObject.setDescription(descriptionField.getText());
				}
			}
		});
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		add(descriptionField, constraints);

		JLabel titleLabel = new JLabel("Bonus del objeto:");
		titleLabel.setFont(Fonts.getInstance().getBoldFont());
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 4;
		constraints.gridwidth = 3;
		add(titleLabel, constraints);

		JLabel categoryLabel = new JLabel("Categoría:");
		constraints.fill = GridBagConstraints.NONE;
		constraints.gridx = 0;
		constraints.gridy = 5;
		constraints.gridwidth = 1;
		add(categoryLabel, constraints);

		categoryComboBox = createCategoryComboBox();
		categorySpinner = createCategorySpinner();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 1;
		constraints.gridy = 5;
		constraints.weightx = 1;
		constraints.gridwidth = 3;
		add(categoryComboBox, constraints);

		constraints.gridx = 4;
		constraints.gridy = 5;
		constraints.gridwidth = 1;
		constraints.weightx = 0;
		add(categorySpinner, constraints);

		JLabel skillLabel = new JLabel("Habilidad:");
		constraints.fill = GridBagConstraints.NONE;
		constraints.gridx = 0;
		constraints.gridy = 6;
		constraints.gridwidth = 1;
		add(skillLabel, constraints);

		skillComboBox = createSkillComboBox();
		skillSpinner = createSkillSpinner();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 1;
		constraints.gridy = 6;
		constraints.gridwidth = 3;
		constraints.weightx = 1;
		add(skillComboBox, constraints);
		if (categorySpinner.getValue() != null) {
			skillComboBox.setSkills((Category) categoryComboBox.getSelectedItem());
		}

		constraints.gridx = 4;
		constraints.gridy = 6;
		constraints.gridwidth = 1;
		constraints.weightx = 0;
		add(skillSpinner, constraints);

		JLabel othersLabel = new JLabel("Otros:");
		constraints.fill = GridBagConstraints.NONE;
		constraints.gridx = 0;
		constraints.gridy = 7;
		constraints.gridwidth = 1;
		add(othersLabel, constraints);

		othersComboBox = createOtherBonusComboBox();
		othersSpinner = createOtherSpinner();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 1;
		constraints.gridy = 7;
		constraints.gridwidth = 3;
		constraints.weightx = 1;
		add(othersComboBox, constraints);

		constraints.gridx = 4;
		constraints.gridy = 7;
		constraints.gridwidth = 1;
		constraints.weightx = 0;
		add(othersSpinner, constraints);

		magicObjectResume = new ResumeMagicObjectPanel();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.LINE_START;
		constraints.gridx = 0;
		constraints.gridy = 8;
		constraints.gridwidth = 5;
		constraints.gridheight = 3;
		constraints.weightx = 1;
		constraints.weighty = 1;
		add(magicObjectResume, constraints);
	}

	private CategoryComboBox createCategoryComboBox() {
		CategoryComboBox categoryComboBox = new CategoryComboBox();
		categoryComboBox.setNormalStyle();
		categoryComboBox.addCategoryChangedListener(new CategoryChangedListener() {
			@Override
			public void categoryChanged(Category category) {
				if (magicObject != null) {
					categorySpinner.setValue(magicObject.getCategoryBonus(category.getName()));
					skillComboBox.setSkills(category);
				}
			}
		});
		return categoryComboBox;
	}

	private SkillComboBox createSkillComboBox() {
		SkillComboBox skillComboBox = new SkillComboBox();
		skillComboBox.setNormalStyle();
		skillComboBox.addSkillChangedListener(new SkillChangedListener() {
			@Override
			public void skillChanged(Skill skill) {
				if (magicObject != null) {
					if (skill != null) {
						skillSpinner.setValue(magicObject.getSkillBonus(skill.getName()));
						skillSpinner.setEnabled(true);
					} else {
						skillSpinner.setValue(0);
						skillSpinner.setEnabled(false);
					}
				}
			}
		});
		return skillComboBox;
	}

	private OtherBonusComboBox createOtherBonusComboBox() {
		OtherBonusComboBox comboBox = new OtherBonusComboBox();
		comboBox.setNormalStyle();
		comboBox.addOthersChangedListener(new OthersChangedListener() {

			@Override
			public void otherChanged(BonusType type) {
				if (magicObject != null) {
					if (type != null) {
						othersSpinner.setValue(magicObject.getObjectBonus(type));
						othersSpinner.setEnabled(true);
					} else {
						othersSpinner.setValue(0);
						othersSpinner.setEnabled(false);
					}
				}
			}
		});
		return comboBox;
	}

	private BaseSpinner createCategorySpinner() {
		SpinnerModel spinnerModel = new SpinnerNumberModel(0, -999, +999, 5);
		BaseSpinner categorySpinner = new BaseSpinner(spinnerModel);
		categorySpinner.addSpinnerValueChangedListener(new SpinnerValueChangedListener() {
			@Override
			public void valueChanged(int value) {
				if (magicObject != null) {
					if (categoryComboBox.getSelectedItem() != null) {
						magicObject.setCategoryBonus(
								((Category) categoryComboBox.getSelectedItem()).getName(), value);
						character.updateMagicItemHelper(magicObject);
					}
					magicObjectResume.update(magicObject);
				}
			}
		});
		return categorySpinner;
	}

	private BaseSpinner createSkillSpinner() {
		SpinnerModel spinnerModel = new SpinnerNumberModel(0, -999, +999, 5);
		BaseSpinner skillSpinner = new BaseSpinner(spinnerModel);
		skillSpinner.addSpinnerValueChangedListener(new SpinnerValueChangedListener() {
			@Override
			public void valueChanged(int value) {
				if (magicObject != null) {
					if (skillComboBox.getSelectedItem() != null) {
						magicObject.setSkillBonus(((Skill) skillComboBox.getSelectedItem()).getName(), value);
						character.updateMagicItemHelper(magicObject);
					}
					magicObjectResume.update(magicObject);
				}
			}
		});
		return skillSpinner;
	}

	private BaseSpinner createOtherSpinner() {
		SpinnerModel spinnerModel = new SpinnerNumberModel(0, -999, +999, 5);
		BaseSpinner otherSpinner = new BaseSpinner(spinnerModel);
		otherSpinner.addSpinnerValueChangedListener(new SpinnerValueChangedListener() {
			@Override
			public void valueChanged(int value) {
				if (magicObject != null) {
					if (othersComboBox.getSelectedItem() != null) {
						magicObject.setObjectBonus(((BonusType) (othersComboBox.getSelectedItem())).toString(),
								(BonusType) (othersComboBox.getSelectedItem()), value);
						character.updateMagicItemHelper(magicObject);
					}
					magicObjectResume.update(magicObject);
				}
			}
		});
		return otherSpinner;
	}

	@Override
	public void update() {
		skillComboBox.setEnabled(magicObject != null);
		categoryComboBox.setEnabled(magicObject != null);
		categorySpinner.setEnabled(magicObject != null);
		skillSpinner.setEnabled(magicObject != null);
		othersComboBox.setEnabled(magicObject != null);
		othersSpinner.setEnabled(magicObject != null);
	}

	public MagicObject getMagicObject() {
		return magicObject;
	}

	public void setMagicObject(MagicObject magicObject) {
		this.magicObject = magicObject;
		if (magicObject != null) {
			nameField.setText(magicObject.getName());
			descriptionField.setText(magicObject.getDescription());
			skillSpinner.setValue(magicObject.getSkillBonus(skillComboBox.getSelectedItem().toString()));
			categorySpinner.setValue(magicObject.getCategoryBonus(categoryComboBox.getSelectedItem()
					.toString()));
		} else {
			nameField.setText("");
			descriptionField.setText("");
			skillSpinner.setValue(0);
			categorySpinner.setValue(0);
		}
		magicObjectResume.update(magicObject);
		update();
	}

	public void addNameUpdateListener(MagicObjectNameUpdated listener) {
		nameListeners.add(listener);
	}

	private void updateNameListeners() {
		for (MagicObjectNameUpdated listener : nameListeners) {
			listener.updatedName(magicObject);
		}
	}

}
