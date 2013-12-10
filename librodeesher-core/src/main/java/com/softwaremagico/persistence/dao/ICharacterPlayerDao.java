package com.softwaremagico.persistence.dao;

import java.util.List;

import com.softwaremagico.librodeesher.pj.CharacterPlayer;

public interface ICharacterPlayerDao {

	List<CharacterPlayer> getAll();

	CharacterPlayer read(Long id);

	CharacterPlayer makePersistent(CharacterPlayer character);

	void makeTransient(CharacterPlayer character);

}
