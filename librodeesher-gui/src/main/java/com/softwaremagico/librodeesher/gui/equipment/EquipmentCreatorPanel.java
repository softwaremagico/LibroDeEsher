package com.softwaremagico.librodeesher.gui.equipment;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import com.softwaremagico.librodeesher.gui.elements.BaseTextField;
import com.softwaremagico.librodeesher.gui.style.BaseButton;
import com.softwaremagico.librodeesher.gui.style.BasePanel;
import com.softwaremagico.librodeesher.gui.style.BaseTextArea;
import com.softwaremagico.librodeesher.gui.style.Fonts;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.equipment.Equipment;

public class EquipmentCreatorPanel extends BasePanel {
	private static final long serialVersionUID = 3918970780650154788L;
	private BaseTextField nameField;
	private BaseTextArea descriptionField;
	private CharacterPlayer characterPlayer;
	private Set<EquipmentChangedListener> equipmentChangedListeners;

	public EquipmentCreatorPanel(CharacterPlayer characterPlayer) {
		equipmentChangedListeners = new HashSet<>();
		this.characterPlayer = characterPlayer;
		setElements();
	}

	@Override
	public void update() {

	}

	private void setElements() {
		setLayout(new GridBagLayout());
		GridBagConstraints gridBagConstraints = new GridBagConstraints();

		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.gridwidth = 3;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 0;
		gridBagConstraints.insets = new Insets(5, 5, 5, 5);

		JLabel nameLabel = new JLabel("Nombre:");
		nameLabel.setFont(Fonts.getInstance().getBoldFont());
		gridBagConstraints.gridy = 1;
		add(nameLabel, gridBagConstraints);

		nameField = new BaseTextField();
		gridBagConstraints.gridy = 2;
		nameField.setHorizontalAlignment(JTextField.LEFT);
		add(nameField, gridBagConstraints);

		gridBagConstraints.gridy = 3;
		JLabel descriptionLabel = new JLabel("Descripción:");
		descriptionLabel.setFont(Fonts.getInstance().getBoldFont());
		add(descriptionLabel, gridBagConstraints);

		descriptionField = new BaseTextArea();
		descriptionField.setCaretPosition(0);
		JScrollPane textScrollPanel = new JScrollPane(descriptionField, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.gridheight = 3;
		gridBagConstraints.weighty = 1;
		gridBagConstraints.insets = new Insets(5, 5, 5, 5);
		add(textScrollPanel, gridBagConstraints);

		BaseButton addButton = new BaseButton("Añadir");
		addButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (nameField.getText().trim().length() > 0) {
					Equipment equipment = new Equipment(nameField.getText().trim(), descriptionField.getText().trim());
					characterPlayer.addStandardEquipment(equipment);
					for (EquipmentChangedListener listener : equipmentChangedListeners) {
						listener.changed(equipment);
					}
					nameField.setText("");
					descriptionField.setText("");
				}
			}
		});
		JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
		buttonPanel.add(addButton);
		gridBagConstraints.anchor = GridBagConstraints.LINE_END;
		gridBagConstraints.fill = GridBagConstraints.NONE;
		gridBagConstraints.gridy = 7;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.weighty = 0;
		add(buttonPanel, gridBagConstraints);
	}

	public void addEquipmentChangedListener(EquipmentChangedListener listener) {
		equipmentChangedListeners.add(listener);
	}

}
