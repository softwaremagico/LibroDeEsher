package com.softwaremagico.librodeesher.basics;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_ROLLS")
public class RollGroup {
	@Id
	@GeneratedValue
	private Long id; // database id.
	
	 @ElementCollection
	private List<Roll> rolls;

	public RollGroup() {
		rolls = new ArrayList<Roll>();
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
	
	public void add(Roll roll){
		rolls.add(roll);
	}
	
	public Roll getFirst(){
		return rolls.remove(0);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
