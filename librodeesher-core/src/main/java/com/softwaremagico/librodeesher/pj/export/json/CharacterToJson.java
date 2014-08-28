package com.softwaremagico.librodeesher.pj.export.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;

public class CharacterToJson {

	public static void toJson(CharacterPlayer characterPlayer){
		final GsonBuilder builder = new GsonBuilder();
		final Gson gson = builder.create();
		final String jsonText = gson.toJson(characterPlayer);
		System.out.println(jsonText);
	}
}
