package com.softwaremagico.librodeesher.pj.magic;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.softwaremagico.librodeesher.gui.ShowMessage;

public class SpellList {
	private String name;
	private List<String> professions;
	private RealmOfMagic realm;

	SpellList(String name, String profession, RealmOfMagic realm) {
		this.name = name;
		this.realm = realm;

		professions = new ArrayList<>();
		String pattern = Pattern.quote("/");
		String[] professionColumns = profession.split(pattern);
		for (String professionDefined : professionColumns) {
			professions.add(professionDefined);
		}
	}

	public String getName() {
		return name;
	}

	public List<String> getProfessions() {
		return professions;
	}

	public RealmOfMagic getRealm() {
		return realm;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		SpellList other = (SpellList) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}