package com.softwaremagico.librodeesher.gui.history;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.softwaremagico.librodeesher.gui.elements.CloseButton;
import com.softwaremagico.librodeesher.gui.elements.PointsCounterTextField;
import com.softwaremagico.librodeesher.gui.style.BaseFrame;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;

public class HistoryWindow extends BaseFrame {
	private static final long serialVersionUID = -2770063842107842255L;
	private CharacterPlayer character;
	private HistorySkillPointsPanel skillPanel;
	private JLabel historyPointsLabel;
	private PointsCounterTextField historyPoints;

	public HistoryWindow(CharacterPlayer character) {
		this.character = character;
		defineWindow(500, 400);
		historyPoints = new PointsCounterTextField();
		// setResizable(false);
		setElements();
		setEvents();
	}
	
	private void setHistorialPointText() {
		historyPoints.setPoints(character.getRemainingHistorialPoints());
	}

	private void setElements() {
		setLayout(new GridBagLayout());
		GridBagConstraints gridBagConstraints = new GridBagConstraints();

		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.weightx = 0.8;
		gridBagConstraints.weighty = 1;
		gridBagConstraints.insets = new Insets(2, 2, 2, 2);
		skillPanel = new HistorySkillPointsPanel(character, this);
		getContentPane().add(skillPanel, gridBagConstraints);
		
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.weightx = 0.8;
		gridBagConstraints.weighty = 0;
		gridBagConstraints.insets = new Insets(2, 2, 2, 2);
		
		JPanel developmentPointsPanel = new JPanel();
		developmentPointsPanel.setLayout(new BoxLayout(developmentPointsPanel, BoxLayout.X_AXIS));
		historyPointsLabel = new JLabel("  Puntos de Historial: ");
		developmentPointsPanel.add(historyPointsLabel);

		historyPoints.setColumns(3);
		historyPoints.setEditable(false);
		historyPoints.setMaximumSize(new Dimension(60, 25));
		setHistorialPointText();
		developmentPointsPanel.add(historyPoints);		
		getContentPane().add(developmentPointsPanel, gridBagConstraints);
		
		JPanel buttonPanel = new JPanel(new GridLayout(1, 2));

		CloseButton closeButton = new CloseButton(this);
		buttonPanel.add(closeButton);

		gridBagConstraints.anchor = GridBagConstraints.LINE_END;
		gridBagConstraints.fill = GridBagConstraints.NONE;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
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
		setHistorialPointText();
	}
}
