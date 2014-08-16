package com.softwaremagico.librodeesher.pj.export.txt;

import java.util.List;

import com.softwaremagico.librodeesher.pj.CharacterPlayer;
import com.softwaremagico.librodeesher.pj.characteristic.Characteristic;
import com.softwaremagico.librodeesher.pj.characteristic.Characteristics;
import com.softwaremagico.librodeesher.pj.characteristic.CharacteristicsAbbreviature;
import com.softwaremagico.librodeesher.pj.perk.Perk;
import com.softwaremagico.librodeesher.pj.resistance.ResistanceType;
import com.softwaremagico.librodeesher.pj.skills.Skill;
import com.softwaremagico.librodeesher.pj.training.TrainingItem;

public class TxtSheet {
	private CharacterPlayer characterPlayer;

	public TxtSheet() {
	}

	/**
	 * Genera un texto con el nombre, raza, profesion y otros detalles del
	 * characterPlayer.
	 */
	public static String basicCharacterInfo(CharacterPlayer characterPlayer) {
		return characterPlayer.getName() + "\tNº "
				+ characterPlayer.getCurrentLevelNumber() + "\n" + characterPlayer.getRace().getName() + "\n"
				+ characterPlayer.getProfession().getName() + "\n";
	}

	private static String getCharacteristicsInfo(CharacterPlayer characterPlayer) {
		List<Characteristic> characteristics = Characteristics.getCharacteristics();
		String text = "Caract\tTemp\tPot\tTot\tRaza\tEsp\tTotal\n";
		text += "---------------------------------------------------------------------------------\n";
		for (int i = 0; i < characteristics.size(); i++) {
			text = text
					+ characteristics.get(i).getAbbreviature().getAbbreviature()
					+ "\t"
					+ characterPlayer.getCharacteristicTemporalValue(characteristics.get(i)
							.getAbbreviature())
					+ "\t"
					+ characterPlayer.getCharacteristicPotencialValue().get(
							characteristics.get(i).getAbbreviature())
					+ "\t"
					+ characterPlayer.getCharacteristicTemporalBonus(characteristics.get(i)
							.getAbbreviature())
					+ "\t"
					+ characterPlayer.getCharacteristicRaceBonus(characteristics.get(i).getAbbreviature())
					+ "\t"
					+ characterPlayer.getCharacteristicSpecialBonus(characteristics.get(i).getAbbreviature()) + "\t"
					+ characterPlayer.getCharacteristicTotalBonus(characteristics.get(i).getAbbreviature()) + "\n";
		}
		return text;
	}

