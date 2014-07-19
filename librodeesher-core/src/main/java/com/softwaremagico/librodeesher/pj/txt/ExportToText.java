package com.softwaremagico.librodeesher.pj.txt;

import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.perk.Perk;
import com.softwaremagico.librodeesher.pj.training.TrainingItem;

public class ExportToText {

	public static String exportSpecials(CharacterPlayer characterPlayer) {
		String text = "Especiales:\n";
		text += "--------------------------------------------------\n";
		for (int i = 0; i < characterPlayer.getRace().getSpecials().size(); i++) {
			text += characterPlayer.getRace().getSpecials().get(i) + "\n\n";
		}
		text = text.replaceAll("\t", "  ");
		return text;
	}

	public static String exportPerks(CharacterPlayer characterPlayer) {
		String text = "Talentos:\n";
		text += "--------------------------------------------------\n";
		for (int i = 0; i < characterPlayer.getPerks().size(); i++) {
			Perk perk = characterPlayer.getPerks().get(i);
			text += perk.getName() + ":\t " + perk.getLongDescription()
					+ ".\n\n";
		}
		return text;
	}

	public static String exportItems(CharacterPlayer characterPlayer) {
		String text = "";
		text = text.replaceAll("\t", "  ");
		if (characterPlayer.getEquipment().size() > 0) {
			text = "Equipo:\n";
			text += "--------------------------------------------------\n";
			for (int i = 0; i < characterPlayer.getEquipment().size(); i++) {
				text += characterPlayer.getEquipment().get(i) + "\n\n";
			}
			text += "\n";
		}

		if (characterPlayer.getMagicItems().size() > 0) {
			text = "Objetos:\n";
			text += "--------------------------------------------------\n";
			for (int i = 0; i < characterPlayer.getMagicItems().size(); i++) {
				TrainingItem magicItem = characterPlayer.getMagicItems().get(i);
				// TODO add characteristics of object
				text += magicItem.getName() + ": "; // +
													// magicItem.DevolverPropiedades();
				text += "\n\n";
			}
			text += "\n";
		}
		return text;
	}
}
