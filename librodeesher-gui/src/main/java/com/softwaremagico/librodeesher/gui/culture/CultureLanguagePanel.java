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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.softwaremagico.librodeesher.basics.Spanish;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;

public class CultureLanguagePanel extends CulturePanel {
	private static final long serialVersionUID = -9203104559414795802L;

	public CultureLanguagePanel(CharacterPlayer character, CultureTitleLine title) {
		this.character = character;
		this.title = title;
		setElements(character);
	}

	@Override
	protected void createElements() {
		int i = 0;
		// Add race languages.
		List<String> languages = new ArrayList<>();
		for (String language : character.getRace().getInitialRaceLanguages().keySet()) {
			if (language != null && !languages.contains(language)) {
				languages.add(language);
			}
		}
		for (String language : character.getCultureDecisions().getOptionalRaceLanguages()) {
			if (language != null && !languages.contains(Spanish.SPOKEN_TAG + " " + language)) {
				languages.add(Spanish.SPOKEN_TAG + " " + language);
			}
			if (language != null && !languages.contains(Spanish.WRITTEN_TAG + " " + language)) {
				languages.add(Spanish.WRITTEN_TAG + " " + language);
			}
		}
		// Add culture languages.
		for (String language : character.getCulture().getLanguagesMaxRanks()) {
			if (language != null && !languages.contains(language)) {
				languages.add(language);
			}
		}
		for (String language : character.getCultureDecisions().getOptionalCulturalLanguages()) {
			if (language != null && !languages.contains(Spanish.SPOKEN_TAG + " " + language)) {
				languages.add(Spanish.SPOKEN_TAG + " " + language);
			}
			if (language != null && !languages.contains(Spanish.WRITTEN_TAG + " " + language)) {
				languages.add(Spanish.WRITTEN_TAG + " " + language);
			}
		}
		Collections.sort(languages);
		for (String language : languages) {
			CultureLanguageLine languageLine = new CultureLanguageLine(character, language, this,
					getLineBackgroundColor(i));
			add(languageLine);
			hobbyLines.add(languageLine);
			i++;
		}
	}

	@Override
	protected void setRankTitle(String rankLabelText) {
		title.setRankTitle(rankLabelText);
	}

}
