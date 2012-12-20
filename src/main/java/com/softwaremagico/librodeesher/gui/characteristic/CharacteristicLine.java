package com.softwaremagico.librodeesher.gui.characteristic;

/*
 * #%L
 * Libro de Esher
 * %%
 * Copyright (C) 2007 - 2012 Softwaremagico
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

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultFormatter;

import com.softwaremagico.librodeesher.gui.style.BaseFrame;
import com.softwaremagico.librodeesher.gui.style.BasicLine;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.characteristic.Characteristic;
import com.softwaremagico.librodeesher.pj.characteristic.Characteristics;

public class CharacteristicLine extends BasicLine {
	private static final long serialVersionUID = 1855952180568184802L;
	protected CharacterPlayer character;
	protected Characteristic characteristic;
	protected JLabel characteristicLabel;
	private JSpinner temporalSpinner;
	private JLabel potentialText;
	private JLabel basicBonusText;
	private JLabel raceBonusText;
	private JLabel specialBonusText;
	protected JLabel totalLabel;
	private BaseFrame parentWindow;

	public CharacteristicLine(CharacterPlayer character, Characteristic characteristic, Color background) {
		this.character = character;
		this.characteristic = characteristic;
		setElements(background);
		setBackground(background);
	}

	protected void setElements(Color background) {
		this.removeAll();
		setLayout(new GridLayout(1, 7));

		characteristicLabel = new JLabel(characteristic.getAbbreviature());
		add(createLabelInsidePanel(characteristicLabel, SwingConstants.LEFT, background, fontColor));

		// SpinnerModel sm = new SpinnerNumberModel(
		// (Integer)
		// character.getCharacteristicsTemporalValues(characteristic.getAbbreviation()),
		// (Integer) 1, (Integer) MAX_VALUE, (Integer) 1);
		temporalSpinner = new JSpinner();
		temporalSpinner.setValue(character.getCharacteristicTemporalValues(characteristic.getAbbreviature()));
		addTemporalSpinnerEvent();
		temporalSpinner.setEnabled(!character.areCharacteristicsConfirmed());
		add(createSpinnerInsidePanel(temporalSpinner, background));

		potentialText = new JLabel("0");
		add(createLabelInsidePanel(potentialText, SwingConstants.CENTER, background, fontColor));

		basicBonusText = new JLabel(character
				.getCharacteristicTemporalBonus(characteristic.getAbbreviature()).toString());
		add(createLabelInsidePanel(basicBonusText, SwingConstants.CENTER, background, fontColor));

		raceBonusText = new JLabel(character.getCharacteristicRaceBonus(characteristic.getAbbreviature())
				.toString());
		add(createLabelInsidePanel(raceBonusText, SwingConstants.CENTER, background, fontColor));

		specialBonusText = new JLabel(character.getCharacteristicSpecialBonus(
				characteristic.getAbbreviature()).toString());
		add(createLabelInsidePanel(specialBonusText, SwingConstants.CENTER, background, fontColor));

		totalLabel = new JLabel(character.getCharacteristicTotalBonus(characteristic.getAbbreviature())
				.toString());
		add(createLabelInsidePanel(totalLabel, SwingConstants.CENTER, background, fontColor));

	}

	public void update() {
		if (basicBonusText != null) {
			basicBonusText.setText(character.getCharacteristicTemporalBonus(characteristic.getAbbreviature())
					.toString());
		}
		if (totalLabel != null) {
			totalLabel.setText(character.getCharacteristicTotalBonus(characteristic.getAbbreviature())
					.toString());
		}
		if (specialBonusText != null) {
			specialBonusText.setText(character
					.getCharacteristicSpecialBonus(characteristic.getAbbreviature()).toString());
		}
		if (temporalSpinner != null) {
			temporalSpinner.setEnabled(!character.areCharacteristicsConfirmed());
		}
	}

	public void setPotential() {
		potentialText.setText(character.getCharacteristicPotentialValues(characteristic.getAbbreviature())
				.toString());
		temporalSpinner.setEnabled(!character.areCharacteristicsConfirmed());
	}

	private void addTemporalSpinnerEvent() {
		JComponent comp = temporalSpinner.getEditor();
		JFormattedTextField field = (JFormattedTextField) comp.getComponent(0);
		DefaultFormatter formatter = (DefaultFormatter) field.getFormatter();
		formatter.setCommitsOnValidEdit(true);
		temporalSpinner.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {

				// Store value.
				character.setCharacteristicTemporalValues(characteristic.getAbbreviature(),
						(Integer) temporalSpinner.getValue());
				
				// Range values
				if ((Integer) temporalSpinner.getValue() < 1) {
					temporalSpinner.setValue(1);
				} else if ((Integer) temporalSpinner.getValue() > Characteristics.MAX_INITIAL_CHARACTERISTIC_VALUE) {
					temporalSpinner.setValue(Characteristics.MAX_INITIAL_CHARACTERISTIC_VALUE);
				} else if ((Integer) temporalSpinner.getValue() < 90
						&& character.isMainProfessionalCharacteristic(characteristic)) {
					temporalSpinner.setValue(90);
					// Development points restrictions.
				} else if (character.getTemporalPointsSpent() > Characteristics.TOTAL_CHARACTERISTICS_POINTS) {
					temporalSpinner.setValue((Integer) temporalSpinner.getValue() - 1);
				}

				update();

				if (parentWindow != null) {
					parentWindow.update();
				}
			}
		});
	}

	public void setParentWindow(BaseFrame window) {
		parentWindow = window;
	}

}