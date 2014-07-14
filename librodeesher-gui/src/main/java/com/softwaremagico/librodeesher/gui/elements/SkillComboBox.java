package com.softwaremagico.librodeesher.gui.elements;

import java.util.ArrayList;
import java.util.List;

import com.softwaremagico.librodeesher.pj.categories.Category;
import com.softwaremagico.librodeesher.pj.skills.Skill;

public class SkillComboBox extends BaseComboBox<Skill> {
	private static final long serialVersionUID = 7160132975462619054L;
	private List<SkillChangedListener> skillListeners;

	public SkillComboBox() {
		skillListeners = new ArrayList<>();
	}

	public void setSkills(Category category) {
		for (Skill skill : category.getSkills()) {
			addItem(skill);
		}
	}

	@Override
	public void doAction() {
		for (SkillChangedListener listener : skillListeners) {
			listener.skillChanged((Skill) this.getSelectedItem());
		}
	}

	public void addSkillChangedListener(SkillChangedListener listener) {
		skillListeners.add(listener);
	}

	public void removeSkillChangedListener(SkillChangedListener listener) {
		skillListeners.remove(listener);
	}
}