package com.softwaremagico.librodeesher.basics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

@MappedSuperclass
@Table(name = "T_CHOOSE_GROUP")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class ChooseGroup<T> {
	@Id
	@GeneratedValue
	private Long id; // database id.

	protected int numberOfOptionsToChoose;
	@ElementCollection
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

	protected Long getId() {
		return id;
	}

	protected void setId(Long id) {
		this.id = id;
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
