package com.softwaremagico.librodeesher.gui.elements;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import com.softwaremagico.librodeesher.gui.style.BasePanel;

public class BaseScrollPanel extends BasePanel {
	private static final long serialVersionUID = 2731483312448464339L;
	private JScrollPane scrollPanel;
	private JPanel titlePanel;

	public BaseScrollPanel() {
		setElements();
	}

	protected void setElements() {
		this.removeAll();
		setLayout(new GridBagLayout());
		GridBagConstraints gridBagConstraints = new GridBagConstraints();

		titlePanel = new JPanel();
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 0;
		add(titlePanel, gridBagConstraints);

		scrollPanel = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPanel.setBorder(getBorder());
		scrollPanel.setBounds(0, 0, scrollPanel.getWidth(), scrollPanel.getHeight());
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridheight = GridBagConstraints.REMAINDER;
		gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		add(scrollPanel, gridBagConstraints);
	}

	public void addTitle(Component component) {
		GridBagConstraints titleGridBagConstraints = new GridBagConstraints();
		titleGridBagConstraints.fill = GridBagConstraints.BOTH;
		titleGridBagConstraints.ipadx = xPadding;
		titleGridBagConstraints.gridx = 0;
		titleGridBagConstraints.gridy = 0;
		titleGridBagConstraints.gridheight = 1;
		titleGridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
		titleGridBagConstraints.weightx = 1;
		titleGridBagConstraints.weighty = 0;
		titlePanel.setLayout(new GridBagLayout());
		titlePanel.add(component, titleGridBagConstraints);
	}

	public void addBody(Component component) {
		scrollPanel.setViewportView(component);
	}

}
