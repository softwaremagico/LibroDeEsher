package com.softwaremagico.librodeesher.gui.history;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultFormatter;

import com.softwaremagico.librodeesher.gui.elements.BaseSpinner;
import com.softwaremagico.librodeesher.gui.elements.ListLabel;
import com.softwaremagico.librodeesher.gui.style.BaseLine;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.skills.SkillFactory;

public class HistoryLanguageLine extends BaseLine {

	private static final long serialVersionUID = 2401612544094265349L;
	private String language;
	private Integer initalValue;
	private HistoryLanguagePanel parentPanel;
	private CharacterPlayer characterPlayer;
	private BaseSpinner rankSpinner;
	private String skillName;

	public HistoryLanguageLine(CharacterPlayer characterPlayer, String language, HistoryLanguagePanel languagePanel, Color background) {
		this.characterPlayer = characterPlayer;
		this.skillName = language;
		this.parentPanel = languagePanel;
		this.language = language;
		setElements(background);
		setBackground(background);
		initalValue = characterPlayer.getLanguageInitialRanks(language);
		SpinnerModel sm = new SpinnerNumberModel((int) initalValue + characterPlayer.getMaxHistoryLanguages().get(language), (int) initalValue,
				(int) characterPlayer.getMaxHistoryLanguages().get(language), 1);
		rankSpinner.setModel(sm);

		// Languages can have 10 ranks. We need a bigger editor.
		rankSpinner.setColumns(2);

		// Initialize the title.
		setTitle();
	}

	protected void setElements(Color background) {
		this.removeAll();
		setLayout(new GridBagLayout());
		GridBagConstraints gridBagConstraints = new GridBagConstraints();

		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.ipadx = xPadding;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 0;

		ListLabel hobby = new ListLabel(skillName, SwingConstants.LEFT);
		add(hobby, gridBagConstraints);

		SpinnerModel sm = new SpinnerNumberModel(0, 0, 3, 1);
		rankSpinner = new BaseSpinner(sm);
		rankSpinner.setValue(0);
		addRankSpinnerEvent();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.weightx = 0;
		add(createSpinnerInsidePanel(rankSpinner, background), gridBagConstraints);
	}

	protected void addRankSpinnerEvent() {
		JComponent comp = rankSpinner.getEditor();
		JFormattedTextField field = (JFormattedTextField) comp.getComponent(0);
		DefaultFormatter formatter = (DefaultFormatter) field.getFormatter();
		formatter.setCommitsOnValidEdit(true);
		rankSpinner.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				// Update character
				characterPlayer.setHistoryRanks(language, (Integer) rankSpinner.getValue() - initalValue);
				// Race and culture language limit.
				if (characterPlayer.getCultureTotalLanguageRanks() > characterPlayer.getRealRanks(SkillFactory.getSkill(language))) {
					rankSpinner.setValue((Integer) rankSpinner.getValue() - 1);
					// No enought history points
				} else if (characterPlayer.getRemainingHistorialPoints() <= 0) {
					rankSpinner.setValue((Integer) rankSpinner.getValue() - 1);
				} else {
					setTitle();
				}
			}
		});
	}

	private void setTitle() {
		parentPanel.setTitle(characterPlayer.getHistoryLanguageRanks() % 20);
	}

	protected Integer getSelectedRanks() {
		return (Integer) rankSpinner.getValue() - initalValue;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Integer getInitalValue() {
		return initalValue;
	}

	public void setInitalValue(Integer initalValue) {
		this.initalValue = initalValue;
	}

	public HistoryLanguagePanel getParentPanel() {
		return parentPanel;
	}

	public void setParentPanel(HistoryLanguagePanel parentPanel) {
		this.parentPanel = parentPanel;
	}

	public CharacterPlayer getCharacterPlayer() {
		return characterPlayer;
	}

	public void setCharacterPlayer(CharacterPlayer characterPlayer) {
		this.characterPlayer = characterPlayer;
	}

	public BaseSpinner getRankSpinner() {
		return rankSpinner;
	}

	public void setRankSpinner(BaseSpinner rankSpinner) {
		this.rankSpinner = rankSpinner;
	}

	public String getSkillName() {
		return skillName;
	}

	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}

	@Override
	public void update() {

	}
}
