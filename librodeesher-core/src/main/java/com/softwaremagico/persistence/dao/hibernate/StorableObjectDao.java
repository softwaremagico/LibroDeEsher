package com.softwaremagico.persistence.dao.hibernate;

import java.sql.Timestamp;
import java.util.Date;

import com.softwaremagico.persistence.StorableObject;

public class StorableObjectDao<T> {

	protected void setCreationInfo(T entity) {
		StorableObject storableObject = (StorableObject) entity;
		if (storableObject.getCreationTime() == null) {
			storableObject.setCreationTime(new Timestamp(new Date().getTime()));
		}
	}

	protected void setUpdateInfo(T entity) {
		StorableObject treeObject = (StorableObject) entity;
		treeObject.setUpdateTime(new Timestamp(new Date().getTime()));
	}

}
