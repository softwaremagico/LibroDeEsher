package com.softwaremagico.librodeesher.gui.skills;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;

import com.softwaremagico.librodeesher.gui.style.BasicLine;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.categories.Category;

public abstract class BasicSkillLine extends BasicLine {
	private static final long serialVersionUID = 8045688485824193991L;
	protected static final Integer columnWidth = 30;
	protected static final Integer columnHeight = 20;
	protected boolean updatingValues = false;
	protected JCheckBox firstRank, secondRank, thirdRank;
	protected CharacterPlayer character;
	protected Category category;
	protected SkillPanel parentWindow;

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
		if (hasRanks()) {
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
	
	protected abstract void update();
	
	protected abstract void setCurrentLevelRanks();

	class CheckBoxListener implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent e) {
			if (!updatingValues) {
				setCurrentLevelRanks();
				update();
			}
		}
	}

}
