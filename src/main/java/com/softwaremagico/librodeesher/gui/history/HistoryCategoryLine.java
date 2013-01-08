package com.softwaremagico.librodeesher.gui.history;

import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

import com.softwaremagico.librodeesher.gui.elements.BaseSkillPanel;
import com.softwaremagico.librodeesher.gui.elements.GenericCategoryLine;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.categories.Category;

public class HistoryCategoryLine extends GenericCategoryLine {
	private static final long serialVersionUID = -3523895407174764934L;
	private JCheckBox historyCheckBox;

	public HistoryCategoryLine(CharacterPlayer character, Category category, Color background,
			BaseSkillPanel parentWindow) {
		super(character, category, background, parentWindow);
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
			character.setHistoryPoints(category, historyCheckBox.isSelected());
			update();
		}
	}

	@Override
	protected void setCurrentLevelRanks() {
		// NO RANKS
	}

}
