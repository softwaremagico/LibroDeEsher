package com.softwaremagico.librodeesher.gui.culture;

import java.awt.Color;

import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultFormatter;

import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.Language;
import com.softwaremagico.librodeesher.pj.skills.SkillFactory;

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
		rankSpinner.setValue(initalValue);
		System.out.println(language.getName() + ":" + initalValue + " < "
				+ character.getLanguageMaxInitialRanks(language));
		SpinnerModel sm = new SpinnerNumberModel((int) initalValue, (int) initalValue, (int) character
				.getLanguageMaxInitialRanks(language), 1);
		rankSpinner.setModel(sm);
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
