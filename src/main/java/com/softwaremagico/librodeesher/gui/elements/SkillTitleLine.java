package com.softwaremagico.librodeesher.gui.elements;

/*
 * #%L
 * Libro de Esher
 * %%
 * Copyright (C) 2007 - 2013 Softwaremagico
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

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import com.softwaremagico.librodeesher.gui.style.BasicTitleLine;

public class SkillTitleLine extends BasicTitleLine {
	private static final long serialVersionUID = 4480268296161276440L;
	private static final Integer columnWidth = 30;
	private static final Integer columnHeight = 20;
	private JLabel prevRanksLabel, currentRanksLabel, bonusRankLabel, bonusCharLabel, bonusMagicObject,
			otherBonus, totalLabel;
	protected boolean costPanel = false;
	protected boolean oldRanksPanel = false;
	protected boolean chooseRanksPanel = false;
	protected boolean ranksValuePanel = false;
	protected boolean bonusCategoryPanel = false;
	protected boolean otherBonusPanel = false;
	protected boolean objectBonusPanel = false;
	protected boolean totalPanel = false;

	public SkillTitleLine() {
		setBackground(background);
	}

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

	protected void setElements() {
		this.removeAll();
		setLayout(new GridBagLayout());
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		Font defaultFont = new Font(font, Font.BOLD, fontSize);

		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.weighty = 0;

		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 0.3;
		JLabel categoryNameLabel = new JLabel("Nombre");
		categoryNameLabel.setFont(defaultFont);
		categoryNameLabel.setHorizontalAlignment(SwingConstants.LEFT);
		categoryNameLabel.setMinimumSize(new Dimension(200, columnHeight));
		categoryNameLabel.setPreferredSize(new Dimension(200, columnHeight));
		add(categoryNameLabel, gridBagConstraints);

		if (costPanel) {
			gridBagConstraints.gridx = 3;
			gridBagConstraints.gridwidth = 1;
			gridBagConstraints.weightx = 0.1;
			JLabel rankCostLabel = new JLabel("Coste");
			rankCostLabel.setMinimumSize(new Dimension(columnWidth * 2, columnHeight));
			rankCostLabel.setPreferredSize(new Dimension(columnWidth * 2, columnHeight));
			rankCostLabel.setHorizontalAlignment(SwingConstants.CENTER);
			add(rankCostLabel, gridBagConstraints);
		}

		if (oldRanksPanel) {
			gridBagConstraints.gridx = 5;
			gridBagConstraints.gridwidth = 1;
			gridBagConstraints.weightx = 0.1;
			prevRanksLabel = new JLabel("Rng");
			prevRanksLabel.setFont(defaultFont);
			prevRanksLabel.setMinimumSize(new Dimension(columnWidth, columnHeight));
			prevRanksLabel.setPreferredSize(new Dimension(columnWidth, columnHeight));
			prevRanksLabel.setHorizontalAlignment(SwingConstants.CENTER);
			add(prevRanksLabel, gridBagConstraints);
		}

		if (chooseRanksPanel) {
			gridBagConstraints.gridx = 7;
			gridBagConstraints.gridwidth = 1;
			gridBagConstraints.weightx = 0.1;
			currentRanksLabel = new JLabel("Rng");
			currentRanksLabel.setFont(defaultFont);
			currentRanksLabel.setMinimumSize(new Dimension(columnWidth * 2, columnHeight));
			currentRanksLabel.setPreferredSize(new Dimension(columnWidth * 2, columnHeight));
			currentRanksLabel.setHorizontalAlignment(SwingConstants.CENTER);
			add(currentRanksLabel, gridBagConstraints);
		}

		if (ranksValuePanel) {
			bonusRankLabel = new JLabel("Val");
			bonusRankLabel.setFont(defaultFont);
			gridBagConstraints.gridx = 9;
			gridBagConstraints.gridwidth = 1;
			gridBagConstraints.weightx = 0.1;
			bonusRankLabel.setMinimumSize(new Dimension(columnWidth, columnHeight));
			bonusRankLabel.setPreferredSize(new Dimension(columnWidth, columnHeight));
			bonusRankLabel.setHorizontalAlignment(SwingConstants.CENTER);
			add(bonusRankLabel, gridBagConstraints);
		}

		if (bonusCategoryPanel) {
			bonusCharLabel = new JLabel("Cat");
			bonusCharLabel.setFont(defaultFont);
			bonusCharLabel.setMinimumSize(new Dimension(columnWidth, columnHeight));
			bonusCharLabel.setPreferredSize(new Dimension(columnWidth, columnHeight));
			bonusCharLabel.setHorizontalAlignment(SwingConstants.CENTER);
			gridBagConstraints.gridx = 11;
			gridBagConstraints.gridwidth = 1;
			gridBagConstraints.weightx = 0.1;
			add(bonusCharLabel, gridBagConstraints);
		}

		if (otherBonusPanel) {
			otherBonus = new JLabel("Bns");
			otherBonus.setMinimumSize(new Dimension(columnWidth, columnHeight));
			otherBonus.setPreferredSize(new Dimension(columnWidth, columnHeight));
			otherBonus.setHorizontalAlignment(SwingConstants.CENTER);
			otherBonus.setFont(defaultFont);
			gridBagConstraints.gridx = 13;
			gridBagConstraints.gridwidth = 1;
			gridBagConstraints.weightx = 0.1;
			add(otherBonus, gridBagConstraints);
		}

		if (objectBonusPanel) {
			bonusMagicObject = new JLabel("Obj");
			bonusMagicObject.setFont(defaultFont);
			bonusMagicObject.setMinimumSize(new Dimension(columnWidth, columnHeight));
			bonusMagicObject.setPreferredSize(new Dimension(columnWidth, columnHeight));
			bonusMagicObject.setHorizontalAlignment(SwingConstants.CENTER);
			gridBagConstraints.gridx = 15;
			gridBagConstraints.gridwidth = 1;
			gridBagConstraints.weightx = 0.1;
			add(bonusMagicObject, gridBagConstraints);
		}

		if (totalPanel) {
			totalLabel = new JLabel("Tot");
			totalLabel.setMinimumSize(new Dimension(columnWidth, columnHeight));
			totalLabel.setPreferredSize(new Dimension(columnWidth, columnHeight));
			totalLabel.setHorizontalAlignment(SwingConstants.CENTER);
			totalLabel.setFont(defaultFont);
			gridBagConstraints.gridx = 17;
			gridBagConstraints.gridwidth = 1;
			gridBagConstraints.weightx = 0.1;
			add(totalLabel, gridBagConstraints);
		}

		int scrollBarSize = ((Integer) UIManager.get("ScrollBar.width")).intValue();
		JLabel scrollBarGap = new JLabel("");
		scrollBarGap.setMinimumSize(new Dimension(scrollBarSize, columnHeight));
		scrollBarGap.setPreferredSize(new Dimension(scrollBarSize, columnHeight));
		scrollBarGap.setHorizontalAlignment(SwingConstants.CENTER);
		scrollBarGap.setFont(defaultFont);
		gridBagConstraints.gridx = 19;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 0;
		add(scrollBarGap, gridBagConstraints);
	}

	public void sizeChanged() {
		if (this.getWidth() < 800) {
			if (oldRanksPanel) {
				prevRanksLabel.setText("Rng");
			}
			if (chooseRanksPanel) {
				currentRanksLabel.setText("Rng");
			}
			if (ranksValuePanel) {
				bonusRankLabel.setText("Val");
			}
			if (bonusCategoryPanel) {
				bonusCharLabel.setText("Cat");
			}
			if (objectBonusPanel) {
				bonusMagicObject.setText("Obj");
			}
			if (otherBonusPanel) {
				otherBonus.setText("Bns");
			}
			if (totalPanel) {
				totalLabel.setText("Tot");
			}
		} else {
			if (oldRanksPanel) {
				prevRanksLabel.setText("Rangos");
			}
			if (chooseRanksPanel) {
				currentRanksLabel.setText("Rangos");
			}
			if (ranksValuePanel) {
				bonusRankLabel.setText("Valor");
			}
			if (bonusCategoryPanel) {
				bonusCharLabel.setText("Car/Cat");
			}
			if (objectBonusPanel) {
				bonusMagicObject.setText("Objeto");
			}
			if (otherBonusPanel) {
				otherBonus.setText("Bonus");
			}
			if (totalPanel) {
				totalLabel.setText("Total");
			}
		}
	}
	
	protected void addColumn(JPanel panel, Integer column) {
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.CENTER;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.weighty = 0;
		gridBagConstraints.gridx = column * 2;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 0.1;
		add(panel, gridBagConstraints);
	}
}
