package com.softwaremagico.librodeesher.gui.culture;

import java.util.List;

import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.magic.MagicFactory;
import com.softwaremagico.librodeesher.pj.magic.SpellList;

public class ChooseSpellPanel extends ChooseCulturePanel {
	private static final long serialVersionUID = -9203104559414795802L;
	private CompleteSpellPanel parentPanel;

	public ChooseSpellPanel(CharacterPlayer character, CultureTitleLine title, CompleteSpellPanel parentPanel) {
		this.character = character;
		this.title = title;
		this.parentPanel = parentPanel;
		setElements(character);
	}

	@Override
	protected void createElements() {
		int i = 0;

		List<SpellList> spellLists = MagicFactory.getListOfProfession(character.getRealmOfMagic(),
				"Lista Abierta");

		for (SpellList spell : spellLists) {
			SpellLine hobbyLine = new SpellLine(character, spell, this, getLineBackgroundColor(i));
			add(hobbyLine);
			hobbyLines.add(hobbyLine);
			i++;
		}
	}

	@Override
	protected void setRankTitle(String rankLabelText) {
		parentPanel.setRankTitle(rankLabelText);

	}

}
