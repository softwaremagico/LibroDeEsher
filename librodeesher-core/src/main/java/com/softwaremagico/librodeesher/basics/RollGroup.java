package com.softwaremagico.librodeesher.basics;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "T_ROLL_GROUP")
public class RollGroup {

	private static final Integer STORED_ROLLS_NUMBER = 10;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name = "ID", unique = true, nullable = false)
	private Long rollGroupId; // database id.

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@CollectionTable(name = "T_ROLL_LIST")
	@JoinColumn(name = "rollId")
	private List<Roll> rolls;

	private String characteristicAbbreviature;

	public RollGroup(String characteristicAbbreviature) {
		rolls = new ArrayList<Roll>();
		this.characteristicAbbreviature = characteristicAbbreviature;
		fillUpRolls();
	}

	private void fillUpRolls() {
		while (rolls.size() < STORED_ROLLS_NUMBER) {
			rolls.add(new Roll());
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
		return roll;
	}

	public Long getRollGroupId() {
		return rollGroupId;
	}

	public void setRollGroupId(Long id) {
		this.rollGroupId = id;
	}

	@Override
	public String toString() {
		return rolls + "";
	}

	public String getCharacteristicAbbreviature() {
		return characteristicAbbreviature;
	}

	public void setCharacteristicAbbreviature(String characteristicAbbreviature) {
		this.characteristicAbbreviature = characteristicAbbreviature;
	}

}
