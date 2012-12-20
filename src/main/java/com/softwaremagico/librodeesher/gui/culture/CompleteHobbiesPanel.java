package com.softwaremagico.librodeesher.gui.culture;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import com.softwaremagico.librodeesher.gui.style.BasePanel;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;

public class CompleteHobbiesPanel extends BasePanel {
	private static final long serialVersionUID = -714349581832077977L;
	private CultureTitleLine title;
	private ChooseHobbyPanel weaponPanel;
	private CharacterPlayer character;

	public CompleteHobbiesPanel(CharacterPlayer character) {
		this.character = character;
		setElements();
	}

	private void setElements() {
		this.removeAll();
		setLayout(new GridBagLayout());
		GridBagConstraints gridBagConstraints = new GridBagConstraints();

		title = new CultureTitleLine("Aficiones", "Rangos (" + character.getCulture().getHobbyRanks() + ")");
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 0;
		add(title, gridBagConstraints);

		weaponPanel = new ChooseHobbyPanel(character, title);
		JScrollPane weaponsScrollPanel = new JScrollPane(weaponPanel,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		weaponsScrollPanel.setBorder(getBorder());
		// characteristicPanel.setMinimumSize(new Dimension(200, 0));
		weaponsScrollPanel.setBounds(0, 0, weaponsScrollPanel.getWidth(),
				weaponsScrollPanel.getHeight());
		// gridBagConstraints.anchor = GridBagConstraints.LINE_START;
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridheight = GridBagConstraints.REMAINDER;
		gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		add(weaponsScrollPanel, gridBagConstraints);
	}

}
