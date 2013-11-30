package com.softwaremagico.librodeesher.basics;

import java.util.ArrayList;
import java.util.List;

public class RollGroup {
	private Long id; // database id.
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
