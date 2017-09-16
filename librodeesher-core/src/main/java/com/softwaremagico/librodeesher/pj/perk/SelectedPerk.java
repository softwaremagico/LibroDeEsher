package com.softwaremagico.librodeesher.pj.perk;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.softwaremagico.persistence.StorableObject;

@Entity
@Table(name = "T_PERKS")
public class SelectedPerk extends StorableObject {
	private static final long serialVersionUID = -3963266207696003617L;
	@Expose
	private String name;
	@Expose
	private Integer cost;
	@Expose
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private SelectedPerk weakness;
	private boolean random = false;

	protected SelectedPerk() {
		super();
	}

	public SelectedPerk(Perk perk) {
		super();
		name = perk.getName();
		cost = perk.getCost();
	}

	@Override
	public void resetIds() {
		resetIds(this);
	}
	
	@Override
	public void resetComparationIds() {
		resetComparationIds(this);
	}

	public String getName() {
		return name;
	}

	public Integer getCost() {
		return cost;
	}

	@Override
	public String toString() {
		return getName();
	}

	public SelectedPerk getWeakness() {
		return weakness;
	}

	public void setWeakness(SelectedPerk weakness) {
		this.weakness = weakness;
	}

	public boolean isRandom() {
		return random;
	}

	public void setRandom(boolean random) {
		this.random = random;
	}
}
