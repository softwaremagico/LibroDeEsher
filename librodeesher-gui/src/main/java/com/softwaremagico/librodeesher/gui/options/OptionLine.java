package com.softwaremagico.librodeesher.gui.options;

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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.softwaremagico.librodeesher.gui.elements.BaseCheckBox;
import com.softwaremagico.librodeesher.gui.elements.ListBackgroundPanel;
import com.softwaremagico.librodeesher.gui.elements.ListLabel;
import com.softwaremagico.librodeesher.gui.style.BaseLine;

public class OptionLine extends BaseLine {
	private static final long serialVersionUID = -6769669550426726858L;
	private OptionsListPanel parent;
	private String option;
	private BaseCheckBox optionCheckBox;
	private Color background;

	protected OptionLine(String option, OptionsListPanel parent, Color background) {
		this.option = option;
		this.parent = parent;
		this.background = background;
		setBackground(background);
		setElements();
	}

	private void setElements() {
		this.removeAll();
		setLayout(new GridBagLayout());
		GridBagConstraints gridBagConstraints = new GridBagConstraints();

		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.anchor = GridBagConstraints.LINE_START;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.weighty = 0;

		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 0;
		JPanel panel = new JPanel();
		optionCheckBox = new BaseCheckBox("");
		panel.add(optionCheckBox);
		panel.setBackground(background);
		optionCheckBox.setBackground(background);
		optionCheckBox.addItemListener(new CheckBoxListener());
		panel.setBackground(background);
		add(panel, gridBagConstraints);

		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
		gridBagConstraints.weightx = 1;
		ListLabel optionLabel = new ListLabel(option, SwingConstants.LEFT);
		add(new ListBackgroundPanel(optionLabel, background), gridBagConstraints);

	}

	public boolean isSelected() {
		if (optionCheckBox != null) {
			return optionCheckBox.isSelected();
		}
		return false;
	}

	public String getOption() {
		return option;
	}

	@Override
	public void update() {
		parent.update();
	}

	class CheckBoxListener implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent e) {
			if (parent.getRemainingOptions() < 0) {
				optionCheckBox.setSelected(false);
			}
			update();
		}
	}

}
