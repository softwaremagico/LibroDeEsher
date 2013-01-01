package com.softwaremagico.librodeesher.gui.culture;
/*
 * #%L
 * Libro de Esher
 * %%
 * Copyright (C) 2007 - 2013 Softwaremagico
 * %%
 * This software is designed by Jorge Hortelano Otero. Jorge Hortelano Otero
 * <softwaremagico@gmail.com> C/Quart 89, 3. Valencia CP:46008 (Spain).
 *  
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *  
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *  
 * You should have received a copy of the GNU General Public License along with
 * this program; If not, see <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

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
