package com.softwaremagico.librodeesher.gui.culture;

import java.awt.Color;

import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultFormatter;

import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.skills.SkillFactory;

public class HobbyLine extends CultureLine {

	private static final long serialVersionUID = 2401612544094265349L;

	public HobbyLine(CharacterPlayer character, String hobby, CulturePanel hobbyPanel,
			Color background) {
		this.character = character;
		this.skillName = hobby;
		this.parentPanel = hobbyPanel;
		setElements(background);
		setBackground(background);
		rankSpinner.setValue(character.getCultureDecisions().getHobbyRanks(hobby));
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
						SkillFactory.getAvailableSkill(skillName).getCategory().getName())) {
					rankSpinner.setValue((Integer) rankSpinner.getValue() - 1);
				} else {
					// Update character
					character.getCultureDecisions().setHobbyRanks(skillName, (Integer) rankSpinner.getValue());
				}
				parentPanel.setRankTitle("Rangos ("
						+ (character.getCulture().getHobbyRanks() - character.getCultureDecisions()
								.getTotalHobbyRanks()) + ")");
			}
		});
	}
}
