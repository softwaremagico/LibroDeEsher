package com.softwaremagico.librodeesher.gui.culture;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import com.softwaremagico.librodeesher.gui.elements.CloseButton;
import com.softwaremagico.librodeesher.gui.style.BaseFrame;
import com.softwaremagico.librodeesher.gui.style.BasicButton;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;

public class CultureWindow extends BaseFrame {
	private static final long serialVersionUID = -3866934730061829486L;
	private BasicButton acceptButton;
	private CharacterPlayer character;

	public CultureWindow(CharacterPlayer character) {
		this.character = character;
		defineWindow(500, 400);
		setResizable(false);
		setElements();
	}

	private void setElements() {
		setLayout(new GridBagLayout());
		GridBagConstraints gridBagConstraints = new GridBagConstraints();

		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		getContentPane().add(new CompleteWeaponPanel(character), gridBagConstraints);

		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		getContentPane().add(createChooseLanguagePanel(), gridBagConstraints);

		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 0;
		getContentPane().add(createChooseHobbiesPanel(), gridBagConstraints);

		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 0;
		getContentPane().add(createChooseSpellsPanel(), gridBagConstraints);

		JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
		acceptButton = new BasicButton("Aceptar");
		acceptButton.addActionListener(new AcceptListener());
		buttonPanel.add(acceptButton);

		CloseButton closeButton = new CloseButton(this);
		buttonPanel.add(closeButton);

		gridBagConstraints.anchor = GridBagConstraints.LINE_END;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 0.2;
		gridBagConstraints.weighty = 0;
		getContentPane().add(buttonPanel, gridBagConstraints);
	}

	private JScrollPane createChooseLanguagePanel() {
		JPanel languagePanel = new JPanel();
		languagePanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));

		JScrollPane scrollPanel = new JScrollPane(languagePanel,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPanel.setBorder(getBorder());
		scrollPanel.setMinimumSize(new Dimension(200, 0));
		return scrollPanel;
	}

	private JScrollPane createChooseHobbiesPanel() {
		JPanel hobbiesPanel = new JPanel();
		hobbiesPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));

		JScrollPane scrollPanel = new JScrollPane(hobbiesPanel,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPanel.setBorder(getBorder());
		scrollPanel.setMinimumSize(new Dimension(200, 0));
		return scrollPanel;
	}

	private JScrollPane createChooseSpellsPanel() {
		JPanel speellsPanel = new JPanel();
		speellsPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));

		JScrollPane scrollPanel = new JScrollPane(speellsPanel,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPanel.setBorder(getBorder());
		scrollPanel.setMinimumSize(new Dimension(200, 0));
		return scrollPanel;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	class AcceptListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

		}
	}

}
