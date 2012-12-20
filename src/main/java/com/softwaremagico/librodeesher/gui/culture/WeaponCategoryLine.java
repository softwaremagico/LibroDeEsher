package com.softwaremagico.librodeesher.gui.culture;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import com.softwaremagico.librodeesher.gui.style.BasicLine;
import com.softwaremagico.librodeesher.pj.categories.Category;

public class WeaponCategoryLine extends BasicLine {
	private static final long serialVersionUID = 4480268296161276440L;
	private Category weaponsCategory;
	private Integer ranks;

	public WeaponCategoryLine(Category weaponsCategory, Integer ranks, Color background) {
		this.weaponsCategory = weaponsCategory;
		this.ranks = ranks;
		setElements(background);
		setBackground(background);
	}

	protected void setElements(Color background) {
		this.removeAll();
		setLayout(new GridBagLayout());
		GridBagConstraints gridBagConstraints = new GridBagConstraints();

		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 0;


		JLabel weaponCategoryLabel = new JLabel(weaponsCategory.getName());
		weaponCategoryLabel.setFont(new Font(font, Font.BOLD, fontSize));
		add(createLabelInsidePanel(weaponCategoryLabel, SwingConstants.LEFT, background, fontColor), gridBagConstraints);

		gridBagConstraints.anchor = GridBagConstraints.CENTER;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 0;
		gridBagConstraints.gridx = 1;
		JLabel rankLabel = new JLabel("("+ranks.toString()+")");
		weaponCategoryLabel.setFont(new Font(font, Font.BOLD, fontSize));
		add(createLabelInsidePanel(rankLabel, SwingConstants.CENTER, background, fontColor), gridBagConstraints);

	}
}
