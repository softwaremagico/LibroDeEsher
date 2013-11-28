package com.softwaremagico.persistence.dao;

import org.hibernate.Session;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.persistence.HibernateInitializator;

@Test(groups = { "characterStorage" })
public class CharacterStorage {

	private CharacterPlayer createPlayer() {
		CharacterPlayer character = new CharacterPlayer();
		character.setName("Fulanito Jr.");
		character.setRace("Enano");
		character.setCulture("Fluvial");
		return character;
	}

	@Test
	public void testCrud() {
		Session session = HibernateInitializator.getSessionFactory().openSession();
		session.beginTransaction();
		Long playerId = (Long) session.save(createPlayer());
		Assert.assertNotNull(playerId);
	}

}
