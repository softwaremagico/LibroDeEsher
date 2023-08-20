package com.softwaremagico.librodeesher.gui;

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
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultFormatter;

import com.softwaremagico.files.RolemasterFolderStructure;
import com.softwaremagico.librodeesher.basics.Spanish;
import com.softwaremagico.librodeesher.config.Config;
import com.softwaremagico.librodeesher.gui.elements.BaseLabel;
import com.softwaremagico.librodeesher.gui.elements.BaseSpinner;
import com.softwaremagico.librodeesher.gui.elements.CloseButton;
import com.softwaremagico.librodeesher.gui.style.BaseFrame;
import com.softwaremagico.librodeesher.gui.style.Fonts;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;

public class OptionsWindow extends BaseFrame {
	private static final long serialVersionUID = -8015912539177057288L;
	private CharacterPlayer character;
	private JCheckBox fireArmsMenuItem, darkSpellsMenuItem, chiPowers, trainingOtherRealms, perkHistoryPoints, disabledCheckBox, magicDisabled;
	private JCheckBox handwrittingFont, sortedSkills;
	private boolean updatingState = false;
	private BaseSpinner categoryMax;
	private JComboBox<String> modulesComboBox;

	public OptionsWindow(CharacterPlayer character) {
		this.character = character;
		defineWindow(500, 430);
		setElements();
		setCurrentCharacterConfig();
		setResizable(false);
	}

	private void setCurrentCharacterConfig() {
		updatingState = true;
		fireArmsMenuItem.setSelected(character.isFirearmsAllowed());
		darkSpellsMenuItem.setSelected(character.isDarkSpellsAsBasicListsAllowed());
		chiPowers.setSelected(character.isChiPowersAllowed());
		trainingOtherRealms.setSelected(character.isOtherRealmTrainingSpellsAllowed());
		perkHistoryPoints.setSelected(character.isPerksCostBackgroundPoints());
		handwrittingFont.setSelected(character.isHandWrittingFont());
		sortedSkills.setSelected(character.isSortPdfSkills());
		magicDisabled.setSelected(!character.isMagicAllowed());
		updatingState = false;
	}

	private void setElements() {
		setLayout(new GridBagLayout());
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		CheckBoxCharacterListener checkboxListener = new CheckBoxCharacterListener();

		JPanel characterPanel = new JPanel();
		characterPanel.setLayout(new BoxLayout(characterPanel, BoxLayout.Y_AXIS));
		characterPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Personaje actual"));

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

		perkHistoryPoints = new JCheckBox(Spanish.ENABLE_PERK_BACKGROUND_COST);
		perkHistoryPoints.addActionListener(checkboxListener);
		characterPanel.add(perkHistoryPoints);

		magicDisabled = new JCheckBox(Spanish.DISABLE_MAGIC);
		magicDisabled.addActionListener(checkboxListener);
		characterPanel.add(magicDisabled);

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

		JPanel pdfOptionsPanel = new JPanel();
		pdfOptionsPanel.setLayout(new BoxLayout(pdfOptionsPanel, BoxLayout.Y_AXIS));
		pdfOptionsPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Fichas en PDF"));

		handwrittingFont = new JCheckBox(Spanish.HANDWRITTING_TEXT);
		handwrittingFont.addActionListener(checkboxListener);
		pdfOptionsPanel.add(handwrittingFont);

		sortedSkills = new JCheckBox(Spanish.PDF_SORT_SKILLS);
		sortedSkills.addActionListener(checkboxListener);
		pdfOptionsPanel.add(sortedSkills);
		gridBagConstraints.gridy = 1;
		getContentPane().add(pdfOptionsPanel, gridBagConstraints);

		JPanel graphicOptionsPanel = new JPanel();
		graphicOptionsPanel.setLayout(new BoxLayout(graphicOptionsPanel, BoxLayout.Y_AXIS));
		graphicOptionsPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Interfaz Gráfica"));

		JPanel maxCategoryRanks = new JPanel();
		maxCategoryRanks.setLayout(new BoxLayout(maxCategoryRanks, BoxLayout.X_AXIS));
		maxCategoryRanks.setAlignmentX(LEFT_ALIGNMENT);

		BaseLabel maxCategoryLabel = new BaseLabel(" Ocultar categorías con un coste superior a: ");
		maxCategoryLabel.setFont(Fonts.getInstance().getDefaultFont());
		maxCategoryRanks.add(maxCategoryLabel);

		SpinnerModel sm;
		try {
			sm = new SpinnerNumberModel((int) Config.getCategoryMaxCost(), 5, 999, 1);
		} catch (IllegalArgumentException iae) {
			sm = new SpinnerNumberModel(50, 5, 999, 1);
		}
		categoryMax = new BaseSpinner(sm);
		addRankSpinnerEvent();
		categoryMax.setMaximumSize(new Dimension(70, 25));
		maxCategoryRanks.add(categoryMax, BorderLayout.PAGE_START);
		graphicOptionsPanel.add(maxCategoryRanks);
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.gridwidth = 3;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		gridBagConstraints.insets = new Insets(2, 2, 2, 2);
		getContentPane().add(graphicOptionsPanel, gridBagConstraints);

		JPanel globalPanel = createModulesPanel();
		globalPanel.setLayout(new BoxLayout(globalPanel, BoxLayout.X_AXIS));
		globalPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Opciones Globales"));
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.gridwidth = 3;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		gridBagConstraints.insets = new Insets(2, 2, 2, 2);
		getContentPane().add(globalPanel, gridBagConstraints);

		JPanel buttonPanel = new JPanel(new GridLayout(1, 4));
		buttonPanel.add(new JPanel());
		buttonPanel.add(new JPanel());
		buttonPanel.add(new JPanel());

		CloseButton closeButton = new CloseButton(this);
		buttonPanel.add(closeButton);

		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 0;
		gridBagConstraints.weighty = 0;
		gridBagConstraints.insets = new Insets(2, 2, 2, 2);
		getContentPane().add(buttonPanel, gridBagConstraints);

	}

