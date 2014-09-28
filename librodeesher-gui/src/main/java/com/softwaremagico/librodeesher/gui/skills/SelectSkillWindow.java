package com.softwaremagico.librodeesher.gui.skills;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

import com.softwaremagico.librodeesher.gui.elements.CloseButton;
import com.softwaremagico.librodeesher.gui.style.BaseFrame;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;

public class SelectSkillWindow extends BaseFrame {
	private static final long serialVersionUID = 4792683000547964467L;
	private CharacterPlayer character;
	private List<String> skillsToChoose;
	private Integer heightCells;
	private List<JCheckBox> checkBoxes = new ArrayList<>();

	public SelectSkillWindow(CharacterPlayer character, List<String> skillsToChoose) {
		this.skillsToChoose = skillsToChoose;
		this.character = character;
		defineSize();
		setElements();
	}

	private void defineSize() {
		heightCells = skillsToChoose.size();
		defineWindow(250, 50 * heightCells + 200);
	}

	private void setElements() {
		setLayout(new GridBagLayout());
		GridBagConstraints gridBagConstraints = new GridBagConstraints();

		// gridBagConstraints.anchor = GridBagConstraints.LINE_END;
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		gridBagConstraints.insets = new Insets(2, 2, 2, 2);
		// Add common options.
		JPanel skillsOptions = new JPanel(new GridLayout(0, 1));
		for (int c = 0; c < skillsToChoose.size(); c++) {
			JCheckBox skillOption = new JCheckBox(skillsToChoose.get(0));
			checkBoxes.add(skillOption);
			skillOption.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

				}
			});
			skillsOptions.add(skillOption);
		}

		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		getContentPane().add(skillsOptions, gridBagConstraints);

		JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
		CloseButton closeButton = new CloseButton(this);
		buttonPanel.add(closeButton);
		gridBagConstraints.anchor = GridBagConstraints.LINE_END;
		gridBagConstraints.fill = GridBagConstraints.NONE;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 0;
		gridBagConstraints.insets = new Insets(2, 2, 2, 2);
		getContentPane().add(buttonPanel, gridBagConstraints);
	}

	@Override
	public void updateFrame() {
		
	}
}
