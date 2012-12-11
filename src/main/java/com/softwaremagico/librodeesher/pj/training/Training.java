package com.softwaremagico.librodeesher.pj.training;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.softwaremagico.files.Folder;
import com.softwaremagico.files.RolemasterFolderStructure;
import com.softwaremagico.librodeesher.Adiestramiento;
import com.softwaremagico.librodeesher.Caracteristica;
import com.softwaremagico.librodeesher.Esher;
import com.softwaremagico.librodeesher.Habilidad;
import com.softwaremagico.librodeesher.LeerRaza;
import com.softwaremagico.librodeesher.Personaje;
import com.softwaremagico.librodeesher.gui.MostrarMensaje;
import com.softwaremagico.librodeesher.pj.categories.CategoryFactory;
import com.softwaremagico.librodeesher.pj.characteristics.Characteristic;
import com.softwaremagico.librodeesher.pj.culture.CultureCategory;
import com.softwaremagico.librodeesher.pj.culture.CultureSkill;
import com.softwaremagico.librodeesher.pj.skills.SkillFactory;

public class Training {
	private String name;
	private Integer trainingTime;
	private List<String> limitedRaces;
	private List<TrainingSpecial> specials;
	private List<TrainingCategory> categories;
	private List<List<Characteristic>> updateCharacteristics; // Choose one
																// characteristic
																// from this
																// list.

	Training(String name) {
		this.name = name;
		try {
			readTrainingFile(name);
		} catch (Exception ex) {
			Logger.getLogger(LeerRaza.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private void readTrainingFile(String trainingName) throws Exception {
		int lineIndex = 0;
		String trainingFile = RolemasterFolderStructure.searchDirectoryModule(TrainingFactory.TRAINING_FOLDER
				+ File.separator + trainingName + ".txt");
		if (trainingFile.length() > 0) {
			List<String> lines = Folder.readFileLines(trainingFile, false);
			lineIndex = setTrainingTime(lines, lineIndex);
			lineIndex = setLimitedRaces(lines, lineIndex);
			lineIndex = setTrainingSpecial(lines, lineIndex);
			lineIndex = setTrainingSkills(lines, lineIndex);
			lineIndex = setCharacteristicsUpgrade(lines, lineIndex);
			lineIndex = setProfessionalRequirements(lines, lineIndex);
			lineIndex = LeerHabilidadesEstiloDeVida(lines, lineIndex);
			lineIndex = AsignarHabilidadesComunes(lines, lineIndex);
			lineIndex = AsignarHabilidadesProfesionales(lines, lineIndex);
			lineIndex = AsignarHabilidadesRestringidas(lines, lineIndex);
		}
	}

	public int setTrainingTime(List<String> lines, int index) {
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}
		try {
			trainingTime = Integer.parseInt(lines.get(index));
		} catch (Exception e) {
			MostrarMensaje.showErrorMessage("Problema con la linea: \"" + lines.get(index)
					+ "\" del adiestramiento " + name, "Leer adiestramientos");
		}
		return index++;
	}

	public int setLimitedRaces(List<String> lines, int index) {
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}
		limitedRaces = new ArrayList<>();
		while (!lines.get(index).equals("") && !lines.get(index).startsWith("#")) {
			String trainingLine = lines.get(index);
			try {
				String[] limitedRacesColumn = trainingLine.split(", ");
				for (int i = 0; i < limitedRacesColumn.length; i++) {
					if (!limitedRacesColumn[i].contains("Ningun")) {
						limitedRaces.add(limitedRacesColumn[i]);
					}
				}
			} catch (ArrayIndexOutOfBoundsException aiofb) {
				MostrarMensaje.showErrorMessage("Problema con la linea: \"" + trainingLine
						+ "\" del adiestramiento " + name, "Leer adiestramientos");
			}
			index++;
		}
		return index;
	}

	public int setTrainingSpecial(List<String> lines, int index) {
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}
		int bonus;
		int probability = 0;
		specials = new ArrayList<>();
		while (!lines.get(index).equals("")) {
			String trainingLine = lines.get(index);
			try {
				String[] specialColumns = trainingLine.split("\t");
				String special = specialColumns[0];
				try {
					probability = Integer.parseInt(specialColumns[1]);
					if (specialColumns.length > 2) {
						bonus = Integer.parseInt(specialColumns[2]);
					} else {
						bonus = 0;
					}
				} catch (NumberFormatException nfe) {
					MostrarMensaje.showErrorMessage("Formato de porcentaje de especial \"" + special
							+ "\" erróneo en adiestramiento " + name, "Leer adiestramientos");
					continue;
				}
				specials.add(new TrainingSpecial(special, bonus, probability));
			} catch (ArrayIndexOutOfBoundsException aiofb) {
				MostrarMensaje.showErrorMessage("Problema con la linea: \"" + trainingLine
						+ "\" del adiestramiento " + name, "Leer adiestramientos");
			}
			index++;
		}
		return index;
	}

