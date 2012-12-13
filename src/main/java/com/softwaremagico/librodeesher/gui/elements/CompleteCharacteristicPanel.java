package com.softwaremagico.librodeesher.gui.elements;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import com.softwaremagico.librodeesher.gui.style.BaseFrame;
import com.softwaremagico.librodeesher.gui.style.BasePanel;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;

public class CompleteCharacteristicPanel extends BasePanel {
	private static final long serialVersionUID = -1990965342534961094L;
	private CharacteristicPanel characteristicPanel;
	private CharacteristicTitleLine title;

	public CompleteCharacteristicPanel() {
		setElements();
	}

	private void setElements() {
		this.removeAll();
		setLayout(new GridBagLayout());
		GridBagConstraints gridBagConstraints = new GridBagConstraints();

		title = new CharacteristicTitleLine();
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 0;
		add(title, gridBagConstraints);

		characteristicPanel = new CharacteristicPanel();
		JScrollPane characteristicScrollPanel = new JScrollPane(characteristicPanel,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		characteristicScrollPanel.setBorder(getBorder());
		//characteristicPanel.setMinimumSize(new Dimension(200, 0));
		characteristicScrollPanel.setBounds(0, 0, characteristicScrollPanel.getWidth(),
				characteristicScrollPanel.getHeight());
		// gridBagConstraints.anchor = GridBagConstraints.LINE_START;
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridheight = GridBagConstraints.REMAINDER;
		gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		add(characteristicScrollPanel, gridBagConstraints);
	}
	
	public void setCharacter(CharacterPlayer character, boolean summaryMode) {
		characteristicPanel.setCharacter(character, summaryMode);
		title.setSummaryMode(summaryMode);
	}

	public void setParentWindow(BaseFrame characteristicsWindow) {
		characteristicPanel.setParentWindow(characteristicsWindow);
	}

}
