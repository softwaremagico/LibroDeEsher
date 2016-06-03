package com.softwaremagico.librodeesher.gui.history;

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

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.softwaremagico.librodeesher.gui.style.BasePanel;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;

public class HistoryLanguagePanel extends BasePanel {
	private static final long serialVersionUID = -9203104559414795802L;
	private CharacterPlayer character;
	private HistoryLanguageCompletePanel parent;
	protected List<HistoryLanguageLine> languageLines = new ArrayList<>();

	public HistoryLanguagePanel(CharacterPlayer character, HistoryLanguageCompletePanel parent) {
		this.character = character;
		this.parent = parent;
		setElements();
	}

	protected void setElements() {
		this.removeAll();
		setLayout(new GridLayout(0, 1));
		int i = 0;
		// Add race languages.
		List<String> languages = new ArrayList<>();
		for (String language : character.getMaxHistoryLanguages().keySet()) {
			if (!languages.contains(language)) {
				languages.add(language);
			}
		}		
		Collections.sort(languages);
		for (String language : languages) {
			HistoryLanguageLine languageLine = new HistoryLanguageLine(character, language, this, getLineBackgroundColor(i));
			add(languageLine);
			languageLines.add(languageLine);
			i++;
		}
	}

	@Override
	public void update() {
		parent.update();
	}

	public void setTitle(int ranks) {
		parent.setTitleRanks(ranks);
	}
}
