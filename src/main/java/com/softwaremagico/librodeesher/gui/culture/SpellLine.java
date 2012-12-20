package com.softwaremagico.librodeesher.gui.culture;

import java.awt.Color;

import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultFormatter;

import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.magic.MagicLevelRange;
import com.softwaremagico.librodeesher.pj.magic.MagicListType;
import com.softwaremagico.librodeesher.pj.magic.SpellList;

public class SpellLine extends CultureLine {
	private static final long serialVersionUID = 1634524707541210570L;
	private SpellList spell;

	public SpellLine(CharacterPlayer character, SpellList spell, CulturePanel hobbyPanel,
			Color background) {
		this.character = character;
		this.parentPanel = hobbyPanel;
		this.spell = spell;
		this.skillName = spell.getName();
		setElements(background);
		setBackground(background);
		rankSpinner.setValue(character.getCultureDecisions().getSpellRank(spell));
	}

	protected void addRankSpinnerEvent() {
		JComponent comp = rankSpinner.getEditor();
		JFormattedTextField field = (JFormattedTextField) comp.getComponent(0);
		DefaultFormatter formatter = (DefaultFormatter) field.getFormatter();
		formatter.setCommitsOnValidEdit(true);
		rankSpinner.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				// Correct the spinner
				if (parentPanel.getSpinnerValues() > character.getCulture().getSpellRanks()) {
					rankSpinner.setValue((Integer) rankSpinner.getValue() - 1);
					// Too much ranks in one spell list.
				} else if (getSelectedRanks() > character.getProfession().getMagic()
						.getListCost(MagicListType.OPEN, MagicLevelRange.FIRST_FIVE_LEVELS)
						.getMaxRanksPerLevel()) {
					rankSpinner.setValue((Integer) rankSpinner.getValue() - 1);
				} else {
					// Update character
					character.getCultureDecisions().setSpellRank(spell, (Integer) rankSpinner.getValue());
					parentPanel.setRankTitle("Rangos ("
							+ (character.getCulture().getSpellRanks() - character.getCultureDecisions()
									.getTotalSpellRanks()) + ")");
				}
			}
		});
	}
}