	private JPanel createModulesPanel() {
		JPanel modulesPanel = new JPanel();
		modulesComboBox = createModulesComboBox();
		modulesComboBox.addActionListener(new ModulesComboBoxAction());
		modulesPanel.add(modulesComboBox);
		disabledCheckBox = new JCheckBox("Deshabilitar");
		disabledCheckBox.addActionListener(new CheckBoxDisableModulesListener());
		modulesPanel.add(disabledCheckBox);
		updateModulesCheckBox();
		return modulesPanel;
	}

	private JComboBox<String> createModulesComboBox() {
		updatingState = true;
		JComboBox<String> modulesComboBox = new JComboBox<String>();
		for (int i = 0; i < RolemasterFolderStructure.getAllModules().size(); i++) {
			modulesComboBox.addItem(RolemasterFolderStructure.getAllModules().get(i));
		}
		updatingState = false;
		return modulesComboBox;
	}

	class CheckBoxCharacterListener implements ActionListener {

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
					character.setOtherRealmTrainingSpellsAllowed(trainingOtherRealms.isSelected());
					Config.setOtherRealmTrainingSpells(trainingOtherRealms.isSelected());
				} else if (e.getActionCommand().equals(Spanish.ENABLE_PERK_BACKGROUND_COST)) {
					character.setPerksCostBackgroundPoints(perkHistoryPoints.isSelected());
					Config.setPerksCostHistoryPoints(perkHistoryPoints.isSelected());
				} else if (e.getActionCommand().equals(Spanish.HANDWRITTING_TEXT)) {
					character.setHandWrittingFont(handwrittingFont.isSelected());
					Config.setHandWrittingFont(handwrittingFont.isSelected());
				} else if (e.getActionCommand().equals(Spanish.PDF_SORT_SKILLS)) {
					character.setSortPdfSkills(sortedSkills.isSelected());
					Config.setPdfSortSkills(sortedSkills.isSelected());
				} else if (e.getActionCommand().equals(Spanish.DISABLE_MAGIC)) {
					character.setMagicAllowed(!magicDisabled.isSelected());
					Config.setMagicdisabled(magicDisabled.isSelected());
				}
				ShowMessage.showInfoMessage("Recuerda reiniciar la aplicación para que está opción tenga efecto.", "Opciones");
				Config.storeConfiguration();
			}
		}
	}

	class CheckBoxDisableModulesListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (disabledCheckBox != null && modulesComboBox != null && disabledCheckBox.isSelected()) {
				if (!RolemasterFolderStructure.getDisabledModules().contains(modulesComboBox.getSelectedItem().toString())) {
					RolemasterFolderStructure.getDisabledModules().add(modulesComboBox.getSelectedItem().toString());
				}
			} else {
				RolemasterFolderStructure.removeDisabledModule(modulesComboBox.getSelectedItem().toString());
			}
			ShowMessage.showInfoMessage("Recuerda reiniciar la aplicación para que está opción tenga efecto.", "Opciones");
			Config.storeConfiguration();
		}
	}

	private void updateModulesCheckBox() {
		if (RolemasterFolderStructure.getDisabledModules().contains(modulesComboBox.getSelectedItem())) {
			disabledCheckBox.setSelected(true);
		} else {
			disabledCheckBox.setSelected(false);
		}
	}

	class ModulesComboBoxAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent evt) {
			if (!updatingState) {
				updateModulesCheckBox();
			}
		}
	}

	@Override
	public void updateFrame() {
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