	public String ExportarATextoHabilidades() {
String text = "Nombre: "
+ "\tCoste\tRang\tBon\tCar\tOtros\tTotal\n";
text += "------------------------------------------------------------------"
+ "------------------------------------------------------------------"
+ "-------------------------------------------------------------\n";
int sizeMaxIncrease = 10;
int maxNameSize = characterPlayer.DevolverTama��oMaximoNombreCategoriasYHabilidades();
for (int i = 0; i < characterPlayer.categorias.size(); i++) {
Category cat = characterPlayer.categorias.get(i);
if (cat.MereceLaPenaMostrar()) {
text = text + cat.DevolverNombreTama��oDeterminado(
maxNameSize + sizeMaxIncrease)
+ "\t" + cat.GenerarCadenaCosteRangos() + "\t"
+ cat.DevolverRangos() + "\t" + cat.DevolverValorRangoCategoria() + "\t"
+ cat.DevolverValorCaracteristicas() + "\t" + cat.DevolverBonuses();
String letra = "";
if (cat.historial) {
letra += "H";
}
if (cat.DevolverBonusTalentos() != 0) {
letra += "T";
}
if (characterPlayer.ExisteObjetoModificaCategoria(cat)) {
letra += "O";
}
if (!letra.equals("")) {
text += "(" + letra + ")";
}
text += "\t" + cat.Total() + "\n";
for (int j = 0; j < cat.listaHabilidades.size(); j++) {
Skill hab = cat.listaHabilidades.get(j);
if (hab.MereceLaPenaImprimir()) {
text = text + " * " + hab.DevolverNombreTama��oDeterminado(
maxNameSize + sizeMaxIncrease - 5);
text = text + "\t" + "\t"
+ hab.DevolverRangos() + "\t" + hab.DevolverValorRangoHabilidad()
+ "\t" + cat.Total() + "\t"
+ hab.DevolverBonuses();
letra = "";
if (hab.historial) {
letra += "H";
}
int bonusTalentos = hab.DevolverBonusTalentos();
int siempreTalento = hab.DevolverBonusTemporalTalentos();
if (bonusTalentos != 0) {
letra += "T";
if (siempreTalento > 0) {
letra += "*";
}
}
if (characterPlayer.ExisteObjetoModificaHabilidad(hab)) {
letra += "O";
}
if (!letra.equals("")) {
text += "(" + letra + ")";
}
if (hab.DevolverBonusObjetos() > 0 || siempreTalento > 0) {
text += "\t" + (hab.Total() - hab.DevolverBonusObjetos() - siempreTalento) + "/" + hab.Total() + "";
} else {
text += "\t" + hab.Total();
}
text += "\n";
//Mostramos las habilidades especializadas.
for (int m = 0; m < hab.RangosGastadosEnEspecializacion(); m++) {
text = text + " * " + hab.DevolverEspecializacionTama��oDeterminado(
maxNameSize + sizeMaxIncrease, m);
text = text + "\t" + "\t"
+ hab.DevolverRangosEspecializacion() + "\t" + hab.DevolverValorRangoHabilidadEspecializacion()
+ "\t" + cat.Total() + "\t"
+ hab.DevolverBonuses();
letra = "";
if (hab.historial) {
letra += "H";
}
bonusTalentos = hab.DevolverBonusTalentos();
siempreTalento = hab.DevolverBonusTemporalTalentos();
if (bonusTalentos != 0) {
letra += "T";
if (siempreTalento > 0) {
letra += "*";
}
}
if (!letra.equals("")) {
text += "(" + letra + ")";
}
if (hab.DevolverBonusObjetos() > 0 || siempreTalento > 0) {
text += "\t" + (hab.TotalEspecializacion() - hab.DevolverBonusObjetos() - siempreTalento) + "/" + hab.TotalEspecializacion() + "";
} else {
text += "\t" + hab.TotalEspecializacion();
}
text += "\n";
}
} else {
text = text + "";
}
}
}
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

	public String ExportarTRs() {
		String texto = "Modificación a las TR\n";
		texto += "--------------------------------------------------\n";
		texto += "Canalización \t"
				+ (characterPlayer
						.getResistanceBonus(ResistanceType.CANALIZATION))
				+ "\n";
		texto += "Esencia \t"
				+ (characterPlayer.getResistanceBonus(ResistanceType.ESSENCE))
				+ "\n";
		texto += "Mentalismo \t"
				+ (characterPlayer.getResistanceBonus(ResistanceType.MENTALISM))
				+ "\n";
		texto += "Psiónico \t"
				+ characterPlayer.getResistanceBonus(ResistanceType.PSIONIC)
				+ "\n";
		texto += "Veneno \t"
				+ (characterPlayer.getResistanceBonus(ResistanceType.POISON))
				+ "\n";
		texto += "Enfermedad \t"
				+ (characterPlayer.getResistanceBonus(ResistanceType.DISEASE))
				+ "\n";
		texto += "Miedo \t"
				+ (characterPlayer.getResistanceBonus(ResistanceType.FEAR))
				+ "\n";
		texto += "Frío \t"
				+ characterPlayer.getResistanceBonus(ResistanceType.COLD)
				+ "\n";
		texto += "Calor \t"
				+ characterPlayer.getResistanceBonus(ResistanceType.HOT) + "\n";
		return texto;
	}

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

	/**
	 * Exporta un personaje a txt.
	 */
	public boolean ExportarPersonaje(String file) {
		if (file == null || file.equals("")) {
			file = characterPlayer.DevolverNombreCompleto() + "_N"
					+ characterPlayer.nivel + "_" + characterPlayer.raza + "_"
					+ characterPlayer.profesion + ".txt";
		}
		if (!file.endsWith(".txt")) {
			file += ".txt";
		}
		String texto = basicCharacterInfo() + "\n\n"
				+ getCharacteristicsInfo() + "\n\n" + ExportarTRs()
				+ "\n\n" + ExportarATextoHabilidades() + "\n\n" + exportPerks()
				+ "\n\n" + exportSpecials() + "\n\n" + exportItems();
		DirectorioRolemaster.GuardarEnFichero(texto, file);
		return true;
	}

	private String getSizeCode() {
if (characterPlayer.tama��o.equals("Muy Peque��o")) {
return "MP";
}
if (characterPlayer.tama��o.equals("Peque��o")) {
return "P";
}
if (characterPlayer.tama��o.equals("Mediano") || characterPlayer.tama��o.equals("Normal") || characterPlayer.tama��o.equals("Medio")) {
return "M";
}
if (characterPlayer.tama��o.equals("Grande")) {
return "G";
}
if (characterPlayer.tama��o.equals("Enorme") || characterPlayer.tama��o.equals("Muy Grande")) {
return "EN";
}
return "";
}

	private Skill BuscarMejorArma() {
		int maximo = 0;
		Skill maximoAtaque = null;
		for (int i = 0; i < characterPlayer.categorias.size(); i++) {
			Category cat = characterPlayer.categorias.get(i);
			if (cat.DevolverNombre().equals("Armas��2manos")
					|| cat.DevolverNombre().equals("Armas��Contundentes")
					|| cat.DevolverNombre().equals("Armas��Asta")
					|| cat.DevolverNombre().equals("Armas��Filo")) {
				for (int j = 0; j < cat.listaHabilidades.size(); j++) {
					Skill hab = cat.listaHabilidades.get(j);
					if (maximoAtaque == null) {
						maximoAtaque = hab;
					} else {
						if (hab.Total() > maximoAtaque.Total()) {
							maximoAtaque = hab;
						}
					}
				}
			}
		}
		return maximoAtaque;
	}

	private String DevolverCodigoAtaque(Skill hab) {
		if (hab.DevolverNombre().equals("Arma de Asta")) {
			return "aa";
		}
		if (hab.DevolverNombre().equals("Jabalina")) {
			return "la";
		}
		if (hab.DevolverNombre().equals("Lanza")) {
			return "ja";
		}
		if (hab.DevolverNombre().equals("Lanza de Caballer��a")) {
			return "lc";
		}
		if (hab.DevolverNombre().equals("Cayado")) {
			return "ca";
		}
		if (hab.DevolverNombre().equals("Espad��n")) {
			return "e2";
		}
		if (hab.DevolverNombre().equals("Hacha de Batalla")) {
			return "hb";
		}
		if (hab.DevolverNombre().equals("Mangual")) {
			return "mg";
		}
		if (hab.DevolverNombre().equals("Pica de Guerra")) {
			return "pc";
		}
		if (hab.DevolverNombre().equals("Boleadoras")) {
			return "bo";
		}
		if (hab.DevolverNombre().equals("Daga Arrojadiza")) {
			return "daA";
		}
		if (hab.DevolverNombre().equals("Estrella de la Ma��ana")) {
			return "em";
		}
		if (hab.DevolverNombre().equals("Garrote")) {
			return "ga";
		}
		if (hab.DevolverNombre().equals("Látigo")) {
			return "lg";
		}
		if (hab.DevolverNombre().equals("Martillo de Guerra")) {
			return "mr";
		}
		if (hab.DevolverNombre().equals("Maza")) {
			return "ma";
		}
		if (hab.DevolverNombre().equals("Alfanje")) {
			return "af";
		}
		if (hab.DevolverNombre().equals("Cimitarra")) {
			return "ci";
		}
		if (hab.DevolverNombre().equals("Daga")) {
			return "da";
		}
		if (hab.DevolverNombre().equals("Espada")) {
			return "ea";
		}
		if (hab.DevolverNombre().equals("Espada Corta")) {
			return "ec";
		}
		if (hab.DevolverNombre().equals("Estoque")) {
			return "et";
		}
		if (hab.DevolverNombre().equals("Hacha de Mano")) {
			return "hm";
		}
		if (hab.DevolverNombre().equals("Main Gauche")) {
			return "mg";
		}
		if (hab.DevolverNombre().equals("Sable")) {
			return "sb";
		}
		if (hab.DevolverNombre().equals("Arco Compuesto")) {
			return "am";
		}
		if (hab.DevolverNombre().equals("Arco Corto")) {
			return "ac";
		}
		if (hab.DevolverNombre().equals("Arco Largo")) {
			return "al";
		}
		if (hab.DevolverNombre().equals("Ballesta Ligera")) {
			return "ba";
		}
		if (hab.DevolverNombre().equals("Ballesta Pesada")) {
			return "bp";
		}
		if (hab.DevolverNombre().equals("Honda")) {
			return "ho";
		}
		if (hab.DevolverNombre().equals("Barridos Grado 1")) {
			return "bar1";
		}
		if (hab.DevolverNombre().equals("Barridos Grado 2")) {
			return "bar2";
		}
		if (hab.DevolverNombre().equals("Barridos Grado 3")) {
			return "bar3";
		}
		if (hab.DevolverNombre().equals("Barridos Grado 4")) {
			return "bar4";
		}
		if (hab.DevolverNombre().equals("Golpes Grado 1")) {
			return "go1";
		}
		if (hab.DevolverNombre().equals("Golpes Grado 2")) {
			return "go2";
		}
		if (hab.DevolverNombre().equals("Golpes Grado 3")) {
			return "go3";
		}
		if (hab.DevolverNombre().equals("Golpes Grado 4")) {
			return "go4";
		}
		if (hab.DevolverNombre().equals("Boxeo")) {
			return "bx";
		}
		if (hab.DevolverNombre().equals("Placaje")) {
			return "pj";
		}
		if (hab.DevolverNombre().equals("Ataque Racial: Extremidades")) {
			return getSizeCode() + "Ga";
		}
		if (hab.DevolverNombre().equals("Ataque Racial: Boca")) {
			return getSizeCode() + "Mo";
		}
		if (hab.DevolverNombre().equals("Ataque Racial: Otros")) {
			return "??";
		}
		return "";
	}

	private Skill BuscarMejorDisparo() {
		int maximo = 0;
		Skill maximoAtaque = null;
		for (int i = 0; i < characterPlayer.categorias.size(); i++) {
			Category cat = characterPlayer.categorias.get(i);
			if (cat.DevolverNombre().equals("Armas��Proyectiles")
					|| cat.DevolverNombre().equals("Armas��Arrojadizas")
					|| cat.DevolverNombre().equals("Armas��Fuego")) {
				for (int j = 0; j < cat.listaHabilidades.size(); j++) {
					Skill hab = cat.listaHabilidades.get(j);
					if (maximoAtaque == null) {
						maximoAtaque = hab;
					} else {
						if (hab.Total() > maximoAtaque.Total()) {
							maximoAtaque = hab;
						}
					}
				}
			}
		}
		return maximoAtaque;
	}

	private Skill BuscarMejorAtaque() {
		int maximo = 0;
		Skill maximoAtaque = null;
		for (int i = 0; i < characterPlayer.categorias.size(); i++) {
			Category cat = characterPlayer.categorias.get(i);
			if (cat.DevolverNombre().equals("Artes Marciales��Golpes")
					|| cat.DevolverNombre().equals("Artes Marciales��Barridos")
					|| cat.DevolverNombre().equals("Ataques Especiales")) {
				for (int j = 0; j < cat.listaHabilidades.size(); j++) {
					Skill hab = cat.listaHabilidades.get(j);
					if (maximoAtaque == null) {
						maximoAtaque = hab;
					} else {
						if (hab.Total() > maximoAtaque.Total()) {
							maximoAtaque = hab;
						}
					}
				}
			}
		}
		return maximoAtaque;
	}

	private String ObtenerCodigoVelocidad() {
		int rapidez = characterPlayer
				.DevolverBonusCaracteristicaDeAbreviatura("Rp");
		if (rapidez <= -14) {
			return "IM";
		}
		if (rapidez <= -10) {
			return "AR";
		}
		if (rapidez <= -6) {
			return "ML";
		}
		if (rapidez <= -2) {
			return "L";
		}
		if (rapidez <= 2) {
			return "N";
		}
		if (rapidez <= 6) {
			return "MdR";
		}
		if (rapidez <= 10) {
			return "R";
		}
		if (rapidez <= 14) {
			return "MR";
		}
		return "RS";
	}

	/**
	 * Genera una version del personaje del mismo m��todo que en el criaturas y
	 * tesoros.
	 */
	private String GenerarCaracteristicasTipoMonstruo() {
		Skill habCC = BuscarMejorArma();
		Skill habProy = BuscarMejorDisparo();
		Skill habAtaq = BuscarMejorAtaque();
		String vel = ObtenerCodigoVelocidad();
		int PV = characterPlayer
				.DevolverHabilidadDeNombre("Desarrollo F��sico").Total();
		String texto = "Raza\tNivel\tMov.\tMM\tVM/VA\tTam\tPV\tTA(BD)\tAtaques\n";
		texto += characterPlayer.raza
				+ "\t"
				+ characterPlayer.nivel
				+ "\t"
				+ characterPlayer.DevolverCapacidadMovimiento()
				+ "\t"
				+ characterPlayer
						.DevolverBonusCaracteristicaDeAbreviatura("Ag") * 3
				+ "\t" + vel + "\t" + getSizeCode() + "\t" + PV + "\t "
				+ characterPlayer.DevolverTA() + " ("
				+ characterPlayer.DevolverBD() + ")\t" + habCC.Total()
				+ DevolverCodigoAtaque(habCC);
		if (habProy.Total() > 0) {
			texto += "/" + habProy.Total() + DevolverCodigoAtaque(habProy);
		}
		if (habAtaq.Total() > 0) {
			texto += "/" + habAtaq.Total() + DevolverCodigoAtaque(habAtaq)
					+ " \n";
		}
		return texto;
	}

	private String GenerarAbreviaturaHabilidades() {
		String texto = "";
		for (int i = 0; i < characterPlayer.categorias.size(); i++) {
			Category cat = characterPlayer.categorias.get(i);
			texto += cat.DevolverAbreviatura() + " " + cat.Total();
			int added = 0;
			for (int j = 0; j < cat.listaHabilidades.size(); j++) {
				Skill hab = cat.listaHabilidades.get(j);
				if (hab.DevolverRangos() > 0) {
					if (added == 0) {
						texto += " (";
					}
					if (added > 0) {
						texto += ", ";
					}
					texto += hab.DevolverNombre() + " " + hab.Total();
					added++;
				}
			}
			if (added > 0) {
				texto += ")";
			}
			if (i < characterPlayer.categorias.size() - 1) {
				texto += ", ";
			}
			if (i == characterPlayer.categorias.size() - 1) {
				texto += ".";
			}
		}
		return texto;
	}

	public boolean ExportarAbreviaturaPersonaje(String file) {
		if (file == null || file.equals("")) {
			file = characterPlayer.getName() + "_N"
					+ characterPlayer.getCurrentLevelNumber() + "_"
					+ characterPlayer.getRace().getName() + "_"
					+ characterPlayer.getProfession().getName() + ".txt";
		}
		if (!file.endsWith(".txt")) {
			file += ".txt";
		}
		String texto = characterPlayer.getName() + "\n"
				+ GenerarCaracteristicasTipoMonstruo() + "\n"
				+ "HABILIDADES: \n" + GenerarAbreviaturaHabilidades();
		DirectorioRolemaster.GuardarEnFichero(texto, file);
		return true;
	}
}