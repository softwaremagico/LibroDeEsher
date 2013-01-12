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

import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultFormatter;

import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.Language;

public class LanguageLine extends CultureLine {

	private static final long serialVersionUID = 2401612544094265349L;
	protected Language language;
	private Integer initalValue;

	public LanguageLine(CharacterPlayer character, Language language, CulturePanel languagePanel,
			Color background) {
		this.character = character;
		this.skillName = language.getName();
		this.parentPanel = languagePanel;
		this.language = language;
		setElements(background);
		setBackground(background);
		initalValue = character.getLanguageInitialRanks(language);
		SpinnerModel sm = new SpinnerNumberModel((int) initalValue, (int) initalValue,
				(int) character.getLanguageMaxInitialRanks(language), 1);
		rankSpinner.setModel(sm);

		// Languages can have 10 ranks. We need a bigger editor.
		rankSpinner.setColumns(2);
	}

	protected void addRankSpinnerEvent() {
		JComponent comp = rankSpinner.getEditor();
		JFormattedTextField field = (JFormattedTextField) comp.getComponent(0);
		DefaultFormatter formatter = (DefaultFormatter) field.getFormatter();
		formatter.setCommitsOnValidEdit(true);
		rankSpinner.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				// Max language points limit.
				if (parentPanel.getSpinnerValues() > character.getRace().getLanguagePoints()) {
					rankSpinner.setValue((Integer) rankSpinner.getValue() - 1);
				} else if ((Integer) rankSpinner.getValue() > character.getRace().getLanguageMaxRanks(
						language)) { // Race language limit.
					rankSpinner.setValue((Integer) rankSpinner.getValue() - 1);
				} else {
					// Update character
					character.getRaceDecisions().setLanguageRank(language, (Integer) rankSpinner.getValue());
					parentPanel.setRankTitle("Rangos ("
							+ (character.getRace().getLanguagePoints() - parentPanel.getSpinnerValues())
							+ ")");
				}
			}
		});
	}

	@Override
	protected Integer getSelectedRanks() {
		return (Integer) rankSpinner.getValue() - initalValue;
	}
}
