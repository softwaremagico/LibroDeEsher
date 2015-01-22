package com.softwaremagico.persistence.dao;

import java.util.List;

public interface IGenericDao<T> {

	/**
	 * Get all forms stored into the database.
	 * 
	 * @return
	 */
	List<T> getAll();

	/**
	 * Saves or update a form.
	 * 
	 * @param planningEvent
	 * @return
	 * @throws Exception 
	 */
	T makePersistent(T entity) throws Exception;

	/**
	 * Delete the persistence of the object (but not the object).
	 * 
	 * 
	 * @param planningEvent
	 */
	void makeTransient(T entity);

	/**
	 * Gets the total number of forms.
	 * 
	 * @return
	 */
	int getRowCount();

	/**
	 * Gets one element by id.
	 * 
	 * @param id
	 * @return
	 */
	T read(Long id);

	/**
	 * Remove all elements from database.
	 * 
	 * @return
	 */
	void removeAll();
}
