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
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultFormatter;

import com.softwaremagico.librodeesher.pj.CharacterPlayer;

public class CultureLanguageLine extends CultureLine {

	private static final long serialVersionUID = 2401612544094265349L;
	protected String language;
	private Integer initalValue;

	public CultureLanguageLine(CharacterPlayer characterPlayer, String language, CulturePanel languagePanel, Color background) {
		this.characterPlayer = characterPlayer;
		this.skillName = language;
		this.parentPanel = languagePanel;
		this.language = language;
		setElements(background);
		setBackground(background);
		initalValue = characterPlayer.getLanguageRaceInitialRanks(language);
		SpinnerModel sm = new SpinnerNumberModel((int) initalValue + characterPlayer.getCultureLanguageRanks(language), (int) initalValue,
				(int) characterPlayer.getLanguageMaxInitialRanks(language), 1);
		rankSpinner.setModel(sm);

		// Languages can have 10 ranks. We need a bigger editor.
		rankSpinner.setColumns(2);

		// Initialize the title.
		setTitle();
	}

	protected void addRankSpinnerEvent() {
		JComponent comp = rankSpinner.getEditor();
		JFormattedTextField field = (JFormattedTextField) comp.getComponent(0);
		DefaultFormatter formatter = (DefaultFormatter) field.getFormatter();
		formatter.setCommitsOnValidEdit(true);
		rankSpinner.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				// Update character
				characterPlayer.setCultureLanguageRanks(language, (Integer) rankSpinner.getValue() - characterPlayer.getLanguageRaceInitialRanks(language));
				// Max language points limit.
				if (characterPlayer.getCultureTotalLanguageRanks() > characterPlayer.getRace().getLanguagePoints()
						+ characterPlayer.getCulture().getLanguageRanksToChoose()) {
					rankSpinner.setValue((Integer) rankSpinner.getValue() - 1);
					// Race and culture language limit.
				} else if ((Integer) rankSpinner.getValue() > characterPlayer.getLanguageMaxInitialRanks(language)) {
					rankSpinner.setValue((Integer) rankSpinner.getValue() - 1);
				} else {
					setTitle();
				}
			}
		});
	}

	private void setTitle() {
		parentPanel.setRankTitle("Rangos ("
				+ (characterPlayer.getRace().getLanguagePoints() + characterPlayer.getCulture().getLanguageRanksToChoose() - characterPlayer
						.getCultureTotalLanguageRanks()) + ")");
	}

	@Override
	protected Integer getSelectedRanks() {
		return (Integer) rankSpinner.getValue() - initalValue;
	}
}
