package com.softwaremagico.librodeesher.gui.magicitem;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.SwingConstants;

import com.softwaremagico.librodeesher.gui.elements.TitleBackgroundPanel;
import com.softwaremagico.librodeesher.gui.elements.TitleLabel;
import com.softwaremagico.librodeesher.gui.style.BaseTitleLine;

public class BonusObjectTitle extends BaseTitleLine {
	private static final long serialVersionUID = -6194140531984171426L;

	public BonusObjectTitle() {
		setBackground(background);
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

		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.weightx = 0.6;
		TitleLabel categoryNameLabel = new TitleLabel("Bonus", SwingConstants.LEFT, 200, columnHeight);
		add(new TitleBackgroundPanel(categoryNameLabel), gridBagConstraints);

		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 0.3;
		TitleLabel bonusValue = new TitleLabel("Valor", 50, columnHeight);
		add(new TitleBackgroundPanel(bonusValue), gridBagConstraints);
	}

}
