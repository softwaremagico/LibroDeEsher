package com.softwaremagico.librodeesher.pj.equipment;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.softwaremagico.persistence.StorableObject;

@Entity
@Table(name = "T_MAGIC_OBJECT")
public class MagicObject extends StorableObject {
	@Expose
	private String name;

	@Expose
	@ElementCollection
	private List<ObjectBonus> bonus;

	public MagicObject() {

	}

	public String getName() {
		return name;
	}

	public List<ObjectBonus> getBonus() {
		return bonus;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setBonus(List<ObjectBonus> bonus) {
		this.bonus = bonus;
	}

	@Override
	public void resetIds() {
		setId(null);
		for (ObjectBonus bonus : getBonus()) {
			bonus.resetIds();
		}
	}
}
