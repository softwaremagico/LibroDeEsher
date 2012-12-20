package com.softwaremagico.librodeesher.gui.culture;

import java.awt.Color;

import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultFormatter;

import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.culture.CultureSkill;
import com.softwaremagico.librodeesher.pj.skills.SkillFactory;

public class HobbyLine extends CultureLine {

	private static final long serialVersionUID = 2401612544094265349L;
	protected CultureSkill hobby;

	public HobbyLine(CharacterPlayer character, CultureSkill skill, CulturePanel hobbyPanel,
			Color background) {
		this.character = character;
		this.skillName = skill.getName();
		this.parentPanel = hobbyPanel;
		hobby = skill;
		setElements(background);
		setBackground(background);
		rankSpinner.setValue(character.getCultureDecisions().getHobbyRank(hobby));
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
						SkillFactory.getAvailableSkill(hobby.getName()).getCategory().getName())) {
					rankSpinner.setValue((Integer) rankSpinner.getValue() - 1);
				} else {
					// Update character
					character.getCultureDecisions().setHobbyRank(hobby, (Integer) rankSpinner.getValue());
				}
				parentPanel.setRankTitle("Rangos ("
						+ (character.getCulture().getHobbyRanks() - character.getCultureDecisions()
								.getTotalHobbyRanks()) + ")");
			}
		});
	}
}
