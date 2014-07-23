package com.softwaremagico.persistence;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import com.softwaremagico.utils.IdGenerator;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class StorableObject {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name = "ID", unique = true, nullable = false)
	private Long id;

	@Column(nullable = false)
	private Timestamp creationTime = null;
	private Timestamp updateTime = null;

	// A unique Id created with the object used to compare persisted objects and
	// in memory objects.
	@Column(unique = true, nullable = false, updatable = false)
	private String comparationId;

	public StorableObject() {
		creationTime = new java.sql.Timestamp(new java.util.Date().getTime());
		updateTime = new java.sql.Timestamp(new java.util.Date().getTime());
		comparationId = IdGenerator.createId();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Timestamp getCreationTime() {
		if (creationTime != null) {
			return creationTime;
		} else {
			creationTime = new java.sql.Timestamp(
					new java.util.Date().getTime());
			return creationTime;
		}
	}

	public void setUpdateTime() {
		setUpdateTime(new java.sql.Timestamp(new java.util.Date().getTime()));
	}

	public Timestamp getUpdateTime() {
		if (updateTime != null) {
			return updateTime;
		} else {
			updateTime = new java.sql.Timestamp(new java.util.Date().getTime());
			return updateTime;
		}
	}


	public void setCreationTime(Timestamp dateCreated) {
		this.creationTime = dateCreated;
	}

	public void setUpdateTime(Timestamp dateUpdated) {
		this.updateTime = dateUpdated;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((comparationId == null) ? 0 : comparationId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StorableObject other = (StorableObject) obj;
		if (comparationId == null) {
			if (other.comparationId != null)
				return false;
		} else if (!comparationId.equals(other.comparationId))
			return false;
		return true;
	}

	public void resetIds() {
		setId(null);
	}

}