	public int setTrainingSkills(List<String> lines, int index) {
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}
		categories = new ArrayList<>();
		TrainingCategory category = null;
		while (!lines.get(index).equals("") && !lines.get(index).startsWith("#")) {
			// It is a category
			if (!lines.get(index).startsWith("  *  ")) {
				String[] trainingSkills = lines.get(index).split("\t");
				if (CategoryFactory.existCategory(trainingSkills[0])) {
					try {
						category = new TrainingCategory(trainingSkills[0],
								Integer.parseInt(trainingSkills[1]), Integer.parseInt(trainingSkills[2]),
								Integer.parseInt(trainingSkills[3]));
						categories.add(category);
					} catch (NumberFormatException nfe) {
						MostrarMensaje.showErrorMessage(
								"Numero de rangos mal formado en: \"" + lines.get(index)
										+ "\" del adiestramiento: " + name, "Leer adiestramientos");
						continue;
					}
				} else {
					MostrarMensaje.showErrorMessage("Categoría no encontrada: " + trainingSkills[0],
							"Añadir habilidades de adiestramiento.");
				}
			} else { // It is a skill.
				if (category == null) {
					MostrarMensaje.showErrorMessage("Habilidad sin categoria asociada: " + lines.get(index),
							"Añadir habilidades de adiestramiento.");
					continue;
				}
				try {
					if (lines.get(index).contains("{")) {
						// List of skills to choose one.
						String[] lineColumns = lines.get(index).replace("*", "").trim().split("}");
						String[] skillList = lineColumns[0].replace("{", "").replace(";", ",").split(",");
						TrainingSkill skill = new TrainingSkill(Arrays.asList(skillList),
								Integer.parseInt(lineColumns[1]));
						category.addSkill(skill);
					} else {
						// Skill with ranges.
						String[] trainingSkills = lines.get(index).replace("*", "").trim().split("\t");
						List<String> skillList = new ArrayList<>();
						skillList.add(trainingSkills[0]); //List with only one element. 
						TrainingSkill skill = new TrainingSkill(skillList,
								Integer.parseInt(trainingSkills[1]));
						category.addSkill(skill);
					}
				} catch (NumberFormatException nfe) {
					MostrarMensaje.showErrorMessage("Numero de rangos mal formado en: \"" + lines.get(index)
							+ "\" del adiestramiento: " + name, "Leer adiestramientos");
					continue;
				}
			}
			index++;
		}
		return index;
	}

	private int setCharacteristicsUpgrade(List<String> lines, int index) {
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}
		updateCharacteristics = new ArrayList<>();
		while (!lines.get(index).equals("") && !lines.get(index).startsWith("#")) {
			String trainingLine = lines.get(index);
			try {
				if (trainingLine.contains("{")) {
					// List to choose a characteristic.
					List<Characteristic> listToChoose = new ArrayList<>();
					trainingLine = trainingLine.replace("}", "").replace("{", "");
					String[] chars = trainingLine.replace(";", ",").split(",");
					for (String abbrev : chars) {
						listToChoose.add(new Characteristic(abbrev));
					}
					updateCharacteristics.add(listToChoose);
				} else {
					// List of only one characteristic (Player is not allowed to
					// choose).
					String[] chars = trainingLine.replace(";", ",").split(",");
					for (String abbrev : chars) {
						List<Characteristic> listToChoose = new ArrayList<>();
						listToChoose.add(new Characteristic(abbrev));
						updateCharacteristics.add(listToChoose);
					}
				}
			} catch (Exception e) {
				MostrarMensaje.showErrorMessage("Problema con la linea: \"" + trainingLine
						+ "\" del adiestramiento " + Personaje.getInstance().adiestramiento.nombre,
						"Leer Adiestramiento");
			}
			index++;
		}
		return index;
	}

	private int setProfessionalRequirements(List<String> lines, int index) {
		while (lines.get(index).equals("") || lines.get(index).startsWith("#")) {
			index++;
		}
		
		
		
		Habilidad hab;
		Caracteristica car;
		boolean seguirComprobando = true;

		index += 3;
		Personaje.getInstance().listaAficiones = new ArrayList<>();
		while (!lines.get(index).equals("")) {
			if (creandoPJ) {
				String lineaAdiestramiento = lines.get(index);
				if (!lineaAdiestramiento.equals("Ninguna") && !lineaAdiestramiento.equals("Ninguno")) {
					String[] requisitos = lineaAdiestramiento.split(", ");
					for (int i = 0; i < requisitos.length; i++) {
						// Religion (10) (-3)
						String[] requisito = requisitos[i].split("\\(");
						String nombre = requisito[0];
						int valor = Integer.parseInt(requisito[1].replace(")", ""));
						int modificaciones = Integer.parseInt(requisito[2].replace(")", ""));
						// Si es una habilidad, es tener más de X rangos.
						if ((hab = Personaje.getInstance().DevolverHabilidadDeNombre(nombre)) != null
								&& seguirComprobando) {
							if (hab.DevolverRangos() >= valor) {
								modificadorCoste = modificaciones;
							}
						} else if ((car = Personaje.getInstance().caracteristicas
								.DevolverCaracteristicaDeAbreviatura(nombre)) != null && seguirComprobando) {
							// Si es una caracteristica, es superar un valor.
							if (car.Total() >= valor) {
								modificadorCoste = modificaciones;
							}
						} else {
							modificadorCoste = 0;
							seguirComprobando = false;
						}
					}
				}
			}
			index++;
		}
		return index;
	}
}

class TrainingSpecial {
	private String name;
	private int bonus;
	private int probability;

	public TrainingSpecial(String name, int bonus, int probability) {
		this.name = name;
		this.probability = probability;
		this.bonus = bonus;
	}
}

class TrainingCategory {
	private String name;
	private Integer ranks;
	private Integer minSkills;
	private Integer maxSkills;
	private List<TrainingSkill> skills;

	public TrainingCategory(String name, Integer ranks, Integer minSkills, Integer maxSkills) {
		this.name = name;
		this.ranks = ranks;
		this.minSkills = minSkills;
		this.maxSkills = maxSkills;
		skills = new ArrayList<>();
	}

	protected void addSkill(TrainingSkill skill) {
		skills.add(skill);
	}
}

class TrainingSkill {
	private List<String> skillOptions; // List to choose from.
	private Integer ranks;

	public TrainingSkill(List<String> skillOptions, Integer ranks) {
		this.skillOptions = skillOptions;
		this.ranks = ranks;
	}
}