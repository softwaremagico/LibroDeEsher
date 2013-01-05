package com.softwaremagico.librodeesher.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultFormatter;

import com.softwaremagico.librodeesher.config.Config;
import com.softwaremagico.librodeesher.core.Spanish;
import com.softwaremagico.librodeesher.gui.elements.CloseButton;
import com.softwaremagico.librodeesher.gui.style.BaseFrame;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;

public class OptionsWindow extends BaseFrame {
	private static final long serialVersionUID = -8015912539177057288L;
	private CharacterPlayer character;
	private JCheckBox fireArmsMenuItem, darkSpellsMenuItem, chiPowers, trainingOtherRealms;
	private boolean updatingState = false;
	private JSpinner categoryMax;

	public OptionsWindow(CharacterPlayer character) {
		this.character = character;
		defineWindow(500, 250);
		setElements();
		setCurrentCharacterConfig();
		setResizable(false);
	}

	private void setCurrentCharacterConfig() {
		updatingState = true;
		fireArmsMenuItem.setSelected(character.isFirearmsAllowed());
		darkSpellsMenuItem.setSelected(character.isDarkSpellsAsBasicListsAllowed());
		chiPowers.setSelected(character.isChiPowersAllowed());
		trainingOtherRealms.setSelected(character.isOtherRealmtrainingSpellsAllowed());
		updatingState = false;
	}

	private void setElements() {
		setLayout(new GridBagLayout());
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		CheckBoxListener checkboxListener = new CheckBoxListener();

		JPanel characterPanel = new JPanel();
		characterPanel.setLayout(new BoxLayout(characterPanel, BoxLayout.Y_AXIS));
		characterPanel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Personaje actual"));

		fireArmsMenuItem = new JCheckBox(Spanish.FIREARMS_CHECKBOX_TEXT);
		fireArmsMenuItem.addActionListener(checkboxListener);
		characterPanel.add(fireArmsMenuItem);

		darkSpellsMenuItem = new JCheckBox(Spanish.DARKSPELLS_CHECKBOX_TEXT);
		darkSpellsMenuItem.addActionListener(checkboxListener);
		characterPanel.add(darkSpellsMenuItem);

		chiPowers = new JCheckBox(Spanish.CHI_POWERS_TEXT);
		chiPowers.addActionListener(checkboxListener);
		characterPanel.add(chiPowers);

		trainingOtherRealms = new JCheckBox(Spanish.OTHER_TRAINING_SPELLS);
		trainingOtherRealms.addActionListener(checkboxListener);
		characterPanel.add(trainingOtherRealms);

		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.gridwidth = 3;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		gridBagConstraints.insets = new Insets(2, 2, 2, 2);
		getContentPane().add(characterPanel, gridBagConstraints);

		JPanel globalOptionsPanel = new JPanel();
		globalOptionsPanel.setLayout(new BoxLayout(globalOptionsPanel, BoxLayout.Y_AXIS));
		globalOptionsPanel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Interfaz Gráfica"));

		JPanel maxCategoryRanks = new JPanel();
		maxCategoryRanks.setLayout(new BoxLayout(maxCategoryRanks, BoxLayout.X_AXIS));
		maxCategoryRanks.setAlignmentX(LEFT_ALIGNMENT);

		JLabel maxCategoryLabel = new JLabel(" Ocultar categorías con un coste superior a: ");
		maxCategoryRanks.add(maxCategoryLabel);

		SpinnerModel sm;
		try {
			sm = new SpinnerNumberModel((int) Config.getCategoryMaxCost(), 5, 999, 1);
		} catch (IllegalArgumentException iae) {
			sm = new SpinnerNumberModel(50, 5, 999, 1);
		}
		categoryMax = new JSpinner(sm);
		addRankSpinnerEvent();
		categoryMax.setMaximumSize(new Dimension(70, 25));
		maxCategoryRanks.add(categoryMax, BorderLayout.PAGE_START);

		globalOptionsPanel.add(maxCategoryRanks);

		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.gridwidth = 3;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		gridBagConstraints.insets = new Insets(2, 2, 2, 2);
		getContentPane().add(globalOptionsPanel, gridBagConstraints);

		JPanel buttonPanel = new JPanel(new GridLayout(1, 4));
		buttonPanel.add(new JPanel());
		buttonPanel.add(new JPanel());
		buttonPanel.add(new JPanel());

		CloseButton closeButton = new CloseButton(this);
		buttonPanel.add(closeButton);

		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 0;
		gridBagConstraints.weighty = 0;
		gridBagConstraints.insets = new Insets(2, 2, 2, 2);
		getContentPane().add(buttonPanel, gridBagConstraints);

	}

	class CheckBoxListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (!updatingState) {
				if (e.getActionCommand().equals(Spanish.FIREARMS_CHECKBOX_TEXT)) {
					character.setFirearmsAllowed(fireArmsMenuItem.isSelected());
					Config.setFireArmsActivated(fireArmsMenuItem.isSelected());
				} else if (e.getActionCommand().equals(Spanish.DARKSPELLS_CHECKBOX_TEXT)) {
					character.setDarkSpellsAsBasicListsAllowed(darkSpellsMenuItem.isSelected());
					Config.setDarkSpellsAsBasic(darkSpellsMenuItem.isSelected());
				} else if (e.getActionCommand().equals(Spanish.CHI_POWERS_TEXT)) {
					character.setChiPowersAllowed(chiPowers.isSelected());
					Config.setChiPowersAllowed(chiPowers.isSelected());
				} else if (e.getActionCommand().equals(Spanish.OTHER_TRAINING_SPELLS)) {
					character.setOtherRealmtrainingSpellsAllowed(trainingOtherRealms.isSelected());
					Config.setOtherRealmtrainingSpells(trainingOtherRealms.isSelected());
				}
			}
		}
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	private void addRankSpinnerEvent() {
		JComponent comp = categoryMax.getEditor();
		JFormattedTextField field = (JFormattedTextField) comp.getComponent(0);
		DefaultFormatter formatter = (DefaultFormatter) field.getFormatter();
		formatter.setCommitsOnValidEdit(true);
		categoryMax.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				Config.setCategoryMaxCost((Integer) categoryMax.getValue());
			}
		});
	}

}
