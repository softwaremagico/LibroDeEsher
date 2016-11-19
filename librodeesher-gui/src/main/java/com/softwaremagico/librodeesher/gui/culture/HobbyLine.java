package com.softwaremagico.librodeesher.gui.culture;

/*
 * #%L
 * Libro de Esher
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

import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultFormatter;

import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.skills.Skill;
import com.softwaremagico.librodeesher.pj.skills.SkillFactory;

public class HobbyLine extends CultureLine {

	private static final int MAX_HOBBY_COST = 40;
	private static final long serialVersionUID = 2401612544094265349L;
	private Skill skill;

	public HobbyLine(CharacterPlayer character, String hobby, CulturePanel hobbyPanel, Color background) {
		this.characterPlayer = character;
		this.skillName = hobby;
		skill = SkillFactory.getAvailableSkill(skillName);
		this.parentPanel = hobbyPanel;
		setElements(background);
		setBackground(background);
		rankSpinner.setValue(character.getCultureHobbyRanks(hobby));
	}

	protected void addRankSpinnerEvent() {
		JComponent comp = rankSpinner.getEditor();
		JFormattedTextField field = (JFormattedTextField) comp.getComponent(0);
		DefaultFormatter formatter = (DefaultFormatter) field.getFormatter();
		formatter.setCommitsOnValidEdit(true);
		rankSpinner.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				// Correct the spinner
				System.out.println(skill + ": " + parentPanel.getSpinnerValues() + ">"
						+ characterPlayer.getCulture().getHobbyRanks() + " "
						+ ((Integer) rankSpinner.getValue()));
				if (parentPanel.getSpinnerValues() > characterPlayer.getCulture().getHobbyRanks()
						&& (Integer) rankSpinner.getValue() > 0) {
					rankSpinner.setValue((Integer) rankSpinner.getValue() - 1);
				} else if (getSelectedRanks() > characterPlayer.getMaxRanksPerCulture(skill.getCategory())) {
					rankSpinner.setValue((Integer) rankSpinner.getValue() - 1);
					// Cost greater than 40 can not be a hobby
				} else if (characterPlayer.getCultureStimatedCategoryCost(skill.getCategory()) > MAX_HOBBY_COST
						&& ((Integer) rankSpinner.getValue()) > 0) {
					rankSpinner.setValue((Integer) rankSpinner.getValue() - 1);
				} else {
					// Update character
					characterPlayer.setCultureHobbyRanks(skillName, (Integer) rankSpinner.getValue());
				}
				parentPanel.setRankTitle("Rangos ("
						+ (characterPlayer.getCulture().getHobbyRanks() - characterPlayer
								.getCultureTotalHobbyRanks()) + ")");
			}
		});
	}
}
