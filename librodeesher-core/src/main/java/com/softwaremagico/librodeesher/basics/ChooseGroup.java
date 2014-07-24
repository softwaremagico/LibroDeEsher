package com.softwaremagico.librodeesher.basics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class ChooseGroup<T> {

	protected int numberOfOptionsToChoose;
	protected List<T> optionsGroup;
	protected ChooseType chooseType;

	public ChooseGroup(ChooseType chooseType) {
		this.numberOfOptionsToChoose = 0;
		this.optionsGroup = new ArrayList<>();
	}

	public ChooseGroup(int chooseNumber, List<T> optionsGroup, ChooseType chooseType) {
		this.numberOfOptionsToChoose = chooseNumber;
		this.optionsGroup = optionsGroup;
		this.chooseType = chooseType;
	}

	public ChooseGroup(int chooseNumber, T option, ChooseType chooseType) {
		this.chooseType = chooseType;
		this.numberOfOptionsToChoose = chooseNumber;
		List<T> optionsGroup = new ArrayList<>();
		optionsGroup.add(option);
		this.optionsGroup = optionsGroup;
	}

	public ChooseGroup(int chooseNumber, T[] optionsGroup, ChooseType chooseType) {
		this.chooseType = chooseType;
		this.numberOfOptionsToChoose = chooseNumber;
		this.optionsGroup = Arrays.asList(optionsGroup);
	}

	public Integer getNumberOfOptionsToChoose() {
		return numberOfOptionsToChoose;
	}

	public abstract List<String> getOptionsAsString();

	public ChooseType getChooseType() {
		return chooseType;
	}

	protected List<T> getOptionsGroup() {
		return optionsGroup;
	}

	protected void setOptionsGroup(List<T> optionsGroup) {
		this.optionsGroup = optionsGroup;
	}

	protected void setNumberOfOptionsToChoose(int numberOfOptionsToChoose) {
		this.numberOfOptionsToChoose = numberOfOptionsToChoose;
	}

	protected void setChooseType(ChooseType chooseType) {
		this.chooseType = chooseType;
	}
}
