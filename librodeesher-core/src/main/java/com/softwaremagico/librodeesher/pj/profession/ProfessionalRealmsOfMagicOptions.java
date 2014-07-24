package com.softwaremagico.librodeesher.pj.profession;

/*
 * #%L
 * Libro de Esher
 * %%
 * Copyright (C) 2007 - 2013 Softwaremagico
 * %%
 * This software is designed by Jorge Hortelano Otero. Jorge Hortelano Otero
 * <softwaremagico@gmail.com> C/Quart 89, 3. Valencia CP:46008 (Spain).
 *  
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *  
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *  
 * You should have received a copy of the GNU General Public License along with
 * this program; If not, see <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.softwaremagico.librodeesher.basics.ShowMessage;
import com.softwaremagico.librodeesher.pj.magic.RealmOfMagic;
import com.softwaremagico.persistence.StorableObject;

@Entity
@Table(name = "T_PROFESSIONS_REALMS_MAGIC")
public class ProfessionalRealmsOfMagicOptions extends StorableObject {
	// Standard spellers will have only one element, hybrids will have two or
	// more elements.
	@ElementCollection
	@LazyCollection(LazyCollectionOption.FALSE)
	@CollectionTable(name = "T_PROFESSION_REALMS_OF_MAGIC_AVAILABLE")
	private List<RealmOfMagic> magicRealmsAvailable;

	public ProfessionalRealmsOfMagicOptions() {
		magicRealmsAvailable = new ArrayList<>();
	}
	
	@Override
	public void resetIds(){
		resetIds(this);
	}

	public void add(String realms, String professionName) {
		String[] hybridRealms = realms.split(Pattern.quote("/"));
		for (String realm : hybridRealms) {
			RealmOfMagic realmType = RealmOfMagic.getMagicRealm(realm);
			if (realmType != null) {
				magicRealmsAvailable.add(realmType);
			} else {
				ShowMessage.showErrorMessage("Problemas con el reino de magia " + realms + " mostrada en el archivo "
						+ professionName + ".txt.", "Leer Profesion");
			}
		}
		Collections.sort(magicRealmsAvailable);
	}

	public void add(RealmOfMagic realm) {
		magicRealmsAvailable.add(realm);
	}

	public List<RealmOfMagic> getRealmsOfMagic() {
		return magicRealmsAvailable;
	}

	@Override
	public String toString() {
		String value = "";
		for (int i = 0; i < magicRealmsAvailable.size(); i++) {
			value += magicRealmsAvailable.get(i);
			if (i < magicRealmsAvailable.size() - 1) {
				value += "/";
			}
		}
		return value;
	}

	protected List<RealmOfMagic> getMagicRealmsAvailable() {
		return magicRealmsAvailable;
	}

	protected void setMagicRealmsAvailable(List<RealmOfMagic> magicRealmsAvailable) {
		this.magicRealmsAvailable = magicRealmsAvailable;
	}

}
