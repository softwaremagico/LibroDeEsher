package com.softwaremagico.librodeesher.gui.culture;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import com.softwaremagico.librodeesher.gui.style.BasePanel;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.culture.CultureSkill;

public class ChooseHobbyPanel extends BasePanel {
	private static final long serialVersionUID = -9203104559414795802L;
	private List<HobbyLine> hobbyLines = new ArrayList<>();
	private CultureTitleLine title;
	private CharacterPlayer character;

	public ChooseHobbyPanel(CharacterPlayer character, CultureTitleLine title) {
		setElements(character);
		this.character = character;
		this.title = title;
	}

	private void setElements(CharacterPlayer character) {
		this.removeAll();
		setLayout(new GridLayout(0, 1));
		int i = 0;

		for (CultureSkill skill : character.getCulture().getHobbySkills()) {
			HobbyLine hobbyLine = new HobbyLine(character, skill, this, getLineBackgroundColor(i));
			add(hobbyLine);
			hobbyLines.add(hobbyLine);
			i++;
		}
	}
	
	protected Integer getSpinnerValues() {
		Integer total = 0;
		for (HobbyLine lines : hobbyLines) {
			total += lines.getSelectedRanks();
		}
		title.setRankTitle("Rangos (" + (character.getCulture().getHobbyRanks() - total) + ")");
		return total;
	}

}
