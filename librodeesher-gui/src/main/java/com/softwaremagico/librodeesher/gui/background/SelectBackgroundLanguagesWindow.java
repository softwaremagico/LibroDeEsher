package com.softwaremagico.librodeesher.gui.background;

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

import java.awt.Dimension;
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
import javax.swing.JScrollPane;

import com.softwaremagico.files.MessageManager;
import com.softwaremagico.librodeesher.basics.ChooseGroup;
import com.softwaremagico.librodeesher.basics.ChooseType;
import com.softwaremagico.librodeesher.gui.options.SelectOption;
import com.softwaremagico.librodeesher.gui.style.BaseButton;
import com.softwaremagico.librodeesher.gui.style.BaseFrame;
import com.softwaremagico.librodeesher.gui.style.BasePanel;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.skills.ChooseSkillName;

public class SelectBackgroundLanguagesWindow extends BaseFrame {
	private static final long serialVersionUID = -8825802390489101493L;
	private static final int LANGUAGE_PANEL_WIDTH = 300;
	private static final int LANGUAGE_PANEL_HEIGHT = 500;
	private CharacterPlayer characterPlayer;
	private final List<SelectOption<String>> raceLanguageOptions;
	private SelectOption<String> raceLayout;
	private final Set<LanguageSelectionUpdateListener> updateListeners;

	public interface LanguageSelectionUpdateListener {
		void updated();
	}

	public SelectBackgroundLanguagesWindow(CharacterPlayer characterPlayer) {
		this.characterPlayer = characterPlayer;
		updateListeners = new HashSet<>();
		raceLanguageOptions = new ArrayList<>();
		defineSize();
		this.setAlwaysOnTop(true);
	}

	private void defineSize() {
		defineWindow(LANGUAGE_PANEL_WIDTH + 10, 500);
		setMaximumSize(new Dimension(LANGUAGE_PANEL_WIDTH + 60, 500));
		setElements();
	}

	private void setElements() {
		BasePanel rootPanel = new BasePanel() {
			private static final long serialVersionUID = 3747845070335243598L;

			@Override
			public void update() {

			}
		};
		rootPanel.setPreferredSize(new Dimension(LANGUAGE_PANEL_WIDTH, LANGUAGE_PANEL_HEIGHT * 2));
		rootPanel.setLayout(new GridBagLayout());
		GridBagConstraints gridBagConstraints = new GridBagConstraints();

		// gridBagConstraints.anchor = GridBagConstraints.LINE_END;
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.insets = new Insets(2, 2, 2, 10);

		// Race language
		List<String> selectedLanguages = new ArrayList<>(characterPlayer.getBackground()
				.getOptionalRaceLanguageSelection());
		for (int languageIndex = 0; languageIndex < characterPlayer.getRace().getOptionalBackgroundLanguages().size(); languageIndex++) {
			ChooseGroup<String> chooseLanguageByRaceGroup = new ChooseSkillName(1,
					characterPlayer.getUnusedLanguages(), ChooseType.ENABLED);
			raceLayout = new SelectOption<String>(this, chooseLanguageByRaceGroup);
			raceLanguageOptions.add(raceLayout);

			gridBagConstraints.gridy = languageIndex;
			raceLayout.setPointCounterLabel("   Idioma '"
					+ characterPlayer.getRace().getOptionalBackgroundLanguages().get(languageIndex)
							.getMaxSpeakingRanks()
					+ "/"
					+ characterPlayer.getRace().getOptionalBackgroundLanguages().get(languageIndex)
							.getMaxWritingRanks() + "' por Raza");
			raceLayout.setPreferredSize(new Dimension(LANGUAGE_PANEL_WIDTH, LANGUAGE_PANEL_HEIGHT));
			raceLayout.setOptionsCountVisible(false);
			rootPanel.add(raceLayout, gridBagConstraints);

			// Select previously selected.
			if (languageIndex < selectedLanguages.size()) {
				raceLayout.select(selectedLanguages.get(languageIndex));
			}
		}

		JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
		BaseButton closeButton = new BaseButton("Aceptar");
		closeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (validateValues()) {
					updateFrame();
					for (LanguageSelectionUpdateListener listener : updateListeners) {
						listener.updated();
					}
					dispose();
				}
			}
		});
		buttonPanel.add(closeButton);

		setLayout(new GridBagLayout());
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		gridBagConstraints.insets = new Insets(2, 2, 2, 2);

		JScrollPane scrollPane = new JScrollPane(rootPanel);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setPreferredSize(new Dimension(LANGUAGE_PANEL_WIDTH, LANGUAGE_PANEL_HEIGHT * 5));
		getContentPane().add(scrollPane, gridBagConstraints);
		gridBagConstraints.fill = GridBagConstraints.NONE;
		gridBagConstraints.anchor = GridBagConstraints.LINE_END;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.weighty = 0;
		getContentPane().add(buttonPanel, gridBagConstraints);
	}

	private boolean validateValues() {
		// Check values.
		if (characterPlayer.getBackground().getOptionalRaceLanguageSelection().size() != characterPlayer.getRace()
				.getOptionalBackgroundLanguages().size()) {
			MessageManager.basicErrorMessage(this.getClass().getName(),
					"Debes seleccionar todos los idiomas de raza y estos no pueden estar repetidos.", "Error");
			return false;
		}
		return true;
	}

	@Override
	public void updateFrame() {
		// Remove any selected rank in optional languages.
		for (String language : new HashSet<String>(characterPlayer.getBackground().getLanguageRanks().keySet())) {
			// Old language not selected now, remove any added rank.
			characterPlayer.getBackground().setHistoryLanguageRank(language, 0);
		}
		// Remove any selected language.
		characterPlayer.getBackground().resetLanguageOptions();
		// Race languages.
		if (raceLayout != null) {
			for (int i = 0; i < raceLanguageOptions.size(); i++) {
				// Max race ranks
				if (!raceLanguageOptions.get(i).getSelectedOptions().isEmpty()) {
					characterPlayer.getBackground().getOptionalRaceLanguageSelection()
							.add(raceLanguageOptions.get(i).getSelectedOptions().get(0));
				} else {
					characterPlayer.getBackground().getOptionalRaceLanguageSelection().add(null);
				}
			}
		}
		characterPlayer.clearCache();
	}

	public void addLanguageSelectionUpdateListener(LanguageSelectionUpdateListener listener) {
		updateListeners.add(listener);
	}

}
