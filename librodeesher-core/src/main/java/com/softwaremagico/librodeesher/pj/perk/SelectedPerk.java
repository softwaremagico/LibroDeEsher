package com.softwaremagico.librodeesher.pj.perk;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.softwaremagico.persistence.StorableObject;

@Entity
@Table(name = "T_PERKS")
public class SelectedPerk extends StorableObject {
	private String name;
	private Integer cost;
	
	protected SelectedPerk(){
		
	}
	
	public SelectedPerk(Perk perk){
		name = perk.getName();
		cost = perk.getCost();
	}
	
	@Override
	public void resetIds(){
		resetIds(this);
	}

	public String getName() {
		return name;
	}

	public Integer getCost() {
		return cost;
	}
	
	@Override
	public String toString(){
		return getName();
	}
}
