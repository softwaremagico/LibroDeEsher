package com.softwaremagico.persistence.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.softwaremagico.librodeesher.pj.RandomCharacterPlayer;
import com.softwaremagico.persistence.HibernateInitializator;

@Test(groups = { "characterStorage" })
public class CharacterStorage {

	@Test
	public void testCrud() {
		Session session = HibernateInitializator.getSessionFactory().openSession();
		Transaction tr = session.beginTransaction();
		Long playerId = (Long) session.save(new RandomCharacterPlayer(null, null, null, null, 1).getCharacterPlayer());
		Assert.assertNotNull(playerId);
		// Close transaction to delete db-journal.
		tr.commit();
		session.close();
	}

}
