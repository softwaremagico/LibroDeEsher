package com.softwaremagico.persistence.dao.hibernate;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.softwaremagico.librodeesher.basics.Roll;
import com.softwaremagico.librodeesher.basics.RollGroup;
import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.characteristic.CharacteristicRoll;
import com.softwaremagico.persistence.dao.ICharacterPlayerDao;

public class CharacterPlayerDao extends GenericDao<CharacterPlayer> implements ICharacterPlayerDao {
	private static CharacterPlayerDao instance = null;

	public CharacterPlayerDao() {
		super(CharacterPlayer.class);
	}

	@Override
	public CharacterPlayer makePersistent(CharacterPlayer entity) throws DatabaseException {
		// Imported from TXT causes to have more than one character with same
		// comparationId but different id. Remove any previous imported
		// character if exists.
		if (entity.getComparationId() != null && entity.getId() == null) {
			CharacterPlayer previousCharacter = getByComparationId(entity.getComparationId());
			if (previousCharacter != null) {
				makeTransient(previousCharacter);
			}
		}
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
			if (player != null) {
				// Initializes the sets for lazy-loading (within the same
				// session)
				Hibernate.initialize(player.getCharacteristicInitialTemporalValue());
				Hibernate.initialize(player.getCharacteristicPotencialValue());
				Hibernate.initialize(player.getCharacteristicsTemporalUpdatesRolls());
				Hibernate.initialize(player.getSelectedPerks());
				Hibernate.initialize(player.getPerkDecisions());
				Hibernate.initialize(player.getLevelUps());
				Hibernate.initialize(player.getEnabledSkill());
				Hibernate.initialize(player.getAllMagicItems());
				Hibernate.initialize(player.getTrainingDecisions());
				Hibernate.initialize(player.getBackground());
				Hibernate.initialize(player.getRealmOfMagic());
				Hibernate.initialize(player.getProfessionDecisions());
				Hibernate.initialize(player.getCultureDecisions());
				Hibernate.initialize(player.getStandardEquipment());
			}
		}
	}

	/**
	 * As RollGroup cannot be a @OneToMany but @ManyToMany due to hibernate
	 * issue, we need to disable orphan removal and implement by this method.
	 */
	private void removeOrphanRolls() {
		Session session = getSessionFactory().getCurrentSession();
		session.beginTransaction();
		String sql = "DELETE FROM T_ROLL WHERE ID NOT IN (SELECT r.rolls_ID FROM T_ROLL_LIST r) AND ID NOT IN (SELECT crg.roll_ID FROM T_CHARACTERISTIC_ROLL_GROUP crg)";

		SQLQuery query = session.createSQLQuery(sql).addEntity(Roll.class).addEntity(CharacteristicRoll.class).addEntity(RollGroup.class);
		query.executeUpdate();
		session.getTransaction().commit();
	}
}
