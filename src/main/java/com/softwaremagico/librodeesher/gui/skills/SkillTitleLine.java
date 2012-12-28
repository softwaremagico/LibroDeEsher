package com.softwaremagico.librodeesher.gui.skills;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import com.softwaremagico.librodeesher.gui.style.BasicTitleLine;

public class SkillTitleLine extends BasicTitleLine {
	private static final long serialVersionUID = 4480268296161276440L;
	private JLabel rankLabel;

	public SkillTitleLine(String titleLabelText, String rankLabelText) {
		setElements(background, titleLabelText, rankLabelText);
		setBackground(background);
	}

	protected void setElements(Color background, String labelText, String rankLabelText) {
		this.removeAll();
		setLayout(new GridLayout(1, 2));

		JLabel weaponCategoryLabel = new JLabel(labelText);
		add(createLabelInsidePanel(weaponCategoryLabel, SwingConstants.CENTER, background, fontColor));

		rankLabel = new JLabel(rankLabelText);
		add(createLabelInsidePanel(rankLabel, SwingConstants.CENTER, background, fontColor));

	}
	
	protected void setRankTitle(String rankLabelText){
		rankLabel.setText(rankLabelText);
	}
}
