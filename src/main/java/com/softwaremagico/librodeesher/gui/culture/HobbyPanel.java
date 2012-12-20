package com.softwaremagico.librodeesher.gui.culture;

import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.culture.CultureSkill;

public class HobbyPanel extends CulturePanel {
	private static final long serialVersionUID = -1080201556731377298L;
	private CompleteHobbiesPanel parentPanel;

	public HobbyPanel(CharacterPlayer character, CultureTitleLine title,
			CompleteHobbiesPanel parentPanel) {
		this.character = character;
		this.parentPanel = parentPanel;
		this.title = title;
		setElements(character);
	}

	@Override
	protected void createElements() {
		int i = 0;
		for (CultureSkill skill : character.getCulture().getHobbySkills()) {
			HobbyLine hobbyLine = new HobbyLine(character, skill, this, getLineBackgroundColor(i));
			add(hobbyLine);
			hobbyLines.add(hobbyLine);
			i++;
		}
	}

	protected void setRankTitle(String rankLabelText) {
		parentPanel.setRankTitle(rankLabelText);
	}
}
