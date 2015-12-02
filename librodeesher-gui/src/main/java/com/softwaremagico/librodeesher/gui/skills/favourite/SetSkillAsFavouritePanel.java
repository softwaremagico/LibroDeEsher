package com.softwaremagico.librodeesher.gui.skills.favourite;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import com.softwaremagico.librodeesher.gui.elements.BaseCheckBox;
import com.softwaremagico.librodeesher.gui.style.BasePanel;

public class SetSkillAsFavouritePanel extends BasePanel {
	private static final long serialVersionUID = -2805487044197535337L;
	private BaseCheckBox favouriteCheckBox;

	protected SetSkillAsFavouritePanel() {
		setElements();
	}

	private void setElements() {
		setLayout(new BorderLayout());
		favouriteCheckBox = new BaseCheckBox("Favorita");
		add(favouriteCheckBox, BorderLayout.LINE_END);
	}

	@Override
	public void update() {

	}

	public void addFavouriteCheckBoxActionListener(ActionListener al) {
		favouriteCheckBox.addActionListener(al);
	}

	public void setFavouriteCheckBoxSelected(boolean selected) {
		favouriteCheckBox.setSelected(selected);
	}

	public boolean isFavouriteCheckBoxSelected() {
		return favouriteCheckBox.isSelected();
	}
}
