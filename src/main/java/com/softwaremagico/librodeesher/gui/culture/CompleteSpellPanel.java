package com.softwaremagico.librodeesher.gui.culture;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import com.softwaremagico.librodeesher.gui.style.BasePanel;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;

public class CompleteSpellPanel extends BasePanel {
	private static final long serialVersionUID = -714349581832077977L;
	private CultureTitleLine title;
	private SpellPanel spellPanel;
	private CharacterPlayer character;

	public CompleteSpellPanel(CharacterPlayer character) {
		this.character = character;
		setElements();
	}

	private void setElements() {
		this.removeAll();
		setLayout(new GridBagLayout());
		GridBagConstraints gridBagConstraints = new GridBagConstraints();

		title = new CultureTitleLine("Hechizos", "Rangos (" + character.getCulture().getSpellRanks() + ")");
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 0;
		add(title, gridBagConstraints);

		spellPanel = new SpellPanel(character, title);
		JScrollPane spellScrollPanel = new JScrollPane(spellPanel,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		spellScrollPanel.setBorder(getBorder());
		spellScrollPanel.setBounds(0, 0, spellScrollPanel.getWidth(), spellScrollPanel.getHeight());
		gridBagConstraints.anchor = GridBagConstraints.LINE_START;
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridheight = GridBagConstraints.REMAINDER;
		gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		add(spellScrollPanel, gridBagConstraints);
	}

	protected void setRankTitle(String rankLabelText) {
		title.setRankTitle(rankLabelText);
	}

}
