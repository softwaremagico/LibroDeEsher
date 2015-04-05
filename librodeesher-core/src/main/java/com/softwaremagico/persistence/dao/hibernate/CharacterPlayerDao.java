package com.softwaremagico.persistence.dao.hibernate;

import java.util.List;

import org.hibernate.Hibernate;

import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.persistence.dao.ICharacterPlayerDao;

public class CharacterPlayerDao extends GenericDao<CharacterPlayer> implements ICharacterPlayerDao {
	private static CharacterPlayerDao instance = null;

	public CharacterPlayerDao() {
		super(CharacterPlayer.class);
	}

	@Override
	public CharacterPlayer makePersistent(CharacterPlayer entity) throws DatabaseException {
		CharacterPlayer player = super.makePersistent(entity);
		removeOrphanRolls();
		return player;
	}

	@Override
	public void makeTransient(CharacterPlayer entity) {
		super.makeTransient(entity);
		removeOrphanRolls();
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
	protected void initializeSets(List<CharacterPlayer> elements) {
		for (CharacterPlayer player : elements) {
			// Initializes the sets for lazy-loading (within the same session)
			Hibernate.initialize(player.getCharacteristicInitialTemporalValue());
			Hibernate.initialize(player.getCharacteristicPotencialValue());
			Hibernate.initialize(player.getCharacteristicsTemporalUpdatesRolls());
			Hibernate.initialize(player.getSelectedPerks());
			Hibernate.initialize(player.getPerkDecisions());
			Hibernate.initialize(player.getLevelUps());
			Hibernate.initialize(player.getEnabledSkill());
			Hibernate.initialize(player.getMagicItems());
			Hibernate.initialize(player.getTrainingDecisions());
			Hibernate.initialize(player.getHistorial());
			Hibernate.initialize(player.getRealmOfMagic());
			Hibernate.initialize(player.getProfessionDecisions());
			Hibernate.initialize(player.getCultureDecisions());
		}
	}

	/**
	 * As RollGroup cannot be a @OneToMany but @ManyToMany due to hibernate
	 * issue, we need to disable orphan removal and implement by this method.
	 */
	private void removeOrphanRolls() {

	}
}
