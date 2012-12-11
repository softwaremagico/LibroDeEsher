package com.softwaremagico.librodeesher.pj.culture;

import com.softwaremagico.librodeesher.pj.Language;

public class CultureLanguage extends Language {

	CultureLanguage(String name, Integer writtenRanks, Integer spokenRanks) {
		super(name, writtenRanks, spokenRanks);
	}

	CultureLanguage(String name, String writtenRanks, String spokenRanks) throws NumberFormatException{
		super(name, Integer.parseInt(writtenRanks), Integer.parseInt(spokenRanks));
	}
}
