package com.softwaremagico.persistence.dao.hibernate;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.softwaremagico.librodeesher.basics.Roll;
import com.softwaremagico.librodeesher.basics.RollGroup;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.persistence.HibernateInitializator;
import com.softwaremagico.persistence.dao.ICharacterPlayerDao;

public class CharacterPlayerDao implements ICharacterPlayerDao {
	private SessionFactory sessionFactory = null;
	private static CharacterPlayerDao instance = null;

	private CharacterPlayerDao() {
		sessionFactory = HibernateInitializator.getSessionFactory();
	}

	public static CharacterPlayerDao getInstance() {
		if (instance == null) {
			synchronized (CharacterPlayerDao.class) {
				if (instance == null) {
					instance = new CharacterPlayerDao();
				}
			}
		}
		return instance;
	}

	@Override
	public List<CharacterPlayer> getAll() {
		// With spring, it close and open the session. We would use
		// sessionFactory.getCurrentSession();
		Session session = getSessionFactory().openSession();
		@SuppressWarnings("unchecked")
		List<CharacterPlayer> characters = session.createQuery("from T_CHARACTERPLAYER").list();
		session.close();
		return characters;
	}

	@Override
	public CharacterPlayer read(Long id) {
		Session session = getSessionFactory().openSession();
		CharacterPlayer character = (CharacterPlayer) session.get(CharacterPlayer.class, id);
		session.close();
		return character;
	}

	@Override
	public Roll makePersistent(Roll roll) {
		Session session = getSessionFactory().openSession();
		session.beginTransaction();
		session.saveOrUpdate(roll);
		// Close transaction to delete db-journal.
		session.getTransaction().commit();
		session.close();
		return roll;
	}

	@Override
	public CharacterPlayer makePersistent(CharacterPlayer character) {

		Session session = getSessionFactory().openSession();
		session.beginTransaction();
		session.saveOrUpdate(character);
		// Close transaction to delete db-journal.
		session.getTransaction().commit();
		session.close();
		return character;
	}

	@Override
	public void makeTransient(CharacterPlayer character) {
		Session session = getSessionFactory().openSession();
		session.beginTransaction();
		session.delete(character);
		session.getTransaction().commit();
		session.close();

	}

	protected SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}
