package com.softwaremagico.librodeesher.gui.culture;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultFormatter;

import com.softwaremagico.librodeesher.gui.style.BasicLine;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.culture.CultureSkill;
import com.softwaremagico.librodeesher.pj.skills.Skill;
import com.softwaremagico.librodeesher.pj.skills.SkillFactory;

public class HobbyLine extends BasicLine {

	private static final long serialVersionUID = 2401612544094265349L;
	private CharacterPlayer character;
	private Skill hobby;
	private JSpinner rankSpinner;
	private CultureSkill skill;
	private ChooseHobbyPanel parentPanel;

	public HobbyLine(CharacterPlayer character, CultureSkill skill, ChooseHobbyPanel hobbyPanel,
			Color background) {
		this.character = character;
		this.skill = skill;
		this.parentPanel = hobbyPanel;
		hobby = SkillFactory.getAvailableSkill(skill.getName());
		setElements(background);
		setBackground(background);
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

		JLabel hobby = new JLabel(skill.getName());
		hobby.setFont(new Font(font, Font.PLAIN, fontSize));
		add(createLabelInsidePanel(hobby, SwingConstants.LEFT, background, fontColor), gridBagConstraints);

		SpinnerModel sm = new SpinnerNumberModel(0, 0, 3, 1);
		rankSpinner = new JSpinner(sm);
		rankSpinner.setValue(0);
		addRankSpinnerEvent();
		gridBagConstraints.weightx = 0;
		gridBagConstraints.gridx = 1;
		add(createSpinnerInsidePanel(rankSpinner, background), gridBagConstraints);

	}

	private void addRankSpinnerEvent() {
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
			}
		});
	}

	protected Integer getSelectedRanks() {
		return (Integer) rankSpinner.getValue();
	}

}
