package com.softwaremagico.librodeesher.gui.perk;

/*
 * #%L
 * Libro de Esher GUI
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
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.softwaremagico.librodeesher.basics.ShowMessage;
import com.softwaremagico.librodeesher.gui.elements.CloseButton;
import com.softwaremagico.librodeesher.gui.options.SelectOption;
import com.softwaremagico.librodeesher.gui.style.BaseFrame;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.perk.Perk;

public class PerkOptions extends BaseFrame {
	private CharacterPlayer character;
	private Perk perk;
	private SelectOption selectedOption;
	private PerkLine parent;

	public PerkOptions(CharacterPlayer character, Perk perk, PerkLine parent) {
		this.character = character;
		this.perk = perk;
		this.parent = parent;
		defineWindow(500, 400);
		setResizable(false);
		setElements();
	}

	private void setElements() {
		setLayout(new GridBagLayout());
		GridBagConstraints gridBagConstraints = new GridBagConstraints();

		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		gridBagConstraints.insets = new Insets(2, 2, 2, 2);
		selectedOption = new SelectOption(character, (BaseFrame) this, perk.getOptionsToChoose(),
				perk.getNumberOfChooseOptions());
		getContentPane().add(selectedOption, gridBagConstraints);

		JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
		IntelligentCloseButton closeButton = new IntelligentCloseButton(this);
		buttonPanel.add(closeButton);
		gridBagConstraints.anchor = GridBagConstraints.LINE_END;
		gridBagConstraints.fill = GridBagConstraints.NONE;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 0.3;
		gridBagConstraints.weighty = 0;
		gridBagConstraints.insets = new Insets(2, 2, 2, 2);
		getContentPane().add(buttonPanel, gridBagConstraints);
	}

	@Override
	public void update() {
		character.setPerkBonusDecision(perk, selectedOption.getSelectedOptions());
	}

	class IntelligentCloseButton extends CloseButton {

		public IntelligentCloseButton(JFrame window) {
			super(window);
		}

		protected void closeAction() {
			if (selectedOption.getSelectedOptions().size() != perk.getNumberOfChooseOptions()) {
				ShowMessage
						.showErrorMessage(
								"Error. Debes seleccionar todas las opciones disponibles. El talento será eliminado.",
								"Talentos");
				parent.removePerk();
			}
			window.dispose();
		}
	}
}
