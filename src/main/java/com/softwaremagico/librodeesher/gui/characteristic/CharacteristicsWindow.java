package com.softwaremagico.librodeesher.gui.characteristic;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.softwaremagico.librodeesher.gui.elements.CloseButton;
import com.softwaremagico.librodeesher.gui.elements.DevelopmentTextField;
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
	private DevelopmentTextField characteristicsPointsTextField;

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

		JPanel characteristicPointsPanel = new JPanel();
		characteristicPointsPanel.setLayout(new BoxLayout(characteristicPointsPanel, BoxLayout.X_AXIS));

		spentPointsLabel = new JLabel();
		spentPointsLabel = new JLabel("  Puntos restantes: ");
		characteristicPointsPanel.add(spentPointsLabel);

		characteristicsPointsTextField = new DevelopmentTextField();
		characteristicsPointsTextField.setColumns(3);
		characteristicsPointsTextField.setEditable(false);
		characteristicsPointsTextField.setMaximumSize(new Dimension(60, 25));
		setRemainingPoints(0);
		characteristicPointsPanel.add(characteristicsPointsTextField);

		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 0.8;
		gridBagConstraints.weighty = 0;
		getContentPane().add(characteristicPointsPanel, gridBagConstraints);

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

	private void setRemainingPoints(Integer value) {
		characteristicsPointsTextField.setDevelopmentPoints(value);
	}

	private String getSpentPointsText(Integer spentPoints, Integer totalPoints) {
		return "  Puntos restantes: " + spentPoints + " de " + totalPoints;
	}

	public void setCharacter(CharacterPlayer character) {
		this.character = character;
		characteristicPanel.setCharacter(character, false);
		characteristicPanel.setParentWindow(this);
		setRemainingPoints(Characteristics.TOTAL_CHARACTERISTICS_POINTS
				- character.getCharacteristicsTemporalPointsSpent());
		acceptButton.setEnabled(!character.areCharacteristicsConfirmed());
		setPotential();
	}

	@Override
	public void update() {
		setRemainingPoints(Characteristics.TOTAL_CHARACTERISTICS_POINTS
				- character.getCharacteristicsTemporalPointsSpent());
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
