package com.softwaremagico.librodeesher.pj.export.json;

import com.google.gson.annotations.Expose;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.level.LevelUp;

public class LevelExporter {
	@Expose
	private LevelUp level;
	@Expose
	private String characterName;
	@Expose
	private String characterRace;
	@Expose
	private String characterProfession;
	@Expose
	private Integer levelNumber;
	@Expose
	private String characterComparatorId;

	public LevelExporter(CharacterPlayer characterPlayer) {
		this.level = characterPlayer.getCurrentLevel();
		this.levelNumber = characterPlayer.getCurrentLevelNumber();
		this.characterRace = characterPlayer.getRace().getName();
		this.characterProfession = characterPlayer.getProfession().getName();
		this.characterName = characterPlayer.getName();
		this.characterComparatorId = characterPlayer.getComparationId();
	}

	public Integer getLevelNumber() {
		return levelNumber;
	}

	public LevelUp getLevel() {
		return level;
	}

	public String getCharacterName() {
		return characterName;
	}

	public String getCharacterRace() {
		return characterRace;
	}

	public String getCharacterProfession() {
		return characterProfession;
	}

	public String getCharacterComparatorId() {
		return characterComparatorId;
	}

}
