package com.softwaremagico.librodeesher.gui.skills;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JCheckBox;
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

	public SkillTitleLine(String titleLabelText, String rankLabelText) {
		setElements(background, titleLabelText, rankLabelText);
		setBackground(background);
	}

	protected void setElements(Color background, String labelText, String rankLabelText) {
		this.removeAll();
		setLayout(new GridBagLayout());
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		Font defaultFont = new Font(font, Font.BOLD, fontSize);

		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.weighty = 0;

		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 0.3;
		JLabel categoryNameLabel = new JLabel("Nombre");
		categoryNameLabel.setFont(defaultFont);
		categoryNameLabel.setHorizontalAlignment(SwingConstants.LEFT);
		categoryNameLabel.setMinimumSize(new Dimension(200, columnHeight));
		categoryNameLabel.setPreferredSize(new Dimension(200, columnHeight));
		add(categoryNameLabel, gridBagConstraints);

		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 0.1;
		JLabel rankCostLabel = new JLabel("Coste");
		rankCostLabel.setMinimumSize(new Dimension(columnWidth * 2, columnHeight));
		rankCostLabel.setPreferredSize(new Dimension(columnWidth * 2, columnHeight));
		rankCostLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(rankCostLabel, gridBagConstraints);

		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 0.1;
		prevRanksLabel = new JLabel("Rng");
		prevRanksLabel.setFont(defaultFont);
		prevRanksLabel.setMinimumSize(new Dimension(columnWidth, columnHeight));
		prevRanksLabel.setPreferredSize(new Dimension(columnWidth, columnHeight));
		prevRanksLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(prevRanksLabel, gridBagConstraints);

		gridBagConstraints.gridx = 3;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 0.1;
		currentRanksLabel = new JLabel("Rng");
		currentRanksLabel.setFont(defaultFont);
		currentRanksLabel.setMinimumSize(new Dimension(columnWidth * 2, columnHeight));
		currentRanksLabel.setPreferredSize(new Dimension(columnWidth * 2, columnHeight));
		currentRanksLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(currentRanksLabel, gridBagConstraints);

		bonusRankLabel = new JLabel("Val");
		bonusRankLabel.setFont(defaultFont);
		gridBagConstraints.gridx = 4;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 0.1;
		bonusRankLabel.setMinimumSize(new Dimension(columnWidth, columnHeight));
		bonusRankLabel.setPreferredSize(new Dimension(columnWidth, columnHeight));
		bonusRankLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(bonusRankLabel, gridBagConstraints);

		bonusCharLabel = new JLabel("Cat");
		bonusCharLabel.setFont(defaultFont);
		bonusCharLabel.setMinimumSize(new Dimension(columnWidth, columnHeight));
		bonusCharLabel.setPreferredSize(new Dimension(columnWidth, columnHeight));
		bonusCharLabel.setHorizontalAlignment(SwingConstants.CENTER);
		gridBagConstraints.gridx = 5;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 0.1;
		add(bonusCharLabel, gridBagConstraints);

		otherBonus = new JLabel("Bns");
		otherBonus.setMinimumSize(new Dimension(columnWidth, columnHeight));
		otherBonus.setPreferredSize(new Dimension(columnWidth, columnHeight));
		otherBonus.setHorizontalAlignment(SwingConstants.CENTER);
		otherBonus.setFont(defaultFont);
		gridBagConstraints.gridx = 6;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 0.1;
		add(otherBonus, gridBagConstraints);
		
		bonusMagicObject = new JLabel("Obj");
		bonusMagicObject.setFont(defaultFont);
		bonusMagicObject.setMinimumSize(new Dimension(columnWidth, columnHeight));
		bonusMagicObject.setPreferredSize(new Dimension(columnWidth, columnHeight));
		bonusMagicObject.setHorizontalAlignment(SwingConstants.CENTER);
		gridBagConstraints.gridx = 7;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 0.1;
		add(bonusMagicObject, gridBagConstraints);

		totalLabel = new JLabel("Tot");
		totalLabel.setMinimumSize(new Dimension(columnWidth, columnHeight));
		totalLabel.setPreferredSize(new Dimension(columnWidth, columnHeight));
		totalLabel.setHorizontalAlignment(SwingConstants.CENTER);
		totalLabel.setFont(defaultFont);
		gridBagConstraints.gridx = 8;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 0.1;
		add(totalLabel, gridBagConstraints);

		int scrollBarSize = ((Integer) UIManager.get("ScrollBar.width")).intValue();
		JLabel scrollBarGap = new JLabel("");
		scrollBarGap.setMinimumSize(new Dimension(scrollBarSize, columnHeight));
		scrollBarGap.setPreferredSize(new Dimension(scrollBarSize, columnHeight));
		scrollBarGap.setHorizontalAlignment(SwingConstants.CENTER);
		scrollBarGap.setFont(defaultFont);
		gridBagConstraints.gridx = 9;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 0;
		add(scrollBarGap, gridBagConstraints);
	}

	public void sizeChanged() {
		if (this.getWidth() < 800) {
			prevRanksLabel.setText("Rng");
			currentRanksLabel.setText("Rng");
			bonusRankLabel.setText("Val");
			bonusCharLabel.setText("Cat");
			bonusMagicObject.setText("Obj");
			otherBonus.setText("Bns");
			totalLabel.setText("Tot");
		} else {
			prevRanksLabel.setText("Rangos");
			currentRanksLabel.setText("Rangos");
			bonusRankLabel.setText("Valor");
			bonusCharLabel.setText("Car/Cat");
			bonusMagicObject.setText("Objeto");
			otherBonus.setText("Bonus");
			totalLabel.setText("Total");
		}
	}
}
