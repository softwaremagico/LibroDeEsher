package com.softwaremagico.librodeesher.pj;

import com.softwaremagico.librodeesher.pj.categories.CategoryFactory;
import com.softwaremagico.librodeesher.pj.skills.StandardSkill;

public class Language extends StandardSkill{
	public final static String SPOKEN_TAG = "Hablar";
	public final static String WRITTEN_TAG = "Escribir";
	private Integer ranks;

	public Language(String name, Integer ranks) {
		super(name);
		this.ranks = ranks;
		setCategory(CategoryFactory.getAvailableCategory("Comunicación"));
	}
	
	public Integer getRanks(){
		return ranks;
	}
}
