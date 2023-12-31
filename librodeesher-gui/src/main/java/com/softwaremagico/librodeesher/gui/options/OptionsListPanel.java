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

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import com.softwaremagico.librodeesher.gui.style.BasePanel;

public class OptionsListPanel extends BasePanel {
	private static final long serialVersionUID = -4518185385799868381L;
	private OptionsPanel parent;
	private List<OptionLine> optionLines;
	private Integer numberOfOptions;
	private List<String> selectedOptions;

	public OptionsListPanel(OptionsPanel parent) {
		this.parent = parent;
		optionLines = new ArrayList<>();
	}

	public OptionsListPanel(List<String> options, Integer numberOfOptions, OptionsPanel parent,
			List<String> selectedOptions) {
		this.parent = parent;
		optionLines = new ArrayList<>();
		this.numberOfOptions = numberOfOptions;
		this.selectedOptions = selectedOptions;
		setElements(options);
	}

	public void setElements(List<String> options) {
		this.removeAll();
		setLayout(new GridLayout(0, 1));
		int i = 0;
		for (String option : options) {
			OptionLine optionLine = new OptionLine(option, this, getLineBackgroundColor(i));
			add(optionLine);
			optionLine.setSelected(selectedOptions.contains(option));
			optionLines.add(optionLine);
			i++;
		}
	}

	protected List<String> getSelectedOptions() {
		List<String> selectedOptions = new ArrayList<>();
		for (OptionLine line : optionLines) {
			if (line.isSelected()) {
				selectedOptions.add(line.getOption());
			}
		}
		return selectedOptions;
	}

	protected Integer getRemainingOptions() {
		return numberOfOptions - getSelectedOptions().size();
	}

	public void update() {
		parent.update();
	}

	public void setNumberOfOptions(Integer numberOfOptions) {
		this.numberOfOptions = numberOfOptions;
	}

	public void setSelectedOptions(List<String> selectedOptions) {
		this.selectedOptions = selectedOptions;
		updateSelectedOptions();
	}

	private void updateSelectedOptions() {
		for (OptionLine line : optionLines) {
			line.setSelected(selectedOptions.contains(line.getOption()));
		}
	}

	@Override
	public void setEnabled(boolean enabled) {
		for (OptionLine line : optionLines) {
			line.setEnabled(enabled);
		}
	}
}
