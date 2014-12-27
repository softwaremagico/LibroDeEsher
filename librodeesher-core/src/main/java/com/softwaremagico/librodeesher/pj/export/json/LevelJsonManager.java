package com.softwaremagico.librodeesher.pj.export.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.export.json.exceptions.InvalidCharacterException;
import com.softwaremagico.librodeesher.pj.export.json.exceptions.InvalidLevelException;
import com.softwaremagico.librodeesher.pj.level.LevelUp;

public class LevelJsonManager {

	public static String toJson(CharacterPlayer characterPlayer) {
		if (characterPlayer != null) {
			LevelExporter levelExporter = new LevelExporter(characterPlayer);
			final Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
			final String jsonText = gson.toJson(levelExporter);
			return jsonText;
		}
		return null;
	}

	public static LevelUp fromJson(CharacterPlayer characterPlayer, String jsonText) throws InvalidLevelException,
			InvalidCharacterException {
		if (characterPlayer != null && jsonText != null && jsonText.length() > 0) {
			final Gson gson = new GsonBuilder().create();
			LevelExporter levelExporter = gson.fromJson(jsonText, LevelExporter.class);
			// Only for correct character.
			if (!characterPlayer.getComparationId().equals(levelExporter.getCharacterComparatorId())
					|| !characterPlayer.getRace().getName().equals(levelExporter.getCharacterRace())
					|| !(characterPlayer.getProfession().getName().equals(levelExporter.getCharacterProfession()))
					|| !(characterPlayer.getName().equals(levelExporter.getCharacterName()))) {
				throw new InvalidCharacterException("Invalid level to be imported. Level is defined for '"
						+ levelExporter.getCharacterName() + "' and the actual character is '"
						+ characterPlayer.getName() + "'");
			}
			// Must be next level.
			if (!levelExporter.getLevelNumber().equals(characterPlayer.getCurrentLevelNumber() + 1)) {
				throw new InvalidLevelException("Level invalid to be imported. Level to import is '"
						+ levelExporter.getLevelNumber() + "' and the character has level '"
						+ characterPlayer.getCurrentLevelNumber() + "'.");
			}

			return levelExporter.getLevel();
		}
		return null;
	}
}
