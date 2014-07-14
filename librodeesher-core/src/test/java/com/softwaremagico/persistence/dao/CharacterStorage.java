package com.softwaremagico.persistence.dao;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.random.RandomCharacterPlayer;
import com.softwaremagico.persistence.dao.hibernate.CharacterPlayerDao;

@Test(groups = { "characterStorage" })
public class CharacterStorage {
	private CharacterPlayerDao characterPlayerDao = CharacterPlayerDao.getInstance();

	@Test
	public void testCrud() {
		CharacterPlayer characterPlayer = new RandomCharacterPlayer(null, null, null, null, 1).getCharacterPlayer();
		characterPlayerDao.makePersistent(characterPlayer);
		Assert.assertNotNull(characterPlayer.getId());
	}

}
