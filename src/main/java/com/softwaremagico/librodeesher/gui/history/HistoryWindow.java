package com.softwaremagico.librodeesher.gui.history;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import com.softwaremagico.librodeesher.gui.skills.CompleteSkillPanel;
import com.softwaremagico.librodeesher.gui.style.BaseFrame;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;

public class HistoryWindow extends BaseFrame {
	private static final long serialVersionUID = -2770063842107842255L;
	private CharacterPlayer character;
	private HistorySkillPointsPanel skillPanel;

	public HistoryWindow(CharacterPlayer character) {
		this.character = character;
		defineWindow(500, 400);
		// setResizable(false);
		setElements();
		setEvents();
	}

	private void setElements() {
		setLayout(new GridBagLayout());
		GridBagConstraints gridBagConstraints = new GridBagConstraints();

		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.gridheight = 3;
		gridBagConstraints.weightx = 0.8;
		gridBagConstraints.weighty = 1;
		gridBagConstraints.insets = new Insets(2, 2, 2, 2);
		skillPanel = new HistorySkillPointsPanel(character, this);
		getContentPane().add(skillPanel, gridBagConstraints);
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

	}
}
