package com.softwaremagico.persistence.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.DistinctRootEntityResultTransformer;

import com.softwaremagico.persistence.HibernateInitializator;
import com.softwaremagico.persistence.dao.IGenericDao;

public abstract class GenericDao<T> extends StorableObjectDao<T> implements IGenericDao<T> {

	private Class<T> type;

	private SessionFactory sessionFactory = null;

	public GenericDao(Class<T> type) {
		this.type = type;
	}

	public Class<T> getType() {
		return type;
	}

	protected SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			sessionFactory = HibernateInitializator.getSessionFactory();
		}
		return sessionFactory;
	}

	protected void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public T makePersistent(T entity) throws Exception {
		setCreationInfo(entity);
		setUpdateInfo(entity);
		Session session = getSessionFactory().getCurrentSession();
		session.beginTransaction();
		try {
			session.saveOrUpdate(entity);
			session.flush();
			session.getTransaction().commit();
			return entity;
		} catch (RuntimeException e) {
			session.getTransaction().rollback();
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public void makeTransient(T entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.beginTransaction();
		try {
			session.delete(entity);
			session.flush();
			session.getTransaction().commit();
		} catch (RuntimeException e) {
			session.getTransaction().rollback();
			throw e;
		}
	}

	@Override
	public T read(Long id) {
		Session session = getSessionFactory().getCurrentSession();
		session.beginTransaction();
		try {
			@SuppressWarnings("unchecked")
			T object = (T) session.get(getType(), id);
			initializeSet(object);
			session.getTransaction().commit();
			return object;
		} catch (RuntimeException e) {
			session.getTransaction().rollback();
			throw e;
		}
	}

	@Override
	public int getRowCount() {
		Session session = getSessionFactory().getCurrentSession();
		session.beginTransaction();
		try {
			Criteria criteria = session.createCriteria(getType());
			criteria.setProjection(Projections.rowCount());
			int rows = ((Long) criteria.uniqueResult()).intValue();
			session.getTransaction().commit();
			return rows;
		} catch (RuntimeException e) {
			session.getTransaction().rollback();
			throw e;
		}
	}

	@Override
	public List<T> getAll() {
		Session session = getSessionFactory().getCurrentSession();
		session.beginTransaction();
		try {
			// session.createCriteria(getType()).list() is not working returns repeated elements due to
			// http://stackoverflow.com/questions/8758363/why-session-createcriteriaclasstype-list-return-more-object-than-in-list
			// if we have a list with eager fetch.
			Criteria criteria = session.createCriteria(getType());
			// This is executed in java side.
			criteria.setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE);
			criteria.addOrder(Order.desc("updateTime"));
			@SuppressWarnings("unchecked")
			List<T> objects = criteria.list();
			initializeSets(objects);
			session.getTransaction().commit();
			return objects;
		} catch (RuntimeException e) {
			session.getTransaction().rollback();
			throw e;
		}
	}

	/**
	 * Truncates the table.
	 */
	@Override
	public void removeAll() {
		List<T> elements = getAll();
		for (T element : elements) {
			makeTransient(element);
		}
	}

	/**
	 * When using lazy loading, the sets must have a proxy to avoid a
	 * "LazyInitializationException: failed to lazily initialize a collection of..." error. This procedure must be
	 * called before closing the session.
	 * 
	 * @param planningEvent
	 */
	private void initializeSet(T element) {
		List<T> elements = new ArrayList<>();
		elements.add(element);
		initializeSets(elements);
	}

	/**
	 * When using lazy loading, the sets must have a proxy to avoid a
	 * "LazyInitializationException: failed to lazily initialize a collection of..." error. This procedure must be
	 * called before closing the session.
	 * 
	 * @param elements
	 */
	protected abstract void initializeSets(List<T> elements);
}
