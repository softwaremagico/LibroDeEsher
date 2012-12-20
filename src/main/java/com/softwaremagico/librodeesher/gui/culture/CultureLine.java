package com.softwaremagico.librodeesher.gui.culture;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import com.softwaremagico.librodeesher.gui.style.BasicLine;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;

public abstract class CultureLine extends BasicLine {
	protected static final long serialVersionUID = -8287822744700383705L;
	protected CharacterPlayer character;
	protected JSpinner rankSpinner;
	protected String skillName;
	protected ChooseCulturePanel parentPanel;

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

		JLabel hobby = new JLabel(skillName);
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

	protected abstract void addRankSpinnerEvent();

	protected Integer getSelectedRanks() {
		return (Integer) rankSpinner.getValue();
	}

}
