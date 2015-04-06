package com.softwaremagico.persistence.dao.hibernate;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.softwaremagico.librodeesher.pj.CharacterPlayerInfo;
import com.softwaremagico.librodeesher.pj.SexType;
import com.softwaremagico.persistence.HibernateInitializator;
import com.softwaremagico.persistence.dao.ICharacterPlayerInfoDao;

public class CharacterPlayerInfoDao implements ICharacterPlayerInfoDao {
	private static CharacterPlayerInfoDao instance = null;

	private Class<CharacterPlayerInfo> type;

	private SessionFactory sessionFactory = null;

	public CharacterPlayerInfoDao() {
		this.type = CharacterPlayerInfo.class;
	}

	public Class<CharacterPlayerInfo> getType() {
		return type;
	}

	@Override
	public SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			sessionFactory = HibernateInitializator.getSessionFactory();
		}
		return sessionFactory;
	}

	@Override
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public static CharacterPlayerInfoDao getInstance() {
		if (instance == null) {
			synchronized (CharacterPlayerInfoDao.class) {
				if (instance == null) {
					instance = new CharacterPlayerInfoDao();
				}
			}
		}
		return instance;
	}

	@Override
	public int getRowCount() {
		Session session = getSessionFactory().getCurrentSession();
		session.beginTransaction();
		SQLQuery query = session.createSQLQuery("SELECT COUNT(*) FROM T_CHARACTERPLAYER");

		BigInteger count = (BigInteger) query.uniqueResult();
		session.getTransaction().commit();
		return count.intValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CharacterPlayerInfo> getAll() {
		Session session = getSessionFactory().getCurrentSession();
		session.beginTransaction();
		SQLQuery query = session
				.createSQLQuery("SELECT cp.ID, cp.name, cp.raceName, cp.professionName, cp.cultureName, cp.sex, characterLevel.levelNumber, insertedCharacter.insertLevel "
						+ "from T_CHARACTERPLAYER cp "
						+ "INNER JOIN "
						+ "(SELECT insertedLevels as insertLevel, ID insID FROM T_INSERTED_DATA ins) as insertedCharacter on cp.insertedDataId=insID "
						+ "INNER JOIN "
						+ "(SELECT COUNT(*) as levelNumber, T_CHARACTERPLAYER_ID as characterID from T_CHARACTERPLAYER_LEVEL_UP cl "
						+ "GROUP BY cl.T_CHARACTERPLAYER_ID) as characterLevel " + "on characterID = cp.ID;");

		List<Object[]> rows = query.list();

		session.getTransaction().commit();

		List<CharacterPlayerInfo> characterPlayers = new ArrayList<>();
		for (Object[] row : rows) {
			CharacterPlayerInfo characterPlayer = new CharacterPlayerInfo();
			characterPlayer.setId(((BigInteger) row[0]).longValue());
			characterPlayer.setName((String) row[1]);
			characterPlayer.setRaceName((String) row[2]);
			characterPlayer.setProfessionName((String) row[3]);
			characterPlayer.setCultureName((String) row[4]);
			characterPlayer.setSex(SexType.fromString((String) row[5]));
			characterPlayer.setCreatedLevel(((BigInteger) row[6]).intValue());
			characterPlayer.setInsertedLevel((Integer) row[7]);
			characterPlayers.add(characterPlayer);
		}

		return characterPlayers;
	}
}
