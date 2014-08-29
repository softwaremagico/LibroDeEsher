package com.softwaremagico.librodeesher.pj.export.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;

public class CharacterJsonManager {

	public static String toJson(CharacterPlayer characterPlayer) {
		final Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
		final String jsonText = gson.toJson(characterPlayer);
		return jsonText;
	}

	public static CharacterPlayer fromJson(String jsonText) {
		final Gson gson = new GsonBuilder().create();
		CharacterPlayer characterPlayer = gson.fromJson(jsonText, CharacterPlayer.class);
		return characterPlayer;
	}
}
