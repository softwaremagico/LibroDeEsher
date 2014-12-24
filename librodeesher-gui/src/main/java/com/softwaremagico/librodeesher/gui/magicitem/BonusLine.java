package com.softwaremagico.librodeesher.gui.magicitem;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.SwingConstants;

import com.softwaremagico.librodeesher.gui.elements.BoldListLabel;
import com.softwaremagico.librodeesher.gui.elements.ListBackgroundPanel;
import com.softwaremagico.librodeesher.gui.style.BaseLine;
import com.softwaremagico.librodeesher.pj.equipment.ObjectBonus;

public class BonusLine extends BaseLine {
	private static final long serialVersionUID = -6440213804132215064L;
	private BoldListLabel bonusNameLabel, bonusValue;
	private ObjectBonus bonus;

	public BonusLine(ObjectBonus bonus, Color background) {
		this.bonus = bonus;
		this.background = background;
		setElements();
		setBackground(background);
	}

	@Override
	public void update() {

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
		if (bonus != null) {
			bonusNameLabel = new BoldListLabel(bonus.getBonusName(), SwingConstants.LEFT, 200, columnHeight);
		} else {
			bonusNameLabel = new BoldListLabel("", SwingConstants.LEFT, 200, columnHeight);
		}
		add(new ListBackgroundPanel(bonusNameLabel, background), gridBagConstraints);

		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 0.3;
		if (bonus != null) {
			bonusValue = new BoldListLabel(bonus.getBonus() + "", 50, columnHeight);
		} else {
			bonusValue = new BoldListLabel("", 50, columnHeight);
		}
		add(new ListBackgroundPanel(bonusValue, background), gridBagConstraints);
	}

}
