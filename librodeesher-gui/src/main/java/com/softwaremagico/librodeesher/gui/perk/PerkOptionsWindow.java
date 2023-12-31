package com.softwaremagico.librodeesher.gui.perk;

/*
 * #%L
 * Libro de Esher GUI
 * %%
 * Copyright (C) 2007 - 2013 Softwaremagico
 * %%
 * This software is designed by Jorge Hortelano Otero. Jorge Hortelano Otero
 * <softwaremagico@gmail.com> Valencia (Spain).
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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashSet;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.softwaremagico.librodeesher.basics.ChooseGroup;
import com.softwaremagico.librodeesher.gui.ShowMessage;
import com.softwaremagico.librodeesher.gui.elements.CloseButton;
import com.softwaremagico.librodeesher.gui.options.SelectOption;
import com.softwaremagico.librodeesher.gui.style.BaseFrame;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.perk.Perk;

public class PerkOptionsWindow<T> extends BaseFrame {
	private static final long serialVersionUID = 5275328306603274360L;
	private CharacterPlayer character;
	private Perk perk;
	private SelectOption<T> selectedOption;
	private PerkLine parent;
	private ChooseGroup<T> chooseOptions;

	public PerkOptionsWindow(CharacterPlayer character, Perk perk, ChooseGroup<T> chooseOptions,
			PerkLine parent) {
		this.character = character;
		this.perk = perk;
		this.parent = parent;
		this.chooseOptions = chooseOptions;
		defineWindow(500, 400);
		setResizable(false);
		addWindowListener(new WindowsClose());
		setElements();
	}

	public void setPointCounterLabel(String text) {
		selectedOption.setPointCounterLabel(text);
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
		selectedOption = new SelectOption<T>((BaseFrame) this, chooseOptions);
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
	public void updateFrame() {
		switch (chooseOptions.getChooseType()) {
		case BONUS:
			character.setPerkBonusDecision(perk, new HashSet<String>(selectedOption.getSelectedOptions()));
			break;
		case COMMON:
			character.setPerkCommonDecision(perk, new HashSet<String>(selectedOption.getSelectedOptions()));
			break;
		case RANK:
			character.setPerkRankDecision(perk, new HashSet<String>(selectedOption.getSelectedOptions()));
			break;
		default:
			break;
		}
	}

	private void checkSelection() {
		if (selectedOption.getSelectedOptions().size() != chooseOptions.getNumberOfOptionsToChoose()) {
			ShowMessage.showErrorMessage(
					"Debes seleccionar todas las opciones disponibles. El talento será eliminado.",
					"Talentos");
			parent.removePerk();
		}
	}

	class WindowsClose extends WindowAdapter {
		@Override
		public void windowClosing(WindowEvent e) {
			checkSelection();
		}
	}

	class IntelligentCloseButton extends CloseButton {
		private static final long serialVersionUID = 7427505640112272261L;

		public IntelligentCloseButton(JFrame window) {
			super(window);
		}

		@Override
		protected void closeAction() {
			checkSelection();
			window.dispose();
		}
	}
}
