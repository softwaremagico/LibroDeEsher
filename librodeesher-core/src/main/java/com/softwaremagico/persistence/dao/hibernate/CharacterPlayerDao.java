package com.softwaremagico.persistence.dao.hibernate;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

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
		Session session = sessionFactory.openSession();
		@SuppressWarnings("unchecked")
		List<CharacterPlayer> characters = session.createQuery("from Employee").list();
		session.close();
		return characters;
	}

	@Override
	public CharacterPlayer read(Long id) {
		Session session = sessionFactory.openSession();
		CharacterPlayer character = (CharacterPlayer) session.get(CharacterPlayer.class, id);
		session.close();
		return character;
	}

	@Override
	public CharacterPlayer save(CharacterPlayer character) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Long id = (Long) session.save(character);
		character.setId(id);
		session.getTransaction().commit();
		session.close();
		return character;
	}

	@Override
	public CharacterPlayer update(CharacterPlayer character) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.merge(character);
		session.getTransaction().commit();
		session.close();
		return character;
	}

	@Override
	public void delete(CharacterPlayer character) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.delete(character);
		session.getTransaction().commit();
		session.close();

	}
}
