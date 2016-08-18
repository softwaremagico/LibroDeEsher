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

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import com.softwaremagico.librodeesher.gui.style.BaseTitleLine;

public class SkillTitleLine extends BaseTitleLine {
	private static final long serialVersionUID = 4480268296161276440L;
	private static final Integer columnWidth = 30;
	private static final Integer columnHeight = 20;
	private TitleLabel prevRanksLabel, insertedRanksLabel, currentRanksLabel, totalRanksLabel, bonusRankLabel,
			bonusCharLabel, bonusMagicObject, otherBonus, totalLabel;
	protected boolean costPanel = false;
	protected boolean oldRanksPanel = false;
	protected boolean insertedRanksPanel = false;
	protected boolean chooseRanksPanel = false;
	protected boolean totalRanksPanel = false;
	protected boolean ranksValuePanel = false;
	protected boolean bonusCategoryPanel = false;
	protected boolean otherBonusPanel = false;
	protected boolean objectBonusPanel = false;
	protected boolean totalPanel = false;

	public SkillTitleLine() {
		setBackground(title_background);
	}

	public void enableColumns(boolean costPanel, boolean oldRanksPanel, boolean insertedRanksPanel,
			boolean chooseRanksPanel, boolean totalRanksPanel, boolean ranksValuePanel, boolean bonusCategoryPanel,
			boolean otherBonusPanel, boolean objectBonusPanel, boolean totalPanel) {
		this.costPanel = costPanel;
		this.oldRanksPanel = oldRanksPanel;
		this.insertedRanksPanel = insertedRanksPanel;
		this.chooseRanksPanel = chooseRanksPanel;
		this.totalRanksPanel = totalRanksPanel;
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

		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.weighty = 0;

		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 0.3;
		TitleLabel categoryNameLabel = new TitleLabel("Nombre", SwingConstants.LEFT, 200, columnHeight);
		add(new TitleBackgroundPanel(categoryNameLabel), gridBagConstraints);

		if (costPanel) {
			gridBagConstraints.gridx = 3;
			gridBagConstraints.gridwidth = 1;
			gridBagConstraints.weightx = 0.1;
			TitleLabel rankCostLabel = new TitleLabel("Coste", columnWidth * 2, columnHeight);
			add(new TitleBackgroundPanel(rankCostLabel), gridBagConstraints);
		}

		if (oldRanksPanel) {
			gridBagConstraints.gridx = 5;
			gridBagConstraints.gridwidth = 1;
			gridBagConstraints.weightx = 0.1;
			prevRanksLabel = new TitleLabel("Rng", columnWidth, columnHeight);
			add(new TitleBackgroundPanel(prevRanksLabel), gridBagConstraints);
		}

		if (insertedRanksPanel) {
			gridBagConstraints.gridx = 7;
			gridBagConstraints.gridwidth = 1;
			gridBagConstraints.weightx = 0.1;
			insertedRanksLabel = new TitleLabel("Ins", columnWidth, columnHeight);
			add(new TitleBackgroundPanel(insertedRanksLabel), gridBagConstraints);
		}

		if (chooseRanksPanel) {
			gridBagConstraints.gridx = 9;
			gridBagConstraints.gridwidth = 1;
			gridBagConstraints.weightx = 0.1;
			currentRanksLabel = new TitleLabel("Rng", columnWidth * 2, columnHeight);
			add(new TitleBackgroundPanel(currentRanksLabel), gridBagConstraints);
		}

		if (totalRanksPanel) {
			gridBagConstraints.gridx = 11;
			gridBagConstraints.gridwidth = 1;
			gridBagConstraints.weightx = 0.1;
			totalRanksLabel = new TitleLabel("TR", columnWidth, columnHeight);
			add(new TitleBackgroundPanel(totalRanksLabel), gridBagConstraints);
		}

		if (ranksValuePanel) {
			gridBagConstraints.gridx = 13;
			gridBagConstraints.gridwidth = 1;
			gridBagConstraints.weightx = 0.1;
			bonusRankLabel = new TitleLabel("Val", columnWidth, columnHeight);
			add(new TitleBackgroundPanel(bonusRankLabel), gridBagConstraints);
		}

		if (bonusCategoryPanel) {
			gridBagConstraints.gridx = 15;
			gridBagConstraints.gridwidth = 1;
			gridBagConstraints.weightx = 0.1;
			bonusCharLabel = new TitleLabel("Cat", columnWidth, columnHeight);
			add(new TitleBackgroundPanel(bonusCharLabel), gridBagConstraints);
		}

		if (otherBonusPanel) {
			gridBagConstraints.gridx = 17;
			gridBagConstraints.gridwidth = 1;
			gridBagConstraints.weightx = 0.1;
			otherBonus = new TitleLabel("Bns", columnWidth, columnHeight);
			add(new TitleBackgroundPanel(otherBonus), gridBagConstraints);
		}

		if (objectBonusPanel) {
			gridBagConstraints.gridx = 19;
			gridBagConstraints.gridwidth = 1;
			gridBagConstraints.weightx = 0.1;
			bonusMagicObject = new TitleLabel("Obj", columnWidth, columnHeight);
			add(new TitleBackgroundPanel(bonusMagicObject), gridBagConstraints);
		}

		if (totalPanel) {
			gridBagConstraints.gridx = 21;
			gridBagConstraints.gridwidth = 1;
			gridBagConstraints.weightx = 0.1;
			totalLabel = new TitleLabel("Tot", columnWidth, columnHeight);
			add(new TitleBackgroundPanel(totalLabel), gridBagConstraints);
		}

		gridBagConstraints.gridx = 23;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 0;
		int scrollBarSize = ((Integer) UIManager.get("ScrollBar.width")).intValue();
		BaseLabel scrollBarGap = new BaseLabel("");
		scrollBarGap.setMinimumSize(new Dimension(scrollBarSize, columnHeight));
		scrollBarGap.setPreferredSize(new Dimension(scrollBarSize, columnHeight));
		scrollBarGap.setHorizontalAlignment(SwingConstants.CENTER);
		add(scrollBarGap, gridBagConstraints);
	}

	public void sizeChanged() {
		defaultElementsSizeChanged();
	}

	public void defaultElementsSizeChanged() {
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
		panel.setBackground(title_background);
		add(panel, gridBagConstraints);
	}

	protected void addColumn(TitleLabel label, Integer column) {
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.CENTER;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.weighty = 0;
		gridBagConstraints.gridx = column * 2;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 0.1;
		add(label, gridBagConstraints);
	}
}
