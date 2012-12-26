package com.softwaremagico.librodeesher.gui.culture;

import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.Language;

public class LanguagePanel extends CulturePanel {
	private static final long serialVersionUID = -9203104559414795802L;

	public LanguagePanel(CharacterPlayer character, CultureTitleLine title) {
		this.character = character;
		this.title = title;
		setElements(character);
		setRankTitle("Rangos");
	}

	@Override
	protected void createElements() {
		int i = 0;
		for (Language language : character.getCulture().getLanguages()) {
			LanguageLine hobbyLine = new LanguageLine(character, language, this, getLineBackgroundColor(i));
			add(hobbyLine);
			hobbyLines.add(hobbyLine);
			i++;
		}
	}

	@Override
	protected void setRankTitle(String rankLabelText) {
		title.setRankTitle(rankLabelText);
	}

}
