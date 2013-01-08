package com.softwaremagico.librodeesher.gui.history;

import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

import com.softwaremagico.librodeesher.gui.elements.BaseSkillPanel;
import com.softwaremagico.librodeesher.gui.elements.GenericSkillLine;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.skills.Skill;

public class HistorySkillLine extends GenericSkillLine {
	private static final long serialVersionUID = 5951462195062999304L;
	private JCheckBox historyCheckBox;
	private BaseSkillPanel parent;

	public HistorySkillLine(CharacterPlayer character, Skill skill, Color background,
			BaseSkillPanel parentWindow) {
		super(character, skill, background, parentWindow);
		this.parent = parentWindow;
		enableColumns(false, false, false, false, false, false, false, true);
		addHistoryCheckBox();
	}

	private void addHistoryCheckBox() {
		JPanel panel = new JPanel();
		historyCheckBox = new JCheckBox("H");
		panel.add(historyCheckBox);
		historyCheckBox.addItemListener(new CheckBoxListener());
		addColumn(panel, 1);
	}

	class CheckBoxListener implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent e) {
			character.setHistoryPoints(skill, historyCheckBox.isSelected());
			update();
			parent.update();
		}
	}

}
