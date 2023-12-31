package com.softwaremagico.librodeesher.pj;

import java.sql.Timestamp;

public class CharacterPlayerInfo {

	private Long id;
	private String name;
	private int createdLevel;
	private int insertedLevel;
	private SexType sex;
	private String raceName;
	private String cultureName;
	private String professionName;
	private Timestamp creationTime = null;
	private Timestamp updateTime = null;

	public static CharacterPlayerInfo getCharacterPlayerInfo(CharacterPlayer characterPlayer) {
		CharacterPlayerInfo characterPlayerInfo = new CharacterPlayerInfo();
		characterPlayerInfo.setId(characterPlayer.getId());
		characterPlayerInfo.setName(characterPlayer.getName());
		characterPlayerInfo.setCreatedLevel(characterPlayer.getCurrentLevelNumber());
		characterPlayerInfo.setInsertedLevel(characterPlayer.getInsertedLevel());
		characterPlayerInfo.setSex(characterPlayer.getSex());
		characterPlayerInfo.setRaceName(characterPlayer.getRaceName());
		characterPlayerInfo.setCultureName(characterPlayer.getCultureName());
		characterPlayerInfo.setProfessionName(characterPlayer.getProfessionName());
		characterPlayerInfo.setCreationTime(characterPlayer.getCreationTime());
		characterPlayerInfo.setUpdateTime(characterPlayer.getUpdateTime());

		return characterPlayerInfo;
	}

	@Override
	public String toString() {
		return name + " " + professionName + " " + raceName + " " + sex + " " + cultureName + " " + id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCreatedLevel() {
		return createdLevel;
	}
	
	public int getLevel(){
		return createdLevel + insertedLevel;
	}

	public void setCreatedLevel(int level) {
		this.createdLevel = level;
	}

	public SexType getSex() {
		return sex;
	}

	public void setSex(SexType sex) {
		this.sex = sex;
	}

	public String getRaceName() {
		return raceName;
	}

	public void setRaceName(String raceName) {
		this.raceName = raceName;
	}

	public String getCultureName() {
		return cultureName;
	}

	public void setCultureName(String cultureName) {
		this.cultureName = cultureName;
	}

	public String getProfessionName() {
		return professionName;
	}

	public void setProfessionName(String professionName) {
		this.professionName = professionName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cultureName == null) ? 0 : cultureName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + createdLevel;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((professionName == null) ? 0 : professionName.hashCode());
		result = prime * result + ((raceName == null) ? 0 : raceName.hashCode());
		result = prime * result + ((sex == null) ? 0 : sex.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CharacterPlayerInfo other = (CharacterPlayerInfo) obj;
		if (cultureName == null) {
			if (other.cultureName != null)
				return false;
		} else if (!cultureName.equals(other.cultureName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (createdLevel != other.createdLevel)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (professionName == null) {
			if (other.professionName != null)
				return false;
		} else if (!professionName.equals(other.professionName))
			return false;
		if (raceName == null) {
			if (other.raceName != null)
				return false;
		} else if (!raceName.equals(other.raceName))
			return false;
		if (sex != other.sex)
			return false;
		return true;
	}

	public int getInsertedLevel() {
		return insertedLevel;
	}

	public void setInsertedLevel(int insertedLevel) {
		this.insertedLevel = insertedLevel;
	}

	public Timestamp getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Timestamp creationTime) {
		this.creationTime = creationTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

}
