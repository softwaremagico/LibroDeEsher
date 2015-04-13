package com.softwaremagico.librodeesher.basics;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.google.gson.annotations.Expose;
import com.softwaremagico.librodeesher.pj.characteristic.CharacteristicsAbbreviature;
import com.softwaremagico.persistence.StorableObject;

@Entity
@Table(name = "T_ROLL_GROUP")
public class RollGroup extends StorableObject {
	private static final long serialVersionUID = 2665940065448390512L;

	private static final Integer STORED_ROLLS_NUMBER = 10;

	@Expose
	// Must be @OneToMany and orphan removal, but causes a DuplicatedEntry
	// exception.
	// http://stackoverflow.com/questions/4022509/constraint-violation-in-hibernate-unidirectional-onetomany-mapping-with-jointabl
	@ManyToMany(cascade = CascadeType.ALL)
	@CollectionTable(name = "T_ROLL_LIST")
	@LazyCollection(LazyCollectionOption.FALSE)
	@OrderColumn(name = "roll_index")
	@BatchSize(size = 10)
	private List<Roll> rolls;

	@Expose
	@Enumerated(EnumType.STRING)
	private CharacteristicsAbbreviature characteristicAbbreviature;

	protected RollGroup() {
		rolls = new ArrayList<Roll>();
	}

	public RollGroup(CharacteristicsAbbreviature characteristicAbbreviature) {
		rolls = new ArrayList<Roll>();
		this.characteristicAbbreviature = characteristicAbbreviature;
		fillUpRolls();
	}

	@Override
	public void resetIds() {
		resetIds(this);
		resetIds(rolls);
	}

	private void fillUpRolls() {
		while (rolls.size() < STORED_ROLLS_NUMBER) {
			add(new Roll());
		}
	}

	public List<Roll> getRolls() {
		return rolls;
	}

	public void setRolls(List<Roll> rolls) {
		this.rolls = rolls;
	}

	public int size() {
		return rolls.size();
	}

	public void add(Roll roll) {
		rolls.add(roll);
	}

	public Roll getFirst() {
		Roll roll = rolls.remove(0);
		fillUpRolls();
		return new Roll(roll);
	}

	@Override
	public String toString() {
		return rolls + "";
	}

	public CharacteristicsAbbreviature getCharacteristicAbbreviature() {
		return characteristicAbbreviature;
	}

	public void setCharacteristicAbbreviature(CharacteristicsAbbreviature characteristicAbbreviature) {
		this.characteristicAbbreviature = characteristicAbbreviature;
	}

}
