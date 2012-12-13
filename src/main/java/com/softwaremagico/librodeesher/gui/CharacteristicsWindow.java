package com.softwaremagico.librodeesher.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.softwaremagico.librodeesher.gui.elements.CloseButton;
import com.softwaremagico.librodeesher.gui.elements.CompleteCharacteristicPanel;
import com.softwaremagico.librodeesher.gui.style.BaseFrame;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;

public class CharacteristicsWindow extends BaseFrame {
	private static final long serialVersionUID = -2484205144800968016L;
	private CompleteCharacteristicPanel characteristicPanel;
	private JLabel spentPointsLabel;
	private CharacterPlayer character;

	protected CharacteristicsWindow() {
		defineWindow(500, 400);
		setResizable(false);
		setElements();
	}

	private void setElements() {
		setLayout(new GridBagLayout());
		GridBagConstraints gridBagConstraints = new GridBagConstraints();

		characteristicPanel = new CompleteCharacteristicPanel();
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.gridwidth = 3;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		gridBagConstraints.insets = new Insets(5, 5, 5, 5);
		getContentPane().add(characteristicPanel, gridBagConstraints);

		JPanel pointsPanel = new JPanel();
		spentPointsLabel = new JLabel();
		pointsPanel.add(spentPointsLabel);

		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 0;
		getContentPane().add(pointsPanel, gridBagConstraints);

		CloseButton closeButton = new CloseButton(this);
		gridBagConstraints.anchor = GridBagConstraints.LINE_END;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 0;
		gridBagConstraints.weighty = 0;
		getContentPane().add(closeButton, gridBagConstraints);
	}

	private String getSpentPointsText(Integer spentPoints, Integer totalPoints) {
		return "Puntos restantes: " + spentPoints + " de " + totalPoints;
	}

	public void setCharacter(CharacterPlayer character) {
		characteristicPanel.setCharacter(character, false);
		characteristicPanel.setParentWindow(this);
		spentPointsLabel.setText(getSpentPointsText(CharacterPlayer.CHARACTERISTICS_POINTS,
				CharacterPlayer.CHARACTERISTICS_POINTS));
		this.character = character;
	}

	@Override
	public void update() {
		spentPointsLabel.setText(getSpentPointsText(
				CharacterPlayer.CHARACTERISTICS_POINTS - character.getTemporalPointsSpent(),
				CharacterPlayer.CHARACTERISTICS_POINTS));
	}
}
