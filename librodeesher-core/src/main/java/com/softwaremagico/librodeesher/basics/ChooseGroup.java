package com.softwaremagico.librodeesher.basics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.softwaremagico.librodeesher.pj.skills.Skill;
import com.softwaremagico.librodeesher.pj.skills.SkillFactory;

public abstract class ChooseGroup<T> {
	protected int numberOfOptionsToChoose;
	protected List<T> optionsGroup;

	public ChooseGroup() {
		this.numberOfOptionsToChoose = 0;
		this.optionsGroup = new ArrayList<>();
	}

	public ChooseGroup(int chooseNumber, List<T> optionsGroup) {
		this.numberOfOptionsToChoose = chooseNumber;
		this.optionsGroup = optionsGroup;
	}

	public ChooseGroup(int chooseNumber, T option) {
		this.numberOfOptionsToChoose = chooseNumber;
		List<T> optionsGroup = new ArrayList<>();
		optionsGroup.add(option);
		this.optionsGroup = optionsGroup;
	}

	public ChooseGroup(int chooseNumber, T[] optionsGroup) {
		this.numberOfOptionsToChoose = chooseNumber;
		this.optionsGroup = Arrays.asList(optionsGroup);
	}
	
	public Integer getNumberOfOptionsToChoose(){
		return numberOfOptionsToChoose;
	}
	
	public abstract List<String> getOptionsAsString();
}
