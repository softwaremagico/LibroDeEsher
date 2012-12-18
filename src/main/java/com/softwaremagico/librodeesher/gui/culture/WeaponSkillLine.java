package com.softwaremagico.librodeesher.gui.culture;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultFormatter;

import com.softwaremagico.librodeesher.gui.style.BasicLine;
import com.softwaremagico.librodeesher.pj.skills.Skill;

public class WeaponSkillLine extends BasicLine {
	private static final long serialVersionUID = -4697558156145520144L;
	private Skill weaponSkill;
	private Integer maxRanks;
	private JSpinner rankSpinner;

	public WeaponSkillLine(Skill weaponSkill, Integer ranks, Color background) {
		this.weaponSkill = weaponSkill;
		this.maxRanks = ranks;
		setElements(background);
		setBackground(background);
	}
	
	protected void setDefaultSize() {
		
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

		JLabel weaponSkillLabel = new JLabel(weaponSkill.getName());
		weaponSkillLabel.setFont(new Font(font, Font.PLAIN, fontSize));
		add(createLabelInsidePanel(weaponSkillLabel, SwingConstants.LEFT, background, fontColor), gridBagConstraints);

		rankSpinner = new JSpinner();
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

			}
		});
	}
}
