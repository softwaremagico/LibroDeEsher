package com.softwaremagico.librodeesher.basics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class ChooseGroup<T> {

	private int numberOfOptionsToChoose;
	private List<T> optionsGroup;
	private ChooseType chooseType;

	public ChooseGroup(ChooseType chooseType) {
		numberOfOptionsToChoose = 0;
		optionsGroup = new ArrayList<>();
		this.chooseType = chooseType;
	}

	public ChooseGroup(int chooseNumber, List<T> optionsGroup, ChooseType chooseType) {
		numberOfOptionsToChoose = chooseNumber;
		this.optionsGroup = optionsGroup;
		this.chooseType = chooseType;
	}

	public ChooseGroup(int chooseNumber, T option, ChooseType chooseType) {
		this.chooseType = chooseType;
		numberOfOptionsToChoose = chooseNumber;
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
		// Choose all except a negative number.
		if (numberOfOptionsToChoose < 0) {
			return optionsGroup.size() + numberOfOptionsToChoose;
		}
		return numberOfOptionsToChoose;
	}

	public abstract List<String> getOptionsAsString();

	public ChooseType getChooseType() {
		return chooseType;
	}

	public List<T> getOptionsGroup() {
		return optionsGroup;
	}

	protected void setOptionsGroup(List<T> optionsGroup) {
		this.optionsGroup = optionsGroup;
	}

	public void setNumberOfOptionsToChoose(int numberOfOptionsToChoose) {
		this.numberOfOptionsToChoose = numberOfOptionsToChoose;
	}
}
