package com.softwaremagico.librodeesher.gui.culture;

/*
 * #%L
 * Libro de Esher (GUI)
 * %%
 * Copyright (C) 2007 - 2016 Softwaremagico
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
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JPanel;

import com.softwaremagico.librodeesher.basics.ChooseGroup;
import com.softwaremagico.librodeesher.basics.ChooseType;
import com.softwaremagico.librodeesher.basics.Spanish;
import com.softwaremagico.librodeesher.gui.elements.CloseButton;
import com.softwaremagico.librodeesher.gui.options.SelectOption;
import com.softwaremagico.librodeesher.gui.style.BaseFrame;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.skills.ChooseSkillName;

public class SelectLanguagesWindow extends BaseFrame {
	private static final long serialVersionUID = 4191435751876725272L;
	private CharacterPlayer characterPlayer;
	private final List<SelectOption<String>> raceLanguageOptions;
	private SelectOption<String> raceLayout;
	private final List<SelectOption<String>> cultureLanguageOptions;
	private SelectOption<String> cultureLayout;
	private final Set<LanguageSelectionUpdateListener> updateListeners;

	public interface LanguageSelectionUpdateListener {
		void updated();
	}

	public SelectLanguagesWindow(CharacterPlayer characterPlayer) {
		this.characterPlayer = characterPlayer;
		updateListeners = new HashSet<>();
		raceLanguageOptions = new ArrayList<>();
		cultureLanguageOptions = new ArrayList<>();
		defineSize();
		this.setAlwaysOnTop(true);
	}

	private void defineSize() {
		int widthCells = (characterPlayer.getRace().getOptionalLanguages().isEmpty() ? 0 : 1)
				+ (characterPlayer.getCulture().getOptionalLanguages().isEmpty() ? 0 : 1);
		defineWindow(250 * widthCells, 500);
		setElements();
	}

	private void setElements() {
		setLayout(new GridBagLayout());
		GridBagConstraints gridBagConstraints = new GridBagConstraints();

		// gridBagConstraints.anchor = GridBagConstraints.LINE_END;
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.insets = new Insets(2, 2, 2, 2);

		// Race language
		int row = 0;
		for (int languageIndex = 0; languageIndex < characterPlayer.getRace().getOptionalLanguages().size(); languageIndex++) {
			ChooseGroup<String> chooseLanguageByRaceGroup = new ChooseSkillName(1,
					characterPlayer.getUnusedLanguages(), ChooseType.ENABLED);
			raceLayout = new SelectOption<String>(this, chooseLanguageByRaceGroup);
			raceLanguageOptions.add(raceLayout);
			row++;

			gridBagConstraints.gridy = languageIndex;
			raceLayout.setPointCounterLabel("   Idioma '"
					+ characterPlayer.getRace().getOptionalLanguages().get(languageIndex).getInitialSpeakingRanks()
					+ "/"
					+ characterPlayer.getRace().getOptionalLanguages().get(languageIndex).getInitialWrittingRanks()
					+ "' - '"
					+ characterPlayer.getRace().getOptionalLanguages().get(languageIndex).getMaxSpeakingRanks() + "/"
					+ characterPlayer.getRace().getOptionalLanguages().get(languageIndex).getMaxWritingRanks()
					+ "' por Raza");
			getContentPane().add(raceLayout, gridBagConstraints);
		}

		gridBagConstraints.gridx = 1;
		for (int languageIndex = 0; languageIndex < characterPlayer.getCulture().getOptionalLanguages().size(); languageIndex++) {
			// Culture language
			row++;
			ChooseGroup<String> chooseLanguageByCultureGroup = new ChooseSkillName(1,
					characterPlayer.getUnusedLanguages(), ChooseType.ENABLED);
			cultureLayout = new SelectOption<String>(this, chooseLanguageByCultureGroup);
			cultureLanguageOptions.add(cultureLayout);

			gridBagConstraints.gridy = languageIndex;

			cultureLayout.setPointCounterLabel("   Idioma '"
					+ characterPlayer.getCulture().getOptionalLanguages().get(languageIndex).getMaxSpeakingRanks()
					+ "/" + characterPlayer.getCulture().getOptionalLanguages().get(languageIndex).getMaxWritingRanks()
					+ "' por Cultura");
			getContentPane().add(cultureLayout, gridBagConstraints);
		}

		JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
		CloseButton closeButton = new CloseButton(this);
		closeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (LanguageSelectionUpdateListener listener : updateListeners) {
					listener.updated();
				}
			}
		});
		buttonPanel.add(closeButton);
		gridBagConstraints.anchor = GridBagConstraints.LINE_END;
		gridBagConstraints.fill = GridBagConstraints.NONE;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = row;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 0;
		gridBagConstraints.insets = new Insets(2, 2, 2, 2);
		getContentPane().add(buttonPanel, gridBagConstraints);
	}

	@Override
	public void updateFrame() {
		// Remove any selected rank in optional languages.
		for (String language : characterPlayer.getCultureDecisions().getLanguageRanks().keySet()) {
			if (characterPlayer.getCultureDecisions().getOptionalCulturalLanguages().contains(language)) {
				characterPlayer.getCultureDecisions().setLanguageRank(language, 0);
			}
			if (characterPlayer.getCultureDecisions().getOptionalRaceLanguages().contains(language)) {
				characterPlayer.getCultureDecisions().setLanguageRank(language, 0);
			}
		}
		// Remove any language.
		characterPlayer.getCultureDecisions().resetLanguageOptions();
		// Race languages.
		if (raceLayout != null) {
			List<String> selectedLanguages = new ArrayList<>();
			for (int i = 0; i < raceLanguageOptions.size(); i++) {
				// Max race ranks
				characterPlayer.getCultureDecisions().addOptionalRaceInitialLanguageSelection(
						Spanish.SPOKEN_TAG + " " + selectedLanguages.get(i),
						characterPlayer.getRace().getOptionalLanguages().get(i).getInitialSpeakingRanks());
				characterPlayer.getCultureDecisions().addOptionalRaceInitialLanguageSelection(
						Spanish.WRITTEN_TAG + " " + selectedLanguages.get(i),
						characterPlayer.getRace().getOptionalLanguages().get(i).getInitialWrittingRanks());
				characterPlayer.getCultureDecisions().addOptionalRaceMaxLanguageSelection(
						Spanish.SPOKEN_TAG + " " + selectedLanguages.get(i),
						characterPlayer.getRace().getOptionalLanguages().get(i).getMaxSpeakingRanks());
				characterPlayer.getCultureDecisions().addOptionalRaceMaxLanguageSelection(
						Spanish.WRITTEN_TAG + " " + selectedLanguages.get(i),
						characterPlayer.getRace().getOptionalLanguages().get(i).getMaxWritingRanks());
			}
		}
		// Culture languages.
		if (cultureLayout != null) {
			List<String> selectedLanguages = new ArrayList<>();
			for (int i = 0; i < cultureLanguageOptions.size(); i++) {
				// Max race ranks
				characterPlayer.getCultureDecisions().addOptionalCulturalLanguageSelection(
						Spanish.SPOKEN_TAG + " " + selectedLanguages.get(i),
						characterPlayer.getRace().getOptionalLanguages().get(i).getMaxSpeakingRanks());
				characterPlayer.getCultureDecisions().addOptionalCulturalLanguageSelection(
						Spanish.WRITTEN_TAG + " " + selectedLanguages.get(i),
						characterPlayer.getRace().getOptionalLanguages().get(i).getMaxWritingRanks());
			}
		}
	}

	public void addLanguageSelectionUpdateListener(LanguageSelectionUpdateListener listener) {
		updateListeners.add(listener);
	}

}
