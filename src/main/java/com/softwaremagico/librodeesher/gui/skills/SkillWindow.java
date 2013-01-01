package com.softwaremagico.librodeesher.gui.skills;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.softwaremagico.librodeesher.gui.elements.CloseButton;
import com.softwaremagico.librodeesher.gui.elements.DevelopmentTextField;
import com.softwaremagico.librodeesher.gui.style.BaseFrame;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;

public class SkillWindow extends BaseFrame {
	private static final long serialVersionUID = 3505731416535837471L;
	private CharacterPlayer character;
	private CompleteSkillPanel skillPanel;
	private JLabel pointsLabel;
	private DevelopmentTextField developmentPoints;

	public SkillWindow(CharacterPlayer character) {
		this.character = character;
		defineWindow(750, 450);
		// setResizable(false);
		setElements();
		setEvents();
	}

	private void setDevelopmentPointText() {
		developmentPoints.setDevelopmentPoints(character.getRemainingDevelopmentPoints());
	}

	private void setElements() {
		setLayout(new GridBagLayout());
		GridBagConstraints gridBagConstraints = new GridBagConstraints();

		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.gridwidth = 3;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		gridBagConstraints.insets = new Insets(2, 2, 2, 2);
		skillPanel = new CompleteSkillPanel(character, this);
		getContentPane().add(skillPanel, gridBagConstraints);

		JPanel developmentPointsPanel = new JPanel();
		developmentPointsPanel.setLayout(new BoxLayout(developmentPointsPanel, BoxLayout.X_AXIS));
		pointsLabel = new JLabel("  Puntos de Desarrollo restantes: ");
		developmentPointsPanel.add(pointsLabel);

		developmentPoints = new DevelopmentTextField();
		developmentPoints.setColumns(3);
		developmentPoints.setEditable(false);
		developmentPoints.setMaximumSize(new Dimension(60,25));
		setDevelopmentPointText();
		developmentPointsPanel.add(developmentPoints);

		gridBagConstraints.anchor = GridBagConstraints.CENTER;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 0;
		gridBagConstraints.insets = new Insets(2, 2, 2, 2);
		getContentPane().add(developmentPointsPanel, gridBagConstraints);
		setDevelopmentPointText();

		JPanel buttonPanel = new JPanel(new GridLayout(1, 2));

		CloseButton closeButton = new CloseButton(this);
		buttonPanel.add(closeButton);

		gridBagConstraints.anchor = GridBagConstraints.LINE_END;
		gridBagConstraints.fill = GridBagConstraints.NONE;
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

	private void setEvents() {
		addComponentListener(new ComponentListener() {
			@Override
			public void componentHidden(ComponentEvent e) {

			}

			@Override
			public void componentMoved(ComponentEvent e) {

			}

			@Override
			public void componentResized(ComponentEvent evt) {
				skillPanel.sizeChanged();
			}

			@Override
			public void componentShown(ComponentEvent e) {

			}
		});
	}

	@Override
	public void update() {
		setDevelopmentPointText();
	}

	class AcceptListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

		}
	}

}
