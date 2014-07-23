package com.softwaremagico.librodeesher.basics;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

import com.softwaremagico.librodeesher.pj.characteristic.CharacteristicsAbbreviature;
import com.softwaremagico.persistence.StorableObject;

@Entity
@Table(name = "T_ROLL_GROUP")
public class RollGroup extends StorableObject {

	private static final Integer STORED_ROLLS_NUMBER = 10;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	@CollectionTable(name = "T_ROLL_LIST")
	@OrderColumn(name = "roll_index")
	private List<Roll> rolls;

	@Enumerated(EnumType.STRING) 
	private CharacteristicsAbbreviature characteristicAbbreviature;

	protected RollGroup(){
		rolls = new ArrayList<Roll>();
	}
	
	public RollGroup(CharacteristicsAbbreviature characteristicAbbreviature) {
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
