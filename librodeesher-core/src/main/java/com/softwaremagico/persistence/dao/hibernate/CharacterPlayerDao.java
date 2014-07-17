package com.softwaremagico.persistence.dao.hibernate;

import java.util.List;

import org.hibernate.Hibernate;

import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.persistence.dao.ICharacterPlayerDao;

public class CharacterPlayerDao extends GenericDao<CharacterPlayer> implements
		ICharacterPlayerDao {
	private static CharacterPlayerDao instance = null;

	public CharacterPlayerDao(Class<CharacterPlayer> type) {
		super(type);
	}

	public static CharacterPlayerDao getInstance() {
		if (instance == null) {
			synchronized (CharacterPlayerDao.class) {
				if (instance == null) {
					instance = new CharacterPlayerDao(CharacterPlayer.class);
				}
			}
		}
		return instance;
	}

	@Override
	protected void initializeSets(List<CharacterPlayer> elements) {
		for (CharacterPlayer player : elements) {
			// Initializes the sets for lazy-loading (within the same session)
			Hibernate.initialize(player.getCharacteristicInitialTemporalValue());
			Hibernate.initialize(player.getCharacteristicPotencialValue());
			Hibernate.initialize(player.getCharacteristicsTemporalUpdatesRolls());
			Hibernate.initialize(player.getTrainingsNames());
			Hibernate.initialize(player.getTrainingDecisions());
			Hibernate.initialize(player.getPerks());
		}

	}
}
