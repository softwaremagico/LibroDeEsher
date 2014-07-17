package com.softwaremagico.librodeesher.basics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

import com.softwaremagico.persistence.StorableObject;

@MappedSuperclass
@Table(name = "T_CHOOSE_GROUP")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class ChooseGroup<T>  extends StorableObject {

	protected int numberOfOptionsToChoose;
	@ElementCollection
	@CollectionTable(name = "T_CHOOSE_GROUP_OPTIONS")
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
