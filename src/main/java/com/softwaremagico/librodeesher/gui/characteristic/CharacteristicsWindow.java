package com.softwaremagico.librodeesher.gui.characteristic;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.softwaremagico.librodeesher.gui.elements.CloseButton;
import com.softwaremagico.librodeesher.gui.style.BaseFrame;
import com.softwaremagico.librodeesher.gui.style.BasicButton;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.characteristic.Characteristics;

public class CharacteristicsWindow extends BaseFrame {
	private static final long serialVersionUID = -2484205144800968016L;
	private CompleteCharacteristicPanel characteristicPanel;
	private JLabel spentPointsLabel;
	private CharacterPlayer character;
	private BasicButton acceptButton;

	public CharacteristicsWindow() {
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
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 0.8;
		gridBagConstraints.weighty = 0;
		getContentPane().add(pointsPanel, gridBagConstraints);

		JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
		acceptButton = new BasicButton("Aceptar");
		acceptButton.addActionListener(new AcceptListener());
		buttonPanel.add(acceptButton);

		CloseButton closeButton = new CloseButton(this);
		buttonPanel.add(closeButton);

		gridBagConstraints.anchor = GridBagConstraints.LINE_END;
		gridBagConstraints.fill = GridBagConstraints.NONE;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 0.2;
		gridBagConstraints.weighty = 0;
		getContentPane().add(buttonPanel, gridBagConstraints);
	}

	private String getSpentPointsText(Integer spentPoints, Integer totalPoints) {
		return "Puntos restantes: " + spentPoints + " de " + totalPoints;
	}

	public void setCharacter(CharacterPlayer character) {
		this.character = character;
		characteristicPanel.setCharacter(character, false);
		characteristicPanel.setParentWindow(this);
		spentPointsLabel.setText(getSpentPointsText(
				Characteristics.TOTAL_CHARACTERISTICS_POINTS - character.getTemporalPointsSpent(),
				Characteristics.TOTAL_CHARACTERISTICS_POINTS));
		acceptButton.setEnabled(!character.areCharacteristicsConfirmed());
		setPotential();
	}

	@Override
	public void update() {
		spentPointsLabel.setText(getSpentPointsText(
				Characteristics.TOTAL_CHARACTERISTICS_POINTS - character.getTemporalPointsSpent(),
				Characteristics.TOTAL_CHARACTERISTICS_POINTS));
		acceptButton.setEnabled(!character.areCharacteristicsConfirmed());
	}

	public void setPotential() {
		characteristicPanel.setPotential();
	}

	class AcceptListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			character.setCharacteristicsAsConfirmed();
			acceptButton.setEnabled(!character.areCharacteristicsConfirmed());
			setPotential();
		}
	}
}
