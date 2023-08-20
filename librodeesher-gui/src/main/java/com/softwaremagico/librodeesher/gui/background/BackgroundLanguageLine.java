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

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultFormatter;

import com.softwaremagico.librodeesher.gui.elements.BaseSpinner;
import com.softwaremagico.librodeesher.gui.elements.ListLabel;
import com.softwaremagico.librodeesher.gui.style.BaseLine;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.skills.SkillFactory;

public class BackgroundLanguageLine extends BaseLine {

	private static final long serialVersionUID = 2401612544094265349L;
	private String language;
	private Integer initalValue;
	private BackgroundLanguagePanel parentPanel;
	private CharacterPlayer characterPlayer;
	private BaseSpinner rankSpinner;
	private String skillName;

	public BackgroundLanguageLine(CharacterPlayer characterPlayer, String language, BackgroundLanguagePanel languagePanel, Color background) {
		this.characterPlayer = characterPlayer;
		this.skillName = language;
		this.parentPanel = languagePanel;
		this.language = language;
		setElements(background);
		setBackground(background);

		// Set spinner.
		initalValue = characterPlayer.getTotalRanks(SkillFactory.getAvailableSkill(language));
		SpinnerModel sm = new SpinnerNumberModel((int) initalValue, (int) initalValue - characterPlayer.getHistoryLanguageRanks(language),
				(int) Math.max(initalValue, (characterPlayer.getMaxHistoryLanguages().get(language) != null ? (int) characterPlayer
						.getMaxHistoryLanguages().get(language) : initalValue)), 1);
		rankSpinner.setModel(sm);

		// Languages can have 10 ranks. We need a bigger editor.
		rankSpinner.setColumns(2);

		if (characterPlayer.getCurrentLevelNumber() != 1) {
			rankSpinner.setEnabled(false);
		}

		// Initialize the title.
		setTitle();
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

		ListLabel hobby = new ListLabel(skillName, SwingConstants.LEFT);
		add(hobby, gridBagConstraints);

		SpinnerModel sm = new SpinnerNumberModel(0, 0, 3, 1);
		rankSpinner = new BaseSpinner(sm);
		rankSpinner.setValue(0);
		addRankSpinnerEvent();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.weightx = 0;
		add(createSpinnerInsidePanel(rankSpinner, background), gridBagConstraints);
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
				characterPlayer.setHistoryLanguageRanks(language,
						(Integer) rankSpinner.getValue() - (characterPlayer.getTotalRanks(SkillFactory.getAvailableSkill(language)) - characterPlayer.getHistoryLanguageRanks(language)));
				// Race and culture language limit.
				if (Math.max(characterPlayer.getMaxHistoryLanguages().get(language), initalValue) < characterPlayer
						.getTotalRanks(SkillFactory.getSkill(language))) {
					try {
						rankSpinner.setValue((Integer) rankSpinner.getValue() - 1);
					} catch (Exception ex) {
						rankSpinner.setValue(initalValue);
					}
					// No enough history points
				} else if (characterPlayer.getRemainingBackgroundPoints() < 0) {
					rankSpinner.setValue((Integer) rankSpinner.getValue() - 1);
				} else {
					setTitle();
				}
				update();
			}
		});
	}

	private void setTitle() {
		int ranks = 20 - (characterPlayer.getBackgroundTotalLanguageRanks() % 20);
		parentPanel.setTitle(ranks == 20 ? 0 : ranks);
	}

	protected Integer getSelectedRanks() {
		return (Integer) rankSpinner.getValue() - initalValue;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Integer getInitalValue() {
		return initalValue;
	}

	public void setInitalValue(Integer initalValue) {
		this.initalValue = initalValue;
	}

	public BackgroundLanguagePanel getParentPanel() {
		return parentPanel;
	}

	public void setParentPanel(BackgroundLanguagePanel parentPanel) {
		this.parentPanel = parentPanel;
	}

	public CharacterPlayer getCharacterPlayer() {
		return characterPlayer;
	}

	public void setCharacterPlayer(CharacterPlayer characterPlayer) {
		this.characterPlayer = characterPlayer;
	}

	public BaseSpinner getRankSpinner() {
		return rankSpinner;
	}

	public void setRankSpinner(BaseSpinner rankSpinner) {
		this.rankSpinner = rankSpinner;
	}

	public String getSkillName() {
		return skillName;
	}

	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}

	@Override
	public void update() {
		parentPanel.update();
	}

	public void updateRanks() {
		rankSpinner.setValue((int) initalValue + characterPlayer.getHistoryLanguageRanks(language));
	}
}
