package com.softwaremagico.librodeesher.gui.characteristic;

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

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.SwingConstants;

import com.softwaremagico.librodeesher.gui.elements.BoldListLabel;
import com.softwaremagico.librodeesher.gui.elements.ListBackgroundPanel;
import com.softwaremagico.librodeesher.gui.style.BaseFrame;
import com.softwaremagico.librodeesher.gui.style.BaseLine;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.characteristic.Characteristic;

public abstract class CharacteristicUpLine extends BaseLine {
	private static final long serialVersionUID = -7664364625714636655L;
	protected CharacterPlayer character;
	protected Characteristic characteristic;
	private BoldListLabel characteristicLabel, temporalText, potentialText;
	private BaseFrame parentWindow;
	private JButton updateButton;

	public CharacteristicUpLine(CharacterPlayer character, Characteristic characteristic, Color background) {
		this.character = character;
		this.characteristic = characteristic;
		setElements(background);
		setBackground(background);
	}

	protected void setElements(Color background) {
		this.removeAll();
		setLayout(new GridLayout(1, 0, 3, 3));

		characteristicLabel = new BoldListLabel(characteristic.getAbbreviature(), SwingConstants.LEFT);
		add(new ListBackgroundPanel(characteristicLabel, background));

		temporalText = new BoldListLabel("0");
		add(new ListBackgroundPanel(temporalText, background));

		potentialText = new BoldListLabel("0");
		add(new ListBackgroundPanel(potentialText, background));

		updateButton = new JButton("Subir");
		addAcceptListener();
		add(updateButton);

		update();
	}

	public abstract void addAcceptListener();

	/**
	 * Decides when the Accept button will be enabled or disabled.
	 */
	public abstract void setAcceptEnabled();

	@Override
	public void update() {
		temporalText.setText(character.getCharacteristicTemporalValue(characteristic.getAbbreviature())
				.toString());
		potentialText.setText(character.getCharacteristicPotentialValue(characteristic.getAbbreviature())
				.toString());
		setAcceptEnabled();
	}

	public void setParentWindow(BaseFrame window) {
		parentWindow = window;
	}

	public BaseFrame getParentWindow() {
		return parentWindow;
	}

	public JButton getUpdateButton() {
		return updateButton;
	}
}
