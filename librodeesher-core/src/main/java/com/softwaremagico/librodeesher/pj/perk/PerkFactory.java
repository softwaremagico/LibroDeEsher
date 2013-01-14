package com.softwaremagico.librodeesher.pj.perk;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;

import com.softwaremagico.files.Folder;
import com.softwaremagico.files.RolemasterFolderStructure;
import com.softwaremagico.librodeesher.basics.ShowMessage;

public class PerkFactory {
	private static final String PERKS_FILE = "talentos.txt";
	private static final String PERKS_FOLDER = "talentos";
	private static Hashtable<String, Perk> availablePerks = new Hashtable<>();
	private static List<Perk> perksList = new ArrayList<>();

	static {
		try {
			getPerksFromFile();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void getPerksFromFile() throws Exception {
		int lineIndex = 0;
		String perkFile = RolemasterFolderStructure.getDirectoryModule(PERKS_FOLDER + File.separator
				+ PERKS_FILE);
		if (perkFile.length() > 0) {
			List<String> lines = Folder.readFileLines(perkFile, false);
			lineIndex = setPerks(lines, lineIndex);
		}
	}

	private static int setPerks(List<String> lines, int index) {
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}
		while (!lines.get(index).equals("") && !lines.get(index).startsWith("#")) {
			String line = lines.get(index);
			try {
				String[] descomposed_line = line.split("\t");
				String perkName = descomposed_line[0];
				Integer cost = Integer.parseInt(descomposed_line[1]);
				String classification = descomposed_line[2];
				String bonuses = descomposed_line[3];
				String allowed = descomposed_line[4];
				String description = descomposed_line[5];
				Perk perk = new Perk(perkName, cost, classification, description, allowed);
				perk.addBonuses(bonuses);
				availablePerks.put(perk.getName(), perk);
				perksList.add(perk);
			} catch (NumberFormatException npe) {
				npe.printStackTrace();
				ShowMessage.showErrorMessage("Error en el coste del talento de la línea:\"" + line + "\"",
						"Talentos");
			} catch (ArrayIndexOutOfBoundsException aiob) {
				ShowMessage.showErrorMessage("Error en el talento de la línea:\"" + line + "\"", "Talentos");
			}
			index++;
		}
		Collections.sort(perksList, new PerkComparator());
		return index;
	}

	public Perk getPerk(String name) {
		return availablePerks.get(name);
	}
	
	
}
