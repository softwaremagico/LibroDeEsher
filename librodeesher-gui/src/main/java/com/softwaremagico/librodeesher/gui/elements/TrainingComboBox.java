package com.softwaremagico.librodeesher.gui.elements;
/*
 * #%L
 * Libro de Esher (GUI)
 * %%
 * Copyright (C) 2007 - 2014 Softwaremagico
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

import java.util.List;

import com.softwaremagico.librodeesher.pj.training.TrainingFactory;

public class TrainingComboBox extends BaseComboBox<String> {
	private static final long serialVersionUID = 6685372259909729296L;

	public TrainingComboBox(List<String> trainings, List<String> avoidTrainings) {
		setElements(trainings, avoidTrainings);
	}

	public void setElements(List<String> trainings, List<String> avoidTrainings) {
		this.removeAllItems();
		for (String training : trainings) {
			if (avoidTrainings == null || !avoidTrainings.contains(training)) {
				addItem(training);
			}
		}
	}

	@Override
	public void doAction() {
		// Do nothing.
	}

}
