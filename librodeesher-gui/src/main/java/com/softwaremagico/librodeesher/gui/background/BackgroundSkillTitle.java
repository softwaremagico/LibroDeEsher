package com.softwaremagico.librodeesher.gui.background;

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

import javax.swing.SwingConstants;

import com.softwaremagico.librodeesher.gui.elements.SkillTitleLine;
import com.softwaremagico.librodeesher.gui.elements.TitleLabel;

public class BackgroundSkillTitle extends SkillTitleLine {
	private static final long serialVersionUID = -7713583862792690761L;
	private TitleLabel historyLabel;

	public BackgroundSkillTitle() {
		enableColumns(false, false, false, false, false, false, false, false, false, true);
		addHistoryCheckBox();
	}

	private void addHistoryCheckBox() {
		historyLabel = new TitleLabel("Hst", SwingConstants.LEFT);
		addColumn(historyLabel, 0);
	}

	@Override
	public void sizeChanged() {
		defaultElementsSizeChanged();
		if (this.getWidth() < 800) {
			historyLabel.setText("Hst");
		} else {
			historyLabel.setText("Historial");
		}
	}
}
