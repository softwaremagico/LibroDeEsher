package com.softwaremagico.librodeesher.pj.profession;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import com.softwaremagico.librodeesher.gui.ShowMessage;
import com.softwaremagico.librodeesher.pj.magic.RealmOfMagic;

public class ProfessionalRealmsOfMagicOptions {
	// Standard spellers will have only one element, hybrids will have two or
	// more elements.
	private List<RealmOfMagic> magicRealmsAvailable;

	public ProfessionalRealmsOfMagicOptions() {
		magicRealmsAvailable = new ArrayList<>();
	}

	public void add(String realms, String professionName) {
		String[] hybridRealms = realms.split(Pattern.quote("/"));
		for (String realm : hybridRealms) {
			RealmOfMagic realmType = RealmOfMagic.getMagicRealm(realm);
			if (realmType != null) {
				magicRealmsAvailable.add(realmType);
			} else {
				ShowMessage.showErrorMessage("Problemas con el reino de magia " + realms
						+ " mostrada en el archivo " + professionName + ".txt.", "Leer Profesion");
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
}
