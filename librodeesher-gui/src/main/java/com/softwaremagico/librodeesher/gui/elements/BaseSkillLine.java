package com.softwaremagico.librodeesher.gui.elements;

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

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;

import com.softwaremagico.librodeesher.gui.style.BaseLine;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.categories.Category;

public abstract class BaseSkillLine extends BaseLine {
	private static final long serialVersionUID = 8045688485824193991L;
	protected static final Integer columnWidth = 30;
	protected boolean updatingValues = false;
	protected JCheckBox firstRank, secondRank, thirdRank;
	protected CharacterPlayer character;
	protected Category category;
	protected BaseSkillPanel parentWindow;
	protected boolean costPanel = false;
	protected boolean oldRanksPanel = false;
	protected boolean chooseRanksPanel = false;
	protected boolean ranksValuePanel = false;
	protected boolean bonusCategoryPanel = false;
	protected boolean otherBonusPanel = false;
	protected boolean objectBonusPanel = false;
	protected boolean totalPanel = false;

	public void enableColumns(boolean costPanel, boolean oldRanksPanel, boolean chooseRanksPanel,
			boolean ranksValuePanel, boolean bonusCategoryPanel, boolean otherBonusPanel,
			boolean objectBonusPanel, boolean totalPanel) {
		this.costPanel = costPanel;
		this.oldRanksPanel = oldRanksPanel;
		this.chooseRanksPanel = chooseRanksPanel;
		this.ranksValuePanel = ranksValuePanel;
		this.bonusCategoryPanel = bonusCategoryPanel;
		this.otherBonusPanel = otherBonusPanel;
		this.objectBonusPanel = objectBonusPanel;
		this.totalPanel = totalPanel;
		setElements();
	}
	
	protected abstract void setElements();

	protected void enableRanks(Integer currentRanks) {
		updatingValues = true;
		Integer ranks = character.getMaxRanksPerLevel(category, currentRanks);

		if (firstRank != null) {
			if (ranks > 0) {
				firstRank.setEnabled(true);
			} else {
				firstRank.setEnabled(false);
			}
			if (ranks > 1) {
				secondRank.setEnabled(true);
			} else {
				secondRank.setEnabled(false);
			}
			if (ranks > 2) {
				thirdRank.setEnabled(true);
			} else {
				thirdRank.setEnabled(false);
			}
		}
		updatingValues = false;
	}

	protected Integer getRanksSelected() {
		Integer total = 0;
		if (firstRank.isSelected()) {
			total++;
		}
		if (secondRank.isSelected()) {
			total++;
		}
		if (thirdRank.isSelected()) {
			total++;
		}
		return total;
	}

	protected abstract boolean hasRanks();

	protected void setRanksSelected(Integer value) {
		updatingValues = true;
		if (hasRanks() && firstRank != null) {
			if (value > 0) {
				firstRank.setSelected(true);
			} else {
				firstRank.setSelected(false);
			}
			if (value > 1) {
				secondRank.setSelected(true);
			} else {
				secondRank.setSelected(false);
			}
			if (value > 2) {
				thirdRank.setSelected(true);
			} else {
				thirdRank.setSelected(false);
			}
		}
		updatingValues = false;
	}

	protected abstract void updateCharacterPlayerWithCurrentLevelRanks();

	class CheckBoxListener implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent e) {
			if (!updatingValues) {
				updateCharacterPlayerWithCurrentLevelRanks();
				update();
			}
		}
	}

}
