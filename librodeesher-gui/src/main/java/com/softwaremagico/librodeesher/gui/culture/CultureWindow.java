package com.softwaremagico.librodeesher.gui.culture;

/*
 * #%L
 * Libro de Esher
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import com.softwaremagico.librodeesher.gui.ShowMessage;
import com.softwaremagico.librodeesher.gui.culture.SelectLanguagesWindow.LanguageSelectionUpdateListener;
import com.softwaremagico.librodeesher.gui.elements.CloseButton;
import com.softwaremagico.librodeesher.gui.elements.RandomButton;
import com.softwaremagico.librodeesher.gui.style.BaseFrame;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.magic.MagicDefinitionException;
import com.softwaremagico.librodeesher.pj.profession.InvalidProfessionException;
import com.softwaremagico.librodeesher.pj.random.RandomCharacterPlayer;

public class CultureWindow extends BaseFrame {
	private static final long serialVersionUID = -3866934730061829486L;
	private CharacterPlayer character;

	public CultureWindow(CharacterPlayer character) {
		this.character = character;
		defineWindow(650, 400);
		// setResizable(false);
		setElements();
		if (!character.getRace().getOptionalLanguages().isEmpty()
				|| !character.getCulture().getOptionalLanguages().isEmpty()) {
			SelectLanguagesWindow selectWindow = new SelectLanguagesWindow(character);
			selectWindow.addLanguageSelectionUpdateListener(new LanguageSelectionUpdateListener() {
				@Override
				public void updated() {

				}
			});
			selectWindow.setVisible(true);
		}
	}

	private void setElements() {
		setLayout(new GridBagLayout());
		GridBagConstraints gridBagConstraints = new GridBagConstraints();

		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		gridBagConstraints.insets = new Insets(2, 2, 2, 2);
		getContentPane().add(new CompleteWeaponPanel(character), gridBagConstraints);

		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		gridBagConstraints.insets = new Insets(2, 2, 2, 2);
		getContentPane().add(new CompleteHobbiesPanel(character), gridBagConstraints);

		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		gridBagConstraints.insets = new Insets(2, 2, 2, 2);
		getContentPane().add(new CompleteLanguagePanel(character), gridBagConstraints);

		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 0;
		gridBagConstraints.insets = new Insets(2, 2, 2, 2);
		getContentPane().add(new CompleteSpellPanel(character), gridBagConstraints);

		JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
		JPanel emptyPanel = new JPanel();
		buttonPanel.add(emptyPanel);

		RandomButton randomButton = new RandomButton() {
			private static final long serialVersionUID = 3347462905897024435L;

			@Override
			public void RandomAction() {
				RandomCharacterPlayer.setWeaponCosts(character);
				try {
					RandomCharacterPlayer.setCulture(character, character.getCulture().getName(), 0);
				} catch (MagicDefinitionException | InvalidProfessionException e) {
					ShowMessage.showErrorMessage(e.getMessage(), "Error");
				}
				updateFrame();
			}
		};
		buttonPanel.add(randomButton);
		CloseButton closeButton = new CloseButton(this);
		buttonPanel.add(closeButton);

		gridBagConstraints.anchor = GridBagConstraints.LINE_END;
		gridBagConstraints.fill = GridBagConstraints.NONE;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 0;
		gridBagConstraints.weighty = 0;
		getContentPane().add(buttonPanel, gridBagConstraints);
	}

	@Override
	public void updateFrame() {
		getContentPane().removeAll();
		setElements();
		revalidate();
		repaint();
	}

	class AcceptListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

		}
	}

}
