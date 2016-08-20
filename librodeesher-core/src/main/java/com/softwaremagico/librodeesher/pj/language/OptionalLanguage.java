package com.softwaremagico.librodeesher.pj.language;

/**
 * Idioma Racial, Idioma Cultural
 */
public class OptionalLanguage {
	int startingSpeakingRanks = 0;
	int startingWrittingRanks = 0;
	int maxSpeakingRanks = 0;
	int maxWritingRanks = 0;

	public int getStartingSpeakingRanks() {
		return startingSpeakingRanks;
	}

	public void setStartingSpeakingRanks(int startingSpeakingRanks) {
		this.startingSpeakingRanks = startingSpeakingRanks;
	}

	public int getStartingWrittingRanks() {
		return startingWrittingRanks;
	}

	public void setStartingWrittingRanks(int startingWrittingRanks) {
		this.startingWrittingRanks = startingWrittingRanks;
	}

	public int getMaxSpeakingRanks() {
		return maxSpeakingRanks;
	}

	public void setMaxSpeakingRanks(int maxSpeakingRanks) {
		this.maxSpeakingRanks = maxSpeakingRanks;
	}

	public int getMaxWritingRanks() {
		return maxWritingRanks;
	}

	public void setMaxWritingRanks(int maxWritingRanks) {
		this.maxWritingRanks = maxWritingRanks;
	}
}
