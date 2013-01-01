package com.softwaremagico.librodeesher.gui.culture;
/*
 * #%L
 * Libro de Esher
 * %%
 * Copyright (C) 2007 - 2013 Softwaremagico
 * %%
 * This software is designed by Jorge Hortelano Otero. Jorge Hortelano Otero
 * <softwaremagico@gmail.com> C/Quart 89, 3. Valencia CP:46008 (Spain).
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
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultFormatter;

import com.softwaremagico.librodeesher.gui.style.BasicLine;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.categories.Category;
import com.softwaremagico.librodeesher.pj.skills.Skill;

public class WeaponSkillLine extends BasicLine {
	private static final long serialVersionUID = -4697558156145520144L;
	private Skill weaponSkill;
	private JSpinner rankSpinner;
	private Category category;
	private ChooseWeaponPanel parentPanel;
	private CharacterPlayer character;

	public WeaponSkillLine(CharacterPlayer character, Category category, ChooseWeaponPanel parentPanel,
			Skill weaponSkill, Color background) {
		this.character = character;
		this.parentPanel = parentPanel;
		this.category = category;
		this.weaponSkill = weaponSkill;
		setElements(background);
		setBackground(background);
		rankSpinner.setValue(character.getCultureDecisions().getWeaponRanks(weaponSkill.getName()));
	}

	protected void setDefaultSize() {

	}

	protected void setElements(Color background) {
		this.removeAll();
		setLayout(new GridBagLayout());
		GridBagConstraints gridBagConstraints = new GridBagConstraints();

		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 0;

		JLabel weaponSkillLabel = new JLabel(weaponSkill.getName());
		weaponSkillLabel.setFont(new Font(font, Font.PLAIN, fontSize));
		add(createLabelInsidePanel(weaponSkillLabel, SwingConstants.LEFT, background, fontColor),
				gridBagConstraints);

		SpinnerModel sm = new SpinnerNumberModel(0, 0, 3, 1);
		rankSpinner = new JSpinner(sm);
		rankSpinner.setValue(0);
		addRankSpinnerEvent();
		gridBagConstraints.weightx = 0;
		gridBagConstraints.gridx = 1;
		add(createSpinnerInsidePanel(rankSpinner, background), gridBagConstraints);

	}

	protected Integer getSelectedRanks() {
		return (Integer) rankSpinner.getValue();
	}

	private void addRankSpinnerEvent() {
		JComponent comp = rankSpinner.getEditor();
		JFormattedTextField field = (JFormattedTextField) comp.getComponent(0);
		DefaultFormatter formatter = (DefaultFormatter) field.getFormatter();
		formatter.setCommitsOnValidEdit(true);
		rankSpinner.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				// Correct the spinner
				if (parentPanel.getSpinnerValues(category) > character.getCulture().getCultureRanks(
						category)) {
					rankSpinner.setValue((Integer) rankSpinner.getValue() - 1);
				} else {
					//Update character
					character.getCultureDecisions().setWeaponRanks(weaponSkill.getName(), (Integer) rankSpinner.getValue());
				}
			}
		});
	}
}
