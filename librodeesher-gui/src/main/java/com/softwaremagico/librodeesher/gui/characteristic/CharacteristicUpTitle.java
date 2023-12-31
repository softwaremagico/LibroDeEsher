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

import java.awt.GridLayout;

import com.softwaremagico.librodeesher.gui.elements.TitleLabel;
import com.softwaremagico.librodeesher.gui.style.BaseTitleLine;

public class CharacteristicUpTitle extends BaseTitleLine {
	private static final long serialVersionUID = 8983336623653594103L;
	private TitleLabel characteristicLabel, temporalLabel, potentialTextField, updateLabel;

	public CharacteristicUpTitle() {
		setElements();
	}

	private void setElements() {
		this.removeAll();
		setLayout(new GridLayout(1, 0));

		characteristicLabel = new TitleLabel("Caract");
		add(characteristicLabel);

		temporalLabel = new TitleLabel("Tem");
		add(temporalLabel);

		potentialTextField = new TitleLabel("Pot");
		add(potentialTextField);

		updateLabel = new TitleLabel("");
		add(updateLabel);
	}

	@Override
	public void update() {

	}

	public void sizeChanged() {
		if (this.getWidth() < 600) {
			characteristicLabel.setText("Caract");
		} else {
			characteristicLabel.setText("Característica");
		}

		if (this.getWidth() < 400) {
			temporalLabel.setText("Tem");
		} else {
			temporalLabel.setText("Temporal");
		}

		if (this.getWidth() < 400) {
			potentialTextField.setText("Pot");
		} else {
			potentialTextField.setText("Potencial");
		}
	}
}
