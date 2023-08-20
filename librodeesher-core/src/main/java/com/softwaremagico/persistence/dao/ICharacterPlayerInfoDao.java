package com.softwaremagico.persistence.dao;

import java.util.List;

import org.hibernate.SessionFactory;

import com.softwaremagico.librodeesher.pj.CharacterPlayerInfo;

public interface ICharacterPlayerInfoDao {

	SessionFactory getSessionFactory();

	void setSessionFactory(SessionFactory sessionFactory);

	int getRowCount();

	List<CharacterPlayerInfo> getAll();

}
