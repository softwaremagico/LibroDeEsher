package com.softwaremagico.librodeesher.pj.equipment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.softwaremagico.persistence.StorableObject;

/**
 * Standard items (not magic).
 */
@Entity
@Table(name = "T_EQUIPMENT")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Equipment extends StorableObject {
	private static final long serialVersionUID = -140738752904493245L;
	public static final int MAX_DESCRIPTION_LENGTH = 500;
	@Expose
	private String name;
	@Expose
	@Column(length = MAX_DESCRIPTION_LENGTH)
	private String description;

	@Override
	public void resetIds() {
		resetIds(this);
	}

	@Override
	public void resetComparationIds() {
		resetComparationIds(this);
	}

	public Equipment() {
		super();
	}

	public Equipment(Equipment trainingItem) {
		this();
		name = trainingItem.getName();
		description = trainingItem.getDescription();
	}

	public Equipment(String name, String description) {
		this();
		this.name = name;
		if (description != null && description.length() > 0) {
			this.description = description;
		} else {
			if (name.contains("(")) {
				this.name = name.substring(0, name.indexOf("(")).trim();
				this.description = name.substring(name.indexOf("(") + 1, name.indexOf(")")).trim();
			} else {
				this.name = name;
			}
		}
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	protected void setName(String name) {
		this.name = name;
	}

	protected void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return getName() + " (" + getDescription() + ")";
	}

}
