package com.softwaremagico.librodeesher.pj.export.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;

public class CharacterJsonManager {

	public static String toJson(CharacterPlayer characterPlayer) {
		if (characterPlayer != null) {
			final Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation()
					.create();
			final String jsonText = gson.toJson(characterPlayer);
			return jsonText;
		}
		return null;
	}

	public static CharacterPlayer fromJson(String jsonText) {
		if (jsonText != null && jsonText.length() > 0) {
			final Gson gson = new GsonBuilder().create();
			CharacterPlayer characterPlayer = gson.fromJson(jsonText, CharacterPlayer.class);
			characterPlayer.clearCache();
			return characterPlayer;
		}
		return null;
	}
}
