package com.softwaremagico.librodeesher.pj.race;

import com.softwaremagico.librodeesher.pj.Language;

public class RaceLanguage extends Language {
	private Integer maxWrittenRanks;
	private Integer maxSpokenRanks;

	public RaceLanguage(String name, Integer writtenRanks, Integer spokenRanks, Integer maxWrittenRanks, Integer maxSpokenRanks) {
		super(name, writtenRanks, spokenRanks);
		this.maxSpokenRanks = maxSpokenRanks;
		this.maxWrittenRanks = maxWrittenRanks;
	}
	
	public RaceLanguage(String name, String writtenRanks, String spokenRanks, String maxWrittenRanks, String maxSpokenRanks) throws NumberFormatException {
		super(name, Integer.parseInt(writtenRanks), Integer.parseInt(spokenRanks));
		this.maxSpokenRanks = Integer.parseInt(maxSpokenRanks);
		this.maxWrittenRanks = Integer.parseInt(maxWrittenRanks);
	}
	
}
