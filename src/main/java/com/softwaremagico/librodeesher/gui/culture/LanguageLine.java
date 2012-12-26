package com.softwaremagico.librodeesher.gui.culture;

import java.awt.Color;

import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultFormatter;

import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.Language;
import com.softwaremagico.librodeesher.pj.skills.SkillFactory;

public class LanguageLine extends CultureLine {

	private static final long serialVersionUID = 2401612544094265349L;
	protected Language language;

	public LanguageLine(CharacterPlayer character, Language language, CulturePanel languagePanel,
			Color background) {
		this.character = character;
		this.skillName = language.getName();
		this.parentPanel = languagePanel;
		this.language = language;
		setElements(background);
		setBackground(background);
		rankSpinner.setValue(character.getLanguageInitialRanks(language));
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
				if (parentPanel.getSpinnerValues() > character.getCulture().getHobbyRanks()) {
					rankSpinner.setValue((Integer) rankSpinner.getValue() - 1);
				} else if (getSelectedRanks() > character.getProfession().getMaxRanksPerLevel(
						SkillFactory.getAvailableSkill(language.getName()).getCategory().getName())) {
					rankSpinner.setValue((Integer) rankSpinner.getValue() - 1);
				} else {
					// Update character
					character.getCultureDecisions().setHobbyRank(language, (Integer) rankSpinner.getValue());
				}
			}
		});
	}
}
