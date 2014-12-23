package com.softwaremagico.librodeesher.gui.magicitem;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import com.softwaremagico.librodeesher.gui.elements.BaseSpinner;
import com.softwaremagico.librodeesher.gui.elements.BaseTextField;
import com.softwaremagico.librodeesher.gui.elements.CategoryChangedListener;
import com.softwaremagico.librodeesher.gui.elements.CategoryComboBox;
import com.softwaremagico.librodeesher.gui.elements.SkillChangedListener;
import com.softwaremagico.librodeesher.gui.elements.SkillComboBox;
import com.softwaremagico.librodeesher.gui.elements.SpinnerValueChangedListener;
import com.softwaremagico.librodeesher.gui.style.BasePanel;
import com.softwaremagico.librodeesher.gui.style.Fonts;
import com.softwaremagico.librodeesher.pj.categories.Category;
import com.softwaremagico.librodeesher.pj.equipment.MagicObject;
import com.softwaremagico.librodeesher.pj.skills.Skill;

public class EditMagicItemPanel extends BasePanel {
	private static final long serialVersionUID = 689205483854072507L;
	private CategoryComboBox categoryComboBox;
	private SkillComboBox skillComboBox;
	private BaseSpinner categorySpinner, skillSpinner;
	private MagicObject magicObject;

	protected EditMagicItemPanel(MagicObject magicObject) {
		this.magicObject = magicObject;
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
		
		BaseTextField nameField = new BaseTextField();
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
		
		BaseTextField descriptionField = new BaseTextField();
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

	private BaseSpinner createCategorySpinner() {
		SpinnerModel spinnerModel = new SpinnerNumberModel(0, -999, +999, 5);
		BaseSpinner categorySpinner = new BaseSpinner(spinnerModel);
		categorySpinner.addSpinnerValueChangedListener(new SpinnerValueChangedListener() {
			@Override
			public void valueChanged(int value) {
				if (magicObject != null) {
					magicObject.setCategoryBonus(((Category) categoryComboBox.getSelectedItem()).getName(),
							value);
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
					magicObject.setSkillBonus(((Skill) skillComboBox.getSelectedItem()).getName(), value);
				}
			}
		});
		return skillSpinner;
	}

	@Override
	public void update() {
		skillComboBox.setEnabled(magicObject!=null);
		categoryComboBox.setEnabled(magicObject!=null);
		categorySpinner.setEnabled(magicObject!=null);
		skillSpinner.setEnabled(magicObject!=null);
	}

	public MagicObject getMagicObject() {
		return magicObject;
	}

	public void setMagicObject(MagicObject magicObject) {
		this.magicObject = magicObject;
	}
	
	

}
