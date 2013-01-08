package com.softwaremagico.librodeesher.gui.history;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.softwaremagico.librodeesher.gui.elements.SkillTitleLine;

public class HistorySkillTitle extends SkillTitleLine {
	private static final long serialVersionUID = -7713583862792690761L;
	private JLabel historyCheckBox;

	public HistorySkillTitle() {
		enableColumns(false, false, false, false, false, false, false, true);
		addHistoryCheckBox();
	}
	
	private void addHistoryCheckBox() {
		JPanel panel = new JPanel();
		historyCheckBox = new JLabel("Hist.");
		historyCheckBox.setFont(defaultFont);
		panel.add(historyCheckBox);
		addColumn(panel, 1);
	}
	
	public void sizeChanged(){
		defaultElementsSizeChanged();
		if (this.getWidth() < 800) {
			historyCheckBox.setText("Hst.");
		}else{
			historyCheckBox.setText("Historial");
		}
	}
}
