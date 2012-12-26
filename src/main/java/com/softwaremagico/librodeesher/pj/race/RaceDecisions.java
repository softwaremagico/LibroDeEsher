package com.softwaremagico.librodeesher.pj.race;

import java.util.Hashtable;

import com.softwaremagico.librodeesher.pj.Language;

public class RaceDecisions {
	Hashtable<Language, Integer> languageRanks;
	
	public RaceDecisions(){
		languageRanks = new Hashtable<>();
	}
	
	public void setLanguageRank(Language language, Integer ranks) {
		if (ranks <= 0) {
			languageRanks.remove(language);
		} else {
			languageRanks.put(language, ranks);
		}
	}
	
	public Integer getTotalLanguageRanks(){
		Integer total = 0;
		for (Integer value : languageRanks.values()) {
			total += value;
		}
		return total;
	}
	
}
