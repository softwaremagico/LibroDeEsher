package com.softwaremagico.librodeesher.gui.skills;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.categories.Category;
import com.softwaremagico.librodeesher.pj.categories.CategoryCost;

public class WeaponCategoryLine extends CategoryLine {
	private static final long serialVersionUID = -4281133156537443212L;
	private WideComboBox<CategoryCost> costComboBox;
	private static final int COMBO_BOX_WIDTH = 55;
	private static final int COMBO_BOX_HEIGHT = 20;
	private Integer previousSelectedIndex = 0;
	private Integer weaponLineNumber;
	private boolean updatingWeaponCost = true;

	public WeaponCategoryLine(CharacterPlayer character, Category category, Color background,
			SkillPanel parentWindow, Integer weaponIndex) {
		super(character, category, background, parentWindow);
		this.background = background;
		this.weaponLineNumber = weaponIndex;
		previousSelectedIndex = weaponIndex;
		try {
			CategoryCost cost = character.getProfessionDecisions().getWeaponCost(category);
			if (cost != null) {
				costComboBox.setSelectedItem(cost);
			} else {
				costComboBox.setSelectedIndex(weaponIndex);
			}
		} catch (IllegalArgumentException iae) {

		}
		updateWeaponCost();
		enableRanks();
		updatingWeaponCost = false;
	}

	private void addItemsToComboBox() {
		updatingWeaponCost = true;
		for (CategoryCost cost : character.getProfession().getWeaponCategoryCost()) {
			costComboBox.addItem(cost);
		}
		updatingWeaponCost = false;
	}

	protected JPanel createCostPanel() {
		JPanel costPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 1, 1));
		costComboBox = new WideComboBox<>();
		costComboBox.setPreferredSize(new Dimension(COMBO_BOX_WIDTH, COMBO_BOX_HEIGHT));
		costComboBox.setBackground(background);
		costComboBox.addActionListener(new ComboBoxListener());

		DefaultListCellRenderer dlcr = new DefaultListCellRenderer();
		dlcr.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
		costComboBox.setRenderer(dlcr);

		addItemsToComboBox();
		costPanel.add(costComboBox);
		return costPanel;
	}

	protected Integer getSelectedIndex() {
		return costComboBox.getSelectedIndex();
	}

	protected void setSelectedIndex(Integer value) {
		costComboBox.setSelectedIndex(value);
	}

	private void updateWeaponCost() {
		character.getProfessionDecisions().setWeaponCost(category,
				(CategoryCost) costComboBox.getSelectedItem());
	}

	public class ComboBoxListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (!updatingWeaponCost) {
				if (weaponLineNumber != null) {
					parentWindow.updateWeaponsCost(costComboBox.getSelectedIndex(), previousSelectedIndex,
							weaponLineNumber);
				}
				previousSelectedIndex = costComboBox.getSelectedIndex();
				updateWeaponCost();
				enableRanks();
				parentWindow.update();
			}
		}
	}

	public class WideComboBox<E> extends JComboBox<E> {
		private static final long serialVersionUID = -8969953615732925312L;

		public WideComboBox() {
		}

		public WideComboBox(ComboBoxModel<E> aModel) {
			super(aModel);
		}

		public WideComboBox(final E[] items) {
			super(items);
		}

		public WideComboBox(Vector<E> items) {
			super(items);
		}

		public void doLayout() {
			try {
				super.doLayout();
			} finally {

			}
		}

		public Dimension getSize() {
			Dimension dim = new Dimension((int) (COMBO_BOX_WIDTH * 1.2), COMBO_BOX_HEIGHT);
			return dim;
		}
	}
}