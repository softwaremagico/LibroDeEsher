package com.softwaremagico.librodeesher.pj.equipment;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.softwaremagico.persistence.StorableObject;

@Entity
@Table(name = "T_MAGIC_OBJECT")
public class MagicObject extends StorableObject {
	@Expose
	private String name;

	@Expose
	private String description;

	@Expose
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<ObjectBonus> bonus = new ArrayList<>();

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
