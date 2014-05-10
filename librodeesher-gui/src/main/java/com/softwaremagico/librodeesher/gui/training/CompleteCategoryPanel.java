package com.softwaremagico.librodeesher.gui.training;

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

import com.softwaremagico.librodeesher.gui.elements.BaseScrollPanel;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.training.Training;

public class CompleteCategoryPanel extends BaseScrollPanel {
	private static final long serialVersionUID = -8277707872122891420L;
	private CategoryTitleLine title;
	private TrainingCategoryPanel categoryPanel;
	private Training training;
	private CharacterPlayer character;

	public CompleteCategoryPanel(CharacterPlayer character) {
		this.character=character;
		title = new CategoryTitleLine();
		addTitle(title);
		categoryPanel = new TrainingCategoryPanel(character, this);
		setBody(categoryPanel);
	}

	protected void sizeChanged() {
		title.sizeChanged();
	}

	public void setTraining(Training training) {
		this.training = training;
		categoryPanel = new TrainingCategoryPanel(character, this);
		setBody(categoryPanel);
		categoryPanel.setTraining(training);
		update();
	}

	@Override
	public void update() {
		categoryPanel.update();
	}
}
